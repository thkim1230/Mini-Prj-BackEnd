package com.kh.gym.controller;

import com.kh.gym.dao.SalesDAO;
import com.kh.gym.vo.SalesVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

    @Controller
    @RequestMapping("/delete")
    public class DeleteCtrl {
        @GetMapping("/sales")
        public String deleteSalesFrom(Model model) {
            model.addAttribute("sales", new SalesVO());
            return "thymeleafGym/salesDeleteView";
        }

        @PostMapping("/sales")
        public String deleteSales() {
            SalesDAO dao = new SalesDAO();
            dao.salesDelete();
            return "thymeleafGym/salesDeleteRes";
        }
}
