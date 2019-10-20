package com.example.library.repository;

import com.example.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsBooksByTitleAndAuthorAndGenreAndYear(String title, String author, String genre, Integer year);

    Book findBookByTitleAndAuthorAndGenreAndYear(String title, String author, String genre, Integer year);
}
