package com.fsq.fsqsalary.dao;

import com.fsq.fsqsalary.po.SalaryRecordDO;
import org.springframework.stereotype.Repository;


@Repository
public interface SalaryRecordDOMapper {
    int deleteByPrimaryKey(Integer payId);

    int insert(SalaryRecordDO record);

    int insertSelective(SalaryRecordDO record);

    SalaryRecordDO selectByPrimaryKey(Integer payId);

    int updateByPrimaryKeySelective(SalaryRecordDO record);

    int updateByPrimaryKey(SalaryRecordDO record);


}