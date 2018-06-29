package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Size(min = 1)
    private String name;

    @NotEmpty
    @Size(min = 1)
    private String description;

    private String publicationDate;

    private String imageAddress;

    private String imageCloudinary;

    private int likeCounter = 0;

    private int dislikeCounter = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public String getImageCloudinary() {
        return imageCloudinary;
    }

    public void setImageCloudinary(String imageCloudinary) {
        this.imageCloudinary = imageCloudinary;
    }

    public int getLikeCounter() {
        return likeCounter;
    }

    public void setLikeCounter(int likeCounter) {
        this.likeCounter = likeCounter;
    }

    public int getDislikeCounter() {
        return dislikeCounter;
    }

    public void setDislikeCounter(int dislikeCounter) {
        this.dislikeCounter = dislikeCounter;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                ", imageAddress='" + imageAddress + '\'' +
                '}';
    }
}
