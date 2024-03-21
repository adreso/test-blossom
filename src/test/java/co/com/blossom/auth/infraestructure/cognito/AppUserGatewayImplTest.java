package co.com.blossom.auth.infraestructure.cognito;

import co.com.blossom.auth.appuser.domain.model.AppUserDTO;
import co.com.blossom.auth.appuser.infraestructure.cognito.gateway.AppUserGatewayImpl;
import co.com.blossom.configs.enumerators.RoleENUM;
import co.com.blossom.configs.utils.EnvironmentProps;
import co.com.blossom.masters.users.domain.model.UserDTO;
import co.com.blossom.masters.users.infraestructure.mysql.UserRepository;
import co.com.blossom.masters.users.infraestructure.mysql.gateway.UserGatewayImpl;
import co.com.blossom.masters.users.infraestructure.mysql.mapper.UserMapper;
import co.com.blossom.masters.users.infraestructure.mysql.model.UserEntity;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpResponse;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserGatewayImplTest {
    @Mock
    private CognitoIdentityProviderClient cognitoIdentityProviderClient;
    @Mock
    private MessageSource messageSource;
    @Mock
    private EnvironmentProps environmentProps;
    @InjectMocks
    private AppUserGatewayImpl gateway;
    private AppUserDTO dto;

    @BeforeEach
    void setUp() {
        dto = AppUserDTO.builder()
            .username("user@test.com")
            .password("123456")
            .build();
    }
//
//    @Test
//    @DisplayName("Authentication, sign up success")
//    void signUpSuccess() {
//        when(environmentProps.getAWSCognitoClientId()).thenReturn("clientId");
//        when(environmentProps.getAWSCognitoSecret()).thenReturn("secret");
//        when(environmentProps.getAWSRegion()).thenReturn("us-east-1");
//        when(gateway.getCognitoClient()).thenReturn(cognitoIdentityProviderClient);
//
//        SignUpResponse signUpResponse = SignUpResponse.builder().build();
//        when(cognitoIdentityProviderClient.signUp(any(SignUpRequest.class))).thenReturn(signUpResponse);
//
//        // Act
//        gateway.signUp(dto);
//
//        // Assert
//        verify(cognitoIdentityProviderClient).signUp(any(SignUpRequest.class));
//    }

}