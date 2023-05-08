package com.example.mini_rt.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ReservationVO {
    private int resId;
    private String memId;
    private String  restId;
    private Date resDate;
    private Date appDate;
    private Date conDate;
    private String resReq;
    private int resSeat;
    private int resPeople;
    private String resCon;
}
