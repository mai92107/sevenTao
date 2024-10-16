package com.rafa.sevenTao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafa.sevenTao.model.Hotel;
import com.rafa.sevenTao.model.User;
import com.rafa.sevenTao.request.CreateHotelRequest;
import com.rafa.sevenTao.service.HotelService;
import com.rafa.sevenTao.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminHotelController {
	@Autowired
	HotelService hotelService;
	@Autowired
	UserService userService;

	@PostMapping("/hotel/{userId}")
	public ResponseEntity<Hotel> createHotel(@PathVariable int userId, @RequestBody CreateHotelRequest request) {
		User user = userService.findUserByUserId(userId);
		Hotel hotel = hotelService.createHotel(user, request);
		if (hotel != null)
			return new ResponseEntity<>(hotel, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	};

	@DeleteMapping("/hotel/{hotelId}")
	public ResponseEntity<?> deleteHotelByHotelId(@PathVariable int hotelId) {
		boolean success = hotelService.deleteHotelByHotelId(hotelId);
		if (success)
			return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	};

	@PutMapping("/hotel/{hotelId}")
	public ResponseEntity<Hotel> updateHotelData(@PathVariable int hotelId, @RequestBody CreateHotelRequest request) {
		Hotel hotel = hotelService.updateHotelData(hotelId, request);
		if (hotel != null)
			return new ResponseEntity<>(hotel, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	};

	@GetMapping("/{userId}/hotels")
	public ResponseEntity<List<Hotel>> findHotelsByUserId(@PathVariable int userId) {
		List<Hotel> myHotels = hotelService.findHotelsByUserId(userId);
		if (myHotels != null)
			return new ResponseEntity<>(myHotels, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	};

	@PutMapping("/{userId}")
	public ResponseEntity<User> setUserToHotelerFromUserId(@PathVariable int userId) {
		User user = userService.setUserToHotelerFromUserId(userId);
		if (user != null)
			return new ResponseEntity<User>(user, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
