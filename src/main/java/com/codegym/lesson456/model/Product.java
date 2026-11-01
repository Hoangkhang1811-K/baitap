package com.codegym.lesson456.model;
import jakarta.persistence.*;
@Entity @Table(name="products")
public class Product {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String name; private String description; private String image;
    @Column(nullable=false) private int ratingSum=0;
    @Column(nullable=false) private int ratingCount=0;
    public Product() {}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getDescription(){return description;} public void setDescription(String description){this.description=description;}
    public String getImage(){return image;} public void setImage(String image){this.image=image;}
    public int getRatingSum(){return ratingSum;} public void setRatingSum(int ratingSum){this.ratingSum=ratingSum;}
    public int getRatingCount(){return ratingCount;} public void setRatingCount(int ratingCount){this.ratingCount=ratingCount;}
    public double getAverageRating(){return ratingCount == 0 ? 0.0 : (double) ratingSum / ratingCount;}
}
