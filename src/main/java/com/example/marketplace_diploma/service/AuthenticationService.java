package com.example.marketplace_diploma.service;

import com.example.marketplace_diploma.exceptions.AuthFailException;
import com.example.marketplace_diploma.model.AuthToken;
import com.example.marketplace_diploma.model.User;
import com.example.marketplace_diploma.repository.TokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

    @Autowired
    TokenRepo tokenRepo;


    public void saveConfirmationToken(AuthToken authToken) {

        tokenRepo.save(authToken);
    }

    public AuthToken getToken(User user) {
        return tokenRepo.findByUser(user);
    }

    public User getUserFromToken(String token){
        AuthToken tokenFromRepo = tokenRepo.findByToken(token);
        if (Objects.isNull(tokenFromRepo)){
            return null;
        }

        return tokenFromRepo.getUser();
    }

    public void authenticateToken(String token){
        if(Objects.isNull(token)){
            throw new AuthFailException("Token is null");
        }

        if(Objects.isNull(getUserFromToken(token))){
            throw new AuthFailException("Token not valid");
        }

    }
}
