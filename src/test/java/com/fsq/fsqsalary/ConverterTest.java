package com.fsq.fsqsalary;

import com.fsq.fsqsalary.converter.TaxRuleConverter;
import com.fsq.fsqsalary.converter.TaxRuleConverterImpl;
import com.fsq.fsqsalary.dao.DeductionInfoDOMapper;
import com.fsq.fsqsalary.dao.RuleDOMapper;
import com.fsq.fsqsalary.dto.TaxRuleDTO;
import com.fsq.fsqsalary.po.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Shiqi on 2021/8/6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConverterTest {

    @Autowired
    RuleDOMapper ruleDOMapper;

    @Test
    void taxConverterTest() {
        List<RuleDO> ruleDOList = ruleDOMapper.queryPage(RuleQuery.builder().ruleType(RuleTypeEnum.TAX.getType()).build());
        List<TaxRuleDTO> taxRuleDTOList = TaxRuleConverter.INSTANCE.toDTOs(ruleDOList);
        Assert.assertNotNull(taxRuleDTOList);
    }

}
