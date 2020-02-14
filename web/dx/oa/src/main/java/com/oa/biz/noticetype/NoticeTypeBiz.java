package com.oa.biz.noticetype;

import com.a_268.base.core.BaseBiz;
import com.oa.dao.noticetype.NoticeTypeDao;
import com.oa.entity.noticetype.NoticeType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公告类型
 *
 * @author ccl
 * @create 2016-12-29-9:44
 */
@Service
public class NoticeTypeBiz extends BaseBiz<NoticeType,NoticeTypeDao>{

    /**
     * @Description:查询所有类型
     * @author: ccl
     * @Param: []
     * @Return: java.util.List<com.oa.entity.noticetype.NoticeType>
     * @Date: 2016-12-29
     */
    public List<NoticeType> noticeTypeList(){
        List<NoticeType> noticeTypeList=this.find(null,"1=1");
        return noticeTypeList;
    }

}
