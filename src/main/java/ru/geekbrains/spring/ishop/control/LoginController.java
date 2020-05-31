package ru.geekbrains.spring.ishop.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String showMyLoginPage() {
//        return "amin/login";
        return "login";
    }
}
