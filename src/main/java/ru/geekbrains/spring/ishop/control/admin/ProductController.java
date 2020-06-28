package ru.geekbrains.spring.ishop.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import ru.geekbrains.spring.ishop.entity.Category;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.entity.ProductImage;
import ru.geekbrains.spring.ishop.service.CategoryService;
import ru.geekbrains.spring.ishop.service.ImageSaverService;
import ru.geekbrains.spring.ishop.service.ProductService;
import ru.geekbrains.spring.ishop.utils.filters.ProductFilter;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin/product")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductFilter productFilter;
    private final ImageSaverService imageSaverService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService,
                             ProductFilter productFilter, ImageSaverService imageSaverService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.productFilter = productFilter;
        this.imageSaverService = imageSaverService;
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
        return "amin/products";
    }

    @GetMapping("/create")
    public RedirectView createNewProduct(Model model, HttpSession session) {
        Product product = new Product();
        session.setAttribute("product", product);
        return new RedirectView("/amin/admin/product/edit/0/prod_id");
    }

    @GetMapping("/edit/{prod_id}/prod_id")
    public String editProduct(@PathVariable Long prod_id, Model model,
                              HttpSession session) {
        Product product;
        if(prod_id != 0) {
            product = productService.findById(prod_id);
        } else {
            product = (Product) session.getAttribute("product");
        }
        //получаем коллекцию всех категорий
        List<Category> categories = categoryService.findAll(
                Sort.by(Sort.Direction.ASC, "title"));
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "amin/product-form";
    }

    @GetMapping("/delete/{prod_id}/prod_id")
    public String deleteProduct(@PathVariable Long prod_id) {
        Product product = productService.findById(prod_id);
        productService.delete(product);
        return "redirect:/admin/product/all";
    }

    @PostMapping("/process/create")
    public RedirectView processCreateProduct(@Valid @ModelAttribute("product") Product product,
                                        BindingResult bindingResult,
                                        @RequestParam("file") MultipartFile file) {
        if(bindingResult.hasErrors()){
            return new RedirectView("/amin/admin/product/edit/0/prod_id");
        }

        if (productService.isProductWithTitleExists(product.getTitle())) {
            // todo не отображает сообщение
            bindingResult.addError(new ObjectError("product.title", "Товар с таким названием уже существует"));
        }

        if (!file.isEmpty()) {
            String pathToSavedImage = imageSaverService.saveFile(file);
            ProductImage productImage = new ProductImage();
            productImage.setPath(pathToSavedImage);
            productImage.setProduct(product);
            productService.addImage(product, productImage);
        }

        productService.save(product);
        return new RedirectView("/amin/admin/product/all");
    }

    @PostMapping("/process/edit")
    public RedirectView updateProduct(@ModelAttribute @Valid Product product,
                                      BindingResult bindingResult, HttpSession session) {
//        //TODO получить prod_id из системного продукта в сессии
//        long prod_id = 1L;
//
//        if(bindingResult.hasErrors()){
//            return new RedirectView("/amin/admin/product/edit/" + prod_id + "/prod_id");
//        }
//
//        if (product.getId() == 0 && productService.isProductWithTitleExists(product.getTitle())) {
//            theBindingResult.addError(new ObjectError("product.title", "Товар с таким названием уже существует")); // todo не отображает сообщение
//        }
//
//        if (theBindingResult.hasErrors()) {
//            model.addAttribute("categories", categoryService.getAllCategories());
//            return "edit-product";
//        }
//        if (!file.isEmpty()) {
//            String pathToSavedImage = imageSaverService.saveFile(file);
//            ProductImage productImage = new ProductImage();
//            productImage.setPath(pathToSavedImage);
//            productImage.setProduct(product);
//            productService.addImage(product, productImage);
//        }
//
//        productService.save(product);
        return new RedirectView("/amin/admin/product/all");
    }

}
