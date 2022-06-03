package com.example.marketplace_diploma.exceptions;

public class ProductNotExistsException extends IllegalArgumentException {

    public ProductNotExistsException(String message){
        super(message);
    }
}
