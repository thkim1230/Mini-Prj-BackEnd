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
    public String selectSales1(Model model) {
        SalesDAO dao = new SalesDAO();
        List<SalesVO> dailySalSel = dao.dailySalSel();
        model.addAttribute("dailySalSel", dailySalSel);
        return "thymeleafGym/dailySalSelView";
    }
    @GetMapping("/monthlySalSel")
    public String selectSales2(Model model) {
        SalesDAO dao = new SalesDAO();
        List<SalesVO> monthlySalSel = dao.monthlySalSel();
        model.addAttribute("monthlySalSel", monthlySalSel);
        return "thymeleafGym/monthlySalSelView";
    }
    @GetMapping("/annualSalSel")
    public String selectSales3(Model model) {
        SalesDAO dao = new SalesDAO();
        List<SalesVO> annualSalSel = dao.annualSalSel();
        model.addAttribute("annualSalSel", annualSalSel);
        return "thymeleafGym/annualSalSelView";
    }
    @GetMapping("/somDaySalSel")
    public String selectSales4(Model model) {
        SalesDAO dao = new SalesDAO();
        List<SalesVO> somDaySalSel = dao.somDaySalSel();
        model.addAttribute("somDaySalSel", somDaySalSel);
        return "thymeleafGym/somDaySalSelView";
    }
}
