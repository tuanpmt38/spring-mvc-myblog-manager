package com.codegym.blog.validation;

import com.codegym.blog.model.FormPost;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BlogValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BlogValidation.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        FormPost formPost = (FormPost) target;
        String title = formPost.getTitle();

        if(title.trim().length() == 0){
            errors.rejectValue("title","blog.title.empty");
        }
    }
}
