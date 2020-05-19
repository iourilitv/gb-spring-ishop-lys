package ru.geekbrains.spring.ishop.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.ishop.entity.Category;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.service.CategoryService;
import ru.geekbrains.spring.ishop.service.ProductService;
import ru.geekbrains.spring.ishop.utils.ProductFilter;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

//    @RequestMapping("/product")
//    public void productRequests (@RequestAttribute Re) {
//      }

}
