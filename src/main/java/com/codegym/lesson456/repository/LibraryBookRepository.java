package com.codegym.lesson456.repository;

import com.codegym.lesson456.model.LibraryBook;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class LibraryBookRepository {
    @PersistenceContext private EntityManager em;
    public List<LibraryBook> findAll() { return em.createQuery("select b from LibraryBook b order by b.title", LibraryBook.class).getResultList(); }
    public LibraryBook findById(Long id) { return em.find(LibraryBook.class, id); }
    public long count() { return em.createQuery("select count(b) from LibraryBook b", Long.class).getSingleResult(); }
    public LibraryBook save(LibraryBook book) { return em.merge(book); }
}
