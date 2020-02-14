package com.jiaowu.biz.thqHour;

import com.a_268.base.core.BaseBiz;
import com.jiaowu.dao.thqHour.ThqHourDao;
import com.jiaowu.entity.thqHour.ThqHour;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author YaoZhen
 * @date 05-29, 13:59, 2018.
 */
@Service
public class ThqHourBiz extends BaseBiz<ThqHour, ThqHourDao> {

    public void saveOrUpd(ThqHour thqHour) {
        Long thqId = thqHour.getThqId();
        List<ThqHour> hours = getThqHours(thqId);

        if (CollectionUtils.isEmpty(hours)){
            this.save(thqHour);
        } else {
            thqHour.setId(hours.get(0).getId());
            this.update(thqHour);
        }
    }

    public List<ThqHour> getThqHours(Long thqId) {
        return this.find(null, " 1=1 and thqId =" + thqId);
    }


}
