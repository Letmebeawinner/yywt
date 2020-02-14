package com.oa.entity.desk;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 我的桌面
 *
 * @author ccl
 * @create 2017-01-17-14:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Desk extends BaseEntity{

    private Long userId;//用户id

    private  Long  functionId;//功能id


}
