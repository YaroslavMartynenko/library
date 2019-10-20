package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.CustomFilter;
import com.example.library.model.User;
import com.example.library.repository.BookRepository;
import com.example.library.repository.SpecialRepository;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final SpecialRepository specialRepository;

    public BookServiceImpl(BookRepository bookRepository, SpecialRepository specialRepository) {
        this.bookRepository = bookRepository;
        this.specialRepository = specialRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).get();
    }

    @Override
    public void saveBook(Book book) {
        if (existsBookInRepository(book.getTitle(), book.getAuthor(), book.getGenre(), book.getYear())) {
            Book bookFromRepository = findBookByTitleAndAuthorAndGenreAndYear(book.getTitle(),
                    book.getAuthor(), book.getGenre(), book.getYear());
            bookFromRepository.increaseQuantity(book.getQuantity());
            book = bookFromRepository;
        }
        bookRepository.save(book);
    }

    @Override
    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public boolean existsBookInRepository(String title, String author, String genre, Integer year) {
        return bookRepository.existsBooksByTitleAndAuthorAndGenreAndYear(title, author, genre, year);
    }

    @Override
    public Book findBookByTitleAndAuthorAndGenreAndYear(String title, String author, String genre, Integer year) {
        return bookRepository.findBookByTitleAndAuthorAndGenreAndYear(title, author, genre, year);
    }

    @Override
    public List<Book> getBooksByCustomFilter(CustomFilter customFilter) {
        return specialRepository.getBooksByCustomFilter(customFilter);
    }

    @Override
    public void takeBook(User user, Long bookId) {
        Book book = getBookById(bookId);
        boolean sameUser = book.getUsers()
                .stream().map(u -> u.getId()).anyMatch(id -> id.equals(user.getId()));
        if (book.getQuantity() > 0 && !sameUser) {
            book.reduceQuantity(1);
            book.getUsers().add(user);
            updateBook(book);
        }
    }

    @Override
    public void giveBackBook(User currentUser, Long bookId) {
        Book book = getBookById(bookId);
        Iterator<User> iterator = book.getUsers().iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (currentUser.getId().equals(user.getId())) {
                iterator.remove();
                break;
            }
        }
        book.increaseQuantity(1);
        updateBook(book);
    }
}
