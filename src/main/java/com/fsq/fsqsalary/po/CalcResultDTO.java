package com.fsq.fsqsalary.po;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CalcResultDTO {
    private BigDecimal taxPreMonth;
    private RuleDO ruleDO;
}
