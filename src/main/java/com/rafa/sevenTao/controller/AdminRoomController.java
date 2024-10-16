package com.rafa.sevenTao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafa.sevenTao.model.Hotel;
import com.rafa.sevenTao.model.Room;
import com.rafa.sevenTao.request.CreateRoomRequest;
import com.rafa.sevenTao.service.HotelService;
import com.rafa.sevenTao.service.RoomService;

@CrossOrigin
@RestController
@RequestMapping("/admin/hotel")
public class AdminRoomController {

	@Autowired
	HotelService hotelService;

	@Autowired
	RoomService roomService;

	@PostMapping("/{hotelId}/create")
	public ResponseEntity<Room> createRoom(@PathVariable int hotelId, @RequestBody CreateRoomRequest request) {
		Hotel hotel = hotelService.findHotelByHotelId(hotelId);
		Room room = roomService.createRoom(hotel, request);
		if (room != null)
			return new ResponseEntity<>(room, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	};

	@DeleteMapping("/room/{roomId}")
	public ResponseEntity<?> deleteRoomByRoomId(@PathVariable int roomId) {
		boolean success = roomService.deleteRoomByRoomId(roomId);
		if (success)
			return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	};

	@GetMapping("/room/{roomId}")
	public ResponseEntity<Room> findRoomByRoomId(@PathVariable int roomId) {
		Room room = roomService.findRoomByRoomId(roomId);
		if (room != null)
			return new ResponseEntity<>(room, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	};
}
