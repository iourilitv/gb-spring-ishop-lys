package ru.geekbrains.spring.ishop.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.ishop.entity.User;
import ru.geekbrains.spring.ishop.service.UserService;
import ru.geekbrains.spring.ishop.utils.SystemUser;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/profile")
public class UserProfileController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showProfilePage(HttpSession session, Model theModel) {
        User theUser = (User) session.getAttribute("user");
        theModel.addAttribute("systemUser", new SystemUser(theUser));
        return "amin/profile";
    }

}
