package com.example.marketplace_diploma.controller;

import com.example.marketplace_diploma.common.ApiResponse;
import com.example.marketplace_diploma.dto.cart.AddToCartDto;
import com.example.marketplace_diploma.dto.cart.CartDto;
import com.example.marketplace_diploma.model.User;
import com.example.marketplace_diploma.service.AuthenticationService;
import com.example.marketplace_diploma.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
                                                 @RequestParam("token") String token) {

        authenticationService.authenticateToken(token);

        User user = authenticationService.getUserFromToken(token);

        cartService.addToCart(addToCartDto, user);

        return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token){

        authenticationService.authenticateToken(token);

        User user = authenticationService.getUserFromToken(token);

        CartDto cartDto = cartService.listCartItems(user);

        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@RequestParam("token") String token,
                                           @PathVariable("cartItemId") Integer cartId){

        authenticationService.authenticateToken(token);

        User user = authenticationService.getUserFromToken(token);

        cartService.deleteCartItem(cartId, user);

        return new ResponseEntity<>(new ApiResponse(true, "Deleted from cart"), HttpStatus.OK);

    }
}
