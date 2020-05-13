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
import ru.geekbrains.spring.ishop.service.ProductService;
import ru.geekbrains.spring.ishop.utils.ProductFilter;

import java.util.Map;

@Controller
@RequestMapping("/catalog")
public class ProductController {
    private ProductService service;

    @Autowired
    public void setRepository(ProductService service) {
        this.service = service;
    }

    //http://localhost:8080/shop/catalog/all
    //http://localhost:8080/shop/catalog/all?page=1&limit=6&direction=DESC&min_price=1000&max_price=10000
    @GetMapping("/all")
    public String allProducts(Model model, @RequestParam Map<String, String> params) {
        //инициируем объект фильтра продуктов
        ProductFilter filter = new ProductFilter(params);
        //получаем объект страницы с применением фильтра
        Page<Product> page = service.findAll(params, filter);
        //передаем в .html атрибуты:
        //часть строки запроса с параметрами фильтра
        model.addAttribute("filterDef", filter.getFilterDefinition());
        //объект страницы продуктов
        model.addAttribute("page", page);
        //вызываем файл catalog.html
        return "catalog";
    }

    @GetMapping("/{prod_id}/details")
    public String productDetails(@PathVariable(value = "prod_id") Long prod_id,
                                 Model model) {
        model.addAttribute("product", service.findById(prod_id));
        return "product_details";
    }

}
