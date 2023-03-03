package com.kh.gym.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
@AllArgsConstructor
public class SalesVO {
    private int order_No;
    private int mem_ID;
    private String mName;
    private String purchase;
    private int sales;
    private Date p_Date;
    private String p_DateStr;

    public SalesVO() {

    }
}
