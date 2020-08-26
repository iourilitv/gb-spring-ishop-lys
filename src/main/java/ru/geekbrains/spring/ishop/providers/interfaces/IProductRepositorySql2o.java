package ru.geekbrains.spring.ishop.providers.interfaces;

import ru.geekbrains.spring.ishop.entity.Product;

/**
 * Interface "ProductMapper" for lesson 6 hw.
 */
public interface IProductRepositorySql2o {
    Product findById(Long id);
    Product findByTitle(String title);
    void insert(Product product);
    void update(Product product);
    void delete(Product product);
}
