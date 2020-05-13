package ru.geekbrains.spring.ishop.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.spring.ishop.entity.Product;

import java.math.BigDecimal;

public class ProductSpecifications {

    //метод возвращает спецификацию для фильтра "больше минимальной цены"
    public static Specification<Product> priceGEThan(BigDecimal value) {
        //root - здесь Product - объект спецификации
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), value);
    }

    //метод возвращает спецификацию для фильтра "меньше минимальной цены"
    public static Specification<Product> priceLEThan(BigDecimal value) {
        //root - здесь Product - объект спецификации
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), value);
    }


//    public static Specification<Product> findProductById(Long id) {
//        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder)
//                -> criteriaBuilder.equal(root.get("id"), id);
//    }

//    public static Specification<Product> findProductByPrice(int price) {
//        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder)
//                -> criteriaBuilder.equal(root.get("price"), price);
//    }

}