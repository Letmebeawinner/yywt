package com.houqin.biz.electricityType;

import com.a_268.base.core.BaseBiz;
import com.houqin.dao.electricityType.ElectricityTypeDao;
import com.houqin.entity.electricityType.ElectricityType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用电区域类型
 * Created by Administrator on 2017/6/14 0014.
 */
@Service
public class ElectricityTypeBiz extends BaseBiz<ElectricityType, ElectricityTypeDao> {

    /**
     * 查询所有用电区域类型
     * @return
     */
    public List<ElectricityType> queryAllType() {
        return this.find(null,"1=1");
    }
}
