package com.rafa.sevenTao.controller;

import com.rafa.sevenTao.model.*;
import com.rafa.sevenTao.repository.OrderRepository;
import com.rafa.sevenTao.request.CountPriceRequest;
import com.rafa.sevenTao.request.CreateOrderRequest;
import com.rafa.sevenTao.service.OrderService;
import com.rafa.sevenTao.service.RoomService;
import com.rafa.sevenTao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/hotel/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    RoomService roomService;


    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestHeader("Authorization") String jwt, @RequestBody CreateOrderRequest request) {

        Users user = userService.findUserByJwt(jwt);
        Room room = roomService.findRoomByRoomId(request.getRoomId());
        System.out.println("我要訂這個房間" + room.getRoomName());
        Order newOrder = orderService.createOrder(user, room, request.getCheckInDate(), request.getCheckOutDate());

        return new ResponseEntity<>(newOrder, HttpStatus.OK);
    }

    @GetMapping("/price")
    public ResponseEntity<Integer> countRoomPrice(@RequestBody CountPriceRequest countPriceRequest) {
        Room room = roomService.findRoomByRoomId(countPriceRequest.getRoomId());
        int price = orderService.countPrice(room, countPriceRequest.getStart(), countPriceRequest.getEnd());
        return new ResponseEntity<>(price, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deletePastOrderFromUser(@RequestHeader("Authorization") String jwt, @PathVariable long orderId) {
        Users user = userService.findUserByJwt(jwt);
        System.out.println("我要刪除order"+orderId);
        if (orderService.deletePastOrderFromUser(user, orderId))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
