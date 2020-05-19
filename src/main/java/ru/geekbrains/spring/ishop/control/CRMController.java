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
public class CRMController {
    private ProductService productService;
    private CategoryService categoryService;

    @Autowired
    public CRMController(   ProductService productService,
                            CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String adminSection() {
        return "redirect:/admin/product/all";
    }

    @GetMapping("/product/all")
    public String productList(@RequestParam Map<String, String> params, Model model) {
        //инициируем объект фильтра продуктов
        ProductFilter filter = new ProductFilter(params);
        //получаем объект страницы с применением фильтра
        Page<Product> page = productService.findAll(params, filter, "id");
        //получаем коллекцию всех категорий
        List<Category> categories = categoryService.findAll(
                Sort.by(Sort.Direction.ASC, "title"));
        //передаем в .html атрибуты:
        //часть строки запроса с параметрами фильтра
        model.addAttribute("filterDef", filter.getFilterDefinition());
        //коллекцию категорий
        model.addAttribute("categories", categories);
        //объект страницы продуктов
        model.addAttribute("page", page);
        return "products";
    }

    @GetMapping("/product/form")
    public String productForm(Model model) {
        model.addAttribute("product", new Product());
        return "product_form";
    }

    @PostMapping("/product/form")
    public String updateProduct(@ModelAttribute @Valid Product product,
                                BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "/product/form";
        }
        productService.save(product);
        return "redirect:/catalog/all";
    }

    //    @GetMapping("/product/form/{id}/id")
//    public String formProduct(@PathVariable Optional<Long> id, Model model) {
//        Product product = productService.findById(id.orElseThrow(() -> new RuntimeException("There is no id presented!")));
//        //получаем коллекцию всех категорий
//        List<Category> categories = categoryService.findAll(
//                Sort.by(Sort.Direction.ASC, "id"));
//        model.addAttribute("product", product);
//        model.addAttribute("categories", categories);
//        return "product_form";
//    }
    @GetMapping("/product/{id}/id/edit")
    public String editProduct(@PathVariable Optional<Long> id, Model model) {
        Product product = productService.findById(id.orElseThrow(() -> new RuntimeException("There is no id presented!")));
        //получаем коллекцию всех категорий
        List<Category> categories = categoryService.findAll(
                Sort.by(Sort.Direction.ASC, "id"));
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "product_form";
    }

    @GetMapping("/product/{id}/id/delete")
    public String deleteProduct(@PathVariable Optional<Long> id) {
        Product product = productService.findById(id.orElseThrow(() -> new RuntimeException("There is no id presented!")));
        productService.delete(product);
        return "redirect:/catalog/all";
    }

//    @PostMapping("/product/form")
//    @ResponseBody
//    public String updateProduct(@ModelAttribute @Valid Product product,
//                                BindingResult bindingResult) {
//        if(bindingResult.hasErrors()){
//            return "/product/form";
//        }
//        return product.toString();
//    }

}
