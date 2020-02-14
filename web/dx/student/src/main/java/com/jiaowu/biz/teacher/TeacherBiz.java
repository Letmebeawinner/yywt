package com.jiaowu.biz.teacher;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.StringUtils;
import com.jiaowu.biz.common.BaseHessianService;
import com.jiaowu.dao.teacher.TeacherDao;
import com.jiaowu.entity.teacher.Teacher;
import com.jiaowu.entity.userInfo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TeacherBiz extends BaseBiz<Teacher,TeacherDao>{
	@Autowired
	private BaseHessianService baseHessianService;
	
	
	
	public String validatePassword(UserInfo userInfo){
		//验证密码
        if (StringUtils.isTrimEmpty(userInfo.getPassword())) {
            return "密码不能为空";
        }
        if(userInfo.getPassword().length()<6||userInfo.getPassword().length()>15){
        	return "密码位数为6-15位";
        }
        if (!userInfo.getPassword().equals(userInfo.getConfirmPassword())) {
            return "两次密码不匹配";
        }
        return null;
	}
	
	public String validateTeacher(Teacher teacher){
		if(teacher.getSex()==null){
			return "性别不能为空";
		}

		/*if(StringUtils.isTrimEmpty(teacher.getName())){
			return "姓名不能为空";
		}
		if(teacher.getBirthDay()==null){
			return "生日不能为空";
		}
		if(teacher.getBirthDay().getTime()>new Date().getTime()){
			return "生日不能晚于当前时间";
		}
		Pattern pattern = Pattern.compile("[0-9]{14,17}[0-9X]{1}");
		if(StringUtils.isTrimEmpty(teacher.getIdentityCard())){
			return "身份证号不能为空";
		}
		Matcher isNum = pattern.matcher(teacher.getIdentityCard());
		if(!isNum.matches()){
			return "身份证号格式有误";
		}
		*//*if(teacher.getAge()==null||teacher.getAge()<=0){
			return "年龄不能为空";
		}*//*
		if(StringUtils.isTrimEmpty(teacher.getNationality())){
			return "民族不能为空";
		}
		if(StringUtils.isTrimEmpty(teacher.getEducation())){
			return "学历不能为空";
		}
		if(StringUtils.isTrimEmpty(teacher.getProfession())){
			return "专业不能为空";
		}
		if(StringUtils.isTrimEmpty(teacher.getPosition())){
			return "职务不能为空";
		}
		*//*if(teacher.getBaseMoney()==null){
			return "基本工资不能为空";
		}*//*
		if(StringUtils.isTrimEmpty(teacher.getResumeInfo())){
			return "履历信息不能为空";
		}*/
		return null;
	}
	
	public String validateEmail(String email){
		if (StringUtils.isTrimEmpty(email)) 
			return "邮箱不能为空";
        if (!StringUtils.isEmail(email)) 
        	return "邮箱格式不正确";
        String suffix=email.substring(email.indexOf(".")+1, email.length());
        if(!suffix.equals("com")&&!suffix.equals("cn")&&!suffix.equals("net")){
        	return "邮箱格式不正确";
        }
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
	 * 根据生日计算出年龄
	 * @param teacher
	 * @return
	 */
	public Long calculateAge(Teacher teacher){
		return teacher.getBirthDay()!=null?(long)(new Date().getYear()-teacher.getBirthDay().getYear()):null;
	}
}
