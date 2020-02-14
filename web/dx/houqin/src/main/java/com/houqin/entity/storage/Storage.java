package com.houqin.entity.storage;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 库房信息
 *
 * @author wanghailong
 * @create 2017-05-19-上午 11:27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Storage extends BaseEntity {

    private String name;//库房名称

    private String address;//库房地址

    private String managerName;//管理员名称

    private String managerPhone;//管理员名称

    private Long sysId;//管理员绑定系统用户id
}
