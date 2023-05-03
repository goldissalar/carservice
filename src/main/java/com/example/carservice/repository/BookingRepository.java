package com.example.carservice.repository;

import com.example.carservice.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<Booking, Integer> {
}