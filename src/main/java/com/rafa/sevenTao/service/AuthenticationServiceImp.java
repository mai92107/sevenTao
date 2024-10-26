package com.rafa.sevenTao.service;

import com.rafa.sevenTao.config.JwtProvider;
import com.rafa.sevenTao.config.UserDetailService;
import com.rafa.sevenTao.model.USER_ROLE;
import com.rafa.sevenTao.model.Users;
import com.rafa.sevenTao.repository.UserRepository;
import com.rafa.sevenTao.request.SignInRequest;
import com.rafa.sevenTao.request.SignUpRequest;
import com.rafa.sevenTao.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLClientInfoException;
import java.util.Arrays;

@Service
public class AuthenticationServiceImp implements AuthenticationService {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


    @Autowired
    UserDetailService userDetailService;


    @Override
    public LoginResponse verifyUser(SignInRequest request) {

        UserDetails userDetails = userDetailService.loadUserByUsername(request.getUserName());
        if (userDetails == null) {
            throw new BadCredentialsException("使用者帳號錯誤");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassWord(), userDetails.getAuthorities());

        if (!authentication.isAuthenticated())
            throw new BadCredentialsException("使用者 " + request.getUserName() + " 密碼錯誤");


        USER_ROLE role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(USER_ROLE::valueOf)
                .findFirst()
                .orElse(USER_ROLE.ROLE_CUSTOMER);

        LoginResponse response = new LoginResponse();
        response.setJwt(jwtProvider.generateToken(authentication));
        response.setRole(role);
        response.setUsername(userDetails.getUsername());
        return response;
    }


    @Override
    public LoginResponse signUp(SignUpRequest request)throws Exception {

        Users userCheck = userDetailService.findUserByUserNameFromAccountOrEmail(request.getEmail());
        if(userCheck!=null)
            throw new SQLClientInfoException();

        Users user = userService.adduser(request);
        userRepository.save(user);
        UserDetails realUser = userDetailService.loadUserByUsername(request.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword(), realUser.getAuthorities());
        LoginResponse response = new LoginResponse();
        response.setJwt(jwtProvider.generateToken(authentication));
        response.setRole(user.getROLE());
        response.setUsername(user.getEmail());
        return  response;
    }
}
