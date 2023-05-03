package com.example.carservice.controller;

import com.example.carservice.DTO.BookingDTO;
import com.example.carservice.DTO.CarDTO;
import com.example.carservice.kafka.bookingProducerREMOVE.BookingService;
import com.example.carservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
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
        //todo remove

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBookingId(3);
        String startTime = "2023-04-22" + " " + "23:00:00";
        String endTime = "2023-04-25" + " " + "16:16:16";
        Date pickupDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(startTime);
        Date returnDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(endTime);
        bookingDTO.setStartDate(pickupDate);
        bookingDTO.setEndDate(returnDate);
        bookingDTO.setReturned(false);
        bookingDTO.setCarId(1);
        this.bookingService.sendMessage(bookingDTO);
        return carService.findCars(pickupdate, pickuphour, returndate, returnhour, currency);
    }
}