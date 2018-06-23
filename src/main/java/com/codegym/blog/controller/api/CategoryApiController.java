package com.codegym.blog.controller.api;

import com.codegym.blog.model.Blog;
import com.codegym.blog.model.Category;
import com.codegym.blog.service.BlogService;
import com.codegym.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class CategoryApiController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/api/category")
    public ResponseEntity<Iterable<Category>> listAllCategory(){

        Iterable<Category> categories = categoryService.findAll();
        return new ResponseEntity<Iterable<Category>>(categories, HttpStatus.OK);
    }

    @GetMapping("/api/category/{id}")
    public ResponseEntity <Category> getCategory(@PathVariable("id") Long id){
        Category category = categoryService.findById(id);
        if(category == null){
            return new ResponseEntity<Category>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Category>(category, HttpStatus.OK);
    }

    @GetMapping("/api/view-category/{id}")
    public ResponseEntity<Category> getBlogInCategory(@PathVariable("id") Long id){
        Category category = categoryService.findById(id);
        Iterable<Blog> blogs = blogService.findAllByCategory(category);
        if(category == null){
            return new ResponseEntity<Category>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Category>(category, HttpStatus.OK);
    }

}
