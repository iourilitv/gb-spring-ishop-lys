package ru.geekbrains.spring.ishop.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminSectionController {
    private AdminProductController adminProductController;

    @Autowired
    public AdminSectionController(AdminProductController adminProductController) {
        this.adminProductController = adminProductController;
    }

    //FIXME сделать индексную страницу для раздела admin
    @GetMapping
    public String adminSection() {
        return "redirect:/admin/product/all";
    }

}
