package com.rafa.sevenTao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.rafa.sevenTao.config.JwtProvider;
import com.rafa.sevenTao.config.UserDetailService;
import com.rafa.sevenTao.model.USER_ROLE;
import com.rafa.sevenTao.request.UpdateProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rafa.sevenTao.model.Hotel;
import com.rafa.sevenTao.model.Users;
import com.rafa.sevenTao.repository.UserRepository;
import com.rafa.sevenTao.request.SignUpRequest;

@Service
public class UserServiceImp implements UserService {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    List<Users> allUsersData = new CopyOnWriteArrayList<>();

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserDetailService userDetailService;

    @Override
    public Users adduser(SignUpRequest request) {
        Users newUser = new Users();
        newUser.setPassword(encoder.encode(request.getPassword()));
        newUser.setLastName(request.getLastName());
        newUser.setFirstName(request.getFirstName());
        newUser.setEmail(request.getEmail());
        newUser.setPhoneNum(request.getPhoneNum());

        userRepository.save(newUser);
        System.out.println(newUser.toString());
        return newUser;
    }

    @Override
    public void deleteUser(Users user) {

        userRepository.delete(user);
        System.out.println("deleted user" + user);

    }

    @Override
    public Users findUser(int userId) {
        Users user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new NullPointerException();
        }
        return user;
    }

    @Override
    public List<Users> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Users setUserToHotelerFromUserId(int userId) {
        Users hotelUser = findUser(userId);
        hotelUser.setROLE(USER_ROLE.ROLE_HOTELER);

        return userRepository.save(hotelUser);
    }

    @Override
    public Users updateUserData(Users user, UpdateProfileRequest request) {
        user.setAccount(request.getAccount());
        user.setLastName(request.getLastName());
        if(request.getPhoto()!=null)
            user.setPhoto(request.getPhoto());
        if (request.getFirstName() != null)
            user.setFirstName(request.getFirstName());
        if (request.getNickName() != null)
            user.setNickName(request.getNickName());
        if (request.getSex() != null)
            user.setSex(request.getSex());
        if (request.getCustomSex() != null)
            user.setCustomSex(request.getCustomSex());

        if (request.getPhoneNum() != null)

            user.setPhoneNum(request.getPhoneNum());
        if (request.getAddress() != null)
            user.setAddress(request.getAddress());

        return userRepository.save(user);
    }

    @Override
    public List<Hotel> getFavoriteHotels(Users user) {
         return user.getFavoriteHotels();
    }

    @Override
    public Users findUserByJwt(String jwt) {
        String userName = jwtProvider.findUserNameFromJwt(jwt);
        return userDetailService.findUserByUserNameFromAccountOrEmail(userName);
    }
}
