package com.example.marketplace_diploma.service;

import com.example.marketplace_diploma.dto.ProductDto;
import com.example.marketplace_diploma.model.User;
import com.example.marketplace_diploma.model.WishList;
import com.example.marketplace_diploma.repository.WishListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {

    @Autowired
    WishListRepo wishListRepo;

    @Autowired
    ProductService productService;

    public void createWishList(WishList wishList) {
        wishListRepo.save(wishList);
    }

    public List<ProductDto> getWishListsForUser(User user) {
        final List<WishList> wishLists = wishListRepo.findAllByUserOrderByCreatedDateDesc(user);
        List<ProductDto> productDtos = new ArrayList<>();
        for (WishList wishList : wishLists){
            productDtos.add(productService.transformProductToDto(wishList.getProduct()));
        }

        return productDtos;
    }
}
