package com.rafa.sevenTao.service;

import java.util.List;

import com.rafa.sevenTao.model.User;
import com.rafa.sevenTao.request.SignUpRequest;

public interface UserService {

	public User adduser(SignUpRequest request);

	public void deleteUserByUserId(int userId);

	public User findUserByUserId(int userId);

	public List<User> getAllUser();

	public User setUserToHotelerFromUserId(int userId);

	public User updateUserData(int userId, SignUpRequest request);

}
