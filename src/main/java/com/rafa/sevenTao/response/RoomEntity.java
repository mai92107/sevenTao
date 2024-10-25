package com.rafa.sevenTao.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RoomEntity {

    private long roomId;

    private List<String> roomPic;

    private String roomName;

    private List<String> specialties;

    private int price;

    private int roomSize;

    private int capacity;

    private boolean available;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date start;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date end;
}
