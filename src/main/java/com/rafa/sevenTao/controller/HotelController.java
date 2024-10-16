package com.rafa.sevenTao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rafa.sevenTao.model.Hotel;
import com.rafa.sevenTao.service.HotelService;

@RestController
@CrossOrigin
public class HotelController {
	@Autowired
	HotelService hotelService;

	@GetMapping("/hotel/{hotelId}")
	public ResponseEntity<Hotel> findHotelByHotelId(@PathVariable int hotelId) {
		Hotel hotel = hotelService.findHotelByHotelId(hotelId);
		if (hotel != null)
			return new ResponseEntity<>(hotel, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	};

	@GetMapping("/hotels")
	public ResponseEntity<List<Hotel>> getAllHotels() {
		List<Hotel> allHotels = hotelService.getAllHotels();
		if (allHotels != null)
			return new ResponseEntity<>(allHotels, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	};

	@PutMapping("/hotel/{hotelId}/{userId}")
	public ResponseEntity<Hotel> updateHotelLikeList(@PathVariable int userId, @PathVariable int hotelId) {
		Hotel hotel = hotelService.updateHotelLikeList(userId, hotelId);
		return new ResponseEntity<>(hotel, HttpStatus.OK);
	}

	@GetMapping("/hotel/search")
	public ResponseEntity<List<Hotel>> searchHotels(@RequestParam String keyword) {
		List<Hotel> hotels = hotelService.searchHotel(keyword);
		return new ResponseEntity<>(hotels, HttpStatus.OK);
	}

}
