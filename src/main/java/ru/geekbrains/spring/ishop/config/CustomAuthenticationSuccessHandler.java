package ru.geekbrains.spring.ishop.config;

import ru.geekbrains.spring.ishop.entity.User;
import ru.geekbrains.spring.ishop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String userName = authentication.getName();
        User theUser = userService.findByUserName(userName);
        HttpSession session = request.getSession();
        session.setAttribute("user", theUser);
        if(!request.getHeader("referer").contains("login")) {
            response.sendRedirect(request.getHeader("referer"));
        } else {
            response.sendRedirect(request.getContextPath());
        }
    }
}

