package com.fsq.fsqsalary.po;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RuleDO {

    private String name;

    private Integer ruleId;

    private Integer taxLevel;

    private RuleTypeEnum ruleType;

    private BigDecimal rangeUpper;

    private BigDecimal rangeLower;

    private BigDecimal rate;

    private BigDecimal reduction;

}