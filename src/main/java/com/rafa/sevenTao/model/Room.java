package com.rafa.sevenTao.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

	private int roomPrice;

	private int roomSize;

	private int capacity;

	@ManyToOne
	@JsonIgnore
	private Hotel hotel;

	private boolean available = true;
}
