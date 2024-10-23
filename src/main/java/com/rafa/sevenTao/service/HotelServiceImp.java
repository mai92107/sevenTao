package com.rafa.sevenTao.service;

import java.util.*;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import com.rafa.sevenTao.model.*;
import com.rafa.sevenTao.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Hotel createHotel(Users boss, CreateHotelRequest request) {
        // 創建新的 Hotel 實例
        Hotel newHotel = new Hotel();

        newHotel.setChName(request.getChName());
        newHotel.setEnName(request.getEnName());
        newHotel.setIntroduction(request.getIntroduction());
        newHotel.setFacilities(request.getFacilities());
        newHotel.setBoss(boss);
        newHotel = hotelRepository.save(newHotel);

        // 創建並儲存地址
        Address address = request.getAddress();
        if (address != null) {
            Address newAddress = new Address();
            newAddress.setCity(address.getCity());
            newAddress.setDistrict(address.getDistrict());
            newAddress.setStreet(address.getStreet());
            newAddress.setNumber(address.getNumber());
            addressRepository.save(newAddress);

            newHotel.setAddress(newAddress);
        }
        System.out.println(request.getPictures());
        Map<String, String> pictureMap = request.getPictures();
        List<String> pictures = new ArrayList<>();

        if (pictureMap != null) {
            if (pictureMap.get("firstPic") != null)
                pictures.add(pictureMap.get("firstPic"));
            if (pictureMap.get("secondPic") != null)
                pictures.add(pictureMap.get("secondPic"));
            if (pictureMap.get("thirdPic") != null)
                pictures.add(pictureMap.get("thirdPic"));
        }
        System.out.println(pictures);
        newHotel.setPictures(pictures);
        newHotel.setRooms(request.getRooms());
        List<Hotel> myHotels = boss.getMyHotels();
        myHotels.add(newHotel);
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
    public List<Hotel> findHotelsByUser(Users user) {
        return hotelRepository.findByBoss(user);
    }

    @Override
    public Hotel findHotelByHotelId(int hotelId) {

        return hotelRepository.findById(hotelId).orElse(null);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    private final ToDoubleFunction<Hotel> compareByScore = (Hotel::getScore);
    private final ToIntFunction<Hotel> compareByOrders = (hotel -> hotel.getRooms().stream().mapToInt(room -> room.getOrders().size()).sum());
    private final Function<Hotel,Date> compareByBuildDate = (Hotel::getBuildDate);

    @Override
    public List<Hotel> getHotelsByScore(List<Hotel> hotels) {
        List<Hotel> bestHotels = hotels;
        if(hotels == null)
            bestHotels= getAllHotels();
        return bestHotels.stream().sorted(Comparator
                .comparingDouble(compareByScore)
                .thenComparingInt(compareByOrders).reversed()
                .thenComparing(compareByBuildDate, Comparator.reverseOrder()))
                .limit(9).toList();

    }

    @Override
    public List<Hotel> getHotelsByOrders(List<Hotel> hotels) {
        List<Hotel> hotHotels = hotels;
        if(hotels == null)
            hotHotels= getAllHotels();
        return hotHotels.stream().sorted(Comparator
                .comparingInt(compareByOrders).reversed()
                .thenComparingDouble(compareByScore)
                .thenComparing(compareByBuildDate, Comparator.reverseOrder()))
                .limit(9).toList();
    }

    @Override
    public List<Hotel> getHotelsByBuildDate(List<Hotel> hotels) {
        List<Hotel> newHotels = hotels;
        if(hotels == null)
            newHotels= getAllHotels();
        return newHotels.stream().sorted(Comparator
                .comparing(compareByBuildDate, Comparator.reverseOrder())
                .thenComparingDouble(compareByScore)
                .thenComparingInt(compareByOrders).reversed())
                .limit(9).toList();
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
        if (hotel != null) {
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
        }
        return hotelRepository.save(hotel);

    }

    @Override
    public Hotel updateHotelLikeList(Users user, int hotelId) {
        Hotel hotel = findHotelByHotelId(hotelId);
        if (hotel == null) {
            throw new RuntimeException("Hotel not found with id: " + hotelId);
        }

        List<Users> favoriteHotelsByUser = hotel.getLikedByUsers();
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
    public List<Integer> getHotelCity() {
        List<Address> allAddress = hotelRepository.findAllAddress();
        return allAddress.parallelStream().map(Address::getCity).distinct().collect(Collectors.toList());
    }

    @Override
    public Hotel addComment(Users user, Hotel hotel, Comment comment) {
        comment.setHotel(hotel);
        String name = user.getNickName();
        ;
        if (name == null)
            name = user.getFirstName();
        if (name == null)
            name = user.getLastName();

        comment.setUserName(name);
        List<Comment> comments = hotel.getComments();
        comments.addFirst(comment);
        hotel.setComments(comments);

        double averageScore = updateHotelScore(comments);
        if (comments.size() > 5)
            hotel.setScore(averageScore);
        System.out.println("hotel " + hotel.getChName() + "現在平均分數為" + averageScore);

        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> findHotelsByDetail(int cityCode, Date start, Date end, int people) {
        return hotelRepository.findHotelsByDetail(cityCode, start, end, people);
    }

    @Override
    public List<Hotel> findHotelsByKeyword(String keyword) {
        return hotelRepository.searchHotelsByKeyword(keyword);
    }

    private double updateHotelScore(List<Comment> comments) {
        return comments.parallelStream().mapToInt(Comment::getRate).average().getAsDouble();
    }

}
