package com.codegym.blog.repository;

import com.codegym.blog.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    Category findByName(String name);
    Page<Category> findAllByNameContaining(String name, Pageable pageable);

}
