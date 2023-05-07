package com.example.carservice.kafka.bookingConsumer;

import com.example.carservice.DTO.BookingDTO;
import com.example.carservice.model.Booking;
import com.example.carservice.model.Car;
import com.example.carservice.repository.BookingRepository;
import com.example.carservice.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class BookingListener {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private BookingRepository bookingRepository;

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @KafkaListener(topics = "booking", containerFactory = "kafkaBookingListenerContainerFactory")
    public void newBookingListener(BookingDTO bookingDTO) throws ParseException {
        log.info("Get request from booking topic " + bookingDTO.toString());
        Optional<Car> car = carRepository.findById(bookingDTO.getCarId());
        if (car.isPresent()) {
            Date pickupDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(bookingDTO.getPickupDate() + " " + bookingDTO.getPickupHour());
            Date returnDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(bookingDTO.getReturnDate() + " " + bookingDTO.getReturnHour());
            Booking booking = new Booking(bookingDTO.getBookingId(), pickupDate, returnDate, false);
            bookingRepository.save(booking);
            car.get().getBookings().add(booking);
            carRepository.save(car.get());
        }
    }
}