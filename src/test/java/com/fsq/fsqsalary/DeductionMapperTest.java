package com.fsq.fsqsalary;

import com.fsq.fsqsalary.dao.DeductionInfoDOMapper;
import com.fsq.fsqsalary.po.*;
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
public class DeductionMapperTest {
    @Autowired
    private DeductionInfoDOMapper deductionInfoDOMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void insertDeduction(){
        deductionInfoDOMapper.insert(DeductionInfoDO.builder().employeeId(1).childDeduction(1).illnessDeduction(BigDecimal.ZERO).proStudyDeduction(0).housingLoanDeduction(0).housingRentDeduction(1).parentDeduction(2).build());
    }

    @Test
    void queryDeduction() {
        DeductionQuery query = DeductionQuery.builder().employeeId(1).build();
        List<DeductionInfoDO> result = deductionInfoDOMapper.queryPage(query);
        int a = result.size();
        Assert.assertTrue(a == 7);
    }
}
