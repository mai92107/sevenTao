package com.rafa.sevenTao.service;

import com.rafa.sevenTao.model.Hotel;
import com.rafa.sevenTao.model.Room;
import com.rafa.sevenTao.request.CreateRoomRequest;

public interface RoomService {
	public Room createRoom(Hotel hotel, CreateRoomRequest request);

	public boolean deleteRoomByRoomId(int roomId);

	public Room findRoomByRoomId(int roomId);
}
