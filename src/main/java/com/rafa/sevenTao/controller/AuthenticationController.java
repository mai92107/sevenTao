package com.rafa.sevenTao.controller;

import com.rafa.sevenTao.model.Users;
import com.rafa.sevenTao.request.SignInRequest;
import com.rafa.sevenTao.request.SignUpRequest;
import com.rafa.sevenTao.response.LoginResponse;
import com.rafa.sevenTao.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping
public class AuthenticationController {

   @Autowired
   AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<Users> signUp(@RequestBody SignUpRequest request) {
        Users user = authenticationService.signUp(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    };

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signIn(@RequestBody SignInRequest request) {
        LoginResponse response =authenticationService.verifyUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    };


}
