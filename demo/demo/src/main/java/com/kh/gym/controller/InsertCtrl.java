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
@RequestMapping("/insert")
public class InsertCtrl {
    @GetMapping("/sales")
    public String insertSalesFrom(Model model) {
        model.addAttribute("sales", new SalesVO());
        return "thymeleafGym/salesInsertView";
    }

    @PostMapping("/sales")
    public String saveSales() {
        SalesDAO dao = new SalesDAO();
        dao.salesInsert();
        return "thymeleafGym/salesInsertRes";
    }
}
