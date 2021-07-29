package com.fsq.fsqsalary.dao;

import com.fsq.fsqsalary.po.EmployeeInfoDO;
import com.fsq.fsqsalary.po.EmployeeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeInfoDOMapper {
    int deleteByPrimaryKey(Integer employeeId);

    int insert(EmployeeInfoDO record);

    int insertSelective(EmployeeInfoDO record);

    EmployeeInfoDO selectByPrimaryKey(Integer employeeId);

    List<EmployeeInfoDO> queryPage(EmployeeQuery query);

    int updateByPrimaryKeySelective(EmployeeInfoDO record);

    int updateByPrimaryKey(EmployeeInfoDO record);

    int countByQuery(EmployeeQuery query);
}