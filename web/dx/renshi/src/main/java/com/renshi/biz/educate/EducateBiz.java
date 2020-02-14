package com.renshi.biz.educate;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.DateUtils;
import com.a_268.base.util.StringUtils;
import com.renshi.dao.educate.EducateDao;
import com.renshi.entity.educate.Educate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 培训项目Biz
 *
 * @author 268
 */
@Service
public class EducateBiz extends BaseBiz<Educate, EducateDao> {

//    /**
//     * 培训项目列表
//     *
//     * @param educate
//     */
//    public List<Educate> getEducateList(Educate educate, Pagination pagination) {
//        String whereSql = " 1=1";
//        whereSql += " and status!=2";
//        Long id = educate.getId();
//        if (id!=null && id > 0) {
//            whereSql += " and id=" + id;
//        }
//
//        if (educate.getBeginTime()!=null) {
//            whereSql += " and left(beginTime,10) = left('" + DateUtils.format(educate.getBeginTime(),"yyyy-MM-dd HH:mm:ss") + "',10)";
//        }
//        String teacher = educate.getTeacher();
//        if (!StringUtils.isTrimEmpty(teacher)) {
//            whereSql += " and teacher like '%" + teacher + "%'";
//        }
//        String name = educate.getName();
//        if (!StringUtils.isTrimEmpty(name)) {
//            whereSql += " and name like '%" + name + "%'";
//        }
//        whereSql += " order by id desc";
//        return this.find(pagination,whereSql);
//    }
}
