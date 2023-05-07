package com.example.carservice.controller;

import com.example.carservice.DTO.CarDTO;
import com.example.carservice.kafka.bookingProducerREMOVE.BookingService;
import com.example.carservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CarController {
    @Autowired
    CarService carService;

    BookingService bookingService;

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    public CarController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

//TODO when to send cars to bookingservice as producer? when starting the app? everytime findcars called?
    @GetMapping("/cars")
    public List<CarDTO> findCars(@RequestParam String pickupdate, @RequestParam String pickuphour, @RequestParam String returndate, @RequestParam String returnhour, @RequestParam String currency) throws ParseException {
        return carService.findCars(pickupdate, pickuphour, returndate, returnhour, currency);
    }
}