package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.CustomFilter;
import com.example.library.model.User;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(Long id);

    void saveBook(Book book);

    void updateBook(Book book);

    void deleteBookById(Long id);

    boolean existsBookInRepository(String title, String author, String genre, Integer year);

    Book findBookByTitleAndAuthorAndGenreAndYear(String title, String author, String genre, Integer year);

    List<Book> getBooksByCustomFilter(CustomFilter customFilter);

    void takeBook(User user, Long bookId);

    void giveBackBook(User currentUser, Long boolId);
}
