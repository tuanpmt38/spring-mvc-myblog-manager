package com.codegym.blog.service;

import com.codegym.blog.model.Blog;
import com.codegym.blog.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {

    Page<Blog> findAll(Pageable pageable);

    Iterable findAllByCategory(Category category);

    Blog findById (Long id);

    void save(Blog blog);

    void delete (Long id);

    Page<Blog> findAllByTitleContaining(String title, Pageable pageable);
}
