package com.rafa.sevenTao.config;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.rafa.sevenTao.model.Users;
import com.rafa.sevenTao.model.USER_ROLE;
import com.rafa.sevenTao.repository.UserRepository;
import org.springframework.security.core.userdetails.User;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Users user = findUserByUserNameFromAccountOrEmail(userName);

        if (user == null) {
            throw new UsernameNotFoundException("未找到使用者: " + userName);
        }
        USER_ROLE role = user.getROLE();
        return new User(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority(role.toString())));
    }

    public Users findUserByUserNameFromAccountOrEmail(String userName) {
        if (userName.contains("@")) {
            return userRepository.findUserByEmail(userName);
        } else {
            return userRepository.findUserByAccount(userName);
        }
    }
}
