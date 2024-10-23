package com.rafa.sevenTao.controller;

import com.rafa.sevenTao.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

//    @Autowired


//    @GetMapping("/")
//    public ResponseEntity<List<Users>> getAllUser() {
//        List<Users> allList = userService.getAllUser();
//        return new ResponseEntity<List<Users>>(allList, HttpStatus.OK);
//    };
}
