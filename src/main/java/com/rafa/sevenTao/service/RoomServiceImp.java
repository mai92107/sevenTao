package com.rafa.sevenTao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import com.rafa.sevenTao.model.RoomPrice;
import com.rafa.sevenTao.repository.RoomPriceRepository;
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

    @Autowired
    RoomPriceRepository roomPriceRepository;

    @Override
    public Room createRoom(Hotel hotel, CreateRoomRequest request) {
        Room room = new Room();

        room.setRoomName(request.getRoomName());
        room.setSpecialties(request.getSpecialties());
        room.setCapacity(request.getCapacity());
        room.setRoomSize(request.getRoomSize());
        room.setAvailable(true);
        room.setHotel(hotel);

        List<RoomPrice> roomPrices = new CopyOnWriteArrayList<>();
        request.getPrices().parallelStream().forEach(rp -> {
            RoomPrice roomPrice = new RoomPrice();
            roomPrice.setPrice(rp.getPrice());
            roomPrice.setWeekDay(rp.getWeekDay());
            roomPrice.setRoom(room);
            roomPrices.add(roomPrice);
        });

        room.setRoomPrices(roomPrices);
        List<String> pictures = request.getRoomPic();
        room.setRoomPic(pictures);
        roomRepository.save(room);
        roomPriceRepository.saveAll(roomPrices);


        return room;
    }

    @Override
    public boolean deleteRoomByRoomId(long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isPresent()) {
            roomRepository.deleteById(roomId);
            System.out.println("roomId" + roomId + "已成功刪除");
            return true;
        } else
            return false;
    }

    @Override
    public Room findRoomByRoomId(long roomId) {
        return roomRepository.findById(roomId).orElse(null);
    }

}
