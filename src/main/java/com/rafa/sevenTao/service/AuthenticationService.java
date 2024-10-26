package com.rafa.sevenTao.service;


import com.rafa.sevenTao.model.Users;
import com.rafa.sevenTao.request.SignInRequest;
import com.rafa.sevenTao.request.SignUpRequest;
import com.rafa.sevenTao.response.LoginResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


public interface AuthenticationService {

    public LoginResponse verifyUser(SignInRequest request);

    public LoginResponse signUp(SignUpRequest request)throws Exception;

}
