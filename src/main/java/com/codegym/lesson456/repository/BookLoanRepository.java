package com.codegym.lesson456.repository;

import com.codegym.lesson456.model.BookLoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class BookLoanRepository {
    @PersistenceContext private EntityManager em;
    public BookLoan findActiveByCode(String code) {
        return em.createQuery("select l from BookLoan l join fetch l.book where l.code = :code and l.returnedAt is null", BookLoan.class)
                .setParameter("code", code).getResultStream().findFirst().orElse(null);
    }
    public BookLoan save(BookLoan loan) { em.persist(loan); return loan; }
}
