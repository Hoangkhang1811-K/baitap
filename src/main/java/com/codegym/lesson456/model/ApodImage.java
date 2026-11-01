package com.codegym.lesson456.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "apod_images")
public class ApodImage {
    @Id
    @Column(length = 10)
    private String date;
    @Column(nullable = false, length = 500)
    private String title;
    @Column(nullable = false, length = 2000)
    private String imageUrl;
    @Column(nullable = false)
    private int ratingSum;
    @Column(nullable = false)
    private int ratingCount;

    public ApodImage() {}
    public ApodImage(String date, String title, String imageUrl) {
        this.date = date;
        this.title = title;
        this.imageUrl = imageUrl;
    }
    public String getDate() { return date; }
    public String getTitle() { return title; }
    public String getImageUrl() { return imageUrl; }
    public int getRatingSum() { return ratingSum; }
    public int getRatingCount() { return ratingCount; }
    public double getAverageRating() { return ratingCount == 0 ? 0.0 : (double) ratingSum / ratingCount; }
    public void setTitle(String title) { this.title = title; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void addRating(int rating) { ratingSum += rating; ratingCount++; }
}
