package com.mLastovsky.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter(value = "/*",
        dispatcherTypes = DispatcherType.ERROR
)
public class ErrorPageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("ErrorPageFilter invoked");
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (IOException | ServletException e) {
            log.error("Error during request processing in ErrorPageFilter", e);
        }
        log.info("ErrorPageFilter completed");
    }
}
