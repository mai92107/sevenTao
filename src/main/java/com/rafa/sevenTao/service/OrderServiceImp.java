package com.rafa.sevenTao.service;

import com.rafa.sevenTao.model.*;
import com.rafa.sevenTao.repository.OrderRepository;
import com.rafa.sevenTao.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    OrderRepository orderRepository;


    @Override
    public Order createOrder(Users user, Room room, Date start, Date end) {

        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setTotalPrice(countPrice(room, start, end));
        newOrder.setCheckInDate(start);
        newOrder.setCheckOutDate(end);
        newOrder.setRoom(room);
        return orderRepository.save(newOrder);
    }

    @Override
    public boolean isRoomAvailable(Room room, Date start, Date end) {
        if (room.getOrders().size() == 0) {
            return true;
        }
        return room.getOrders()
                .stream()
                .filter(o -> !o.getCheckOutDate().after(start) || !o.getCheckInDate().before(end))
                .findAny().isEmpty();
    }

    private List<Date> getDateFromRange(Date start, Date end) {
        List<Date> allDate = new ArrayList<>();
        if (start.after(end))
            return null;
        while (start.before(end)) {
            allDate.add(start);
            start = Date
                    .from(start.toInstant().plusMillis(1000 * 60 * 60 * 24));
        }
        return allDate;
    }

    private int countByDay(Room room, Date date) {
        int price = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int chosenDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        //將原本日一二三四五六改為一二三四五六日 都是１２３４５６７
        if (chosenDayOfWeek == Calendar.SUNDAY) {
            chosenDayOfWeek = 7;
        } else {
            chosenDayOfWeek--;
        }
        //0123456 vs 1234567 所以減一
        WeekDay weekDay = WeekDay.values()[chosenDayOfWeek - 1];
        System.out.println("查詢的星期：" + weekDay);

        RoomPrice roomPrice = room.getRoomPrices().stream()
                .peek(rp -> System.out.println(rp.getWeekDay() + " 需要花費 " + rp.getPrice()))
                .findFirst().orElse(null);
        if (roomPrice != null)
            price = roomPrice.getPrice();
        return price;
    }



//    public int countBySpecialDate(Room room, Date date) {
//
//        List<RoomPrice> fitRules = room.getRoomPrices()
//                .stream()
//                .filter(RoomPrice::isSpecialDayPrice)
//                .filter(rp -> !rp.getStart().after(date))
//                .filter(rp -> !rp.getEnd().before(date))
//                .toList();
//        return fitRules.stream()
//                .min(Comparator.comparingInt(RoomPrice::getPrice))
//                .map(RoomPrice::getPrice)
//                .orElse(0);
//    }


    @Override
    public int countPrice(Room room, Date checkInDate, Date checkOutDate) {
        System.out.println("計算價格的房間為 "+room.getRoomName());
        if (!checkInDate.before(checkOutDate)) {
            return 0;
        }
        List<Date> livingDate = getDateFromRange(checkInDate, checkOutDate);
        int totalPrice = 0;
        System.out.println("計算居住天數為 "+livingDate.size());

        if (livingDate != null) {
            List<Integer> priceList = livingDate.stream()
                    .map(d -> countByDay(room, d))
                    .toList();
            System.out.println("這間房間的價格表為"+priceList);
            totalPrice = priceList.parallelStream()
                    .mapToInt(p -> p)
                    .sum();
        }

        return totalPrice;
    }

}
