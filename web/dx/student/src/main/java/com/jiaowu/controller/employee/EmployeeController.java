package com.jiaowu.controller.employee;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.jiaowu.biz.common.BaseHessianService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jiaowu.biz.common.HrHessianService;
import com.jiaowu.common.RegExpressionUtil;
import com.jiaowu.common.security.PurseSecurityUtils;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.teacher.Teacher;
import com.jiaowu.entity.user.User;
import com.jiaowu.entity.userInfo.UserInfo;

/**
 * 教职工Controller
 * 
 * @author 李帅雷
 *
 */
@Controller
@RequestMapping("/admin/jiaowu/employee")
public class EmployeeController extends BaseController {
	private static Logger logger = LoggerFactory
			.getLogger(EmployeeController.class);
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
			.create();
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	HrHessianService hrHessianService;
	@Autowired
	BaseHessianService baseHessianService;

	/**
	 * @Description 跳转到批量导入教职工的页面
	 * @return
	 */
	@RequestMapping("/toBatchImportEmployee")
	public String toBatchImportEmployee() {
		return "/admin/employee/batch_import_employee";
	}

	/**
	 * @Description 批量导入教职工
	 * @author 李帅雷
	 * @param request
	 * @param myFile
	 * @return java.lang.String
	 * 
	 */
	@RequestMapping("/batchImportEmployee")
	public String batchImportEmployee(HttpServletRequest request,
			@RequestParam("myFile") MultipartFile myFile) {
		try {
			Map<String,Object> map=getEmployeeInfoFromExcel(myFile);
			List<Map<String, Object>> list=(List<Map<String, Object>>)map.get("list");
			String errorInfo=map.get("errorInfo").toString();
			errorInfo+=saveEmployee(list);
			if (StringUtils.isTrimEmpty(errorInfo)) {
				request.setAttribute("errorInfo", "导入成功");
			} else {
				request.setAttribute("errorInfo", errorInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/employee/batch_import_employee";
	}

	/**
	 * 从excel文件中解析获得教职工信息
	 * @param myFile
	 * @return
	 */
	public Map<String,Object> getEmployeeInfoFromExcel(
			MultipartFile myFile) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			StringBuffer sb=new StringBuffer();
		try {
			HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
			HSSFSheet sheet = wookbook.getSheetAt(0);
			int rows = sheet.getLastRowNum();
			for (int i = 1; i < rows + 1; i++) {
				HSSFRow row = sheet.getRow(i);
				if (row != null) {
					Map<String, Object> map = new HashMap<String, Object>();
					String name = getCellValue(row.getCell((short) 0));
					String birthDay = getCellValue(row.getCell((short) 1));
					String identityCard = getCellValue(row.getCell((short) 2));
					String mobile = getCellValue(row.getCell((short) 3));
					String email = getCellValue(row.getCell((short) 4));
					String password = getCellValue(row.getCell((short) 5));
					String age = getCellValue(row.getCell((short) 6));
					String sex = getCellValue(row.getCell((short) 7));
//					String ifCripple = getCellValue(row.getCell((short) 8));
					String nationality = getCellValue(row.getCell((short) 8));
					String education = getCellValue(row.getCell((short) 9));
					String profession = getCellValue(row.getCell((short) 10));
					String position = getCellValue(row.getCell((short) 11));
//					String baseMoney = getCellValue(row.getCell((short) 13));
					String resumeInfo = getCellValue(row.getCell((short) 12));
					map.put("name", name);
					map.put("birthDay", birthDay);
					map.put("identityCard", identityCard);
					map.put("mobile", mobile);
					map.put("email", email);
					map.put("password", password);
					map.put("age", age);
					map.put("sex", sex);
//					map.put("ifCripple", ifCripple);
					map.put("nationality", nationality);
					map.put("education", education);
					map.put("profession", profession);
					map.put("position", position);
//					map.put("baseMoney", baseMoney);
					map.put("resumeInfo", resumeInfo);
					map.put("employeeType", 1);
					String errorInfo=validateEmployee(map);
					if(!StringUtils.isTrimEmpty(errorInfo)){
						sb.append("第"+i+"行"+errorInfo+";");
						continue;
					}
					map.put("birthDay",map.get("birthDay")+" 00:00:00");
					list.add(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list",list);
		map.put("errorInfo",sb.toString());
		return map;
	}

	/**
	 * 验证教师信息
	 * @param map
	 * @return
	 */
	public String validateEmployee(Map<String,Object> map){
		if(StringUtils.isTrimEmpty(map.get("name").toString())){
			return "姓名不能为空";
		}
		try{
			sdf.parse(map.get("birthDay").toString());
		}catch (Exception e){
			return "生日格式不对";
		}
		String identityCard=map.get("identityCard").toString();
		if(StringUtils.isTrimEmpty(identityCard)||(identityCard.length()!=15&&identityCard.length()!=18)){
			return "身份证格式不对";
		}
		String errorInfo=validateEmail(map.get("email").toString());
		if(!StringUtils.isTrimEmpty(errorInfo)){
			return errorInfo;
		}
		errorInfo=validateMobile(map.get("mobile").toString());
		if(!StringUtils.isTrimEmpty(errorInfo)){
			return errorInfo;
		}
		if(StringUtils.isTrimEmpty(map.get("password").toString())){
			return "密码不能为空";
		}
		if(StringUtils.isTrimEmpty(map.get("age").toString())||!com.jiaowu.common.StringUtils.isNumeric(map.get("age").toString())){
			return "年龄格式不对";
		}
		String sex=map.get("sex").toString();
		if(StringUtils.isTrimEmpty(sex)||(!sex.equals("1")&&!sex.equals("0"))){
			return "性别格式不对";
		}
		if(StringUtils.isTrimEmpty(map.get("nationality").toString())){
			return "民族不能为空";
		}if(StringUtils.isTrimEmpty(map.get("education").toString())){
			return "学历不能为空";
		}if(StringUtils.isTrimEmpty(map.get("profession").toString())){
			return "专业不能为空";
		}
		if(StringUtils.isTrimEmpty(map.get("position").toString())){
			return "职务不能为空";
		}
		if(StringUtils.isTrimEmpty(map.get("resumeInfo").toString())){
			return "履历信息不能为空";
		}
		return null;
	}

	public String validateEmail(String email){
		if (StringUtils.isTrimEmpty(email))
			return "邮箱不能为空";
		if (!StringUtils.isEmail(email))
			return "邮箱格式不正确";
		if (baseHessianService.isEmailOrMobileExist(email,1))
			return "邮箱已注册";
		return null;
	}

	public String validateMobile(String mobile){
		if (StringUtils.isTrimEmpty(mobile))
			return "手机号不能为空";
		if (!StringUtils.isMobile(mobile))
			return "手机号格式错误";
		if (baseHessianService.isEmailOrMobileExist(mobile,2))
			return "手机号已注册";
		return null;
	}
	
	/**
	 * 增加教职工记录
	 * @param employeeList
	 * @return
	 */
	public String saveEmployee(List<Map<String, Object>> employeeList){
		StringBuffer msg = new StringBuffer();
		if (employeeList != null && employeeList.size() > 0) {
			for (int i = 0; i < employeeList.size(); i++) {
				Map<String, Object> map = employeeList.get(i);
				Gson gson = new Gson();
				String errorInfo = hrHessianService.saveEmployee(gson
						.toJson(map));
				System.out.print(errorInfo);

				if (!StringUtils.isTrimEmpty(errorInfo)&&!com.jiaowu.common.StringUtils.isNumeric(errorInfo)&&!errorInfo.contains("成功")) {
					msg.append("第" + i + "行" + errorInfo + ";");
				}
			}
		}
		return msg.toString();
	}

	/**
	 * @Description 获得Hsscell内容
	 * 
	 * @param cell
	 * @return String
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
}
