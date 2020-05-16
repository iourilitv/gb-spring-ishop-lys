package ru.geekbrains.spring.ishop.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.spring.ishop.entity.Category;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.service.CategoryService;
import ru.geekbrains.spring.ishop.service.ProductService;
import ru.geekbrains.spring.ishop.utils.ProductFilter;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/catalog")
public class CatalogController {
    private ProductService productService;
    private CategoryService categoryService;

    @Autowired
    public CatalogController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    //http://localhost:8080/shop/catalog/all
    //http://localhost:8080/shop/catalog/all?page=1&limit=6&direction=DESC&minPrice=1000&maxPrice=10000
    @GetMapping("/all")
    public String allProducts(Model model, @RequestParam Map<String, String> params) {
        //инициируем объект фильтра продуктов
        ProductFilter filter = new ProductFilter(params);
        //получаем объект страницы с применением фильтра
        Page<Product> page = productService.findAll(params, filter);
        //получаем коллекцию всех категорий
//        List<Category> categories = categoryService.findAll();
//        List<Category> categories = categoryService.findAll(
//                Sort.by(Sort.Direction.ASC, "id"));
        List<Category> categories = categoryService.findAll(
                Sort.by(Sort.Direction.ASC, "title"));

        //передаем в .html атрибуты:
        //часть строки запроса с параметрами фильтра
        model.addAttribute("filterDef", filter.getFilterDefinition());
        //коллекцию категорий
        model.addAttribute("categories", categories);
        //объект страницы продуктов
        model.addAttribute("page", page);
        //вызываем файл catalog.html
        return "catalog";
    }

    @GetMapping("/{prod_id}/details")
    public String productDetails(@PathVariable(value = "prod_id") Long prod_id,
                                 Model model) {
        model.addAttribute("product", productService.findById(prod_id));
        return "product_details";
    }

}
