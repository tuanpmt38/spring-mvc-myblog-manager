package com.codegym.blog.controller;

import com.codegym.blog.model.Blog;
import com.codegym.blog.model.Category;
import com.codegym.blog.service.BlogService;
import com.codegym.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.Lob;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/categories")
    public ModelAndView listCategory(){
        Iterable<Category> categories = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView("/category/list");
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/create-category")
    public ModelAndView createFormCategory(){

        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/create-category")
    public ModelAndView create(@ModelAttribute ("category") Category category){

        ModelAndView modelAndView = new ModelAndView("/category/create");
        categoryService.save(category);
        modelAndView.addObject("category",category);
        modelAndView.addObject("message","create new the category successfully");
        return modelAndView;
    }

    @GetMapping("/edit-category/{id}")
    public ModelAndView editFormCategory(@PathVariable("id")Long id){
        Category category = categoryService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/category/edit");
        modelAndView.addObject("category",category);
        return modelAndView;
    }

    @PostMapping("/edit-category")
    public ModelAndView editCategory (@ModelAttribute("category") Category category){
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("/category/edit");
        modelAndView.addObject("category",category);
        modelAndView.addObject("message","update successfully");
        return modelAndView;
    }

    @GetMapping("/delete-category/{id}")
    public ModelAndView deleteFormCategory (@PathVariable("id") Long id){
        Category category = categoryService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/category/delete");
        modelAndView.addObject("category", category);
        return modelAndView;
    }

    @PostMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable("id") Long id){
        categoryService.remove(id);
        return "redirect:/categories";
    }

    @GetMapping("/view-category/{id}")
    public ModelAndView viewCategory(@PathVariable("id") Long id){

        Category category = categoryService.findById(id);
        if(category == null){
            ModelAndView modelAndView = new ModelAndView("/error-404");
            return modelAndView;
        }else {
            Iterable<Blog> blogs = blogService.findAllByCategory(category);
            ModelAndView modelAndView = new ModelAndView("/category/view");
            modelAndView.addObject("category",category);
            modelAndView.addObject("blog", blogs);
            return modelAndView;
        }
    }
}
