package ru.geekbrains.spring.ishop.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String indexPage() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homePage() {
        return "redirect:/catalog/all";//TODO
    }

}
