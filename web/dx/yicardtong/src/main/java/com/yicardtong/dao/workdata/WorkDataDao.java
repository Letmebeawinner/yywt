package com.yicardtong.dao.workdata;

import com.a_268.base.core.BaseDao;
import com.yicardtong.entity.work.WorkDayData;
import com.yicardtong.entity.workdaydata.WorkDayDataVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 查考勤记录
 *
 * @author YaoZhen
 * @date 04-04, 16:21, 2018.
 */
@Component
public interface WorkDataDao extends BaseDao<WorkDayData> {

    /**
     * 查询打卡树
     * @param ids 考勤卡号
     * @return 考勤记录
     */
    List<WorkDayDataVO> queryAllWorkData(@Param("ids") String ids,
                                         @Param("start") String strStart,
                                         @Param("end") String strEnd);

    List<WorkDayDataVO> queryAllWorkDataByPage(@Param("param") Map<String, Object> param);

    int countQueryAllWorkDataByPage(@Param("param") String param);
}
