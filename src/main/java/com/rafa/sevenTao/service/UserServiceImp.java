package com.rafa.sevenTao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafa.sevenTao.model.User;
import com.rafa.sevenTao.repository.UserRepository;
import com.rafa.sevenTao.request.SignUpRequest;

@Service
public class UserServiceImp implements UserService {

	List<User> allUsersData = new ArrayList<>();

	@Autowired
	UserRepository userRepository;

	@Override
	public User adduser(SignUpRequest request) {
		User newUser = new User();
		newUser.setAccount(request.getAccount());
		newUser.setPassword(request.getPassword());
		newUser.setLastName(request.getLastName());
		newUser.setFirstName(request.getFirstName());
		newUser.setEmail(request.getEmail());
		newUser.setNickName(request.getNickName());
		newUser.setSex(request.getSex());
		newUser.setPhoneNum(request.getPhoneNum());
		newUser.setAddress(request.getAddress());

		userRepository.save(newUser);
		System.out.println(newUser.toString());
		return newUser;
	}

	@Override
	public void deleteUserByUserId(int userId) {

		userRepository.deleteById(userId);
		System.out.println("deleted user" + userId);

	}

	@Override
	public User findUserByUserId(int userId) {
		Optional<User> user = userRepository.findById(userId);

		if (user == null) {
			throw new NullPointerException();
		}
		return user.get();
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User setUserToHotelerFromUserId(int userId) {
		User hotelUser = findUserByUserId(userId);
		hotelUser.setHoteler(true);

		return userRepository.save(hotelUser);
	}

	@Override
	public User updateUserData(int userId, SignUpRequest request) {
		User user = findUserByUserId(userId);
		user.setAccount(request.getAccount());
		user.setLastName(request.getLastName());
		if (request.getFirstName() != null)
			user.setFirstName(request.getFirstName());
		if (request.getNickName() != null)
			user.setNickName(request.getNickName());
		if (request.getSex() != null)
			user.setSex(request.getSex());
		if (request.getPhoneNum() != null)

			user.setPhoneNum(request.getPhoneNum());
		if (request.getAddress() != null)
			user.setAddress(request.getAddress());

		return userRepository.save(user);
	}

}
