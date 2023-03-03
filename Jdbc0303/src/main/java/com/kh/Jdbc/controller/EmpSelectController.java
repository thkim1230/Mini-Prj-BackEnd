package com.kh.Jdbc.controller;
import com.kh.Jdbc.dao.EmpDAO;
import com.kh.Jdbc.vo.EmpVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
@Controller
@RequestMapping("/select")
public class EmpSelectController {
    @GetMapping("/emp")
    public String empSelect(Model model) { // view에 데이터를 전달하기 위해 사용
        EmpDAO dao = new EmpDAO();
        List<EmpVO> employees = dao.empSelect();
        System.out.println("EMP SELECT Request !!!!!");
        model.addAttribute("employees",employees);
        return "thymeleafEx/empSelectView";
    }
}
