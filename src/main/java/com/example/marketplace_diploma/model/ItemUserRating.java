package com.example.marketplace_diploma.model;

import javax.persistence.*;

@Entity
@Table(name = "item_user_rating")
public class ItemUserRating {

    @EmbeddedId
    ItemUserRatingKey id = new ItemUserRatingKey();

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    int rating;

    public ItemUserRating() {
    }

    public ItemUserRatingKey getId() {
        return id;
    }

    public void setId(ItemUserRatingKey id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
