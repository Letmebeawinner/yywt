package com.houqin.biz.dishesType;

import com.a_268.base.core.BaseBiz;
import com.houqin.dao.dishesType.DishesTypeDao;
import com.houqin.entity.dishesType.DishesType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/12/15.
 */
@Service
public class DishesTypeBiz extends BaseBiz<DishesType,DishesTypeDao>{

    public List<DishesType> getAllDishesType(){
        List<DishesType>dishesTypeList=this.find(null,"1=1");
        return dishesTypeList;
    }
}
