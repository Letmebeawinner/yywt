package com.jiaowu.biz.userWorkDayData;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.jiaowu.dao.userWorkDayData.UserWorkDayDataDao;
import com.jiaowu.entity.material.MaterialAnalysisDTO;
import com.jiaowu.entity.userWorkDayData.UserWorkDayData;
import com.jiaowu.entity.userWorkDayData.UserWorkDayDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by 李帅雷 on 2017/10/19.
 */
@Service
public class UserWorkDayDataBiz extends BaseBiz<UserWorkDayData, UserWorkDayDataDao> {
    @Autowired
    private UserWorkDayDataDao userWorkDayDataDao;

    public List<UserWorkDayDataDTO> studentWorkDayDataList(Map<String, Object> conditionMap, Pagination pagination) {
        int totalCount = userWorkDayDataDao.studentWorkDayDataListCount(conditionMap);
        if (totalCount <= 0) {
            return null;
        }
        pagination.setTotalCount(totalCount);
        pagination.setTotalPages(totalCount % pagination.getPageSize() == 0 ? totalCount / pagination.getPageSize() : totalCount / pagination.getPageSize() + 1);
        conditionMap.put("start", (pagination.getCurrentPage() - 1) * pagination.getPageSize());
        conditionMap.put("end", pagination.getCurrentPage() * pagination.getPageSize());
        List<UserWorkDayDataDTO> studentWorkDayDataList = userWorkDayDataDao.studentWorkDayDataList(conditionMap);
        return studentWorkDayDataList;
    }
}
