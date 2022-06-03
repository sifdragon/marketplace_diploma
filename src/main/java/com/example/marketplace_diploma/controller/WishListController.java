package com.example.marketplace_diploma.controller;

import com.example.marketplace_diploma.common.ApiResponse;
import com.example.marketplace_diploma.dto.ProductDto;
import com.example.marketplace_diploma.model.AuthToken;
import com.example.marketplace_diploma.model.Product;
import com.example.marketplace_diploma.model.User;
import com.example.marketplace_diploma.model.WishList;
import com.example.marketplace_diploma.service.AuthenticationService;
import com.example.marketplace_diploma.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    WishListService wishListService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product, @RequestParam("token") String authToken){

        authenticationService.authenticateToken(authToken);

        User user = authenticationService.getUserFromToken(authToken);

        WishList wishList = new WishList(user, product);

        wishListService.createWishList(wishList);

        ApiResponse apiResponse = new ApiResponse(true, "Added to wishlist");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token){

        authenticationService.authenticateToken(token);

        User user = authenticationService.getUserFromToken(token);

        List<ProductDto> productDtos = wishListService.getWishListsForUser(user);

        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
}
