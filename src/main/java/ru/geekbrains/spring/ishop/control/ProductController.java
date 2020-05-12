package ru.geekbrains.spring.ishop.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.service.ProductService;

import java.util.List;

@Controller
@RequestMapping("/catalog")
public class ProductController {
    private ProductService service;

    @Autowired
    public void setRepository(ProductService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public String allProducts(Model model) {
        List<Product> products = service.findAllProducts();
        model.addAttribute("products", products);
        return "catalog";
    }

    @GetMapping("/{prod_id}/details")
    public String productDetails(@PathVariable(value = "prod_id") Long prod_id,
                                 Model model) {
        model.addAttribute("product", service.findById(prod_id));
        return "product_details";
    }

}
