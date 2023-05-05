package com.example.mini_rt.controller;

import com.example.mini_rt.dao.SearchDAO;
import com.example.mini_rt.vo.RestListVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class SearchController {
    @Autowired
    SearchDAO dao = new SearchDAO();
    @PostMapping("/restaurantList")
    public ResponseEntity<List<RestListVO>> restList(@RequestBody Restaurant rst){


//        Restaurant rst = new Restaurant();
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, Object> map = mapper.convertValue(restList, Map.class);
//
//        rst.setRegion((Map<String, String[]>) map.get("region"));
//        rst.setCategory((String[]) map.get("category"));
//        rst.setPrice((String[]) map.get("price"));
//        rst.setRating((String[]) map.get("rating"));

        List<RestListVO> list = dao.searchRest(
                rst.getRegion(),
                rst.getCategory(),
                rst.getPrice(),
                rst.getRating()
        );
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Getter
    @Setter
    private static class Restaurant {
        private Map<String, String[]> region;
        private String[] category;
        private String[] price;
        private String rating;
    }

}