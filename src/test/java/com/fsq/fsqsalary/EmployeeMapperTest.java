package com.fsq.fsqsalary;

import com.fsq.fsqsalary.dao.EmployeeInfoDOMapper;
import com.fsq.fsqsalary.po.EmployeeInfoDO;
import com.fsq.fsqsalary.po.EmployeeQuery;
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

public class EmployeeMapperTest {
    @Autowired
    private EmployeeInfoDOMapper employeeInfoDOMapper;

    @Test
    void contextLoads() {

    }

    @Test
    void insertEmployee(){
        EmployeeInfoDO record = EmployeeInfoDO.builder().employeeName("Zhangsan").preTaxSalary(new BigDecimal("300000")).bankAccount("6201234567894561231").mailAddress("zs123@163.com").phoneNum("13816795467").build();
        employeeInfoDOMapper.insertSelective(record);
    }


    @Test
    void queryEmployee() {
        EmployeeQuery query = EmployeeQuery.builder().build();
        List<EmployeeInfoDO> all = employeeInfoDOMapper.queryPage(query);
        Assert.assertNotNull(all);
    }
}
