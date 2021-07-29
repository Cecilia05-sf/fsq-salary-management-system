package com.fsq.fsqsalary;

import com.fsq.fsqsalary.po.RuleDO;
import com.fsq.fsqsalary.service.RuleService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleServiceTest {

    @Autowired
    private RuleService ruleService;
    private RuleDO ruleDO;

    @Test
    void contextLoads() {
    }
    @Test
    void match() {
        ruleDO = ruleService.matchRule(new BigDecimal("965000.85"));
        Assert.assertNotNull(ruleDO);
    }
}