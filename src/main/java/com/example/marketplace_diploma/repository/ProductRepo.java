package com.example.marketplace_diploma.repository;

import com.example.marketplace_diploma.model.Category;
import com.example.marketplace_diploma.model.Product;
import com.example.marketplace_diploma.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Query("SELECT e FROM Product e")
    List<Product> getAll();

    Product getProductByName(String name);
}
