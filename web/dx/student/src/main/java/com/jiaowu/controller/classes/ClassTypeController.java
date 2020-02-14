package com.jiaowu.controller.classes;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.jiaowu.biz.classes.ClassTypeBiz;
import com.jiaowu.biz.common.BaseHessianService;
import com.jiaowu.dao.classes.ClassTypeDao;
import com.jiaowu.entity.classes.ClassType;
import com.jiaowu.util.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 班型Controller
 *
 * @author 李帅雷
 */
@Controller
public class ClassTypeController extends BaseController {

    private static final String ADMIN_PREFIX = "/admin/jiaowu/classType";
    private static final String WITHOUT_ADMIN_PREFIX = "/jiaowu/classType";
    private static Logger logger = LoggerFactory.getLogger(ClassTypeController.class);
    @Autowired
    BaseHessianService baseHessianService;
    @Autowired
    private ClassTypeBiz classTypeBiz;
    @Autowired
    private ClassTypeDao classTypeDao;

    @InitBinder({"classType"})
    public void initClassType(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("classType.");
    }

    /**
     * @param request
     * @return java.lang.String
     * @Description 跳转到创建班型的页面
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/createClassType")
    public String createClassType(HttpServletRequest request) {
        try {

        } catch (Exception e) {
            logger.info("ClassTypeController.createClassType", e);
        }
        return "/admin/class/create_classtype";
    }

    /**
     * @param request
     * @param classType
     * @return java.util.Map
     * @Description 创建班型
     */
    @RequestMapping(ADMIN_PREFIX + "/doCreateClassType")
    @ResponseBody
    public Map<String, Object> doCreateClassType(HttpServletRequest request, @ModelAttribute("classType") ClassType classType) {
        Map<String, Object> json = null;
        try {
            json = validateClassType(classType);
            if (json != null) {
                return json;
            }
            classType.setStatus(1);
            classTypeBiz.save(classType);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.info("ClassTypeController.doCreateClassType", e);
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    /**
     * @param request
     * @param pagination
     * @return java.lang.String
     * @Description 班型列表
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/classTypeList")
    public String classTypeList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
            String roleIds=baseHessianService.queryUserRolesByUserId(Long.parseLong(userMap.get("id")));
            if(!StringUtils.isEmpty(roleIds)){
                if(roleIds.indexOf("27")!=-1){
                    request.setAttribute("unit","true");
                }
            }

            ClassType classType = new ClassType();
            String whereSql = " status=1 order by id desc";
            whereSql += classTypeBiz.addCondition(request, classType);
            pagination.setRequest(request);
            List<ClassType> classTypeList = classTypeBiz.find(pagination, whereSql);
            request.setAttribute("classTypeList", classTypeList);
            request.setAttribute("pagination", pagination);
            request.setAttribute("classType", classType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/class/classtype_list";
    }

    /**
     * @param request
     * @param id
     * @return java.lang.String
     * @Description 跳转到修改班型的页面
     * @author 李帅雷
     */
    @RequestMapping(ADMIN_PREFIX + "/toUpdateClassType")
    public String toUpdateClassType(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            ClassType classType = classTypeBiz.findById(id);
            request.setAttribute("classType", classType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/class/update_classtype";
    }

    /**
     * @param request
     * @param classType
     * @return java.util.Map
     * @Description 修改班型
     */
    @RequestMapping(ADMIN_PREFIX + "/updateClassType")
    @ResponseBody
    public Map<String, Object> updateClassType(HttpServletRequest request, @ModelAttribute("classType") ClassType classType) {
        Map<String, Object> json = null;
        try {
            json = validateClassType(classType);
            if (json != null) {
                return json;
            }
            classTypeBiz.update(classType);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @param id
     * @return java.util.Map
     * @Description 删除班型
     */
    @RequestMapping(ADMIN_PREFIX + "/delClassType")
    @ResponseBody
    public Map<String, Object> delClassType(HttpServletRequest request, @RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            ClassType classType = new ClassType();
            classType.setId(id);
            classType.setStatus(0);
            classTypeBiz.update(classType);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 验证班型
     *
     * @param classType
     * @return
     */
    public Map<String, Object> validateClassType(ClassType classType) {
        Map<String, Object> error = null;
        if (StringUtils.isTrimEmpty(classType.getName())) {
            error = this.resultJson(ErrorCode.ERROR_PARAMETER_VERIFY, "名称不能为空", null);
            return error;
        }
        return null;
    }

    /**
     * 查询班型人数
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/queryClassTypeNum")
    public String queryClassTypeNum(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("classType") ClassType classType) {
        try {
            String whereSql = GenerateSqlUtil.getSql(classType);
            pagination.setRequest(request);
            List<ClassType> classTypeList = classTypeBiz.find(pagination, whereSql);
            if (ObjectUtils.isNotNull(classTypeList)) {
                for (ClassType type : classTypeList) {
                    int num = classTypeDao.queryClassTypeCount(type.getId().toString());
                    type.setNum(num);
                }
            }
            request.setAttribute("classTypeList", classTypeList);
            request.setAttribute("classType", classType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/class/classtype_num_list";
    }


}
