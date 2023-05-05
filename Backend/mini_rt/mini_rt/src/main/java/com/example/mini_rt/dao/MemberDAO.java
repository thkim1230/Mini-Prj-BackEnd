package com.example.mini_rt.dao;

import com.example.mini_rt.common.Common;
import com.example.mini_rt.vo.MemberVO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


    public class MemberDAO {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        // 로그인 체크
        public boolean loginCheck(String id, String pwd) {
            try {
                conn = Common.getConnection();
                stmt = conn.createStatement(); // Statement 객체 얻기
                String sql ="SELECT * FROM MEMBER_INFO WHERE MEMBER_ID = "+ "'" + id + "'";
                rs = stmt.executeQuery(sql);

                while(rs.next()) { // 읽은 데이타가 있으면 true
                    String sqlId = rs.getString("MEMBER_ID"); // 쿼리문 수행 결과에서 ID값을 가져 옴
                    String sqlPwd = rs.getString("PASSWORD");
//                    System.out.println("ID : " + sqlId);
//                    System.out.println("PWD : " + sqlPwd);
                    if(id.equals(sqlId) && pwd.equals(sqlPwd)) {
                        Common.close(rs);
                        Common.close(stmt);
                        Common.close(conn);
                        return true;
                    }
                }
                Common.close(rs);
                Common.close(stmt);
                Common.close(conn);
            } catch(Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        public List<MemberVO> memberSelect(String id) {
            List<MemberVO> list = new ArrayList<>();
            try {
                conn = Common.getConnection(); //연결
                stmt = conn.createStatement(); //정적인 sql 사용
                System.out.println(id);
                String sql= "SELECT * FROM MEMBER_INFO WHERE MEMBER_ID = " + "'" + id + "'";
                rs = stmt.executeQuery(sql); //
                while(rs.next()){ //읽을 행이 있으면 참
                    String memId = rs.getString("MEMBER_ID");
                    String pwd = rs.getString("PASSWORD");
                    String name = rs.getString("NAME");
                    String nickName = rs.getString("NICKNAME");
                    String eMail = rs.getString("EMAIL");
                    String phoneNum = rs.getString("PHONE_NUM");
                    String addr = rs.getString("ADDRESS");
                    Date joinDate = rs.getDate("JOIN_DATE");
                    String imgFileName = rs.getString("IMAGE_FILE_NAME");

                    MemberVO vo = new MemberVO();
                    vo.setMemId(memId);
                    vo.setPwd(pwd);
                    vo.setName(name);
                    vo.setNickName(nickName);
                    vo.setEMail(eMail);
                    vo.setPhoneNum(phoneNum);
                    vo.setAddr(addr);
                    vo.setJoinDate(joinDate);
                    vo.setImgFileName(imgFileName);
                    list.add(vo);

                }
                Common.close(rs); // 연결과 역순으로 해제
                Common.close(stmt);
                Common.close(conn);
            }catch(Exception e){
                e.printStackTrace();

            }
            return list;
        }
}
