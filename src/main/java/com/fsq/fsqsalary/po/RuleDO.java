package com.fsq.fsqsalary.po;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
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

    @Override
    public String toString() {
        return  "\n个税级数=" + taxLevel +
                "\n缴纳比例=" + rate +
                "\n上限=" + rangeUpper +
                "\n下限=" + rangeLower ;
    }
}