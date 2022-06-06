package com.example.marketplace_diploma.controller;

import com.example.marketplace_diploma.common.ApiResponse;
import com.example.marketplace_diploma.dto.ProductDto;
import com.example.marketplace_diploma.dto.RatingDto;
import com.example.marketplace_diploma.model.Category;
import com.example.marketplace_diploma.repository.CategoryRepo;
import com.example.marketplace_diploma.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryRepo categoryRepo;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto) {
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "Category does not exist"), HttpStatus.NOT_FOUND);
        }
        productService.createProduct(productDto, optionalCategory.get());
        return new ResponseEntity<>(new ApiResponse(true, "Product has been created in category: " + optionalCategory.get().getCategoryName()), HttpStatus.CREATED);

    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> productDtos = productService.getAllProducts();
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @PostMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Integer productId, @RequestBody ProductDto productDto) throws Exception {
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "Category does not exist"), HttpStatus.NOT_FOUND);
        }
        productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(new ApiResponse(true, "Product has been updated: " + optionalCategory.get().getCategoryName()), HttpStatus.OK);
    }


    @PostMapping("/rate")
    public ResponseEntity<ApiResponse> updateRating(@RequestParam("token") String token, @RequestBody RatingDto ratingDto) {

        productService.updateRating(ratingDto, token);
        return new ResponseEntity<>(new ApiResponse(true, "Updated the rating"), HttpStatus.OK);
    }
}
