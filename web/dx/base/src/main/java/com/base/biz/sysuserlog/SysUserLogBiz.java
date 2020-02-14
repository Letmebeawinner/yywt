package com.base.biz.sysuserlog;

import com.a_268.base.core.BaseBiz;
import com.base.biz.common.SysUserLogService;
import com.base.biz.common.SysUserService;
import com.base.dao.sysuserlog.SysUserLogDao;
import com.base.entity.sysuserlog.SysUserLog;
import org.springframework.stereotype.Service;

@Service
public class SysUserLogBiz extends BaseBiz<SysUserLog, SysUserLogDao>{
   public void saveLog(SysUserLog log){
       save(log);
    };

}
