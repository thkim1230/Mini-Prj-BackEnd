package com.example.mini_rt.dao;

import com.example.mini_rt.common.Common;
import com.example.mini_rt.vo.RestListVO;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDAO {

    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;
    public List<RestListVO> filterRest(Map<String, String[]> region, String[] cat, String[] price, String rat){
        List<RestListVO> list = new ArrayList<>();

        String sql = "SELECT R.RESTAURANT_ID, RESTAURANT_NAME, RESTAURANT_CATEGORY , RESTAURANT_PHONE, RESTAURANT_ADDR, RESERVATION_POSSIBILITY ,TRUNC(AVG(RATING),1) AS RATING, COUNT(REVIEW_ID) AS REVIEWS" +
                " FROM RESTAURANT R JOIN RESTAURANT_INFO RI ON R.RESTAURANT_ID = RI.RESTAURANT_ID" +
                " LEFT JOIN REVIEW RV ON R.RESTAURANT_ID = RV.RESTAURANT_ID" +
                " WHERE R.RESTAURANT_ID IN (SELECT DISTINCT R.RESTAURANT_ID FROM RESTAURANT R" +
                " JOIN RESTAURANT_INFO RI ON R.RESTAURANT_ID = RI.RESTAURANT_ID" +
                " JOIN R_MENU RM ON RI.RESTAURANT_ID = RM.RESTAURANT_ID WHERE";
        if(region != null) {
            //키가 여러 개일 경우, 쿼리문에서 괄호 포함 때문에 경우의 수를 나눠야해서 리스트로 만든 후 인덱스 출력
            List<String> regList = new ArrayList<>(region.keySet());

            for(int i = 0; i < regList.size(); i++) {
                String state = regList.get(i);
                Object value = region.get(state);
                if (value instanceof String[]) {
                    String[] reg = region.get(state);
                    if (reg != null) {
                        for (int j = 0; j < reg.length; j++) {
                            if (regList.size() == 1) {
                                if (reg.length != 1) {
                                    if (j == 0) sql = sql + " (RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%'";
                                    else if (j == reg.length - 1)
                                        sql = sql + " OR RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%')";
                                    else sql = sql + " OR RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%'";
                                } else sql = sql + " (RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%')";
                            } else if (regList.size() != 1) {
                                int index = regList.indexOf(state);
                                if (index == 0) {
                                    if (reg.length != 1) {
                                        if (j == 0)
                                            sql = sql + " (RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%'";
                                        else sql = sql + " OR RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%'";
                                    } else sql = sql + " (RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%'";
                                } else if (index == regList.size() - 1) {
                                    if (reg.length != 1) {
                                        if (j == reg.length - 1)
                                            sql = sql + " OR RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%')";
                                        else sql = sql + " OR RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%'";
                                    } else sql = sql + " OR RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%')";
                                } else {
                                    sql = sql + " OR RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%'";
                                }

                            }
                        }
                    }
                }

//                if(state.equals("세종특별자치시")) {
//                    if(regList.size() == 1) {
//
//                    }
//                }
            }
        }


//        if(reg != null) {
//            for (int i = 0; i < reg.length; i++) {
//                if(reg.length != 1) {
//                    if (i == 0) sql = sql + " (RESTAURANT_ADDR LIKE '%" + reg[i] + "%'";
//                    else if (i == reg.length - 1) sql = sql + " OR RESTAURANT_ADDR LIKE '%" + reg[i] + "%')";
//                    else sql = sql + " OR RESTAURANT_ADDR LIKE '%" + reg[i] + "%'";
//                } else sql = sql + " (RESTAURANT_ADDR LIKE '%" + reg[i] + "%')";
//            }
//        }
        // 앞 배열이 비어있지 않으면 or 붙이고 아니면 처음에 안 붙이고 카테고리 바뀔 때 AND 사용
        if(cat != null) {
            if (region != null) {
                for (int i = 0; i < cat.length; i++) {
                    if(cat.length != 1) {
                        if (i == 0) sql = sql + " AND (RESTAURANT_CATEGORY LIKE '%" + cat[i] + "%'";
                        else if (i == cat.length - 1) sql = sql + " OR RESTAURANT_CATEGORY LIKE '%" + cat[i] + "%')";
                        else sql = sql + " OR RESTAURANT_CATEGORY LIKE '%" + cat[i] + "%'";
                    } else sql = sql + " AND (RESTAURANT_CATEGORY LIKE '%" + cat[i] + "%')";
                }
            } else {
                for (int i = 0; i < cat.length; i++) {
                    if(cat.length != 1) {
                        if (i == 0) sql = sql + " (RESTAURANT_CATEGORY LIKE '%" + cat[i] + "%'";
                        else if (i == cat.length - 1) sql = sql + " OR RESTAURANT_CATEGORY LIKE '%" + cat[i] + "%')";
                        else sql = sql + " OR RESTAURANT_CATEGORY LIKE '%" + cat[i] + "%'";
                    } else sql = sql + " (RESTAURANT_CATEGORY LIKE '%" + cat[i] + "%')";
                }
            }
        }
        if(price != null) {
            for (int i = 0; i < price.length; i++) {
                if (price[i].equals("1만원대")) price[i] = "10000 <= RM.MENU_PRICE AND RM.MENU_PRICE < 20000";
                else if (price[i].equals("2만원대")) price[i] = "20000 <= RM.MENU_PRICE AND RM.MENU_PRICE < 30000";
                else if (price[i].equals("3만원대")) price[i] = "30000 <= RM.MENU_PRICE AND RM.MENU_PRICE < 40000";
                else if (price[i].equals("5만원대")) price[i] = "50000 <= RM.MENU_PRICE AND RM.MENU_PRICE < 60000";
                else if (price[i].equals("10만원 이상")) price[i] = "100000 <= RM.MENU_PRICE";
            }
            if (region == null && cat == null) {
                for (int i = 0; i < price.length; i++) {
                    if (price.length != 1) {
                        if (i == 0) sql = sql + "(" + price[i];
                        else if (i == price.length - 1) sql = sql + " OR " + price[i] + ")";
                        else sql = sql + " OR " + price[i];
                    } else sql = sql + " (" + price[i] + ") ";
                }
            } else {
                for (int i = 0; i < price.length; i++) {
                    if (price.length != 1) {
                        if (i == 0) sql = sql + " AND (" + price[i];
                        else if (i == price.length - 1) sql = sql + " OR " + price[i] + ")";
                        else sql = sql + " OR " + price[i];
                    } else sql = sql + " AND (" + price[i] + ") ";
                }
            }
        }

        sql = sql + ") GROUP BY R.RESTAURANT_ID, RESTAURANT_NAME, RESTAURANT_ADDR, RESTAURANT_CATEGORY, RESERVATION_POSSIBILITY, RESTAURANT_PHONE";

        if(rat != null){



            if(rat.equals("3.0 이상")) rat = " HAVING 3.0 <= TRUNC(AVG(RATING),1)";
            else if(rat.equals("3.5 이상")) rat = " HAVING 3.5 <= TRUNC(AVG(RATING),1)";
            else if(rat.equals("4.0 이상")) rat = " HAVING 4.0 <= TRUNC(AVG(RATING),1)";
            else if(rat.equals("4.5 이상")) rat = " HAVING 4.5 <= TRUNC(AVG(RATING),1)";


            sql = sql + rat;

        }

        try{
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();

            while(rs.next()){
                String id = rs.getString("RESTAURANT_ID");
                String name = rs.getString("RESTAURANT_NAME");
                String category = rs.getString("RESTAURANT_CATEGORY");
                int reservation = rs.getInt("RESERVATION_POSSIBILITY");
                String pNum = rs.getString("RESTAURANT_PHONE");
                String addr = rs.getString("RESTAURANT_ADDR");
                double rating = rs.getDouble("RATING");
                int reviews = rs.getInt("REVIEWS");

                RestListVO vo = new RestListVO();
                vo.setRestId(id);
                vo.setRestName(name);
                vo.setAddr(addr);
                vo.setCategory(category);
                vo.setReservation(reservation);
                vo.setRestPhone(pNum);
                vo.setRating(rating);
                vo.setReviews(reviews);
                list.add(vo);

            }

        } catch (Exception e){
            e.printStackTrace();
        }

        Common.close(rs);
        Common.close(pStmt);
        Common.close(conn);

        return list;
    }

    public List<RestListVO> searchRest(String[] kw){
        List<RestListVO> list = new ArrayList<>();
        String sql = "SELECT R.RESTAURANT_ID, RESTAURANT_NAME, RESTAURANT_CATEGORY , RESTAURANT_PHONE, RESTAURANT_ADDR, RESERVATION_POSSIBILITY ,TRUNC(AVG(RATING),1) AS RATING, COUNT(REVIEW_ID) AS REVIEWS" +
                " FROM RESTAURANT R JOIN RESTAURANT_INFO RI ON R.RESTAURANT_ID = RI.RESTAURANT_ID" +
                " LEFT JOIN REVIEW RV ON R.RESTAURANT_ID = RV.RESTAURANT_ID" +
                " WHERE R.RESTAURANT_ID IN (SELECT DISTINCT R.RESTAURANT_ID FROM RESTAURANT R" +
                " JOIN RESTAURANT_INFO RI ON R.RESTAURANT_ID = RI.RESTAURANT_ID" +
                " JOIN R_MENU RM ON RI.RESTAURANT_ID = RM.RESTAURANT_ID WHERE";

        if(kw.length == 1) {
            sql = sql + "(RESTAURANT_NAME LIKE '%" + kw[0] + "%' OR RESTAURANT_CATEGORY LIKE '%" + kw[0] + "%' OR RESTAURANT_ADDR LIKE '%" + kw[0] + "%' OR MENU_NAME LIKE '%" + kw[0] + "%' OR RESTAURANT_INTRODUCE LIKE '%" + kw[0] + "%')";
        } else {
            for(int i = 0; i < kw.length; i++){
                if(i >= 1) sql = sql + " AND ";
                sql = sql + "(RESTAURANT_NAME LIKE '%" + kw[i] + "%' OR RESTAURANT_CATEGORY LIKE '%" + kw[i] + "%' OR RESTAURANT_ADDR LIKE '%" + kw[i] + "%' OR MENU_NAME LIKE '%" + kw[i] + "%' OR RESTAURANT_INTRODUCE LIKE '%" + kw[i] + "%')";
            }
        }



        sql = sql + ") GROUP BY R.RESTAURANT_ID, RESTAURANT_NAME, RESTAURANT_ADDR, RESTAURANT_CATEGORY, RESERVATION_POSSIBILITY, RESTAURANT_PHONE";

        try{
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();

            while(rs.next()){
                String id = rs.getString("RESTAURANT_ID");
                String name = rs.getString("RESTAURANT_NAME");
                String category = rs.getString("RESTAURANT_CATEGORY");
                int reservation = rs.getInt("RESERVATION_POSSIBILITY");
                String pNum = rs.getString("RESTAURANT_PHONE");
                String addr = rs.getString("RESTAURANT_ADDR");
                double rating = rs.getDouble("RATING");
                int reviews = rs.getInt("REVIEWS");

                RestListVO vo = new RestListVO();
                vo.setRestId(id);
                vo.setRestName(name);
                vo.setAddr(addr);
                vo.setCategory(category);
                vo.setReservation(reservation);
                vo.setRestPhone(pNum);
                vo.setRating(rating);
                vo.setReviews(reviews);
                list.add(vo);

            }

        } catch (Exception e){
            e.printStackTrace();
        }

        Common.close(rs);
        Common.close(pStmt);
        Common.close(conn);

        return list;
    }

    public List<RestListVO> searchAndFilter(String[] kw, Map<String, String[]> region, String[] cat, String[] price, String rat){
        List<RestListVO> list = new ArrayList<>();
        String sql = "SELECT R.RESTAURANT_ID, RESTAURANT_NAME, RESTAURANT_CATEGORY , RESTAURANT_PHONE, RESTAURANT_ADDR, RESERVATION_POSSIBILITY ,TRUNC(AVG(RATING),1) AS RATING, COUNT(REVIEW_ID) AS REVIEWS" +
                " FROM RESTAURANT R JOIN RESTAURANT_INFO RI ON R.RESTAURANT_ID = RI.RESTAURANT_ID" +
                " LEFT JOIN REVIEW RV ON R.RESTAURANT_ID = RV.RESTAURANT_ID" +
                " WHERE R.RESTAURANT_ID IN (SELECT DISTINCT R.RESTAURANT_ID FROM RESTAURANT R" +
                " JOIN RESTAURANT_INFO RI ON R.RESTAURANT_ID = RI.RESTAURANT_ID" +
                " JOIN R_MENU RM ON RI.RESTAURANT_ID = RM.RESTAURANT_ID WHERE";
        if(kw != null) {
            if (kw.length == 1) {
                sql = sql + " (RESTAURANT_NAME LIKE '%" + kw[0] + "%' OR RESTAURANT_CATEGORY LIKE '%" + kw[0] + "%' OR RESTAURANT_ADDR LIKE '%" + kw[0] + "%' OR MENU_NAME LIKE '%" + kw[0] + "%' OR RESTAURANT_INTRODUCE LIKE '%" + kw[0] + "%')";
            } else {
                for (int i = 0; i < kw.length; i++) {
                    if (i >= 1) sql = sql + " AND ";
                    sql = sql + " (RESTAURANT_NAME LIKE '%" + kw[i] + "%' OR RESTAURANT_CATEGORY LIKE '%" + kw[i] + "%' OR RESTAURANT_ADDR LIKE '%" + kw[i] + "%' OR MENU_NAME LIKE '%" + kw[i] + "%' OR RESTAURANT_INTRODUCE LIKE '%" + kw[i] + "%')";
                }
            }
        }

        if(kw.length != 0 && (!region.isEmpty() || cat.length != 0 || price.length !=0)) sql = sql + " AND ";

        List<String> regList = new ArrayList<>(region.keySet());

        if(region != null || !region.isEmpty()) {
            //키가 여러 개일 경우, 쿼리문에서 괄호 포함 때문에 경우의 수를 나눠야해서 리스트로 만든 후 인덱스 출력
            for(int i = 0; i < regList.size(); i++) {
                String state = regList.get(i);
                Object value = region.get(state);
                if (value instanceof String[]) {
                    String[] reg = region.get(state);
                    if (reg != null) {
                        for (int j = 0; j < reg.length; j++) {
                            if(state.equals("세종특별자치시")){
                                if (regList.size() == 1) {
                                    if (reg.length != 1) {
                                        if (j == 0) sql = sql + " (RESTAURANT_ADDR LIKE '%" + reg[j] + "%'";
                                        else if (j == reg.length - 1)
                                            sql = sql + " OR RESTAURANT_ADDR LIKE '%" + reg[j] + "%')";
                                        else sql = sql + " OR RESTAURANT_ADDR LIKE '%" + reg[j] + "%'";
                                    } else sql = sql + " (RESTAURANT_ADDR LIKE '%" + reg[j] + "%')";
                                } else if (regList.size() != 1) {
                                    int index = regList.indexOf(state);
                                    if (index == 0) {
                                        if (reg.length != 1) {
                                            if (j == 0)
                                                sql = sql + " (RESTAURANT_ADDR LIKE '%" +reg[j] + "%'";
                                            else sql = sql + " OR RESTAURANT_ADDR LIKE '%" + reg[j] + "%'";
                                        } else sql = sql + " (RESTAURANT_ADDR LIKE '%" + reg[j] + "%'";
                                    } else if (index == regList.size() - 1) {
                                        if (reg.length != 1) {
                                            if (j == reg.length - 1)
                                                sql = sql + " OR RESTAURANT_ADDR LIKE '%" + reg[j] + "%')";
                                            else sql = sql + " OR RESTAURANT_ADDR LIKE '%" + reg[j] + "%'";
                                        } else sql = sql + " OR RESTAURANT_ADDR LIKE '%" + reg[j] + "%')";
                                    } else {
                                        sql = sql + " OR RESTAURANT_ADDR LIKE '%" + reg[j] + "%'";
                                    }

                                }
                            }
                            if (regList.size() == 1) {
                                if (reg.length != 1) {
                                    if (j == 0) sql = sql + " (RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%'";
                                    else if (j == reg.length - 1)
                                        sql = sql + " OR RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%')";
                                    else sql = sql + " OR RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%'";
                                } else sql = sql + " (RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%')";
                            } else if (regList.size() != 1) {
                                int index = regList.indexOf(state);
                                if (index == 0) {
                                    if (reg.length != 1) {
                                        if (j == 0)
                                            sql = sql + " (RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%'";
                                        else sql = sql + " OR RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%'";
                                    } else sql = sql + " (RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%'";
                                } else if (index == regList.size() - 1) {
                                    if (reg.length != 1) {
                                        if (j == reg.length - 1)
                                            sql = sql + " OR RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%')";
                                        else sql = sql + " OR RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%'";
                                    } else sql = sql + " OR RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%')";
                                } else {
                                    sql = sql + " OR RESTAURANT_ADDR LIKE '%" + state + " " + reg[j] + "%'";
                                }

                            }
                        }
                    }
                }

            }
        }

        // 앞 배열이 비어있지 않으면 or 붙이고 아니면 처음에 안 붙이고 카테고리 바뀔 때 AND 사용
        if(cat.length != 0) {
            if (region != null || !region.isEmpty()) {
                sql = sql + " AND ";
                for (int i = 0; i < cat.length; i++) {
                    if(cat.length != 1) {
                        if (i == 0) sql = sql + "(RESTAURANT_CATEGORY LIKE '%" + cat[i] + "%'";
                        else if (i == cat.length - 1) sql = sql + " OR RESTAURANT_CATEGORY LIKE '%" + cat[i] + "%')";
                        else sql = sql + " OR RESTAURANT_CATEGORY LIKE '%" + cat[i] + "%'";
                    } else sql = sql + "(RESTAURANT_CATEGORY LIKE '%" + cat[i] + "%')";
                }
            } else {
                for (int i = 0; i < cat.length; i++) {
                    if(cat.length != 1) {
                        if (i == 0) sql = sql + " (RESTAURANT_CATEGORY LIKE '%" + cat[i] + "%'";
                        else if (i == cat.length - 1) sql = sql + " OR RESTAURANT_CATEGORY LIKE '%" + cat[i] + "%')";
                        else sql = sql + " OR RESTAURANT_CATEGORY LIKE '%" + cat[i] + "%'";
                    } else sql = sql + " (RESTAURANT_CATEGORY LIKE '%" + cat[i] + "%')";
                }
            }
        }

        if(price.length != 0) {
            for (int i = 0; i < price.length; i++) {
                if (price[i].equals("1만원대")) price[i] = "10000 <= RM.MENU_PRICE AND RM.MENU_PRICE < 20000";
                else if (price[i].equals("2만원대")) price[i] = "20000 <= RM.MENU_PRICE AND RM.MENU_PRICE < 30000";
                else if (price[i].equals("3만원대")) price[i] = "30000 <= RM.MENU_PRICE AND RM.MENU_PRICE < 40000";
                else if (price[i].equals("5만원대")) price[i] = "50000 <= RM.MENU_PRICE AND RM.MENU_PRICE < 60000";
                else if (price[i].equals("10만원 이상")) price[i] = "100000 <= RM.MENU_PRICE";
            }
            if (!region.isEmpty() && cat.length != 0) {
                for (int i = 0; i < price.length; i++) {
                    if (price.length != 1) {
                        if (i == 0) sql = sql + "(" + price[i];
                        else if (i == price.length - 1) sql = sql + " OR " + price[i] + ")";
                        else sql = sql + " OR " + price[i];
                    } else sql = sql + " (" + price[i] + ") ";
                }
            } else {
                for (int i = 0; i < price.length; i++) {
                    if (price.length != 1) {
                        if (i == 0) sql = sql + " AND (" + price[i];
                        else if (i == price.length - 1) sql = sql + " OR " + price[i] + ")";
                        else sql = sql + " OR " + price[i];
                    } else sql = sql + " AND (" + price[i] + ") ";
                }
            }
        }

        sql = sql + ") GROUP BY R.RESTAURANT_ID, RESTAURANT_NAME, RESTAURANT_ADDR, RESTAURANT_CATEGORY, RESERVATION_POSSIBILITY, RESTAURANT_PHONE";

        if(rat != null){



            if(rat.equals("3.0 이상")) rat = " HAVING 3.0 <= TRUNC(AVG(RATING),1)";
            else if(rat.equals("3.5 이상")) rat = " HAVING 3.5 <= TRUNC(AVG(RATING),1)";
            else if(rat.equals("4.0 이상")) rat = " HAVING 4.0 <= TRUNC(AVG(RATING),1)";
            else if(rat.equals("4.5 이상")) rat = " HAVING 4.5 <= TRUNC(AVG(RATING),1)";


            sql = sql + rat;

        }



        try{
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();

            while(rs.next()){
                String id = rs.getString("RESTAURANT_ID");
                String name = rs.getString("RESTAURANT_NAME");
                String category = rs.getString("RESTAURANT_CATEGORY");
                int reservation = rs.getInt("RESERVATION_POSSIBILITY");
                String pNum = rs.getString("RESTAURANT_PHONE");
                String addr = rs.getString("RESTAURANT_ADDR");
                double rating = rs.getDouble("RATING");
                int reviews = rs.getInt("REVIEWS");

                RestListVO vo = new RestListVO();
                vo.setRestId(id);
                vo.setRestName(name);
                vo.setAddr(addr);
                vo.setCategory(category);
                vo.setReservation(reservation);
                vo.setRestPhone(pNum);
                vo.setRating(rating);
                vo.setReviews(reviews);
                list.add(vo);

            }

        } catch (Exception e){
            e.printStackTrace();
        }

        Common.close(rs);
        Common.close(pStmt);
        Common.close(conn);

        return list;
    }

    public List<RestListVO> popularList(){
        List<RestListVO> list = new ArrayList<>();

        String sql = "SELECT R.RESTAURANT_ID, RESTAURANT_NAME, RESTAURANT_CATEGORY , RESTAURANT_PHONE,  RESTAURANT_ADDR, RESERVATION_POSSIBILITY ,TRUNC(AVG(RATING),1) AS RATING ,COUNT(REVIEW_ID) AS REVIEWS " +
                "FROM RESTAURANT R JOIN RESTAURANT_INFO RI ON R.RESTAURANT_ID = RI.RESTAURANT_ID " +
                "LEFT JOIN REVIEW RV ON R.RESTAURANT_ID = RV.RESTAURANT_ID " +
                "WHERE R.RESTAURANT_ID IN (SELECT DISTINCT R.RESTAURANT_ID FROM RESTAURANT R " +
                "JOIN RESTAURANT_INFO RI ON R.RESTAURANT_ID = RI.RESTAURANT_ID " +
                "JOIN R_MENU RM ON RI.RESTAURANT_ID =RM.RESTAURANT_ID) " +
                "GROUP BY R.RESTAURANT_ID, RESTAURANT_NAME, RESTAURANT_ADDR, RESTAURANT_CATEGORY, RESERVATION_POSSIBILITY, RESTAURANT_PHONE " +
                "HAVING COUNT(REVIEW_ID) >= 3 " +
                "ORDER BY RATING DESC";

        try{
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();

            while(rs.next()){
                String id = rs.getString("RESTAURANT_ID");
                String name = rs.getString("RESTAURANT_NAME");
                String category = rs.getString("RESTAURANT_CATEGORY");
                int reservation = rs.getInt("RESERVATION_POSSIBILITY");
                String pNum = rs.getString("RESTAURANT_PHONE");
                String addr = rs.getString("RESTAURANT_ADDR");
                double rating = rs.getDouble("RATING");
                int reviews = rs.getInt("REVIEWS");

                RestListVO vo = new RestListVO();
                vo.setRestId(id);
                vo.setRestName(name);
                vo.setAddr(addr);
                vo.setCategory(category);
                vo.setReservation(reservation);
                vo.setRestPhone(pNum);
                vo.setRating(rating);
                vo.setReviews(reviews);
                list.add(vo);

            }

        } catch (Exception e){
            e.printStackTrace();
        }

        Common.close(rs);
        Common.close(pStmt);
        Common.close(conn);

        return list;
    }


}