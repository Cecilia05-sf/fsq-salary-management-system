package com.fsq.fsqsalary.service;

import com.fsq.fsqsalary.dao.DeductionInfoDOMapper;
import com.fsq.fsqsalary.dao.EmployeeInfoDOMapper;
import com.fsq.fsqsalary.dao.RuleDOMapper;
import com.fsq.fsqsalary.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SalaryCalcService {

    @Autowired
    EmployeeInfoDOMapper employeeInfoDOMapper;
    @Autowired
    RuleDOMapper ruleDOMapper;
    @Autowired
    DeductionInfoDOMapper deductionInfoDOMapper;
    @Autowired
    RuleService ruleService;

    //计算工资发放详情
    public SalaryRecordDO SalaryService(Integer employeeId, Integer monthIndex) {
        SalaryRecordDO salaryRecordDO = new SalaryRecordDO();
        BigDecimal preTaxSalary = employeeInfoDOMapper.selectByPrimaryKey(employeeId).getPreTaxSalary();
        salaryRecordDO.setPreTaxSalary(preTaxSalary); //应发工资
        salaryRecordDO.setStatus((byte) 0); // 发放状态：0-未发放，1-已发放
        salaryRecordDO.setEmployeeId(employeeId); //工号
        salaryRecordDO.setPayDate(new Date()); //发工资时间
        salaryRecordDO.setPensionInsur(SocialInsurCalc(preTaxSalary).get(2)); //养老保险
        salaryRecordDO.setUnemployInsur(SocialInsurCalc(preTaxSalary).get(0)); //失业保险
        salaryRecordDO.setMedicareInsur(SocialInsurCalc(preTaxSalary).get(1)); //医疗保险
        salaryRecordDO.setHousingProvident(HousingProvidentCalc(preTaxSalary)); //公积金
        salaryRecordDO.setDeduction(DeductionCalc(employeeId).stream().reduce(BigDecimal.ZERO, BigDecimal::add)); //个税专项扣除总额

        BigDecimal calTax = TaxableSalaryCalc(employeeId, salaryRecordDO.getPreTaxSalary()); //应缴税部分工资
        BigDecimal[] taxArchive = TaxCalcService(monthIndex, calTax); // 每月应缴税额
        salaryRecordDO.setTax(taxArchive[monthIndex-1]); //当月应缴税额
        salaryRecordDO.setFinalSalary(preTaxSalary.subtract(taxArchive[monthIndex-1]).subtract(HousingProvidentCalc(preTaxSalary)).subtract(SocialInsurCalc(preTaxSalary).stream().reduce(BigDecimal.ZERO, BigDecimal::add)) ); //实发工资
        //todo: rule
        //salaryRecordDO.setRule(rule);
        return salaryRecordDO;
    }

    //工资试算，默认计算12个月monthIndex=12，且工资由用户输入而不是查员工信息表
    public BigDecimal[] TrySalaryService(Integer employeeId, BigDecimal preTaxSalary) {
        BigDecimal calTax = TaxableSalaryCalc(employeeId, preTaxSalary);
        BigDecimal[] tryResult = TaxCalcService(12, calTax);
        return tryResult;
    }

    //社保计算，返回List[养老金，失业金，医保]
    public List<BigDecimal> SocialInsurCalc(BigDecimal preTaxSalary) {

        List<RuleDO> result = ruleService.getSocialRule();

        //socialInsur保存社保计算结果
        List<BigDecimal> socialInsur = new ArrayList<BigDecimal>(result.size());

        for (int i = 0; i < result.size(); i++) {
            //如果工资超过社保基数上限用上限计算，低于下限用下限计算，介于两者之间用工资计算
            if (preTaxSalary.compareTo(result.get(i).getRangeUpper()) > 0) {
                socialInsur.add(result.get(i).getRangeUpper().multiply(result.get(i).getRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if (preTaxSalary.compareTo(result.get(i).getRangeLower()) < 0) {
                socialInsur.add(result.get(i).getRangeLower().multiply(result.get(i).getRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
            } else {
                socialInsur.add(preTaxSalary.multiply(result.get(i).getRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        }
        return socialInsur;
    }

    //计算公积金，返回公积金缴纳额
    public BigDecimal HousingProvidentCalc(BigDecimal preTaxSalary) {

        //从数据库获取公积金规则
        RuleDO result = ruleDOMapper.selectByRuleType(RuleTypeEnum.housing.toString());
        BigDecimal housingProvident;

        //如果工资超过公积金基数上限用上限计算，低于下限用下限计算，介于两者之间用工资计算
        if (preTaxSalary.compareTo(result.getRangeUpper()) > 0) {
            housingProvident = result.getRangeUpper().multiply(result.getRate()).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (preTaxSalary.compareTo(result.getRangeLower()) < 0) {
            housingProvident = result.getRangeLower().multiply(result.getRate()).setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            housingProvident = preTaxSalary.multiply(result.getRate()).setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        return housingProvident;

    }

    //计算个税附加专项扣除额度
    public List<BigDecimal> DeductionCalc(Integer employeeId) {
        //从数据库获取制定员工的专项扣除信息
        DeductionInfoDO deductionInfoDO = deductionInfoDOMapper.selectByEmployeeId(employeeId);
        List<BigDecimal> deductionValue = new ArrayList<>();

        //todo:个税附加专项扣除规则是写死在代码里的
        deductionValue.add(BigDecimal.valueOf(deductionInfoDO.getChildDeduction() * 1000).setScale(2, BigDecimal.ROUND_HALF_UP));
        deductionValue.add(BigDecimal.valueOf(deductionInfoDO.getProStudyDeduction() * 400).setScale(2, BigDecimal.ROUND_HALF_UP));
        deductionValue.add(deductionInfoDO.getIllnessDeduction().divide(new BigDecimal("12")).setScale(2, BigDecimal.ROUND_HALF_UP));
        deductionValue.add(BigDecimal.valueOf(deductionInfoDO.getHousingLoanDeduction() * 1000).setScale(2, BigDecimal.ROUND_HALF_UP));
        deductionValue.add(BigDecimal.valueOf(deductionInfoDO.getHousingRentDeduction() * 1500).setScale(2, BigDecimal.ROUND_HALF_UP));
        deductionValue.add(BigDecimal.valueOf(deductionInfoDO.getParentDeduction() * 1000).setScale(2, BigDecimal.ROUND_HALF_UP));

        return deductionValue;
    }

    //计算需缴税部分的工资额
    public BigDecimal TaxableSalaryCalc(Integer employeeId, BigDecimal preTaxSalary) {

        //Todo:动态配置起征点=5000元
        final BigDecimal THRESHOULD = new BigDecimal("5000");

        //社保总和
        BigDecimal socialInsurSum = SocialInsurCalc(preTaxSalary).stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        //个税附加专项扣除总额
        BigDecimal deductionSum = DeductionCalc(employeeId).stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        //每月需缴税部分（税前-社保-公积金-扣除-起征点）
        BigDecimal calTax = preTaxSalary.subtract(socialInsurSum).subtract(HousingProvidentCalc(preTaxSalary)).subtract(deductionSum).subtract(THRESHOULD);

        return calTax;
    }


    //根据需缴税额计算每个月的缴税额
    public BigDecimal[] TaxCalcService(Integer monthIndex, BigDecimal calTax) {

        //每月应交税记录
        BigDecimal[] taxArchive = new BigDecimal[12];

        //累计应缴税记录
        BigDecimal[] totalTaxArchive = new BigDecimal[12];

        for (int i = 0; i < monthIndex; i++) {
            //截止当前月份，总需缴税部分
            BigDecimal calTaxSum = calTax.multiply(BigDecimal.valueOf(i + 1)).setScale(2, BigDecimal.ROUND_HALF_UP);
            //截止当前月份，累计需缴税额
            BigDecimal[] taxMatch = ruleService.MatchRule(calTaxSum);
            totalTaxArchive[i] = taxMatch[1];
            //当不是第一个月的时候，每月应缴税额 = 当月累计需缴税额 - 前一个月累计需缴税额
            if (taxArchive[0] == null) {
                taxArchive[0] = totalTaxArchive[0];
            } else {
                taxArchive[i] = totalTaxArchive[i].subtract(totalTaxArchive[i - 1]);
            }
        }
        return taxArchive;
    }
}

