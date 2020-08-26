package ru.geekbrains.spring.ishop.providers;

import org.springframework.stereotype.Repository;
import ru.geekbrains.spring.ishop.entity.Product;
import ru.geekbrains.spring.ishop.providers.interfaces.IIdentityMap;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductIdentityMap implements IIdentityMap {
    private final Map<Long, Product> identityMap = new HashMap<>();

    @Override
    public void add(Object object) {
        Product product = (Product) object;
        identityMap.put(product.getId(), product);
    }

    @Override
    public Object delete(Object object) {
        Product product = (Product) object;
        return identityMap.remove(product.getId());
    }

    @Override
    public Object get(Object object) {
        Long id = (Long) object;
        Product mapProduct = identityMap.get(id);
        //чтобы не давать доступ извне к элементам множества, копируем объект
        Product product = new Product();
        product.setId(mapProduct.getId());
        product.setTitle(mapProduct.getTitle());
        product.setDescription(mapProduct.getDescription());
        product.setPrice(mapProduct.getPrice());
        product.setCategory(mapProduct.getCategory());
        product.setImgPathname(mapProduct.getImgPathname());

        return product;
    }
}
