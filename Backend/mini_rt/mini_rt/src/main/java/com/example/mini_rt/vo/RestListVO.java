package com.example.mini_rt.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestListVO {
    private String restId;
    private String restName;
    private String category;
    private String restPhone;
    private String addr;
    private int reservation;
    private double rating;
    private int reviews;
    private String imageUrl;

}