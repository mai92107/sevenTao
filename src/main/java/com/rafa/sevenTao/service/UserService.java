package com.rafa.sevenTao.service;

import java.util.List;

import com.rafa.sevenTao.model.Hotel;
import com.rafa.sevenTao.model.Users;
import com.rafa.sevenTao.request.SignUpRequest;
import com.rafa.sevenTao.request.UpdateProfileRequest;

public interface UserService {

	public Users adduser(SignUpRequest request);

	public void deleteUser(Users user);

	public Users findUser(int userId);

	public List<Users> getAllUser();

	public Users setUserToHotelerFromUserId(int userId);

	public Users updateUserData(Users user, UpdateProfileRequest request);

	public List<Hotel> getFavoriteHotels(Users user);

	public Users findUserByJwt(String jwt);
}
