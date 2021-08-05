package com.fsq.fsqsalary.web.controller;

import com.fsq.fsqsalary.po.SalaryRecordDO;
import com.fsq.fsqsalary.service.SalaryCalcServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class SalaryCalculatorController {

    @Autowired
    SalaryCalcServiceImpl salaryCalcService;

    @RequestMapping("/try")
    public String getPreTaxSalary() {
        return "calcInput";
    }

    @RequestMapping("/calcoutput")
    public String showResult(Integer employeeId, String preTaxSalary, Model model) {

        BigDecimal salary = new BigDecimal(preTaxSalary);
        List<SalaryRecordDO> tryResult = salaryCalcService.trySalaryCalc(employeeId, salary);
        model.addAttribute("tryResult", tryResult);
        return "calcOutput";
    }
}
