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
        Order order = new Order();

        order.setCheckInDate(request.getCheckInDate());
        order.setCheckOutDate(request.getCheckOutDate());
        order.setRoom(room);
        order.setUser(user);
        order.setTotalPrice(request.getTotalPrice());
//        orderRepository.save(order);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/price")
    public ResponseEntity<Integer> countRoomPrice(@RequestBody CountPriceRequest countPriceRequest){
        Room room = roomService.findRoomByRoomId(countPriceRequest.getRoomId());
        int price = orderService.countPrice(room,countPriceRequest.getStart(),countPriceRequest.getEnd());
return new ResponseEntity<>(price, HttpStatus.OK);
    }
}
