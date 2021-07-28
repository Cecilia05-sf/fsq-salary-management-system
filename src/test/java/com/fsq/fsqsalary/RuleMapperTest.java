package com.fsq.fsqsalary;

import com.fsq.fsqsalary.dao.RuleDOMapper;
import com.fsq.fsqsalary.po.RuleDO;
import com.fsq.fsqsalary.po.RuleQuery;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        RuleQuery query = RuleQuery.builder().ruleType("housing").build();
        List<RuleDO> result = ruleDOMapper.queryPage(query);
        Assert.assertTrue(result.size() != 0);
    }

}
