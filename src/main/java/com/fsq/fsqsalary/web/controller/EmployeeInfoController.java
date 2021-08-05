package com.fsq.fsqsalary.web.controller;

import com.fsq.fsqsalary.dao.EmployeeInfoDOMapper;
import com.fsq.fsqsalary.po.EmployeeInfoDO;
import com.fsq.fsqsalary.po.EmployeeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class EmployeeInfoController {
    @Autowired
    EmployeeInfoDOMapper employeeInfoDOMapper;

    @RequestMapping("/employee")
    public String showEmployee(Model model){
        EmployeeQuery query = EmployeeQuery.builder().build();
        List<EmployeeInfoDO> all = employeeInfoDOMapper.queryPage(query);
        model.addAttribute("all",all);
        return "employeeInfo";
    }

    @RequestMapping("employee/toAdd")
    public String toAdd(){
        return "addEmployee";
    }

    @RequestMapping("/employee/add")
    public String add(EmployeeInfoDO employeeInfoDO){
        employeeInfoDOMapper.insertSelective(employeeInfoDO);
        return "redirect:/employee";
    }

    @RequestMapping("/employee/del/{userId}")
    public String del(@PathVariable Integer userId){
        employeeInfoDOMapper.deleteByPrimaryKey(userId);
        return "redirect:/employee";
    }

    @RequestMapping("/employee/edit/{userId}")
    public String edit(@PathVariable Integer userId ,Model model){
        EmployeeInfoDO employeeInfoDO = employeeInfoDOMapper.selectByPrimaryKey(userId);
        model.addAttribute("user",employeeInfoDO);
        return "editEmployee";
    }
}
