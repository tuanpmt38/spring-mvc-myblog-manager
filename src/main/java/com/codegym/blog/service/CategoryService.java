package com.codegym.blog.service;

import com.codegym.blog.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Iterable<Category> findAll();

    Page<Category> findAll(Pageable pageable);

    Category findById (Long id);

    void save (Category category);

    void remove(Long id);

    boolean isExitName (String name);

    Page<Category> findAllByNameContaining(String name, Pageable pageable);

}
