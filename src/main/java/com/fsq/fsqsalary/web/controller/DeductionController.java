package com.fsq.fsqsalary.web.controller;

import com.fsq.fsqsalary.dao.DeductionInfoDOMapper;
import com.fsq.fsqsalary.po.DeductionInfoDO;
import com.fsq.fsqsalary.po.DeductionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class DeductionController {

    @Autowired
    DeductionInfoDOMapper deductionInfoDOMapper;

    @RequestMapping("/deduction")
    public String showDeductionInfo(Model model){
        DeductionQuery query = DeductionQuery.builder().build();
        List<DeductionInfoDO> all = deductionInfoDOMapper.queryPage(query);
        model.addAttribute("all",all);
        return "deductionInfo";
    }

    @RequestMapping("deduction/toAdd")
    public String toAdd(){
        return "addDeduction";
    }

    @RequestMapping("/deduction/add")
    public String add(DeductionInfoDO deductionInfoDO){

        deductionInfoDOMapper.insertSelective(deductionInfoDO);
        return "redirect:/deduction";
    }

    @RequestMapping("/deduction/del/{deductionId}")
    public String del(@PathVariable Integer deductionId){
        deductionInfoDOMapper.deleteByPrimaryKey(deductionId);
        return "redirect:/deduction";
    }

    @RequestMapping("/deduction/edit/{deductionId}")
    public String edit(@PathVariable Integer deductionId ,Model model){
        DeductionInfoDO deductionInfoDO = deductionInfoDOMapper.selectByPrimaryKey(deductionId);
        model.addAttribute("deduction",deductionInfoDO);
        return "editDeduction";
    }
}
