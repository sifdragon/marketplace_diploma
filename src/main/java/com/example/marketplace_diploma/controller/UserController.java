package com.example.marketplace_diploma.controller;

import com.example.marketplace_diploma.common.ApiResponse;
import com.example.marketplace_diploma.dto.ProductDto;
import com.example.marketplace_diploma.dto.ResponseDto;
import com.example.marketplace_diploma.dto.user.SignInDto;
import com.example.marketplace_diploma.dto.SignInResponseDto;
import com.example.marketplace_diploma.dto.user.SignUpDto;
import com.example.marketplace_diploma.model.User;
import com.example.marketplace_diploma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("signup")
    public ResponseDto signup(@RequestBody SignUpDto signUpDto){
        return userService.signup(signUpDto);
    }

    @PostMapping("/signin")
    public SignInResponseDto signin(@RequestBody SignInDto signInDto){
        return userService.signin(signInDto);
    }

    @PostMapping("/addFriend")
    public ResponseEntity<ApiResponse> addFriend(@RequestParam("token") String token,
                                                 @RequestBody ProductDto productDto){
        return userService.addFriend(token, productDto);
    }
}
