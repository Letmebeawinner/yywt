package com.oa.biz.function;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.oa.dao.function.FunctionDao;
import com.oa.entity.function.Function;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * 功能列表
 *
 * @author ccl
 * @create 2017-01-16-11:42
 */
@Service
public class FunctionBiz extends BaseBiz<Function, FunctionDao> {

    public List<Function> queryFunctionList() {
        List<Function> functionList = this.find(null, "1=1");
        return functionList;
    }

}
