package com.rafa.sevenTao.service;

import java.util.List;

import com.rafa.sevenTao.model.Hotel;
import com.rafa.sevenTao.model.User;
import com.rafa.sevenTao.request.CreateHotelRequest;

public interface HotelService {

	public Hotel createHotel(User user, CreateHotelRequest request);

	public boolean deleteHotelByHotelId(int hotelId);

	public List<Hotel> findHotelsByUserId(int userId);

	public Hotel findHotelByHotelId(int hotelId);

	public List<Hotel> getAllHotels();

	public Hotel updateHotelData(int hotelId, CreateHotelRequest request);

	public Hotel updateHotelLikeList(int userId, int hotelId);

	public List<Hotel> searchHotel(String keyword);
}
