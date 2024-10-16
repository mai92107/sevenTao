package com.rafa.sevenTao.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafa.sevenTao.model.User;
import com.rafa.sevenTao.request.SignUpRequest;
import com.rafa.sevenTao.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/member")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping("/add")
	public ResponseEntity<User> adduser(@RequestBody SignUpRequest request) {
		User user = userService.adduser(request);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	};

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteUserByEmail(@RequestBody Map<String, String> request) {
		int userId = Integer.parseInt(request.get("userId"));
		userService.deleteUserByUserId(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	};

	@GetMapping("/{userId}")
	public ResponseEntity<User> findUserByUserId(@PathVariable int userId) {
		User user = userService.findUserByUserId(userId);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	};

//
	@GetMapping("/")
	public ResponseEntity<List<User>> getAllUser() {
		List<User> allList = userService.getAllUser();
		return new ResponseEntity<List<User>>(allList, HttpStatus.OK);
	};

	@PutMapping("/{userId}")
	public ResponseEntity<User> updateUserDataByUserId(@PathVariable int userId, @RequestBody SignUpRequest request) {
		User updatedUser = userService.updateUserData(userId, request);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

}
