package com.rafa.sevenTao.response;

import com.rafa.sevenTao.model.Room;
import lombok.Data;

import java.util.List;

@Data
public class HotelsResponse {
long hotelId;
    String chName;
    String enName;
    List<Room> rooms;
    String introduction;
    String picture;
    double score;

}
