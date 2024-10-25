package com.rafa.sevenTao.service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import com.rafa.sevenTao.model.*;
import com.rafa.sevenTao.repository.CommentRepository;
import com.rafa.sevenTao.response.HotelsResponse;
import com.rafa.sevenTao.response.RoomEntity;
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

    @Autowired
    OrderService orderService;

    @Override
    public Hotel createHotel(Users boss, CreateHotelRequest request) {
        // 創建新的 Hotel 實例
        Hotel newHotel = new Hotel();

        newHotel.setChName(request.getChName());
        newHotel.setEnName(request.getEnName());
        newHotel.setIntroduction(request.getIntroduction());
        newHotel.setFacilities(request.getFacilities());
        newHotel.setBoss(boss);
        newHotel.setScore(0);
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
        List<String> pictures = new CopyOnWriteArrayList<>();

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
    public List<Hotel> findHotelsByBoss(Users user) {
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

    @Override
    public List<Hotel> findHotelsByDetail(Integer cityCode, Date start, Date end, Integer people, String keyword) {

        return hotelRepository.findHotelsByDetail(cityCode, start, end, people, keyword);
    }

    @Override
    public List<RoomEntity> convertRoomToDataRoom(List<Room> rooms, Date start, Date end, Integer people) {
        List<RoomEntity> dataRooms = new CopyOnWriteArrayList<>();
        System.out.println("收到rooms 幾間準備轉換" + rooms.size());

        List<Room> filteredRoom = rooms.stream()
                .filter(r -> {
                    if (people != null) {
                        return r.getCapacity() >= people;
                    }
                    return true;
                })
                .filter(r -> {
                    if (start != null && end != null) {
                        return r.getOrders() != null && r.getOrders().stream()
                                .noneMatch(o -> o.getCheckOutDate().after(start) && o.getCheckInDate().before(end));
                    }
                    return true;
                })
                .toList();
        System.out.println("過濾完的rooms有幾間" + filteredRoom.size());

        filteredRoom.forEach(r -> {
            RoomEntity re = new RoomEntity();
            re.setRoomName(r.getRoomName());
            re.setPrice(orderService.countPrice(r, start, end));
            System.out.println("轉換的room名字及價格" + r.getRoomName() + orderService.countPrice(r, start, end));

            re.setCapacity(r.getCapacity());
            re.setStart(start);
            re.setEnd(end);
            re.setRoomId(r.getRoomId());
            re.setSpecialties(r.getSpecialties());
            re.setRoomSize(r.getRoomSize());
            re.setAvailable(r.isAvailable());
            dataRooms.add(re);
        });
        System.out.println("轉換完的rooms有幾間" + dataRooms.size());

        return dataRooms;
    }

    @Override
    public boolean deleteComment(long commentId) {
        commentRepository.deleteById(commentId);
        return false;
    }

    private HotelsResponse filterInvalidRoom(HotelsResponse hotelCardResponse, Date start, Date end, Integer people) {
        // 過濾符合條件的房間
        List<Room> newRooms = hotelCardResponse.getRooms().stream()
                .filter(r -> r.getCapacity() >= people) // 檢查房間容量是否足夠
                .filter(r -> r.getOrders().stream() // 檢查是否有訂單與當前查詢衝突
                        .noneMatch(o -> o.getCheckOutDate().after(start) && o.getCheckInDate().before(end))) // 沒有與查詢日期重疊的訂單
                .toList();

        hotelCardResponse.setRooms(newRooms); // 設置轉換後的房間列表
        return hotelCardResponse;
    }


    private final ToDoubleFunction<Hotel> compareByScore = (Hotel::getScore);
    private final ToIntFunction<Hotel> compareByOrders = (hotel ->
            hotel.getRooms().stream().mapToInt(room ->
                    room.getOrders().size()).sum());
    private final Function<Hotel, Date> compareByBuildDate = (Hotel::getBuildDate);

    public List<HotelsResponse> conversion(List<Hotel> hotels) {
        List<HotelsResponse> responseHotel = new CopyOnWriteArrayList<>();
        if (hotels.isEmpty())
            return null;
        hotels.forEach(h -> {
            HotelsResponse hc = new HotelsResponse();
            hc.setChName(h.getChName());
            hc.setHotelId(h.getHotelId());
            hc.setEnName(h.getEnName());
            if (!h.getPictures().isEmpty())
                hc.setPicture(h.getPictures().getFirst());
            hc.setScore(h.getScore());
            hc.setRooms(h.getRooms());
            hc.setIntroduction(h.getIntroduction());
            responseHotel.add(hc);
        });
        return responseHotel;
    }

    @Override
    public List<Hotel> getHotelsByScore(List<Hotel> hotels) {
        List<Hotel> bestHotels = hotels;
        if (hotels == null)
            bestHotels = getAllHotels();

        bestHotels = bestHotels.stream().sorted(Comparator
                        .comparingDouble(compareByScore)
                        .thenComparingInt(compareByOrders).reversed()
                        .thenComparing(compareByBuildDate, Comparator.reverseOrder()))
                .limit(9)
                .toList();

        return bestHotels;
    }

    @Override
    public List<Hotel> getHotelsByOrders(List<Hotel> hotels) {
        List<Hotel> hotHotels = hotels;
        if (hotels == null)
            hotHotels = getAllHotels();
        hotHotels = hotHotels.stream().sorted(Comparator
                        .comparingInt(compareByOrders).reversed()
                        .thenComparingDouble(compareByScore)
                        .thenComparing(compareByBuildDate, Comparator.reverseOrder()))
                .limit(9).toList();

        return hotHotels;
    }


    @Override
    public List<Hotel> getHotelsByBuildDate(List<Hotel> hotels) {
        List<Hotel> newHotels = hotels;
        if (hotels == null)
            newHotels = getAllHotels();
        newHotels = newHotels.stream().sorted(Comparator
                        .comparing(compareByBuildDate, Comparator.reverseOrder())
                        .thenComparingDouble(compareByScore)
                        .thenComparingInt(compareByOrders).reversed())
                .limit(9).toList();

        return newHotels;
    }


    @Override
    public Hotel updateHotelData(int hotelId, CreateHotelRequest request) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(
                () -> new RuntimeException("Hotel not found with id: " + hotelId)
        );

        List<String> pictures = new CopyOnWriteArrayList<>();
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
            hotel.setFixedDate(new Date());
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
        return allAddress.parallelStream().map(Address::getCity)
                .distinct().collect(Collectors.toList());
    }

    @Override
    public Comment addComment(Users user, Hotel hotel, Comment comment) {
        Comment newComment = new Comment();
        newComment.setComment(comment.getComment());
        newComment.setHotel(hotel);
        newComment.setUserId(user.getUserId());
        newComment.setRate(comment.getRate());
        newComment.setUserPhoto(user.getPhoto());

        String userName = "";
        if (user.getNickName() != null) {
            userName = user.getNickName();
            System.out.println("評論名字決定採用 " + userName);
        } else if (user.getFirstName() != null) {
            userName = user.getFirstName();
            System.out.println("評論名字決定採用 " + userName);
        } else {
            userName = user.getLastName();
            System.out.println("評論名字決定採用 " + userName);
        }

        newComment.setUserName(userName);

        List<Comment> comments = hotel.getComments();
        comments.addFirst(newComment);
        hotel.setComments(comments);

        double averageScore = updateHotelScore(comments);
        if (comments.size() > 5)
            hotel.setScore(averageScore);
        System.out.println("hotel " + hotel.getChName() + "現在平均分數為" + averageScore);
        hotelRepository.save(hotel);

        return commentRepository.save(newComment);
    }

    @Override
    public List<HotelsResponse> convertHotelFilterRoom(List<Hotel> hotels, Date start, Date end, Integer people) {
        return conversion(hotels).stream().map(hr ->
                        filterInvalidRoom(hr, start, end, people))
                .collect(Collectors.toList());
    }


    public Map<String, List<List<Order>>> findOrdersFromBoss(Users user) {
        Map<String, List<List<Order>>> hotelOrders = new HashMap<>();

        List<Hotel> hotels = findHotelsByBoss(user);
        hotels.forEach(h -> {
            List<Order> validOrder = new CopyOnWriteArrayList<>();
            List<Order> expiredOrder = new CopyOnWriteArrayList<>();
            h.getRooms().forEach(r ->
                    r.getOrders().forEach(o -> {
                        if (o.checkValidOrder())
                            validOrder.add(o);
                        else expiredOrder.add(o);
                    })
            );
            List<List<Order>> hotelOrder = new CopyOnWriteArrayList<>();
            hotelOrder.add(validOrder);
            hotelOrder.add(expiredOrder);
            hotelOrders.put(h.getChName(), hotelOrder);
        });
        return hotelOrders;
    }


    private double updateHotelScore(List<Comment> comments) {
        return comments.parallelStream().mapToInt(Comment::getRate).average().getAsDouble();
    }

}
