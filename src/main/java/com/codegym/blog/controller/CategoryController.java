package com.codegym.blog.controller;

import com.codegym.blog.model.Blog;
import com.codegym.blog.model.Category;
import com.codegym.blog.service.BlogService;
import com.codegym.blog.service.CategoryService;
import com.codegym.blog.validation.CategoryValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    public static final String ADMIN_CATEGORY_LIST = "/admin/category/list";
    public static final String ADMIN_CATEGORY_FORM_CREATE = "/admin/category/create";
    public static final String ADMIN_CATEGORY_FORM_EDIT = "/admin/category/edit";
    public static final String ADMIN_CATEGORY_DELETE = "/admin/category/delete";
    public static final String ADMIN_CATEGORY_VIEW = "/admin/category/view";
    public static final String ERROR_404 = "/error-404";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BlogService blogService;

    @GetMapping("")
    public ModelAndView listCategory(@RequestParam("search") Optional<String> search, Pageable pageable){

        Page<Category> categories;
        if(search.isPresent()){
            categories = categoryService.findAllByNameContaining(search.get(), pageable);
        }else{
            categories = categoryService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView(ADMIN_CATEGORY_LIST);
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/create-category")
    public ModelAndView createFormCategory(){

        ModelAndView modelAndView = new ModelAndView(ADMIN_CATEGORY_FORM_CREATE);
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/create-category")
    public ModelAndView create(@Valid @ModelAttribute ("category") Category category, BindingResult bindingResult){

        ModelAndView modelAndView = new ModelAndView(ADMIN_CATEGORY_FORM_CREATE);
        new CategoryValidation(categoryService).validate(category, bindingResult);
        if(bindingResult.hasFieldErrors()){
            return modelAndView;
        }
        categoryService.save(category);
        modelAndView.addObject("category",new Category());
        modelAndView.addObject("message","create new the category successfully");
        return modelAndView;
    }

    @GetMapping("/edit-category/{id}")
    public ModelAndView editFormCategory(@PathVariable("id")Long id){
        Category category = categoryService.findById(id);
        ModelAndView modelAndView = new ModelAndView(ADMIN_CATEGORY_FORM_EDIT);
        modelAndView.addObject("category",category);
        return modelAndView;
    }

    @PostMapping("/edit-category")
    public ModelAndView editCategory (@Valid @ModelAttribute("category") Category category, BindingResult bindingResult){

        ModelAndView modelAndView = new ModelAndView(ADMIN_CATEGORY_FORM_EDIT);
        new CategoryValidation(categoryService).validate(category, bindingResult);
        if(bindingResult.hasFieldErrors()){
            return modelAndView;
        }
        categoryService.save(category);
        modelAndView.addObject("category",category);
        modelAndView.addObject("message","update successfully");
        return modelAndView;
    }

    @GetMapping("/delete-category/{id}")
    public ModelAndView deleteFormCategory (@PathVariable("id") Long id){
        Category category = categoryService.findById(id);
        ModelAndView modelAndView = new ModelAndView(ADMIN_CATEGORY_DELETE);
        modelAndView.addObject("category", category);
        return modelAndView;
    }

    @PostMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable("id") Long id){
        categoryService.remove(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/view-category/{id}")
    public ModelAndView viewCategory(@PathVariable("id") Long id){

        Category category = categoryService.findById(id);
        if(category == null){
            ModelAndView modelAndView = new ModelAndView(ERROR_404);
            return modelAndView;
        }else {
            Iterable<Blog> blogs = blogService.findAllByCategory(category);
            ModelAndView modelAndView = new ModelAndView(ADMIN_CATEGORY_VIEW);
            modelAndView.addObject("category",category);
            modelAndView.addObject("blog", blogs);
            return modelAndView;
        }
    }
}
