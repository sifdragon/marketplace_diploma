package com.example.marketplace_diploma.repository;

import com.example.marketplace_diploma.model.ItemUserRating;
import com.example.marketplace_diploma.model.ItemUserRatingKey;
import com.example.marketplace_diploma.model.Product;
import com.example.marketplace_diploma.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemUserRatingRepo extends JpaRepository<ItemUserRating, ItemUserRatingKey> {

    ItemUserRating findByUserAndProduct(User user, Product product);

    @Query("SELECT e FROM ItemUserRating e")
    List<ItemUserRating> getAll();
}
