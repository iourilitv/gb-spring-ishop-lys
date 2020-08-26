package ru.geekbrains.spring.ishop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.providers.ProductIdentityMap;
import ru.geekbrains.spring.ishop.providers.interfaces.IProductRepositorySql2o;
import ru.geekbrains.spring.ishop.repository.ProductRepository;
import ru.geekbrains.spring.ishop.utils.ProductFilter;
import ru.geekbrains.spring.ishop.utils.UtilFilter;

@Service
public class ProductService {
    private ProductIdentityMap productIdentityMap;
    private IProductRepositorySql2o productRepositorySql2o;
    private ProductRepository repository;
    private UtilFilter utilFilter;

    @Autowired
    public void setProductIdentityMap(ProductIdentityMap productIdentityMap) {
        this.productIdentityMap = productIdentityMap;
    }

    @Autowired
    public void setProductRepositorySql2o(IProductRepositorySql2o productRepositorySql2o) {
        this.productRepositorySql2o = productRepositorySql2o;
    }

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

//    public Product findById(Long id) {
//        return repository.getOne(id);
//    }
    public Product findById(Long id) {
        Product product = (Product) productIdentityMap.get(id);
        if(product == null) {
            product = productRepositorySql2o.findById(id);
            productIdentityMap.add(product);
        }
        return product;
    }

//    public void save(Product product) {
//        repository.save(product);
//    }
    public void save(Product product) {
        if(product.getId() == null) {
            productRepositorySql2o.insert(product);
        } else {
            productRepositorySql2o.update(product);
        }
        productIdentityMap.add(productRepositorySql2o.findByTitle(product.getTitle()));
    }

//    public void delete(Product product) {
//        repository.delete(product);
//    }
    public void delete(Product product) {
        productRepositorySql2o.delete(product);
        productIdentityMap.delete(product);
    }

}
