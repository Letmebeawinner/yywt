package com.jiaowu.biz.xinde;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.jiaowu.common.FileExportImportUtil;
import com.jiaowu.dao.xinde.XinDeDao;
import com.jiaowu.entity.xinde.XinDe;

import java.io.File;

@Service
public class XinDeBiz extends BaseBiz<XinDe,XinDeDao>{
	/**
	 * 验证心得
	 * @return
	 */
	public String validateXinDe(XinDe xinDe,Map<String,String> userMap){
		if(!userMap.get("userType").equals("3")){
			return "只有学员拥有创建心得的权限!";
		}
		if(xinDe.getType().equals("会议心得")){
			if(xinDe.getMeetingId()==null||xinDe.getMeetingId()<=0){
                return "会议不能为空";
            }
		}
		/*if(StringUtils.isTrimEmpty(xinDe.getContent())){
			return "内容不能为空";
		}*/
		if(StringUtils.isTrimEmpty(xinDe.getFileUrl())){
			return "请上传附件";
		}
		return null;
	}
	
	/**
	 * 增加心得搜索条件
	 * @param request
	 * @return
	 */
	public String addCondition(HttpServletRequest request){
		StringBuffer sb=new StringBuffer();
		sb.append(" status=1");
		String studentId=request.getParameter("studentId");
		if(!StringUtils.isTrimEmpty(studentId) && Long.parseLong(studentId)>0){
			sb.append(" and studentId="+studentId);
        }
		String meetingId=request.getParameter("meetingId");
		if(!StringUtils.isTrimEmpty(meetingId) && Long.parseLong(meetingId)>0){
			sb.append(" and meetingId="+meetingId);
        }
		String classId=request.getParameter("classId");
		if(!StringUtils.isTrimEmpty(classId) && Long.parseLong(classId)>0){
			sb.append(" and classId="+classId);
		}
		return sb.toString();
	}
	
	/**
	 * 获取心得的excel文件列表
	 * @param request
	 * @param dir
	 * @param headName
	 * @param expName
	 * @return
	 * @throws Exception
	 */
	public List<File> getExcelFileList(HttpServletRequest request,String dir,String[] headName,String expName) throws Exception{
		Pagination pagination = new Pagination();
        pagination.setPageSize(10000);
        String whereSql=addCondition(request);
		pagination.setRequest(request);
        find(pagination,whereSql);
        int num = pagination.getTotalPages();// 总页数
        List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
        for (int i = 1; i <= num; i++) {// 循环生成num个xls文件
        	pagination.setCurrentPage(i);
        	List<XinDe> xinDeList = find(pagination,whereSql);
        	List<List<String>> list=convert(xinDeList);
            File file = FileExportImportUtil.createExcel(headName, list, expName + "_" + i, dir);
            srcfile.add(file);
        }
        return srcfile;
	}
	
	/**
     * 将XinDe的集合转化为List<List<String>>类型.
     * @param xinDeList
     * @return
     */
    public List<List<String>> convert(List<XinDe> xinDeList){
    	List<List<String>> list=new ArrayList<List<String>>();
    	if(xinDeList!=null&&xinDeList.size()>0){
    		for(XinDe xinDe:xinDeList){
    			List<String> smallList=new ArrayList<String>();
    			smallList.add(xinDe.getId()+"");
    			smallList.add(xinDe.getStudentId()+"");
    			smallList.add(xinDe.getStudentName());
    			smallList.add(xinDe.getContent());
    			smallList.add(xinDe.getType());
    			if(xinDe.getMeetingId()==null){
    				smallList.add("");
    			}else{
    				smallList.add(xinDe.getMeetingId()+"");
    			}
    			smallList.add(xinDe.getMeetingName());
    			smallList.add(xinDe.getNote());
    			smallList.add(xinDe.getCreateTime()+"");
    			list.add(smallList);
    		}
    	}
    	return list;
    }
}
