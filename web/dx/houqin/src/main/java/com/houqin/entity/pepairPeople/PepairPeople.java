package com.houqin.entity.pepairPeople;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 维修人员
 *
 * @author wanghailong
 * @create 2017-06-07-下午 6:49
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PepairPeople extends BaseEntity{

    private Long userId; //用户id;

    private String userName;//用户名

    private String mobile;//手机号码
}
