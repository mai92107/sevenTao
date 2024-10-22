package com.rafa.sevenTao.service;


import com.rafa.sevenTao.model.Order;
import com.rafa.sevenTao.model.Room;
import com.rafa.sevenTao.model.Users;

import java.util.Date;

public interface OrderService {

    public Room CreateOrder(Users user, Room room, Date start, Date end);

    public boolean isRoomAvailable(Room room, Date start, Date end);

    public int countPrice(Room room, Date checkInDate, Date checkOutDate);
}
