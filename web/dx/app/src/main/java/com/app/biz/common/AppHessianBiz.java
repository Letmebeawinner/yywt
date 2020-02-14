package com.app.biz.common;

import com.a_268.base.util.ObjectUtils;
import com.app.biz.app.AppUpdateBiz;
import com.app.entity.AppUpdate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * app模块Hessian接口实现
 *
 * @author sk
 * @since 2017-02-23
 */
public class AppHessianBiz implements AppHessianService {

    @Autowired
    private AppUpdateBiz appUpdateBiz;

    @Override
    public Map<String, String> getAppUpdate(Integer mobileType) {
        Map<String, String> data = new HashMap<>();
        if (ObjectUtils.isNull(mobileType)) {
            return data;
        }
        String where = " mobileType=" + mobileType;
        List<AppUpdate> list = appUpdateBiz.find(null, where);
        if (ObjectUtils.isNull(list)) {
            return data;
        } else {
            return ObjectUtils.objToMap(list.get(0));
        }
    }

    @Override
    public List<Map<String, String>> listAppUpdate() {
        List<AppUpdate> list = appUpdateBiz.findAll();
        if (ObjectUtils.isNull(list)) {
            return new LinkedList<>();
        }
        return ObjectUtils.listObjToListMap(list);
    }
}
