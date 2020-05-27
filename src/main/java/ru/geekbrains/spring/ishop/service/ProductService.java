package ru.geekbrains.spring.ishop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.repository.ProductRepository;
import ru.geekbrains.spring.ishop.utils.ProductFilter;
import ru.geekbrains.spring.ishop.utils.UtilFilter;

@Service
public class ProductService {
    private ProductRepository repository;
    private UtilFilter utilFilter;

    @Autowired
    public void setRepository(ProductRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setUtilFilter(UtilFilter utilFilter) {
        this.utilFilter = utilFilter;
    }

    public Page<Product> findAll(ProductFilter filter, String property) {
        //инициируем объект пагинации с сортировкой
        Pageable pageRequest = PageRequest.of(utilFilter.getPageIndex(),
                utilFilter.getLimit(), utilFilter.getDirection(), property);
        return repository.findAll(filter.getSpec(), pageRequest);
    }

    public Product findById(Long id) {
        return repository.getOne(id);
    }

    public void save(Product product) {
        repository.save(product);
    }

    public void delete(Product product) {
        repository.delete(product);
    }
}
