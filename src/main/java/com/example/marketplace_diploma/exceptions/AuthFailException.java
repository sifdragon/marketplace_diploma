package com.example.marketplace_diploma.exceptions;


public class AuthFailException extends IllegalArgumentException{
    public AuthFailException(String message){
        super(message);
    }
}
