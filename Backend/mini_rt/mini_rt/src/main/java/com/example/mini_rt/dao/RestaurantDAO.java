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
    public List<RestJoinVO> rtSelect(RestaurantVO restaurantVO){
        List<RestJoinVO> list = new ArrayList<>();
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
                RestJoinVO vo = new RestJoinVO();
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
    public List<ReviewJoinVO> reviewSelect(RestaurantVO restaurantVO){
        List<ReviewJoinVO> list = new ArrayList<>();

        try{
            String sql ="SELECT M.NICKNAME,R.REVIEW_ID,R.REVIEW_TITLE,R.REVIEW_CONTENT,R.RATING,R.REVIEW_DATE,COUNT(L.REVIEW_ID),REVIEW_IMAGE_FILE_NAME FROM REVIEW R JOIN MEMBER_INFO M ON R.MEMBER_ID = M.MEMBER_ID LEFT JOIN REVIEW_LIKE L ON R.REVIEW_ID = L.REVIEW_ID WHERE R.RESTAURANT_ID = ? GROUP BY M.NICKNAME, R.REVIEW_ID, R.REVIEW_TITLE, R.REVIEW_CONTENT, R.RATING, R.REVIEW_DATE,REVIEW_IMAGE_FILE_NAME";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, restaurantVO.getRestaurantId());
            rs = pStmt.executeQuery();
            while (rs.next()){
                String nickName = rs.getString("NICKNAME");
                int reviewId = rs.getInt("REVIEW_ID");
                String title = rs.getString("REVIEW_TITLE");
                String content = rs.getString("REVIEW_CONTENT");
                double reviewRating = rs.getDouble("RATING");
                Date reviewDate = rs.getDate("REVIEW_DATE");
                int likeCnt = rs.getInt("COUNT(L.REVIEW_ID)");
                String image = rs.getString("REVIEW_IMAGE_FILE_NAME");

                ReviewJoinVO vo = new ReviewJoinVO();
                vo.setNickName(nickName);
                vo.setReviewId(reviewId);
                vo.setReviewTitle(title);
                vo.setReviewContent(content);
                vo.setReviewRating(reviewRating);
                vo.setReviewDate(reviewDate);
                vo.setLikeCnt(likeCnt);
                vo.setReviewImage(image);
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
    // 리뷰 상세 정보
    public List<ReviewJoinVO> reviewDetailSelect(ReviewVO reviewVO){
        List<ReviewJoinVO> list = new ArrayList<>();

        try{
            String sql ="SELECT NICKNAME,REVIEW_ID,REVIEW_TITLE,REVIEW_CONTENT,RATING,REVIEW_DATE FROM REVIEW JOIN MEMBER_INFO ON REVIEW.MEMBER_ID = MEMBER_INFO.MEMBER_ID WHERE REVIEW_ID = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, reviewVO.getReviewId());
            rs = pStmt.executeQuery();
            while (rs.next()){
                String nickName = rs.getString("NICKNAME");
                int reviewId = rs.getInt("REVIEW_ID");
                String title = rs.getString("REVIEW_TITLE");
                String content = rs.getString("REVIEW_CONTENT");
                double reviewRating = rs.getDouble("RATING");
                Date reviewDate = rs.getDate("REVIEW_DATE");

                ReviewJoinVO vo = new ReviewJoinVO();
                vo.setNickName(nickName);
                vo.setReviewId(reviewId);
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
    // 매장 리스트
    public List<RestJoinVO> restList (){
        List<RestJoinVO> list = new ArrayList<>();

        try{
            conn = Common.getConnection();
            stmt = conn.createStatement();

            String sql = "SELECT RESTAURANT.RESTAURANT_ID,RESTAURANT_NAME,RESTAURANT_ADDR,TRUNC(AVG(RATING),1),COUNT(REVIEW_ID) FROM RESTAURANT JOIN RESTAURANT_INFO ON RESTAURANT.RESTAURANT_ID = RESTAURANT_INFO.RESTAURANT_ID JOIN REVIEW ON RESTAURANT.RESTAURANT_ID = REVIEW.RESTAURANT_ID GROUP BY RESTAURANT.RESTAURANT_ID,RESTAURANT_NAME,RESTAURANT_ADDR";
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                String id = rs.getString("RESTAURANT_ID");
                String name = rs.getString("RESTAURANT_NAME");
                String addr = rs.getString("RESTAURANT_ADDR");
                double avgRating = rs.getDouble("TRUNC(AVG(RATING),1)");
                int reviewCount = rs.getInt("COUNT(REVIEW_ID)");

                RestJoinVO vo = new RestJoinVO();
                vo.setId(id);
                vo.setName(name);
                vo.setAddr(addr);
                vo.setAvgRating(avgRating);
                vo.setReviewCount(reviewCount);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // 리뷰 추가
    public boolean addReview(String restId, String memId, String title, String content, double rating,String image) {
        int result = 0;
        String sql = "INSERT INTO REVIEW(REVIEW_ID,RESTAURANT_ID,MEMBER_ID,REVIEW_TITLE,REVIEW_CONTENT,RATING,REVIEW_IMAGE_FILE_NAME) VALUES(SEQ_REVIEW_ID.NEXTVAL,?,?,?,?,?,?)";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, restId);
            pStmt.setString(2, memId);
            pStmt.setString(3, title);
            pStmt.setString(4, content);
            pStmt.setDouble(5, rating);
            pStmt.setString(6, image);

            result = pStmt.executeUpdate();

            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }
    // 문의 등록
    public boolean addInquiry(String restId, String memId, String title, String content) {
        int result = 0;
        String sql = "INSERT INTO INQUIRY(INQUIRY_ID,RESTAURANT_ID,MEMBER_ID,INQUIRY_TITLE,INQUIRY_CONTENT) VALUES(SEQ_INQUIRY_ID.NEXTVAL,?,?,?,?)";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, restId);
            pStmt.setString(2, memId);
            pStmt.setString(3, title);
            pStmt.setString(4, content);
            result = pStmt.executeUpdate();

            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }
    // 찜 등록
    public boolean addRestLike(String restId, String memId) {
        int result = 0;
        String sql = "INSERT INTO RESTAURANT_LIKE(RESTAURANT_ID,MEMBER_ID) VALUES(?,?)";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, restId);
            pStmt.setString(2, memId);
            result = pStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }
    // 리뷰 공감 추가
    public boolean addRevLike(int revId, String memId) {
        int result = 0;
        String sql = "INSERT INTO REVIEW_LIKE(REVIEW_ID,MEMBER_ID) VALUES(?,?)";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, revId);
            pStmt.setString(2, memId);
            result = pStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }
    // 찜 삭제
    public boolean delRestLike(String restId,String memId) {
        int result = 0;
        String sql = "DELETE FROM RESTAURANT_LIKE WHERE RESTAURANT_ID = ? AND MEMBER_ID = ?";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, restId);
            pStmt.setString(2, memId);

            result = pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }
    // 리뷰 공감 삭제
    public boolean delRevLike(int revId,String memId) {
        int result = 0;
        String sql = "DELETE FROM REVIEW_LIKE WHERE REVIEW_ID = ? AND MEMBER_ID = ?";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, revId);
            pStmt.setString(2, memId);

            result = pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }
    // 찜 리스트 조회
    public List<RestLikedVO> restLikedSelect(MemberVO memberVO){
        List<RestLikedVO> list = new ArrayList<>();

        try{
            String sql ="SELECT RESTAURANT_ID,MEMBER_ID FROM RESTAURANT_LIKE WHERE MEMBER_ID = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, memberVO.getMemId());
            rs = pStmt.executeQuery();
            while (rs.next()){
                String restId = rs.getString("RESTAURANT_ID");
                String memId = rs.getString("MEMBER_ID");
                RestLikedVO vo = new RestLikedVO();
                vo.setRestId(restId);
                vo.setMemId(memId);
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
    // 리뷰 공감 리스트 조회
    public List<ReviewLikedVO> revLikedSelect(MemberVO memberVO){
        List<ReviewLikedVO> list = new ArrayList<>();

        try{
            String sql ="SELECT * FROM REVIEW_LIKE WHERE MEMBER_ID = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, memberVO.getMemId());
            rs = pStmt.executeQuery();
            while (rs.next()){
                int revId = rs.getInt("REVIEW_ID");
                String memId = rs.getString("MEMBER_ID");

                ReviewLikedVO vo = new ReviewLikedVO();
                vo.setReviewId(revId);
                vo.setMemId(memId);
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
    // 예약 추가
    public boolean addRes(String restId,String memId,String resDate,String resReq,int resSeat,int resPeo) {
        int result = 0;
        String sql = "INSERT INTO RESERVATION(RESERVATION_ID,RESTAURANT_ID,MEMBER_ID,RESERVATION_DATE,RESERVATION_REQUEST,RESERVATION_SEAT,RESERVATION_PEOPLE) VALUES(SEQ_RES_ID.NEXTVAL,?,?,TO_DATE(?,'YYYY-MM-DD HH24:MI:SS'),?,?,?)";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, restId);
            pStmt.setString(2, memId);
            pStmt.setString(3, resDate);
            pStmt.setString(4, resReq);
            pStmt.setInt(5, resSeat);
            pStmt.setInt(6, resPeo);

            result = pStmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(result == 1) return true;
        else return false;
    }
    // 예약 좌석 조회
    public List<ReservationVO> seatSelect(String date, RestaurantVO restaurantVO, int seatNum){
        List<ReservationVO> list = new ArrayList<>();

        try{
            String sql ="SELECT RESERVATION_SEAT FROM RESERVATION WHERE RESERVATION_DATE = TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') AND RESTAURANT_ID = ? AND RESERVATION_SEAT = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,date);
            pStmt.setString(2,restaurantVO.getRestaurantId());
            pStmt.setInt(3,seatNum);
            rs = pStmt.executeQuery();

            while (rs.next()){
                int seat = rs.getInt("RESERVATION_SEAT");

                ReservationVO vo = new ReservationVO();
                vo.setResSeat(seat);
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
}
