package org.springhello.takehomeaycaurl.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import java.time.LocalDateTime;

@Entity //this class is an entity of a database
public class ShortUrl {

    //our object is a URL, the user wants to turn the original URl into a shorter one
    //if the user doesnt have a short URL for the original, the programm assigns one automatically


    @Id
    private String id;   //the primary key

    @Column(name = "Original_URL", nullable = false)     //original URL cant be null

    private String originalUrl;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
}
