package com.rafa.sevenTao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long commentId;

	private String comment;

	private int rate;

	@ManyToOne
	@JsonIgnore
	private Hotel hotel;

	private String userName;
	private String userPhoto;

	private long userId;
}
