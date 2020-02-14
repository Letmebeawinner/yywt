package com.app.entity;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AppUpdate extends BaseEntity {

    private static final long serialVersionUID = -5632805782880768719L;

    //版本号
    private String version;
    //下载新版本链接
    private String updateUrl;
    //手机类型。1.Android 2.IOS
    private Integer mobileType;
    //更新信息
    private String updateInfo;
}
