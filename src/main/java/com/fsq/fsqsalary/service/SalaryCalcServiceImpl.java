package com.fsq.fsqsalary.service;

import com.fsq.fsqsalary.dao.DeductionInfoDOMapper;
import com.fsq.fsqsalary.dao.EmployeeInfoDOMapper;
import com.fsq.fsqsalary.dao.RuleDOMapper;
import com.fsq.fsqsalary.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SalaryCalcServiceImpl implements SalaryCalcService{

    @Autowired
    EmployeeInfoDOMapper employeeInfoDOMapper;
    @Autowired
    RuleDOMapper ruleDOMapper;
    @Autowired
    DeductionInfoDOMapper deductionInfoDOMapper;
    @Autowired
    RuleServiceImpl ruleServiceImpl;

    //计算工资发放详情
    @Override
    public SalaryRecordDO salaryCalcMonthly(Integer employeeId, Integer monthIndex) {
        BigDecimal preTaxSalary = employeeInfoDOMapper.selectByPrimaryKey(employeeId).getPreTaxSalary();
        List<SalaryRecordDO> tryResult =trySalaryCalc(employeeId, preTaxSalary);
        SalaryRecordDO salaryRecordDO = tryResult.get(monthIndex-1);
        salaryRecordDO.setPayDate(new Date());
        salaryRecordDO.setStatus((byte) 0); // 发放状态：0-未发放，1-已发放
        salaryRecordDO.setEmployeeId(employeeId); //工号
        return salaryRecordDO;
    }

    //工资试算，默认计算12个月monthIndex=12，且工资由用户输入而不是查员工信息表
    @Override
    public List<SalaryRecordDO> trySalaryCalc(Integer employeeId, BigDecimal preTaxSalary) {
        List<BigDecimal> socialInsur = socialInsurCalc(preTaxSalary); //社保
        BigDecimal housingProvident = housingProvidentCalc(preTaxSalary); //公积金
        BigDecimal deduction = deductionCalc(employeeId).stream().reduce(BigDecimal.ZERO, BigDecimal::add); //个税专项附加扣除总额

        BigDecimal calTax = taxableSalaryCalc(employeeId, preTaxSalary); //月工资的应缴税部分
        List<CalcResultDTO> calcResult = taxCalc(calTax);  //每月缴税额和使用的个税级数

        List<SalaryRecordDO> tryResult = new ArrayList<>(12);
        for (int i = 0; i < 12; i++) {
            SalaryRecordDO salaryRecordDO = new SalaryRecordDO();
            salaryRecordDO.setPayMonth(String.valueOf(i+1)); //月份编号
            salaryRecordDO.setPreTaxSalary(preTaxSalary); //应发工资
            salaryRecordDO.setPensionInsur(socialInsur.get(0)); //养老保险
            salaryRecordDO.setUnemployInsur(socialInsur.get(2)); //失业保险
            salaryRecordDO.setMedicareInsur(socialInsur.get(1)); //医疗保险
            salaryRecordDO.setHousingProvident(housingProvident); //公积金
            salaryRecordDO.setDeduction(deduction); //个税专项扣除总额
            salaryRecordDO.setRule(calcResult.get(i).getRuleDO().toString());//当月使用的个税级数
            salaryRecordDO.setTax(calcResult.get(i).getTaxPreMonth()); //当月应缴税额
            salaryRecordDO.setFinalSalary(preTaxSalary.subtract(calcResult.get(i).getTaxPreMonth()).subtract(housingProvident).subtract(socialInsur.stream().reduce(BigDecimal.ZERO, BigDecimal::add))); //实发工资
            tryResult.add(salaryRecordDO);
        }
        return tryResult;
    }

    //社保计算，返回List[养老金，失业金，医保]--todo:DTO
    private List<BigDecimal> socialInsurCalc(BigDecimal preTaxSalary) {

        List<RuleDO> result = ruleServiceImpl.getSocialRule();

        //socialInsur保存社保计算结果
        List<BigDecimal> socialInsur = new ArrayList<BigDecimal>();

        for (RuleDO ruleDO:result) {
            //如果工资超过社保基数上限用上限计算，低于下限用下限计算，介于两者之间用工资计算
            if (preTaxSalary.compareTo(ruleDO.getRangeUpper()) > 0) {
                socialInsur.add(ruleDO.getRangeUpper().multiply(ruleDO.getRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if (preTaxSalary.compareTo(ruleDO.getRangeLower()) < 0) {
                socialInsur.add(ruleDO.getRangeLower().multiply(ruleDO.getRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
            } else {
                socialInsur.add(preTaxSalary.multiply(ruleDO.getRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        }
        return socialInsur;
    }

    //计算公积金，返回公积金缴纳额
    @Override
    public BigDecimal housingProvidentCalc(BigDecimal preTaxSalary) {

        //从数据库获取公积金规则
        RuleDO result = ruleDOMapper.selectByRuleType(RuleTypeEnum.HOUSING.getType());
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
    @Override
    public List<BigDecimal> deductionCalc(Integer employeeId) {
        //从数据库获取制定员工的专项扣除信息
        DeductionInfoDO deductionInfoDO = deductionInfoDOMapper.selectByEmployeeId(employeeId);
        List<BigDecimal> deductionValue = new ArrayList<>();

        //todo:个税附加专项扣除规则是写死在代码里的
        deductionValue.add(BigDecimal.valueOf(deductionInfoDO.getChildDeduction() * 1000).setScale(2, BigDecimal.ROUND_HALF_UP));
        deductionValue.add(BigDecimal.valueOf(deductionInfoDO.getProStudyDeduction() * 400).setScale(2, BigDecimal.ROUND_HALF_UP));
        deductionValue.add(deductionInfoDO.getIllnessDeduction().divide(new BigDecimal("12"),2,BigDecimal.ROUND_HALF_UP));
        deductionValue.add(BigDecimal.valueOf(deductionInfoDO.getHousingLoanDeduction() * 1000).setScale(2, BigDecimal.ROUND_HALF_UP));
        deductionValue.add(BigDecimal.valueOf(deductionInfoDO.getHousingRentDeduction() * 1500).setScale(2, BigDecimal.ROUND_HALF_UP));
        deductionValue.add(BigDecimal.valueOf(deductionInfoDO.getParentDeduction() * 1000).setScale(2, BigDecimal.ROUND_HALF_UP));

        return deductionValue;
    }

    //计算需缴税部分的工资额
    @Override
    public BigDecimal taxableSalaryCalc(Integer employeeId, BigDecimal preTaxSalary) {

        //Todo:动态配置起征点=5000元
        final BigDecimal THRESHOULD = new BigDecimal("5000");

        //社保总和
        BigDecimal socialInsurSum = socialInsurCalc(preTaxSalary).stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        //个税附加专项扣除总额
        BigDecimal deductionSum = deductionCalc(employeeId).stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        //每月需缴税部分（税前-社保-公积金-扣除-起征点）
        BigDecimal calTax = preTaxSalary.subtract(socialInsurSum).subtract(housingProvidentCalc(preTaxSalary)).subtract(deductionSum).subtract(THRESHOULD);

        return calTax;
    }


    //根据需缴税额计算每个月的缴税额
    @Override
    public List<CalcResultDTO> taxCalc(BigDecimal calTax) {
        List<CalcResultDTO> resultList = new ArrayList<>(12);

        //累计应缴税记录
        BigDecimal[] totalTaxArchive = new BigDecimal[12];

        for (int i = 0; i < 12; i++) {

            CalcResultDTO calcResultDTO = new CalcResultDTO();
            //截止当前月份，总需缴税部分
            BigDecimal calTaxSum = calTax.multiply(BigDecimal.valueOf(i + 1)).setScale(2, BigDecimal.ROUND_HALF_UP);
            //使用总工资中需缴税部分，匹配个税规则，返回<个税规则，总缴税额>
            Pair taxMatch = ruleServiceImpl.matchRule(calTaxSum);
            //当前月份使用的个税级数
            calcResultDTO.setRuleDO((RuleDO) taxMatch.getFirst());
            //截止当前月份，累计需缴税额
            totalTaxArchive[i] = (BigDecimal) taxMatch.getSecond();
            //当不是第一个月的时候，每月应缴税额 = 当月累计需缴税额 - 前一个月累计需缴税额
            if (i == 0) {
                calcResultDTO.setTaxPreMonth(totalTaxArchive[0]);
            } else {
                calcResultDTO.setTaxPreMonth(totalTaxArchive[i].subtract(totalTaxArchive[i - 1]));
            }
            resultList.add(calcResultDTO);
        }
        return resultList;
    }
}

