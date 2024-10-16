package com.rafa.sevenTao.request;

import java.util.List;
import java.util.Map;

import com.rafa.sevenTao.model.Address;
import com.rafa.sevenTao.model.Room;

import lombok.Data;

@Data
public class CreateHotelRequest {
	private Map<String, String> pictures;
	private String chName;
	private String enName;
	private String introduction;
	private List<String> facilities;
	private Address address;
	private List<Room> rooms;
}
