package com.fsq.fsqsalary.dto;

import lombok.*;

import java.math.BigDecimal;

/**
 * @author Shiqi on 2021/8/6.
 */


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaxRuleDTO {

    private Integer taxLevel;

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
