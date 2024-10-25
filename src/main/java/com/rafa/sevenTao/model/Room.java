package com.rafa.sevenTao.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long roomId;

	private String roomPic;

	private String roomName;

	@ElementCollection
	private List<String> specialties;

	@ElementCollection
	@OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
	private List<RoomPrice> roomPrices;

	private int roomSize;

	private int capacity;

	@ManyToOne
	@JsonIgnore
	private Hotel hotel;

	@JsonIgnore
	@OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
	List<Order> orders;

	private boolean available = true;

	@Override
	public String toString() {
		return "Room{" +
				"roomId=" + roomId +
				", roomPic='" + roomPic + '\'' +
				", roomName='" + roomName + '\'' +
				", specialties=" + specialties +
				", roomPrices=" + roomPrices +
				", roomSize=" + roomSize +
				", capacity=" + capacity +
				", hotelId=" + hotel.getHotelId() +
				", orders=" + orders +
				", available=" + available +
				'}';
	}
}
