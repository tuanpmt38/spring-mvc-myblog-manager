package com.codegym.blog.validation;

import com.codegym.blog.model.Category;
import com.codegym.blog.service.CategoryService;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class CategoryValidation implements Validator {

    private CategoryService categoryService;

    public CategoryValidation(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CategoryValidation.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Category category = (Category) target;
        String name = category.getName();

        ValidationUtils.rejectIfEmpty(errors, "name","category.empty");
        if(categoryService.isExitName(name)){
            errors.rejectValue("name", "category.exit");
        }
    }
}
