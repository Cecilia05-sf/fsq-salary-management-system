package com.fsq.fsqsalary.dao;

import com.fsq.fsqsalary.po.RuleDO;
import com.fsq.fsqsalary.po.RuleQuery;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RuleDOMapper {
    int deleteByPrimaryKey(Integer ruleId);

    int insert(RuleDO record);

    int insertSelective(RuleDO record);

    RuleDO selectByPrimaryKey(Integer ruleId);

    RuleDO selectByRuleType(String ruleType);

    List<RuleDO> queryPage(RuleQuery query);

    int updateByPrimaryKeySelective(RuleDO record);

    int updateByPrimaryKey(RuleDO record);

    int countByQuery(RuleQuery query);

}