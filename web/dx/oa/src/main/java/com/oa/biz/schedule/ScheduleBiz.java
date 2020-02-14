package com.oa.biz.schedule;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.dao.schedule.ScheduleDao;
import com.oa.entity.schedule.Schedule;
import com.oa.entity.schedule.ScheduleDto;
import com.oa.utils.GenerateSqlUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 日程安排
 *
 * @author ccl
 * @create 2017-01-17-18:36
 */
@Service
public class ScheduleBiz extends BaseBiz<Schedule,ScheduleDao> {

    @Autowired
    private BaseHessianBiz baseHessianBiz;
    /**
     * @Description:获取
     * @author: lzh
     * @Param: [pagination, newsDto]
     * @Return: java.util.List<com.oa.entity.news.NewsDto>
     * @Date: 9:43
//     */
    public List<ScheduleDto> getScheduleDtos(Pagination pagination, Schedule _schedule) {
        String whereSql = GenerateSqlUtil.getSql(_schedule);
        List<Schedule> schedules = this.find(pagination, whereSql);
        List<ScheduleDto> scheduleDtos = null;
        scheduleDtos = schedules.stream()
                .map(schedule -> convertScheduleToDto(schedule))
                .collect(Collectors.toList());
        return scheduleDtos;
    }

    /**
     * @Description: 将类的属性全部放到拓展类
     * @author: lzh
     * @Param: [news]
     * @Return: com.oa.entity.news.NewsDto
     * @Date: 11:40
     */
    private ScheduleDto convertScheduleToDto(Schedule schedule) {
        ScheduleDto scheduleDto = new ScheduleDto();
        BeanUtils.copyProperties(schedule, scheduleDto);
        return scheduleDto;
    }


}
