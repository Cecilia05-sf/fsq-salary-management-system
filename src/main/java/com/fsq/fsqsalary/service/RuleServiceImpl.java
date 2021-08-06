package com.fsq.fsqsalary.service;

import com.fsq.fsqsalary.dao.RuleDOMapper;
import com.fsq.fsqsalary.po.RuleDO;
import com.fsq.fsqsalary.po.RuleQuery;
import com.fsq.fsqsalary.po.RuleTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class RuleServiceImpl implements RuleService{

    @Autowired
    private RuleDOMapper ruleDOMapper;

    //从数据库获取社保规则
    // (如果个人缴纳的社保项目变了怎么办？)
    @Override
    public  List<RuleDO> getSocialRule(){
        List<String> ruleType = new ArrayList<>();
        ruleType.add(RuleTypeEnum.PENSION.getType());
        ruleType.add(RuleTypeEnum.UNEMPLOY.getType());
        ruleType.add(RuleTypeEnum.MEDICARE.getType());
        RuleQuery query = RuleQuery.builder().ruleTypeList(ruleType).build();
        List<RuleDO> result = ruleDOMapper.queryPage(query);
        return  result;

    }

    //对于输入的应缴税工资部分，进行个税计算规则匹配
    @Override
    public Pair<RuleDO, BigDecimal> matchRule(BigDecimal calTax) {

        //需缴税工资>0
        if (calTax.compareTo(BigDecimal.ZERO) > 0) {
            RuleQuery query = RuleQuery.builder().ruleType(RuleTypeEnum.TAX.getType()).build();
            List<RuleDO> result = ruleDOMapper.queryPage(query);

            RuleDO ruleDO = new RuleDO();
            for (int i = 0; i < result.size(); i++) {

                ruleDO = result.get(i);
                if (calTax.compareTo(ruleDO.getRangeUpper()) <= 0) {
                    break;
                }
            }

            BigDecimal tax = calTax.multiply(ruleDO.getRate()).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(ruleDO.getReduction());
            return Pair.of(ruleDO, tax);
        } else {
            RuleDO ruleDO = RuleDO.builder().taxLevel(0).rate(BigDecimal.ZERO).rangeLower(BigDecimal.ZERO).rangeUpper(new BigDecimal("5000")).build();
            return Pair.of(ruleDO,BigDecimal.ZERO);
        }
    }
}
