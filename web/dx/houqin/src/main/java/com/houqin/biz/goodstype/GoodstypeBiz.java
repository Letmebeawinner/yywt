package com.houqin.biz.goodstype;

import com.a_268.base.core.BaseBiz;
import com.houqin.dao.goodstype.GoodstypeDao;
import com.houqin.entity.goodstype.Goodstype;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物品分类
 * Created by Administrator on 2016/12/15.
 */
@Service
public class GoodstypeBiz extends BaseBiz<Goodstype,GoodstypeDao>{

    public List<Goodstype> getAllGoodstype(){
        List<Goodstype>goodstypeList=this.find(null,"1=1");
        return goodstypeList;
    }
}
