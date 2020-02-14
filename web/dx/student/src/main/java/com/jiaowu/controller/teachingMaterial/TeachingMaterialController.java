package com.jiaowu.controller.teachingMaterial;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jiaowu.biz.classes.ClassesBiz;
import com.jiaowu.biz.materialType.MaterialTypeBiz;
import com.jiaowu.biz.user.UserBiz;
import com.jiaowu.entity.materialType.MaterialType;
import com.jiaowu.entity.materialType.MaterialTypeDto;
import com.jiaowu.entity.user.User;
import lombok.experimental.Accessors;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;
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
import com.jiaowu.biz.teachingMaterial.TeachingMaterialBiz;
import com.jiaowu.controller.classes.ClassesController;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.teachingMaterial.TeachingMaterial;

@Controller
public class TeachingMaterialController extends BaseController {

	private static Logger logger = LoggerFactory
			.getLogger(TeachingMaterialController.class);

	@Autowired
	private TeachingMaterialBiz teachingMaterialBiz;
	@Autowired
	private MaterialTypeBiz materialTypeBiz;
	@Autowired
	private UserBiz userBiz;
	@Autowired
	private ClassesBiz classesBiz;

	private static final String ADMIN_PREFIX="/admin/jiaowu/teachingMaterial";
    private static final String WITHOUT_ADMIN_PREFIX="/jiaowu/teachingMaterial";

	@InitBinder({ "teachingMaterial" })
	public void initTeachingMaterial(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("teachingMaterial.");
	}
	@InitBinder({"materialType"})
	public void initMaterialType(WebDataBinder binder){
		binder.setFieldDefaultPrefix("materialType.");
	}

