package com.oa.biz.rule;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.oa.dao.rule.RuleDao;
import com.oa.entity.rule.QueryRule;
import com.oa.entity.rule.Rule;
import com.oa.entity.rule.RuleDto;
import com.oa.entity.rule.RuleType;
import com.oa.utils.GenerateSqlUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 规章制度
 *
 * @author ccl
 * @create 2017-01-04-19:11
 */
@Service
public class RuleBiz extends BaseBiz<Rule,RuleDao>{

    @Autowired
    private RuleTypeBiz ruleTypeBiz;
    /**
     * @Description:
     * @author: lzh
     * @Param: [pagination, ruleDto]
     * @Return: java.util.List<com.oa.entity.seal.ruleDto>
     * @Date: 9:43
     */
    public List<RuleDto> getRulesList(Pagination pagination, QueryRule rule) {
        String whereSql = GenerateSqlUtil.getSql(rule);
        List<Rule> rules = this.find(pagination, whereSql);
        List<RuleDto> ruleDtos = null;
        ruleDtos = rules.stream()
                .map(rules1 -> convertSealToDto(rules1))
                .collect(Collectors.toList());
        return ruleDtos;
    }

    /**
     * @Description:
     * @author: lzh
     * @Param: [seal]
     * @Return: com.oa.entity.seal.ruleDto
     * @Date: 11:40
     */
    private RuleDto convertSealToDto(Rule rule) {
        RuleDto ruleDto = new RuleDto();
        BeanUtils.copyProperties(rule, ruleDto);
        if (rule.getTypeId() != null) {
            RuleType ruleType = ruleTypeBiz.findById(ruleDto.getTypeId());
            if (ruleType != null) {
                ruleDto.setTypeName(ruleType.getName());
            }
        }
        return ruleDto;
    }
    
}
