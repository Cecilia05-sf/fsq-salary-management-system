package com.fsq.fsqsalary.po;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
@Builder
public class SalaryRecordQuery extends BaseQuery{
    private String payMonth;
    private Integer employeeId;
    private List<String> payMonthList;
    private List<Integer> employeeIdList;
}
