package ru.geekbrains.spring.ishop.utils;

import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.repository.specifications.ProductSpecifications;

import java.math.BigDecimal;
import java.util.Map;

public class ProductFilter {
    private Specification<Product> spec;
    private StringBuilder filterDefinition;

    public ProductFilter(Map<String, String> map) {
        //инициируем нулевую спецификацию фильтра(фильтр не применится)
        this.spec = Specification.where(null);
        //инициируем объект билдера строки для сборки строки с параметрами фильтра,
        // добавляемыми к запросу
        this.filterDefinition = new StringBuilder();
        //если в параметрах есть параметр минимальной цены
        if(map != null && map.containsKey("min_price")
                && !map.get("min_price").isEmpty()) {
            //инициируем переменную минимальной цены из параметра
            BigDecimal minPrice = new BigDecimal(map.get("min_price"));
            //добавляем по и условие фильтра в спецификацию фильтра
            spec = spec.and(ProductSpecifications.priceGEThan(minPrice));
            //добавляем параметр фильтра к строке запроса
            filterDefinition.append("&min_price=").append(minPrice);
        }
        //если в параметрах есть параметр максимальной цены
        if(map != null && map.containsKey("max_price")
                && !map.get("max_price").isEmpty()) {
            //инициируем переменную минимальной цены из параметра
            BigDecimal maxPrice = new BigDecimal(map.get("max_price"));
            //добавляем по и условие фильтра в спецификацию фильтра
            spec = spec.and(ProductSpecifications.priceLEThan(maxPrice));
            //добавляем параметр фильтра к строке запроса
            filterDefinition.append("&max_price=").append(maxPrice);
        }

//        if(map.containsKey("product_id") && !map.get("product_id").isEmpty()) {
//            Long product_id = Long.parseLong(map.get("product_id"));
//            spec.or(ProductSpecifications.findProductById(product_id));
//        }

//        if(map != null && map.containsKey("price") && !map.get("price").isEmpty()) {
//            int price = Integer.parseInt(map.get("price"));
//            spec = spec.or(ProductSpecifications.findProductByPrice(price));
//        }
    }

    public Specification<Product> getSpec() {
        return spec;
    }

    public StringBuilder getFilterDefinition() {
        return filterDefinition;
    }
}
