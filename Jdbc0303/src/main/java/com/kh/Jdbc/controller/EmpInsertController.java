package com.kh.Jdbc.controller;
import com.kh.Jdbc.dao.EmpDAO;
import com.kh.Jdbc.vo.EmpVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/insert")
public class EmpInsertController {
    @GetMapping("/emp")
    public String insertEmpFrom(Model model) { // view에 데이터를 전달하기 위해 사용
        model.addAttribute("employees", new EmpVO());
        return "thymeleafEx/empInsertView";
    }

    @PostMapping("/emp")
    public String saveEmployee(@ModelAttribute("employees") EmpVO empVO) {
        EmpDAO dao = new EmpDAO();
        dao.empInsert(empVO);
        return "thymeleafEx/empInsertRes";
    }
}