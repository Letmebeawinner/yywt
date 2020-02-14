package com.app.biz.app;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.ObjectUtils;
import com.app.dao.AppUpdateDao;
import com.app.entity.AppUpdate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * app更新 Service
 */
@Service
public class AppUpdateBiz extends BaseBiz<AppUpdate, AppUpdateDao> {

    @Override
    public void save(AppUpdate appUpdate) {
        Integer mobileType = appUpdate.getMobileType();
        if (mobileType != null) {
            String where = " mobileType=" + mobileType;
            List<AppUpdate> appUpdateList = this.find(null, where);
            if (ObjectUtils.isNotNull(appUpdateList))
                this.updateByStrWhere(appUpdate, where);
            else super.save(appUpdate);
        }
    }
}
