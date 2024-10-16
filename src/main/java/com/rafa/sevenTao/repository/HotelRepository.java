package com.rafa.sevenTao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafa.sevenTao.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

	public List<Hotel> findByBossUserId(int userId);

//	@Query("SELECT p FROM hotel p WHERE LOWER(p.chName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.enName) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
//	public List<Hotel> searchHotels(String keyword);
}
