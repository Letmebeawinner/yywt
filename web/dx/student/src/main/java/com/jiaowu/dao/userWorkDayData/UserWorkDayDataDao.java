package com.jiaowu.dao.userWorkDayData;

import com.a_268.base.core.BaseDao;
import com.jiaowu.entity.material.MaterialAnalysisDTO;
import com.jiaowu.entity.userWorkDayData.UserWorkDayData;
import com.jiaowu.entity.userWorkDayData.UserWorkDayDataDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by 李帅雷 on 2017/10/19.
 */
public interface UserWorkDayDataDao extends BaseDao<UserWorkDayData> {
    /**
     * 查询日期集合
     *
     * @return
     */
    public List<UserWorkDayData> getDateStatisticsList(String whereSql);
    public List<UserWorkDayData> getDatePersonnelList(String whereSql);


    public List<UserWorkDayDataDTO> studentWorkDayDataList(Map<String,Object> map);

    public int studentWorkDayDataListCount(Map<String,Object> map);

}
