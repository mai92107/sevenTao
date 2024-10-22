package com.rafa.sevenTao.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafa.sevenTao.model.Hotel;
import com.rafa.sevenTao.model.Room;
import com.rafa.sevenTao.repository.RoomRepository;
import com.rafa.sevenTao.request.CreateRoomRequest;

@Service
public class RoomServiceImp implements RoomService {

	@Autowired
	RoomRepository roomRepository;

	@Override
	public Room createRoom(Hotel hotel, CreateRoomRequest request) {
		Room room = new Room();

		room.setRoomName(request.getRoomName());
//		room.setRoomPrice(request.getRoomPrice());
		room.setRoomPic(request.getRoomPic());
		room.setSpecialties(request.getSpecialties());
		room.setCapacity(request.getCapacity());
		room.setRoomSize(request.getRoomSize());
		room.setHotel(hotel);

		return roomRepository.save(room);
	}

	@Override
	public boolean deleteRoomByRoomId(int roomId) {
		Optional<Room> room = roomRepository.findById(roomId);
		if (room.isPresent()) {
			roomRepository.deleteById(roomId);
			System.out.println("roomId" + roomId + "已成功刪除");
			return true;
		} else
			return false;
	}

	@Override
	public Room findRoomByRoomId(int roomId) {
		Room room = roomRepository.findById(roomId).orElse(null);
		return room;
	}

}
