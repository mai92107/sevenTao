package com.rafa.sevenTao.response;

import com.rafa.sevenTao.model.USER_ROLE;
import lombok.Data;

@Data
public class LoginResponse {
    private String jwt;
    private USER_ROLE role;
    private String username;
}
