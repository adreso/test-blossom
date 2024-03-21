package co.com.blossom.configs.security;

import co.com.blossom.configs.utils.EnvironmentProps;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.Arrays;
import java.util.List;


@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfiguration {
    @NotNull
    private JWTFilter jwtFilter;
    @NotNull
    private final EnvironmentProps environments;

    @Bean
    public SecurityFilterChain security2FilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .cors()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        final String ALL = "ALL";

        String origins = environments.getSecurityAllowedOrigins();
        String methods = environments.getSecurityAllowedMethods();
        String headers = environments.getSecurityAllowedHeaders();

        if(origins != null) {
            if(origins.equals(ALL)) {
                configuration.setAllowedOrigins(List.of(CorsConfiguration.ALL));
            }else {
                configuration.setAllowedOrigins(Arrays.asList(origins.split(",")));
            }
        }

        if(methods != null) {
            configuration.setAllowedMethods(Arrays.asList(methods.split(",")));
        }

        if(headers != null) {
            if(headers.equals(ALL)) {
                configuration.setAllowedHeaders(List.of(CorsConfiguration.ALL));
            }else {
                configuration.setAllowedHeaders(Arrays.asList(headers.split(",")));
            }
        }

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.cognitoClientRegistration());
    }

    private ClientRegistration cognitoClientRegistration() {
        final String USER_POOL_URI = "https://" + environments.getAWSCognitoUserPoolId() + ".auth.us-east-1.amazoncognito.com";
        final String AUTHORIZATION_URI = USER_POOL_URI + "/oauth2/authorize";
        final String TOKEN_URI = USER_POOL_URI + "/oauth2/token";
        final String USER_INFO_URI = USER_POOL_URI + "/oauth2/userInfo";

        return ClientRegistration.withRegistrationId("cognito")
            .clientId(environments.getAWSCognitoClientId())
            .clientSecret(environments.getAWSCognitoSecret())
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .scope("email")
            .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
            .authorizationUri(AUTHORIZATION_URI)
            .tokenUri(TOKEN_URI)
            .userInfoUri(USER_INFO_URI)
            .userNameAttributeName("sub")
            .clientName("Cognito")
            .build();
    }
}
