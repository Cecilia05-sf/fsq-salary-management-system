package com.fsq.fsqsalary.converter;

import com.fsq.fsqsalary.dto.TaxRuleDTO;
import com.fsq.fsqsalary.po.RuleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Shiqi on 2021/8/6.
 */
@Mapper
public interface TaxRuleConverter {
    TaxRuleConverter INSTANCE = Mappers.getMapper(TaxRuleConverter.class);
    TaxRuleDTO toDTO(RuleDO ruleDO);
    List<TaxRuleDTO> toDTOs(List<RuleDO> ruleDOList);
    RuleDO toDO(TaxRuleDTO taxRuleDTO);
    List<RuleDO> toDOs(List<TaxRuleDTO> taxRuleDTOList);
}
