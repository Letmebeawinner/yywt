package com.oa.biz.word;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.ObjectUtils;
import com.oa.dao.word.TaoHongDao;
import com.oa.entity.word.TaoHong;
import org.springframework.stereotype.Service;

/**
 * 套红biz
 * Created by xiangdong.chang on 2018/5/31 0031.
 */
@Service
public class TaoHongBiz extends BaseBiz<TaoHong, TaoHongDao> {
    /**
     * 添加修改
     *
     * @param taoHong
     */
    public void saveOrupdateTaoHong(TaoHong taoHong) {
        if (ObjectUtils.isNotNull(taoHong.getId())) {
            this.update(taoHong);
        } else {
            this.save(taoHong);
        }
    }
}
