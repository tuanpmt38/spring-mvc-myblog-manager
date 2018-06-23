package com.codegym.blog.validation;


import com.codegym.blog.model.Category;
import com.codegym.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@ComponentScan
public class CategoryValidation implements Validator {

    public CategoryValidation(){}
    @Override
    public boolean supports(Class<?> clazz) {
        return CategoryValidation.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Category category = (Category) target;
        String name = category.getName();
        ValidationUtils.rejectIfEmpty(errors, "name","category.empty");
    }
}
