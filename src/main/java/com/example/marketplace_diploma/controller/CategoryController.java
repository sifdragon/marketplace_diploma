package com.example.marketplace_diploma.controller;

import com.example.marketplace_diploma.common.ApiResponse;
import com.example.marketplace_diploma.model.Category;
import com.example.marketplace_diploma.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<>(new ApiResponse(true, "Created a new category"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public List<Category> listCategory(){
        return categoryService.listCategory();
    }

    @PostMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") int categoryId, @RequestBody Category category){
        if (!categoryService.findById(categoryId)){
            return new ResponseEntity<>(new ApiResponse(true, "Wrong id"), HttpStatus.NOT_FOUND);
        }
        categoryService.editCategory(categoryId, category);
        return new ResponseEntity<>(new ApiResponse(true, "Updated a category"), HttpStatus.OK);

    }
}
