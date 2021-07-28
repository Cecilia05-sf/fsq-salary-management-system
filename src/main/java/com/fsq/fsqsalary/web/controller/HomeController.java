package com.fsq.fsqsalary.web.controller;
import com.fsq.fsqsalary.dao.EmployeeInfoDOMapper;
import com.fsq.fsqsalary.dao.RuleDOMapper;
import com.fsq.fsqsalary.po.EmployeeInfoDO;
import com.fsq.fsqsalary.po.RuleDO;
import com.fsq.fsqsalary.po.RuleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/fsq")
public class HomeController {

    @Autowired
    private EmployeeInfoDOMapper employeeInfoDOMapper;

    @Autowired
    private RuleDOMapper ruleDOMapper;

    @RequestMapping("/")
    public String home(){
        return "Hello World!";
    }


    @RequestMapping("/queryRule")
    public String queryRule(){
        RuleQuery query = RuleQuery.builder().ruleType("tax").build();
        List<RuleDO> result = ruleDOMapper.queryPage(query);
        return result.get(0).toString();
    }


}
