package com.codegym.blog.validation;

import com.codegym.blog.model.User;
import com.codegym.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidation implements Validator {

    private UserService userService;

    @Autowired
    public UserValidation(UserService userService){
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserValidation.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        User user = (User) target;
        String name = user.getName();
        String email = user.getEmail();
        String phone = user.getPhone();
        String password = user.getPassword();

        ValidationUtils.rejectIfEmpty(errors,"name","name.empty");
        ValidationUtils.rejectIfEmpty(errors,"name","email.empty");
        ValidationUtils.rejectIfEmpty(errors,"name","phone.empty");
        ValidationUtils.rejectIfEmpty(errors,"name","password.empty");

        if(userService.existEmail(email)) {
            errors.rejectValue("email", "email.exists" );
        }
        if (userService.existPhone(phone)) {
            errors.rejectValue("phone", "phone.exists");
        }
        if (!phone.startsWith("0")){
            errors.rejectValue("phone", "phone.startsWith");
        }
        if (password.length() < 6 || password.length() > 20){
            errors.rejectValue("password", "password.length");
        }

    }
}
