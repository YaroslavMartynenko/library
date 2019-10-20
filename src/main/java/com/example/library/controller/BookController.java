package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.CustomFilter;
import com.example.library.model.User;
import com.example.library.model.UserPrincipal;
import com.example.library.service.BookService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/list")
    public String getAllBooks(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        boolean admin = userPrincipal.getUser().getRoles()
                .stream().map(role -> role.getName()).anyMatch(name -> name.equals("ADMIN"));
        if (admin) {
            return "books_admin";
        }
        return "books";
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        String message = "Hello, " + userPrincipal.getUsername() + "!";
        model.addAttribute("message", message);
        boolean admin = userPrincipal.getUser().getRoles()
                .stream().map(role -> role.getName()).anyMatch(name -> name.equals("ADMIN"));
        if (admin) {
            return "main_admin";
        }
        return "main";
    }

    @PostMapping("/get/{id}")
    public String getBookById(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "update_book";
    }

    @PostMapping("/update")
    public String updateBook(Book book) {
        bookService.updateBook(book);
        return "redirect:/book/list";
    }

    @GetMapping("/save")
    public String saveNewBook() {
        return "save_book";
    }

    @PostMapping("/save")
    public String saveNewBook(Book book) {
        bookService.saveBook(book);
        return "redirect:/book/list";
    }

    @PostMapping("/find")
    public String getBookByCustomFilter(@RequestParam String title, @RequestParam String author,
                                        @RequestParam String genre, @RequestParam String year, Model model,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {

        CustomFilter customFilter = new CustomFilter(title, author, genre, year);
        List<Book> books = bookService.getBooksByCustomFilter(customFilter);
        String message = null;
        if (books.isEmpty()) {
            message = "No books conform to your request.";
        }
        model.addAttribute("books", books);
        model.addAttribute("message", message);
        boolean admin = userPrincipal.getUser().getRoles()
                .stream().map(role -> role.getName()).anyMatch(name -> name.equals("ADMIN"));
        if (admin) {
            return "books_admin";
        }
        return "books";
    }

    @PostMapping("delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return "redirect:/book/list";
    }

    @PostMapping("/take/{id}")
    public String takeBook(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        bookService.takeBook(user, id);
        return "redirect:/book/list";
    }

    @PostMapping("/giveBack/{id}")
    public String giveBackBook(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        bookService.giveBackBook(user, id);
        return "redirect:/user/profile";
    }
}
