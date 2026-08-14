package com.realestate.property_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "properties")
public class Property {
    @Id
    private String id;
    
    private String title;
    private String description;
    private double price;
    private String location;
    private String imageUrl;
    private String ownerId; // මෙම ප්‍රොපර්ටි එක දැමූ යූසර්ගේ ID එක

    public Property() {
    }

    public Property(String id, String title, String description, double price, String location, String imageUrl, String ownerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.location = location;
        this.imageUrl = imageUrl;
        this.ownerId = ownerId;
    }

    // Getters සහ Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }
}