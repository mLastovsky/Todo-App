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
        log.info("doGet method in LoginServlet, redirect to login.jsp");
        req.getRequestDispatcher(JspHelper.getPath("login"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.login(req.getParameter("username"), req.getParameter("password"))
                .ifPresentOrElse(
                        user -> onLoginSuccess(user, req, resp),
                        () -> getOnLoginFail(req, resp)
                );
    }

    @SneakyThrows
    private void onLoginSuccess(UserDto user, HttpServletRequest req, HttpServletResponse resp){
        log.info("login success, redirect to {}", HOME);
        req.getSession().setAttribute("user", user);
        resp.sendRedirect(HOME);
    }

    @SneakyThrows
    private static void getOnLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        log.error("login error for user:{}", req.getParameter("username"));
        resp.sendRedirect(LOGIN + "?username=" + req.getParameter("username"));
    }
}
