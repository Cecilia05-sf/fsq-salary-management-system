package com.fsq.fsqsalary;

import com.fsq.fsqsalary.dao.RuleDOMapper;
import com.fsq.fsqsalary.dao.SalaryRecordDOMapper;
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
public class SalaryRecordDOMapperTest {

    @Autowired
    SalaryCalcService salaryCalcService;
    @Autowired
    SalaryRecordDOMapper salaryRecordDOMapper;

    @Test
    void insertRecordTest() {
        SalaryRecordDO salaryRecordDO = new SalaryRecordDO();
        salaryRecordDO = salaryCalcService.SalaryService(1, 1);
        int i = salaryRecordDOMapper.insert(salaryRecordDO);
        Assert.assertTrue( i==1 );
    }
}
