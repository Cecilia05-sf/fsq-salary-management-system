
package com.fsq.fsqsalary;

import com.fsq.fsqsalary.dao.RuleDOMapper;
import com.fsq.fsqsalary.po.RuleDO;
import com.fsq.fsqsalary.po.RuleQuery;
import com.fsq.fsqsalary.po.RuleTypeEnum;
import com.fsq.fsqsalary.po.SalaryRecordDO;
import com.fsq.fsqsalary.service.RuleService;
import com.fsq.fsqsalary.service.SalaryCalcService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class RuleMapperTest {

    @Autowired
    private RuleDOMapper ruleDOMapper;
    @Autowired
    private SalaryCalcService salaryCalcService;
    @Autowired
    private RuleService ruleService;

    @Test
    void salaryTest() {
        BigDecimal[] trySalary = salaryCalcService.TrySalaryService(1, new BigDecimal("20000"));
        Assert.assertNotNull(trySalary);
    }

    @Test
    void salaryCalTest() {
        SalaryRecordDO salaryRecordDO = new SalaryRecordDO();
        salaryRecordDO = salaryCalcService.SalaryService(1, 1);
        Assert.assertNotNull(salaryRecordDO);
    }

    @Test
    void matchRuleTest() {
        BigDecimal[]  matchResult= ruleService.MatchRule(BigDecimal.valueOf(6222.52*8));
        Assert.assertNotNull(matchResult);
    }

}