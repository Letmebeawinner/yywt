package com.houqin.controller.messManage;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.houqin.biz.mess.MessBiz;
import com.houqin.biz.messManage.MessManageBiz;
import com.houqin.entity.mess.Mess;
import com.houqin.entity.messManage.MessManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 食堂管理员
 *
 * @author ccl
 * @create 2016-12-23-10:35
 */
@Controller
@RequestMapping("/admin/houqin")
public class MessManageController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(MessManageController.class);
    //添加食堂人员
    private static final String createMessManage = "/messManage/add-messManage";
    //修改食堂人员
    private static final String toUpdateMessManage = "/messManage/update-messManage";
    //食堂人员列表
    private static final String messManageList = "/messManage/messManage_list";

    @Autowired
    private MessManageBiz messManageBiz;

    @Autowired
    private MessBiz messBiz;

    /**
     * 绑定变量
     *
     * @param binder WebDataBinder
     */
    @InitBinder({"messManage"})
    public void initMessManage(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("messManage.");
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 去添加食堂管理员
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddMessManage")
    public String toAddMessManage(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            request.setAttribute("messId", id);
        } catch (Exception e) {
            logger.info("MessManageController--toAddMessManage", e);
            return this.setErrorPath(request, e);
        }
        return createMessManage;
    }

    /**
     * @Description:去添加食堂管理员
     * @author: ccl
     * @Param: [request, pagination]
     * @Return: java.lang.String
     * @Date: 2016-12-14
     */
    @RequestMapping("/queryAllMessManage")
    public String queryAllMessManage(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @RequestParam(value = "id") Long messId) {
        try {
            String whereSql = "1=1";
            whereSql += " and messId=" + messId;
            pagination.setRequest(request);
            List<MessManage> messManageList = messManageBiz.find(pagination, whereSql);
            request.setAttribute("messManageList", messManageList);

            Mess mess = messBiz.findById(messId);
            request.setAttribute("mess", mess);
        } catch (Exception e) {
            logger.info("MessManageController--queryAllMessManage", e);
            return this.setErrorPath(request, e);
        }
        return messManageList;
    }

    /**
     * @Description:添加食堂管理员
     * @author: ccl
     * @Param: [request, menuType]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-14
     */
    @RequestMapping("/addSaveMessManage")
    @ResponseBody
    public Map<String, Object> addSaveMessManage(HttpServletRequest request, @ModelAttribute("messManage") MessManage messManage) {

        try {
            messManageBiz.save(messManage);
        } catch (Exception e) {
            logger.info("MessManageController--addSaveMessManage", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);

        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * @Description:
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2016-12-14
     */
    @RequestMapping("/toUpdateMessManage")
    public String toUpdateMessManage(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            MessManage messManage = messManageBiz.findById(id);
            request.setAttribute("messManage", messManage);
        } catch (Exception e) {
            logger.info("MessManageController--toUpdateMessManage", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateMessManage;
    }


    /**
     * @Description:去修改食堂管理员
     * @author: ccl
     * @Param: [request, menuType]
     * @Return: java.lang.String
     * @Date: 2016-12-14
     */
    @RequestMapping("/updateMessManage")
    @ResponseBody
    public Map<String, Object> updateMessManage(HttpServletRequest request, @ModelAttribute("messManage") MessManage messManage) {
        Map<String, Object> resultMap = null;
        try {
            messManageBiz.update(messManage);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("MessManageController--updateMessManage", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:删除食堂管理员
     * @author: ccl
     * @Param: [request]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-14
     */
    @RequestMapping("/delMessMange")
    @ResponseBody
    public Map<String, Object> delMessMange(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            messManageBiz.deleteById(Long.parseLong(id));
        } catch (Exception e) {
            logger.info("--", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }


}
