package com.fsq.fsqsalary.dao;
import com.fsq.fsqsalary.po.RuleQuery;
import com.fsq.fsqsalary.po.SalaryRecordDO;
import com.fsq.fsqsalary.po.SalaryRecordQuery;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SalaryRecordDOMapper {
    int deleteByPrimaryKey(Integer payId);

    int insert(SalaryRecordDO record);

    int insertSelective(SalaryRecordDO record);

    SalaryRecordDO selectByPrimaryKey(Integer payId);

    SalaryRecordDO selectByEmployeeId(Integer employeeId);

    List<SalaryRecordDO> queryPage(SalaryRecordQuery query);

    int countByQuery(SalaryRecordQuery query);

    int updateByPrimaryKeySelective(SalaryRecordDO record);

    int updateByPrimaryKey(SalaryRecordDO record);

    int updateStatus(SalaryRecordDO record);

}