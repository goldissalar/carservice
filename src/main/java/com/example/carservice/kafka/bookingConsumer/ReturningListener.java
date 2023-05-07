package com.example.carservice.kafka.bookingConsumer;

import com.example.carservice.DTO.BookingDTO;
import com.example.carservice.DTO.ReturningDTO;
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
public class ReturningListener {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private BookingRepository bookingRepository;

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @KafkaListener(topics = "returning", containerFactory = "kafkaReturningListenerContainerFactory")
    public void newBookingListener(ReturningDTO returningDTO) throws ParseException {
        log.info("Get request from booking topic " + returningDTO.toString());
        Optional<Car> car = carRepository.findById(returningDTO.getCarId());
        if (car.isPresent()) {

            Optional<Booking> booking = bookingRepository.findById(returningDTO.getBookingId());

            if (booking.isPresent()) {
                booking.get().setReturned(true);
                car.get().getBookings().add(booking.get());
                bookingRepository.save(booking.get());
                carRepository.save(car.get());
            }
        }
    }
}