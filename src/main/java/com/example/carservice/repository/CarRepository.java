package com.example.carservice.repository;

import com.example.carservice.model.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarRepository extends MongoRepository<Car, Integer> {
}