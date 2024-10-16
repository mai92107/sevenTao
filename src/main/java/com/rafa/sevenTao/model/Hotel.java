package com.rafa.sevenTao.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hotelId;

	@ManyToOne
	@JoinColumn(name = "boss_id", referencedColumnName = "userId")
	@JsonIgnore
	private User boss;

	@ElementCollection
	@CollectionTable(name = "hotel_pictures", joinColumns = @JoinColumn(name = "hotel_id"))
	@Column(name = "picture_url")
	private List<String> pictures;
	private String chName;
	private String enName;
	private String introduction;

	@ElementCollection
	private List<String> facilities;

	@OneToOne
	private Address address;

	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
	private List<Room> rooms;

	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
	private List<Comment> comments;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date buildDate;

	@ManyToMany
	@JsonIgnore
	private List<User> likedByUsers;

	private double score = 3;

}
