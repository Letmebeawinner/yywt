package com.sms.entity.profile;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Administrator on 2016/12/22.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Profile extends BaseEntity{
    private static final long serialVersionUID = 5214908966627492349L;
    /**配置名*/
    private String configName;
    /**配置关键字*/
    private String configKey;
    /**短信配置*/
    private String configContext;

}
