package ru.geekbrains.spring.ishop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.ishop.entity.Category;
import ru.geekbrains.spring.ishop.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository repository;

    @Autowired
    public void setRepository(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public List<Category> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    public Category findById(Short id) {
        return repository.getOne(id);
    }
}
