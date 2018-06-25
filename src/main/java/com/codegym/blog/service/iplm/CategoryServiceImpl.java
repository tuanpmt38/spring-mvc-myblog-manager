package com.codegym.blog.service.iplm;

import com.codegym.blog.model.Category;
import com.codegym.blog.repository.CategoryRepository;
import com.codegym.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findOne(id);
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void remove(Long id) {
        categoryRepository.delete(id);
    }

    @Override
    public boolean isExitName(String name) {
        Category category = categoryRepository.findByName(name);
        return (name !=null);
    }

    @Override
    public Page<Category> findAllByNameContaining(String name, Pageable pageable) {
        return categoryRepository.findAllByNameContaining(name,pageable);
    }

    @Override
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }
}
