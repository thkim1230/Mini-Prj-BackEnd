package com.example.mini_rt.contoller;


import com.example.mini_rt.dao.RestaurantDAO;
import com.example.mini_rt.vo.RestaurantInfoVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController

public class RestaurantController {

    @GetMapping("/restaurant/info/{restaurantId}")
    public ResponseEntity<List<RestaurantInfoVO>> restaurantInfo(@PathVariable String restaurantId){
        RestaurantDAO dao = new RestaurantDAO();
        RestaurantInfoVO vo = new RestaurantInfoVO();
        vo.setRestaurantId(restaurantId);
        List<RestaurantInfoVO> list = dao.infoSelect(vo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
