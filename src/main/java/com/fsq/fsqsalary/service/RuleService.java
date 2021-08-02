package com.fsq.fsqsalary.service;

import com.fsq.fsqsalary.dao.RuleDOMapper;
import com.fsq.fsqsalary.po.RuleDO;
import com.fsq.fsqsalary.po.RuleQuery;
import com.fsq.fsqsalary.po.RuleTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class RuleService {

    @Autowired
    private RuleDOMapper ruleDOMapper;
    @Autowired
    private SalaryCalcService salaryCalcService;

    //从数据库获取社保规则
    // (如果个人缴纳的社保项目变了怎么办？)
    public  List<RuleDO> getSocialRule(){
        List<String> ruleType = new ArrayList<>();
        ruleType.add(RuleTypeEnum.pension.toString());
        ruleType.add(RuleTypeEnum.unemploy.toString());
        ruleType.add(RuleTypeEnum.medicare.toString());
        RuleQuery query = RuleQuery.builder().ruleTypeList(ruleType).build();
        List<RuleDO> result = ruleDOMapper.queryPage(query);
        return  result;

    }

    //对于输入的应缴税工资部分，进行个税计算规则匹配
    public BigDecimal[] MatchRule(BigDecimal calTax) {

        BigDecimal[] matchResult = new BigDecimal[3];

        if (calTax.compareTo(BigDecimal.ZERO) > 0) {
            RuleQuery query = RuleQuery.builder().ruleType(RuleTypeEnum.tax.toString()).build();
            List<RuleDO> result = ruleDOMapper.queryPage(query);

            RuleDO ruleDO = new RuleDO();

            for (int i = 0; i < result.size(); i++) {
                ruleDO = result.get(i);
                if (calTax.compareTo(ruleDO.getRangeLower()) > 0) {
                    break;
                }
            }

            BigDecimal tax = calTax.multiply(ruleDO.getRate()).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(ruleDO.getReduction());

            matchResult[0] = (ruleDO.getRate());
            matchResult[1] = (tax);
        } else {
            matchResult[0] = BigDecimal.ZERO;
            matchResult[1] = BigDecimal.ZERO;
        }
        return matchResult;
    }
}
