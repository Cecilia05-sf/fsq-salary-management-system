package com.fsq.fsqsalary.dao;

import com.fsq.fsqsalary.po.DeductionInfoDO;
import org.springframework.stereotype.Repository;


@Repository
public interface DeductionInfoDOMapper {
    int deleteByPrimaryKey(Integer deductionId);

    int insert(DeductionInfoDO record);

    int insertSelective(DeductionInfoDO record);

    DeductionInfoDO selectByPrimaryKey(Integer deductionId);

    int updateByPrimaryKeySelective(DeductionInfoDO record);

    int updateByPrimaryKey(DeductionInfoDO record);
}