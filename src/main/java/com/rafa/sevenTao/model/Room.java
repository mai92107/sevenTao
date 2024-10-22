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

	@OneToMany(mappedBy = "room")
	private List<RoomPrice> roomPrices;

	private int roomSize;

	private int capacity;

	@ManyToOne
	@JsonIgnore
	private Hotel hotel;

	@OneToMany(mappedBy = "room")
	List<Order> orders;

	private boolean available = true;


}
