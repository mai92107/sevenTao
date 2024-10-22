package com.rafa.sevenTao.model;

import lombok.Data;

import java.util.List;

@Data
public class HomeHotels {
    private List<Hotel> hotels;
    private List<Hotel> hotHotels;
    private List<Hotel> newHotels;
    private List<Hotel> bestHotels;
}
