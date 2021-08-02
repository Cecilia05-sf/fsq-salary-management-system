package com.fsq.fsqsalary.po;

import lombok.*;
import java.util.List;

@ToString
@Getter
@Setter
@Builder
public class DeductionQuery extends BaseQuery {
    private Integer deductionId;
    private Integer employeeId;
    private List<Integer> employeeIdList;
}