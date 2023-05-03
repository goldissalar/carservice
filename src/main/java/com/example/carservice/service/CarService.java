package com.example.carservice.service;

import com.example.DTO.CarDTO;
import com.example.carservice.model.Booking;
import com.example.carservice.model.Car;
import com.example.carservice.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CarService {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
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
            //TODO get bookings from bookingService and store in database, THEN access database to get them
            List<Booking> bookings = car.getBookings();
            boolean carAvailable = bookings.stream().allMatch(b -> isNotInRange(b.getStartDate(), pickupDate, returnDate) && isNotInRange(b.getEndDate(), pickupDate, returnDate) || (b.isReturned()));
            if (carAvailable) {
                availableCars.add(car);
            }
        }
        availableCars.forEach(car -> carsDTOS.add(convertCarToCarDTO(car, currency)));
        return carsDTOS;
    }

    private boolean isNotInRange(Date date, Date startDate, Date endDate) {
        return date.before(startDate) || date.after(endDate);
    }

    //todo currency service call
    /*private BigDecimal convertCurrency(BigDecimal amount, String from, String to) {
        mypackage.WebService1Soap service = new WebService1().getPort(WebService1Soap.class);
        return service.convertCurrency(amount, from, to);
        //return BigDecimal.valueOf(50);
    }*/

    private CarDTO convertCarToCarDTO(Car car, String currency) {
        CarDTO carsDTO = new CarDTO();
        carsDTO.setCarId(car.getId());
        carsDTO.setMake(car.getMake());
        carsDTO.setModel(car.getModel());
        carsDTO.setYear(car.getYear());
        carsDTO.setCurrency(currency);
        //todo currency??
        carsDTO.setDailyRate(BigDecimal.valueOf(car.getDailyRate()));
        return carsDTO;
    }

}
