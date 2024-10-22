package com.rafa.sevenTao.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hotelId;

	@ManyToOne
	@JoinColumn(name = "boss_id", referencedColumnName = "userId")
	@JsonIgnore
	private Users boss;

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

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date buildDate;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date fixedDate;

	@ManyToMany
	@JsonIgnore
	private List<Users> likedByUsers;

	private double score;

}
