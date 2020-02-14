package com.oa.biz.schedule;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.dao.schedule.ScheduleArrangeDao;
import com.oa.entity.schedule.Schedule;
import com.oa.entity.schedule.ScheduleArrange;
import com.oa.entity.schedule.ScheduleDto;
import com.oa.entity.sysuser.SysUser;
import com.oa.utils.GenerateSqlUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 安排
 *
 * @author ccl
 * @create 2017-01-18-9:14
 */
@Service
public class ScheduleArrangeBiz extends BaseBiz<ScheduleArrange, ScheduleArrangeDao> {


    @Autowired
    private ScheduleBiz scheduleBiz;


    @Autowired
    private BaseHessianBiz baseHessianBiz;

    /**
     * 更新日程安排人  先刪除  在添加
     */
    public void tx_updateScheduleArrange(Long scheduleId, String employees) {

        //添加  scheduleIds格式多个id以，隔开
        if (!StringUtils.isTrimEmpty(employees)) {
            String[] arr = employees.substring(0, employees.length()).split(",");
            List<ScheduleArrange> arranges = new ArrayList<>();
            for (String employee : arr) {
                ScheduleArrange scheduleArrange = new ScheduleArrange();
                scheduleArrange.setScheduleId(scheduleId);
                scheduleArrange.setReceiverId(Long.parseLong(employee));
                arranges.add(scheduleArrange);
            }
            this.saveBatch(arranges);
        }
    }


    /**
     * @Description:获取
     * @Date: 9:43
    //     */
    public List<ScheduleDto> getScheduleDtos(Pagination pagination, String  whereSql) {
        List<ScheduleArrange> scheduleArrangeList = this.find(pagination, whereSql);
        List<ScheduleDto> scheduleDtos=new ArrayList<>();
        ScheduleDto scheduleDto=new ScheduleDto();
        if(scheduleArrangeList.size()>0&&scheduleArrangeList!=null){
            for(ScheduleArrange scheduleArrange:scheduleArrangeList){
                Schedule schedule=scheduleBiz.findById(scheduleArrange.getScheduleId());
                if (schedule != null) {
                    scheduleDto.setStartTime(schedule.getStartTime());
                    scheduleDto.setType(schedule.getType());
                    scheduleDto.setContext(schedule.getContext());
                    scheduleDto.setId(scheduleArrange.getId());
                    scheduleDto.setStatus(schedule.getStatus());
                    scheduleDto.setScheduleId(schedule.getSenderId());
                    if(schedule.getSenderId()!=null){
                        SysUser sysUser=baseHessianBiz.getSysUserById(schedule.getSenderId());
                        scheduleDto.setSysUserName(sysUser.getUserName());
                    }
                    scheduleDtos.add(scheduleDto);
                }
            }
        }

        return scheduleDtos;
    }


}

