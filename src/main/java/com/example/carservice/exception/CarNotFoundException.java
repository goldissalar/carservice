package com.example.carservice.exception;

import lombok.Getter;

@Getter
public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
