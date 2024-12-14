package com.mLastovsky.servlet;

import com.mLastovsky.dto.CreateUserDto;
import com.mLastovsky.exception.ValidationException;
import com.mLastovsky.service.UserService;
import com.mLastovsky.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;

import static com.mLastovsky.util.UrlPath.LOGIN;
import static com.mLastovsky.util.UrlPath.REGISTRATION;

@Slf4j
@WebServlet(REGISTRATION)
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServletException {
        log.debug("Handling GET request for registration page.");
        req.getRequestDispatcher(JspHelper.getPath("registration"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        log.info("Registration attempt for username: {}, email: {}", username, email);
        var userDto = CreateUserDto.builder()
                .username(username)
                .email(email)
                .password(hashedPassword)
                .build();

        try {
            userService.create(userDto);
            log.info("User {} successfully registered, redirecting to login page.", username);
            resp.sendRedirect(LOGIN);
        } catch (ValidationException e){
            log.warn("Validation failed for user: {} with errors: {}", username, e.getErrors());
            req.setAttribute("error", e.getErrors());
            doGet(req,resp);
        }
    }
}
