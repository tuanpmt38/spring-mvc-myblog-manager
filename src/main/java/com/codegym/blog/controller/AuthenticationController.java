package com.codegym.blog.controller;

import com.codegym.blog.model.User;
import com.codegym.blog.service.UserService;
import com.codegym.blog.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AuthenticationController {

    private UserService userService;
    @Autowired
    public AuthenticationController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView("/login");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView formRegister(){
        ModelAndView modelAndView = new ModelAndView("/register");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult){
        new UserValidation(userService).validate(user, bindingResult);

        if(!bindingResult.hasFieldErrors()){
            userService.saveUser(user);
            ModelAndView modelAndView = new ModelAndView("redirect:/login");
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("/register");
        return modelAndView;
    }
}
