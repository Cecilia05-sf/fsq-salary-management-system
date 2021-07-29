package com.fsq.fsqsalary;

import com.fsq.fsqsalary.dao.RuleDOMapper;
import com.fsq.fsqsalary.po.RuleDO;
import com.fsq.fsqsalary.po.RuleQuery;
import com.fsq.fsqsalary.po.RuleTypeEnum;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class RuleMapperTest {

    @Autowired
    private RuleDOMapper ruleDOMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void queryRule() {
        RuleQuery query = RuleQuery.builder().ruleType(RuleTypeEnum.tax.toString()).build();
        List<RuleDO> result = ruleDOMapper.queryPage(query);
        int a=result.size();
        Assert.assertTrue( a ==7);
    }

}
