package com.rafa.sevenTao.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.rafa.sevenTao.model.Comment;
import com.rafa.sevenTao.model.HomeHotels;
import com.rafa.sevenTao.model.Users;
import com.rafa.sevenTao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rafa.sevenTao.model.Hotel;
import com.rafa.sevenTao.service.HotelService;

@RestController
@CrossOrigin
public class HotelController {
    @Autowired
    HotelService hotelService;

    @Autowired
    UserService userService;

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<Hotel> findHotelByHotelId(@PathVariable int hotelId) {
        Hotel hotel = hotelService.findHotelByHotelId(hotelId);
        if (hotel != null)
            return new ResponseEntity<>(hotel, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/hotel/{hotelId}")
    public ResponseEntity<Hotel> updateHotelLikeList(@RequestHeader("Authorization") String jwt, @PathVariable int hotelId) {
        Users user = userService.findUserByJwt(jwt);
        Hotel hotel = hotelService.updateHotelLikeList(user, hotelId);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @GetMapping("/hotelAddress")
    public ResponseEntity<List<Integer>> getHotelCities() {
        List<Integer> cities = hotelService.getHotelCity();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @PostMapping("/hotel/{hotelId}/comment")
    public ResponseEntity<Comment> addComment(@RequestHeader("Authorization") String jwt, @PathVariable int hotelId, @RequestBody Comment comment) {
        Users user = userService.findUserByJwt(jwt);
        Hotel hotel = hotelService.findHotelByHotelId(hotelId);
        hotelService.addComment(user, hotel, comment);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }


    ;

    @GetMapping("/hotels")
    public ResponseEntity<HomeHotels> getHotels() {
        HomeHotels allHotelType = new HomeHotels();
        allHotelType.setBestHotels(hotelService.getHotelsByScore(null));
        allHotelType.setHotHotels(hotelService.getHotelsByOrders(null));
        allHotelType.setNewHotels(hotelService.getHotelsByBuildDate(null));
        allHotelType.setHotels(hotelService.getAllHotels());

        if (allHotelType.getHotels() != null || allHotelType.getHotHotels() !=null||allHotelType.getNewHotels() !=null||allHotelType.getBestHotels() !=null)
            return new ResponseEntity<>(allHotelType, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/hotel/find")
    public ResponseEntity<HomeHotels> searchHotelByDetails(@RequestParam int cityCode,
                                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
                                                           @RequestParam int people) {
        List<Hotel> hotels = hotelService.findHotelsByDetail(cityCode, start, end, people);
        HomeHotels allHotelType = new HomeHotels();
        allHotelType.setBestHotels(hotelService.getHotelsByScore(hotels));
        allHotelType.setHotHotels(hotelService.getHotelsByOrders(hotels));
        allHotelType.setNewHotels(hotelService.getHotelsByBuildDate(hotels));
        allHotelType.setHotels(hotels);

        return new ResponseEntity<>(allHotelType, HttpStatus.OK);
    }

    @GetMapping("/hotel/search")
    public ResponseEntity<HomeHotels> searchHotels(@RequestParam String keyword) {
        List<Hotel> hotels = hotelService.findHotelsByKeyword(keyword);
        HomeHotels allHotelType = new HomeHotels();
        allHotelType.setBestHotels(hotelService.getHotelsByScore(hotels));
        allHotelType.setHotHotels(hotelService.getHotelsByOrders(hotels));
        allHotelType.setNewHotels(hotelService.getHotelsByBuildDate(hotels));
        allHotelType.setHotels(hotels);

        return new ResponseEntity<>(allHotelType, HttpStatus.OK);

    }

}
