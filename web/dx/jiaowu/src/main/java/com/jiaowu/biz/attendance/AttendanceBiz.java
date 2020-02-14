package com.jiaowu.biz.attendance;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import com.a_268.base.util.StringUtils;
import com.google.gson.Gson;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.biz.userWorkDayData.UserWorkDayDataBiz;
import com.jiaowu.common.CommonConstants;
import com.jiaowu.common.FileExportImportUtil;
import com.jiaowu.entity.user.User;
import com.jiaowu.entity.user.UserCondition;
import com.jiaowu.entity.userWorkDayData.UserWorkDayData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a_268.base.core.BaseBiz;
import com.jiaowu.biz.common.WorkSourceService;
import com.jiaowu.biz.course.CourseArrangeBiz;
import com.jiaowu.dao.attendance.AttendanceDao;
import com.jiaowu.entity.attendance.Attendance;
import com.jiaowu.entity.course.CourseArrange;

import javax.servlet.http.HttpServletRequest;

@Service
public class AttendanceBiz extends BaseBiz<Attendance, AttendanceDao>{
	/*@Autowired
	private WorkSourceService workSourceService;*/
	@Autowired
	private CourseArrangeBiz courseArrangeBiz;
	@Autowired
	private UserWorkDayDataBiz userWorkDayDataBiz;
	@Autowired
	private UserBiz userBiz;
	private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 每天根据排课信息和打卡记录算出每天的考勤记录
	 */
	public void dailySumAttendance(){
		List<CourseArrange> courseArrangeList=getYesterdayCourseArrange();
		if(courseArrangeList!=null&&courseArrangeList.size()>0){
//			List<Map<String,String>> 
		}
	}
	
	public Map<String,Date> getStartTimeAndEndTime(){
		Date now=new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(now);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date endTime=calendar.getTime();
		calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-1);
		Date startTime=calendar.getTime();
		Map<String,Date> map=new HashMap<String,Date>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		return map;
	}
	
	public List<CourseArrange> getYesterdayCourseArrange(){
		Map<String,Date> dateMap=getStartTimeAndEndTime();
		Date startTime=dateMap.get("startTime");
		Date endTime=dateMap.get("endTime");
		return courseArrangeBiz.find(null," status=1 and startTime>'"+startTime+"' and startTime<'"+endTime+"'");
	}

	/**
	 * 定时获取work_day_data数据，将数据添加到数据库中。
	 */
	public void autoAddWorkDayData(){
		List<Map<String,Object>> workDayDataList=parseWorkDayData(getTodayWorkDayData());
		if(workDayDataList!=null&&workDayDataList.size()>0){
			List<User> userList=null;
			for(Map<String,Object> workDayData:workDayDataList){
				userList=userBiz.find(null," status=1 and perId='"+workDayData.get("basePerID")+"'");
				if(userList!=null&&userList.size()>0){
					workDayData.put("userId",userList.get(0).getId());
					workDayData.put("classTypeId",userList.get(0).getClassTypeId());
					workDayData.put("classId",userList.get(0).getClassId());
					workDayData.put("userName",userList.get(0).getName());
					workDayData.put("classTypeName",userList.get(0).getClassTypeName());
					workDayData.put("className",userList.get(0).getClassName());
				}
				userWorkDayDataBiz.save(new UserWorkDayData(workDayData));
			}
		}
	}

	/**
	 * 解析work_day_data数据
	 * @param result
	 * @return
	 */
	public List<Map<String,Object>> parseWorkDayData(String result){
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
	 * 获取今天work_day_data数据
	 * @return
	 */
	public String getTodayWorkDayData(){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_YEAR,-9);
		String whereSql=" where Work_Date like '"+"2017-10-10"+"'";
		HttpURLConnection connection = null;
		try {
			StringBuilder builder = new StringBuilder();
			builder.append(String.format("%1$s=%2$s&", URLEncoder.encode("whereSql", "UTF-8"),  URLEncoder.encode(whereSql, "UTF-8")));
			System.out.println(builder.toString());
			/*for (String key : params.keySet()) {
				String val="xmlValues".equalsIgnoreCase(key)? URLEncoder.encode(params.get(key), "UTF-8"):params.get(key);
				builder.append(String.format("%1$s=%2$s&", key,  URLEncoder.encode(val, "UTF-8")));
			}*/
			connection = (HttpURLConnection) new URL(CommonConstants.YICARDTONG_PATH+"/yikatong/workDayDataList.json").openConnection();
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

	public List<File> getExcelWorkDayDataStatisticsList(UserCondition userCondition, HttpServletRequest request, String dir, String[] headName, String expName) throws Exception{
		/*Pagination pagination = new Pagination();
		pagination.setPageSize(10000);
		String whereSql=addCondition(request);
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
		return srcfile;*/
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
		List<User> userList = userBiz.find(null,whereSql+" order by unitId");
		List<List<String>> statisticsList=new LinkedList<List<String>>();
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
				List<String> statistics=new LinkedList<String>();
				/*statistics.put("classTypeName",user.getClassTypeName());
				statistics.put("className",user.getClassName());
				statistics.put("userName",user.getName());*/
				statistics.add(user.getClassTypeName());
				statistics.add(user.getClassName());
				statistics.add(user.getName());
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
				/*statistics.put("normal",normal);
				statistics.put("late",late);
				statistics.put("leaveEarly",leaveEarly);
				statistics.put("absenteeism",absenteeism);*/
				statistics.add(normal+"");
				statistics.add(late+"");
				statistics.add(leaveEarly+"");
				statistics.add(absenteeism+"");
				statisticsList.add(statistics);
			}
		}
		List<File> srcfile = new ArrayList<File>();
		File file = FileExportImportUtil.createExcel(headName, statisticsList, expName, dir);
		srcfile.add(file);
		return srcfile;
	}
}
