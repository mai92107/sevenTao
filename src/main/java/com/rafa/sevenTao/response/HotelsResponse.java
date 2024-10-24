package com.rafa.sevenTao.response;

import com.rafa.sevenTao.model.Room;
import lombok.Data;

import java.util.List;

@Data
public class HotelsResponse {
long hotelId;
    String hotelChName;
    String hotelEnName;
    List<Room> rooms;
    String description;
    String hotelPic;
    double rate;

}
