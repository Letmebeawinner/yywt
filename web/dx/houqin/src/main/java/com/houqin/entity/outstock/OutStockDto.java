package com.houqin.entity.outstock;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 出库表
 *
 * @author ccl
 * @create 2016-12-20-14:57
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OutStockDto extends OutStock {

   //出库类型名
   private String typeName;
   //操作人
   private String operatorName;
}
