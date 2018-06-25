package com.codegym.blog.service;

import com.codegym.blog.model.User;

public interface UserService {

    User findByEmail(String email);

    User findById(Long id);
}
