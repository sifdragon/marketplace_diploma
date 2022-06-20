package com.example.marketplace_diploma.service;

import com.example.marketplace_diploma.dto.ProductDto;
import com.example.marketplace_diploma.dto.RatingDto;
import com.example.marketplace_diploma.exceptions.ProductNotExistsException;
import com.example.marketplace_diploma.model.Category;
import com.example.marketplace_diploma.model.ItemUserRating;
import com.example.marketplace_diploma.model.Product;
import com.example.marketplace_diploma.model.User;
import com.example.marketplace_diploma.repository.CategoryRepo;
import com.example.marketplace_diploma.repository.ItemUserRatingRepo;
import com.example.marketplace_diploma.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class ProductService {
    @Autowired
    ProductRepo productRepo;

    @Autowired
    ItemUserRatingRepo itemUserRatingRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    CategoryRepo categoryRepo;

    public void createProduct(ProductDto productDto, Category category, String token){
        authenticationService.authenticateToken(token);

        User user = authenticationService.getUserFromToken(token);
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setCategory(category);
        product.setUser(user);
        productRepo.save(product);
    }

    public ProductDto transformProductToDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setId(product.getId());
        productDto.setUserName(product.getUser().getEmail());
        return productDto;
    }
    public List<ProductDto> getAllProducts() {
        List<Product> allProducts = productRepo.findAll();
        List<ProductDto> allProductDtos = new ArrayList<>();

        for (Product product : allProducts){
            allProductDtos.add(transformProductToDto(product));
        }
        return allProductDtos;
    }

    public void updateProduct(ProductDto productDto, Integer productId) throws Exception {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if(!optionalProduct.isPresent()){
            throw new Exception("Couldnt find product by id");
        }

        Product product = optionalProduct.get();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        productRepo.save(product);

    }

    public Product findById(Integer productId) throws ProductNotExistsException {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if (optionalProduct.isEmpty()){
            throw new ProductNotExistsException("Couldnt find product");
        }

        return optionalProduct.get();
    }

    public void updateRating(RatingDto ratingDto, String token) {
        authenticationService.authenticateToken(token);

        User user = authenticationService.getUserFromToken(token);
        Product product = productRepo.getById(ratingDto.getProductId());
        ItemUserRating itemUserRating = itemUserRatingRepo.findByUserAndProduct(user, product);
        if (itemUserRating == null) {
            itemUserRating = new ItemUserRating();
            itemUserRating.setUser(user);
            itemUserRating.setProduct(productRepo.findById(ratingDto.getProductId()).get());
        }

        itemUserRating.setRating(ratingDto.getRating());
        itemUserRatingRepo.save(itemUserRating);
    }

    public List<String> getProductsFromFriends(User user){
        List<Product> allProducts = productRepo.getAll();
        List<String> productNames = new ArrayList<>();
        Set<User> subscriptions = user.getSubscriptions();

        for (Product iterableProduct : allProducts){
            if (subscriptions.contains(iterableProduct.getUser())) {
                productNames.add(iterableProduct.getName());
            }
        }

    return productNames;
    }
}
