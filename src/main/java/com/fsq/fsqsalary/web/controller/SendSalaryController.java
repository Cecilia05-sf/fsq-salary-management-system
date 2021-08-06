package com.fsq.fsqsalary.web.controller;

import com.fsq.fsqsalary.dao.SalaryRecordDOMapper;
import com.fsq.fsqsalary.po.SalaryRecordDO;
import com.fsq.fsqsalary.po.SalaryRecordQuery;
import com.fsq.fsqsalary.service.BankService;
import com.fsq.fsqsalary.service.SalaryCalcServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
public class SendSalaryController {

    @Autowired
    SalaryCalcServiceImpl salaryCalcService;
    @Autowired
    SalaryRecordDOMapper salaryRecordDOMapper;
    @Autowired
    BankService bankService;

    //发薪记录展示
    @RequestMapping("/salary")
    public String showSalary(Model model) {
        SalaryRecordQuery query = SalaryRecordQuery.builder().build();
        List<SalaryRecordDO> all =salaryRecordDOMapper.queryPage(query);
        model.addAttribute("all",all);
        return "salaryRecord";
    }

    //发薪操作页面
    @RequestMapping("/salary/send")
    public String sendSalary() {
        return "sendSalary";
    }

    //发放结果展示
    @RequestMapping("/salary/sendsalaryresult")
    public String sendResult(String monthIndex, Integer employeeId, Model model) {

        //todo:检查是否有该员工
        //todo:检查是否登记过该员工的个税专项扣除信息

        //检查是否已经发放过该月该员工薪水
        SalaryRecordQuery query = SalaryRecordQuery.builder().payMonth(monthIndex).employeeId(employeeId).build();
        List<SalaryRecordDO> result1 =salaryRecordDOMapper.queryPage(query);
        if ( result1.size() != 0) {
            model.addAttribute("records",result1);
            return "duplicatePayment";
        }
        //把yyyy-mm形式的monthIndex转换为Integer mm
        Integer month = Integer.parseInt(monthIndex.substring(monthIndex.length() - 2));
        //调用薪酬计算服务算出实发金额
        SalaryRecordDO salaryRecordDO = salaryCalcService.salaryCalcMonthly(employeeId, month);
        //设置发放月份
        salaryRecordDO.setPayMonth(monthIndex);
        //保存数据库 todo：保存失败异常处理
        salaryRecordDOMapper.insertSelective(salaryRecordDO);
        //如果银行转账成功
//        if (bankService.transfer(employeeId, salaryRecordDO.getPayId())) {
//            //更新发放时间和状态
//            byte a = 1;
//            salaryRecordDO.setStatus(a); //发放状态：已发放
//            salaryRecordDO.setPayDate(new Date()); //发工资时间
//            salaryRecordDOMapper.updateStatus(salaryRecordDO);
//        }
        model.addAttribute("sendSalaryResult", salaryRecordDO);
        return "sendSalaryResult";
    }
}

