package com.example.carservice.config;


import com.example.carservice.model.Booking;
import com.example.carservice.model.Car;
import com.example.carservice.repository.BookingRepository;
import com.example.carservice.repository.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.awt.print.Book;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EnableMongoRepositories(basePackageClasses = CarRepository.class)
@Configuration
public class MongoConfig {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    //TODO delete if not necessary
    @Bean
    CommandLineRunner commandLineRunner(CarRepository carRepository, BookingRepository bookingRepository) {
        return strings -> {
            carRepository.save(new Car(1, "Audi", "A1", "2013", "USD", 80d, createBookings(bookingRepository, "2023-04-16" + " " + "22:00:00", "2023-04-17" + " " + "14:16:16", "1")));
            carRepository.save(new Car(2, "Audi", "A2", "2013", "USD", 90d, createBookings(bookingRepository, "2023-04-18" + " " + "20:00:00", "2023-04-19" + " " + "16:16:16", "2")));
        };
    }

    private List<Booking> createBookings(BookingRepository bookingRepository, String startTime, String endTime, String bookingId) throws ParseException {
        Date pickupDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(startTime);
        Date returnDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(endTime);
        Booking booking = new Booking(bookingId, pickupDate, returnDate, false);
        Booking savedBooking = bookingRepository.save(booking);
        List<Booking> bookingsForCar = new ArrayList<>();
        bookingsForCar.add(savedBooking);
        return bookingsForCar;
    }
}