package com.example.library.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomFilter {

    private String title;
    private String author;
    private String genre;
    private String year;

    public CustomFilter() {
    }

    public CustomFilter(String title, String author, String genre, String year) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCustomQuery() {

        String titleFilter;
        String authorFilter;
        String genreFilter;
        String yearFilter;

        List<String> filters = new ArrayList<>();

        if (Objects.nonNull(title) && !title.isEmpty()) {
            titleFilter = "b.title = '" + title + "'";
            filters.add(titleFilter);
        }
        if (Objects.nonNull(author) && !author.isEmpty()) {
            authorFilter = "b.author = '" + author + "'";
            filters.add(authorFilter);
        }
        if (Objects.nonNull(genre) && !genre.isEmpty()) {
            genreFilter = "b.genre = '" + genre + "'";
            filters.add(genreFilter);
        }
        if (Objects.nonNull(year) && !year.isEmpty()) {
            yearFilter = "b.year = " + year;
            filters.add(yearFilter);
        }

        StringBuilder compositeFilter = new StringBuilder();
        int size = filters.size();

        if (size == 0) {
        } else if (size == 1) {
            compositeFilter.append(" WHERE " + filters.get(0));
        } else {
            compositeFilter.append(" WHERE ");
            for (int i = 0; i < size - 1; i++) {
                compositeFilter.append(filters.get(i) + " AND ");
            }
            compositeFilter.append(filters.get(size - 1));
        }
        String query = compositeFilter.insert(0, "SELECT b FROM Book b").toString();
        return query;
    }
}
