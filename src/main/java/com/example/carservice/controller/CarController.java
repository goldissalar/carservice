package com.example.carservice.controller;

import com.example.carservice.DTO.CarDTO;
import com.example.carservice.exception.CarNotFoundException;
import com.example.carservice.kafka.bookingProducerREMOVE.BookingService;
import com.example.carservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/cars")
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

    @GetMapping()
    public List<CarDTO> findCars(@RequestParam String pickupdate, @RequestParam String pickuphour, @RequestParam String returndate, @RequestParam String returnhour, @RequestParam String currency) throws ParseException {
        return carService.findCars(pickupdate, pickuphour, returndate, returnhour, currency);
    }

    @GetMapping("/{carId}")
    public ResponseEntity findCarById(@PathVariable("carId") int carId) {
        try {
            return ResponseEntity.ok(carService.findCarById(carId));
        } catch (CarNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}