package co.com.blossom.auth.application.rest;

import co.com.blossom.auth.appuser.application.rest.AppUserController;
import co.com.blossom.auth.appuser.domain.model.AppUserCodeDTO;
import co.com.blossom.auth.appuser.domain.model.AppUserDTO;
import co.com.blossom.auth.appuser.domain.model.TokenDTO;
import co.com.blossom.auth.appuser.domain.services.AppUserService;
import co.com.blossom.masters.users.application.rest.UserController;
import co.com.blossom.masters.users.domain.model.UserDTO;
import co.com.blossom.masters.users.domain.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AppUserControllerTest {
    @Mock
    private AppUserService service;
    @InjectMocks
    private AppUserController controller;
    private MockMvc mockMvc;
    private AppUserDTO dto;
    private AppUserCodeDTO codeDTO;
    private TokenDTO tokenDTO;

    @BeforeEach
    void setUp() {
        dto = AppUserDTO.builder()
                .username("user@test.com")
                .password("123456")
                .build();

        tokenDTO = TokenDTO.builder()
            .accessToken("abc.123.xyz")
            .refreshToken("xyz.123.abc")
            .tokenType("Bearer")
            .expiresIn(3600)
            .build();

        codeDTO = AppUserCodeDTO.builder()
            .username("user@test.com")
            .code("123456")
            .build();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(
                        new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    @DisplayName("Authentication, confirm account")
    void confirmAccount() throws Exception {
        when(service.confirmAccount(codeDTO)).thenReturn(true);

        mockMvc.perform(post("/v1/auth/confirm-account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(codeDTO)))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Authentication, sign in")
    void signIn() throws Exception {
        when(service.signIn(dto)).thenReturn(tokenDTO);

        mockMvc.perform(post("/v1/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto)))
            .andExpect(status().isOk());
    }

    static String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


}