package com.example.mini_rt.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RestMenuVO {
    private String restId;
    private String menuName;
    private int menuPrice;
    private String menuDesc;
    private String menuImgFileName;
}
