package com.rafa.sevenTao.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.rafa.sevenTao.model.Address;
import com.rafa.sevenTao.model.Comment;
import com.rafa.sevenTao.model.Hotel;
import com.rafa.sevenTao.model.Users;
import com.rafa.sevenTao.request.CreateHotelRequest;
import org.springframework.web.bind.annotation.RequestParam;

public interface HotelService {

	public Hotel createHotel(Users user, CreateHotelRequest request);

	public boolean deleteHotelByHotelId(int hotelId);

	public List<Hotel> findHotelsByUser(Users user);

	public Hotel findHotelByHotelId(int hotelId);

	public List<Hotel> getAllHotels();

	public List<Hotel> getHotelsByScore(List<Hotel> hotels);

	public List<Hotel> getHotelsByOrders(List<Hotel> hotels);

	public List<Hotel> getHotelsByBuildDate(List<Hotel> hotels);

	public Hotel updateHotelData(int hotelId, CreateHotelRequest request);

	public Hotel updateHotelLikeList(Users user, int hotelId);

	public List<Integer> getHotelCity();

	public Hotel addComment(Users user,Hotel hotel, Comment comment);

	public List<Hotel> findHotelsByDetail(int cityCode, Date start, Date end, int people);

	public List<Hotel> findHotelsByKeyword(String keyword);
}
