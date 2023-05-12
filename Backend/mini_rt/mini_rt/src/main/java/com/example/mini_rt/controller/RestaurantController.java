package com.example.mini_rt.controller;


import com.example.mini_rt.dao.RestaurantDAO;
import com.example.mini_rt.vo.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000") // 이 주소를 허용해주는 코드
@RestController //컨트롤러 선언

public class RestaurantController {

    @GetMapping("/list")
    public ResponseEntity<List<RestJoinVO>> restaurantList() {
        RestaurantDAO dao = new RestaurantDAO();

        List<RestJoinVO> list = dao.restList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 상단 고정 상세 정보
    @GetMapping("/restaurant")
    public ResponseEntity<List<RestJoinVO>> joinInfo(@RequestParam String restaurantId) {
        RestaurantDAO dao = new RestaurantDAO();
        RestaurantVO vo = new RestaurantVO();
        vo.setRestaurantId(restaurantId);

        List<RestJoinVO> list = dao.rtSelect(vo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 매장 상세 정보 불러오기
    @GetMapping("/restaurant/info")
    public ResponseEntity<List<RestaurantInfoVO>> restaurantInfo(@RequestParam String restaurantId) {
        RestaurantDAO dao = new RestaurantDAO();
        RestaurantVO vo = new RestaurantVO();
        vo.setRestaurantId(restaurantId);

        List<RestaurantInfoVO> list = dao.infoSelect(vo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 메뉴 정보 불러오기
    @GetMapping("/restaurant/menu")
    public ResponseEntity<List<RestMenuVO>> restaurantMenu(@RequestParam String restaurantId) {
        RestaurantDAO dao = new RestaurantDAO();
        RestaurantVO vo = new RestaurantVO();
        vo.setRestaurantId(restaurantId);

        List<RestMenuVO> list = dao.menuSelect(vo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 리뷰 정보 불러오기
    @GetMapping("/restaurant/review")
    public ResponseEntity<List<ReviewJoinVO>> restaurantReview(@RequestParam String restaurantId) {
        RestaurantDAO dao = new RestaurantDAO();
        RestaurantVO vo = new RestaurantVO();
        vo.setRestaurantId(restaurantId);

        List<ReviewJoinVO> list = dao.reviewSelect(vo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    //리뷰 상세 정보
    @GetMapping("/review/detail")
    public ResponseEntity<List<ReviewJoinVO>> reviewDetail(@RequestParam int reviewId) {
        RestaurantDAO dao = new RestaurantDAO();
        ReviewVO vo = new ReviewVO();
        vo.setReviewId(reviewId);

        List<ReviewJoinVO> list = dao.reviewDetailSelect(vo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    // 리뷰 추가

    @PostMapping("/restaurant/add/review")
    public ResponseEntity<Boolean> addReview(@RequestBody Map<String, String> reviewData) {
        String getRestId = reviewData.get("restId");
        String getMemberId = reviewData.get("memberId");
        String getTitle = reviewData.get("title");
        String getContent = reviewData.get("content");
        Double getRating = Double.parseDouble(reviewData.get("rating"));
        String getImage = reviewData.get("image");
        RestaurantDAO dao = new RestaurantDAO();
        boolean list = dao.addReview(getRestId, getMemberId, getTitle, getContent, getRating,getImage);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //문의 추가
    @PostMapping("/restaurant/add/inquiry")
    public ResponseEntity<Boolean> addInquiry(@RequestBody Map<String, String> inquiryData) {
        String getRestId = inquiryData.get("restId");
        String getMemberId = inquiryData.get("memberId");
        String getTitle = inquiryData.get("title");
        String getContent = inquiryData.get("content");

        RestaurantDAO dao = new RestaurantDAO();
        boolean list = dao.addInquiry(getRestId, getMemberId, getTitle, getContent);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 찜 추가
    @PostMapping("/restaurant/add/restLike")
    public ResponseEntity<Boolean> addRestLike(@RequestBody Map<String, String> likeData) {
        String getRestId = likeData.get("restId");
        String getMemberId = likeData.get("memberId");

        RestaurantDAO dao = new RestaurantDAO();
        boolean list = dao.addRestLike(getRestId, getMemberId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 리뷰 공감 추가
    @PostMapping("/restaurant/add/revLike")
    public ResponseEntity<Boolean> addRevLike(@RequestBody Map<String, String> likeData) {
        int getRevId = Integer.parseInt(likeData.get("revId"));
        String getMemberId = likeData.get("memberId");

        RestaurantDAO dao = new RestaurantDAO();
        boolean list = dao.addRevLike(getRevId, getMemberId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 찜 삭제
    @PostMapping("/restaurant/del/restLike")
    public ResponseEntity<Boolean> delRestLike(@RequestBody Map<String, String> delData) {
        String getRestId = delData.get("restId");
        String getMemberId = delData.get("memberId");

        RestaurantDAO dao = new RestaurantDAO();
        boolean list = dao.delRestLike(getRestId, getMemberId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    // 리뷰 공감 삭제

    @PostMapping("/restaurant/del/revLike")
    public ResponseEntity<Boolean> delRevLike(@RequestBody Map<String, String> delData) {
        int getRevId = Integer.parseInt(delData.get("revId"));
        String getMemberId = delData.get("memberId");

        RestaurantDAO dao = new RestaurantDAO();
        boolean list = dao.delRevLike(getRevId, getMemberId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    // 찜 리스트 조회

    @GetMapping("/restaurant/liked")
    public ResponseEntity<List<RestLikedVO>> restLiked(@RequestParam String memberId) {
        RestaurantDAO dao = new RestaurantDAO();
        MemberVO vo = new MemberVO();
        vo.setMemId(memberId);

        List<RestLikedVO> list = dao.restLikedSelect(vo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 공감 리스트 조회
    @GetMapping("/review/liked")
    public ResponseEntity<List<ReviewLikedVO>> revLiked(@RequestParam String memberId) {
        RestaurantDAO dao = new RestaurantDAO();
        MemberVO vo = new MemberVO();
        vo.setMemId(memberId);

        List<ReviewLikedVO> list = dao.revLikedSelect(vo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //예약 추가
    @PostMapping("/restaurant/add/reservation")
    public ResponseEntity<Boolean> addRes(@RequestBody Map<String, String> resData) {
        String getRestId = resData.get("restId");
        String getMemberId = resData.get("memberId");
        String getResDate = resData.get("resDate");
        String getResReq = resData.get("resReq");
        int getResSeat = Integer.parseInt(resData.get("resSeat"));
        int getResPeo = Integer.parseInt(resData.get("resPeo"));

        RestaurantDAO dao = new RestaurantDAO();
        boolean list = dao.addRes(getRestId, getMemberId, getResDate, getResReq, getResSeat, getResPeo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    //좌석 조회
    @GetMapping("/reservation/seat")
    public ResponseEntity<List<ReservationVO>> resSeat(@RequestParam String date,@RequestParam String restaurantId, @RequestParam int seat) {
        RestaurantDAO dao = new RestaurantDAO();
        RestaurantVO vo = new RestaurantVO();
        vo.setRestaurantId(restaurantId);
        List<ReservationVO> list = dao.seatSelect(date,vo,seat);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
