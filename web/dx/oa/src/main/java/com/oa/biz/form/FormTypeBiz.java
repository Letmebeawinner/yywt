package com.oa.biz.form;

import com.a_268.base.core.BaseBiz;
import com.oa.dao.form.FormTypeDao;
import com.oa.entity.form.FormType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 表单分类
 *
 * @author ccl
 * @create 2017-01-07-15:35
 */
@Service
public class FormTypeBiz extends BaseBiz<FormType,FormTypeDao> {

    public List<FormType> formTypeList(){
        List<FormType> flowTypeList=this.find(null," parentId=0");
        return flowTypeList;
    }
}
