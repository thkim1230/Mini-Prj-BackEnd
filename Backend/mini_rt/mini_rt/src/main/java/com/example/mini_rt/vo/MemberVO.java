package com.example.mini_rt.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter

public class MemberVO {

        private String memId;
        private String pwd;
        private String name;
        private String nickName;
        private String eMail;
        private String phoneNum;
        private String addr;
        private Date joinDate;
        private String imgFileName;
}

