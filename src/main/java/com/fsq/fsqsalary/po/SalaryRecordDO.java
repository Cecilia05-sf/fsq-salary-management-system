package com.fsq.fsqsalary.po;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SalaryRecordDO {
    private Integer payId;

    private Integer employeeId;

    private Date payDate;

    private Byte status;

    private BigDecimal preTaxSalary;

    private BigDecimal finalSalary;

    private BigDecimal tax;

    private BigDecimal deduction;

    private BigDecimal pensionInsur;

    private BigDecimal TaxRuleConvertermedicareInsur;

    private BigDecimal housingProvident;

    private BigDecimal unemployInsur;

    private BigDecimal MedicareInsur;

    private String rule;

    private String payMonth;


}