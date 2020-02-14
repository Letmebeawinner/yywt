package com.oa.biz.meetingapply;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.biz.meeting.MeetingBiz;
import com.oa.dao.meetingapply.MeetingapplyDao;
import com.oa.entity.meeting.Meeting;
import com.oa.entity.meetingapply.MeetingApplyDto;
import com.oa.entity.meetingapply.Meetingapply;
import com.oa.entity.news.News;
import com.oa.entity.news.NewsDto;
import com.oa.entity.news.NewsType;
import com.oa.entity.sysuser.SysUser;
import com.oa.utils.GenerateSqlUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 会议室申请
 *
 * @author ccl
 * @create 2016-12-28-10:37
 */
@Service
public class MeetingapplyBiz extends BaseBiz<Meetingapply,MeetingapplyDao> {

    @Autowired
    private MeetingBiz meetingBiz;

    @Autowired
    private BaseHessianBiz baseHessianBiz;


    /**
     * @Description: 获取新闻拓展类
     * @author: lzh
     * @Param: [pagination, newsDto]
     * @Return: java.util.List<com.oa.entity.news.NewsDto>
     * @Date: 9:43
     */
    public List<MeetingApplyDto> getMeetingApplyDtos(Pagination pagination, Meetingapply meetingapply) {
        String whereSql = GenerateSqlUtil.getSql(meetingapply);
        List<Meetingapply> meetingapplies = this.find(pagination, whereSql);
        List<MeetingApplyDto> meetingApplyDtos = null;
        meetingApplyDtos = meetingapplies.stream()
                .map(me -> convertMeetingToDto(me))
                .collect(Collectors.toList());
        return meetingApplyDtos;
    }

    /**
     * @Description: 将类的属性全部放到拓展类
     * @author: lzh
     * @Param: [news]
     * @Return: com.oa.entity.news.NewsDto
     * @Date: 11:40
     */
    private MeetingApplyDto convertMeetingToDto(Meetingapply meetingapply) {
        MeetingApplyDto meetingApplyDto = new MeetingApplyDto();
        BeanUtils.copyProperties(meetingapply, meetingApplyDto);
        if (meetingApplyDto.getMeetId() != null) {
            Meeting meeting = meetingBiz.findById(meetingApplyDto.getMeetId());
            if (meeting != null) {
                meetingApplyDto.setMeetingName(meeting.getName());
            }
        }
        if (meetingApplyDto.getUserId() != null && meetingApplyDto.getUserId() > 0) {
            SysUser sysUser = baseHessianBiz.getSysUserById(meetingApplyDto.getUserId());
            meetingApplyDto.setUserName(sysUser.getUserName());
        }
        return meetingApplyDto;
    }


}
