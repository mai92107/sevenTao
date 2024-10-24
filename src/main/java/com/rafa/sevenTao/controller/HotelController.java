package com.rafa.sevenTao.controller;

import java.util.Date;
import java.util.List;

import com.rafa.sevenTao.model.Comment;
import com.rafa.sevenTao.model.Room;
import com.rafa.sevenTao.response.HotelEntity;
import com.rafa.sevenTao.model.Users;
import com.rafa.sevenTao.response.HotelPageWithValidRooms;
import com.rafa.sevenTao.response.RoomEntity;
import com.rafa.sevenTao.service.UserService;
import jdk.swing.interop.SwingInterOpUtils;
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
    public ResponseEntity<HotelPageWithValidRooms> findHotelByHotelId(@PathVariable int hotelId,
                                                                      @RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                                                      @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
                                                                      @RequestParam(value = "people", required = false) Integer people) {
        Hotel hotel = hotelService.findHotelByHotelId(hotelId);
        System.out.println("搜尋這個hotel: " + hotel.getChName());

        List<Room> rooms = hotel.getRooms();

        HotelPageWithValidRooms dataHotel = new HotelPageWithValidRooms();
        if(start==null&&end==null&&people==null || rooms.isEmpty()) {
            dataHotel.setHotel(hotel);
            return new ResponseEntity<>(dataHotel, HttpStatus.OK);
        }

        System.out.println("搜尋這間hotel有幾間room: " + rooms.size());

        List<RoomEntity> dataRooms = hotelService.convertRoomToDataRoom(rooms, start, end, people);

        System.out.println("搜尋這間hotel有幾間room符合標準: " + dataRooms.size());

        dataHotel.setHotel(hotel);
        dataHotel.setRooms(dataRooms);


        if (dataHotel.getHotel() != null) {
            System.out.println("取得hotel: " + dataHotel.getHotel().getChName());
            return new ResponseEntity<>(dataHotel, HttpStatus.OK);
        }
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
    public ResponseEntity<HotelEntity> getHotels() {
        HotelEntity allHotelType = new HotelEntity();
        if (hotelService.getHotelsByScore(null) == null || hotelService.getHotelsByOrders(null) == null || hotelService.getHotelsByBuildDate(null) == null || hotelService.getAllHotels() == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        allHotelType.setBestHotels(hotelService.conversion(hotelService.getHotelsByScore(null)));
        allHotelType.setHotHotels(hotelService.conversion(hotelService.getHotelsByOrders(null)));
        allHotelType.setNewHotels(hotelService.conversion(hotelService.getHotelsByBuildDate(null)));
        allHotelType.setHotels(hotelService.conversion(hotelService.getAllHotels()));

        if (allHotelType.getHotels() != null || allHotelType.getHotHotels() != null || allHotelType.getNewHotels() != null || allHotelType.getBestHotels() != null)
            return new ResponseEntity<>(allHotelType, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/hotel/find")
    public ResponseEntity<HotelEntity> searchHotelByDetails(@RequestParam(value = "cityCode", required = false) Integer cityCode,
                                                            @RequestParam(value = "start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                                            @RequestParam(value = "end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
                                                            @RequestParam(value = "people", required = false) Integer people,
                                                            @RequestParam(value = "keyword", required = false) String keyword) {
        List<Hotel> hotels = hotelService.findHotelsByDetail(cityCode, start, end, people, keyword);
        HotelEntity allHotelType = new HotelEntity();
        allHotelType.setBestHotels(hotelService.convertHotelFilterRoom(hotelService.getHotelsByScore(hotels), start, end, people));
        allHotelType.setHotHotels(hotelService.convertHotelFilterRoom(hotelService.getHotelsByOrders(hotels), start, end, people));
        allHotelType.setNewHotels(hotelService.convertHotelFilterRoom(hotelService.getHotelsByScore(hotels), start, end, people));
        allHotelType.setHotels(hotelService.convertHotelFilterRoom(hotels, start, end, people));

        return new ResponseEntity<>(allHotelType, HttpStatus.OK);
    }


}
