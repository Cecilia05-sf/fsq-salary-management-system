package com.fsq.fsqsalary.web.controller;

import com.fsq.fsqsalary.dao.EmployeeInfoDOMapper;
import com.fsq.fsqsalary.po.EmployeeInfoDO;
import com.fsq.fsqsalary.po.EmployeeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
public class IndexController {

    @Autowired
    EmployeeInfoDOMapper employeeInfoDOMapper;

    @RequestMapping("/")
    public String homePage() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

}