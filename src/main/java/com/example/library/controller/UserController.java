package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.User;
import com.example.library.model.UserPrincipal;
import com.example.library.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String getAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/get/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        Set<Book> books = user.getBooks();
        String message = "This is list of books that " + user.getName() + " uses at this time:";
        model.addAttribute("message", message);
        model.addAttribute("books", books);
        return "profile_admin";
    }

    @GetMapping("/profile")
    public String getUserProfile(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        String message = userPrincipal.getUsername() + ", this is list of books that you use at this time:";
        Set<Book> books = userService.getUserById(userPrincipal.getUser().getId()).getBooks();
        model.addAttribute("message", message);
        model.addAttribute("books", books);
        return "profile";
    }

}
