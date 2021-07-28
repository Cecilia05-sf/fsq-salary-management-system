package com.fsq.fsqsalary.dao;

import com.fsq.fsqsalary.po.EmployeeInfoDO;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeInfoDOMapper {
    int deleteByPrimaryKey(Integer employeeId);

    int insert(EmployeeInfoDO record);

    int insertSelective(EmployeeInfoDO record);

    EmployeeInfoDO selectByPrimaryKey(Integer employeeId);

    int updateByPrimaryKeySelective(EmployeeInfoDO record);

    int updateByPrimaryKey(EmployeeInfoDO record);
}