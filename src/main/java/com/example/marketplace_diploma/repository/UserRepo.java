package com.example.marketplace_diploma.repository;

import com.example.marketplace_diploma.model.ItemUserRating;
import com.example.marketplace_diploma.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    @Query("SELECT e FROM User e")
    List<User> getAll();
}
