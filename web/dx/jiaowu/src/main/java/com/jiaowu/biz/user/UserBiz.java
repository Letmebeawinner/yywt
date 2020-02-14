package com.jiaowu.biz.user;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.classes.ClassTypeBiz;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.common.BaseHessianService;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.biz.unit.UnitBiz;
import com.jiaowu.common.FileExportImportUtil;
import com.jiaowu.common.RegExpressionUtil;
import com.jiaowu.dao.user.UserDao;
import com.jiaowu.entity.classes.ClassType;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.course.CourseArrange;
import com.jiaowu.entity.teach.TeachingProgramCourse;
import com.jiaowu.entity.unit.Unit;
import com.jiaowu.entity.user.User;
import com.jiaowu.entity.userInfo.UserInfo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserBiz extends BaseBiz<User,UserDao>{
    @Autowired
    private ClassesBiz classesBiz;
    @Autowired
    private BaseHessianService baseHessianService;
    @Autowired
    CourseArrangeBiz courseArrangeBiz;
    @Autowired
    ClassTypeBiz classTypeBiz;
    @Autowired
    private UnitBiz unitBiz;

    private static final int HMAC_KEY_LEN = 60;

    public String createUser(User user,String year,String month,Map<String, String> userMap){
        //获取单位的ID,根据名称去获取ID.
        List<Unit> unitList=unitBiz.find(null," status=1 and name='"+user.getUnit()+"'");
        if(unitList!=null&&unitList.size()>0){
            user.setUnitId(unitList.get(0).getId());
        }else{
            user.setUnitId(0L);
        }
//		User user=setUserByUserInfo(userInfo,year,month);
        Classes classes=classesBiz.findById(user.getClassId());
        user.setSerialNumber(calculateSerialNumber(classes.getStudentTotalNum()));
        user.setStudentId(year+month+user.getClassTypeId()+classes.getClassNumber()+user.getSerialNumber());

        String mobile=user.getMobile();
        String email=user.getEmail();
        String password=user.getPassword();
        user.setMobile(null);
        user.setEmail(null);
        user.setPassword(null);
        user.setConfirmPassword(null);
        //如果当前登录人是管理员,则设置user的sysUserId为当前管理员.
        /*if(userMap.get("userType").equals(1)){
            user.setSysUserId(Long.parseLong(userMap.get("id")));
        }*/
        save(user);
        user.setMobile(mobile);
        user.setEmail(email);
        user.setPassword(password);
        //保存学员信息之后,获取到学员的ID,设置userInfo的ID,将该ID保存到base系统中.
        user.setId(user.getId());
        String result=addStudentToBase(user);
        //如果result不为null,则说明向base项目添加数据失败,删除教务中的该用户.
        if(!StringUtils.isTrimEmpty(result)&&!(result.equals(""))&&!isNumeric(result)&&!result.contains("成功")){
            deleteById(user.getId());
        }
        return result;
    }

    /**
     * 判断字符穿是否为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        if(str==null||str.equals("")){
            return false;
        }
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public void updateUser(User user){
        updateStudentMobileAndEmailToBase(user);
        //教务user表中没有mobile和email两个字段
        user.setMobile(null);
        user.setEmail(null);
        update(user);
    }

    public User setUserByUserInfo(UserInfo userInfo){
        User user=new User();
        user.setName(userInfo.getName());
        user.setIdNumber(userInfo.getIdNumber());
        user.setSex(userInfo.getSex());
        user.setAge(userInfo.getAge());
        user.setNote(userInfo.getNote());
        return user;
    }

    public User setUserByUserInfo(UserInfo userInfo,String year,String month){
        User user=new User();
        user.setClassTypeId(userInfo.getClassTypeId());
        user.setClassTypeName(userInfo.getClassTypeName());
        user.setClassId(userInfo.getClassId());
        user.setClassName(userInfo.getClassName());
        user.setSerialNumber(userInfo.getSerialNumber());
        user.setName(userInfo.getName());
//		user.setJob(userInfo.getJob());
        user.setIdNumber(userInfo.getIdNumber());
        user.setSex(userInfo.getSex());
        user.setAge(userInfo.getAge());
        Classes classes=classesBiz.findById(user.getClassId());
        user.setSerialNumber(calculateSerialNumber(classes.getStudentTotalNum()));
        user.setStudentId(year+month+user.getClassTypeId()+classes.getClassNumber()+user.getSerialNumber());
        user.setStatus(2);
        user.setIsMonitor(0);
        user.setBaodao(0);
        user.setHasApprove(0);
        user.setHasCheck(0);
        user.setNote(userInfo.getNote());
        return user;
    }

    public String calculateSerialNumber(Long studentTotalNum){
        Long serialNumber=studentTotalNum+1;
        if(serialNumber>99){
            return serialNumber+"";
        }else if(serialNumber>9){
            return "0"+serialNumber;
        }else{
            return "00"+serialNumber;
        }
    }

    public String addStudentToBase(User user){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("userName", user.getName());
        map.put("password", user.getPassword());
        map.put("email", user.getEmail());
        map.put("mobile", user.getMobile());
        map.put("status",0);
        map.put("userType",3);
        map.put("linkId",user.getId());
        return baseHessianService.addSysUser(map);
    }


    public void updateStudentMobileAndEmailToBase(User user){
        List<Map<String,String>> baseUserList=baseHessianService.querySysUser(3,user.getId());
        if(baseUserList!=null&&baseUserList.size()>0){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("id", baseUserList.get(0).get("id"));
            map.put("email", user.getEmail());
            map.put("mobile", user.getMobile());
            map.put("userName", baseUserList.get(0).get("userName"));
            map.put("linkId", Long.parseLong(baseUserList.get(0).get("linkId")));
            baseHessianService.updateSysUser(map);
        }
    }

    public String batchImportStudent(MultipartFile myFile, HttpServletRequest request) throws Exception{
        Map<String,String> userMap= SysUserUtils.getLoginSysUser(request);
        if(!userMap.get("userType").equals("1")){
            return "您无权限导入学员";
        }
        List<Long> departmentIdList =new ArrayList<>();
        departmentIdList.add(78L);//学员部门
        Long sysUserId=Long.parseLong(userMap.get("id"));
        StringBuffer msg =new StringBuffer();
        Pattern pattern = Pattern.compile("[0-9]*");
        Calendar calendar=Calendar.getInstance();
        String year=calendar.get(Calendar.YEAR)+"";
        String month=(calendar.get(calendar.MONTH)+1)+"";
        if(month.length()==1){
            month="0"+month;
        }
        // datalist拼装List<String[]> datalist,
        HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
        HSSFSheet sheet = wookbook.getSheetAt(0);

        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        for (int i = 1; i <rows+1; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null) {
                String classTypeId = getCellValue(row.getCell((short) 0));//班型ID
                if(StringUtils.isTrimEmpty(classTypeId)||Long.parseLong(classTypeId)<=0||classTypeId.length()!=1){
                    /*if(rows==2&&i==2){//当学员记录为0 1 2时,rows均为2.所以就可能该行就是没有数据.这样写就有一个问题,如果学员记录有两条,第二条记录的班型ID为空,就无法正确提示,不过这已经将失误降到最小了.
                        break;
                    }*/
                    msg.append("第"+i+"行班型ID格式错误;");
                    continue;
                }
                String classId = getCellValue(row.getCell((short) 1));//班次ID
                if(StringUtils.isTrimEmpty(classId)||Long.parseLong(classId)<=0){
                    msg.append("第"+i+"行班次ID格式错误;");
                    continue;
                }
                String name = getCellValue(row.getCell((short) 2));//名称
                if(StringUtils.isTrimEmpty(name)){
                    msg.append("第"+i+"行名称为空;");
                    continue;
                }
                String idNumber = getCellValue(row.getCell((short) 3));//身份证号
                if(StringUtils.isTrimEmpty(idNumber)){
                    msg.append("第"+i+"行身份证号为空;");
                    continue;
                }else{
                    Matcher isNum = pattern.matcher(idNumber);
                    if( !isNum.matches() ){
                        msg.append("第"+i+"行身份证号格式错误;");
                        continue;
                    }
                    if((idNumber.length()!=18&&idNumber.length()!=15)){
                        msg.append("第"+i+"行身份证号格式错误;");
                        continue;
                    }
                    List<User> userList=this.find(null," status!=0 and idNumber='"+idNumber+"'");
                    if(userList!=null&&userList.size()>0){
                        msg.append("第"+i+"行身份证号重复");
                        continue;
                    }
                }
                String mobile = getCellValue(row.getCell((short) 4));//手机号
                if(StringUtils.isTrimEmpty(mobile)||!RegExpressionUtil.isMobile(mobile.toLowerCase())){
                    msg.append("第"+i+"行手机号格式错误;");
                    continue;
                }
                String email = getCellValue(row.getCell((short) 5));//邮箱
                if(StringUtils.isTrimEmpty(email)||!RegExpressionUtil.isEmail(email.toLowerCase())){
                    msg.append("第"+i+"行邮箱格式错误;");
                    continue;
                }
                String password = getCellValue(row.getCell((short) 6));//密码
                if(StringUtils.isTrimEmpty(password)){
                    msg.append("第"+i+"行密码格式错误;");
                    continue;
                }
                String sex = getCellValue(row.getCell((short) 7));//性别
                if(StringUtils.isTrimEmpty(sex)||(!sex.equals("男")&&(!sex.equals("女")))){
                    msg.append("第"+i+"行性别格式错误;");
                    continue;
                }
				/*String age = getCellValue(row.getCell((short) 8));//年龄
				if(StringUtils.isTrimEmpty(age)||Long.parseLong(age)<=0){
					msg.append("第"+i+"行年龄格式错误;");
					continue;
				}*/
                String age=getAgeByIdNumber(idNumber);
                String politicalStatus = getCellValue(row.getCell((short) 8));//政治面貌
                Long politicalStatusInt=-1L;
                String[] politicalStatusArray=new String[]{"中共党员","民主党派","无党派人士","群众","其它"};
				/*if(!"中共党员".equals(politicalStatus)&&!"民主党派".equals(politicalStatus)&&!"无党派人士".equals(politicalStatus)&&!"群众".equals(politicalStatus)&&!"其它".equals(politicalStatus)){
					msg.append("第"+i+"行政治面貌填写错误;");
					continue;
				}*/
                int j;
                for(j=0;j<politicalStatusArray.length;j++){
                    if(politicalStatusArray[j].equals(politicalStatus)){
                        politicalStatusInt=(long)j+1;
                        break;
                    }
                }
                if(j==politicalStatusArray.length){
                    msg.append("第"+i+"行政治面貌填写错误;");
                    continue;
                }
                String qualification = getCellValue(row.getCell((short) 9));//学历
                if(StringUtils.isTrimEmpty(qualification)){
                    msg.append("第"+i+"行学历错误;");
                    continue;
                }
                String unit = getCellValue(row.getCell((short) 10));//单位
                Long unitId=0L;
                List<Unit> unitList=unitBiz.find(null," status=1 and name='"+unit+"'");
                if(unitList==null||unitList.size()==0){
					/*msg.append("第"+i+"行单位填写错误;");
					continue;*/
                }else{
                    unitId=unitList.get(0).getId();
                }
                String job = getCellValue(row.getCell((short) 11));//职务职称
                if(StringUtils.isTrimEmpty(job)){
                    msg.append("第"+i+"行职务职称填写错误;");
                    continue;
                }
                String business = getCellValue(row.getCell((short) 12));//级别
                Integer businessInt=0;
                String[] businessArray=new String[]{"正厅","巡视员","副厅","副巡视员","正县","副县","调研员","副调研员","正科","副科"};
                for(j=0;j<businessArray.length;j++){
                    if(businessArray[j].equals(business)){
                        businessInt=j+1;
                        break;
                    }
                }
                if(j==businessArray.length){
                    msg.append("第"+i+"行级别填写错误;");
                    continue;
                }
                String nationality = getCellValue(row.getCell((short) 13));//民族
                if(StringUtils.isTrimEmpty(nationality)){
                    msg.append("第"+i+"行民族填写错误;");
                    continue;
                }
                String note = getCellValue(row.getCell((short) 14));//备注

                User user=new User();
                ClassType classType=classTypeBiz.findById(Long.parseLong(classTypeId));
                if(classType!=null){
                    user.setClassTypeId(Long.parseLong(classTypeId));
                    user.setClassTypeName(classType.getName());
                }else{
                    msg.append("第"+i+"行班型不存在;");
                    continue;
                }

                Classes classes=classesBiz.findById(Long.parseLong(classId));
