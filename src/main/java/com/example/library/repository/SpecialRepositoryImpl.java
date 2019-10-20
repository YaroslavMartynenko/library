package com.example.library.repository;

import com.example.library.model.Book;
import com.example.library.model.CustomFilter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class SpecialRepositoryImpl implements SpecialRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public SpecialRepositoryImpl() {
    }

    @Override
    public List<Book> getBooksByCustomFilter(CustomFilter customFilter) {
        TypedQuery<Book> query = entityManager.createQuery(customFilter.getCustomQuery(), Book.class);
        return query.getResultList();
    }
}
