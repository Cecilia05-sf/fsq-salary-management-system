package com.fsq.fsqsalary.service;

import com.fsq.fsqsalary.dao.EmployeeInfoDOMapper;
import com.fsq.fsqsalary.dao.SalaryRecordDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BankService {
    @Autowired
    private EmployeeInfoDOMapper employeeInfoDOMapper;
    @Autowired
    private SalaryRecordDOMapper salaryRecordDOMapper;

    public boolean transfer(Integer employeeId, Integer payId){
       String bankAcct = employeeInfoDOMapper.selectByPrimaryKey(employeeId).getBankAccount();
       BigDecimal finalSalary = salaryRecordDOMapper.selectByPrimaryKey(payId).getFinalSalary();
       //todo:调用银行接口
       return true;
    }
}
