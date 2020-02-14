package com.houqin.entity.oil;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Administrator on 2017/6/13 0013.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OilDto extends Oil{
    private String userName;//用户名
}
