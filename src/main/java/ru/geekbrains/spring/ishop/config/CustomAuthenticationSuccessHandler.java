package ru.geekbrains.spring.ishop.config;

import ru.geekbrains.spring.ishop.entity.User;
import ru.geekbrains.spring.ishop.services.interfaces.IUserServiceSql2o;
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
    private IUserServiceSql2o IUserServiceSql2o;

    @Autowired
    public void setIUserServiceSql2o(IUserServiceSql2o IUserServiceSql2o) {
        this.IUserServiceSql2o = IUserServiceSql2o;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String userName = authentication.getName();
        User theUser = IUserServiceSql2o.findByUserName(userName);
        HttpSession session = request.getSession();
        session.setAttribute("user", theUser);
        if(!request.getHeader("referer").contains("login")) {
            response.sendRedirect(request.getHeader("referer"));
        } else {
            response.sendRedirect(request.getContextPath());
        }
    }
}

