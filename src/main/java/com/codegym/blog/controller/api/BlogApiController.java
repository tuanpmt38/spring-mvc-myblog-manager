package com.codegym.blog.controller.api;

import com.codegym.blog.model.Blog;
import com.codegym.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
public class BlogApiController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/api/blogs")
    public ResponseEntity<Page<Blog>> listAllBlog(Pageable pageable){
        Page<Blog> blogs = blogService.findAll(pageable);
        if (blogs.getTotalPages()==0){
            return new ResponseEntity<Page<Blog>>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<Page<Blog>>(blogs, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/api/blog/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Blog> getBlog(@PathVariable("id") Long id){
        Blog blog = blogService.findById(id);
        if(blog == null){
            return new ResponseEntity<Blog>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Blog>(blog,HttpStatus.OK);
    }
}
