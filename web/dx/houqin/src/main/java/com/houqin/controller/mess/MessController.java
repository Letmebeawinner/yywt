package com.houqin.controller.mess;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.houqin.biz.mess.MessBiz;
import com.houqin.entity.mess.Mess;
import com.houqin.utils.GenerateSqlUtil;
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
 * 食堂管理
 *
 * @author ccl
 * @create 2016-12-22-17:30
 */
@Controller
@RequestMapping("/admin/houqin")
public class MessController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(MessController.class);
    //添加食堂
    private static final String createMess = "/mess/add-mess";
    //修改食堂
    private static final String toUpdateMess = "/mess/update-mess";
    //食堂管理
    private static final String messList = "/mess/mess_list";

    @Autowired
    private MessBiz messBiz;

    /**
     * 绑定变量
     *
     * @param binder WebDataBinder
     */
    @InitBinder({"mess"})
    public void initMess(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("mess.");
    }


    /**
     * 去添加食堂
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddMess")
    public String toAddMess(HttpServletRequest request) {
        return createMess;
    }

    /**
     * @Description:食堂列表
     * @author: ccl
     * @Param: [request, pagination]
     * @Return: java.lang.String
     * @Date: 2016-12-14
     */
    @RequestMapping("/queryAllMess")
    public String queryAllMess(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("mess") Mess mess) {
        try {
            String whereSql = GenerateSqlUtil.getSql(mess);
            pagination.setRequest(request);
            List<Mess> messList = messBiz.find(pagination, whereSql);

            request.setAttribute("messList", messList);
            request.setAttribute("mess", mess);
        } catch (Exception e) {
            logger.error("MessController--queryAllMess", e);
            return this.setErrorPath(request, e);
        }
        return messList;
    }

    /**
     * @Description:添加食堂
     * @author: ccl
     * @Param: [request, menu]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-14
     */
    @RequestMapping("/addSaveMess")
    @ResponseBody
    public Map<String, Object> addSaveMess(HttpServletRequest request, @ModelAttribute("mess") Mess mess) {
        Map<String, Object> resultMap = null;
        try {
            messBiz.save(mess);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("MessController--addSaveMess", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:去修改食堂
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2016-12-14
     */
    @RequestMapping("/toUpdateMess")
    public String toUpdateMess(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            Mess mess = messBiz.findById(id);
            request.setAttribute("mess", mess);
        } catch (Exception e) {
            logger.error("MessController--toUpdateMess", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateMess;
    }


    /**
     * @Description:去修改食堂
     * @author: ccl
     * @Param: [request, menu]
     * @Return: java.lang.String
     * @Date: 2016-12-14
     */
    @RequestMapping("/updateMess")
    @ResponseBody
    public Map<String, Object> updateMess(HttpServletRequest request, @ModelAttribute("mess") Mess mess) {
        Map<String, Object> resultMap = null;
        try {
            messBiz.update(mess);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("MessController--updateMess", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:删除食堂
     * @author: ccl
     * @Param: [request]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-14
     */
    @RequestMapping("/delMess")
    @ResponseBody
    public Map<String, Object> delMess(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            messBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("MessController--delMess", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


}
