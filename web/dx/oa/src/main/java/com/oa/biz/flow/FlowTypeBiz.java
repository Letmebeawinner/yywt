package com.oa.biz.flow;

import com.a_268.base.core.BaseBiz;
import com.oa.dao.flow.FlowTypeDao;
import com.oa.entity.flow.FlowType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程分类
 *
 * @author ccl
 * @create 2017-01-05-17:37
 */
@Service
public class FlowTypeBiz extends BaseBiz<FlowType,FlowTypeDao> {

    public List<FlowType> flowTypeList(){
        List<FlowType> flowTypeList=this.find(null," parentId=0");
        return flowTypeList;
    }

}
