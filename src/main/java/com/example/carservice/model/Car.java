package com.example.carservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
@AllArgsConstructor
public class Car {
    @Id
    private int id;
    private String make;
    private String model;
    private String year;
    private String currency;
    private int dailyRate;
    @DBRef
    List<Booking> bookings;
}
