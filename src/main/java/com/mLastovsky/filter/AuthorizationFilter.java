package com.mLastovsky.filter;

import com.mLastovsky.dto.UserDto;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

import static com.mLastovsky.util.UrlPath.*;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    private static final Set<String> PUBLIC_PATHS = Set.of(LOGIN, REGISTRATION);
    private static final String USER_SESSION_ATTRIBUTE = "user";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var request = (HttpServletRequest) servletRequest;
        var response = (HttpServletResponse) servletResponse;
        var uri = request.getRequestURI();

        if (isPublicPath(uri) || isUserLoggedIn(request)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            redirectToLogin(response);
        }
    }

    private boolean isPublicPath(String uri) {
        return PUBLIC_PATHS.stream().anyMatch(uri::startsWith);
    }

    private boolean isUserLoggedIn(HttpServletRequest request) {
        UserDto user = (UserDto) request.getSession().getAttribute(USER_SESSION_ATTRIBUTE);
        return user != null;
    }

    private void redirectToLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(LOGIN);
    }
}
