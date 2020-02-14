package com.base.biz.common;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.ObjectUtils;
import com.base.dao.sysuserlog.SysUserLogDao;
import com.base.entity.sysuserlog.SysUserLog;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SysUserLogServiceImpl extends BaseBiz<SysUserLog, SysUserLogDao> implements SysUserLogService {

    @Override
    public void saveLog(Map<String,String> map){
        SysUserLog log = mapToEntity(map,SysUserLog.class);
        save(log);
    };
    /**
     * map集合转实体类
     *
     * @param map   map集合
     * @param clazz 实体类类对象
     * @param <T>   实体类
     * @return 实体类对象
     */
    private <T> T mapToEntity(Map<String, String> map, Class<T> clazz) {
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return gson.fromJson(json, clazz);
    }
}
