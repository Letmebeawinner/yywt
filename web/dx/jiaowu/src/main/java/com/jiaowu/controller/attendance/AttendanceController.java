package com.jiaowu.controller.attendance;

import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.google.gson.Gson;
import com.jiaowu.biz.attendance.AttendanceBiz;
import com.jiaowu.biz.classes.ClassTypeBiz;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.holiday.HolidayBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.biz.userWorkDayData.UserWorkDayDataBiz;
import com.jiaowu.common.CommonConstants;
import com.jiaowu.common.FileExportImportUtil;
//import com.jiaowu.common.StudentCommonConstants;
import com.jiaowu.entity.classes.ClassType;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.holiday.Holiday;
import com.jiaowu.entity.user.User;
import com.jiaowu.entity.user.UserCondition;
import com.jiaowu.entity.userWorkDayData.UserWorkDayData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 考勤Controller
 *
 * @author 李帅雷
 *
 */
@Controller
public class AttendanceController extends BaseController{
	private static Logger logger = LoggerFactory
			.getLogger(AttendanceController.class);
	@Autowired
	private AttendanceBiz attendanceBiz;
	@Autowired
	private ClassTypeBiz classTypeBiz;
	@Autowired
	private UserBiz userBiz;
	@Autowired
	private UserWorkDayDataBiz userWorkDayDataBiz;
	@Autowired
	private ClassesBiz classesBiz;
	@Autowired
	private HolidayBiz holidayBiz;

	@InitBinder({"userCondition"})
	public void initUserCondition(WebDataBinder binder){
		binder.setFieldDefaultPrefix("userCondition.");
	}
	@InitBinder
	protected  void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	/*@Autowired
	private WorkSourceService workSourceService;*/
	private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	private static final String ADMIN_PREFIX = "/admin/jiaowu/attendance";

