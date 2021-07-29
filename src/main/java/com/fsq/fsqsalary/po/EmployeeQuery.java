package com.fsq.fsqsalary.po;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@Builder
public class EmployeeQuery extends BaseQuery{

    private Integer employeeId;

    private String employeeName;

    private List<String> employeeNameList;
}
