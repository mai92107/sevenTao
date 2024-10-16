package com.rafa.sevenTao.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	private String account;
	private String password;
	private String lastName;
	private String firstName;
	private String email;
	private String nickName;
	private String sex;
	private String phoneNum;
	private String address;

	private boolean isHoteler = false;

	@OneToMany(mappedBy = "boss", cascade = CascadeType.ALL)
	private List<Hotel> myHotels;

	@ManyToMany(mappedBy = "likedByUsers", cascade = CascadeType.ALL)
	private List<Hotel> favoriteHotels;

}