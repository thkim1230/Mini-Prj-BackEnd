package com.example.mini_rt.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
public class ReviewVO {
    private int reviewId;
    private String memberId;
    private String restaurantId;
    private String reviewTitle;
    private String reviewContent;
    private String reviewImage;
    private Date reviewDate;
    private double rating;
}
