package com.example.mini_rt.dao;

import com.example.mini_rt.common.Common;
import com.example.mini_rt.vo.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;


    // 매장 상세 정보 불러오기
    public List<RestaurantInfoVO> infoSelect(RestaurantVO restaurantVO){
        List<RestaurantInfoVO> list = new ArrayList<>();
        try{
            String sql = "SELECT RESTAURANT_NOTICE,RESTAURANT_PHONE,RESTAURANT_INTRODUCE,RESTAURANT_HOURS,RESTAURANT_ADDR FROM RESTAURANT_INFO WHERE RESTAURANT_ID = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,restaurantVO.getRestaurantId());
            rs = pStmt.executeQuery();
            while (rs.next()){
                String notice = rs.getString("RESTAURANT_NOTICE");
                String phone = rs.getString("RESTAURANT_PHONE");
                String introduce = rs.getString("RESTAURANT_INTRODUCE");
                String hours = rs.getString("RESTAURANT_HOURS");
                String addr = rs.getString("RESTAURANT_ADDR");

                RestaurantInfoVO vo = new RestaurantInfoVO();
                vo.setRestaurantNotice(notice);
                vo.setRestaurantPhone(phone);
                vo.setRestaurantIntroduce(introduce);
                vo.setRestaurantHours(hours);
                vo.setRestaurantAddr(addr);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // 상단 고정 매장 정보
    public List<JoinVO> rtSelect(RestaurantVO restaurantVO){
        List<JoinVO> list = new ArrayList<>();
        try{
            String sql = "SELECT RESTAURANT_NAME,RESTAURANT_PHONE,RESTAURANT_ADDR,TRUNC(AVG(RATING),1) FROM RESTAURANT JOIN RESTAURANT_INFO ON RESTAURANT.RESTAURANT_ID = RESTAURANT_INFO.RESTAURANT_ID JOIN REVIEW ON RESTAURANT.RESTAURANT_ID = REVIEW.RESTAURANT_ID WHERE RESTAURANT.RESTAURANT_ID =? GROUP BY RESTAURANT_NAME,RESTAURANT_PHONE,RESTAURANT_ADDR";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, restaurantVO.getRestaurantId());
            rs = pStmt.executeQuery();
            while (rs.next()){
                String name = rs.getString("RESTAURANT_NAME");
                String phone = rs.getString("RESTAURANT_PHONE");
                String addr = rs.getString("RESTAURANT_ADDR");
                double avgRating = rs.getDouble("TRUNC(AVG(RATING),1)");
                JoinVO vo = new JoinVO();
                vo.setName(name);
                vo.setPhone(phone);
                vo.setAddr(addr);
                vo.setAvgRating(avgRating);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 메뉴 정보 불러오기
    public List<RestMenuVO> menuSelect(RestaurantVO restaurantVO){
        List<RestMenuVO> list = new ArrayList<>();

        try{
            String sql ="SELECT MENU_NAME, MENU_PRICE, MENU_DESC FROM R_MENU WHERE RESTAURANT_ID = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, restaurantVO.getRestaurantId());
            rs = pStmt.executeQuery();
            while (rs.next()){
                String name = rs.getString("MENU_NAME");
                int price = rs.getInt("MENU_PRICE");
                String desc = rs.getString("MENU_DESC");
                RestMenuVO vo = new RestMenuVO();
                vo.setMenuName(name);
                vo.setMenuPrice(price);
                vo.setMenuDesc(desc);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }


    // 리뷰 정보 불러오기
    public List<ReviewVO> reviewSelect(RestaurantVO restaurantVO){
        List<ReviewVO> list = new ArrayList<>();

        try{
            String sql ="SELECT REVIEW_TITLE,REVIEW_CONTENT,RATING,REVIEW_DATE FROM REVIEW WHERE RESTAURANT_ID = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, restaurantVO.getRestaurantId());
            rs = pStmt.executeQuery();
            while (rs.next()){
                String title = rs.getString("REVIEW_TITLE");
                String content = rs.getString("REVIEW_CONTENT");
                double reviewRating = rs.getDouble("RATING");
                Date reviewDate = rs.getDate("REVIEW_DATE");

                ReviewVO vo = new ReviewVO();
                vo.setReviewTitle(title);
                vo.setReviewContent(content);
                vo.setReviewRating(reviewRating);
                vo.setReviewDate(reviewDate);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    // 매장 아이디 값만 불러오기
    public List<String> restIdList (){
        List<String> list = new ArrayList<>();

        try{
            String sql ="SELECT RESTAURANT_ID FROM RESTAURANT";
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                String id = rs.getString("RESTAURANT_ID");
                list.add(id);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    // 매장 리스트
    public List<JoinVO> restList (RestaurantVO restaurantVO){
        List<JoinVO> list = new ArrayList<>();

        try{
            String sql = "SELECT RESTAURANT_NAME,RESTAURANT_ADDR,TRUNC(AVG(RATING),1),COUNT(REVIEW_ID) FROM RESTAURANT JOIN RESTAURANT_INFO ON RESTAURANT.RESTAURANT_ID = RESTAURANT_INFO.RESTAURANT_ID JOIN REVIEW ON RESTAURANT.RESTAURANT_ID = REVIEW.RESTAURANT_ID WHERE RESTAURANT.RESTAURANT_ID =? GROUP BY RESTAURANT_NAME,RESTAURANT_PHONE,RESTAURANT_ADDR";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, restaurantVO.getRestaurantId());
            rs = pStmt.executeQuery();
            while (rs.next()){
                String name = rs.getString("RESTAURANT_NAME");
                String addr = rs.getString("RESTAURANT_ADDR");
                double avgRating = rs.getDouble("TRUNC(AVG(RATING),1)");
                int reviewCount = rs.getInt("COUNT(REVIEW_ID)");

                JoinVO vo = new JoinVO();
                vo.setName(name);
                vo.setAddr(addr);
                vo.setAvgRating(avgRating);
                vo.setReviewCount(reviewCount);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
