package com.rafa.sevenTao.request;

import lombok.Data;

@Data
public class SignUpRequest {

	private String password;
	private String lastName;
	private String firstName;
	private String email;
	private String phoneNum;

}
