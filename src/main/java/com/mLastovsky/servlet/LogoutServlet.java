package com.mLastovsky.servlet;

import com.mLastovsky.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.mLastovsky.util.UrlPath.LOGIN;
import static com.mLastovsky.util.UrlPath.LOGOUT;

@Slf4j
@WebServlet(LOGOUT)
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Processing logout request for user: {}", ((UserDto) req.getSession().getAttribute("user")).getUsername());
        req.getSession().invalidate();
        log.info("User successfully logged out, redirecting to login page");
        resp.sendRedirect(LOGIN);
    }
}
