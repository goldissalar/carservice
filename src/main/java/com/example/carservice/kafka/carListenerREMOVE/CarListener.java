package com.example.carservice.kafka.carListenerREMOVE;

import com.example.carservice.DTO.CarDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CarListener {

    @KafkaListener(topics = "car", containerFactory = "kafkaCarListenerContainerFactory")
    public void newCarListener(CarDTO carDTO) {
        log.info("Get request from car topic " + carDTO.toString());
    }
}