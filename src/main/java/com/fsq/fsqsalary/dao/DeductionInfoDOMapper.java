package com.fsq.fsqsalary.dao;

import com.fsq.fsqsalary.po.DeductionInfoDO;
import com.fsq.fsqsalary.po.DeductionQuery;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DeductionInfoDOMapper {
    int deleteByPrimaryKey(Integer deductionId);

    int insert(DeductionInfoDO record);

    int insertSelective(DeductionInfoDO record);

    DeductionInfoDO selectByPrimaryKey(Integer deductionId);

    DeductionInfoDO selectByEmployeeId(Integer employeeId);

    List<DeductionInfoDO> queryPage(DeductionQuery query);

    int updateByPrimaryKeySelective(DeductionInfoDO record);

    int updateByPrimaryKey(DeductionInfoDO record);

    int countByQuery(DeductionQuery query);
}