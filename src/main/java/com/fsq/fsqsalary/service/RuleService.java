package com.fsq.fsqsalary.service;

import com.fsq.fsqsalary.po.RuleDO;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.util.List;

public interface RuleService {
    List<RuleDO> getSocialRule();
    Pair<RuleDO, BigDecimal> matchRule(BigDecimal calTax);
}
