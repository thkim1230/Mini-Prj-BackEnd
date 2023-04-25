package com.example.mini_rt.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter

public class RestaurantVO {
    private String restaurantId;
    private String memberId;
    private String restaurantName;
    private Date restaurantDate;
    private int resPos; //예약 가능한지
    private String category;
}
