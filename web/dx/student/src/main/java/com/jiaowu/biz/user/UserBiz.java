package com.jiaowu.biz.user;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.classes.ClassTypeBiz;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.common.BaseHessianService;
import com.jiaowu.biz.common.umc.DpWebService;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.biz.unit.UnitBiz;
import com.jiaowu.common.FileExportImportUtil;
import com.jiaowu.common.RegExpressionUtil;
import com.jiaowu.common.StudentCommonConstants;
import com.jiaowu.dao.user.UserDao;
import com.jiaowu.entity.classes.ClassType;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.course.CourseArrange;
import com.jiaowu.entity.teach.TeachingProgramCourse;
import com.jiaowu.entity.unit.Unit;
import com.jiaowu.entity.user.User;
import com.jiaowu.entity.userExcelRecord.UserExcelRecord;
import com.jiaowu.entity.userInfo.UserInfo;
import com.jiaowu.util.StringUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserBiz extends BaseBiz<User, UserDao> {
    private static final int HMAC_KEY_LEN = 60;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private ClassesBiz classesBiz;
    @Autowired
    private BaseHessianService baseHessianService;
    @Autowired
    private CourseArrangeBiz courseArrangeBiz;
    @Autowired
    private ClassTypeBiz classTypeBiz;
    @Autowired
    private UnitBiz unitBiz;
    @Autowired
    private DpWebService dpWebService;

    /**
     * 判断字符穿是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public String createUser(User user, String year, String month, Map<String, String> userMap) {

        Classes classes = classesBiz.findById(user.getClassId());

        int userNum = this.count(" status=1 and classId=" + classes.getId());
        user.setSerialNumber(calculateSerialNumber((long) userNum));
        user.setStudentId(year + month + user.getClassTypeId() + classes.getClassNumber() + user.getSerialNumber());

        String mobile = user.getMobile();
        String email = user.getEmail();
        String password = user.getPassword();
        user.setMobile(null);
        user.setEmail(null);
        user.setPassword(null);
        user.setConfirmPassword(null);
        //如果当前登录人是管理员,则设置user的sysUserId为当前管理员.
        if (!"3".equals(userMap.get("userType"))) {
            user.setSysUserId(Long.parseLong(userMap.get("id")));
        }
        super.save(user);
        user.setMobile(mobile);
        user.setEmail(email);
        user.setPassword(password);
        //保存学员信息之后,获取到学员的ID,设置userInfo的ID,将该ID保存到base系统中.
        Long linkId = user.getId();
        user.setId(linkId);

        String result = this.addStudentToBase(user);
        // BASE用户添加失败后, 删除本系统用户
        if (StringUtil.isNotNumeric(result)) {
            super.deleteById(linkId);
            return result;
        }

        List<Map<String, String>> studentMap = baseHessianService.queryStudentByMobile(user.getMobile());
        if (studentMap != null && studentMap.size() > 0) { //再次报名
            // 删了原来的wifi账号
            dpWebService.delWifi(user.getMobile());
        }
        // 开通wifi
        boolean isUMCSuccess = dpWebService.saveWifiUser(user.getMobile(), user.getName(), classes.getEndTime(), linkId);

        // 如果BASE里添加用户成功, 但是开通WIFI用户失败,
        // 需要删除BASE里的SYSUSER 和 本DB用户
        if (isNumeric(result) && !isUMCSuccess) {
            super.deleteById(user.getId());
//            baseHessianService.deleteSysUserByUserNo(result);
            return "WIFI用户添加失败, 联系UMC管理员";
        }

        Map<String, String> sysUserMap = baseHessianService.querySysuserByUserno(result);
        Map<String, String> sysUserRoleMap = new HashMap<>();
        sysUserRoleMap.put("roleId", StudentCommonConstants.STUDENT_ROLE_ID + "");
        sysUserRoleMap.put("userId", sysUserMap.get("id"));
        baseHessianService.addSysUserRole(sysUserRoleMap);
        return result;
    }

    public void updateUser(User user) {
        updateStudentMobileAndEmailToBase(user);
        //教务user表中没有mobile和email两个字段
        user.setMobile(null);
        user.setEmail(null);
        update(user);
    }

    public User setUserByUserInfo(UserInfo userInfo) {
        User user = new User();
        user.setName(userInfo.getName());
        user.setIdNumber(userInfo.getIdNumber());
        user.setSex(userInfo.getSex());
        user.setAge(userInfo.getAge());
        user.setNote(userInfo.getNote());
        return user;
    }

    public User setUserByUserInfo(UserInfo userInfo, String year, String month) {
        User user = new User();
        user.setClassTypeId(userInfo.getClassTypeId());
        user.setClassTypeName(userInfo.getClassTypeName());
        user.setClassId(userInfo.getClassId());
        user.setClassName(userInfo.getClassName());
//        user.setSerialNumber(userInfo.getSerialNumber());
        user.setName(userInfo.getName());
//		user.setJob(userInfo.getJob());
        user.setIdNumber(userInfo.getIdNumber());
        user.setSex(userInfo.getSex());
        user.setAge(userInfo.getAge());
        Classes classes = classesBiz.findById(user.getClassId());
        int userNum = this.count(" status=1 and classId=" + classes.getId());
        user.setSerialNumber(calculateSerialNumber((long) userNum));
        user.setStudentId(year + month + user.getClassTypeId() + classes.getClassNumber() + user.getSerialNumber());
        user.setStatus(2);
        user.setIsMonitor(0);
        user.setBaodao(0);
        user.setHasApprove(0);
        user.setHasCheck(0);
        user.setNote(userInfo.getNote());
        return user;
    }

    public String calculateSerialNumber(Long studentTotalNum) {
        Long serialNumber = studentTotalNum + 1;
        if (serialNumber > 99) {
            return serialNumber + "";
        } else if (serialNumber > 9) {
            return "0" + serialNumber;
        } else {
            return "00" + serialNumber;
        }
    }


    private String addStudentToBase(User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", user.getName());
        map.put("password", user.getPassword());
        map.put("email", user.getEmail());
        map.put("mobile", user.getMobile());
        map.put("status", 0);
        map.put("userType", 3);
        map.put("linkId", user.getId());
        return baseHessianService.addSysUserSignUp(map);
    }


    private void updateStudentMobileAndEmailToBase(User user) {
        List<Map<String, String>> baseUserList = baseHessianService.querySysUser(3, user.getId());
        if (baseUserList != null && baseUserList.size() > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", baseUserList.get(0).get("id"));
            map.put("email", user.getEmail());
            map.put("mobile", user.getMobile());
            map.put("userName", baseUserList.get(0).get("userName"));
            map.put("linkId", Long.parseLong(baseUserList.get(0).get("linkId")));
            map.put("userType", 3);
            baseHessianService.updateSysUser(map);
        }
    }

    public String batchImportUnit(MultipartFile myFile, HttpServletRequest request) throws Exception {
        HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
        HSSFSheet sheet = wookbook.getSheetAt(0);
        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        for (int i = 0; i < rows + 1; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null) {
                String userName = getCellValue(row.getCell((short) 0));
                String userNo = getCellValue(row.getCell((short) 1));
                System.out.println("userNo---------" + userNo + "-----userName-------" + userName);
                Long unitId = baseHessianService.querySysuserByUsernoUpdateUserName("000"+userNo, userName);
                System.out.println("unitId-------------" + unitId);
                if(unitId.intValue()>0){
                    Unit unit = new Unit();
                    unit.setId(unitId);
                    unit.setNum(null);
                    unit.setClassNum(null);
                    unit.setName(userName);
                    unitBiz.update(unit);
                }
            }
        }

        return "";
    }

    public String batchImportStudent(MultipartFile myFile, HttpServletRequest request) throws Exception {
        Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
//        if (!userMap.get("userType").equals("1") && !userMap.get("userType").equals("5")) {
//            return "您无权限导入学员";
//        }
        Long userId = SysUserUtils.getLoginSysUserId(request);
        List<Long> roleList = baseHessianService.queryUserRoleByUserId(userId);
        boolean isManage = false;
        if (roleList != null) {
            for (Long roleId : roleList) {
                if (roleId.longValue() == 25) {//如果具有学员处角色
                    isManage = true;
                    break;
                }
            }
        }

        //报名截至时间
//        Map<String, String> resultMap = baseHessianService.queryDepartmentBySysuserId(SysUserUtils.getLoginSysUserId(request));

        List<Long> departmentIdList = new ArrayList<>();
        departmentIdList.add(78L);//学员部门
        Long sysUserId = Long.parseLong(userMap.get("id"));
        StringBuffer msg = new StringBuffer();
        Pattern pattern = Pattern.compile("[0-9]*");
        Pattern pattern1 = Pattern.compile("^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$");
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        String month = (calendar.get(Calendar.MONTH) + 1) + "";
        if (month.length() == 1) {
            month = "0" + month;
        }
        String[] politicalStatusArray = new String[]{"中共党员", "民主党派", "无党派人士", "群众", "其它"};
        // datalist拼装List<String[]> datalist,
        HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
        HSSFSheet sheet = wookbook.getSheetAt(0);

        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        for (int i = 1; i < rows + 1; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null) {
                String classTypeId = getCellValue(row.getCell((short) 0));//班型ID
                if (StringUtils.isTrimEmpty(classTypeId) || Long.parseLong(classTypeId) <= 0) {
                    /*if(rows==2&&i==2){//当学员记录为0 1 2时,rows均为2.所以就可能该行就是没有数据.这样写就有一个问题,如果学员记录有两条,第二条记录的班型ID为空,就无法正确提示,不过这已经将失误降到最小了.
                        break;
                    }*/
                    msg.append("第" + i + "行班型ID格式错误;");
                    continue;
                }
                String classId = getCellValue(row.getCell((short) 1));//班次ID
                if (StringUtils.isTrimEmpty(classId) || Long.parseLong(classId) <= 0) {
                    msg.append("第" + i + "行班次ID格式错误;");
                    continue;
                }
                String name = getCellValue(row.getCell((short) 2));//名称
                if (StringUtils.isTrimEmpty(name)) {
                    msg.append("第" + i + "行名称为空;");
                    continue;
                }
                String idNumber = getCellValue(row.getCell((short) 3));//身份证号
                if (StringUtils.isTrimEmpty(idNumber)) {
                    msg.append("第" + i + "行身份证号为空;");
                    continue;
                } else {
                    Matcher isNum = pattern1.matcher(idNumber);
                    if (!isNum.matches()) {
                        msg.append("第" + i + "行身份证号格式错误;");
                        continue;
                    }
                    if ((idNumber.length() != 18 && idNumber.length() != 15)) {
                        msg.append("第" + i + "行身份证号格式错误;");
                        continue;
                    }
                    /*List<User> userList=this.find(null," status!=0 and idNumber='"+idNumber+"'");
                    if(userList!=null&&userList.size()>0){
                        for(User tempUser:userList){
                            if(tempUser.getClassId().equals(Long.parseLong(classId))){
                                msg.append("第"+i+"行身份证号重复");
                                continue;
                            }
                        }

                    }*/
                }
                String mobile = getCellValue(row.getCell((short) 4));//手机号
                if (StringUtils.isTrimEmpty(mobile) || !RegExpressionUtil.isMobile(mobile.toLowerCase())) {
                    msg.append("第" + i + "行手机号格式错误;");
                    continue;
                }
                String email = getCellValue(row.getCell((short) 5));//邮箱
                if (!StringUtils.isTrimEmpty(email) && !RegExpressionUtil.isEmail(email.toLowerCase())) {
                    msg.append("第" + i + "行邮箱格式错误;");
                    continue;
                }
                String password = getCellValue(row.getCell((short) 6));//密码
                if (StringUtils.isTrimEmpty(password)) {
                    msg.append("第" + i + "行密码格式错误;");
                    continue;
                }
                String sex = getCellValue(row.getCell((short) 7));//性别
                if (StringUtils.isTrimEmpty(sex) || (!sex.equals("男") && (!sex.equals("女")))) {
                    msg.append("第" + i + "行性别格式错误;");
                    continue;
                }
				/*String age = getCellValue(row.getCell((short) 8));//年龄
				if(StringUtils.isTrimEmpty(age)||Long.parseLong(age)<=0){
					msg.append("第"+i+"行年龄格式错误;");
					continue;
				}*/
                String age = getAgeByIdNumber(idNumber);
                String politicalStatus = getCellValue(row.getCell((short) 8));//政治面貌
                Long politicalStatusInt = -1L;

				/*if(!"中共党员".equals(politicalStatus)&&!"民主党派".equals(politicalStatus)&&!"无党派人士".equals(politicalStatus)&&!"群众".equals(politicalStatus)&&!"其它".equals(politicalStatus)){
					msg.append("第"+i+"行政治面貌填写错误;");
					continue;
				}*/
                int j;
                for (j = 0; j < politicalStatusArray.length; j++) {
                    if (politicalStatusArray[j].equals(politicalStatus)) {
                        politicalStatusInt = (long) j;
                        break;
                    }
                }
                if (j == politicalStatusArray.length) {
                    msg.append("第" + i + "行政治面貌填写错误;");
                    continue;
                }
                String qualification = getCellValue(row.getCell((short) 9));//学历
                if (StringUtils.isTrimEmpty(qualification)) {
                    msg.append("第" + i + "行学历为空;");
                    continue;
                }
                String unitId = getCellValue(row.getCell((short) 10));//单位ID
