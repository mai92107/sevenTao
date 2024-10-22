package com.rafa.sevenTao.controller;

import com.rafa.sevenTao.model.Users;
import com.rafa.sevenTao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rafa.sevenTao.model.Hotel;
import com.rafa.sevenTao.model.Room;
import com.rafa.sevenTao.request.CreateRoomRequest;
import com.rafa.sevenTao.service.HotelService;
import com.rafa.sevenTao.service.RoomService;

@CrossOrigin
@RestController
@RequestMapping("/boss/hotel")
public class BossRoomController {

    @Autowired
    HotelService hotelService;

    @Autowired
    UserService userService;

    @Autowired
    RoomService roomService;

    @PostMapping("/{hotelId}/create")
    public ResponseEntity<Room> createRoom(@RequestHeader("Authorization") String jwt, @PathVariable int hotelId, @RequestBody CreateRoomRequest request) {
        Users user = userService.findUserByJwt(jwt);
        Hotel hotel = hotelService.findHotelByHotelId(hotelId);
        Room room = roomService.createRoom(hotel, request);
        if (user != null && room != null)
            return new ResponseEntity<>(room, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    ;

    @DeleteMapping("/room/{roomId}")
    public ResponseEntity<?> deleteRoomByRoomId(@RequestHeader("Authorization") String jwt, @PathVariable int roomId) {
        Users user = userService.findUserByJwt(jwt);

        if (user == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (roomService.deleteRoomByRoomId(roomId))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    ;

//    @GetMapping("/room/{roomId}")
//    public ResponseEntity<Room> findRoomByRoomId(@PathVariable int roomId) {
//        Room room = roomService.findRoomByRoomId(roomId);
//        if (room != null)
//            return new ResponseEntity<>(room, HttpStatus.OK);
//        else
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    ;
}