	/**
	 * 考勤列表
	 * @param request
	 * @param pagination
	 * @return
	 */
	@RequestMapping(ADMIN_PREFIX+"/attendanceList")
	public String attendanceList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("userCondition")UserCondition userCondition){
		try{
			List<ClassType> classTypeList=classTypeBiz.find(null," status=1");
			request.setAttribute("classTypeList",classTypeList);

			List<User> userList=null;
			pagination.setRequest(request);
			Map<String,Object> map=getOnePageData(parseAttendanceData(getAttendanceData(userCondition)),pagination);
			List<Map<String,Object>> attendanceList=(List<Map<String,Object>>)map.get("data");
			if(attendanceList!=null&&attendanceList.size()>0){
				for(Map<String,Object> attendance:attendanceList){
					String perId=attendance.get("Per_ID").toString();
					userList=userBiz.find(null," status=1 and perId ='"+perId+"'");
					if(userList!=null&&userList.size()>0){
						attendance.put("classTypeName",userList.get(0).getClassTypeName());
						attendance.put("className",userList.get(0).getClassName());
						attendance.put("userName",userList.get(0).getName());
					}
				}
			}
			pagination=(Pagination)map.get("pagination");
			request.setAttribute("attendanceList",attendanceList);
			request.setAttribute("pagination",pagination);
			request.setAttribute("userCondition",userCondition);
//			request.setAttribute("xinDe",xinDe);
		}catch (Exception e){
			e.printStackTrace();
		}
		return "/admin/attendance/attendance_list";
	}

	/**
	 * 获取考勤原始数据
	 * @return
	 */
	public String getAttendanceData(UserCondition userCondition){
		StringBuffer whereSql=new StringBuffer(" where 1=1");
		if(!StringUtils.isTrimEmpty(userCondition.getPerId())){
			whereSql.append(" and Per_ID like '"+userCondition.getPerId()+"'");
		}
		/*if(!StringUtils.isTrimEmpty(userCondition.getClassTypeId())){

		}*/
		HttpURLConnection connection = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append(String.format("%1$s=%2$s&", URLEncoder.encode("whereSql", "UTF-8"),  URLEncoder.encode(whereSql.toString(), "UTF-8")));
			System.out.println(builder.toString());
			/*for (String key : params.keySet()) {
				String val="xmlValues".equalsIgnoreCase(key)? URLEncoder.encode(params.get(key), "UTF-8"):params.get(key);
				builder.append(String.format("%1$s=%2$s&", key,  URLEncoder.encode(val, "UTF-8")));
			}*/
			connection = (HttpURLConnection) new URL(CommonConstants.YICARDTONG_PATH+"/yikatong/showAttendListWithCondition.json").openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
			out.write(builder.toString());
			out.flush();
			out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			builder.setLength(0);
			String line = "";
			while ((line = reader.readLine()) != null)
				builder.append(line);
			reader.close();
//			System.out.println("回应数据 :" + builder);
			return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			if (connection != null)
				connection.disconnect();
		}
	}

	/**
	 * 解析考勤数据
	 * @param result
	 * @return
	 */
	public List<Map<String,Object>> parseAttendanceData(String result){
		if(result==null||result.equals("")){
			return null;
		}
		Gson gson=new Gson();
		Map<String,Object> all=gson.fromJson(result,Map.class);
		String code=all.get("code").toString();
		if(code==null||!code.equals("0")){
			return null;
		}
		List<Map<String,Object>> data=(List<Map<String,Object>>)all.get("data");
		return data;
	}

	/**
	 * 获取一页的数据
	 * @param all
	 * @param pagination
	 * @return
	 */
	public Map<String,Object> getOnePageData(List<Map<String,Object>> all,Pagination pagination){
		if(all==null||all.size()==0){
			return null;
		}
		pagination.setTotalCount(all.size());
		pagination.setTotalPages(all.size()%pagination.getPageSize()==0?all.size()/pagination.getPageSize():all.size()/pagination.getPageSize()+1);
		int begin=(pagination.getCurrentPage()-1)*pagination.getPageSize();
		int end=pagination.getCurrentPage()*pagination.getPageSize();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("data",all.subList(begin,end>all.size()?all.size():end));
		map.put("pagination",pagination);
		return map;
	}

	/**
	 * 考勤列表
	 * @param request
	 * @param pagination
	 * @param userCondition
	 * @return
	 */
	@RequestMapping(ADMIN_PREFIX+"/workDayDataList")
	public String workDayDataList(HttpServletRequest request,@ModelAttribute("pagination")Pagination pagination,@ModelAttribute("userCondition")UserCondition userCondition){
		try {
			List<ClassType> classTypeList=classTypeBiz.find(null," status=1");
			request.setAttribute("classTypeList",classTypeList);

			List<UserWorkDayData> userWorkDayDataList=null;
			StringBuffer whereSql=new StringBuffer(" 1=1");

			Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
			if(userMap.get("userType").equals("2")) {
				String teacherId = userMap.get("linkId");
				List<Classes> classesList = classesBiz.find(null, " teacherId=" + teacherId);
				if (classesList != null && classesList.size() > 0) {
					whereSql.append(" and classId=" + classesList.get(0).getId());
				}
			}


			if(userCondition.getClassTypeId()!=null&&!userCondition.getClassTypeId().equals(0L)){
				whereSql.append(" and classTypeId="+userCondition.getClassTypeId());
			}
			if(userCondition.getClassId()!=null&&!userCondition.getClassId().equals(0L)){
				whereSql.append(" and classId="+userCondition.getClassId());
			}
			if(!StringUtils.isTrimEmpty(userCondition.getUserName())){
				whereSql.append(" and userName like '%"+userCondition.getUserName()+"%'");
			}
			if(userCondition.getMorningAttendanceStatus()!=null&&!userCondition.getMorningAttendanceStatus().equals(0)){
				whereSql.append(" and morningAttendanceStatus="+userCondition.getMorningAttendanceStatus());
			}
			if(userCondition.getAfternoonAttendanceStatus()!=null&&!userCondition.getAfternoonAttendanceStatus().equals(0)){
				whereSql.append(" and afternoonAttendanceStatus="+userCondition.getAfternoonAttendanceStatus());
			}
			if(!StringUtils.isTrimEmpty(userCondition.getWorkDate())){
				whereSql.append(" and workDate like '"+userCondition.getWorkDate()+"'");
			}
			whereSql.append(" order by createTime desc");
			pagination.setRequest(request);
			userWorkDayDataList = userWorkDayDataBiz.find(pagination,whereSql.toString());
			request.setAttribute("userWorkDayDataList",userWorkDayDataList);
			request.setAttribute("pagination",pagination);
		}catch (Exception e){
			e.printStackTrace();
		}
		return "/admin/attendance/workDayData_list";
	}

	@RequestMapping("/attendancetest")
	public void attendancetest(){
		try{
			attendanceBiz.autoAddWorkDayData();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 考勤统计
	 * @param request
	 * @param pagination
	 * @param userCondition
	 * @return
	 */
	@RequestMapping(ADMIN_PREFIX+"/workDayDataStatistics")
	public String workDayDataStatistics(HttpServletRequest request,@ModelAttribute("pagination")Pagination pagination,@ModelAttribute("userCondition")UserCondition userCondition){
		try{
			List<ClassType> classTypeList = classTypeBiz.find(null," status=1");
			request.setAttribute("classTypeList",classTypeList);

			StringBuffer whereSql=new StringBuffer(" status=1 and !isnull(perId)");
			if(userCondition.getClassTypeId()!=null&&!userCondition.getClassTypeId().equals(0L)){
				whereSql.append(" and classTypeId="+userCondition.getClassTypeId());
			}
			if(userCondition.getClassId()!=null&&!userCondition.getClassId().equals(0L)){
				whereSql.append(" and classId="+userCondition.getClassId());
			}
			if(!StringUtils.isTrimEmpty(userCondition.getUserName())){
				whereSql.append(" and name like '%"+userCondition.getUserName()+"%'");
			}
			pagination.setRequest(request);
			logger.info("whereSql====" + whereSql);
			List<User> userList = userBiz.find(pagination,whereSql+" order by unitId");
			logger.info("userList====" + userList);
			List<Map<String,Object>> statisticsList=new LinkedList<Map<String,Object>>();
			if(userList!=null&&userList.size()>0){
				int normal=0;
				int late=0;
				int leaveEarly=0;
				int absenteeism=0;
				StringBuffer userWorkDayDateCondition=new StringBuffer("");
				if(userCondition.getStartTime()!=null){
					userWorkDayDateCondition.append(" and createTime>='"+sdf.format(userCondition.getStartTime())+"'");
				}
				if(userCondition.getEndTime()!=null){
					userWorkDayDateCondition.append(" and createTime<='"+sdf.format(userCondition.getEndTime())+"'");
				}
				for(User user:userList){
					normal=0;
					late=0;
					leaveEarly=0;
					absenteeism=0;
					Map<String,Object> statistics=new HashMap<String,Object>();
					statistics.put("classTypeName",user.getClassTypeName());
					statistics.put("className",user.getClassName());
					statistics.put("userName",user.getName());
					List<UserWorkDayData> userWorkDayDataList=userWorkDayDataBiz.find(null," userId="+user.getId()+userWorkDayDateCondition.toString());
					if(userWorkDayDataList!=null&&userWorkDayDataList.size()>0){
						for(UserWorkDayData userWorkDayData:userWorkDayDataList){
							switch (userWorkDayData.getMorningAttendanceStatus()){
								case 1:
									normal++;
									break;
								case 2:
									late++;
									break;
								case 3:
									leaveEarly++;
									break;
								case 4:
									absenteeism++;
									break;
							}
							switch(userWorkDayData.getAfternoonAttendanceStatus()){
								case 1:
									normal++;
									break;
								case 2:
									late++;
									break;
								case 3:
									leaveEarly++;
									break;
								case 4:
									absenteeism++;
									break;
							}
						}
					}
					statistics.put("normal",normal);
					statistics.put("late",late);
					statistics.put("leaveEarly",leaveEarly);
					statistics.put("absenteeism",absenteeism);
					List<Holiday> holidayList=holidayBiz.find(null," userId="+user.getId());
					statistics.put("holiday",holidayList!=null&&holidayList.size()>0?holidayList.size():0);
					statisticsList.add(statistics);
				}
			}
			logger.info("statisticsList====" + statisticsList);
			request.setAttribute("statisticsList",statisticsList);
			request.setAttribute("pagination",pagination);
		}catch (Exception e){
			e.printStackTrace();
		}
		return "/admin/attendance/workDayData_statistics";
	}

	/**
	 * 导出考勤统计数据
	 * @param request
	 */
	@RequestMapping(ADMIN_PREFIX+"/exportWorkDayDataStatistics")
	public void exportWorkDayDataStatistics(HttpServletRequest request, HttpServletResponse response,@ModelAttribute("userCondition")UserCondition userCondition){
		try{
			String dir = request.getSession().getServletContext().getRealPath("/excelfile/workDayDataStatisticsList");
			String expName = "考勤统计_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String[] headName = {"班型","班次","姓名","正常次数","迟到次数","早退次数","旷工次数"};
			List<File> srcfile=attendanceBiz.getExcelWorkDayDataStatisticsList(userCondition,request,dir,headName,expName);
			FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 学员考勤
	 * @param request
	 * @param pagination
	 * @param userCondition
	 * @return
	 */
	@RequestMapping(ADMIN_PREFIX+"/studentWorkDayDataList")
	public String studentWorkDayDataList(HttpServletRequest request,@ModelAttribute("pagination")Pagination pagination,@ModelAttribute("userCondition")UserCondition userCondition){
		try {
			/*List<ClassType> classTypeList=classTypeBiz.find(null," status=1");
			request.setAttribute("classTypeList",classTypeList);*/

			List<UserWorkDayData> userWorkDayDataList=null;
			Map<String,String> userMap= SysUserUtils.getLoginSysUser(request);
			StringBuffer whereSql=new StringBuffer(" userId="+userMap.get("linkId"));
			/*if(userCondition.getClassTypeId()!=null&&!userCondition.getClassTypeId().equals(0L)){
				whereSql.append(" and classTypeId="+userCondition.getClassTypeId());
			}
			if(userCondition.getClassId()!=null&&!userCondition.getClassId().equals(0L)){
				whereSql.append(" and classId="+userCondition.getClassId());
			}
			if(!StringUtils.isTrimEmpty(userCondition.getUserName())){
				whereSql.append(" and userName like '%"+userCondition.getUserName()+"%'");
			}*/
			if(userCondition.getMorningAttendanceStatus()!=null&&!userCondition.getMorningAttendanceStatus().equals(0)){
				whereSql.append(" and morningAttendanceStatus="+userCondition.getMorningAttendanceStatus());
			}
			if(userCondition.getAfternoonAttendanceStatus()!=null&&!userCondition.getAfternoonAttendanceStatus().equals(0)){
				whereSql.append(" and afternoonAttendanceStatus="+userCondition.getAfternoonAttendanceStatus());
			}
			if(!StringUtils.isTrimEmpty(userCondition.getWorkDate())){
				whereSql.append(" and workDate like '"+userCondition.getWorkDate()+"'");
			}
			whereSql.append(" order by createTime desc");
			pagination.setRequest(request);
			userWorkDayDataList = userWorkDayDataBiz.find(pagination,whereSql.toString());
			request.setAttribute("userWorkDayDataList",userWorkDayDataList);
			request.setAttribute("pagination",pagination);
		}catch (Exception e){
			e.printStackTrace();
		}
		return "/admin/attendance/student_workDayData_list";
	}
}
