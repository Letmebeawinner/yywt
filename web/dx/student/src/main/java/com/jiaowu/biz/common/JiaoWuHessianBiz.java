package com.jiaowu.biz.common;

import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jiaowu.biz.classes.ClassTypeBiz;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.holiday.HolidayBiz;
import com.jiaowu.biz.research.ResearchReportBiz;
import com.jiaowu.biz.teachingInfo.TeachingInfoBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.entity.classes.ClassType;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.holiday.Holiday;
import com.jiaowu.entity.research.ResearchReport;
import com.jiaowu.entity.teachingInfo.TeachingInfo;
import com.jiaowu.entity.user.User;
import com.jiaowu.util.GenerateSqlUtil;
import com.jiaowu.util.HessianUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//import com.jiaowu.biz.teacher.TeacherBiz;

@Service
public class JiaoWuHessianBiz implements JiaoWuHessianService {

    private static final Logger logger = LoggerFactory.getLogger(JiaoWuHessianBiz.class);
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private TeachingInfoBiz teachingInfoBiz;
    @Autowired
    private BaseHessianService baseHessianService;
    @Autowired
    private ClassesBiz classesBiz;
    @Autowired
    private ClassTypeBiz classTypeBiz;
    @Autowired
    private HolidayBiz holidayBiz;
    @Autowired
    private ResearchReportBiz researchReportBiz;
    @Autowired
    private HrHessianService hrHessianService;

    /**
     * 教师列表
     * @param pagination
     * @param whereSql
     * @return
     */
    /*public Map<String,Object> teacherList(Pagination pagination, String whereSql){
        List<Teacher> teacherList=teacherBiz.find(pagination,whereSql);
		List<Map<String,String>> listMap =ObjectUtils.listObjToListMap(teacherList);
        Map<String,String> objMap = ObjectUtils.objToMap(pagination);
        Map<String,Object> map = new HashMap<>();
        map.put("teacherList",listMap);
        map.put("pagination",objMap);
        return map;
	}*/

    /**
     * @Description 根据ID获取教师
     * @param id
     * @return
     */
	/*public Map<String,String> getTeacherById(Long id){
		Teacher teacher=teacherBiz.findById(id);
		Map<String,String> map=ObjectUtils.objToMap(teacher);
		return map;
	}*/

    /**
     * 学员列表
     *
     * @param pagination
     * @param whereSql
     * @return
     */
    public Map<String, Object> userList(Pagination pagination, String whereSql) {
        if (StringUtils.isTrimEmpty(whereSql)) {
            /*whereSql = " status=1";*/
            whereSql = " 1=1";
        } else {
            whereSql += " and 1=1";
        }
        List<User> userList = userBiz.find(pagination, whereSql);
        List<Map<String, String>> listMap = ObjectUtils.listObjToListMap(userList);
        Map<String, String> objMap = ObjectUtils.objToMap(pagination);
        Map<String, Object> map = new HashMap<>();
        map.put("userList", listMap);
        map.put("pagination", objMap);
        return map;
    }

    /**
     * @param id
     * @return
     * @Description 根据ID获取学员
     */
    public Map<String, String> getUserById(Long id) {
        User user = userBiz.findById(id);
        Map<String, String> map = ObjectUtils.objToMap(user);
        return map;
    }

    /**
     * @param num
     * @return
     * @Description 获取最新的教学动态
     */
    public List<Map<String, String>> getLatestTeachingInfo(Long num) {
        List<TeachingInfo> teachingInfoList = teachingInfoBiz.find(null, " 1=1 order by createTime desc limit " + num);
        List<Map<String, String>> listMap = ObjectUtils.listObjToListMap(teachingInfoList);
        return listMap;
    }

    /**
     * @param id
     * @return
     * @Description 查询教学动态详情
     */
    public Map<String, String> getTeachingInfoById(Long id) {
        TeachingInfo teachingInfo = teachingInfoBiz.findById(id);
        Map<String, String> map = ObjectUtils.objToMap(teachingInfo);
        return map;
    }


    /**
     * @param id
     * @return
     * @Description 查询班次信息
     */
    public Map<String, String> getClassesById(Long id) {
        Classes classes = classesBiz.findById(id);
        Map<String, String> map = ObjectUtils.objToMap(classes);
        return map;
    }

