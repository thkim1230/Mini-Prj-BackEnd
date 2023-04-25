package com.example.mini_rt.vo;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class RestaurantInfoVO {
    private String restaurantId;
    private String restaurantImage;
    private String restaurantPhone;
    private String restaurantAddr;
    private String restaurantNotice;
    private String restaurantHours;
    private String restaurantIntroduce;
}
