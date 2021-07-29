package com.fsq.fsqsalary.service;

import com.fsq.fsqsalary.dao.RuleDOMapper;
import com.fsq.fsqsalary.po.RuleDO;
import com.fsq.fsqsalary.po.RuleQuery;
import com.fsq.fsqsalary.po.RuleTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RuleService {
    @Autowired
    private RuleDOMapper ruleDOMapper;
    private RuleDO ruleDO;

    public RuleDO matchRule(BigDecimal preTaxSalary) {
        //Todo:preTaxSalary-社保-公积金=taxableSalary
        BigDecimal taxableSalary = preTaxSalary;
        //Todo:动态配置起征点=5000元
        BigDecimal calTax = taxableSalary.subtract(new BigDecimal("5000"));
        RuleQuery query = RuleQuery.builder().ruleType(RuleTypeEnum.tax.toString()).build();
        List<RuleDO> result = ruleDOMapper.queryPage(query);
        int i = 0;
        while (true) {
            ruleDO = result.get(6-i);
            i++;
            if (calTax.compareTo(ruleDO.getRangeUpper()) <= 0) {
                break;
            }
        }
        return ruleDO;
    }
}
