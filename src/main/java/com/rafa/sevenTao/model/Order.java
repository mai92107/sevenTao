package com.rafa.sevenTao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date checkInDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date checkOutDate;

    @ManyToOne
    @JsonIgnore
    private Users user;

    @ManyToOne
    private Room room;

    @Column(nullable = false)
    private int totalPrice;

    public boolean checkValidOrder() {
        return !this.getCheckOutDate().before(new Date());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", totalPrice=" + totalPrice +
                ", roomId=" + room.getRoomId() +
                ", userId=" + user.getUserId() +
                '}';
    }
}
