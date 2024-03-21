package co.com.blossom.configs.security;

import co.com.blossom.configs.security.model.TokenDetail;
import co.com.blossom.configs.security.model.UserSession;
import co.com.blossom.configs.security.services.JWTProcessor;
import co.com.blossom.configs.utils.EnvironmentProps;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JWTFilter extends OncePerRequestFilter {
    private RequestMatcher requestMatcherDirectAccess;
    private final JWTProcessor jwtProcessor;
    private final EnvironmentProps environmentProps;

    public JWTFilter(EnvironmentProps environmentProps, JWTProcessor jwtProcessor) {
        String property = environmentProps.getSecurityAllowedUris();

        this.environmentProps = environmentProps;
        this.jwtProcessor = jwtProcessor;

        if(property != null) {
            List<RequestMatcher> matchers = Arrays.stream(property.split(","))
                    .map(AntPathRequestMatcher::new).collect(Collectors.toList());

            requestMatcherDirectAccess = new OrRequestMatcher(matchers);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if(validateRequestAllowedWithoutToken(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!validateRequestWithToken(authorizationHeader)) {
            createLogAnd401Response(request.getRequestURI(),
                    "request without Authorization", response);
            return;
        }

        UserSession userSession = processAuthentication(request, response, authorizationHeader);

        if(userSession != null && processAuthorization(request, response, userSession)) {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userSession, null, null);

            SecurityContextHolder.getContext().setAuthentication(auth);

            filterChain.doFilter(request, response);
        }
    }

    private UserSession processAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              String authorizationHeader) {
        UserSession userSession = null;

        try {
            TokenDetail tokenDetail = jwtProcessor.deserializeToken(
                    authorizationHeader.substring(7));

            userSession = UserSession.builder()
                    .username(tokenDetail.getNombreUsuario())
                    .build();

            if(tokenDetail.getRoles() != null && !tokenDetail.getRoles().isEmpty()) {
                userSession.setGrantedAuthorities(
                        tokenDetail.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            }
        }catch (RuntimeException ex) {
            createLogAnd401Response(request.getRequestURI(), ex.getMessage(), response);
        }

        return userSession;
    }

    /**
     * Procesa la autorización para la petición. Lee los permisos configurados en
     * el caché y valida si puede ejecutar la acción
     *
     * @param request Objeto con la información de la petición
     * @param response Objeto con la información de la respuesta
     * @return true si puede ejecutar la petición, false en caso contrario
     */
    private boolean processAuthorization(HttpServletRequest request,
                                         HttpServletResponse response,
                                         UserSession userSession) {
        boolean hasAccess = true;

        //TODO: Implement the logic to validate the user session and the url access

        if(!hasAccess) {
            log.error(userSession.getUsername() + " " + request.getRequestURI() + " " + HttpServletResponse.SC_FORBIDDEN);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        return true;
    }

    private boolean validateRequestWithToken(String authorizationHeader) {

        boolean hasAuthToken = Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer");

        return hasAuthToken;
    }

    /**
     * Valida si la URI de la petición permite acceso sin un token
     *
     * @param request Objeto con la información del request
     * @return true si el URI permite acceso sin token, false en caso contrario
     */
    private boolean validateRequestAllowedWithoutToken(HttpServletRequest request) {
        return Objects.nonNull(requestMatcherDirectAccess) && requestMatcherDirectAccess.matches(request);
    }

    /**
     * Verificar si el UUID de sesión existe en el caché
     * @param uuidsession
     * @return true si el UUID de sesión existe, false en caso contrario
     */

    /**
     * Crea el log y configura el response para que entregue un código status 401
     *
     * @param uri URI a la cual se hizo la petición
     * @param logMessage Mensaje a registrar en el log
     * @param response Objeto con la información del response
     */
    private void createLogAnd401Response(String uri, String logMessage,
                                         HttpServletResponse response) {
        log.error(uri + " " + logMessage);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
