package com.oa.biz.notice;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.oa.biz.noticetype.NoticeTypeBiz;
import com.oa.dao.notice.NoticeDao;
import com.oa.entity.notice.Notice;
import com.oa.entity.notice.NoticeDepartment;
import com.oa.entity.notice.NoticeDto;
import com.oa.entity.noticetype.NoticeType;
import com.oa.utils.GenerateSqlUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 公告管理
 *
 * @author ccl
 * @create 2016-12-29-18:10
 */
@Service
public class NoticeBiz extends BaseBiz<Notice,NoticeDao>{
    
    @Autowired
    private NoticeDepartmentBiz noticeDepartmentBiz;

    @Autowired
    private NoticeTypeBiz noticeTypeBiz;
    /**
     * @Description:发布公告，同时更新发布状态
     * @author: lzh
     * @Param: [id, ids], id公告id，ids部门ids
     * @Return: void
     * @Date: 10:52
     */
    public void tx_publish(Long id, Long [] ids) {
        Notice notice = new Notice();
        notice.setId(id);
        notice.setPublish(1);
        this.update(notice);
        NoticeDepartment noticeDepartment = new NoticeDepartment();
        noticeDepartment.setNoticeId(id);
        //先删除再增加
        List<NoticeDepartment> noticeDepartments = noticeDepartmentBiz.find(null, GenerateSqlUtil.getSql(noticeDepartment));
        List<Long> idSets = noticeDepartments.stream()
                                             .map(NoticeDepartment::getId)
                                             .collect(Collectors.toList());
        //删除掉和这个公告相关的
        noticeDepartmentBiz.deleteByIds(idSets);
        List<NoticeDepartment> departments = Arrays.asList(ids)
                                                   .stream()
                                                   .map(departmentId -> idConvertToObject(id, departmentId))
                                                   .collect(Collectors.toList());
        noticeDepartmentBiz.saveBatch(departments);
    }

    /**
     * @Description:id转换成实体
     * @author: lzh
     * @Param: [noticeId, departmentId]
     * @Return: com.oa.entity.notice.NoticeDepartment
     * @Date: 11:06
     */
    private NoticeDepartment idConvertToObject(Long noticeId, Long departmentId) {
        NoticeDepartment noticeDepartment = new NoticeDepartment();
        noticeDepartment.setNoticeId(noticeId);
        noticeDepartment.setDepartmentId(departmentId);
        return noticeDepartment;
    }


    public List<Notice> queryAppNotice(){
        List<Notice> noticeList=this.find(null," publish=1");
        return noticeList;
    }



    public List<NoticeDto> getNoticeDtos(Pagination pagination, Notice newsEntity) {
        String whereSql = GenerateSqlUtil.getSql(newsEntity);
        List<Notice> notices = this.find(pagination, whereSql);
        List<NoticeDto> newsDtos = null;
        newsDtos = notices.stream()
                .map(notice-> convertNoticeToDto(notice))
                .collect(Collectors.toList());
        return newsDtos;
    }

    /**
     * @Description: 将类的属性全部放到拓展类
     * @author: lzh
     * @Param: [news]
     * @Return: com.oa.entity.news.NewsDto
     * @Date: 11:40
     */
    private NoticeDto convertNoticeToDto(Notice notice) {
        NoticeDto noticesDto = new NoticeDto();
        BeanUtils.copyProperties(notice, noticesDto);
        if (noticesDto.getTypeId() != null) {
            NoticeType newsType = noticeTypeBiz.findById(noticesDto.getTypeId());
            if (newsType != null) {
                noticesDto.setTypeName(newsType.getName());
            }
        }
        return noticesDto;
    }



}
