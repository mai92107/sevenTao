package com.rafa.sevenTao.request;

import java.util.List;

import com.rafa.sevenTao.model.RoomPrice;
import lombok.Data;

@Data
public class CreateRoomRequest {

	private String roomPic;

	private String roomName;

	private List<String> specialties;

	private List<RoomPrice> prices;

	private int capacity;

	private int roomSize;

}
