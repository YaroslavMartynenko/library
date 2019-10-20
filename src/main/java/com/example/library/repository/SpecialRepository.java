package com.example.library.repository;

import com.example.library.model.Book;
import com.example.library.model.CustomFilter;

import java.util.List;

public interface SpecialRepository {

    List<Book> getBooksByCustomFilter(CustomFilter customFilter);
}
