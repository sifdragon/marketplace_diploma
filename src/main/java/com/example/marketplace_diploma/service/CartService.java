package com.example.marketplace_diploma.service;

import com.example.marketplace_diploma.dto.cart.AddToCartDto;
import com.example.marketplace_diploma.dto.cart.CartDto;
import com.example.marketplace_diploma.dto.cart.CartItemDto;
import com.example.marketplace_diploma.exceptions.CustomException;
import com.example.marketplace_diploma.model.Cart;
import com.example.marketplace_diploma.model.Product;
import com.example.marketplace_diploma.model.User;
import com.example.marketplace_diploma.repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CartRepo cartRepo;
    @Autowired
    ProductService productService;
    public void addToCart(AddToCartDto addToCartDto, User user) {

        Product product = productService.findById(addToCartDto.getProductId());

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());

        cartRepo.save(cart);
    }

    public CartDto listCartItems(User user) {

       List<Cart> cartList = cartRepo.findAllByUserOrderByCreatedDateDesc(user);

       List<CartItemDto> cartDtoList = new ArrayList<>();
       double totalCost = 0;
       for (Cart cart : cartList) {
           CartItemDto cartItemDto = new CartItemDto(cart);
           totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
           cartDtoList.add(cartItemDto);
       }

       CartDto cartDto = new CartDto();
       cartDto.setCartItems(cartDtoList);
       cartDto.setTotalCost(totalCost);

       return cartDto;
    }


    public void deleteCartItem(Integer cartId, User user) {

        Optional<Cart> optionalCart = cartRepo.findById(cartId);
        if (optionalCart.isEmpty()){
            throw new CustomException("Cart item id invalid");
        }

        Cart cart = optionalCart.get();

        if (cart.getUser() != user) {
            throw new CustomException("Cart item does not belong to user " + user.getFirstName());
        }

        cartRepo.delete(cart);
    }
}
