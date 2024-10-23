package com.rafa.sevenTao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
public class RoomPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomPriceId;

    @ManyToOne
    @JsonIgnore
    private Room room;

    private int price;

    private WeekDay weekDay;

}
