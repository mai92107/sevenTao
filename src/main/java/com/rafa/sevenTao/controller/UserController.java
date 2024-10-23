package com.rafa.sevenTao.controller;

import java.util.List;
import java.util.Map;

import com.rafa.sevenTao.request.UpdateProfileRequest;
import com.rafa.sevenTao.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rafa.sevenTao.model.Hotel;
import com.rafa.sevenTao.model.Users;
import com.rafa.sevenTao.request.SignUpRequest;
import com.rafa.sevenTao.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/member")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    HotelService hotelService;

    @DeleteMapping()
    public ResponseEntity<?> deleteUserByEmail(@RequestBody Map<String, String> request) {
        Users user = userService.findUser(Integer.parseInt(request.get("userId")));
        userService.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    };

    @GetMapping
    public ResponseEntity<Users> getUserProfile(@RequestHeader("Authorization") String jwt) {
        Users user = userService.findUserByJwt(jwt);
        return new ResponseEntity<Users>(user, HttpStatus.OK);
    }

    ;

    @PutMapping
    public ResponseEntity<Users> updateUserData(@RequestHeader("Authorization") String jwt, @RequestBody UpdateProfileRequest request) {
        Users user = userService.findUserByJwt(jwt);
        Users updatedUser = userService.updateUserData(user, request);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/favoriteHotels")
    public ResponseEntity<List<Hotel>> getUserFavoriteHotel(@RequestHeader("Authorization") String jwt) {
        Users user = userService.findUserByJwt(jwt);
        System.out.println(user);
        List<Hotel> favoriteHotels = userService.getFavoriteHotels(user);
        return new ResponseEntity<>(favoriteHotels, HttpStatus.OK);
    }

    @GetMapping("/{hotelId}/favorite")
    public ResponseEntity<Boolean> checkIsFavoriteHotel(@RequestHeader("Authorization") String jwt, @PathVariable int hotelId) {
        Users user = userService.findUserByJwt(jwt);
        Hotel hotel = hotelService.findHotelByHotelId(hotelId);
        List<Hotel> favoriteHotels = userService.getFavoriteHotels(user);
        boolean isFavoriteHotel = favoriteHotels.contains(hotel);
        return new ResponseEntity<>(isFavoriteHotel, HttpStatus.OK);
    }


}
