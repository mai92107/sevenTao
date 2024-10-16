package com.rafa.sevenTao.request;

import lombok.Data;

@Data
public class SignUpRequest {

	private String account;
	private String password;
	private String lastName;
	private String firstName;
	private String email;
	private String nickName;
	private String sex;
	private String phoneNum;
	private String address;

}
