package com.codegym.lesson456.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_loans", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class BookLoan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "book_id") private LibraryBook book;
    @Column(nullable = false, unique = true, length = 5) private String code;
    @Column(nullable = false) private LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;
    public BookLoan() {}
    public BookLoan(LibraryBook book, String code) { this.book = book; this.code = code; this.borrowedAt = LocalDateTime.now(); }
    public Long getId() { return id; }
    public LibraryBook getBook() { return book; }
    public String getCode() { return code; }
    public LocalDateTime getBorrowedAt() { return borrowedAt; }
    public LocalDateTime getReturnedAt() { return returnedAt; }
    public void setReturnedAt(LocalDateTime returnedAt) { this.returnedAt = returnedAt; }
}
