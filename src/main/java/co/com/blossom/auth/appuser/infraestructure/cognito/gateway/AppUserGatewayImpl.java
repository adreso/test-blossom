package co.com.blossom.auth.appuser.infraestructure.cognito.gateway;

import co.com.blossom.auth.appuser.domain.gateways.AppUserGateway;
import co.com.blossom.auth.appuser.domain.model.AppUserDTO;
import co.com.blossom.auth.appuser.domain.model.TokenDTO;
import co.com.blossom.configs.exceptions.InfraestructureException;
import co.com.blossom.configs.utils.EnvironmentProps;
import co.com.blossom.configs.utils.ErrorCode;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserGatewayImpl implements AppUserGateway {
    @NotNull
    private final EnvironmentProps environments;
    @NotNull
    private final MessageSource messageSource;

    @Override
    public void signUp(AppUserDTO appUserDTO) {
        registerUser(getCognitoClient(), appUserDTO.getUsername(), appUserDTO.getPassword());
    }

    @Override
    public TokenDTO signIn(AppUserDTO appUserDTO) {
        return initiateAuth(getCognitoClient(), appUserDTO.getUsername(), appUserDTO.getPassword());
    }

    @Override
    public boolean confirmAccount(String username, String code) {
        return confirmSignUp(getCognitoClient(), username, code);
    }

    private void registerUser(CognitoIdentityProviderClient identityProviderClient, String userName, String password) {
        String clientId = environments.getAWSCognitoClientId();
        String secret = environments.getAWSCognitoSecret();
        String hash = calculateSecretHash(userName, clientId, secret);
        AttributeType email = AttributeType.builder()
            .name("email")
            .value(userName)
            .build();

        List<AttributeType> userAttrsList = Arrays.asList(email);
        try {
            SignUpRequest signUpRequest = SignUpRequest.builder()
                .userAttributes(userAttrsList)
                .username(userName)
                .clientId(clientId)
                .secretHash(hash)
                .password(password)
                .build();

            identityProviderClient.signUp(signUpRequest);
            log.info("User {} has been registered", userName);
        } catch (CognitoIdentityProviderException e) {
            throw new InfraestructureException(messageSource.getMessage("common.cognito.error",
                new Object[] {e.awsErrorDetails().errorMessage()}, Locale.getDefault()), ErrorCode.INFRA_RESOURCE_VIOLATED_CONDITION);
        }
    }

    private boolean confirmSignUp(CognitoIdentityProviderClient identityProviderClient, String userName, String code) {
        String clientId = environments.getAWSCognitoClientId();
        String secret = environments.getAWSCognitoSecret();
        String hash = calculateSecretHash(userName, clientId, secret);

        try {
            ConfirmSignUpRequest signUpRequest = ConfirmSignUpRequest.builder()
                .clientId(clientId)
                .confirmationCode(code)
                .username(userName)
                .secretHash(hash)
                .build();

            identityProviderClient.confirmSignUp(signUpRequest);

            log.info("User {} has been confirmed", userName);
            return true;

        } catch (CognitoIdentityProviderException e) {
            throw new InfraestructureException(messageSource.getMessage("common.cognito.error",
                new Object[] {e.awsErrorDetails().errorMessage()}, Locale.getDefault()), ErrorCode.INFRA_RESOURCE_VIOLATED_CONDITION);
        }
    }

    private TokenDTO initiateAuth(CognitoIdentityProviderClient identityProviderClient, String userName, String password) {
        String userPoolId = environments.getAWSCognitoUserPoolId();
        String clientId = environments.getAWSCognitoClientId();
        String secret = environments.getAWSCognitoSecret();
        String hash = calculateSecretHash(userName, clientId, secret);

        try {
            Map<String, String> authParameters = new HashMap<>();
            authParameters.put("USERNAME", userName);
            authParameters.put("PASSWORD", password);
            authParameters.put("SECRET_HASH", hash);

            AdminInitiateAuthRequest authRequest = AdminInitiateAuthRequest.builder()
                .clientId(clientId)
                .userPoolId(userPoolId)
                .authParameters(authParameters)
                .authFlow(AuthFlowType.ADMIN_USER_PASSWORD_AUTH)
                .build();

            AdminInitiateAuthResponse response = identityProviderClient.adminInitiateAuth(authRequest);

            log.info("User {} has been authenticated", userName);
            return TokenDTO.builder()
                .accessToken(response.authenticationResult().idToken())
                .expiresIn(response.authenticationResult().expiresIn())
                .refreshToken(response.authenticationResult().refreshToken())
                .tokenType(response.authenticationResult().tokenType())
                .build();

        } catch (CognitoIdentityProviderException e) {
            throw new InfraestructureException(messageSource.getMessage("common.cognito.error",
                new Object[] {e.awsErrorDetails().errorMessage()}, Locale.getDefault()), ErrorCode.INFRA_RESOURCE_VIOLATED_CONDITION);
        }
    }

    private CognitoIdentityProviderClient getCognitoClient() {
        Region region = Region.of(environments.getAWSRegion());
        return CognitoIdentityProviderClient.builder().region(region).build();
    }

    private static String calculateSecretHash(String userName, String clientId, String clientSecret) {
        try {
            String message = userName + clientId;
            byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
            byte[] secretKeyBytes = clientSecret.getBytes(StandardCharsets.UTF_8);

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA256");
            sha256_HMAC.init(secretKeySpec);

            byte[] hashBytes = sha256_HMAC.doFinal(messageBytes);
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
