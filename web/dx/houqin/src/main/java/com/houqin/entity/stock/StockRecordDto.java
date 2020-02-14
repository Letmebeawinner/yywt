package com.houqin.entity.stock;

import lombok.Data;

/**
 * 库存出入库记录
 *
 * @author lzh
 * @create 2018-01-08-17:32
 */
@Data
public class StockRecordDto extends StockRecord{
    //物品名称
   private String goodsName;
   //操作人
   private String operatorName;
   //库房名
   private String stockHouseName;
   //单位名
   private String unitName;
   //商品编号
   private String goodsCode;
}
