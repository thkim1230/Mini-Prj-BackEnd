package com.kh.gym.controller;

import com.kh.gym.dao.SalesDAO;
import com.kh.gym.vo.SalesVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping( "/select")
public class SelectCtrl {
    @GetMapping("/dailySalSel")
    public String selectEmp(Model model) {
        SalesDAO dao = new SalesDAO();
        List<SalesVO> dailySalSel = dao.dailySalSel();
        model.addAttribute("dailySalSel", dailySalSel);
        return "salesTl/dailySalSelView";
    }
}
