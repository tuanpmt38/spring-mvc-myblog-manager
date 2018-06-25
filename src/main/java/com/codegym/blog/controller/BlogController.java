package com.codegym.blog.controller;


import com.codegym.blog.model.Blog;
import com.codegym.blog.model.Category;
import com.codegym.blog.model.FormPost;
import com.codegym.blog.service.BlogService;
import com.codegym.blog.service.CategoryService;
import com.codegym.blog.validation.BlogValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import utils.StorageUtils;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Controller

@RequestMapping("/blogs")
public class BlogController {
    public static final String ADMIN_BLOG_CREATE = "/admin/blog/create";
    public static final String ADMIN_BLOG_LIST = "/admin/blog/list";
    public static final String ADMIN_BLOG_EDIT = "/admin/blog/edit";
    public static final String ADMIN_BLOG_DELETE = "/admin/blog/delete";
    public static final String ADMIN_BLOG_VIEW = "/admin/blog/view";
    public static final String ERROR_404 = "/error-404";

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping("/create-blog")
    public ModelAndView createForm() {

        ModelAndView modelAndView = new ModelAndView(ADMIN_BLOG_CREATE);
        modelAndView.addObject("formPost", new FormPost());
        return modelAndView;
    }

    @PostMapping("/create-blog")
    public ModelAndView saveBlog(@Valid @ModelAttribute("formPost") FormPost formPost, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView(ADMIN_BLOG_CREATE);

        new BlogValidation().validate(formPost,bindingResult);

        if(bindingResult.hasFieldErrors()){
            return modelAndView;
        }
        try {

            String randomCode = UUID.randomUUID().toString();
            String originFileName = formPost.getFeature().getOriginalFilename();
            String randomName = randomCode + StorageUtils.getFileExtension(originFileName);
            formPost.getFeature().transferTo(new File(StorageUtils.FEATURE_LOCATION + "/" + randomName));

            Blog blog = new Blog();
            blog.setTitle(formPost.getTitle());
            blog.setSummary(formPost.getSummary());
            blog.setContent(formPost.getContent());
            blog.setCreateDate(LocalDate.now());
            blog.setFeature(randomName);
            blog.setCategory(formPost.getCategory());
            blogService.save(blog);

        } catch (IOException e) {
            e.printStackTrace();
        }


        modelAndView.addObject("formPost", new FormPost());
        modelAndView.addObject("message", "create blog successfully");
        return modelAndView;
    }

    @GetMapping("/")
    public ModelAndView listBlog(@RequestParam("search") Optional<String> search, Pageable pageable) {

        Page<Blog> blogs;

        if (search.isPresent()) {
            blogs = blogService.findAllByTitleContaining(search.get(), pageable);
        } else {
            blogs = blogService.findAll(pageable);
        }

        ModelAndView modelAndView = new ModelAndView(ADMIN_BLOG_LIST);
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }

    @GetMapping("/edit-blog/{id}")
    public ModelAndView editForm(@PathVariable("id") Long id) {

        Blog blog = blogService.findById(id);
        FormPost formPost = new FormPost();

        formPost.setId(blog.getId());
        formPost.setTitle(blog.getTitle());
        formPost.setSummary(blog.getSummary());
        formPost.setContent(blog.getContent());
        formPost.setCategory(blog.getCategory());
        formPost.setFeatureUrl(blog.getFeature());

        ModelAndView modelAndView = new ModelAndView(ADMIN_BLOG_EDIT);
        modelAndView.addObject("formPost", formPost);
        return modelAndView;
    }

    @PostMapping("/edit-blog/{id}")
    public ModelAndView updateBlog(@Valid @PathVariable("id") Long id,
                                   @ModelAttribute("formPost") FormPost formPost,
                                   BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView(ADMIN_BLOG_EDIT);

        new BlogValidation().validate(formPost, bindingResult);
        if(bindingResult.hasFieldErrors()){
            return modelAndView;
        }

        Blog blog = blogService.findById(id);

        if(!formPost.getFeature().isEmpty()){

            StorageUtils.removeFeature(blog.getFeature());
            String randomCode = UUID.randomUUID().toString();
            String originFileName = formPost.getFeature().getOriginalFilename();
            String randomName = randomCode + StorageUtils.getFileExtension(originFileName);

            try {
                formPost.getFeature().transferTo(new File(StorageUtils.FEATURE_LOCATION +"/"+randomName));
            } catch (IOException e) {
                e.printStackTrace();
            }

            blog.setFeature(randomName);
            formPost.setFeatureUrl(randomName);
        }

        blog.setId(formPost.getId());
        blog.setTitle(formPost.getTitle());
        blog.setSummary(formPost.getSummary());
        blog.setContent(formPost.getContent());
        blog.setCategory(formPost.getCategory());
        blogService.save(blog);

        modelAndView.addObject("formPost", formPost);
        modelAndView.addObject("message", "update blog successfully");
        return modelAndView;
    }

    @GetMapping("/delete-blog/{id}")
    public ModelAndView deleteForm(@PathVariable("id") Long id) {

        Blog blog = blogService.findById(id);
        if (blog == null) {
            ModelAndView modelAndView = new ModelAndView(ERROR_404);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView(ADMIN_BLOG_DELETE);
            modelAndView.addObject("blog", blog);
            return modelAndView;
        }
    }

    @PostMapping("/delete-blog")
    public String delete(@ModelAttribute("blog") Blog blog) {

        blogService.delete(blog.getId());
        return "redirect:/blogs";
    }

    @GetMapping("/view-blog/{id}")
    public ModelAndView view(@PathVariable Long id) {

        Blog blog = blogService.findById(id);
        if (blog != null) {
            ModelAndView modelAndView = new ModelAndView(ADMIN_BLOG_VIEW);
            modelAndView.addObject("blog", blog);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView(ERROR_404);
            return modelAndView;
        }
    }
}
