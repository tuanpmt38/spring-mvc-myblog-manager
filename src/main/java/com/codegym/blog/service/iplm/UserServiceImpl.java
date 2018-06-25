package com.codegym.blog.service.iplm;

import com.codegym.blog.model.User;
import com.codegym.blog.repository.UserRepository;
import com.codegym.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean existEmail(String email) {
        userRepository.findByEmail(email);
        return (email != null);
    }

    @Override
    public boolean existPhone(String phone) {
        userRepository.findByPhone(phone);
        return (phone !=null);
    }
}
