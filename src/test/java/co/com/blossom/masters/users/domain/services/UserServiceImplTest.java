package co.com.blossom.masters.users.domain.services;

import co.com.blossom.auth.appuser.domain.gateways.AppUserGateway;
import co.com.blossom.auth.appuser.domain.model.AppUserDTO;
import co.com.blossom.configs.exceptions.DomainException;
import co.com.blossom.configs.utils.ErrorCode;
import co.com.blossom.masters.users.domain.gateways.UserGateway;
import co.com.blossom.masters.users.domain.model.UserDTO;
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
class UserServiceImplTest {

    @Mock
    private UserGateway gateway;

    @Mock
    private AppUserGateway appUserGateway;

    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private MessageSource messageSource;

    private UserDTO dto;

    @BeforeEach
    void setUp() {
        dto = UserDTO.builder()
            .id(1)
            .userName("user@test.com")
            .firstName("User")
            .lastName("Test")
            .password("123456")
            .build();
    }

    @Test
    @DisplayName("User create")
    void create() {
        when(gateway.createUser(any())).thenReturn(dto);
        UserDTO userDTOCreated = service.createUser(dto);

        assertNotNull(userDTOCreated);
        assertEquals(1, userDTOCreated.getId());
        assertEquals("user@test.com", userDTOCreated.getUserName());

        verify(appUserGateway, times(1)).signUp(any(AppUserDTO.class));
    }

    @Test
    @DisplayName("User create, Exception")
    void createException() {
        String existingUsername = "existingUsername";
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(existingUsername);
        when(gateway.findByUsername(any())).thenReturn(userDTO);
        when(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class)))
            .thenReturn("message");


        Exception exception = assertThrows(DomainException.class, () -> {
            service.createUser(userDTO);
        });
        assertTrue(exception.getMessage().contains("message"));
        assertEquals(ErrorCode.DOMAIN_RESOURCE_DUPLICATE, ((DomainException) exception).getCode());

        // Verify that gateway.signUp() is not called if the username already exists
        verify(gateway, never()).createUser(any(UserDTO.class));
    }
}