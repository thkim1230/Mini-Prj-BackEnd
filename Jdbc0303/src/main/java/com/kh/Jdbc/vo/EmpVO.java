package com.kh.Jdbc.vo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;
@Getter
@Setter
@AllArgsConstructor
public class EmpVO {
    private int empNO;
    private String name;
    private String job;
    private int mgr;
    private Date date;
    private double sal;
    private double comm;
    private int deptNO;

    public EmpVO() {

    }

}
