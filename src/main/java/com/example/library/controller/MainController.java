package com.example.library.controller;

import com.example.library.model.Role;
import com.example.library.model.User;
import com.example.library.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Controller
public class MainController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MainController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/index")
    public String greeting() {
        return "index";
    }


    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String saveNewUser(User user, Model model) {
        User userFromDb = userService.getUserByName(user.getName());
        String message;
        if (Objects.nonNull(userFromDb)) {
            message = "User with such name already exists!";
            model.addAttribute("message", message);
            return "registration";
        }
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("USER"));
        user.setRoles(roles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        message = "Registration is succesfull, so now you can sign in!";
        model.addAttribute("message", message);
        return "index";
    }

}
