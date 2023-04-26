package com.example.mini_rt.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
public class ReviewVO {
    private String memberId;
    private String restaurantId;
    private String reviewId;
    private String reviewTitle;
    private String reviewContent;
    private String reviewImg;
    private Date reviewDate;
    private int rating;
}
