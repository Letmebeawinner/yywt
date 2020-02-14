package com.jiaowu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.jiaowu.controller.xinde.XinDeController;
import com.jiaowu.entity.xinde.XinDe;

@Controller
@RequestMapping("/admin/jiaowu")
public class CommonController extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	private static final String userListForSelect="/admin/user/user_list_forSelect";
	
	/*@RequestMapping("/user/userListForSelect")
	public String userListForSelect(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination){
		try{
			String whereSql=" 1=1";
			pagination.setRequest(request);
			pagination.setCurrentPage(1);
			pagination.setTotalPages(1);
			//应从base的学员表中取,但该表还没有建,之后再改.
	        List<Map<String,Object>> studentList = new ArrayList<Map<String,Object>>();
	        Map<String,Object> map=new HashMap<String,Object>();
	        map.put("id", 2);
	        map.put("name", "小明");
	        studentList.add(map);
	        map=new HashMap<String,Object>();
	        map.put("id", 1);
	        map.put("name", "小红");
	        studentList.add(map);
	        request.setAttribute("studentList",studentList);
	        request.setAttribute("pagination",pagination);
		}catch(Exception e){
			e.printStackTrace();
		}
		return userListForSelect;
	}*/
}
