package com.mLastovsky.filter;

import com.mLastovsky.dto.UserDto;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Set;

import static com.mLastovsky.util.UrlPath.*;

@Slf4j
@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    private static final Set<String> PUBLIC_PATHS = Set.of(LOGIN, REGISTRATION);
    private static final String USER_SESSION_ATTRIBUTE = "user";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var request = (HttpServletRequest) servletRequest;
        var response = (HttpServletResponse) servletResponse;
        var uri = request.getRequestURI();

        log.info("AuthorizationFilter invoked for URI: {}", uri);

        try {
            if (isPublicPath(uri)) {
                log.debug("Public path accessed: {}", uri);
                filterChain.doFilter(servletRequest, servletResponse);
            } else if (isUserLoggedIn(request)) {
                log.info("User is logged in. Proceeding with request for URI: {}", uri);
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                log.warn("Unauthorized access attempt to URI: {}. Redirecting to login.", uri);
                redirectToLogin(response);
            }
        } catch (IOException | ServletException e) {
            log.error("Error during request processing in AuthorizationFilter for URI: {}", uri, e);
        }
    }

    private boolean isPublicPath(String uri) {
        boolean isPublic = PUBLIC_PATHS.stream().anyMatch(uri::startsWith);
        log.debug("Is URI public: {} -> {}", uri, isPublic);
        return isPublic;
    }

    private boolean isUserLoggedIn(HttpServletRequest request) {
        UserDto user = (UserDto) request.getSession().getAttribute(USER_SESSION_ATTRIBUTE);
        log.debug("User logged in: {}", user != null);
        return user != null;
    }

    private void redirectToLogin(HttpServletResponse response) throws IOException {
        log.info("Redirecting to login page: {}", LOGIN);
        response.sendRedirect(LOGIN);
    }
}