//				List<Classes> classesList=classesBiz.find(null, " classId="+classId);
                if(classes!=null){
                    if(classes.getMaxNum()<=classes.getStudentTotalNum()){
                        msg.append("第"+i+"行该班次报名人数已满,请选择其它班次!;");
                        continue;
                    }
                    user.setClassId(Long.parseLong(classId));
                    user.setClassName(classes.getName());
                    user.setSerialNumber(calculateSerialNumber(classes.getStudentTotalNum()));
					/*int index=classIdList.indexOf(user.getClassId());
					if(index>=0){
						addNumList.set(index,addNumList.get(index)+1);
					}else{
						classIdList.add(user.getClassId());
						addNumList.add(1L);
					}*/
                }else{
                    msg.append("第"+i+"行班次不存在;");
                    continue;
                }
//				user.setSerialNumber(serialNumber);

                user.setStudentId(year+month+classTypeId+classes.getClassNumber()+user.getSerialNumber());
                user.setName(name);
                user.setIdNumber(idNumber);
//				user.setMobile(mobile);
//				user.setEmail(email);
                if(StringUtils.isTrimEmpty(password)){
                    password="111111";
                }
//				String customerKey =getRandomString(HMAC_KEY_LEN);
//				password = PurseSecurityUtils.secrect(password, customerKey);
//				user.setPassword(password);
//				user.setCustomerKey(customerKey);
                user.setSex(sex);
                if(StringUtils.isTrimEmpty(age)||Long.parseLong(age)<=0){
                    age="0";
                }
                user.setAge(Integer.parseInt(age));
                user.setNote(note);
