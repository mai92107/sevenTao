package com.rafa.sevenTao.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.rafa.sevenTao.model.*;
import com.rafa.sevenTao.request.CreateHotelRequest;
import com.rafa.sevenTao.response.HotelsResponse;
import com.rafa.sevenTao.response.RoomEntity;

public interface HotelService {

    public Hotel createHotel(Users user, CreateHotelRequest request);

    public boolean deleteHotelByHotelId(int hotelId);

    public List<Hotel> findHotelsByBoss(Users user);

    public Hotel findHotelByHotelId(int hotelId);

    public List<Hotel> getAllHotels();

    public List<Hotel> getHotelsByScore(List<Hotel> hotels);

    public List<Hotel> getHotelsByOrders(List<Hotel> hotels);

    public List<Hotel> getHotelsByBuildDate(List<Hotel> hotels);

    public Hotel updateHotelData(int hotelId, CreateHotelRequest request);

    public Hotel updateHotelLikeList(Users user, int hotelId);

    public List<Integer> getHotelCity();


    public List<HotelsResponse> convertHotelFilterRoom(List<Hotel> hotels, Date start, Date end, Integer people);

    public Comment addComment(Users user, Hotel hotel, Comment comment);

    public List<Hotel> findHotelsByDetail(Integer cityCode, Date start, Date end, Integer people, String keyword);

    public Map<String, List<List<Order>>> findOrdersFromBoss(Users user);

    public List<HotelsResponse> conversion(List<Hotel> hotels);

    public List<RoomEntity> convertRoomToDataRoom(List<Room> rooms, Date start, Date end, Integer people);

    public boolean deleteComment(long commentId);
}
