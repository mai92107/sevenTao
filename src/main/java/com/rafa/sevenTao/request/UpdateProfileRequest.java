package com.rafa.sevenTao.request;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    String account;
    String lastName;
    String firstName;
    String email;
    String nickName;
    String sex;
    String customSex;
    String phoneNum;
    String address;
    String photo;
}
