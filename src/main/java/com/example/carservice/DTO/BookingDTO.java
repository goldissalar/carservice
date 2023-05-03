package com.example.carservice.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class BookingDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3965748134914559751L;

    private int bookingId;

    private Date startDate;

    private Date endDate;

    private boolean returned;

    private int carId;

    @Override
    public String toString() {
        return "BookingDTO{" +
                "bookingId=" + bookingId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", returned=" + returned +
                '}';
    }
}
