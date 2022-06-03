package com.example.marketplace_diploma.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wishlist")
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "createdDate")
    private Date createdDate;

    public WishList(Integer id, Product product, User user, Date createdDate) {
        this.id = id;
        this.product = product;
        this.user = user;
        this.createdDate = createdDate;
    }

    public WishList() {
    }

    public WishList(User user, Product product) {
        this.user = user;
        this.product = product;
        this.createdDate = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Date getcreatedDate() {
        return createdDate;
    }

    public void setcreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
