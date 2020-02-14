package com.oa.controller.noticetype;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.biz.common.OAHessianService;
import com.oa.biz.noticetype.NoticeTypeBiz;
import com.oa.entity.department.DepartMent;
import com.oa.entity.noticetype.NoticeType;
import com.oa.utils.GenerateSqlUtil;
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
 * 公告类型
 *
 * @author ccl
 * @create 2016-12-29-9:46
 */
@Controller
@RequestMapping("/admin/oa")
public class NoticeTypeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(NoticeTypeController.class);


    @Autowired
    private NoticeTypeBiz noticeTypeBiz;

    @Autowired
    private OAHessianService oaHessionService;

    @Autowired
    private BaseHessianBiz baseHessianBiz;

    @InitBinder({"noticeType"})
    public void initTelephone(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("noticeType.");
    }

    private static final String toAddNoticeType = "/noticetype/noticetype_add";
    private static final String toUpdateNoticeType = "/noticetype/noticetype_update";
    private static final String noticeTypeList = "/noticetype/noticetype_list";

    /**
     * 查询所有的类型
     *
     * @author: ccl
     * @Param: [request, pagination, noticeType]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/queryAllNoticeType")
    public String queryAllNoticeType(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("noticeType") NoticeType noticeType) {
        try {
            String whereSql = GenerateSqlUtil.getSql(noticeType);
            whereSql+=" order by sort desc";
            pagination.setRequest(request);
            List<NoticeType> noticeTypeList = noticeTypeBiz.find(pagination, whereSql);
            request.setAttribute("noticeTypeList", noticeTypeList);
            request.setAttribute("noticeType",noticeType);
        } catch (Exception e) {
            logger.info("NoticeTypeController--queryAllNoticeType", e);
            return this.setErrorPath(request, e);
        }
        return noticeTypeList;
    }


    /**
     * @Description:去添加类型
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/toAddNoticeType")
    public String toAddNoticeType(HttpServletRequest request) {
        try {

        } catch (Exception e) {
            logger.info("NoticeTypeController--toAddNoticeType", e);
            return this.setErrorPath(request, e);
        }
        return toAddNoticeType;
    }

    /**
     * @Description:
     * @author: ccl
     * @Param: [noticeType]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/addSaveNoticeType")
    @ResponseBody
    public Map<String, Object> addSaveNoticeType(@ModelAttribute("noticeType") NoticeType noticeType) {
        Map<String, Object> resultMap = null;
        try {
            noticeTypeBiz.save(noticeType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("NoticeTypeController--addSaveNoticeType", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:去修改
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/toUpdateNoticeType")
    public String toUpdateNoticeType(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        try {
            NoticeType noticeType = noticeTypeBiz.findById(id);
            request.setAttribute("noticeType", noticeType);
        } catch (Exception e) {
            logger.error("NoticeTypeController--toUpdateNoticeType", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateNoticeType;
    }


    /**
     * @Description:修改公告类型
     * @author: ccl
     * @Param: [request, noticeType]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/updateNoticeType")
    @ResponseBody
    public Map<String, Object> updateNoticeType(HttpServletRequest request, @ModelAttribute("noticeType") NoticeType noticeType) {
        Map<String, Object> resultMap = null;
        try {
            noticeTypeBiz.update(noticeType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("NoticeTypeController--updateNoticeType", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:删除公告类型
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/deleteNoticeType")
    @ResponseBody
    public Map<String, Object> deleteNoticeType(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = null;
        try {
            noticeTypeBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("NoticeTypeController-deleteNoticeType", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    @RequestMapping("/ceshi")
    public  Map<String,Object> test(){
        String wheresSql="1=1";
        Map<String,Object> userList=oaHessionService.getRuleList(null,wheresSql);
        return userList;
    }


    @RequestMapping("/baseTest")
    @ResponseBody
    public Map<String,Object>  getAlldepartment(){
        Map<String, Object> resultMap = null;
        DepartMent departMent=new DepartMent();
        List<DepartMent> departMentList=baseHessianBiz.getDepartMentList(departMent);
        resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", departMentList);
        return resultMap;
    }





}
