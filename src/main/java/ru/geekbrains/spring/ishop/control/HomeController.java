package ru.geekbrains.spring.ishop.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {

    @GetMapping
    public String indexPage() {
        return "redirect:/home";
    }

//    @GetMapping("/home")
//    public String homePage() {
//        return "redirect:/catalog/all";//TODO
//    }
    //TODO temporarily
    @GetMapping("/home")
    public RedirectView homePage() {
        return new RedirectView("/amin/profile/order/all");
    }

}
