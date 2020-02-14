package com.houqin.biz.goods;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.houqin.biz.goodstype.GoodstypeBiz;
import com.houqin.biz.goodsunit.GoodsunitBiz;
import com.houqin.biz.storage.StorageBiz;
import com.houqin.dao.goods.GoodsDao;
import com.houqin.entity.goods.Goods;
import com.houqin.entity.goods.GoodsDto;
import com.houqin.entity.goodstype.Goodstype;
import com.houqin.entity.goodsunit.Goodsunit;
import com.houqin.entity.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

/**
 * 物品管理
 *
 * @author ccl
 * @create 2016-12-19-15:21
 */
@Service
public class GoodsBiz extends BaseBiz<Goods, GoodsDao> {

    @Autowired
    private GoodstypeBiz goodstypeBiz;
    @Autowired
    private GoodsunitBiz goodsunitBiz;
    @Autowired
    private StorageBiz storageBiz;

    public List<GoodsDto> getAllGoodsDto(@ModelAttribute("pagination") Pagination pagination, String whereSql) {
        List<Goods> goodsrecordList = this.find(pagination, whereSql);
        List<GoodsDto> list = new ArrayList<GoodsDto>();
        if (goodsrecordList != null && goodsrecordList.size() > 0) {
            for (Goods goods : goodsrecordList) {
                GoodsDto dto = new GoodsDto();
                Goodstype goodstype = goodstypeBiz.findById(goods.getTypeId());
                Goods goods1 = this.findById(goods.getId());
                Goodsunit goodsunit = goodsunitBiz.findById(goods.getUnitId());
                dto.setId(goods.getId());
                dto.setName(goods1.getName());
                dto.setTypeName(goodstype.getTypeName());
                if (ObjectUtils.isNotNull(goodsunit)) {
                    dto.setUnitName(goodsunit.getName());
                } else {
                    dto.setUnitName("");
                }
                Storage storage=storageBiz.findById(goods.getStorageId());
                if(ObjectUtils.isNotNull(storage)){
                    dto.setStorageName(storage.getName());
                }else{
                    dto.setStorageName("");
                }
                dto.setPrice(goods.getPrice());
                dto.setModel(goods.getModel());
                dto.setName(goods.getName());
                dto.setDescribes(goods.getDescribes());
                dto.setNum(goods.getNum());
                dto.setCode(goods.getCode());
                dto.setCreateTime(goods.getCreateTime());
                list.add(dto);
            }
        }
        return list;
    }

}