//				user.setJob(job);
                user.setBaodao(0);
                user.setStatus(1);
                user.setIsMonitor(0);
                user.setHasApprove(0);
                user.setHasCheck(0);
                user.setSysUserId(sysUserId);
                user.setPoliticalStatus(politicalStatusInt);
                user.setQualification(qualification);
                user.setUnitId(unitId);
                user.setUnit(unit);
                user.setJob(job);
                user.setBusiness(businessInt);
                user.setNationality(nationality);
                save(user);
                //base系统增加一条记录
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("userName", user.getName());
                map.put("password", password);
                map.put("email", email);
                map.put("mobile", mobile);
                map.put("status",0);
                map.put("userType",3);
                map.put("linkId",user.getId());
                //先向base项目中插入数据
                String errorInfo=baseHessianService.addSysUser(map);
                //如果没有错误,则保存用户到教务,否则将addNumList该用户对应的班次的用户数量-1.
                if(StringUtils.isTrimEmpty(errorInfo)||isNumeric(errorInfo)&&!errorInfo.contains("成功")){

                    classes.setStudentTotalNum(classes.getStudentTotalNum()+1);
                    classesBiz.update(classes);
                    //设置学员部门为"学员"
                    baseHessianService.updateSysUserDepartment(Long.parseLong(errorInfo),departmentIdList);
                }else{
					/*int index=classIdList.indexOf(user.getClassId());
					addNumList.set(index, addNumList.get(index)-1);*/
                    deleteById(user.getId());
                    msg.append("第"+i+"行"+errorInfo+";");
                }
            }
        }
