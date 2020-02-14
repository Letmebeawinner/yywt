package com.jiaowu.controller.material;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.common.CommonBiz;
import com.jiaowu.biz.material.MaterialAnalysisBiz;
import com.jiaowu.common.FileExportImportUtil;
import com.jiaowu.controller.classes.ClassesController;
import com.jiaowu.entity.exam.ExamComment;
import com.jiaowu.entity.material.MaterialAnalysis;
import com.jiaowu.entity.xinde.XinDe;

/**
 * 党性材料分析Controller
 * @author 李帅雷
 *
 */
@Controller
public class MaterialAnalysisController extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(MaterialAnalysisController.class);
	
	@Autowired
	private MaterialAnalysisBiz materialAnalysisBiz;
	@Autowired
	private CommonBiz commonBiz;
	
	@InitBinder({"materialAnalysis"})
	public void initMaterialAnalysis(WebDataBinder binder){
		binder.setFieldDefaultPrefix("materialAnalysis.");
	}
	
	private static final String ADMIN_PREFIX="/admin/jiaowu/materialAnalysis";
    private static final String WITHOUT_ADMIN_PREFIX="/jiaowu/materialAnalysis";
	
	/**
     * @Description 跳转到新建 党性材料分析的页面
     * @author 李帅雷
     * @param request
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/toCreateMaterialAnalysis")
	public String toCreateMaterialAnalysis(HttpServletRequest request){
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/materialAnalysis/create_materialAnalysis";
	}
	
	/**
     * @Description 新建 党性材料分析
     * @param request
     * @param materialAnalysis
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/createMaterialAnalysis")
	@ResponseBody
	public Map<String,Object> createMaterialAnalysis(HttpServletRequest request,@ModelAttribute("materialAnalysis")MaterialAnalysis materialAnalysis){
		Map<String,Object> json=null;
		try{
			json=validateMaterialAnalysis(request,materialAnalysis);
			if(json!=null){
				return json;
			}
			Map<String,String> userMap=SysUserUtils.getLoginSysUser(request);
			materialAnalysis.setStudentId(Long.parseLong(userMap.get("linkId")));
			materialAnalysis.setStudentName(commonBiz.getCurrentUserName(request));
			materialAnalysisBiz.save(materialAnalysis);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	/**
     * @Description 党性材料分析列表
     * @author 李帅雷
     * @param request
     * @param pagination
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/materialAnalysisList")
	public String materialAnalysisList(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination){
		try{
			String whereSql=" status=1";
			MaterialAnalysis materialAnalysis=new MaterialAnalysis();
			whereSql+=materialAnalysisBiz.addCondition(request, materialAnalysis);
			pagination.setRequest(request);
	        List<MaterialAnalysis> materialAnalysisList = materialAnalysisBiz.find(pagination,whereSql);
	        request.setAttribute("materialAnalysisList",materialAnalysisList);
	        request.setAttribute("pagination",pagination);
	        request.setAttribute("materialAnalysis",materialAnalysis);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/materialAnalysis/materialAnalysis_list";
	}
	
	/**
     * @Description 一条党性材料分析详情
     * @author 李帅雷
     * @param request
     * @param id
     * @return java.lang.String
     * 
     */
	@RequestMapping(ADMIN_PREFIX+"/queryMaterialAnalysis")
	public String queryMaterialAnalysis(HttpServletRequest request,@RequestParam("id")Long id){
		try{
			MaterialAnalysis materialAnalysis=materialAnalysisBiz.findById(id);
			request.setAttribute("materialAnalysis", materialAnalysis);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/materialAnalysis/query_materialAnalysis";
	}
	
	/**
	 * @Description 党性材料导出
     * @author 李帅雷
	 * @param request
	 * @param response
	 */
	@RequestMapping(ADMIN_PREFIX+"/exportExcel")
    public void userListExport(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 指定文件生成路径
            String dir = request.getSession().getServletContext().getRealPath("/excelfile/materialAnalysis");
            // 文件名
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(currentTime);
            String expName = "党性材料分析列表_" + dateString;
            // 表头信息
            String[] headName = {"ID", "学员ID", "学员名称", "内容", "类型", "会议ID","会议名称","备注","创建时间"};

            // 拆分为一万条数据每Excel，防止内存使用太大
            Pagination pagination = new Pagination();
            pagination.setPageSize(10000);
            String whereSql=" status=1";
			MaterialAnalysis materialAnalysis=new MaterialAnalysis();
			whereSql+=materialAnalysisBiz.addCondition(request,materialAnalysis);
			pagination.setRequest(request);
			materialAnalysisBiz.find(pagination,whereSql);
            int num = pagination.getTotalPages();// 总页数
            List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
            for (int i = 1; i <= num; i++) {// 循环生成num个xls文件
            	pagination.setCurrentPage(i);
            	List<MaterialAnalysis> materialAnalysisList = materialAnalysisBiz.find(pagination,whereSql);
            	List<List<String>> list=convert(materialAnalysisList);
                File file = FileExportImportUtil.createExcel(headName, list, expName + "_" + i, dir);
                srcfile.add(file);
            }
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	
    
    /**
     * @Description 删除党性材料分析
     * @param request
     * @param id
     * @return java.util.Map
     */
	@RequestMapping(ADMIN_PREFIX+"/deleteMaterialAnalysis")
	@ResponseBody
	public Map<String,Object> deleteMaterialAnalysis(HttpServletRequest request,@RequestParam("id")Long id){
		Map<String,Object> json=null;
		try{
			MaterialAnalysis materialAnalysis=new MaterialAnalysis();
			materialAnalysis.setId(id);
			materialAnalysis.setStatus(0);
			materialAnalysisBiz.update(materialAnalysis);
			json=this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
		}catch(Exception e){
			e.printStackTrace();
			json=this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	/**
	 * 验证党性材料分析
	 * @param request
	 * @param materialAnalysis
	 * @return
	 */
	public Map<String,Object> validateMaterialAnalysis(HttpServletRequest request,MaterialAnalysis materialAnalysis){
		if(StringUtils.isTrimEmpty(materialAnalysis.getContent())){
			return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "内容不能为空", null);
		}
		if(materialAnalysis.getType().equals("meeting")){
			if(materialAnalysis.getMeetingId()==null||materialAnalysis.getMeetingId()<=0){
				return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "会议不能为空", null);
            }
		}
		return null;
	}
	
	/**
	 * 将MaterialAnalysis集合转为List<List<String>>
	 * @param materialAnalysisList
	 * @return
	 */
    public List<List<String>> convert(List<MaterialAnalysis> materialAnalysisList){
    	List<List<String>> list=new ArrayList<List<String>>();
    	if(materialAnalysisList!=null&&materialAnalysisList.size()>0){
    		for(MaterialAnalysis materialAnalysis:materialAnalysisList){
    			List<String> smallList=new ArrayList<String>();
    			smallList.add(materialAnalysis.getId()+"");
    			smallList.add(materialAnalysis.getStudentId()+"");
    			smallList.add(materialAnalysis.getStudentName());
    			smallList.add(materialAnalysis.getContent());
    			smallList.add(materialAnalysis.getType());
    			smallList.add(materialAnalysis.getMeetingId()+"");
    			smallList.add(materialAnalysis.getMeetingName());
    			smallList.add(materialAnalysis.getNote());
    			smallList.add(materialAnalysis.getCreateTime()+"");
    			list.add(smallList);
    		}
    	}
    	return list;
    }
}
