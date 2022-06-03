package com.example.marketplace_diploma.repository;

import com.example.marketplace_diploma.model.AuthToken;
import com.example.marketplace_diploma.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<AuthToken, Integer> {

    AuthToken findByUser(User user);

    AuthToken findByToken(String token);
}
