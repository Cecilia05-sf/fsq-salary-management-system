package com.fsq.fsqsalary.po;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeductionInfoDO {
    private Integer deductionId;

    private Integer employeeId;

    private Integer childDeduction;

    private Integer proStudyDeduction;

    private BigDecimal illnessDeduction;

    private Integer housingLoanDeduction;

    private Integer housingRentDeduction;

    private Integer parentDeduction;

}