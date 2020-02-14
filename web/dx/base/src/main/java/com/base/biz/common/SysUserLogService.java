package com.base.biz.common;

import com.base.entity.sysuserlog.SysUserLog;

import java.util.Map;

public interface SysUserLogService {
    void saveLog(Map<String,String> map);
}