//		updateClassStudentNum(classIdList,addNumList);
        return msg.toString();
    }


    public String getAgeByIdNumber(String idNumber){
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
            //15位身份证
            String idNumber1 = idNumber.substring(6, 8); //截取身份证的年
            int b = Integer.valueOf("19" + idNumber1).intValue();
            userAge = year - b; //实际年龄
        }
        return userAge+"";
    }

    /**
     * 去掉数字前面的0
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
    public User queryUserByIdentityNumber(String idNumber){
        String whereSql =" idNumber="+idNumber;
        List<User> userList =	this.find(null,whereSql);
        if(userList!=null&&userList.size()>0){
            return 	userList.get(0);
        }else{
            return null;
        }
    }



    //修改指定用户的数据

    public void updateUserById(User user, Long id){
        String whereSql =" id="+id;
        this.updateByStrWhere(user,whereSql);
    }



    public void updateClassStudentNum(List<Long> classIdList,List<Long> addNumList){
        if(classIdList.size()>0){
            for(int i=0;i<classIdList.size();i++){
                Classes classes=classesBiz.findById(classIdList.get(i));
                classes.setStudentTotalNum(classes.getStudentTotalNum()+addNumList.get(i));
                classesBiz.update(classes);
            }
        }
    }

    /**
     * @Description 获得Hsscell内容
     * @param cell
     * @return
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

    public List<Map<String,Object>> getAllProgressOfOneStudentProgramCourse(List<TeachingProgramCourse> teachingProgramCourseList){
        List<Map<String,Object>> list=new LinkedList<Map<String,Object>>();
        if(teachingProgramCourseList!=null&&teachingProgramCourseList.size()>0){
            for(TeachingProgramCourse teachingProgramCourse:teachingProgramCourseList){
                Map<String,Object> map=getProgressOfOneStudentProgramCourse(teachingProgramCourse);
                if(map!=null){
                    list.add(map);
                }
            }
        }
        return list;
    }

    public Map<String,Object> getProgressOfOneStudentProgramCourse(TeachingProgramCourse teachingProgramCourse){
        List<CourseArrange> courseArrangeList=courseArrangeBiz.find(null," status=1 and teachingProgramCourseId="+teachingProgramCourse.getId());
        if(courseArrangeList!=null&&courseArrangeList.size()>0){
            int hasFinishedCourseArrange=getProgressByCourseArrangeList(courseArrangeList);
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("courseName", teachingProgramCourse.getCourseName());
            map.put("progress",(int)(Float.parseFloat(hasFinishedCourseArrange+"")/courseArrangeList.size()*100));
            return map;
        }
        return null;
    }

    public int getProgressByCourseArrangeList(List<CourseArrange> courseArrangeList){
        Long currentTimeMillis=new Date().getTime();
        int hasFinishedCourseArrange=0;
        for(CourseArrange courseArrange:courseArrangeList){
            if(courseArrange.getEndTime().getTime()<currentTimeMillis)
                hasFinishedCourseArrange++;
        }
        return hasFinishedCourseArrange;
    }

    /**
     * 获取轮训报名名单的excel文件列表
     * @param request
     * @param dir
     * @param headName
     * @param expName
     * @return
     * @throws Exception
     */
    public List<File> getExcelUserList(HttpServletRequest request, String dir, String[] headName, String expName) throws Exception{
        Pagination pagination = new Pagination();
        pagination.setPageSize(10000);
        String whereSql=addCondition(request);

        Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
        if(userMap.get("userType").equals("2")){
            String teacherId=userMap.get("linkId");
            List<Classes> classesList=classesBiz.find(null," teacherId="+teacherId);
            if(classesList!=null&&classesList.size()>0){
                whereSql+=" and classId="+classesList.get(0).getId();
            }
        }
        pagination.setRequest(request);
        find(pagination,whereSql);
        int num = pagination.getTotalPages();// 总页数
        List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
        for (int i = 1; i <= num; i++) {// 循环生成num个xls文件
            pagination.setCurrentPage(i);
            List<User> userList = find(pagination,whereSql);
            List<List<String>> list=convert(userList);
            File file = FileExportImportUtil.createExcel(headName, list, expName + "_" + i, dir);
            srcfile.add(file);
        }
        return srcfile;
    }

    /**
     * 增加心得搜索条件
     * @param request
     * @return
     */
    public String addCondition(HttpServletRequest request){
        StringBuffer sb=new StringBuffer();
        sb.append(" status=1");
        String userId=request.getParameter("userId");
        if(!StringUtils.isTrimEmpty(userId) && Long.parseLong(userId)>0){
            sb.append(" and id="+userId);
        }
        String classTypeId=request.getParameter("classTypeId");
        if(!StringUtils.isTrimEmpty(classTypeId) && Long.parseLong(classTypeId)>0){
            sb.append(" and classTypeId="+classTypeId);
        }
        String classId=request.getParameter("classId");
        if(!StringUtils.isTrimEmpty(classId) && Long.parseLong(classId)>0){
            sb.append(" and classId="+classId);
        }
        String studentId=request.getParameter("studentId");
        if(!StringUtils.isTrimEmpty(studentId)){
            sb.append(" and studentId like '%"+studentId+"%'");
        }
        String name=request.getParameter("name");
        if(!StringUtils.isTrimEmpty(name)){
            sb.append(" and name like '%"+name+"%'");
        }
        String idNumber=request.getParameter("idNumber");
        if(!StringUtils.isTrimEmpty(idNumber)){
            sb.append(" and idNumber like '%"+idNumber+"%'");
        }
        String unitId=request.getParameter("unitId");
        if(!StringUtils.isTrimEmpty(unitId)&&!unitId.equals("0")){
            sb.append(" and unitId="+unitId);
        }
        String time=request.getParameter("time");
        request.setAttribute("time",time);
        if(!StringUtils.isTrimEmpty(time)){
            if(time.equals("1")){
                sb.append(" and createTime>'"+getThisYearBeginTime()+"' and createTime<'"+getThisYearMiddleTime()+"'");
            }else if(time.equals("2")){
                sb.append(" and createTime>'"+getThisYearBeginTime()+"' and createTime<'"+getThisYearEndTime()+"'");
            }else if(time.equals("3")){
                sb.append(" and createTime>'"+getFourYearBeforeTime()+"' and createTime<'"+getThisYearEndTime()+"'");
            }
        }
        return sb.toString();
    }

    /**
     * 将XinDe的集合转化为List<List<String>>类型.
     * @param userList
     * @return
     */
    public List<List<String>> convert(List<User> userList){
        List<List<String>> list=new ArrayList<List<String>>();
        if(userList!=null&&userList.size()>0){
            for(User user:userList){
                List<String> smallList=new ArrayList<String>();
                smallList.add(user.getClassTypeName());
                smallList.add(user.getClassName());
                smallList.add(user.getName());
                smallList.add(user.getStudentId());
                smallList.add(user.getIdNumber());
                smallList.add(user.getSex());
                smallList.add(user.getAge()+"");
                smallList.add(user.getUnit());

                smallList.add(user.getJob());
                switch(user.getBusiness()){
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
                switch(user.getPoliticalStatus().intValue()){
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
                smallList.add(user.getQualification());
                list.add(smallList);
            }
        }
        return list;
    }

    public String getThisYearBeginTime(){
        Calendar c=Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR)+"-01-01 :00:00:00";
    }

    public String getThisYearMiddleTime(){
        Calendar c=Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR)+"-06-01 :00:00:00";
    }

    public String getThisYearEndTime(){
        Calendar c=Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR)+"-12-31 :00:00:00";
    }

    public String getFourYearBeforeTime(){
        Calendar c=Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR,-4);
        return c.get(Calendar.YEAR)+"-01-01 :00:00:00";
    }


    public File zuoqutuExcel(HttpServletRequest request,String[] nameArray,String className)throws FileNotFoundException,IOException {
        int[] oddarray=new int[]{9,11,13,16,18,20};
        int[] evenarray=new int[]{8,10,12,17,19,21};
        int length=nameArray.length;
        File file=new File(request.getSession().getServletContext().getRealPath("/static/common/zuoqutu.xls"));
        FileInputStream inputStream=new FileInputStream(file);
        // datalist拼装List<String[]> datalist,
        HSSFWorkbook wookbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wookbook.getSheetAt(0);

        sheet.getRow(0).getCell((short)0).setEncoding(HSSFCell.ENCODING_UTF_16);
        sheet.getRow(0).getCell((short)0).setCellValue(className);

        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        int index=0;
//                    row.getCell((short) 5).setCellValue(courseArrange.getClassroomName());
        for (int i = 5; i <rows+1; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null) {
//                System.out.println("-------第"+i+"行"+"-----------0:"+getCellValue(row.getCell((short) 0))+"-----------1:"+getCellValue(row.getCell((short) 1))+"-------------2:"+getCellValue(row.getCell((short) 2))+"--------------3:"+getCellValue(row.getCell((short) 3))+"---------4:"+getCellValue(row.getCell((short) 4))+"--------------5:"+getCellValue(row.getCell((short) 5)));
                switch(i){
                    case 5:
                        for(int j=5;j<9;j++){
                            if(index>=length){
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);

                            index++;
                        }
                        for(int j=10;j<27;j++){
                            if(index>=length){
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        for(int j=28;j<32;j++){
                            if(index>=length){
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        break;
                    case 6:
                        for(int j=32;j>27;j--){
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
                        }
                        break;
                    case 7:
                        for(int j=3;j<9;j++){
                            if(index>=length){
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        for(int j=10;j<27;j++){
                            if(index>=length){
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        for(int j=28;j<34;j++){
                            if(index>=length){
                                break;
                            }
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index]);
                            index++;
                        }
                        break;
                    case 15:
                        for(int j=32;j>27;j--){
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
                        }
                        break;
                    default:
                        boolean odd=false;
                        for(int j=0;j<oddarray.length;j++){
                            if(oddarray[j]==i){
                                odd=true;
                                break;
                            }
                        }
                        if(odd){
                            for(int j=2;j<9;j++){
                                if(index>=length){
                                    break;
                                }
                                row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                                row.getCell((short) j).setCellValue(nameArray[index]);
                                index++;
                            }
                            for(int j=10;j<27;j++){
                                if(index>=length){
                                    break;
                                }
                                row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                                row.getCell((short) j).setCellValue(nameArray[index]);
                                index++;
                            }
                            for(int j=28;j<35;j++){
                                if(index>=length){
                                    break;
                                }
                                row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                                row.getCell((short) j).setCellValue(nameArray[index]);
                                index++;
                            }
                        }else{
                            for(int j=34;j>27;j--){
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


    public File zuoqutuExcel100(HttpServletRequest request,String[] nameArray,String className)throws FileNotFoundException,IOException {
        int index=0;
        int maxSize=0;
        File file=new File(request.getSession().getServletContext().getRealPath("/static/common/zuoqutu100.xls"));
        FileInputStream inputStream=new FileInputStream(file);
        // datalist拼装List<String[]> datalist,
        HSSFWorkbook wookbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wookbook.getSheetAt(0);

        sheet.getRow(0).getCell((short)0).setEncoding(HSSFCell.ENCODING_UTF_16);
        sheet.getRow(0).getCell((short)0).setCellValue(className);

        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        boolean loop=false;
        if(nameArray!=null&&nameArray.length>0){
            loop=true;
            maxSize=nameArray.length;
        }
//                    row.getCell((short) 5).setCellValue(courseArrange.getClassroomName());
        for (int i = 5; i <13; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            if(i%2==1) {
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
            }else{
                // 行不为空
                if (row != null && loop) {
                    for (int j = 16; j > 12; j--) {
                        if (index < maxSize) {
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index++]);
                        } else {
                            loop = false;
                            break;
                        }
                    }

                    for (int j = 11; j >5; j--) {
                        if (index < maxSize) {
                            row.getCell((short) j).setEncoding(HSSFCell.ENCODING_UTF_16);
                            row.getCell((short) j).setCellValue(nameArray[index++]);
                        } else {
                            loop = false;
                            break;
                        }
                    }
                    for (int j = 4; j >0; j--) {
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
    public void updateGraduateStudentStatus(){
        List<Classes> classesList=classesBiz.find(null," end=0 and endTime<='"+new Date()+"'");
        if(classesList!=null&&classesList.size()>0){
            for(Classes classes:classesList){
                Classes tempClass=new Classes();
                tempClass.setId(classes.getId());
                tempClass.setEnd(1);
                classesBiz.update(classes);
                List<User> userList=find(null," classId="+classes.getId());
                if(userList!=null&&userList.size()>0){
                    for(User user:userList){
                        User tempUser=new User();
                        tempUser.setId(user.getId());
                        tempUser.setStatus(7);
                        update(tempUser);
                    }
                }
            }
        }
    }


    private void addEmailAndMobileToUserList(List<User> userList) {
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                List<Map<String, String>> baseUserList = baseHessianService.querySysUser(3, user.getId());

                if (baseUserList != null && baseUserList.size() > 0) {
                    user.setMobile(baseUserList.get(0).get("mobile"));
                    user.setEmail(baseUserList.get(0).get("email"));
                }
            }
        }
    }


    /**
     * 将用户信息的集合转化为List<List<String>>类型.
     * @param userList 将信息转换为了导出
     * @return
     */
    private List<List<String>> convertForUserExport(List<User> userList){
        //一组多少人
        int numbers = 50;
        int i = 0;
        List<List<String>> list=new ArrayList<List<String>>();
        if(userList!=null&&userList.size()>0){
            for(User user:userList){
                List<String> smallList=new ArrayList<String>();
                smallList.add((i % numbers + 1) + "");
                smallList.add(user.getName());
                smallList.add(user.getSex());
                if (user.getPoliticalStatus() == null) {
                    smallList.add("");
                } else {
                    switch(user.getPoliticalStatus().intValue()){
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
                smallList.add(user.getAge()+"");
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
                ++ i;
            }
        }
        return list;
    }
    /**
     * 按照学员处所提表格导出某班次学员列表
     * @param request
     * @param classId
     * @return
     * @throws Exception
     */
    public File newUserListExcel(HttpServletRequest request,Long classId)throws Exception{
        Classes classes=classesBiz.findById(classId);
        List<User> userList=find(null, " status in (1,7,8) and classId="+classId+" order by unitId,business");
        addEmailAndMobileToUserList(userList);
        List<List<String>> userPackageInfos = convertForUserExport(userList);

        Iterator<User> iterator=null;
        User user=null;
        HSSFRow row=null;
        if(userList!=null&&userList.size()>0){
            iterator=userList.iterator();
        }
        File file=new File(request.getSession().getServletContext().getRealPath("/static/common/export_user_list_model.xls"));
        FileInputStream inputStream=new FileInputStream(file);
        HSSFWorkbook wookbook = new HSSFWorkbook(inputStream);
        HSSFSheet sheet = wookbook.getSheetAt(0);
        sheet.getRow(0).getCell(Short.valueOf("0")).setEncoding(HSSFCell.ENCODING_UTF_16);
        sheet.getRow(0).getCell(Short.valueOf("0")).setCellValue(classes.getName()+"学员名单("+userList.size()+"人)");
        List<Long> userIdList=null;
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
        if(iterator!=null) {
            for (int i = 10; i < cnt + 10 + groupNumber; i++) {
                int j = 0;
                int ii = (i - 10) % number;
                int jj = (i - 10) /number;
                if (ii == 0) {
                    //每组的实际人数
                    int _number = number - 1;
                    if (jj + 1 == groupNumber) {
                        _number = cnt - (jj * (number - 1));
                    }
                    row = sheet.createRow((short)i);
                    cell = row.createCell((short) j);
                    cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
                    cell.setCellStyle(style);
                    //合并单元格
                    sheet.addMergedRegion(new Region(i,(short)0,i,(short)9));
                    cell.setCellValue("第" + (jj + 1) + "组,共" + _number + "人");
                    i ++;
                }
                row = sheet.createRow((short) i);
                List<String> strs = userPackageInfos.get(k);
                for (String str : strs) {
                    cell = row.createCell((short) j);
                    cell.setEncoding(HSSFWorkbook.ENCODING_UTF_16);
                    cell.setCellValue(str);
                    j ++;
                }
                k ++;
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
}
