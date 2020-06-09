package ru.geekbrains.spring.ishop.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.services.CategoryService;
import ru.geekbrains.spring.ishop.services.ProductService;
import ru.geekbrains.spring.ishop.utils.ProductFilter;

import java.util.Map;

@Controller
@RequestMapping("/catalog")
public class CatalogController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductFilter productFilter;

    @Autowired
    public CatalogController(ProductService productService,
                             CategoryService categoryService,
                             ProductFilter productFilter) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.productFilter  = productFilter;
    }

    //http://localhost:8080/shop/catalog/all
    //http://localhost:8080/shop/catalog/all?page=1&limit=6&direction=DESC&minPrice=1000&maxPrice=10000
    @GetMapping("/all")
    public String allProducts(@RequestParam Map<String, String> params, Model model) {
        //инициируем настройки фильтра
        productFilter.init(params);
        //получаем объект страницы с применением фильтра
        Page<Product> page = productService.findAll(productFilter,"price");//TODO price -> константы
        //передаем в .html атрибуты:
        //часть строки запроса с параметрами фильтра
        model.addAttribute("filterDef", productFilter.getFilterDefinition());
        //коллекцию категорий
        categoryService.addToModelAttributeCategories(model);
        //объект страницы продуктов
        model.addAttribute("page", page);
        //активную страницу
        model.addAttribute("activePage", "Catalog");
        //вызываем файл catalog.html
        return "amin/catalog";
    }

    @GetMapping("/{prod_id}/details")
    public String productDetails(@PathVariable(value = "prod_id") Long prod_id,
                                 Model model) {
        categoryService.addToModelAttributeCategories(model);
        model.addAttribute("product", productService.findById(prod_id));
        return "amin/product-details";
    }

//    public void addToModelAttributeCategories(Model model){
//        //получаем коллекцию всех категорий
//        List<Category> categories = categoryService.findAll(
//                Sort.by(Sort.Direction.ASC, "title"));//TODO title -> константы
//        //коллекцию категорий
//        model.addAttribute("categories", categories);
//    }
}
