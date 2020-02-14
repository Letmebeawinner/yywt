package com.jiaowu.biz.teachEvaluate;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.ObjectUtils;
import com.jiaowu.dao.teachEvaluate.TeachEvaluateDao;
import com.jiaowu.entity.teachEvaluate.TeachEvaluate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiangdong on 2017/8/29.
 */
@Service
public class TeachEvaluateBiz extends BaseBiz<TeachEvaluate, TeachEvaluateDao> {

    /**
     * 根据讲师查询所有学员
     *
     * @param classId
     */
    public int getAverageNum(Long classId) {
        List<TeachEvaluate> teachEvaluates = this.find(null, " classId=" + classId);
        Integer a = 0;
        if (ObjectUtils.isNotNull(teachEvaluates)) {
            for (TeachEvaluate t : teachEvaluates) {
                a += t.getTotal();
            }
            return a / this.getUserNum(classId);
        }

        return 0;
    }

    /**
     * 查询学员个数
     *
     * @param classId
     * @return
     */
    public int getUserNum(Long classId) {
        List<TeachEvaluate> teachEvaluates = this.find(null, " classId=" + classId + " group by  userId");
        return teachEvaluates == null ? 0 : teachEvaluates.size();
    }
}
