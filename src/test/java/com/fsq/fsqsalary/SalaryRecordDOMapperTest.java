package com.fsq.fsqsalary;

import com.fsq.fsqsalary.dao.RuleDOMapper;
import com.fsq.fsqsalary.dao.SalaryRecordDOMapper;
import com.fsq.fsqsalary.po.*;
import com.fsq.fsqsalary.service.RuleServiceImpl;
import com.fsq.fsqsalary.service.SalaryCalcServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SalaryRecordDOMapperTest {

    @Autowired
    SalaryCalcServiceImpl salaryCalcServiceImpl;
    @Autowired
    SalaryRecordDOMapper salaryRecordDOMapper;
    @Autowired
    RuleServiceImpl ruleService;
    @Autowired
    RuleDOMapper ruleDOMapper;

    @Test
    public void matchRule() {
        BigDecimal calTax = new BigDecimal("150000");

        //需缴税工资>0
        if (calTax.compareTo(BigDecimal.ZERO) > 0) {
            RuleQuery query = RuleQuery.builder().ruleType(RuleTypeEnum.tax.toString()).build();
            List<RuleDO> result = ruleDOMapper.queryPage(query);

            RuleDO ruleDO = new RuleDO();
            for (int i = 0; i < result.size(); i++) {

                ruleDO = result.get(i);
                if (calTax.compareTo(ruleDO.getRangeUpper()) <= 0) {
                    break;
                }
            }

            BigDecimal tax = calTax.multiply(ruleDO.getRate()).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(ruleDO.getReduction());
            Pair pair = Pair.of(ruleDO, tax);
        } else {
            Pair pair = Pair.of(null, BigDecimal.ZERO);
        }
    }

    @Test
   public void insert(){
        SalaryRecordDO salaryRecordDO= salaryCalcServiceImpl.salaryCalcMonthly(1,8);
        salaryRecordDOMapper.insert(salaryRecordDO);
        Assert.assertNotNull(salaryRecordDO);
    }

    @Test
    public void queryPage(){
        SalaryRecordQuery query = SalaryRecordQuery.builder().build();
        List<SalaryRecordDO> result =salaryRecordDOMapper.queryPage(query);
        Assert.assertNotNull(result);
    }

    @Test
    void trySalaryCalcTest() {
       List tryResult = salaryCalcServiceImpl.trySalaryCalc(2, new BigDecimal("300"));
        //RuleDO rule = JSON.parseObject("wfeewe", RuleDO.class);
        Assert.assertNotNull(tryResult);
    }

    @Test
    void updateTest(){
        SalaryRecordDO salaryRecordDO = SalaryRecordDO.builder().employeeId(1).payMonth("2021-08").build();
        byte a = 1;
        salaryRecordDO.setStatus(a); //发放状态：已发放
        salaryRecordDO.setPayDate(new Date()); //发工资时间
        int i = salaryRecordDOMapper.updateStatus(salaryRecordDO);
        Assert.assertNotNull(i);
    }


}
