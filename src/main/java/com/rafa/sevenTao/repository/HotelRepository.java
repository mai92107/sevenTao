package com.rafa.sevenTao.repository;

import java.util.Date;
import java.util.List;

import com.rafa.sevenTao.model.Address;
import com.rafa.sevenTao.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rafa.sevenTao.model.Hotel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    public List<Hotel> findByBoss(Users user);


    @Query("SELECT h.address FROM Hotel h")
    public List<Address> findAllAddress();


    @Query("SELECT h FROM Hotel h " +
            "JOIN h.address a " +
            "JOIN h.rooms r " +
            "LEFT JOIN r.`orders` o " +
            "WHERE a.city = :cityCode " +
            "AND r.capacity >= :people " +
            "AND (o.checkInDate < :start OR o.checkInDate >= :end OR o IS NULL)")
    public List<Hotel> findHotelsByDetail(@Param("cityCode") int cityCode, @Param("start") Date start, @Param("end") Date end, @Param("people") int people);


    @Query("SELECT h FROM Hotel h WHERE LOWER(h.chName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(h.enName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(h.introduction) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    public List<Hotel> searchHotelsByKeyword(String keyword);
}
