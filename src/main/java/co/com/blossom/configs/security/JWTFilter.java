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

        if (property != null) {
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

        if (validateRequestAllowedWithoutToken(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!validateRequestWithToken(authorizationHeader)) {
            createLogAnd401Response(request.getRequestURI(),
                    "request without Authorization", response);
            return;
        }

        UserSession userSession = processAuthentication(request, response, authorizationHeader);

        if (userSession != null && processAuthorization(request, response, userSession)) {
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
                    .username(tokenDetail.getNameUser())
                    .build();

        } catch (RuntimeException ex) {
            createLogAnd401Response(request.getRequestURI(), ex.getMessage(), response);
        }

        return userSession;
    }

    /**
     * Processes the authorization for the request.
     * Reads the permissions configured in * the cache and validates if it can execute the action * *
     *
     * @param request Object with the request information * @param response Object with the response information *
     * @return true if it can execute the request, false otherwise
     */
    private boolean processAuthorization(HttpServletRequest request,
                                         HttpServletResponse response,
                                         UserSession userSession) {
        boolean hasAccess = true;

        //TODO: Implement the logic to validate the user session and the url access

        if (!hasAccess) {
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
     * Validates if the URI of the request allows access without a token
     *
     * @param request Object with the request information
     * @return true if the URI allows access without a token, false otherwise
     */
    private boolean validateRequestAllowedWithoutToken(HttpServletRequest request) {
        return Objects.nonNull(requestMatcherDirectAccess) && requestMatcherDirectAccess.matches(request);
    }

    /**
     * Verify if the session UUID exists in the cache
     * @param uuidsession
     * @return true if the session UUID exists, false otherwise
     */

    /**
     * Creates the log and configures the response to deliver a status code 401
     *
     * @param uri        URI to which the request was made
     * @param logMessage Message to be recorded in the log
     * @param response   Object with the response information
     */
    private void createLogAnd401Response(String uri, String logMessage,
                                         HttpServletResponse response) {
        log.error(uri + " " + logMessage);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
