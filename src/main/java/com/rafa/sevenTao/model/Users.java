package com.rafa.sevenTao.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private USER_ROLE ROLE = USER_ROLE.ROLE_CUSTOMER;

    @Column(unique = true)
    private String account;

    private String password;

    @Column(nullable = false)
    private String lastName;

    private String firstName;

    @Column(unique = true)
    private String email;
    private String photo;
    private String nickName;
    private String sex;
    private String customSex;
    private String phoneNum;
    private String address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Order> orders;

    @OneToMany(mappedBy = "boss", cascade = CascadeType.REMOVE)
    private List<Hotel> myHotels;

    @JsonIgnore
    @ManyToMany(mappedBy = "likedByUsers", cascade = CascadeType.REMOVE)
    private List<Hotel> favoriteHotels;

    @Override
    public String toString() {
        return "Users{" +
                ", ROLE=" + ROLE +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", nickName='" + nickName + '\'' +
                ", sex='" + sex + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}