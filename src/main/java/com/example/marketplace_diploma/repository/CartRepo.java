package com.example.marketplace_diploma.repository;

import com.example.marketplace_diploma.model.Cart;
import com.example.marketplace_diploma.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepo extends JpaRepository<Cart, Integer> {


    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
}
