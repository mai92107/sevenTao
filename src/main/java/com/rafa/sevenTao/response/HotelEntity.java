package com.rafa.sevenTao.response;

import lombok.Data;

import java.util.List;

@Data
public class HotelEntity {
    private List<HotelsResponse> hotels;
    private List<HotelsResponse> hotHotels;
    private List<HotelsResponse> newHotels;
    private List<HotelsResponse> bestHotels;
}
