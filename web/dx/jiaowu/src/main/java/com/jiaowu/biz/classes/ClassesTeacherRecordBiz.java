package com.jiaowu.biz.classes;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.google.common.collect.Maps;
import com.jiaowu.biz.common.HrHessianService;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.dao.classes.ClassesTeacherRecordDao;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.classes.ClassesTeacherRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public class ClassesTeacherRecordBiz extends BaseBiz<ClassesTeacherRecord, ClassesTeacherRecordDao> {

	@Autowired
	private UserBiz userBiz;
	@Autowired
	private ClassesBiz classesBiz;
	@Autowired
	private HrHessianService hrHessianService;
	@Autowired
	private ClassesTeacherRecordDao classesTeacherRecordDao;
	/**
	 * 查询班主任记录
	 * @param pagination
	 * @param classesTeacherRecord
     * @return
     */
	public List<ClassesTeacherRecord> queryClassesTeacherRecordList(ClassesTeacherRecord classesTeacherRecord, Pagination pagination) {
		updateTeacherRecord();
		String whereSql = " classesteacherrecord.status=1";
		if (!StringUtils.isTrimEmpty(classesTeacherRecord.getTeacherName())) {
			whereSql += " and classesteacherrecord.teacherName like '%" + classesTeacherRecord.getTeacherName() + "%'";
		}
		if (!StringUtils.isTrimEmpty(classesTeacherRecord.getClassesName())) {
			whereSql += " and classesteacherrecord.classesName like '%" + classesTeacherRecord.getClassesName() + "%'";
		}

		HashMap<String, Object> paraMap = Maps.newHashMap();
		paraMap.put("whereSql",whereSql);
		int totalCount = classesTeacherRecordDao.getRecordOrderByTimeCount(paraMap);
		if(totalCount<=0){
			return null;
		}
		pagination.setTotalCount(totalCount);
		pagination.setTotalPages(totalCount%pagination.getPageSize()==0?totalCount/pagination.getPageSize():totalCount/pagination.getPageSize()+1);
		paraMap.put("start",(pagination.getCurrentPage()-1)*pagination.getPageSize());
		paraMap.put("end",pagination.getCurrentPage()*pagination.getPageSize());
		return classesTeacherRecordDao.getRecordOrderByTime(paraMap);
	}

	/**
	 * 查询全部班主任记录
	 * @return
	 */
	public List<ClassesTeacherRecord> queryAppClassesTeacherRecordList() {
		updateTeacherRecord();
		List<ClassesTeacherRecord> classesTeacherRecordList=this.find(null,"status=1");
		return classesTeacherRecordList;
	}

	private void updateTeacherRecord() {
		List<Classes> classesList=classesBiz.find(null," endTime < now()");
		if(classesList!=null && classesList.size()>0){
			for(Classes c:classesList){
				List<ClassesTeacherRecord> classesTeacherRecordList;
				if(ObjectUtils.isNotNull(c.getTeacherId())){
					classesTeacherRecordList=this.find(null," type=1 and teacherId="+c.getTeacherId()+" and classesId="+c.getId());
					if(classesTeacherRecordList==null || classesTeacherRecordList.size()<=0){
						ClassesTeacherRecord record=new ClassesTeacherRecord();
						record.setClassesId(c.getId());
						record.setClassesName(c.getName());
						record.setTeacherId(c.getTeacherId());
						record.setTeacherName(hrHessianService.queryEmployeeNameById(c.getTeacherId()));
						record.setType(1);
						this.save(record);
					}
				}
				if(ObjectUtils.isNotNull(c.getDeputyTeacherId())){
					classesTeacherRecordList=this.find(null," type=2 and  teacherId="+c.getDeputyTeacherId()+" and classesId="+c.getId());
					if(classesTeacherRecordList==null || classesTeacherRecordList.size()<=0){
						ClassesTeacherRecord record=new ClassesTeacherRecord();
						record.setClassesId(c.getId());
						record.setClassesName(c.getName());
						record.setTeacherId(c.getDeputyTeacherId());
						record.setTeacherName(hrHessianService.queryEmployeeNameById(c.getDeputyTeacherId()));
						record.setType(2);
						this.save(record);
					}
				}
			}
		}
	}


}
