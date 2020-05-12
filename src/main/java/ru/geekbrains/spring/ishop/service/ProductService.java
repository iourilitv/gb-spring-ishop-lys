package ru.geekbrains.spring.ishop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository repository;

    @Autowired
    public void setRepository(ProductRepository repository) {
        this.repository = repository;
    }


    public List<Product> findAllProducts() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        return repository.getOne(id);
    }

//    public Product findById(Long id) {
//        return repository.findById(id).get();
//    }
}
