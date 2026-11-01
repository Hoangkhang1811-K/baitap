package com.codegym.lesson456.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "apod_comments")
public class ApodComment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "apod_date", nullable = false)
    private ApodImage image;
    @Column(nullable = false, length = 80)
    private String author;
    @Column(nullable = false, length = 1000)
    private String content;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public ApodComment() {}
    public ApodComment(ApodImage image, String author, String content) {
        this.image = image;
        this.author = author;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
    public String getAuthor() { return author; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
