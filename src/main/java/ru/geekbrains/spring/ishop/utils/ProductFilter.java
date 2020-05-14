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
        //если есть хотя бы один параметр
        if(map != null && !map.isEmpty()) {
            //если в параметрах есть параметр минимальной цены
            if(map.containsKey("minPrice")
                    && !map.get("minPrice").isEmpty()) {
                //инициируем переменную минимальной цены из параметра
                BigDecimal minPrice = new BigDecimal(map.get("minPrice"));
                //добавляем по и условие фильтра в спецификацию фильтра
                spec = spec.and(ProductSpecifications.priceGEThan(minPrice));
                //добавляем параметр фильтра к строке запроса
                filterDefinition.append("&minPrice=").append(minPrice);
            }
            //если в параметрах есть параметр максимальной цены
            if(map.containsKey("maxPrice")
                    && !map.get("maxPrice").isEmpty()) {
                //инициируем переменную минимальной цены из параметра
                BigDecimal maxPrice = new BigDecimal(map.get("maxPrice"));
                //добавляем по и условие фильтра в спецификацию фильтра
                spec = spec.and(ProductSpecifications.priceLEThan(maxPrice));
                //добавляем параметр фильтра к строке запроса
                filterDefinition.append("&maxPrice=").append(maxPrice);
            }
//            //если в параметрах есть параметр количества элементов на странице
//            if(map.containsKey("limit")
//                    && !map.get("limit").isEmpty()) {
//                //количество объектов, выводимых на страницу
//                int limit = 3;
//                //если указан количество объектов, выводимых на страницу
//                if(map.containsKey("limit") && !map.get("limit").isEmpty()) {
//                    //инициируем переменную из параметра
//                    limit = Integer.parseInt(map.get("limit"));
//                }
////                //добавляем по и условие фильтра в спецификацию фильтра
////                spec = spec.and(ProductSpecifications.priceLEThan(maxPrice));
//                //добавляем параметр фильтра к строке запроса
//                filterDefinition.append("&limit=").append(limit);
//            }
        }
    }

    public Specification<Product> getSpec() {
        return spec;
    }

    public StringBuilder getFilterDefinition() {
        return filterDefinition;
    }

}
