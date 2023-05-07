package com.example.carservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Getter
@Setter
@AllArgsConstructor
public class Booking {
    @Id
    private String id;
    private Date startDate;
    private Date endDate;
    private boolean returned;

    public Booking() {

    }
}
