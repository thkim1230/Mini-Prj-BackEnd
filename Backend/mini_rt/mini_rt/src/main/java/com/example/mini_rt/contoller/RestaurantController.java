package com.example.mini_rt.contoller;


import com.example.mini_rt.dao.RestaurantDAO;
import com.example.mini_rt.vo.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController

public class RestaurantController {

    @GetMapping("/restaurant/info")
    public ResponseEntity<List<RestaurantInfoVO>> restaurantInfo(@RequestParam String restaurantId){
        RestaurantDAO dao = new RestaurantDAO();
        RestaurantVO vo = new RestaurantVO();
        vo.setRestaurantId(restaurantId);

        List<RestaurantInfoVO> list = dao.infoSelect(vo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/restaurant")
    public ResponseEntity<List<JoinVO>> joinInfo (@RequestParam String restaurantId){
        RestaurantDAO dao = new RestaurantDAO();
        RestaurantVO vo = new RestaurantVO();
        vo.setRestaurantId(restaurantId);

        List<JoinVO> list = dao.rtSelect(vo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/restaurant/menu")
    public ResponseEntity<List<RestMenuVO>> restaurantMenu(@RequestParam String restaurantId){
        RestaurantDAO dao = new RestaurantDAO();
        RestaurantVO vo = new RestaurantVO();
        vo.setRestaurantId(restaurantId);

        List<RestMenuVO> list = dao.menuSelect(vo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/restaurant/review")
    public ResponseEntity<List<ReviewVO>> restaurantReview(@RequestParam String restaurantId){
        RestaurantDAO dao = new RestaurantDAO();
        RestaurantVO vo = new RestaurantVO();
        vo.setRestaurantId(restaurantId);

        List<ReviewVO> list = dao.reviewSelect(vo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


}
