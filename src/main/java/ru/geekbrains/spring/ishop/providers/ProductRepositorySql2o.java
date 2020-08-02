package ru.geekbrains.spring.ishop.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.entity.Role;
import ru.geekbrains.spring.ishop.providers.interfaces.IProductRepositorySql2o;

/**
 * Implementation of Interface "ProductMapper" for lesson 6 hw.
 */
@Component
public class ProductRepositorySql2o implements IProductRepositorySql2o {
    //объявляем точку подключения - провайдера подключения к БД
    private final Sql2o sql2o;

    //инжектировали провайдера подключения к БД
    public ProductRepositorySql2o(@Autowired Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Product findById(Long id) {
        final String query =
                "select * from products where id = :id";
        try (Connection connection = sql2o.open()) {
            return connection
                    .createQuery(query, false)
                    .addParameter("id", id)
                    //выполняем запрос и получаем первый объект
                    .executeAndFetchFirst(Product.class);
        }
    }

    @Override
    public void insert(Product product) {
        final String query =
                "INSERT INTO products (title, description, price, category_id) " +
                        "VALUES (:title, :description, :price, :category_id)";
        try (Connection connection = sql2o.beginTransaction()) {

            connection.createQuery(query)
                    .addParameter("title", product.getTitle())
                    .addParameter("description", product.getDescription())
                    .addParameter("price", product.getPrice())
                    .addParameter("category_id", product.getCategory())
                    .executeUpdate();
            connection.commit();
        }
    }

    @Override
    public void update(Product product) {
        final String query =
                "UPDATE products SET title=:title, description=:description, price=:price, category_id=:category_id " +
                        "WHERE id=:id";
        try (Connection connection = sql2o.beginTransaction()) {

            connection.createQuery(query)
                    .addParameter("id", product.getId())
                    .addParameter("title", product.getTitle())
                    .addParameter("description", product.getDescription())
                    .addParameter("price", product.getPrice())
                    .addParameter("category_id", product.getCategory())
                    .executeUpdate();
            connection.commit();
        }
    }

    @Override
    public void delete(Product product) {
        final String query =
                "DELETE FROM products WHERE id=:id";
        try (Connection connection = sql2o.beginTransaction()) {

            connection.createQuery(query)
                    .addParameter("id", product.getId())
                    .executeUpdate();
            connection.commit();
        }
    }
}
