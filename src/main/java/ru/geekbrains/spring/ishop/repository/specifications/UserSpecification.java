package ru.geekbrains.spring.ishop.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.geekbrains.spring.ishop.entity.User;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

//    //метод возвращает спецификацию для фильтра "больше минимальной цены"
//    public static Specification<Product> priceGEThan(BigDecimal value) {
//        //root - здесь Product - объект спецификации
//        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder)
//                -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), value);
//    }

//    //метод возвращает спецификацию для фильтра "меньше минимальной цены"
//    public static Specification<Product> priceLEThan(BigDecimal value) {
//        //root - здесь Product - объект спецификации
//        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder)
//                -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), value);
//    }

//    //метод возвращает спецификацию для фильтра "товары категории"
//    public static Specification<Product> categoryIdEquals(Short value) {
//        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder)
//                -> criteriaBuilder.equal(root.get("category").get("id"), value);
//    }
    //метод возвращает спецификацию для фильтра "пользователи с определенной ролью"
//    public static Specification<User> userRoleIdEquals(Short value) {
//        //TODO почитать
//        //https://www.programcreek.com/java-api-examples/?api=org.springframework.data.jpa.domain.Specification
//
//        return (Specification<User>) (root, criteriaQuery, criteriaBuilder)
//                -> criteriaBuilder.equal(root.get("roles").get(String.valueOf(0)).get("id"), value);
//    }
    public static Specification<User> userRoleIsMember(Short value) {
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder)
                -> criteriaBuilder.isMember(value, root.get("roles"));
    }

}
