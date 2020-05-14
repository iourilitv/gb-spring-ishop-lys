package ru.geekbrains.spring.ishop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.repository.ProductRepository;
import ru.geekbrains.spring.ishop.utils.ProductFilter;

import java.util.Map;

@Service
public class ProductService {

    private ProductRepository repository;

    @Autowired
    public void setRepository(ProductRepository repository) {
        this.repository = repository;
    }

//    public Page<Product> findAll(Map<String, String> params, ProductFilter filter) {
//        //номер страницы
//        int pageIndex = 0;
//        //количество объектов, выводимых на страницу
//        int limit = 3;
//        //направление сортировки (по умолчанию "по возрастанию")
//        Sort.Direction direction = Sort.Direction.ASC;
//        //если в запросе указан хотя бы один параметр, вынимаем параметры
//        if(params != null && !params.isEmpty()) {
//            //если указан номер страницы
//            if(params.containsKey("page") && !params.get("page").isEmpty()) {
//                pageIndex = Integer.parseInt(params.get("page")) - 1;
//            }
//            //если указан количество объектов, выводимых на страницу
//            if(params.containsKey("limit") && !params.get("limit").isEmpty()) {
//                limit = Integer.parseInt(params.get("limit"));
//            }
//            //если указан направление сортировки
//            if(params.containsKey("direction") && !params.get("direction").isEmpty()) {
//                direction = params.get("direction").equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
//            }
//        }
//        //инициируем объект пагинации с сортировкой
//        Pageable pageRequest = PageRequest.of(pageIndex, limit, direction, "price");
//        return repository.findAll(filter.getSpec(), pageRequest);
//    }
    public Page<Product> findAll(Map<String, String> params, ProductFilter filter) {
        //номер страницы
        int pageIndex = 0;
        //количество объектов, выводимых на страницу
        int limit = 3;
        //направление сортировки (по умолчанию "по возрастанию")
        Sort.Direction direction = Sort.Direction.ASC;
        //если в запросе указан хотя бы один параметр, вынимаем параметры
        if(params != null && !params.isEmpty()) {
            //если указан номер страницы
            if(params.containsKey("page") && !params.get("page").isEmpty()) {
                pageIndex = Integer.parseInt(params.get("page")) - 1;
            }
            //если указан количество объектов, выводимых на страницу
            if(params.containsKey("limit") && !params.get("limit").isEmpty()) {
                limit = Integer.parseInt(params.get("limit"));
                //добавляем параметр к строке запроса
                filter.getFilterDefinition().append("&limit=").append(limit);
            }
            //если указан направление сортировки
            if(params.containsKey("direction") && !params.get("direction").isEmpty()) {
                direction = params.get("direction").equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
                //добавляем параметр к строке запроса
                filter.getFilterDefinition().append("&direction=").append(direction);
            }
        }
        //инициируем объект пагинации с сортировкой
        Pageable pageRequest = PageRequest.of(pageIndex, limit, direction, "price");
        return repository.findAll(filter.getSpec(), pageRequest);
    }

    public Product findById(Long id) {
        return repository.getOne(id);
    }

}
