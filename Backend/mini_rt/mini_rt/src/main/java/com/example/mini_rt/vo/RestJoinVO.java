package com.example.mini_rt.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class RestJoinVO {
    private String id;
    private String name;
    private String phone;
    private String addr;
    private double avgRating;
    private int reviewCount;
}
