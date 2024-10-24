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
            "LEFT JOIN r.orders o " +
            "WHERE (:cityCode IS NULL OR a.city = :cityCode) " +
            "AND (:people IS NULL OR r.capacity >= :people) " +
            "AND (o IS NULL OR o.checkOutDate < :start OR o.checkInDate >= :end) " +
            "AND (:keyword IS NULL OR :keyword = '' OR LOWER(h.chName) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            "OR LOWER(h.enName) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            "OR LOWER(h.introduction) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    public List<Hotel> findHotelsByDetail(@Param("cityCode") Integer cityCode,
                                          @Param("start") Date start,
                                          @Param("end") Date end,
                                          @Param("people") Integer people,
                                          @Param("keyword") String keyword);


}