    /**
     * 教学动态列表
     *
     * @param pagination
     * @param whereSql
     * @return
     */
    public Map<String, Object> teachingInfoList(Pagination pagination, String whereSql) {
        List<TeachingInfo> teachingInfoList = teachingInfoBiz.find(pagination, whereSql);
        List<Map<String, String>> listMap = ObjectUtils.listObjToListMap(teachingInfoList);
        Map<String, String> objMap = ObjectUtils.objToMap(pagination);
        Map<String, Object> map = new HashMap<>();
        map.put("teachingInfoList", listMap);
        map.put("pagination", objMap);
        return map;
    }

    /**
     * 获取点击量最高的教学动态
     *
     * @param num
     * @return
     */
    public List<Map<String, String>> hotTeachingInfoList(Long num) {
        List<TeachingInfo> teachingInfoList = teachingInfoBiz.find(null, " 1=1 order by createTime desc limit " + num);
        List<Map<String, String>> listMap = ObjectUtils.listObjToListMap(teachingInfoList);
        return listMap;
    }

    /**
     * 增加教学动态的点击量
     *
     * @param id
     */
    public void addClickTimes(Long id) {
        TeachingInfo teachingInfo = teachingInfoBiz.findById(id);
        teachingInfo.setClickTimes(teachingInfo.getClickTimes() + 1);
        teachingInfoBiz.update(teachingInfo);
    }

    /**
     * 根据学员ID集合获取学员信息
     *
     * @param studentIds
     * @return
     */
    public List<Map<String, String>> queryStudentByIds(List<Long> studentIds) {
        List<Map<String, String>> studentInfoList = new LinkedList<Map<String, String>>();
        if (studentIds != null && studentIds.size() > 0) {
            for (Long studentId : studentIds) {
                User user = userBiz.findById(studentId);
                List<Map<String, String>> sysUserList = baseHessianService.querySysUser(3, studentId);
                if (sysUserList != null && sysUserList.size() > 0) {
                    user.setMobile(sysUserList.get(0).get("mobile"));
                    user.setEmail(sysUserList.get(0).get("email"));
                }
                studentInfoList.add(ObjectUtils.objToMap(user));
            }
        }
        return studentInfoList;
    }

    /**
     * 获取班次列表
     *
     * @param pagination
     * @param sql
     * @return
     */
    public Map<String, Object> queryClassesList(Pagination pagination, String sql) {
        if (StringUtils.isTrimEmpty(sql)) {
            sql = " status=1";
        } else {
            sql += " and status=1";
        }
        List<Classes> classesList = classesBiz.find(pagination, sql);
        classesList.forEach(c -> {
            c.setStudentTotalNum(userBiz.count(" classId= " + c.getId() + " and 1=1").longValue());
            c.setTeacherMobile(hrHessianService.getEmployeeMobileById(c.getId()));
        });

        List<Map<String, String>> classesListWithMap = ObjectUtils.listObjToListMap(classesList);
        Map<String, String> paginationMap = ObjectUtils.objToMap(pagination);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("classesList", classesListWithMap);
        map.put("pagination", paginationMap);
        return map;
    }

    @Override
    public Map<String, Object> listClassType() {
        List<ClassType> classTypeList = classTypeBiz.findAll();
        Map<String, Object> classTypeMap;
        if (ObjectUtils.isNotNull(classTypeList)) {
            classTypeMap = new HashMap<>();
            classTypeList.forEach(e -> classTypeMap.put(e.getId().toString(), e.getName()));
        } else {
            classTypeMap = new HashMap<>(0);
        }
        return classTypeMap;
    }

    @Override
    public Map<String, Object> listClasses() {
        List<Classes> classesList = classesBiz.findAll();
        Map<String, Object> classMap;
        if (ObjectUtils.isNotNull(classesList)) {
            classMap = new HashMap<>(0);
            classesList.forEach(e -> classMap.put(e.getId().toString(), e.getName()));
        } else {
            classMap = new HashMap<>(0);
        }
        return classMap;
    }

    /**
     * 查询个人的考勤记录
     */
    @Override
    public Map<String, Object> listHolidayByUserId(Pagination pagination, String employeeNo) {
        Map<String, Object> json = new HashMap<>(16);
        Map<String, String> sysuserByUserno = baseHessianService.querySysuserByUserno(employeeNo.toString());
        List<Holiday> holidays = holidayBiz.find(pagination, "userId = " + sysuserByUserno.get("id"));
        json.put("holidays", holidays);
        json.put("pagination", pagination);
        return json;
    }


