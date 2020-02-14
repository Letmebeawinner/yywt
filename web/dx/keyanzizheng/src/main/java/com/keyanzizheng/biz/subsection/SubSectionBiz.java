package com.keyanzizheng.biz.subsection;

import com.a_268.base.core.BaseBiz;
import com.keyanzizheng.dao.subsection.SubSectionDao;
import com.keyanzizheng.entity.subsection.SubSection;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 所属处室
 * <p>教研处<p>
 *
 * @author YaoZhen
 * @date 12-20, 13:43, 2017.
 */
@Service
public class SubSectionBiz extends BaseBiz<SubSection, SubSectionDao> {

    public List<SubSection> querySubSectionListBySysUserId(Long sysUserId){
        List<SubSection> subSectionList=this.find(null," linkId="+sysUserId);
        if(subSectionList==null || subSectionList.size()<=0){
            subSectionList=this.find(null,"linkId is null");
        }
        return subSectionList;
    }

}
