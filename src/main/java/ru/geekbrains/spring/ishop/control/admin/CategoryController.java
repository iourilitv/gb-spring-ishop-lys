package ru.geekbrains.spring.ishop.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.ishop.entity.Category;
import ru.geekbrains.spring.ishop.service.CategoryService;
import ru.geekbrains.spring.ishop.utils.filters.CategoryFilter;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin/product/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryFilter categoryFilter;

    @Autowired
    public CategoryController(CategoryService categoryService,
                              CategoryFilter categoryFilter) {
        this.categoryService = categoryService;
        this.categoryFilter = categoryFilter;
    }

    @GetMapping
    public String sectionRoot() {
        return "redirect:/admin/product/category/all";
    }

    @GetMapping("/all")
    public String categoriesList(@RequestParam Map<String, String> params,
                                 Model model) {
        //инициируем настройки фильтра
        categoryFilter.init(params);
        //получаем объект страницы с применением фильтра
        Page<Category> page = categoryService.findAll(categoryFilter, "id");//TODO id -> константы
        //передаем в .html атрибуты:
        //часть строки запроса с параметрами фильтра
        model.addAttribute("filterDef", categoryFilter.getFilterDefinition());
        //объект страницы продуктов
        model.addAttribute("page", page);
        //активную страницу
        model.addAttribute("activePage", "Categories");
        return "categories";
    }

    @GetMapping("/form")
    public String categoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "category_form";
    }

    @GetMapping("/{id}/id/edit")
    public String editCategory(@PathVariable Optional<Short> id, Model model) {
        Category category = categoryService.findById(id.orElseThrow(() -> new RuntimeException("There is no id presented!")));
        model.addAttribute("category", category);
        return "category_form";
    }

    @GetMapping("/{id}/id/delete")
    public String deleteCategory(@PathVariable Optional<Short> id) {
        Category category = categoryService.findById(id.orElseThrow(() -> new RuntimeException("There is no id presented!")));
        categoryService.delete(category);
        return "redirect:/admin/product/category/all";
    }

    @PostMapping("/form")
    public String updateCategory(@ModelAttribute @Valid Category category,
                                BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            //FIXME
            return "redirect:/admin/product/category/form";
        }
        categoryService.save(category);
        return "redirect:/admin/product/category/all";
    }

}
