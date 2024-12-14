package com.mLastovsky.servlet;

import com.mLastovsky.dto.UserDto;
import com.mLastovsky.service.UserService;
import com.mLastovsky.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.mLastovsky.util.UrlPath.*;

@Slf4j
@WebServlet(LOGIN)
public class LoginServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.trace("Handling GET request for login page");
        log.debug("Redirecting to login page");
        req.getRequestDispatcher(JspHelper.getPath("login"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        log.debug("Attempting login for user: {}", username);

        userService.login(username, password)
                .ifPresentOrElse(
                        user -> onLoginSuccess(user, req, resp),
                        () -> onLoginFail(username, req, resp)
                );
    }

    @SneakyThrows
    private void onLoginSuccess(UserDto user, HttpServletRequest req, HttpServletResponse resp) {
        log.info("Login successful for user: {}", user.getUsername());
        req.getSession().setAttribute("user", user);
        log.info("Redirecting to home page: {}", HOME);
        resp.sendRedirect(HOME);
    }

    @SneakyThrows
    private static void onLoginFail(String username, HttpServletRequest req, HttpServletResponse resp) {
        log.error("Login failed for user: {}", username);
        req.setAttribute("error", "Invalid username or password");
        req.getRequestDispatcher(JspHelper.getPath("login"))
                .forward(req, resp);
    }
}
