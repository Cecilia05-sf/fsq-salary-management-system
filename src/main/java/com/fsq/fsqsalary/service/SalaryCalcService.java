package com.fsq.fsqsalary.service;

import com.fsq.fsqsalary.po.CalcResultDTO;
import com.fsq.fsqsalary.po.SalaryRecordDO;

import java.math.BigDecimal;
import java.util.List;

public interface SalaryCalcService {
    SalaryRecordDO salaryCalcMonthly(Integer employeeId, Integer monthIndex);
    List<SalaryRecordDO> trySalaryCalc(Integer employeeId, BigDecimal preTaxSalary);
    BigDecimal housingProvidentCalc(BigDecimal preTaxSalary);
    List<BigDecimal> deductionCalc(Integer employeeId);
    BigDecimal taxableSalaryCalc(Integer employeeId, BigDecimal preTaxSalary);
    List<CalcResultDTO> taxCalc(BigDecimal calTax);


}
