package com.houqin.biz.outstock;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.ObjectUtils;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.goods.GoodsBiz;
import com.houqin.biz.storage.StorageTypeBiz;
import com.houqin.dao.outstock.OutStockDao;
import com.houqin.entity.goods.Goods;
import com.houqin.entity.outstock.OutStock;
import com.houqin.entity.outstock.OutStockDto;
import com.houqin.entity.storage.StorageType;
import com.houqin.entity.sysuser.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 出库记录表
 *
 * @author ccl
 * @create 2016-12-20-15:10
 */
@Service
public class OutStockBiz extends BaseBiz<OutStock,OutStockDao> {

    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private GoodsBiz goodsBiz;
    @Autowired
    private StorageTypeBiz storageTypeBiz;

    public OutStockDto getStockDtoById(Long outStockId) {
        OutStock outStock = this.findById(outStockId);
        return convertToDto(outStock);
    }

    public OutStockDto convertToDto(OutStock outStock) {
        OutStockDto outStockDto = new OutStockDto();
        BeanUtils.copyProperties(outStock, outStockDto);
        if (ObjectUtils.isNotNull(outStock)) {
            SysUser sysUser = baseHessianBiz.getSysUserById(outStock.getUserId());
            String userName = Optional.ofNullable(sysUser).map(SysUser::getUserName).orElse("");
            outStock.setUserName(userName);
            String goodsName = Optional.ofNullable(outStock)
                    .map(OutStock::getGoodsId)
                    .map(id -> {return goodsBiz.findById(id);})
                    .map(Goods::getName)
                    .orElse("");
            outStockDto.setName(goodsName);

            String typeName = Optional.ofNullable(outStock)
                    .map(OutStock::getTypeId)
                    .map(id -> {return storageTypeBiz.findById(id);})
                    .map(StorageType::getName)
                    .orElse("");
            outStockDto.setTypeName(typeName);
            outStockDto.setOperatorName(userName);
        }
        return outStockDto;
    }
}
