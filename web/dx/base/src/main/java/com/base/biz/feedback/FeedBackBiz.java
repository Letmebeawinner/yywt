package com.base.biz.feedback;


import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.base.biz.user.SysUserBiz;
import com.base.dao.feedback.FeedBackDao;
import com.base.entity.feedback.FeedBack;
import com.base.entity.feedback.FeedBackDto;
import com.base.entity.user.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 意见反馈
 *
 * @author ccl
 * @create 2017-03-14-11:44
 */
@Service
public class FeedBackBiz extends BaseBiz<FeedBack,FeedBackDao>{

    @Autowired
    private SysUserBiz sysUserBiz;

    /**
     * 查询所有的意见反馈
     * @param pagination
     * @param whereSql
     * @return
     */
    public List<FeedBackDto> getFeedBackDtos(Pagination pagination, String whereSql) {
        List<FeedBack> notices = this.find(pagination, whereSql);
        List<FeedBackDto> feedBackDtos = null;
        feedBackDtos = notices.stream()
                .map(notice-> convertNoticeToDto(notice))
                .collect(Collectors.toList());
        return feedBackDtos;
    }

    /**
     * @Description: 将类的属性全部放到拓展类
     * @author: lzh
     * @Param: [news]
     * @Return:
     * @Date: 11:40
     */
    private FeedBackDto convertNoticeToDto(FeedBack feedBack) {
        FeedBackDto feedBackDto = new FeedBackDto();
        BeanUtils.copyProperties(feedBack, feedBackDto);
        if (feedBackDto.getUserId()!= null) {
            SysUser sysUser = sysUserBiz.findById(feedBackDto.getUserId());
            if (sysUser != null) {
                feedBackDto.setSysUserName(sysUser.getUserName());
            }
        }
        return feedBackDto;
    }


}
