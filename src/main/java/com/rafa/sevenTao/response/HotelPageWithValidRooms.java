package com.rafa.sevenTao.response;

import com.rafa.sevenTao.model.Hotel;
import lombok.Data;

import java.util.List;

@Data
public class HotelPageWithValidRooms {
    private Hotel hotel;
    private List<RoomEntity> rooms;
}