	/**
	 * 跳转到新建教学材料的页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(ADMIN_PREFIX+"/toCreateTeachingMaterial")
	public String toCreateTeachingMaterial(HttpServletRequest request) {
		try {
			request.setAttribute("materialTypeList",materialTypeBiz.find(null," status=1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/teachingMaterial/create_teachingMaterial";
	}

	/**
	 * 新建教学材料
	 * 
	 * @param request
	 * @param teachingMaterial
	 * @return
	 */
	@RequestMapping(ADMIN_PREFIX+"/createTeachingMaterial")
	@ResponseBody
	public Map<String, Object> createTeachingMaterial(
			HttpServletRequest request,
			@ModelAttribute("teachingMaterial") TeachingMaterial teachingMaterial) {
		Map<String, Object> json = null;
		try {
			String errorInfo=validateTeachingMaterial(teachingMaterial);
			if(!StringUtils.isTrimEmpty(errorInfo)){
				return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo,
						null);
			}
			Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
			/*teachingMaterial.setTeacherId(Long.parseLong(userMap.get("id")));
			teachingMaterial.setTeacherName(userMap.get("userName"));*/
			teachingMaterial.setCreateUserId(Long.parseLong(userMap.get("linkId")));
			teachingMaterial.setCreateUserName(userMap.get("userName"));
			teachingMaterial.setType(Integer.parseInt(userMap.get("userType")));
			if(userMap.get("userType").equals("3")){
				User user=userBiz.findById(Long.parseLong(userMap.get("linkId")));
				teachingMaterial.setClassId(user.getClassId());
			}
			MaterialType materialType=materialTypeBiz.findById(teachingMaterial.getMaterialTypeId());
			teachingMaterial.setMaterialTypeName(materialType.getName());
			teachingMaterialBiz.save(teachingMaterial);
			json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
					null);
		} catch (Exception e) {
			e.printStackTrace();
			json = this.resultJson(ErrorCode.ERROR_SYSTEM,
					ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}

	public String validateTeachingMaterial(TeachingMaterial teachingMaterial){
		if(StringUtils.isTrimEmpty(teachingMaterial.getTitle())){
			return "标题不能为空";
		}
		if(teachingMaterial.getMaterialTypeId()==null||teachingMaterial.getMaterialTypeId().equals(0L)){
			return "请选择资料类别";
		}
		if(StringUtils.isTrimEmpty(teachingMaterial.getPath())){
			return "请上传文件";
		}
		return null;
	}

	@RequestMapping(ADMIN_PREFIX+"/teachingMaterialList")
	public String teachingMaterialList(HttpServletRequest request,
			@ModelAttribute("pagination") Pagination pagination) {
		try {
			Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
			boolean isAdministrator=userMap.get("userType").equals("1")?true:false;
			request.setAttribute("isAdministrator",isAdministrator);

			String whereSql = " status=1";
			TeachingMaterial teachingMaterial=new TeachingMaterial();
			/*String teacherName=request.getParameter("teacherName");
			if(!StringUtils.isTrimEmpty(teacherName)){
                whereSql+=" and teacherName like '%"+teacherName+"%'";
                teachingMaterial.setTeacherName(teacherName);
            }*/
			String title=request.getParameter("title");
			if(!StringUtils.isTrimEmpty(title)){
                whereSql+=" and title like '%"+title+"%'";
                teachingMaterial.setTitle(title);
            }
			String materialTypeId=request.getParameter("materialTypeId");
			if(!StringUtils.isTrimEmpty(materialTypeId)&&!materialTypeId.equals("0")){
				whereSql+=" and materialTypeId="+materialTypeId;
				teachingMaterial.setMaterialTypeId(Long.parseLong(materialTypeId));
			}
			if(isAdministrator) {
				String classId = request.getParameter("classId");
				if (!StringUtils.isTrimEmpty(classId) && !classId.equals("0")) {
					whereSql += " and classId=" + classId;
					teachingMaterial.setClassId(Long.parseLong(classId));
				}
			}else{
				List<Classes> classesList=classesBiz.find(null," status=1 and teacherId="+userMap.get("linkId"));
				teachingMaterial.setClassId(classesList!=null&&classesList.size()>0?classesList.get(0).getId():-1L);
			}
			pagination.setRequest(request);
			List<TeachingMaterial> teachingMaterialList = teachingMaterialBiz
					.find(pagination, whereSql);
			request.setAttribute("teachingMaterialList", teachingMaterialList);
			request.setAttribute("pagination", pagination);
			request.setAttribute("teachingMaterial", teachingMaterial);
			request.setAttribute("materialTypeList",materialTypeBiz.find(null," status=1"));
			request.setAttribute("className",request.getParameter("className"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/teachingMaterial/teachingMaterial_list";
	}

	@RequestMapping(ADMIN_PREFIX+"/delTeachingMaterial")
	@ResponseBody
	public Map<String, Object> delTeachingMaterial(@RequestParam("id") Long id) {
		Map<String, Object> json = null;
		try {
			TeachingMaterial teachingMaterial = new TeachingMaterial();
			teachingMaterial.setId(id);
			teachingMaterial.setStatus(0);
			teachingMaterialBiz.update(teachingMaterial);
			json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
					null);
		} catch (Exception e) {
			e.printStackTrace();
			json = this.resultJson(ErrorCode.ERROR_SYSTEM,
					ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	
	@RequestMapping(ADMIN_PREFIX+"/passTeachingMaterial")
	@ResponseBody
	public Map<String,Object> passTeachingMaterial(@RequestParam("id") Long id){
		Map<String, Object> json = null;
		try{
			TeachingMaterial teachingMaterial = new TeachingMaterial();
			teachingMaterial.setId(id);
			teachingMaterial.setHasCheck(1);
			teachingMaterialBiz.update(teachingMaterial);
			json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
					null);
		}catch (Exception e) {
			e.printStackTrace();
			json = this.resultJson(ErrorCode.ERROR_SYSTEM,
					ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}
	
	/**
     * @Description:下载文件
     * @author: ccl
     * @Param: []
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-01-04
     */
    @RequestMapping(ADMIN_PREFIX+"/downloadFile")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam("fileUrl") String fileUrl) {
        ServletOutputStream outStream = null;
        InputStream inputStream = null;// 创建inputStream流
        try {
            String url =fileUrl;
            // 获得httpclient 建立http连接 获得图片流
            HttpClient httpclient = new HttpClient();
            GetMethod method = new GetMethod(url);
            method.getParams().setParameter("http.protocol.content-charset", "UTF-8");// 设置参数编码

            if (url!=null && url.trim().length()>0){
                method.setQueryString(URIUtil.encodeQuery(url));
                httpclient.executeMethod(method);
                if(method.getStatusCode() == 200) {// 状态200正常
                    inputStream = method.getResponseBodyAsStream();
                } else {
                    return null;
                }
            }
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "application/octet-stream");
            int _index = url.lastIndexOf("/");
            response.setHeader("Content-disposition", "attachment;filename="+url.trim().substring(_index+1,url.trim().length()));
            outStream = response.getOutputStream();
            byte[] block = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(block)) != -1) {
                outStream.write(block, 0, len);
            }
            outStream.flush();
            method.abort();
        } catch (IOException e) {
            logger.error("downloadFile", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException ex) {
                logger.error("downPdf--------", ex);
            }
        }
        return null;
    }

	/**
	 * 跳转到新建材料类别的页面
	 *
	 * @return
	 */
	@RequestMapping(ADMIN_PREFIX+"/toCreateMaterialType")
	public String toCreateMaterialType() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/teachingMaterial/create_materialtype";
	}

	/**
	 * 新建材料类别
	 *
	 * @param request
	 * @param materialType
	 * @return
	 */
	@RequestMapping(ADMIN_PREFIX+"/createMaterialType")
	@ResponseBody
	public Map<String, Object> createMaterialType(
			HttpServletRequest request,
			@ModelAttribute("materialType") MaterialType materialType) {
		Map<String, Object> json = null;
		try {
			String errorInfo=validateMaterialType(materialType);
			if(!StringUtils.isTrimEmpty(errorInfo)){
				return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo,
						null);
			}
			materialTypeBiz.save(materialType);
			json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
					null);
		} catch (Exception e) {
			e.printStackTrace();
			json = this.resultJson(ErrorCode.ERROR_SYSTEM,
					ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}

	/**
	 * 材料类别列表
	 * @param request
	 * @param pagination
     * @return
     */
	@RequestMapping(ADMIN_PREFIX+"/materialTypeList")
	public String materialTypeList(HttpServletRequest request,
									   @ModelAttribute("pagination") Pagination pagination) {
		try {
			DecimalFormat df=new DecimalFormat("######0.00");
			String whereSql = " status=1";
			MaterialType materialType=new MaterialType();
			String name=request.getParameter("name");
			if(!StringUtils.isTrimEmpty(name)){
				whereSql+=" and name like '%"+name+"%'";
				materialType.setName(name);
			}
			pagination.setRequest(request);
			List<MaterialType> materialTypeList = materialTypeBiz
					.find(pagination, whereSql);
			List<MaterialTypeDto> materialTypeDtoList=new LinkedList<MaterialTypeDto>();
			if(materialTypeList!=null&&materialTypeList.size()>0){
				for(MaterialType mt:materialTypeList){
					MaterialTypeDto materialTypeDto=new MaterialTypeDto();
					materialTypeDto.setId(mt.getId());
					materialTypeDto.setName(mt.getName());
					materialTypeDto.setNum(mt.getNum());
					materialTypeDto.setCreateTime(mt.getCreateTime());
					List<TeachingMaterial> teachingMaterialList=teachingMaterialBiz.find(null," status=1 and materialTypeId="+mt.getId());
					materialTypeDto.setActualNum(teachingMaterialList!=null&&teachingMaterialList.size()>0?teachingMaterialList.size():0);
					materialTypeDto.setReportrate(df.format(materialTypeDto.getActualNum()*1.0/materialTypeDto.getNum()));
					materialTypeDtoList.add(materialTypeDto);
				}
			}
			request.setAttribute("materialTypeDtoList", materialTypeDtoList);
			request.setAttribute("pagination", pagination);
			request.setAttribute("materialType", materialType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/teachingMaterial/materialType_list";
	}

	/**
	 * 删除材料类别
	 * @param id
	 * @return
     */
	@RequestMapping(ADMIN_PREFIX+"/delMaterialType")
	@ResponseBody
	public Map<String, Object> delMaterialType(@RequestParam("id") Long id) {
		Map<String, Object> json = null;
		try {
			MaterialType materialType = new MaterialType();
			materialType.setId(id);
			materialType.setStatus(0);
			materialTypeBiz.update(materialType);
			json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
					null);
		} catch (Exception e) {
			e.printStackTrace();
			json = this.resultJson(ErrorCode.ERROR_SYSTEM,
					ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}

	/**
	 * 跳转到修改资料类别的页面
	 * @param id
	 * @return
     */
	@RequestMapping(ADMIN_PREFIX+"/toUpdateMaterialType")
	public String toUpdateMaterialType(HttpServletRequest request,Long id){
		try{
			MaterialType materialType=materialTypeBiz.findById(id);
			request.setAttribute("materialType",materialType);
		}catch (Exception e){
			e.printStackTrace();
		}
		return "/admin/teachingMaterial/update_materialtype";
	}

	/**
	 * 修改资料类别
	 * @param materialType
	 * @return
     */
	@RequestMapping(ADMIN_PREFIX+"/updateMaterialType")
	@ResponseBody
	public Map<String,Object> updateMaterialType(MaterialType materialType){
		Map<String,Object> json=null;
		try{
			String errorInfo=validateMaterialType(materialType);
			if(!StringUtils.isTrimEmpty(errorInfo)){
				return this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, errorInfo,
						null);
			}
			materialTypeBiz.update(materialType);
			json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
					null);
		}catch (Exception e){
			e.printStackTrace();
			json = this.resultJson(ErrorCode.ERROR_SYSTEM,
					ErrorCode.SYS_ERROR_MSG, null);
		}
		return json;
	}


	public String validateMaterialType(MaterialType materialType){
		if(StringUtils.isTrimEmpty(materialType.getName())){
			return "标题不能为空";
		}
		if(materialType.getNum()==null||materialType.equals(0)){
			return "上传数量不能为空";
		}
		return null;
	}
}
