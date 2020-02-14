package com.jiaowu.dao.classes;

import com.a_268.base.core.BaseDao;
import com.jiaowu.entity.classes.ClassesTeacherRecord;

import java.util.List;
import java.util.Map;

public interface ClassesTeacherRecordDao extends BaseDao<ClassesTeacherRecord>{
    
    List<ClassesTeacherRecord> getRecordOrderByTime(Map<String,Object> map);
    
    int getRecordOrderByTimeCount(Map<String,Object> map);

}
