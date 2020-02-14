package com.base.entity.sysuserlog;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserLog  extends BaseEntity{

    private Long userId;

    private String loginIp;

    private Date  loginTime;

    private String address;

    private String osName;

    private String userAgent;

    private String loginFrom;

    private String userName;


}
