package com.houqin.biz.goodsunit;

import com.a_268.base.core.BaseBiz;
import com.houqin.dao.goodsunit.GoodsunitDao;
import com.houqin.entity.goodsunit.Goodsunit;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物品分类
 *
 * @author ccl
 * @create 2016-12-19-13:57
 */
@Service
public class GoodsunitBiz extends BaseBiz<Goodsunit,GoodsunitDao>{

    public List<Goodsunit> getAllGoodsunit(){
        List<Goodsunit> goodsUnitList=this.find(null,"1=1");
        return goodsUnitList;
    }

}
