package com.fsq.fsqsalary.po;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeInfoDO {
    private Integer employeeId;

    private String employeeName;

    private String bankAccount;

    private BigDecimal preTaxSalary;

    private String phoneNum;

    private String mailAddress;

}