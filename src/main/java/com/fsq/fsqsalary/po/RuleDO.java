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

    enum RuleType
    {
        Tax("tax"),Housing("housing"),Pension("pension"),Medicare("medicare"),Unemploy("unemploy");

        private final String rule;
        private RuleType(String rule)
        {
            this.rule=rule;
        }

        public String getRule()
        {
            return rule;
        }
    }



    private String name;

    private Integer ruleId;

    private Integer taxLevel;

    private String ruleType;

    private BigDecimal rangeUpper;

    private BigDecimal rangeLower;

    private BigDecimal rate;

    private BigDecimal reduction;

}