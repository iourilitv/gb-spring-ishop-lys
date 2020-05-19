package ru.geekbrains.spring.ishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.geekbrains.spring.ishop.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends
        JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("from Product p where p.title = :title")
    List<Product> getProductsByTitle(@Param("title") String title);
}
