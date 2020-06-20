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
import ru.geekbrains.spring.ishop.utils.filters.ProductFilter;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductFilter productFilter;

    @Autowired
    public AdminProductController(ProductService productService,
                                  CategoryService categoryService,
                                  ProductFilter productFilter) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.productFilter = productFilter;
    }

    @GetMapping
    public String sectionRoot() {
        return "redirect:/admin/product/all";
    }

    @GetMapping("/all")
    public String productList(@RequestParam Map<String, String> params, Model model) {
        //инициируем настройки фильтра
        productFilter.init(params);
        //получаем объект страницы с применением фильтра
        Page<Product> page = productService.findAll(productFilter, "id");//TODO id -> константы
        //получаем коллекцию всех категорий
        List<Category> categories = categoryService.findAll(
                Sort.by(Sort.Direction.ASC, "title"));//TODO title -> константы
        //передаем в .html атрибуты:
        //часть строки запроса с параметрами фильтра
        model.addAttribute("filterDef", productFilter.getFilterDefinition());
        //коллекцию категорий
        model.addAttribute("categories", categories);
        //объект страницы продуктов
        model.addAttribute("page", page);
        //активную страницу
        model.addAttribute("activePage", "Products");
        return "products";
    }

    @GetMapping("/form")
    public String productForm(Model model) {
        //получаем коллекцию всех категорий
        List<Category> categories = categoryService.findAll(
                Sort.by(Sort.Direction.ASC, "title"));
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categories);
        return "product_form";
    }

    @GetMapping("/{id}/id/edit")
    public String editProduct(@PathVariable Optional<Long> id, Model model) {
        Product product = productService.findById(id.orElseThrow(() -> new RuntimeException("There is no id presented!")));
        //получаем коллекцию всех категорий
        List<Category> categories = categoryService.findAll(
                Sort.by(Sort.Direction.ASC, "title"));
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "product_form";
    }

    @GetMapping("/{id}/id/delete")
    public String deleteProduct(@PathVariable Optional<Long> id) {
        Product product = productService.findById(id.orElseThrow(() -> new RuntimeException("There is no id presented!")));
        productService.delete(product);
        return "redirect:/admin/product/all";
    }

    /**
     * Метод сохраняет в БД новый продукт.
     * @param product - объект нового продукта
     * @param bindingResult - объект результата валидации
     * @return - ссылку на список всех товаров в разделе admin
     */
    @PostMapping("/form")
    public String updateProduct(@ModelAttribute @Valid Product product,
                                BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            //FIXME Не позволяет редиректить на get с тем же адресом, что и post
            // возможно, потому что это post-запрос
            // придется повторить вызов или новой формы или редактирования товара
            // попробовать https://www.baeldung.com/spring-redirect-and-forward
            return "redirect:/admin/product/form";
        }
        productService.save(product);
        return "redirect:/admin/product/all";
    }

}
