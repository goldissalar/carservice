package com.example.carservice.controller;

import com.example.DTO.CarDTO;
import com.example.carservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
public class CarController {
    @Autowired
    CarService carService;

    @GetMapping("/cars")
    public List<CarDTO> findCars(@RequestParam String pickupdate, @RequestParam String pickuphour, @RequestParam String returndate, @RequestParam String returnhour, @RequestParam String currency) throws ParseException {
        return carService.findCars(pickupdate, pickuphour, returndate, returnhour, currency);
    }
}