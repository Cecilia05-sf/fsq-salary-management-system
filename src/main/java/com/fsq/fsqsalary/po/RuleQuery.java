package com.fsq.fsqsalary.po;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@Builder
public class RuleQuery extends  BaseQuery{
    private Integer ruleId;
    private String ruleType;
    private List<String> ruleTypeList;
}
