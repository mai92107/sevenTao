package com.rafa.sevenTao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rafa.sevenTao.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {


	public Users findUserByEmail(String email);

	public Users findUserByAccount(String account);

}
