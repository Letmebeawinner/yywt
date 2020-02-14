package com.jiaowu.controller.xinde;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.common.CommonBiz;
import com.jiaowu.biz.meeting.MeetingBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.biz.xinde.XinDeBiz;
import com.jiaowu.common.FileExportImportUtil;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.user.User;
import com.jiaowu.entity.xinde.XinDe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 心得Controller
 * @author 李帅雷
 *
 */
@Controller
public class XinDeController extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(XinDeController.class);
	
	@InitBinder({"xinDe"})
	public void initXinDe(WebDataBinder binder){
		binder.setFieldDefaultPrefix("xinDe.");
	}
	
	@Autowired
	private XinDeBiz xinDeBiz;
	@Autowired
	private MeetingBiz meetingBiz;
	@Autowired
	private UserBiz userBiz;
	@Autowired
	private CommonBiz commonBiz;
	@Autowired
	private ClassesBiz classesBiz;
	
	private static final String ADMIN_PREFIX="/admin/jiaowu/xinDe";
    private static final String WITHOUT_ADMIN_PREFIX="/jiaowu/xinDe";
//	private static final String createXinDe="/admin/xinde/create_xinde";
//	private static final String queryXinDe="/admin/xinde/query_xinde";
	
	/**
     * @Description 跳转到新建心得的页面
     * @author 李帅雷
     * @param request
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/toCreateXinDe")
	public String createXinDe(HttpServletRequest request){
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/xinde/create_xinde";
	}
	
	/**
     * @Description 创建心得
     * @param request
     * @param xinDe
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/createXinDe")
	@ResponseBody
	public Map<String,Object> createXinDe(HttpServletRequest request,@ModelAttribute("xinDe")XinDe xinDe){
		Map<String,Object> json=null;
		try{
			Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
			String errorInfo=xinDeBiz.validateXinDe(xinDe, userMap);
			if(!StringUtils.isTrimEmpty(errorInfo)){
				json=this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo, null);
				return json;
			}
			User user=userBiz.findById(Long.parseLong(userMap.get("linkId")));
			xinDe.setClassId(user.getClassId());
			xinDe.setStudentId(Long.parseLong(userMap.get("linkId")));
			xinDe.setStudentName(commonBiz.getCurrentUserName(request));
			xinDeBiz.save(xinDe);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	/**
     * @Description 心得列表
     * @author 李帅雷
     * @param request
     * @param pagination
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/xinDeList")
	public String xinDeList(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination){
		try{
			String whereSql=" status=1";
			Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
			if(userMap.get("userType").equals("1")){

			}else if(userMap.get("userType").equals("2")){
				List<Classes> classesList=classesBiz.find(null," status=1 and teacherId="+userMap.get("linkId"));
				if(classesList!=null&&classesList.size()>0){
					whereSql+=" and classId="+classesList.get(0).getId();
				}else{
					return "/admin/xinde/xinde_list";
				}
			}else if(userMap.get("userType").equals("3")){
				whereSql+=" and studentId="+userMap.get("linkId");
			}

			List<XinDe> xinDeList=null;

			XinDe xinDe=new XinDe();
			String studentId=request.getParameter("studentId");
			if(!StringUtils.isTrimEmpty(studentId) && Long.parseLong(studentId)>0){
                whereSql+=" and studentId="+studentId;
                xinDe.setStudentId(Long.parseLong(studentId));
            }
			String meetingId=request.getParameter("meetingId");
			if(!StringUtils.isTrimEmpty(meetingId) && Long.parseLong(meetingId)>0){
                whereSql+=" and meetingId="+meetingId;
                xinDe.setMeetingId(Long.parseLong(meetingId));
            }
			String classId=request.getParameter("classId");
			if(!StringUtils.isTrimEmpty(classId) && Long.parseLong(classId)>0){
				whereSql+=" and classId="+classId;
				xinDe.setClassId(Long.parseLong(classId));
			}
			pagination.setRequest(request);
	        xinDeList = xinDeBiz.find(pagination,whereSql);
	        request.setAttribute("xinDeList",xinDeList);
	        request.setAttribute("pagination",pagination);
	        request.setAttribute("xinDe",xinDe);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/xinde/xinde_list";
	}
	
	/**
	 * 将userList中的id组成字符串
	 * @param userList
	 * @return
	 */
	public String getUserIdsByUserList(List<User> userList){
		StringBuffer sb=new StringBuffer();
		sb.append("(");
		for(User user:userList){
			sb.append(user.getId()+",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(")");
		return sb.toString();
	}
	
	/**
     * @Description 某心得详情
     * @author 李帅雷
     * @param request
     * @param id
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/queryXinDe")
	public String queryXinDe(HttpServletRequest request,@RequestParam("id")Long id){
		try{
			XinDe xinDe=xinDeBiz.findById(id);
			request.setAttribute("xinDe", xinDe);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/xinde/query_xinde";
	}
	
	/**
     * @Description 导出心得列表
     * @author 李帅雷
     * @param request
     * @param response
     * 
     */
    @RequestMapping(ADMIN_PREFIX+"/exportExcel")
    public void userListExport(HttpServletRequest request, HttpServletResponse response) {
        try {
            String dir = request.getSession().getServletContext().getRealPath("/excelfile/xinde");
            String expName = "心得列表_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String[] headName = {"ID", "学员ID", "学员名称", "内容", "类型", "会议ID","会议名称","备注","创建时间"};
            List<File> srcfile=xinDeBiz.getExcelFileList(request,dir,headName,expName);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @Description 删除心得
     * @param request
     * @param id
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/delXinDe")
	@ResponseBody
	public Map<String,Object> delXinDe(HttpServletRequest request,@RequestParam("id")Long id){
		Map<String,Object> json=null;
		try{
			XinDe xinde=new XinDe();
			xinde.setId(id);
			xinde.setStatus(0);
			xinDeBiz.update(xinde);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
}
