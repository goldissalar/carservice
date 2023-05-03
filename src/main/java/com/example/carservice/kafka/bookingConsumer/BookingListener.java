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

import java.util.Optional;

@Slf4j
@Service
public class BookingListener {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @KafkaListener(topics = "booking", containerFactory = "kafkaBookingListenerContainerFactory")
    public void newBookingListener(BookingDTO bookingDTO) {
        log.info("Get request from booking topic " + bookingDTO.toString());
        Optional<Car> car = carRepository.findById(bookingDTO.getCarId());
        if (car.isPresent()) {
            Booking booking = new Booking(bookingDTO.getBookingId(), bookingDTO.getStartDate(), bookingDTO.getEndDate(), bookingDTO.isReturned());
            bookingRepository.save(booking);
            car.get().getBookings().add(booking);
            carRepository.save(car.get());
        }
    }
}