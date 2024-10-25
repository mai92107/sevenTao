package com.rafa.sevenTao.controller;

import java.util.List;
import java.util.Map;

import com.rafa.sevenTao.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rafa.sevenTao.model.Hotel;
import com.rafa.sevenTao.model.Users;
import com.rafa.sevenTao.request.CreateHotelRequest;
import com.rafa.sevenTao.service.HotelService;
import com.rafa.sevenTao.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/boss")
public class BossHotelController {

	@Autowired
	HotelService hotelService;
	@Autowired
	UserService userService;

	@PostMapping("/hotel")
	public ResponseEntity<Hotel> createHotel(@RequestHeader("Authorization") String jwt, @RequestBody CreateHotelRequest request) {
		Users user = userService.findUserByJwt(jwt);
		Hotel hotel = hotelService.createHotel(user, request);
		if (hotel != null)
			return new ResponseEntity<>(hotel, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	};

	@DeleteMapping("/hotel/{hotelId}")
	public ResponseEntity<?> deleteHotelByHotelId(@RequestHeader("Authorization") String jwt,@PathVariable int hotelId) {
		boolean success = hotelService.deleteHotelByHotelId(hotelId);
		if (success)
			return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	};

	@PutMapping("/hotel/{hotelId}")
	public ResponseEntity<Hotel> updateHotelData(@RequestHeader("Authorization") String jwt,@PathVariable int hotelId, @RequestBody CreateHotelRequest request) {
		Users user = userService.findUserByJwt(jwt);
		Hotel hotel = hotelService.updateHotelData(hotelId, request);
		if (user != null&&hotel != null)
			return new ResponseEntity<>(hotel, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	};

	@GetMapping("/hotels")
	public ResponseEntity<List<Hotel>> findHotelsByBoss(@RequestHeader("Authorization") String jwt) {
		System.out.println("this is the jwt : "+jwt);
		Users user = userService.findUserByJwt(jwt);
		List<Hotel> myHotels = hotelService.findHotelsByBoss(user);
		if (myHotels != null)
			return new ResponseEntity<>(myHotels, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	};

	@PutMapping("/{userId}")
	public ResponseEntity<Users> setUserToHotelerFromUserId(@RequestHeader("Authorization") String jwt) {
		Users user = userService.findUserByJwt(jwt);
		if (user != null)
			return new ResponseEntity<Users>(user, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/hotel/orders")
	public ResponseEntity<Map<String,List<List<Order>>>> getRoomsOrders(@RequestHeader("Authorization") String jwt){
		Users user = userService.findUserByJwt(jwt);
		Map<String,List<List<Order>>> hotelOrders = hotelService.findOrdersFromBoss(user);
		if (user != null)
			return new ResponseEntity<>(hotelOrders, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