//                Long unitId=0L;
                /*List<Unit> unitList=unitBiz.find(null," status=1 and name='"+unit+"'");
                if(unitList==null||unitList.size()==0){
                }else{
                    unitId=unitList.get(0).getId();
                }*/
                if (unitId == null || "".equals(unitId) || !pattern.matcher(unitId).matches()) {
                    msg.append("第" + i + "行单位ID填写错误;");
                    continue;
                }
                List<Unit> unitList = unitBiz.find(null, " status=1 and id=" + unitId);
                String unit = null;
                if (unitList == null || unitList.size() == 0) {
                    msg.append("第" + i + "行单位ID填写错误,不存在该单位;");
                    continue;
                } else {
                    unit = unitList.get(0).getName();
                }
                String job = getCellValue(row.getCell((short) 11));//职务职称
                if (StringUtils.isTrimEmpty(job)) {
                    msg.append("第" + i + "行职务职称填写错误;");
                    continue;
                }
                String business = getCellValue(row.getCell((short) 12));//级别
                Integer businessInt = 0;
                String[] businessArray = new String[]{"正厅", "巡视员", "副厅", "副巡视员", "正县", "副县", "调研员", "副调研员", "正科", "副科"};
                for (j = 0; j < businessArray.length; j++) {
                    if (businessArray[j].equals(business)) {
                        businessInt = j + 1;
                        break;
                    }
                }
                if (j == businessArray.length) {
                    msg.append("第" + i + "行级别填写错误;");
                    continue;
                }
                String nationality = getCellValue(row.getCell((short) 13));//民族
                if (StringUtils.isTrimEmpty(nationality)) {
                    msg.append("第" + i + "行民族填写错误;");
                    continue;
                }
                String submittedName = getCellValue(row.getCell((short) 14));//报送人姓名
                if (StringUtils.isTrimEmpty(submittedName)) {
                    msg.append("第" + i + "行报送人姓名填写错误;");
                    continue;
                }
                String submittedMobile = getCellValue(row.getCell((short) 15));//报送人手机号
                if (StringUtils.isTrimEmpty(submittedMobile) || !StringUtils.isMobile(submittedMobile)) {
                    msg.append("第" + i + "行报送人手机号填写错误;");
                    continue;
                }
                String note = getCellValue(row.getCell((short) 16));//备注

                User user = new User();
                ClassType classType = classTypeBiz.findById(Long.parseLong(classTypeId));
                if (classType != null) {
                    user.setClassTypeId(Long.parseLong(classTypeId));
                    user.setClassTypeName(classType.getName());
                } else {
                    msg.append("第" + i + "行班型不存在;");
                    continue;
                }

                Classes classes = classesBiz.findById(Long.parseLong(classId));
//				List<Classes> classesList=classesBiz.find(null, " classId="+classId);
                if (classes != null) {
                    if (classes.getMaxNum() <= classes.getStudentTotalNum()) {
                        msg.append("第" + i + "行该班次报名人数已满,请选择其它班次;");
                        continue;
                    }
                    user.setClassId(Long.parseLong(classId));
                    user.setClassName(classes.getName());
                    int userNum = this.count(" status=1 and classId=" + classes.getId());
                    user.setSerialNumber(calculateSerialNumber((long) userNum));
					/*int index=classIdList.indexOf(user.getClassId());
					if(index>=0){
						addNumList.set(index,addNumList.get(index)+1);
					}else{
						classIdList.add(user.getClassId());
						addNumList.add(1L);
					}*/
                } else {
                    msg.append("第" + i + "行班次不存在;");
                    continue;
                }
                Date registerDeadline = classes.getSignEndTime();
                if (!isManage && registerDeadline.getTime() < System.currentTimeMillis()) {
                    /*//如果是学员处或者组织部
                    if (ObjectUtils.isNotNull(resultMap.get("departmentId"))) {
                        if (!resultMap.get("departmentId").equals("83")) {
                            msg.append("第"+i+"行该班次报名时间已截至,请选择其它班次!");
                            continue;
                        }
                    }*/
                    msg.append("第" + i + "行该班次报名时间已截至,请选择其它班次;");
                    continue;
                }


//                String validateSameStudentInOneClassInfo=validateSameStudentInOneClass(Long.parseLong(classId),idNumber);
//                if(!StringUtils.isTrimEmpty(validateSameStudentInOneClassInfo)&&!(validateSameStudentInOneClassInfo.equals(""))){
//                    msg.append("第"+i+"行"+validateSameStudentInOneClassInfo+";");
//                    continue;
//                }
//				user.setSerialNumber(serialNumber);

                user.setStudentId(year + month + classTypeId + classes.getClassNumber() + user.getSerialNumber());
                user.setName(name);
                user.setIdNumber(idNumber);
                user.setMobile(mobile);
                //根据手机号和邮箱检查是否在同一班次有重复的手机号或身份证号
                boolean sameInOneClass = checkInOneClass(user);
                if (sameInOneClass) {
                    msg.append("第" + i + "行该班次存在相同的手机号或身份证号;");
                    continue;
                }
                //因学员系统不保存手机号，所以需将手机号设为null
                user.setMobile(null);
//				user.setMobile(mobile);
//				user.setEmail(email);
                if (StringUtils.isTrimEmpty(password)) {
                    password = "111111";
                }
//				String customerKey =getRandomString(HMAC_KEY_LEN);
//				password = PurseSecurityUtils.secrect(password, customerKey);
//				user.setPassword(password);
//				user.setCustomerKey(customerKey);
                user.setSex(sex);
                if (StringUtils.isTrimEmpty(age) || Long.parseLong(age) <= 0) {
                    age = "0";
                }
                user.setAge(Integer.parseInt(age));
                user.setNote(note);
                user.setSubmittedName(submittedName);
                user.setSubmittedMobile(submittedMobile);
//				user.setJob(job);
                user.setBaodao(0);
                user.setStatus(isManage ? 1 : 2); //学员处不需要审核
                user.setIsMonitor(0);
                user.setHasApprove(0);
                user.setHasCheck(0);
                user.setSysUserId(sysUserId);
                user.setPoliticalStatus(politicalStatusInt);
                user.setQualification(qualification);
                user.setUnitId(Long.parseLong(unitId));
                user.setUnit(unit);
                user.setJob(job);
                user.setBusiness(businessInt);
                user.setNationality(nationality);
                save(user);
                //base系统增加一条记录
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("userName", user.getName());
                map.put("password", password);
                map.put("email", email);
                map.put("mobile", mobile);
                map.put("status", 0);
                map.put("userType", 3);
                map.put("linkId", user.getId());
                // 先向base项目中插入数据
                String errorInfo = baseHessianService.addSysUserSignUp(map);

                List<Map<String, String>> studentMap = baseHessianService.queryStudentByMobile(user.getMobile());
                if (studentMap != null && studentMap.size() > 0) { //再次报名
                    // 删了原来的wifi账号
                    dpWebService.delWifi(user.getMobile());
                }
                // 向UMC中开通wifi
                boolean isUMCSuccess = dpWebService.saveWifiUser(mobile, user.getName(), classes.getEndTime(), user.getId());
                if (!isUMCSuccess) {
                    deleteById(user.getId());
                    baseHessianService.deleteSysUserByUserNo(errorInfo);
                    msg.append("第").append(i).append("行, 添加wifi失败, 请联系UMC管理员;");
                    break;
                }

                //如果没有错误,则保存用户到教务,否则将addNumList该用户对应的班次的用户数量-1.
                if (StringUtils.isTrimEmpty(errorInfo) ||
                        isNumeric(errorInfo) &&
                                !errorInfo.contains("成功")) {

                    classes.setStudentTotalNum(classes.getStudentTotalNum() + 1);
                    classesBiz.update(classes);
                    //添加学员角色
                    Map<String, String> sysUserMap = baseHessianService.querySysuserByUserno(errorInfo);
                    Map<String, String> sysUserRoleMap = new HashMap<String, String>();
                    sysUserRoleMap.put("roleId", StudentCommonConstants.STUDENT_ROLE_ID + "");
                    sysUserRoleMap.put("userId", sysUserMap.get("id"));
                    baseHessianService.addSysUserRole(sysUserRoleMap);

                } else {
					/*int index=classIdList.indexOf(user.getClassId());
					addNumList.set(index, addNumList.get(index)-1);*/
                    deleteById(user.getId());
//                    baseHessianService.deleteSysUserByUserNo(errorInfo);
                    // 删除wifi
                    dpWebService.delWifi(mobile);
                    msg.append("第" + i + "行" + errorInfo + ";");
                }
            }
        }
