package com.app.biz.common;


import com.app.entity.SysUserLog;

import java.util.Map;

public interface SysUserLogService {
    void saveLog(Map<String,String> map);
}
