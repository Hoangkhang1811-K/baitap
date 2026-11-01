package com.codegym.lesson456.model;
import org.springframework.web.multipart.MultipartFile;
public class ProductForm {
    private Long id; private String name; private String description; private MultipartFile image;
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getDescription(){return description;} public void setDescription(String description){this.description=description;}
    public MultipartFile getImage(){return image;} public void setImage(MultipartFile image){this.image=image;}
}
