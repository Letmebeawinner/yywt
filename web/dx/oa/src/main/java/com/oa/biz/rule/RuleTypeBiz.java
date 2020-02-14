package com.oa.biz.rule;

import com.a_268.base.core.BaseBiz;
import com.oa.dao.rule.RuleTypeDao;
import com.oa.entity.rule.RuleType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 规章制度类型
 *
 * @author ccl
 * @create 2017-01-04-17:36
 */
@Service
public class RuleTypeBiz extends BaseBiz<RuleType,RuleTypeDao>{

    public List<RuleType> ruleTypeList(){
        List<RuleType> ruleTypeList=this.find(null,"1=1");
        return ruleTypeList;
    }

}
