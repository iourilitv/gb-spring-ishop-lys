package ru.geekbrains.spring.ishop.control.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/admin/order")
public class AdminOrderController {

    @GetMapping
    public String sectionRoot() {
        return "redirect:/admin/order/all";
    }

    @GetMapping("/all")
    public String ordersList(@RequestParam Map<String, String> params,
                            Model model) {
        return "amin/admin/admin-orders";
    }

}
