package com.houqin.biz.messtype;

import com.a_268.base.core.BaseBiz;
import com.houqin.dao.messtype.MessTypeDao;
import com.houqin.entity.messtype.MessType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 食堂类型管理
 *
 * @author ccl
 * @create 2016-12-22-16:06
 */
@Service
public class MessTypeBiz extends BaseBiz<MessType,MessTypeDao>{

    /**
     * @Description:查询所有食堂类型
     * @author: ccl
     * @Param: []
     * @Return: java.util.List<com.houqin.entity.messtype.MessType>
     * @Date: 2016-12-22
     */
    public List<MessType> queryMessTypeList(){
        List<MessType> messTypeList=this.find(null,"1=1");
        return  messTypeList;
    }

}
