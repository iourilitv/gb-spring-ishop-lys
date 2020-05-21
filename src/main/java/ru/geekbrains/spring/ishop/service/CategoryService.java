package ru.geekbrains.spring.ishop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.Category;
import ru.geekbrains.spring.ishop.repository.CategoryRepository;
import ru.geekbrains.spring.ishop.utils.CategoryFilter;
import ru.geekbrains.spring.ishop.utils.UtilFilter;

import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository repository;
    private UtilFilter utilFilter;

    @Autowired
    public void setRepository(CategoryRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setUtilFilter(UtilFilter utilFilter) {
        this.utilFilter = utilFilter;
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public List<Category> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    public Page<Category> findAll(CategoryFilter filter, String property) {
        //инициируем объект пагинации с сортировкой
        Pageable pageRequest = PageRequest.of(utilFilter.getPageIndex(),
                utilFilter.getLimit(), utilFilter.getDirection(), property);
        return repository.findAll(filter.getSpec(), pageRequest);
    }

    public Category findById(Short id) {
        return repository.getOne(id);
    }

    public void save(Category category) {
        repository.save(category);
    }

    public void delete(Category category) {
        repository.delete(category);
    }
}
