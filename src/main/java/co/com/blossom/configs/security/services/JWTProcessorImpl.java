package co.com.blossom.configs.security.services;

import co.com.blossom.configs.exceptions.InfraestructureException;
import co.com.blossom.configs.security.JWTUtils;
import co.com.blossom.configs.security.model.TokenDetail;
import co.com.blossom.configs.utils.EnvironmentProps;
import co.com.blossom.configs.utils.ErrorCode;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import com.auth0.jwt.interfaces.Verification;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Locale;
import java.util.Map;

@Service
@AllArgsConstructor
public class JWTProcessorImpl implements JWTProcessor {

    @NotNull
    private final EnvironmentProps environmentProps;
    @NotNull
    private final MessageSource messageSource;

    @Override
    public TokenDetail deserializeToken(String token) {

        TokenDetail tokenDetail = TokenDetail.builder().build();
        try {
            validateToken(token);
            Map<String, Object> out = JWTUtils.decodeJWT(token);
            tokenDetail.setNameUser((String) out.get("email"));
        } catch (JwkException e) {
            throw new InfraestructureException(messageSource.getMessage("common.cognito.token.error",
                new Object[]{e.getMessage()}, Locale.getDefault()), ErrorCode.DOMAIN_RESOURCE_DELETED);
        }

        return tokenDetail;
    }

    private void validateToken(String token) throws JwkException {
        String jwksUrl = "https://cognito-idp.us-east-1.amazonaws.com/" +
            environmentProps.getAWSCognitoUserPoolId();
        JwkProvider provider = new UrlJwkProvider(jwksUrl);

        Jwk jwk = provider.get(environmentProps.getAWSCognitoKidValue());
        RSAKeyProvider keyProvider = new RSAKeyProvider() {
            @Override
            public RSAPublicKey getPublicKeyById(String kid) {
                try {
                    return (RSAPublicKey) jwk.getPublicKey();
                } catch (Exception e) {
                    throw new RuntimeException("Error al obtener la clave pública", e);
                }
            }

            @Override
            public RSAPrivateKey getPrivateKey() {
                return null;
            }

            @Override
            public String getPrivateKeyId() {
                return null;
            }
        };

        Algorithm algorithm = Algorithm.RSA256(keyProvider);
        Verification verifier = JWT.require(algorithm);
        JWTVerifier jwtVerifier = verifier.build();

        jwtVerifier.verify(token);
    }
}
