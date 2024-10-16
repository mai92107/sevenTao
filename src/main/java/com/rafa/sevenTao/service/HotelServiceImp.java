package com.rafa.sevenTao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafa.sevenTao.model.Address;
import com.rafa.sevenTao.model.Hotel;
import com.rafa.sevenTao.model.User;
import com.rafa.sevenTao.repository.AddressRepository;
import com.rafa.sevenTao.repository.HotelRepository;
import com.rafa.sevenTao.request.CreateHotelRequest;

@Service
public class HotelServiceImp implements HotelService {

	@Autowired
	UserService userService;

	@Autowired
	HotelRepository hotelRepository;

	@Autowired
	AddressRepository addressRepository;

	@Override
	public Hotel createHotel(User boss, CreateHotelRequest request) {
		// 創建新的 Hotel 實例
		Hotel newHotel = new Hotel();

		newHotel.setChName(request.getChName());
		newHotel.setEnName(request.getEnName());
		newHotel.setIntroduction(request.getIntroduction());
		newHotel.setFacilities(request.getFacilities());
		newHotel.setBoss(boss);
		newHotel.setBuildDate(new Date());

		newHotel = hotelRepository.save(newHotel);

		// 創建並儲存地址
		Address address = request.getAddress();
		if (address != null) {
			Address newAddress = new Address();
			newAddress.setCity(address.getCity());
			newAddress.setDistrict(address.getDistrict());
			newAddress.setStreet(address.getStreet());
			newAddress.setNumber(address.getNumber());
			addressRepository.save(newAddress); // 保存地址到數據庫

			newHotel.setAddress(newAddress); // 設置新的酒店地址
		}
		System.out.println(request.getPictures());
		// 獲取圖片並初始化列表
		Map<String, String> pictureMap = request.getPictures();
		List<String> pictures = new ArrayList<>();

		// 確保 pictureMap 不為 null
		if (pictureMap != null) {
			if (pictureMap.get("firstPic") != null)
				pictures.add(pictureMap.get("firstPic"));
			if (pictureMap.get("secondPic") != null)
				pictures.add(pictureMap.get("secondPic"));
			if (pictureMap.get("thirdPic") != null)
				pictures.add(pictureMap.get("thirdPic"));
		}
		System.out.println(pictures);

		// 設置酒店屬性
		newHotel.setPictures(pictures);
		// 設置其他屬性
		newHotel.setRooms(request.getRooms());

		// 更新用戶的酒店列表
		List<Hotel> myHotels = boss.getMyHotels();
		myHotels.add(newHotel);

		// 儲存酒店到數據庫
		return hotelRepository.save(newHotel);
	}

	@Override
	public boolean deleteHotelByHotelId(int hotelId) {
		Optional<Hotel> hotel = hotelRepository.findById(hotelId);
		if (hotel.isPresent()) {
			hotelRepository.deleteById(hotelId);
			System.out.println("hotelId" + hotelId + "已成功刪除");
			return true;
		} else
			return false;

	}

	@Override
	public List<Hotel> findHotelsByUserId(int userId) {
		List<Hotel> hotels = hotelRepository.findByBossUserId(userId);
		return hotels;
	}

	@Override
	public Hotel findHotelByHotelId(int hotelId) {
		Hotel hotel = hotelRepository.findById(hotelId).orElse(null);

		return hotel;
	}

	@Override
	public List<Hotel> getAllHotels() {
		List<Hotel> hotels = hotelRepository.findAll();
		return hotels;
	}

	@Override
	public Hotel updateHotelData(int hotelId, CreateHotelRequest request) {
		Hotel hotel = hotelRepository.findById(hotelId).orElse(null);

		List<String> pictures = new ArrayList<>();
		if (request.getPictures().get("firstPic") != null)
			pictures.add(request.getPictures().get("firstPic"));
		if (request.getPictures().get("secondPic") != null)
			pictures.add(request.getPictures().get("secondPic"));
		if (request.getPictures().get("thirdPic") != null)
			pictures.add(request.getPictures().get("thirdPic"));

		hotel.setPictures(pictures);
		hotel.setChName(request.getChName());
		hotel.setEnName(request.getEnName());
		hotel.setIntroduction(request.getIntroduction());
		hotel.setFacilities(request.getFacilities());

		Address address = new Address();
		address.setCity(request.getAddress().getCity());
		address.setDistrict(request.getAddress().getDistrict());
		address.setStreet(request.getAddress().getStreet());
		address.setNumber(request.getAddress().getNumber());
		addressRepository.save(address);

		hotel.setAddress(address);

		if (request.getRooms() != null)
			hotel.setRooms(request.getRooms());

		return hotelRepository.save(hotel);
	}

	@Override
	public Hotel updateHotelLikeList(int userId, int hotelId) {
		Hotel hotel = findHotelByHotelId(hotelId);
		User user = userService.findUserByUserId(userId);
		if (hotel == null) {
			throw new RuntimeException("Hotel not found with id: " + hotelId);
		}
		if (user == null) {
			throw new RuntimeException("User not found with id: " + userId);
		}
		List<User> favoriteHotelsByUser = hotel.getLikedByUsers();
		if (favoriteHotelsByUser.contains(user)) {
			favoriteHotelsByUser.remove(user);
			hotel.setLikedByUsers(favoriteHotelsByUser);
		} else {
			favoriteHotelsByUser.add(user);
			hotel.setLikedByUsers(favoriteHotelsByUser);
		}

		return hotelRepository.save(hotel);
	}

	@Override
	public List<Hotel> searchHotel(String keyword) {
//		return hotelRepository.searchHotels(keyword);
		return null;
	}

}
