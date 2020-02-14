package com.houqin.biz.food;

import com.a_268.base.core.BaseBiz;
import com.houqin.biz.outstock.OutStockBiz;
import com.houqin.dao.food.FoodDao;
import com.houqin.entity.food.Food;
import com.houqin.entity.outstock.OutStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 食材
 *
 * @author wanghailong
 * @create 2017-07-25-下午 1:47
 */
@Service
public class FoodBiz extends BaseBiz<Food, FoodDao> {

    @Autowired
    private OutStockBiz outStockBiz;

    public void tx_editStoreHousePlus(OutStock outStock,Food food){
        outStockBiz.save(outStock);
        this.update(food);
    }
}
