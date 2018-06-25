package com.codegym.blog.repository;

import com.codegym.blog.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByEmail(String email);

    User findByPhone(String phone);
}
