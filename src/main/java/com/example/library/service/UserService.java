package com.example.library.service;

import com.example.library.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    void saveUser(User user);

    void updateUser(User user);

    User getUserByName(String name);
}
