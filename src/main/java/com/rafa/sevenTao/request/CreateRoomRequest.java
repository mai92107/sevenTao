package com.rafa.sevenTao.request;

import java.util.List;

import lombok.Data;

@Data
public class CreateRoomRequest {

	private String roomPic;

	private String roomName;

	private List<String> specialties;

	private int roomPrice;

	private int capacity;

	private int roomSize;

}
