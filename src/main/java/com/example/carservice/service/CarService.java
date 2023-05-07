package com.example.carservice.service;

import com.example.carservice.DTO.CarDTO;
import com.example.carservice.kafka.carProducer.CarProducer;
import com.example.carservice.model.Booking;
import com.example.carservice.model.Car;
import com.example.carservice.repository.CarRepository;
import currencyConverter.ConverterGrpc;
import currencyConverter.ConverterOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CarService {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private CarProducer carProducer;

    @Autowired
    public CarService(CarProducer carProducer) {
        this.carProducer = carProducer;
    }

    @Autowired
    CarRepository carRepository;

    @Transactional(readOnly = true)
    public List<CarDTO> findCars(String pickupdate, String pickuphour, String returndate, String returnhour, String currency) throws ParseException {
        List<CarDTO> carsDTOS = new ArrayList<>();
        List<Car> cars = carRepository.findAll();
        Date pickupDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(pickupdate + " " + pickuphour);
        Date returnDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(returndate + " " + returnhour);
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars) {
            List<Booking> bookings = car.getBookings();
            boolean carAvailable = bookings.stream().allMatch(b -> isNotInRange(b.getStartDate(), pickupDate, returnDate) && isNotInRange(b.getEndDate(), pickupDate, returnDate) || (b.isReturned()));
            if (carAvailable) {
                availableCars.add(car);
            }
        }
        availableCars.forEach(car -> carsDTOS.add(convertCarToCarDTO(car, currency)));
        return carsDTOS;
    }

    @Transactional(readOnly = true)
    public List<CarDTO> getAllCars() {
        List<CarDTO> carsDTOS = new ArrayList<>();
        List<Car> cars = carRepository.findAll();
        cars.forEach(car -> carsDTOS.add(convertCarToCarDTO(car, car.getCurrency())));
        return carsDTOS;
    }

    public void sendMessage(CarDTO message) {
        log.info("[CarService] send car to topic");
        carProducer.send(message);
    }

    public boolean isNotInRange(Date date, Date startdate, Date enddate) {
        return date.after(enddate) || date.before(startdate);
    }

    private Double convertCurrency(Double amount, String from, String to) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("grpccurrencyconvertercode.azurewebsites.net", 443)
                .useTransportSecurity()
                .build();

        // Create a gRPC stub for the Converter service
        ConverterGrpc.ConverterBlockingStub stub = ConverterGrpc.newBlockingStub(channel);

        // Create a request message
        ConverterOuterClass.ConvertRequest request = ConverterOuterClass.ConvertRequest.newBuilder()
                .setAmount(amount)
                .setFrom(from)
                .setTo(to)
                .build();

        // Call the gRPC service and get the response
        ConverterOuterClass.ConvertReply response = stub.converter(request);

        // Shutdown the channel
        channel.shutdown();
        return response.getResult();
        //return BigDecimal.valueOf(50);
    }

    private CarDTO convertCarToCarDTO(Car car, String currency) {
        CarDTO carsDTO = new CarDTO();
        carsDTO.setCarId(car.getId());
        carsDTO.setMake(car.getMake());
        carsDTO.setModel(car.getModel());
        carsDTO.setYear(car.getYear());
        carsDTO.setCurrency(currency);
        carsDTO.setDailyRate(convertCurrency(car.getDailyRate(), car.getCurrency(), currency));
        return carsDTO;
    }
}
