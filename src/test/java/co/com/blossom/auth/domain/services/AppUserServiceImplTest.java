package co.com.blossom.auth.domain.services;

import co.com.blossom.auth.appuser.domain.gateways.AppUserGateway;
import co.com.blossom.auth.appuser.domain.model.AppUserCodeDTO;
import co.com.blossom.auth.appuser.domain.model.AppUserDTO;
import co.com.blossom.auth.appuser.domain.model.TokenDTO;
import co.com.blossom.auth.appuser.domain.services.AppUserServiceImpl;
import co.com.blossom.configs.exceptions.DomainException;
import co.com.blossom.configs.utils.ErrorCode;
import co.com.blossom.masters.users.domain.gateways.UserGateway;
import co.com.blossom.masters.users.domain.model.UserDTO;
import co.com.blossom.masters.users.domain.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppUserServiceImplTest {

    @Mock
    private AppUserGateway gateway;

    @InjectMocks
    private AppUserServiceImpl service;

    private AppUserDTO dto;
    private AppUserCodeDTO codeDTO;
    private TokenDTO tokenDTO;

    @BeforeEach
    void setUp() {
        dto = AppUserDTO.builder()
            .username("user@test.com")
            .password("123456")
            .build();

        codeDTO = AppUserCodeDTO.builder()
            .username("user@test.com")
            .code("123456")
            .build();

        tokenDTO = TokenDTO.builder()
            .accessToken("abc.123.xyz")
            .refreshToken("xyz.123.abc")
            .tokenType("Bearer")
            .expiresIn(3600)
            .build();

    }

    @Test
    @DisplayName("Authentication, sign up")
    void signUp() {
        AppUserDTO appUserDTO = service.signUp(dto);
        assertEquals("user@test.com", appUserDTO.getUsername());
        verify(gateway, times(1)).signUp(dto);
    }

    @Test
    @DisplayName("Authentication, sign in")
    void signIn() {
        when(gateway.signIn(dto)).thenReturn(tokenDTO);

        TokenDTO result = service.signIn(dto);
        assertEquals("abc.123.xyz", result.getAccessToken());
        assertEquals(3600, result.getExpiresIn());
    }

    @Test
    @DisplayName("Authentication, confirm account")
    void confirmAccount() {
        when(gateway.confirmAccount(any(String.class),any(String.class))).thenReturn(true);

        boolean result = service.confirmAccount(codeDTO);
        assertTrue(result);
    }
}