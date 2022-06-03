package com.example.marketplace_diploma.service;

import com.example.marketplace_diploma.dto.ResponseDto;
import com.example.marketplace_diploma.dto.user.SignInDto;
import com.example.marketplace_diploma.dto.SignInResponseDto;
import com.example.marketplace_diploma.dto.user.SignUpDto;
import com.example.marketplace_diploma.exceptions.AuthFailException;
import com.example.marketplace_diploma.exceptions.CustomException;
import com.example.marketplace_diploma.model.AuthToken;
import com.example.marketplace_diploma.model.User;
import com.example.marketplace_diploma.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthenticationService authenticationService;
    @Transactional
    public ResponseDto signup(SignUpDto signUpDto) {
        // check if user exists
        if (Objects.nonNull(userRepo.findByEmail(signUpDto.getEmail()))){
            throw new CustomException("Email is already used");
        }

        // hash the password
        String encryptedPassword;
        try {
            encryptedPassword = hashPassword(signUpDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new CustomException(e.getMessage());
        }

        User user = new User(encryptedPassword, signUpDto.getFirstName(), signUpDto.getLastName(), signUpDto.getEmail());

        userRepo.save(user);

        final AuthToken authToken = new AuthToken(user);
        authenticationService.saveConfirmationToken(authToken);
        ResponseDto responseDto = new ResponseDto("success", "dummy response");
        return responseDto;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes());
        byte[] digest = messageDigest.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SignInResponseDto signin(SignInDto signInDto) {

        User user = userRepo.findByEmail(signInDto.getEmail());
        if (Objects.isNull(user)){
            throw new AuthFailException("Couldnt find user with given email");
        }
        String encryptedPassword = null;
        try {
            encryptedPassword = hashPassword(signInDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        if (!user.getPassword().equals(encryptedPassword)){
            throw new AuthFailException("Wrong password");
        }

        AuthToken authToken = authenticationService.getToken(user);

        if (Objects.isNull(authToken)){
            throw new CustomException("Token is not present");
        }

        return new SignInResponseDto("success", authToken.getToken());
    }
}
