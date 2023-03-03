package com.kh.gym.controller;

import com.kh.gym.dao.SalesDAO;
import com.kh.gym.vo.SalesVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/update")
public class UpdateCtrl {
    @GetMapping("/sales")
    public String updateSalesFrom(Model model) {
        model.addAttribute("sales", new SalesVO());
        return "thymeleafGym/salesUpdateView";
    }

    @PostMapping("/sales")
    public String updateSales() {
        SalesDAO dao = new SalesDAO();
        dao.salesUpdate();
        return "thymeleafGym/salesUpdateRes";
    }
}