//		updateClassStudentNum(classIdList,addNumList);
        return msg.toString();
    }


    public String getAgeByIdNumber(String idNumber) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //获取东八区时间
        int year = c.get(Calendar.YEAR);    //获取年
        int len = idNumber.length();
        int userAge = 0;
        //18位身份证
        if (len == 18) {
            String idNumber1 = idNumber.substring(6, 10); //截取身份证的年
            int b = Integer.valueOf(idNumber1).intValue();
            userAge = year - b; //实际年龄
        } else {
//            //15位身份证
//            String idNumber1 = idNumber.substring(6, 8); //截取身份证的年
//            int b = Integer.valueOf("19" + idNumber1).intValue();
            userAge = 0; //实际年龄
        }
        return userAge + "";
    }

    /**
     * 去掉数字前面的0
     *
     * @param
     * @return
     */
	/*public Long subZero(String s){
		char[] numberArray=s.toCharArray();
		for(int i=0;i<numberArray.length;i++){
			if(numberArray[i]!='0'){
				return Long.parseLong(s.substring(i,s.length()-1));
			}
		}
		return 0L;
	}*/


    //查询当前身份证是否被注册
    public User queryUserByIdentityNumber(String idNumber) {
        String whereSql = " idNumber=" + idNumber;
        List<User> userList = this.find(null, whereSql);
        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }
    }


    //修改指定用户的数据

    public void updateUserById(User user, Long id) {
        String whereSql = " id=" + id;
        this.updateByStrWhere(user, whereSql);
    }


    public void updateClassStudentNum(List<Long> classIdList, List<Long> addNumList) {
        if (classIdList.size() > 0) {
            for (int i = 0; i < classIdList.size(); i++) {
                Classes classes = classesBiz.findById(classIdList.get(i));
                classes.setStudentTotalNum(classes.getStudentTotalNum() + addNumList.get(i));
                classesBiz.update(classes);
            }
        }
    }

    /**
     * @param cell
     * @return
     * @Description 获得Hsscell内容
     */
    public String getCellValue(HSSFCell cell) {
        String value = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_FORMULA:
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    DecimalFormat df = new DecimalFormat("0");
                    value = df.format(cell.getNumericCellValue());
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue().trim();
                    break;
                default:
                    value = "";
                    break;
            }
        }
        return value.trim();
    }

    public List<Map<String, Object>> getAllProgressOfOneStudentProgramCourse(List<TeachingProgramCourse> teachingProgramCourseList) {
        List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
        if (teachingProgramCourseList != null && teachingProgramCourseList.size() > 0) {
            for (TeachingProgramCourse teachingProgramCourse : teachingProgramCourseList) {
                Map<String, Object> map = getProgressOfOneStudentProgramCourse(teachingProgramCourse);
                if (map != null) {
                    list.add(map);
                }
            }
        }
        return list;
    }

    public Map<String, Object> getProgressOfOneStudentProgramCourse(TeachingProgramCourse teachingProgramCourse) {
        List<CourseArrange> courseArrangeList = courseArrangeBiz.find(null, " status=1 and teachingProgramCourseId=" + teachingProgramCourse.getId());
        if (courseArrangeList != null && courseArrangeList.size() > 0) {
            int hasFinishedCourseArrange = getProgressByCourseArrangeList(courseArrangeList);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("courseName", teachingProgramCourse.getCourseName());
            map.put("progress", (int) (Float.parseFloat(hasFinishedCourseArrange + "") / courseArrangeList.size() * 100));
            return map;
        }
        return null;
    }

    public int getProgressByCourseArrangeList(List<CourseArrange> courseArrangeList) {
        Long currentTimeMillis = new Date().getTime();
        int hasFinishedCourseArrange = 0;
        for (CourseArrange courseArrange : courseArrangeList) {
            if (courseArrange.getEndTime().getTime() < currentTimeMillis) {
                hasFinishedCourseArrange++;
            }
        }
        return hasFinishedCourseArrange;
    }

    /**
     * 获取轮训报名名单的excel文件列表
     *
     * @param request
     * @param dir
     * @param headName
     * @param expName
     * @return
     * @throws Exception
     */
    public List<File> getExcelUserList(HttpServletRequest request, String dir, String[] headName, String expName) throws Exception {
        Pagination pagination = new Pagination();
        pagination.setPageSize(10000);
        String whereSql = addCondition(request);
        pagination.setRequest(request);
        find(pagination, whereSql);
        int num = pagination.getTotalPages();// 总页数
        List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
        for (int i = 1; i <= num; i++) {// 循环生成num个xls文件
            pagination.setCurrentPage(i);
            List<User> userList = find(pagination, whereSql);
            List<List<String>> list = convert(userList);
            File file = FileExportImportUtil.createExcel(headName, list, expName + "_" + i, dir);
            srcfile.add(file);
        }
        return srcfile;
    }

    /**
     * 获取轮训报名名单的excel文件列表
     *
     * @param request
     * @param dir
     * @param headName
     * @param expName
     * @return
     * @throws Exception
     */
    public List<File> getUnitExcelUserList(HttpServletRequest request, String dir, String[] headName, String expName) throws Exception {
        Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
        Pagination pagination = new Pagination();
        pagination.setPageSize(10000);
        String whereSql = addCondition(request);
        whereSql += " and sysUserId=" + userMap.get("id");
        pagination.setRequest(request);
        find(pagination, whereSql);
        int num = pagination.getTotalPages();// 总页数
        List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
        for (int i = 1; i <= num; i++) {// 循环生成num个xls文件
            pagination.setCurrentPage(i);
            List<User> userList = find(pagination, whereSql);
            List<List<String>> list = convert(userList);
            File file = FileExportImportUtil.createExcel(headName, list, expName + "_" + i, dir);
            srcfile.add(file);
        }
        return srcfile;
    }

    /**
     * 增加心得搜索条件
     *
     * @param request
     * @return
     */
    public String addCondition(HttpServletRequest request) {
        Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
        StringBuffer sb = new StringBuffer();
        sb.append(" status != 0");
        if (!StringUtils.isEmpty(userMap.get("unitId"))) {
            sb.append("  and unitId = " + userMap.get("unitId"));
        }

        String userId = request.getParameter("userId");
        if (!StringUtils.isTrimEmpty(userId) && Long.parseLong(userId) > 0) {
            sb.append(" and id=" + userId);
        }
        String classTypeId = request.getParameter("classTypeId");
        if (!StringUtils.isTrimEmpty(classTypeId) && Long.parseLong(classTypeId) > 0) {
            sb.append(" and classTypeId=" + classTypeId);
        }
        String classId = request.getParameter("classId");
        if (!StringUtils.isTrimEmpty(classId) && Long.parseLong(classId) > 0) {
            sb.append(" and classId=" + classId);
        }
        String studentId = request.getParameter("studentId");
        if (!StringUtils.isTrimEmpty(studentId)) {
            sb.append(" and studentId like '%" + studentId + "%'");
        }
        String name = request.getParameter("name");
        if (!StringUtils.isTrimEmpty(name)) {
            sb.append(" and name like '%" + name + "%'");
        }
        String idNumber = request.getParameter("idNumber");
        if (!StringUtils.isTrimEmpty(idNumber)) {
            sb.append(" and idNumber like '%" + idNumber + "%'");
        }
        String unitId = request.getParameter("unitId");
        if (!StringUtils.isTrimEmpty(unitId) && !unitId.equals("0")) {
            sb.append(" and unitId=" + unitId);
        }
        String time = request.getParameter("time");
        request.setAttribute("time", time);
        if (!StringUtils.isTrimEmpty(time)) {
            if (time.equals("1")) {
                sb.append(" and createTime>'" + getThisYearBeginTime() + "' and createTime<'" + getThisYearMiddleTime() + "'");
            } else if (time.equals("2")) {
                sb.append(" and createTime>'" + getThisYearBeginTime() + "' and createTime<'" + getThisYearEndTime() + "'");
            } else if (time.equals("3")) {
                sb.append(" and createTime>'" + getFourYearBeforeTime() + "' and createTime<'" + getThisYearEndTime() + "'");
            }
        }
        String unit = request.getParameter("unit");
        if (!StringUtils.isTrimEmpty(unit)) {
            sb.append(" and unit like '%" + unit + "%'");
        }
        String business = request.getParameter("business");
        if (!StringUtils.isTrimEmpty(business)) {
            sb.append(" and business=" + business);
        }
        sb.append(" order by unitId,business");
        return sb.toString();
    }


    /**
     * 增加心得搜索条件
     *
     * @param request
     * @return
     */
    public String addGraCondition(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        sb.append(" status in (1,4,7,8)");
        String userId = request.getParameter("userId");
        if (!StringUtils.isTrimEmpty(userId) && Long.parseLong(userId) > 0) {
            sb.append(" and id=" + userId);
        }
        String classTypeId = request.getParameter("classTypeId");
        if (!StringUtils.isTrimEmpty(classTypeId) && Long.parseLong(classTypeId) > 0) {
            sb.append(" and classTypeId=" + classTypeId);
        }
        String classId = request.getParameter("classId");
        if (!StringUtils.isTrimEmpty(classId) && Long.parseLong(classId) > 0) {
            sb.append(" and classId=" + classId);
        }
        String studentId = request.getParameter("studentId");
        if (!StringUtils.isTrimEmpty(studentId)) {
            sb.append(" and studentId like '%" + studentId + "%'");
        }
        String name = request.getParameter("name");
        if (!StringUtils.isTrimEmpty(name)) {
            sb.append(" and name like '%" + name + "%'");
        }
        String idNumber = request.getParameter("idNumber");
        if (!StringUtils.isTrimEmpty(idNumber)) {
            sb.append(" and idNumber like '%" + idNumber + "%'");
        }
        String unitId = request.getParameter("unitId");
        if (!StringUtils.isTrimEmpty(unitId) && !unitId.equals("0")) {
            sb.append(" and unitId=" + unitId);
        }
        String time = request.getParameter("time");
        request.setAttribute("time", time);
        if (!StringUtils.isTrimEmpty(time)) {
            if (time.equals("1")) {
                sb.append(" and createTime>'" + getThisYearBeginTime() + "' and createTime<'" + getThisYearMiddleTime() + "'");
            } else if (time.equals("2")) {
                sb.append(" and createTime>'" + getThisYearBeginTime() + "' and createTime<'" + getThisYearEndTime() + "'");
            } else if (time.equals("3")) {
                sb.append(" and createTime>'" + getFourYearBeforeTime() + "' and createTime<'" + getThisYearEndTime() + "'");
            }
        }
        String unit = request.getParameter("unit");
        if (!StringUtils.isTrimEmpty(unit)) {
            sb.append(" and unit like '%" + unit + "%'");
        }
        String business = request.getParameter("business");
        if (!StringUtils.isTrimEmpty(business)) {
            sb.append(" and business=" + business);
        }
        sb.append(" order by unitId,business");
        return sb.toString();
    }

    /**
     * 将XinDe的集合转化为List<List<String>>类型.
     *
     * @param userList
     * @return
     */
    public List<List<String>> convert(List<User> userList) {
        List<List<String>> list = new ArrayList<List<String>>();
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                List<String> smallList = new ArrayList<String>();
                smallList.add(user.getClassTypeName());
                Long classId = user.getClassId();
                String className = "";
                if (classId != null) {
                    Classes classes = classesBiz.findById(classId);
                    className = classes.getName();
                }
                smallList.add(className);
                smallList.add(user.getName());
                smallList.add(user.getStudentId());
                smallList.add(user.getIdNumber());
                smallList.add(user.getSex());
                smallList.add(user.getAge() + "");
                smallList.add(user.getUnit());

                smallList.add(user.getJob());
                if (user.getBusiness() != null) {
                    switch (user.getBusiness()) {
                        case 1:
                            smallList.add("正厅");
                            break;
                        case 2:
                            smallList.add("巡视员");
                            break;
                        case 3:
                            smallList.add("副厅");
                            break;
                        case 4:
                            smallList.add("副巡视员");
                            break;
                        case 5:
                            smallList.add("正县");
                            break;
                        case 6:
                            smallList.add("副县");
                            break;
                        case 7:
                            smallList.add("调研员");
                            break;
                        case 8:
                            smallList.add("副调研员");
                            break;
                        case 9:
                            smallList.add("正科");
                            break;
                        case 10:
                            smallList.add("副科");
                            break;
                        default:
                            smallList.add("");
                            break;
                    }
                } else {
                    smallList.add("");
                }
                if (user.getPoliticalStatus() != null) {
                    switch (user.getPoliticalStatus().intValue()) {
                        case 0:
                            smallList.add("中共党员");
                            break;
                        case 1:
                            smallList.add("民主党派");
                            break;
                        case 2:
                            smallList.add("无党派人士");
                            break;
                        case 3:
                            smallList.add("群众");
                            break;
                        case 4:
                            smallList.add("其它");
                            break;
                        default:
                            smallList.add("");
                            break;
                    }
                } else {
                    smallList.add("");
                }
                smallList.add(user.getQualification());
                list.add(smallList);
            }
        }
        return list;
    }

    public String getThisYearBeginTime() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR) + "-01-01 :00:00:00";
    }

    public String getThisYearMiddleTime() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR) + "-06-01 :00:00:00";
    }

    public String getThisYearEndTime() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR) + "-12-31 :00:00:00";
    }

    public String getFourYearBeforeTime() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, -4);
        return c.get(Calendar.YEAR) + "-01-01 :00:00:00";
    }


    public File zuoqutuExcel(HttpServletRequest request, String[] nameArray, String className) throws FileNotFoundException, IOException {
        int[] oddarray = new int[]{9, 11, 13, 16, 18, 20};
        int[] evenarray = new int[]{8, 10, 12, 17, 19, 21};
        int length = nameArray.length;
        File file = new File(request.getSession().getServletContext().getRealPath("/static/common/zuoqutu.xls"));
        FileInputStream inputStream = new FileInputStream(file);
        // datalist拼装List<String[]> datalist,
        HSSFWorkbook wookbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wookbook.getSheetAt(0);

        sheet.getRow(0).getCell((short) 0).setEncoding(HSSFCell.ENCODING_UTF_16);
        sheet.getRow(0).getCell((short) 0).setCellValue(className);

        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        int index = 0;
//                    row.getCell((short) 5).setCellValue(courseArrange.getClassroomName());
        for (int i = 5; i < rows + 1; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null) {
//                System.out.println("-------第"+i+"行"+"-----------0:"+getCellValue(row.getCell((short) 0))+"-----------1:"+getCellValue(row.getCell((short) 1))+"-------------2:"+getCellValue(row.getCell((short) 2))+"--------------3:"+getCellValue(row.getCell((short) 3))+"---------4:"+getCellValue(row.getCell((short) 4))+"--------------5:"+getCellValue(row.getCell((short) 5)));
                switch (i) {
                    case 5:
                        for (int j = 5; j < 9; j++) {
                            if (index >= length) {
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);

                            index++;
                        }
                        for (int j = 10; j < 26; j++) {
                            if (index >= length) {
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        for (int j = 28; j < 32; j++) {
                            if (index >= length) {
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        break;
                    case 6:
                        /*for(int j=32;j>27;j--){
                            if(index>=length){
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        for(int j=26;j>9;j--){
                            if(index>=length){
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        for(int j=8;j>3;j--){
                            if(index>=length){
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }*/
                        for (int j = 4; j < 9; j++) {
                            if (index >= length) {
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        for (int j = 10; j < 27; j++) {
                            if (index >= length) {
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        for (int j = 28; j < 33; j++) {
                            if (index >= length) {
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }

                        break;
                    case 7:
                        for (int j = 3; j < 9; j++) {
                            if (index >= length) {
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        for (int j = 10; j < 27; j++) {
                            if (index >= length) {
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        for (int j = 28; j < 34; j++) {
                            if (index >= length) {
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        break;
                    case 15:
                        /*for(int j=32;j>27;j--){
                            if(index>=length){
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        for(int j=26;j>9;j--){
                            if(index>=length){
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        for(int j=8;j>3;j--){
                            if(index>=length){
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }*/
                        for (int j = 4; j < 9; j++) {
                            if (index >= length) {
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        for (int j = 10; j < 27; j++) {
                            if (index >= length) {
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        for (int j = 28; j < 33; j++) {
                            if (index >= length) {
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        break;
                    case 14:
                        break;
                    case 21:
                        for (int j = 2; j < 9; j++) {
                            if (index >= length) {
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        for (int j = 11; j < 26; j++) {
                            if (index >= length) {
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        for (int j = 28; j < 35; j++) {
                            if (index >= length) {
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        break;
                    default:
                        boolean odd = false;
                        for (int j = 0; j < oddarray.length; j++) {
                            if (oddarray[j] == i) {
                                odd = true;
                                break;
                            }
                        }
                        if (odd) {
                            for (int j = 2; j < 9; j++) {
                                if (index >= length) {
                                    break;
                                }
                                row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                                row.getCell((short) j).setCellValue(nameArray[index]);
                                index++;
                            }
                            for (int j = 10; j < 27; j++) {
                                if (index >= length) {
                                    break;
                                }
                                row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                                row.getCell((short) j).setCellValue(nameArray[index]);
                                index++;
                            }
                            for (int j = 28; j < 35; j++) {
                                if (index >= length) {
                                    break;
                                }
                                row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                                row.getCell((short) j).setCellValue(nameArray[index]);
                                index++;
                            }
                        } else {
                            /*for(int j=34;j>27;j--){
                                if(index>=length){
                                    break;
                                }
                                row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                                row.getCell((short) j).setCellValue(nameArray[index]);
                                index++;
                            }
                            for(int j=26;j>9;j--){
                                if(index>=length){
                                    break;
                                }
                                row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                                row.getCell((short) j).setCellValue(nameArray[index]);
                                index++;
                            }
                            for(int j=8;j>1;j--){
                                if(index>=length){
                                    break;
                                }
                                row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                                row.getCell((short) j).setCellValue(nameArray[index]);
                                index++;
                            }*/
                            for (int j = 2; j < 9; j++) {
                                if (index >= length) {
                                    break;
                                }
                                row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                                row.getCell((short) j).setCellValue(nameArray[index]);
                                index++;
                            }
                            for (int j = 10; j < 27; j++) {
                                if (index >= length) {
                                    break;
                                }
                                row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                                row.getCell((short) j).setCellValue(nameArray[index]);
                                index++;
                            }
                            for (int j = 28; j < 35; j++) {
                                if (index >= length) {
                                    break;
                                }
                                row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                                row.getCell((short) j).setCellValue(nameArray[index]);
                                index++;
                            }
                        }
                        break;
                }

            }
        }
        File newFile = new File(request.getSession().getServletContext().getRealPath("/excelfile/zuoqutu") + "/" + "座区图_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".xls");//生成excel文件
        FileOutputStream fos = new FileOutputStream(newFile);
        wookbook.write(fos);
        fos.close();
        return newFile;
    }

    /**
     * 导出100座的excel
     *
     * @param request
     * @param nameArray
     * @param className
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public File zuoqutuExcel100(HttpServletRequest request, String[] nameArray, String className) throws FileNotFoundException, IOException {
        int index = 0;
        int maxSize = 0;
        File file = new File(request.getSession().getServletContext().getRealPath("/static/common/zuoqutu100.xls"));
        FileInputStream inputStream = new FileInputStream(file);
        // datalist拼装List<String[]> datalist,
        HSSFWorkbook wookbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wookbook.getSheetAt(0);

        sheet.getRow(0).getCell((short) 0).setEncoding(HSSFCell.ENCODING_UTF_16);
        sheet.getRow(0).getCell((short) 0).setCellValue(className);

        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        boolean loop = false;
        if (nameArray != null && nameArray.length > 0) {
            loop = true;
            maxSize = nameArray.length;
        }
//                    row.getCell((short) 5).setCellValue(courseArrange.getClassroomName());
        for (int i = 5; i < 13; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            if (i % 2 == 1) {
                // 行不为空
                if (row != null && loop) {
                    for (int j = 1; j < 5; j++) {
                        if (index < maxSize) {
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index++]);
                        } else {
                            loop = false;
                            break;
                        }
                    }
                    for (int j = 6; j < 12; j++) {
                        if (index < maxSize) {
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index++]);
                        } else {
                            loop = false;
                            break;
                        }
                    }
                    for (int j = 13; j < 17; j++) {
                        if (index < maxSize) {
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index++]);
                        } else {
                            loop = false;
                            break;
                        }
                    }

                } else {
                    break;
                }
            } else {
                // 行不为空
                if (row != null && loop) {

                    for (int j = 1; j < 5; j++) {
                        if (index < maxSize) {
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index++]);
                        } else {
                            loop = false;
                            break;
                        }
                    }
                    for (int j = 6; j < 12; j++) {
                        if (index < maxSize) {
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index++]);
                        } else {
                            loop = false;
                            break;
                        }
                    }
                    for (int j = 13; j < 17; j++) {
                        if (index < maxSize) {
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index++]);
                        } else {
                            loop = false;
                            break;
                        }
                    }
                } else {
                    break;
                }
            }
        }
        File newFile = new File(request.getSession().getServletContext().getRealPath("/excelfile/zuoqutu") + "/" + "座区图_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".xls");//生成excel文件
        FileOutputStream fos = new FileOutputStream(newFile);
        wookbook.write(fos);
        fos.close();
        return newFile;
    }

    /**
     * 定时器来执行,更新已结束班次的学员的状态.
     */
    public void updateGraduateStudentStatus() {
        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = smf.format(new Date());
        List<Classes> classesList = classesBiz.find(null, " end=0 and endTime<='" + nowDate + "'");
        if (classesList != null && classesList.size() > 0) {
            for (Classes classes : classesList) {
                Classes tempClass = new Classes();
                tempClass.setId(classes.getId());
                tempClass.setEnd(1);
                classesBiz.update(classes);
                List<User> userList = find(null, " classId=" + classes.getId());
                if (userList != null && userList.size() > 0) {
                    for (User user : userList) {
                        User tempUser = new User();
                        tempUser.setId(user.getId());
                        tempUser.setStatus(7);
                        update(tempUser);
                    }
                }
            }
        }
    }

    /**
     * 定时器来执行,更新正在进行中的学员的状态.
     */
    public void updateStudentStatus() {
        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = smf.format(new Date());
        List<Classes> classesList = classesBiz.find(null, " end=0 and startTime<='" + nowDate + "' and endTime>='" + nowDate + "' ");
        if (classesList != null && classesList.size() > 0) {
            for (Classes classes : classesList) {
                List<User> userList = find(null, " classId=" + classes.getId());
                if (userList != null && userList.size() > 0) {
                    for (User user : userList) {
                        User tempUser = new User();
                        tempUser.setId(user.getId());
                        tempUser.setStatus(8);
                        update(tempUser);
                    }
                }
            }
        }
    }


    /**
     * 定时器来执行,更新正在进行中的学员的状态.
     */
    public void updateStudentNormalStatus() {
        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = smf.format(new Date());
        List<Classes> classesList = classesBiz.find(null, " end=0  and startTime>='" + nowDate + "' ");
        if (classesList != null && classesList.size() > 0) {
            for (Classes classes : classesList) {
                List<User> userList = find(null, " classId=" + classes.getId());
                if (userList != null && userList.size() > 0) {
                    for (User user : userList) {
                        if (user.getStatus() == 7 || user.getStatus() == 8) {
                            User tempUser = new User();
                            tempUser.setId(user.getId());
                            tempUser.setStatus(1);
                            update(tempUser);
                        }
                    }
                }
            }
        }
    }


    /**
     * 按照学员处所提表格导出某班次学员列表
     *
     * @param request
     * @param classId
     * @return
     * @throws Exception
     */
    public File newUserListExcel(HttpServletRequest request, Long classId) throws Exception {
        Classes classes = classesBiz.findById(classId);
        String classTypeId = request.getParameter("classTypeId");
        String whereSql = "  (status = 2 or status = 3) and classId=" + classId;

        if (!StringUtils.isTrimEmpty(classTypeId) && Long.parseLong(classTypeId) > 0) {
            whereSql += " and classTypeId=" + classTypeId;
        }
        Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
        if (!StringUtils.isTrimEmpty(userMap.get("unitId")) && Long.parseLong(userMap.get("unitId")) > 0) {
            whereSql += " and unitId=" + userMap.get("unitId");
        }
        List<User> userList = find(null, whereSql + " order by unitId,business");
        addEmailAndMobileToUserList(userList);
        List<List<String>> userPackageInfos = convertForUserExport(userList);

        Iterator<User> iterator = null;
        User user = null;
        HSSFRow row = null;
        if (userList != null && userList.size() > 0) {
            iterator = userList.iterator();
        }
        File file = new File(request.getSession().getServletContext().getRealPath("/static/common/export_user_list_model.xls"));
        FileInputStream inputStream = new FileInputStream(file);
        HSSFWorkbook wookbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wookbook.getSheetAt(0);
        sheet.getRow(0).getCell(Short.valueOf("0")).setEncoding(HSSFCell.ENCODING_UTF_16);
        sheet.getRow(0).getCell(Short.valueOf("0")).setCellValue(classes.getName() + "学员名单(" + userList.size() + "人)");
        List<Long> userIdList = null;
        int cnt = userList.size();
        HSSFCell cell = null;
        HSSFCellStyle style = wookbook.createCellStyle();   //样式对象
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平
//        每组人数 + 1 因为显示每组人数占了一行
        int number = 51;
        int flag = cnt % number == 0 ? 0 : 1;
        int groupNumber = flag + cnt / number;
        int k = 0;
        if (iterator != null) {
            for (int i = 10; i < cnt + 10 + groupNumber; i++) {
                int j = 0;
                int ii = (i - 10) % number;
                int jj = (i - 10) / number;
                if (ii == 0) {
                    //每组的实际人数
                    int _number = number - 1;
                    if (jj + 1 == groupNumber) {
                        _number = cnt - (jj * (number - 1));
                    }
                    row = sheet.createRow((short) i);
                    cell = row.createCell((short) j);
                    cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
                    cell.setCellStyle(style);
                    //合并单元格
                    sheet.addMergedRegion(new Region(i, (short) 0, i, (short) 9));
                    cell.setCellValue("第" + (jj + 1) + "组,共" + _number + "人");
                    i++;
                }
                row = sheet.createRow((short) i);
                List<String> strs = userPackageInfos.get(k);
                for (String str : strs) {
                    cell = row.createCell((short) j);
                    cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
                    cell.setCellValue(str);
                    j++;
                }
                k++;
                if (k == cnt) {
                    break;
                }
            }
        }
        File newFile = new File(request.getSession().getServletContext().getRealPath("/excelfile/user") + "/" + "学员名单_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".xls");
        FileOutputStream fos = new FileOutputStream(newFile);
        wookbook.write(fos);
        fos.close();
        return newFile;
    }

    /**
     * 按照学员处所提表格导出某班次学员列表--改回旧的
     *
     * @param request
     * @param classId
     * @return
     * @throws Exception
     */
    public List<File> newUserListExcelTwo(HttpServletRequest request, String dir, String[] headName, String expName, Long classId) throws Exception {
        Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
        String classTypeId = request.getParameter("classTypeId");
        String whereSql = "  (status = 2 or status = 3) and classId=" + classId;
        if (!StringUtils.isTrimEmpty(classTypeId) && Long.parseLong(classTypeId) > 0) {
            whereSql += " and classTypeId=" + classTypeId;
        }
        if (!StringUtils.isTrimEmpty(userMap.get("unitId")) && Long.parseLong(userMap.get("unitId")) > 0) {
            whereSql += " and unitId=" + userMap.get("unitId");
        }
        Pagination pagination = new Pagination();
        pagination.setPageSize(10000);
        whereSql += " and sysUserId=" + userMap.get("id");
        pagination.setRequest(request);
        find(pagination, whereSql);
        int num = pagination.getTotalPages();// 总页数
        List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
        for (int i = 1; i <= num; i++) {// 循环生成num个xls文件
            pagination.setCurrentPage(i);
            List<User> userList = find(pagination, whereSql + " order by unitId,business");
            addEmailAndMobileToUserList(userList);
            List<List<String>> list = convert(userList);
            File file = FileExportImportUtil.createExcel(headName, list, expName + "_" + i, dir);
            srcfile.add(file);
        }
        return srcfile;
    }

    /**
     * 导出 批量导入学员模板
     *
     * @param request
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public File getBatchImportUserModel(HttpServletRequest request) throws FileNotFoundException, IOException {
        List<Classes> classesList = classesBiz.find(null, " status=1");
        File file = new File(request.getSession().getServletContext().getRealPath("/static/common/import_student.xls"));
        FileInputStream inputStream = new FileInputStream(file);
        // datalist拼装List<String[]> datalist,
        HSSFWorkbook wookbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wookbook.getSheetAt(1);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell((short) 0);
        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue("班型ID");
        cell = row.createCell((short) 1);
        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue("班型");
        cell = row.createCell((short) 2);
        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue("班次ID");
        cell = row.createCell((short) 3);
        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue("班次");
        if (classesList != null && classesList.size() > 0) {
            for (int i = 0; i < classesList.size(); i++) {
                row = sheet.createRow(i + 1);
                Classes classes = classesList.get(i);
                cell = row.createCell((short) 0);
                cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                cell.setCellValue("" + classes.getClassTypeId());
                cell = row.createCell((short) 1);
                cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                cell.setCellValue(classes.getClassType());
                cell = row.createCell((short) 2);
                cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                cell.setCellValue("" + classes.getId());
                cell = row.createCell((short) 3);
                cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                cell.setCellValue(classes.getName());
            }
        }
        File newFile = new File(request.getSession().getServletContext().getRealPath("/excelfile/batchImportUserModel") + "/" + "import_student" + ".xls");//生成excel文件
        FileOutputStream fos = new FileOutputStream(newFile);
        wookbook.write(fos);
        fos.close();
        return newFile;
    }


    /**
     * 导入学员名单，根据手机号更新用户信息。
     *
     * @param request
     * @return
     * @throws Exception
     */
    public String updateUser(UserExcelRecord userExcelRecord, HttpServletRequest request) {
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        String errorInfo = null;
        User user = null;
        Pattern pattern = Pattern.compile("[0-9]*");
        //设置导入排序值
        Integer importSort = 100;
        try {
            connection = (HttpURLConnection) new URL(userExcelRecord.getUrl()).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();
            inputStream = connection.getInputStream();
            String[] politicalStatusArray = new String[]{"中共党员", "民主党派", "无党派人士", "群众", "其它"};
            HSSFWorkbook wookbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = wookbook.getSheetAt(0);
//            int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
            /*for (int i = 1; i < rows + 1; i++) {
                // 读取左上端单元格
                HSSFRow row = sheet.getRow(i);
                // 行不为空
                if (row != null) {

                }
            }*/
            int group1 = 0;
            int group2 = 0;
            int group3 = 0;
            int group4 = 0;
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                if (ObjectUtils.isNotNull(sheet.getRow(i))) {
                    System.out.println(getCellValue(sheet.getRow(i).getCell((short) 0)));
                    if (getCellValue(sheet.getRow(i).getCell((short) 0)).indexOf("第一组") > -1) {
                        group1 = i;
                    } else if (getCellValue(sheet.getRow(i).getCell((short) 0)).indexOf("第二组") > -1) {
                        group2 = i;
                    } else if (getCellValue(sheet.getRow(i).getCell((short) 0)).indexOf("第三组") > -1) {
                        group3 = i;
                    } else if (getCellValue(sheet.getRow(i).getCell((short) 0)).indexOf("第四组") > -1) {
                        group4 = i;
                    }
                }
            }
            HSSFRow row = null;
            if (group1 > 0) {
                int end;
                if (group2 == 0) {
                    end = sheet.getLastRowNum() + 1;
                } else {
                    end = group2;
                }
                for (int i = group1 + 1; i < end; i++) {
                    user = new User();
                    row = sheet.getRow(i);
                    if (row != null) {
                        String mobile = getCellValue(row.getCell((short) 8));
                        String note1 = getCellValue(row.getCell((short) 9));
                        if (!StringUtils.isTrimEmpty(note1)) {
                            if ("班长".equals(note1)) {
                                user.setIsMonitor(1);
                            }
                            user.setNote(note1);
                        }
                        if (!StringUtils.isTrimEmpty(mobile)) {
                            //因为一个手机号可能存在多条记录，所以要找到相同班次的那条记录.
                            List<Map<String, String>> sysUserMapList = baseHessianService.queryStudentByMobile(mobile);
                            if (sysUserMapList != null && sysUserMapList.size() > 0) {
                                for (Map<String, String> tempSysUserMap : sysUserMapList) {
                                    User tempUser = this.findById(Long.parseLong(tempSysUserMap.get("linkId")));
                                    if (userExcelRecord.getClassId().equals(tempUser.getClassId())) {
                                        user = tempUser;
                                    } else {
                                        user.setId(tempUser.getId());
                                        update(user);
                                        user = null;
                                    }
                                }
                            }
                            if (user != null) {
                                String name = getCellValue(row.getCell((short) 1));
                                if (!StringUtils.isTrimEmpty(name)) {
                                    user.setName(name.trim());
                                }
                                String sex = getCellValue(row.getCell((short) 2));
                                if (!StringUtils.isTrimEmpty(sex) && (sex.equals("男") || sex.equals("女"))) {
                                    user.setSex(sex);
                                }
                                String politicalStatus = getCellValue(row.getCell((short) 3));
                                if (!StringUtils.isTrimEmpty(politicalStatus)) {
                                    for (int j = 0; j < politicalStatusArray.length; j++) {
                                        if (politicalStatusArray[j].equals(politicalStatus)) {
                                            user.setPoliticalStatus((long) j);
                                            break;
                                        }
                                    }
                                }
                                String age = getCellValue(row.getCell((short) 4));
                                if (!StringUtils.isTrimEmpty(age)) {
                                    Matcher matcher = pattern.matcher(age);
                                    if (matcher.matches()) {
                                        user.setAge(Integer.parseInt(age));
                                    }
                                }
                                String qualification = getCellValue(row.getCell((short) 5));
                                if (!StringUtils.isTrimEmpty(qualification)) {
                                    user.setQualification(qualification);
                                }
                                String nationality = getCellValue(row.getCell((short) 6));
                                if (!StringUtils.isTrimEmpty(nationality)) {
                                    user.setNationality(nationality);
                                }
                                String job = getCellValue(row.getCell((short) 7));
                                if (!StringUtils.isTrimEmpty(job)) {
                                    user.setJob(job);
                                }
                                String note = getCellValue(row.getCell((short) 9));
                                if (!StringUtils.isTrimEmpty(note)) {
                                    user.setNote(note);
                                } else {
                                    user.setNote("");
                                }
                                String idNumber = getCellValue(row.getCell((short) 10));
                                if (!StringUtils.isTrimEmpty(idNumber)) {
                                    List<User> userList = find(null, " idNumber='" + idNumber + "' and classId=" + userExcelRecord.getClassId());
                                    if (userList == null || userList.size() == 0) {
                                        user.setIdNumber(idNumber);
                                    }
                                }
                                user.setGroupId(1);
                                user.setImportsort(importSort--);
                                update(user);
                            }
                        }
                    }
                }
            }

            if (group2 > 0) {
                int end;
                if (group3 == 0) {
                    end = sheet.getLastRowNum() + 1;
                } else {
                    end = group3;
                }
                for (int i = group2 + 1; i < end; i++) {
                    user = null;
                    row = sheet.getRow(i);
                    if (row != null) {
                        String mobile = getCellValue(row.getCell((short) 8));
                        if (!StringUtils.isTrimEmpty(mobile)) {
                            //因为一个手机号可能存在多条记录，所以要找到相同班次的那条记录.
                            List<Map<String, String>> sysUserMapList = baseHessianService.queryStudentByMobile(mobile);
                            if (sysUserMapList != null && sysUserMapList.size() > 0) {
                                for (Map<String, String> tempSysUserMap : sysUserMapList) {
                                    User tempUser = this.findById(Long.parseLong(tempSysUserMap.get("linkId")));
                                    if (userExcelRecord.getClassId().equals(tempUser.getClassId())) {
                                        user = tempUser;
                                    }
                                }
                            }
                            if (user != null) {
                                String name = getCellValue(row.getCell((short) 1));
                                if (!StringUtils.isTrimEmpty(name)) {
                                    user.setName(name.trim());
                                }
                                String sex = getCellValue(row.getCell((short) 2));
                                if (!StringUtils.isTrimEmpty(sex) && (sex.equals("男") || sex.equals("女"))) {
                                    user.setSex(sex);
                                }
                                String politicalStatus = getCellValue(row.getCell((short) 3));
                                if (!StringUtils.isTrimEmpty(politicalStatus)) {
                                    for (int j = 0; j < politicalStatusArray.length; j++) {
                                        if (politicalStatusArray[j].equals(politicalStatus)) {
                                            user.setPoliticalStatus((long) j);
                                            break;
                                        }
                                    }
                                }
                                String age = getCellValue(row.getCell((short) 4));
                                if (!StringUtils.isTrimEmpty(age)) {
                                    Matcher matcher = pattern.matcher(age);
                                    if (matcher.matches()) {
                                        user.setAge(Integer.parseInt(age));
                                    }
                                }
                                String qualification = getCellValue(row.getCell((short) 5));
                                if (!StringUtils.isTrimEmpty(qualification)) {
                                    user.setQualification(qualification);
                                }
                                String nationality = getCellValue(row.getCell((short) 6));
                                if (!StringUtils.isTrimEmpty(nationality)) {
                                    user.setNationality(nationality);
                                }
                                String job = getCellValue(row.getCell((short) 7));
                                if (!StringUtils.isTrimEmpty(job)) {
                                    user.setJob(job);
                                }
                                String note = getCellValue(row.getCell((short) 9));
                                if (!StringUtils.isTrimEmpty(note)) {
                                    user.setNote(note);
                                } else {
                                    user.setNote("");
                                }
                                String idNumber = getCellValue(row.getCell((short) 10));
                                if (!StringUtils.isTrimEmpty(idNumber)) {
                                    List<User> userList = find(null, " idNumber='" + idNumber + "' and classId=" + userExcelRecord.getClassId());
                                    if (userList == null || userList.size() == 0) {
                                        user.setIdNumber(idNumber);
                                    }
                                }
                                user.setGroupId(2);
                                user.setImportsort(importSort--);
                                update(user);
                            }
                        }
                    }
                }
            }

            if (group3 > 0) {
                int end;
                if (group4 == 0) {
                    end = sheet.getLastRowNum() + 1;
                } else {
                    end = group4;
                }
                for (int i = group3 + 1; i < end; i++) {
                    user = null;
                    row = sheet.getRow(i);
                    if (row != null) {
                        String mobile = getCellValue(row.getCell((short) 8));
                        if (!StringUtils.isTrimEmpty(mobile)) {
                            //因为一个手机号可能存在多条记录，所以要找到相同班次的那条记录.
                            List<Map<String, String>> sysUserMapList = baseHessianService.queryStudentByMobile(mobile);
                            if (sysUserMapList != null && sysUserMapList.size() > 0) {
                                for (Map<String, String> tempSysUserMap : sysUserMapList) {
                                    User tempUser = this.findById(Long.parseLong(tempSysUserMap.get("linkId")));
                                    if (userExcelRecord.getClassId().equals(tempUser.getClassId())) {
                                        user = tempUser;
                                    }
                                }
                            }
                            if (user != null) {
                                String name = getCellValue(row.getCell((short) 1));
                                if (!StringUtils.isTrimEmpty(name)) {
                                    user.setName(name.trim());
                                }
                                String sex = getCellValue(row.getCell((short) 2));
                                if (!StringUtils.isTrimEmpty(sex) && (sex.equals("男") || sex.equals("女"))) {
                                    user.setSex(sex);
                                }
                                String politicalStatus = getCellValue(row.getCell((short) 3));
                                if (!StringUtils.isTrimEmpty(politicalStatus)) {
                                    for (int j = 0; j < politicalStatusArray.length; j++) {
                                        if (politicalStatusArray[j].equals(politicalStatus)) {
                                            user.setPoliticalStatus((long) j);
                                            break;
                                        }
                                    }
                                }
                                String age = getCellValue(row.getCell((short) 4));
                                if (!StringUtils.isTrimEmpty(age)) {
                                    Matcher matcher = pattern.matcher(age);
                                    if (matcher.matches()) {
                                        user.setAge(Integer.parseInt(age));
                                    }
                                }
                                String qualification = getCellValue(row.getCell((short) 5));
                                if (!StringUtils.isTrimEmpty(qualification)) {
                                    user.setQualification(qualification);
                                }
                                String nationality = getCellValue(row.getCell((short) 6));
                                if (!StringUtils.isTrimEmpty(nationality)) {
                                    user.setNationality(nationality);
                                }
                                String job = getCellValue(row.getCell((short) 7));
                                if (!StringUtils.isTrimEmpty(job)) {
                                    user.setJob(job);
                                }
                                String note = getCellValue(row.getCell((short) 9));
                                if (!StringUtils.isTrimEmpty(note)) {
                                    user.setNote(note);
                                } else {
                                    user.setNote("");
                                }
                                String idNumber = getCellValue(row.getCell((short) 10));
                                if (!StringUtils.isTrimEmpty(idNumber)) {
                                    List<User> userList = find(null, " idNumber='" + idNumber + "' and classId=" + userExcelRecord.getClassId());
                                    if (userList == null || userList.size() == 0) {
                                        user.setIdNumber(idNumber);
                                    }
                                }
                                user.setGroupId(3);
                                user.setImportsort(importSort--);
                                update(user);
                            }
                        }
                    }
                }
            }

            if (group4 > 0) {
                for (int i = group4 + 1; i <= sheet.getLastRowNum(); i++) {
                    user = null;
                    row = sheet.getRow(i);
                    if (row != null) {
                        String mobile = getCellValue(row.getCell((short) 8));
                        if (!StringUtils.isTrimEmpty(mobile)) {
                            //因为一个手机号可能存在多条记录，所以要找到相同班次的那条记录.
                            List<Map<String, String>> sysUserMapList = baseHessianService.queryStudentByMobile(mobile);
                            if (sysUserMapList != null && sysUserMapList.size() > 0) {
                                for (Map<String, String> tempSysUserMap : sysUserMapList) {
                                    User tempUser = this.findById(Long.parseLong(tempSysUserMap.get("linkId")));
                                    if (userExcelRecord.getClassId().equals(tempUser.getClassId())) {
                                        user = tempUser;
                                    }
                                }
                            }
                            if (user != null) {
                                String name = getCellValue(row.getCell((short) 1));
                                if (!StringUtils.isTrimEmpty(name)) {
                                    user.setName(name.trim());
                                }
                                String sex = getCellValue(row.getCell((short) 2));
                                if (!StringUtils.isTrimEmpty(sex) && (sex.equals("男") || sex.equals("女"))) {
                                    user.setSex(sex);
                                }
                                String politicalStatus = getCellValue(row.getCell((short) 3));
                                if (!StringUtils.isTrimEmpty(politicalStatus)) {
                                    for (int j = 0; j < politicalStatusArray.length; j++) {
                                        if (politicalStatusArray[j].equals(politicalStatus)) {
                                            user.setPoliticalStatus((long) j);
                                            break;
                                        }
                                    }
                                }
                                String age = getCellValue(row.getCell((short) 4));
                                if (!StringUtils.isTrimEmpty(age)) {
                                    Matcher matcher = pattern.matcher(age);
                                    if (matcher.matches()) {
                                        user.setAge(Integer.parseInt(age));
                                    }
                                }
                                String qualification = getCellValue(row.getCell((short) 5));
                                if (!StringUtils.isTrimEmpty(qualification)) {
                                    user.setQualification(qualification);
                                }
                                String nationality = getCellValue(row.getCell((short) 6));
                                if (!StringUtils.isTrimEmpty(nationality)) {
                                    user.setNationality(nationality);
                                }
                                String job = getCellValue(row.getCell((short) 7));
                                if (!StringUtils.isTrimEmpty(job)) {
                                    user.setJob(job);
                                }
                                String note = getCellValue(row.getCell((short) 9));
                                if (!StringUtils.isTrimEmpty(note)) {
                                    user.setNote(note);
                                } else {
                                    user.setNote("");
                                }
                                String idNumber = getCellValue(row.getCell((short) 10));
                                if (!StringUtils.isTrimEmpty(idNumber)) {
                                    List<User> userList = find(null, " idNumber='" + idNumber + "' and classId=" + userExcelRecord.getClassId());
                                    if (userList == null || userList.size() == 0) {
                                        user.setIdNumber(idNumber);
                                    }
                                }
                                user.setGroupId(4);
                                user.setImportsort(importSort--);
                                update(user);
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            errorInfo = "请上传规定的学员名单的excel文件";
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return errorInfo;
    }

    /**
     * 一个人不能在同一班次
     *
     * @return
     */
    public String validateSameStudentInOneClass(Long classId, String idNumber) {
        List<User> userList = this.find(null, " classId=" + classId + " and idNumber='" + idNumber + "'");
        if (userList != null && userList.size() > 0) {
            return "该学员已参加该班次，请选择其它班次";
        }
        return null;
    }

    /**
     * 将用户信息的集合转化为List<List<String>>类型.
     *
     * @param userList 将信息转换为了导出
     * @return
     */
    private List<List<String>> convertForUserExport(List<User> userList) {
        //一组多少人
        int numbers = 50;
        int i = 0;
        List<List<String>> list = new ArrayList<List<String>>();
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                List<String> smallList = new ArrayList<String>();
                smallList.add((i % numbers + 1) + "");
                smallList.add(user.getName());
                smallList.add(user.getSex());
                if (user.getPoliticalStatus() == null) {
                    smallList.add("");
                } else {
                    switch (user.getPoliticalStatus().intValue()) {
                        case 0:
                            smallList.add("中共党员");
                            break;
                        case 1:
                            smallList.add("民主党派");
                            break;
                        case 2:
                            smallList.add("无党派人士");
                            break;
                        case 3:
                            smallList.add("群众");
                            break;
                        case 4:
                            smallList.add("其它");
                            break;
                        default:
                            smallList.add("");
                            break;
                    }
                }
                smallList.add(user.getAge() + "");
                smallList.add(user.getQualification());
                smallList.add(user.getNationality());
                String unitAndPostion = "";
                if (user.getUnitId() != null) {
                    Unit unit = unitBiz.findById(user.getUnitId());
                    unitAndPostion += unit.getName();
                }
                if (!user.getJob().isEmpty()) {
                    unitAndPostion += " " + user.getJob();
                }
                smallList.add(unitAndPostion);
                smallList.add(user.getMobile());
                smallList.add(user.getNote());
                smallList.add(user.getIdNumber());
                smallList.add("");
                list.add(smallList);
                ++i;
            }
        }
        return list;
    }


    private void addEmailAndMobileToUserList(List<User> userList) {
//		List<UserInfo> userInfoList=new LinkedList<UserInfo>();
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                List<Map<String, String>> baseUserList = baseHessianService.querySysUser(3, user.getId());
//        		UserInfo userInfo=new UserInfo();
        		/*userInfo.setId(u.getId());
        		userInfo.setClassTypeId(u.getClassTypeId());
        		userInfo.setClassTypeName(u.getClassTypeName());
        		userInfo.setClassId(u.getClassId());
        		userInfo.setClassName(u.getClassName());
        		userInfo.setStudentId(u.getStudentId());
        		userInfo.setName(u.getName());
        		userInfo.setIdNumber(u.getIdNumber());*/
                if (baseUserList != null && baseUserList.size() > 0) {
                    user.setMobile(baseUserList.get(0).get("mobile"));
                    user.setEmail(baseUserList.get(0).get("email"));
                }
        		/*userInfo.setSex(u.getSex());
        		userInfo.setAge(u.getAge());
        		userInfo.setCreateTime(u.getCreateTime());
        		userInfo.setBaodao(u.getBaodao());
        		userInfo.setHasApprove(u.getHasApprove());
        		userInfo.setHasCheck(u.getHasCheck());
        		userInfo.setIsMonitor(u.getIsMonitor());
        		userInfoList.add(userInfo);*/
            }
        }
    }

    /**
     * 导出大礼堂（765）的excel
     *
     * @param request
     * @param nameArray
     * @param className
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public File zuoqutuExcel765(HttpServletRequest request, String[] nameArray, String className) throws FileNotFoundException, IOException {
        int index = 0;
        int maxSize = 0;
        File file = new File(request.getSession().getServletContext().getRealPath("/static/common/zuoqutu765.xls"));
        FileInputStream inputStream = new FileInputStream(file);
        HSSFWorkbook wookbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wookbook.getSheetAt(0);

        sheet.getRow(0).getCell((short) 2).setEncoding(HSSFCell.ENCODING_UTF_16);
        sheet.getRow(0).getCell((short) 2).setCellValue(className);

        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        boolean loop = false;
        if (nameArray != null && nameArray.length > 0) {
            loop = true;
            maxSize = nameArray.length;
        }
        //1楼1排
        for (int i = 4; i < 5; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null && loop) {
                for (int j = 5; j < 9; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 10; j < 26; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 28; j < 32; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }

            } else {
                break;
            }
        }
        //1楼2排
        for (int i = 5; i < 6; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null && loop) {
                for (int j = 4; j < 9; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 10; j < 27; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 28; j < 33; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }

            } else {
                break;
            }
        }
        //1楼3排
        for (int i = 6; i < 7; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null && loop) {
                for (int j = 3; j < 9; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 10; j < 27; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 28; j < 34; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }

            } else {
                break;
            }
        }
        //1楼4排至9排
        for (int i = 7; i < 13; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null && loop) {
                for (int j = 2; j < 9; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 10; j < 27; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 28; j < 35; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }

            } else {
                break;
            }
        }
        //1楼10排
        for (int i = 14; i < 15; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null && loop) {
                for (int j = 4; j < 9; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 10; j < 27; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 28; j < 33; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }

            } else {
                break;
            }
        }
        //1楼11排至15排
        for (int i = 15; i < 20; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null && loop) {
                for (int j = 2; j < 9; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 10; j < 27; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 28; j < 35; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }

            } else {
                break;
            }
        }
        //1楼16排
        for (int i = 20; i < 21; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null && loop) {
                for (int j = 2; j < 9; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 11; j < 26; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 28; j < 35; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }

            } else {
                break;
            }
        }
        //2楼1-2排
        for (int i = 24; i < 26; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null && loop) {
                for (int j = 3; j < 8; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 9; j < 17; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 18; j < 26; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 27; j < 32; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }

            } else {
                break;
            }
        }
        //2楼3排
        for (int i = 27; i < 28; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null && loop) {
                /*for (int j = 3; j < 8; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }*/
                for (int j = 9; j < 17; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 18; j < 26; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                /*for (int j = 27; j < 32; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }*/

            } else {
                break;
            }
        }
        //2楼4-6排
        for (int i = 28; i < 31; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null && loop) {
                for (int j = 3; j < 8; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 9; j < 17; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 18; j < 26; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 27; j < 32; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }

            } else {
                break;
            }
        }
        //2楼7-10排
        for (int i = 32; i < 36; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null && loop) {
                for (int j = 3; j < 8; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 9; j < 17; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 18; j < 26; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 27; j < 32; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }

            } else {
                break;
            }
        }
        File newFile = new File(request.getSession().getServletContext().getRealPath("/excelfile/zuoqutu") + "/" + "座区图_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".xls");//生成excel文件
        FileOutputStream fos = new FileOutputStream(newFile);
        wookbook.write(fos);
        fos.close();
        return newFile;
    }


    /**
     * 导出200座的excel
     *
     * @param request
     * @param nameArray
     * @param className
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public File zuoqutuExcel200(HttpServletRequest request, String[] nameArray, String className) throws FileNotFoundException, IOException {
        int index = 0;
        int maxSize = 0;
        File file = new File(request.getSession().getServletContext().getRealPath("/static/common/zuoqutu200.xls"));
        FileInputStream inputStream = new FileInputStream(file);
        // datalist拼装List<String[]> datalist,
        HSSFWorkbook wookbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wookbook.getSheetAt(0);

        sheet.getRow(0).getCell((short) 0).setEncoding(HSSFCell.ENCODING_UTF_16);
        sheet.getRow(0).getCell((short) 0).setCellValue(className);

        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        boolean loop = false;
        if (nameArray != null && nameArray.length > 0) {
            loop = true;
            maxSize = nameArray.length;
        }
        for (int i = 2; i < 13; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null && loop) {
                for (int j = 1; j < 5; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 7; j < 13; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 15; j < 19; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }

            } else {
                break;
            }

        }
        HSSFRow row = sheet.getRow(13);
        if (row != null && loop) {
            for (int j = 1; j < 19; j++) {
                if (index < maxSize) {
                    row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                    row.getCell((short) j).setCellValue(nameArray[index++]);
                } else {
                    loop = false;
                    break;
                }
            }
        }
        File newFile = new File(request.getSession().getServletContext().getRealPath("/excelfile/zuoqutu") + "/" + "座区图_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".xls");//生成excel文件
        FileOutputStream fos = new FileOutputStream(newFile);
        wookbook.write(fos);
        fos.close();
        return newFile;
    }


    /**
     * 导出158座的excel
     *
     * @param request
     * @param nameArray
     * @param className
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public File zuoqutuExcel158(HttpServletRequest request, String[] nameArray, String className) throws FileNotFoundException, IOException {
        int index = 0;
        int maxSize = 0;
        File file = new File(request.getSession().getServletContext().getRealPath("/static/common/zuoqutu158.xls"));
        FileInputStream inputStream = new FileInputStream(file);
        // datalist拼装List<String[]> datalist,
        HSSFWorkbook wookbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wookbook.getSheetAt(0);

        sheet.getRow(0).getCell((short) 0).setEncoding(HSSFCell.ENCODING_UTF_16);
        sheet.getRow(0).getCell((short) 0).setCellValue(className);

        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        boolean loop = false;
        if (nameArray != null && nameArray.length > 0) {
            loop = true;
            maxSize = nameArray.length;
        }
        for (int i = 2; i < 12; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null && loop) {
                for (int j = 0; j < 2; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 3; j < 9; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 10; j < 16; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 17; j < 19; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
            } else {
                break;
            }

        }
        HSSFRow row = sheet.getRow(12);
        if (row != null && loop) {
            for (int j = 0; j < 2; j++) {
                if (index < maxSize) {
                    row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                    row.getCell((short) j).setCellValue(nameArray[index++]);
                } else {
                    loop = false;
                    break;
                }
            }
            for (int j = 3; j < 9; j++) {
                if (index < maxSize) {
                    row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                    row.getCell((short) j).setCellValue(nameArray[index++]);
                } else {
                    loop = false;
                    break;
                }
            }
            for (int j = 10; j < 16; j++) {
                if (index < maxSize) {
                    row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                    row.getCell((short) j).setCellValue(nameArray[index++]);
                } else {
                    loop = false;
                    break;
                }
            }
        }
        File newFile = new File(request.getSession().getServletContext().getRealPath("/excelfile/zuoqutu") + "/" + "座区图_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".xls");//生成excel文件
        FileOutputStream fos = new FileOutputStream(newFile);
        wookbook.write(fos);
        fos.close();
        return newFile;
    }


    /**
     * 导出80座(新大楼4楼环形教师)的excel
     *
     * @param request
     * @param nameArray
     * @param className
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public File zuoqutuExcel80(HttpServletRequest request, String[] nameArray, String className) throws FileNotFoundException, IOException {
        int index = 0;
        int maxSize = 0;
        File file = new File(request.getSession().getServletContext().getRealPath("/static/common/zuoqutu80.xls"));
        FileInputStream inputStream = new FileInputStream(file);
        // datalist拼装List<String[]> datalist,
        HSSFWorkbook wookbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wookbook.getSheetAt(0);

        sheet.getRow(0).getCell((short) 0).setEncoding(HSSFCell.ENCODING_UTF_16);
        sheet.getRow(0).getCell((short) 0).setCellValue(className);

        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        boolean loop = false;
        if (nameArray != null && nameArray.length > 0) {
            loop = true;
            maxSize = nameArray.length;
        }

        HSSFRow row = sheet.getRow(2);
        if (row != null && loop) {
            for (int j = 1; j < 5; j++) {
                if (index < maxSize) {
                    row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                    row.getCell((short) j).setCellValue(nameArray[index++]);
                } else {
                    loop = false;
                    break;
                }
            }
            for (int j = 9; j < 17; j++) {
                if (index < maxSize) {
                    row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                    row.getCell((short) j).setCellValue(nameArray[index++]);
                } else {
                    loop = false;
                    break;
                }
            }
            for (int j = 21; j < 25; j++) {
                if (index < maxSize) {
                    row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                    row.getCell((short) j).setCellValue(nameArray[index++]);
                } else {
                    loop = false;
                    break;
                }
            }
        }

        row = sheet.getRow(3);
        if (row != null && loop) {
            for (int j = 1; j < 5; j++) {
                if (index < maxSize) {
                    row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                    row.getCell((short) j).setCellValue(nameArray[index++]);
                } else {
                    loop = false;
                    break;
                }
            }
            for (int j = 8; j < 18; j++) {
                if (index < maxSize) {
                    row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                    row.getCell((short) j).setCellValue(nameArray[index++]);
                } else {
                    loop = false;
                    break;
                }
            }
            for (int j = 21; j < 25; j++) {
                if (index < maxSize) {
                    row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                    row.getCell((short) j).setCellValue(nameArray[index++]);
                } else {
                    loop = false;
                    break;
                }
            }
        }

        row = sheet.getRow(4);
        if (row != null && loop) {
            for (int j = 2; j < 5; j++) {
                if (index < maxSize) {
                    row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                    row.getCell((short) j).setCellValue(nameArray[index++]);
                } else {
                    loop = false;
                    break;
                }
            }
            for (int j = 7; j < 19; j++) {
                if (index < maxSize) {
                    row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                    row.getCell((short) j).setCellValue(nameArray[index++]);
                } else {
                    loop = false;
                    break;
                }
            }
            for (int j = 21; j < 24; j++) {
                if (index < maxSize) {
                    row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                    row.getCell((short) j).setCellValue(nameArray[index++]);
                } else {
                    loop = false;
                    break;
                }
            }
        }

        row = sheet.getRow(5);
        if (row != null && loop) {
            for (int j = 3; j < 5; j++) {
                if (index < maxSize) {
                    row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                    row.getCell((short) j).setCellValue(nameArray[index++]);
                } else {
                    loop = false;
                    break;
                }
            }
            for (int j = 7; j < 19; j++) {
                if (index < maxSize) {
                    row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                    row.getCell((short) j).setCellValue(nameArray[index++]);
                } else {
                    loop = false;
                    break;
                }
            }
            for (int j = 21; j < 23; j++) {
                if (index < maxSize) {
                    row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                    row.getCell((short) j).setCellValue(nameArray[index++]);
                } else {
                    loop = false;
                    break;
                }
            }
        }

        row = sheet.getRow(7);
        if (row != null && loop) {
            for (int j = 6; j < 12; j++) {
                if (index < maxSize) {
                    row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                    row.getCell((short) j).setCellValue(nameArray[index++]);
                } else {
                    loop = false;
                    break;
                }
            }
            for (int j = 14; j < 20; j++) {
                if (index < maxSize) {
                    row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                    row.getCell((short) j).setCellValue(nameArray[index++]);
                } else {
                    loop = false;
                    break;
                }
            }
        }
        File newFile = new File(request.getSession().getServletContext().getRealPath("/excelfile/zuoqutu") + "/" + "座区图_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".xls");//生成excel文件
        FileOutputStream fos = new FileOutputStream(newFile);
        wookbook.write(fos);
        fos.close();
        return newFile;
    }

    /**
     * 导出312座（新大楼报告厅）的excel
     *
     * @param request
     * @param nameArray
     * @param className
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public File zuoqutuExcel312(HttpServletRequest request, String[] nameArray, String className) throws FileNotFoundException, IOException {
        int index = 0;
        int maxSize = 0;
        File file = new File(request.getSession().getServletContext().getRealPath("/static/common/zuoqutu312.xls"));
        FileInputStream inputStream = new FileInputStream(file);
        // datalist拼装List<String[]> datalist,
        HSSFWorkbook wookbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wookbook.getSheetAt(0);

        sheet.getRow(1).getCell((short) 1).setEncoding(HSSFCell.ENCODING_UTF_16);
        sheet.getRow(1).getCell((short) 1).setCellValue(className);

        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        boolean loop = false;
        if (nameArray != null && nameArray.length > 0) {
            loop = true;
            maxSize = nameArray.length;
        }
        for (int i = 7; i < 11; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null && loop) {
                for (int j = 3; j < 8; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 9; j < 17; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 18; j < 26; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 27; j < 32; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
            } else {
                break;
            }

        }

        for (int i = 12; i < 20; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null && loop) {
                for (int j = 3; j < 8; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 9; j < 17; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 18; j < 26; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 27; j < 32; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
            } else {
                break;
            }

        }
        File newFile = new File(request.getSession().getServletContext().getRealPath("/excelfile/zuoqutu") + "/" + "座区图_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".xls");//生成excel文件
        FileOutputStream fos = new FileOutputStream(newFile);
        wookbook.write(fos);
        fos.close();
        return newFile;
    }

    /**
     * 导出207座（演播厅）的excel
     *
     * @param request
     * @param nameArray
     * @param className
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public File zuoqutuExcel207(HttpServletRequest request, String[] nameArray, String className) throws FileNotFoundException, IOException {
        int index = 0;
        int maxSize = 0;
        File file = new File(request.getSession().getServletContext().getRealPath("/static/common/zuoqutu207.xls"));
        FileInputStream inputStream = new FileInputStream(file);
        // datalist拼装List<String[]> datalist,
        HSSFWorkbook wookbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wookbook.getSheetAt(0);

        sheet.getRow(14).getCell((short) 0).setEncoding(HSSFCell.ENCODING_UTF_16);
        sheet.getRow(14).getCell((short) 0).setCellValue(className);

        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        boolean loop = false;
        if (nameArray != null && nameArray.length > 0) {
            loop = true;
            maxSize = nameArray.length;
        }
        for (int i = 10; i < 11; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null && loop) {
                for (int j = 2; j < 6; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 7; j < 16; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 17; j < 21; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }

            } else {
                break;
            }

        }

        for (int i = 9; i > -1; i--) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null && loop) {
                for (int j = 1; j < 6; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 7; j < 16; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 17; j < 22; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }

            } else {
                break;
            }

        }
        File newFile = new File(request.getSession().getServletContext().getRealPath("/excelfile/zuoqutu") + "/" + "座区图_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".xls");//生成excel文件
        FileOutputStream fos = new FileOutputStream(newFile);
        wookbook.write(fos);
        fos.close();
        return newFile;
    }

    /**
     * 导出100座(综合楼4楼演播厅)的excel
     *
     * @param request
     * @param nameArray
     * @param className
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public File zuoqutuExcel100of2(HttpServletRequest request, String[] nameArray, String className) throws FileNotFoundException, IOException {
        int index = 0;
        int maxSize = 0;
        File file = new File(request.getSession().getServletContext().getRealPath("/static/common/zuoqutu100of2.xls"));
        FileInputStream inputStream = new FileInputStream(file);
        // datalist拼装List<String[]> datalist,
        HSSFWorkbook wookbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wookbook.getSheetAt(0);

        sheet.getRow(0).getCell((short) 0).setEncoding(HSSFCell.ENCODING_UTF_16);
        sheet.getRow(0).getCell((short) 0).setCellValue(className);

        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        boolean loop = false;
        if (nameArray != null && nameArray.length > 0) {
            loop = true;
            maxSize = nameArray.length;
        }
//                    row.getCell((short) 5).setCellValue(courseArrange.getClassroomName());
        for (int i = 1; i < 6; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null && loop) {
                for (int j = 0; j < 6; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 7; j < 15; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }
                for (int j = 16; j < 22; j++) {
                    if (index < maxSize) {
                        row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                        row.getCell((short) j).setCellValue(nameArray[index++]);
                    } else {
                        loop = false;
                        break;
                    }
                }

            } else {
                break;
            }

        }
        File newFile = new File(request.getSession().getServletContext().getRealPath("/excelfile/zuoqutu") + "/" + "座区图_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".xls");//生成excel文件
        FileOutputStream fos = new FileOutputStream(newFile);
        wookbook.write(fos);
        fos.close();
        return newFile;
    }


    /**
     * 根据条件导出正式学员列表
     *
     * @param request
     * @param dir
     * @param headName
     * @param expName
     * @return
     * @throws Exception
     */
    public List<File> userListExcelByCondition(HttpServletRequest request, String dir, String[] headName, String expName) throws Exception {
        Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
        Pagination pagination = new Pagination();
        pagination.setPageSize(10000);
        String whereSql = addCondition(request);
        pagination.setRequest(request);
        find(pagination, whereSql);
        int num = pagination.getTotalPages();// 总页数
        List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
        for (int i = 1; i <= num; i++) {// 循环生成num个xls文件
            pagination.setCurrentPage(i);
            List<User> userList = find(pagination, whereSql);
            List<List<String>> list = userListExcelByConditionConvert(userList);
            File file = FileExportImportUtil.createExcelRed(headName, list, expName + "_" + i, dir);
            srcfile.add(file);
        }
        return srcfile;
    }


    /**
     * 根据条件导出已结业学员列表
     *
     * @param request
     * @param dir
     * @param headName
     * @param expName
     * @return
     * @throws Exception
     */
    public List<File> userListExcelByGraCondition(HttpServletRequest request, String dir, String[] headName, String expName) throws Exception {
        Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
        Pagination pagination = new Pagination();
        pagination.setPageSize(10000);
        String whereSql = addGraCondition(request);
        pagination.setRequest(request);
        find(pagination, whereSql);
        int num = pagination.getTotalPages();// 总页数
        List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
        for (int i = 1; i <= num; i++) {// 循环生成num个xls文件
            pagination.setCurrentPage(i);
            List<User> userList = find(pagination, whereSql);
            List<List<String>> list = userListExcelByConditionConvert(userList);
            File file = FileExportImportUtil.createExcelRed(headName, list, expName + "_" + i, dir);
            srcfile.add(file);
        }
        return srcfile;
    }


    /**
     * 将正式学员列表按照条件获取的集合转化为List<List<String>>类型.
     *
     * @param userList
     * @return
     */
    public List<List<String>> userListExcelByConditionConvert(List<User> userList) {
        List<List<String>> list = new ArrayList<List<String>>();
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                List<String> smallList = new ArrayList<String>();
                smallList.add(user.getName());
                smallList.add(user.getSex());
                smallList.add(user.getAge().toString());
                if (user.getPoliticalStatus() != null) {
                    switch (user.getPoliticalStatus().intValue()) {
                        case 0:
                            smallList.add("中共党员");
                            break;
                        case 1:
                            smallList.add("民主党派");
                            break;
                        case 2:
                            smallList.add("无党派人士");
                            break;
                        case 3:
                            smallList.add("群众");
                            break;
                        case 4:
                            smallList.add("其它");
                            break;
                        default:
                            smallList.add("");
                            break;
                    }
                } else {
                    smallList.add("");
                }
                if (user.getNationality() != null) {
                    if (user.getNationality().indexOf("族") <= -1) {
                        smallList.add(user.getNationality() + "族");
                    } else {
                        smallList.add(user.getNationality());
                    }
                } else {
                    smallList.add("");
                }
                smallList.add(user.getUnit());
                if (user.getBusiness() != null) {
                    switch (user.getBusiness()) {
                        case 1:
                            smallList.add("正厅");
                            break;
                        case 2:
                            smallList.add("巡视员");
                            break;
                        case 3:
                            smallList.add("副厅");
                            break;
                        case 4:
                            smallList.add("副巡视员");
                            break;
                        case 5:
                            smallList.add("正县");
                            break;
                        case 6:
                            smallList.add("副县");
                            break;
                        case 7:
                            smallList.add("调研员");
                            break;
                        case 8:
                            smallList.add("副调研员");
                            break;
                        case 9:
                            smallList.add("正科");
                            break;
                        case 10:
                            smallList.add("副科");
                            break;
                        default:
                            smallList.add("");
                            break;
                    }
                } else {
                    smallList.add("");
                }
                smallList.add(user.getJob());
                smallList.add(user.getQualification());
                List<Map<String, String>> baseUserList = baseHessianService.querySysUser(3, user.getId());
                if (baseUserList != null && baseUserList.size() > 0) {
                    smallList.add(baseUserList.get(0).get("mobile"));
                } else {
                    smallList.add("");
                }
                smallList.add(user.getIdNumber());
                smallList.add(SDF.format(user.getCreateTime()));
                smallList.add(user.getGroupId() != null ? user.getGroupId() + "" : "");
                switch (user.getStatus()) {
                    case 1:
                        smallList.add("正常");
                        break;
                    case 7:
                        smallList.add("已结业");
                        break;
                    case 8:
                        smallList.add("在校");
                        break;
                    default:
                        smallList.add("");
                        break;
                }
                smallList.add(user.getNote());
                list.add(smallList);
            }
        }
        return list;
    }

    /**
     * 根据传入参数的手机号查找是否之前有学员，且在同一班次，如果存在返回true,否则返回false.
     *
     * @param user
     * @return
     */
    public boolean checkInOneClass(User user) {
        List<Map<String, String>> userMapList = baseHessianService.queryStudentByMobile(user.getMobile());
        if (userMapList != null && userMapList.size() > 0) {
            for (Map<String, String> userMap : userMapList) {
                User beforeUser = this.findById(Long.parseLong(userMap.get("linkId")));
                if (beforeUser == null) {
                    return false;
                }
                if (beforeUser.getClassId().equals(user.getClassId())) {
                    return true;
                }
            }
        }
        List<User> userList = this.find(null, " idNumber='" + user.getIdNumber() + "' and classId=" + user.getClassId());
        if (userList != null && userList.size() > 0) {
            return true;
        }
        return false;
    }
}
