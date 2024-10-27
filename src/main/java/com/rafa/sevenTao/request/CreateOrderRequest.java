package com.rafa.sevenTao.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rafa.sevenTao.model.Room;
import com.rafa.sevenTao.model.Users;
import com.rafa.sevenTao.model.WeekDay;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class CreateOrderRequest {
    private long roomId;

    @Temporal(TemporalType.DATE)
    private Date checkInDate;

    @Temporal(TemporalType.DATE)
    private Date checkOutDate;

    private int totalPrice;
}
