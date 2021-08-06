package com.fsq.fsqsalary.web.controller;

import com.fsq.fsqsalary.dao.RuleDOMapper;
import com.fsq.fsqsalary.po.RuleDO;
import com.fsq.fsqsalary.po.RuleQuery;
import com.fsq.fsqsalary.po.RuleTypeEnum;
import com.fsq.fsqsalary.service.RuleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class RuleController {
    @Autowired
    RuleDOMapper ruleDOMapper;
    @Autowired
    RuleServiceImpl ruleService;

    @RequestMapping("/rule")
    public String getRule(Model model) {
        RuleQuery query1 = RuleQuery.builder().ruleType(RuleTypeEnum.TAX.getType()).build();
        List<RuleDO> tax = ruleDOMapper.queryPage(query1);
        model.addAttribute("tax", tax);

        List<RuleDO> social = ruleService.getSocialRule();
        model.addAttribute("social", social);

        RuleDO housing = ruleDOMapper.selectByRuleType(RuleTypeEnum.HOUSING.getType());
        model.addAttribute("housing", housing);
        return "displayRule";
    }
}
