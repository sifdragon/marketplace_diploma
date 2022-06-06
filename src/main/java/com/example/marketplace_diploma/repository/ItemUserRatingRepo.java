package com.example.marketplace_diploma.repository;

import com.example.marketplace_diploma.model.ItemUserRating;
import com.example.marketplace_diploma.model.ItemUserRatingKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemUserRatingRepo extends JpaRepository<ItemUserRating, ItemUserRatingKey> {

    ItemUserRating findByIdProductIdAndIdUserId(Integer userId, Integer productId);
}
