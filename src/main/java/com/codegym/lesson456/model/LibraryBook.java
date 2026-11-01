package com.codegym.lesson456.model;

import jakarta.persistence.*;

@Entity
@Table(name = "library_books")
public class LibraryBook {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String title;
    @Column(nullable = false) private String author;
    @Column(nullable = false) private int availableCopies;

    public LibraryBook() {}
    public LibraryBook(String title, String author, int availableCopies) {
        this.title = title; this.author = author; this.availableCopies = availableCopies;
    }
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getAvailableCopies() { return availableCopies; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }
}
