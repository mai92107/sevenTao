package com.rafa.sevenTao.service;


import com.rafa.sevenTao.model.Order;
import com.rafa.sevenTao.model.Room;
import com.rafa.sevenTao.model.Users;

import java.util.Date;
import java.util.List;
import java.util.ListResourceBundle;

public interface OrderService {

    public Order createOrder(Users user, Room room, Date start, Date end);

    public boolean isRoomAvailable(Room room, Date start, Date end);

    public int countPrice(Room room, Date checkInDate, Date checkOutDate);

    public List<Order> getUserHistoryOrder(Users user);

    public List<Order> getUserFutureOrder(Users user);
}