    @Override
    public Map<String, String> listResearchReport(Pagination pagination, String name, Integer resultType, Integer storage) {
        Map<String, String> json = new HashMap<>(16);
        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        // 未删除 未入库
        StringBuilder whereSql = new StringBuilder(" status=0 and storage=" + storage);
        if (!StringUtils.isTrimEmpty(name)) {
            whereSql.append(" and peopleName like ").append("'%").append(name).append("%'");
        }
        whereSql.append(" and approvalDepartment = ").append(resultType);
        List<ResearchReport> researchReportList = researchReportBiz.find(pagination, whereSql.toString());
        json.put("list", gson.toJson(researchReportList));
        json.put("pagination", gson.toJson(pagination));
        return json;
    }


    @Override
    public Boolean juryResearchReport(Long id, Integer status, Integer assessmentLevel) {
        Boolean flag = false;
        ResearchReport researchReport = new ResearchReport();
        researchReport.setId(id);
        researchReport.setAudit(status);
        if (assessmentLevel != null) {
            researchReport.setAssessmentLevel(assessmentLevel);
        }
        Integer i = researchReportBiz.update(researchReport);

        if (i > 0) {
            flag = true;
        }
        return flag;
    }


    @Override
    public byte[] findResearchReportById(byte[] byId) throws Exception {
        Object id = HessianUtil.deserialize(byId);
        ResearchReport result = researchReportBiz.findById(id);
        if (result != null) {
            return HessianUtil.serialize(ObjectUtils.objToMap(result));
        } else {
            return null;
        }

    }

    @Override
    public int countResearchReport(String year) {
        return researchReportBiz.count("approvalDepartment = 2 and year(createTime) = " + year);
    }

    @Override
    public int countResearchReportByKy(String whereTime, int keYan) {
        return researchReportBiz.count(whereTime + " and approvalDepartment = " + keYan
                + " and storage = 0 and archive = 0 and status = 0");
    }

    @Override
    public Integer updateResearchReport(String researchReport) {
        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        ResearchReport report = gson.fromJson(researchReport, ResearchReport.class);
        return researchReportBiz.update(report);
    }

    @Override
    public Map<String, String> listResearchReportByObj(Pagination pagination, String hessianReport) {
        Map<String, String> json = new HashMap<>(16);

        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        ResearchReport researchReport = gson.fromJson(hessianReport, ResearchReport.class);
        String whereSql = GenerateSqlUtil.getSql(researchReport);
        List<ResearchReport> researchReportList = researchReportBiz.find(pagination, whereSql + "order by id desc");
        json.put("list", gson.toJson(researchReportList));
        json.put("pagination", gson.toJson(pagination));
        return json;
    }

    @Override
    public int queryForTeacherKyReportListByTime(String whereTime) {
        List<ResearchReport> researchReportList =
                researchReportBiz.find(null,
                        whereTime + " and type = 'teacher' and approvalDepartment = 1");
        return researchReportList.size();
    }

    @Override
    public int queryForStudentKyReportListByTime(String whereTime) {
        List<ResearchReport> researchReportList =
                researchReportBiz.find(null,
                        whereTime + " and type = 'student' and approvalDepartment = 1");
        return researchReportList.size();
    }

    @Override
    public Boolean removeReportById(Long id) {
        try {
            researchReportBiz.deleteById(id);
        } catch (Exception e) {
            logger.error("JiaoWuHessianBiz.removeReportById", e);
            return false;
        }
        return true;
    }


    /**
     * 修改学员信息
     *
     * @return
     */
    @Override
    public String updateUserInfo(Map<String, Object> userInfoMap) throws Exception {
        String userId = userInfoMap.get("userId").toString();
        String cardId = userInfoMap.get("cardId").toString();
        String timeCardId = userInfoMap.get("timeCardId").toString();
        String roomInformation = userInfoMap.get("roomInformation").toString();

        User user = new User();
        user.setId(Long.parseLong(userId));
        user.setCardNo(cardId);
        user.setTimeCardNo(timeCardId);
        user.setRoomInformation(roomInformation);
        userBiz.update(user);
        return null;
    }


}
