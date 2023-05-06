package com.example.carservice.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
public class ReturningDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7238274281582747928L;

    private String bookingId;
    private int carId;

}
