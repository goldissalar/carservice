package com.example.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CarDTO {

    private int carId;

    private String make;

    private String model;

    private String year;

    private String currency;

    private BigDecimal dailyRate;
}
