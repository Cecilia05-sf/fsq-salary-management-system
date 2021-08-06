
package com.fsq.fsqsalary;

import com.fsq.fsqsalary.dao.RuleDOMapper;
import com.fsq.fsqsalary.po.RuleDO;
import com.fsq.fsqsalary.po.RuleQuery;
import com.fsq.fsqsalary.po.RuleTypeEnum;
import com.fsq.fsqsalary.service.RuleServiceImpl;
import com.fsq.fsqsalary.service.SalaryCalcServiceImpl;
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
    @Autowired
    private SalaryCalcServiceImpl salaryCalcServiceImpl;
    @Autowired
    private RuleServiceImpl ruleService;


    @Test
    void salaryCalTest() {
        List<RuleDO> social = ruleService.getSocialRule();
        Assert.assertNotNull(social);
    }


    @Test
    void ruleMapperTest(){
       RuleQuery query= RuleQuery.builder().ruleType(RuleTypeEnum.TAX.getType()).build();
      List<RuleDO> ruleDOList= ruleDOMapper.queryPage(query);
       Assert.assertNotNull(ruleDOList);
    }
}