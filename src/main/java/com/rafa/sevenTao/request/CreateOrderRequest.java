package com.rafa.sevenTao.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rafa.sevenTao.model.Room;
import com.rafa.sevenTao.model.Users;
import com.rafa.sevenTao.model.WeekDay;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
public class CreateOrderRequest {
    private long roomId;
    private Date checkInDate;
    private Date checkOutDate;

    private int totalPrice;
}
