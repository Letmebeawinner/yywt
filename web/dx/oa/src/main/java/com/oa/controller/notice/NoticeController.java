package com.oa.controller.notice;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.biz.notice.NoticeBiz;
import com.oa.biz.notice.NoticeDepartmentBiz;
import com.oa.biz.noticetype.NoticeTypeBiz;
import com.oa.entity.department.DepartMent;
import com.oa.entity.notice.Notice;
import com.oa.entity.notice.NoticeDepartment;
import com.oa.entity.notice.NoticeDto;
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
 * 公告管理
 *
 * @author ccl
 * @create 2016-12-29-18:11
 */
@Controller
@RequestMapping("/admin/oa")
public class NoticeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    private NoticeBiz noticeBiz;

    @Autowired
    private NoticeTypeBiz noticeTypeBiz;

    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private NoticeDepartmentBiz noticeDepartmentBiz;

    @InitBinder({"notice"})
    public void initNotice(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("notice.");
    }

    private static final String toAddNotice = "/notice/notice_add";
    private static final String toUpdateNotice = "/notice/notice_update";
    private static final String noticeList = "/notice/notice_list";
    private static final String toPublishNotice = "/notice/notice_publish";

    /**
     * @Description:查询所有通知
     * @author: ccl
     * @Param: [request, pagination, notice]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/queryAllNotice")
    public String queryAllNotice(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("notice") Notice notice) {
        try {
            pagination.setRequest(request);
            List<NoticeDto> noticeList = noticeBiz.getNoticeDtos(pagination, notice);

            List<NoticeType> noticeTypeList=noticeTypeBiz.noticeTypeList();
            request.setAttribute("noticeTypeList",noticeTypeList);
            request.setAttribute("noticeList", noticeList);
            request.setAttribute("notice", notice);
        } catch (Exception e) {
            logger.error("NoticeController--queryAllNotice", e);
            return this.setErrorPath(request, e);
        }
        return noticeList;
    }


    /**
     * @Description:其添加通知
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/toAddNotice")
    public String toAddNotice(HttpServletRequest request) {
        try {
            List<NoticeType> noticeTypeList = noticeTypeBiz.noticeTypeList();
            request.setAttribute("noticeTypeList", noticeTypeList);

            DepartMent departMent=new DepartMent();
            List<DepartMent> department=baseHessianBiz.getDepartMentList(departMent);
            request.setAttribute("department",gson.toJson(department));

        } catch (Exception e) {
            logger.error("NoticeController--toAddNotice", e);
            return this.setErrorPath(request, e);
        }
        return toAddNotice;
    }


    /**
     * @Description:添加保存公告
     * @author: ccl
     * @Param: [request, notice]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/addSaveNotice")
    @ResponseBody
    public Map<String, Object> addSaveNotice(HttpServletRequest request, @ModelAttribute("notice") Notice notice) {
        Map<String, Object> resultMap = null;
        try {
            noticeBiz.save(notice);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("NoticeController--addSaveNotice", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:去修改公告
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/toUpdateNotice")
    public String toUpdateNotice(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        try {
            List<NoticeType> noticeTypeList = noticeTypeBiz.noticeTypeList();
            request.setAttribute("noticeTypeList", noticeTypeList);


            Notice notice = noticeBiz.findById(id);
            request.setAttribute("notice", notice);
        } catch (Exception e) {
            logger.error("NoticeController--toUpdateNotice", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateNotice;
    }


    /**
     * @Description:修改公告
     * @author: ccl
     * @Param: [notice]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/updateNotice")
    @ResponseBody
    public Map<String, Object> updateNotice(@ModelAttribute("notice") Notice notice) {
        Map<String, Object> resultMap = null;
        try {
            noticeBiz.update(notice);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("NoticeController--updateNotice", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:删除通知
     * @author: ccl
     * @Param: [id]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/delNotice")
    @ResponseBody
    public  Map<String, Object> delNotice(@RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = null;
        try {
            noticeBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("NoticeController--delNotice", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * @Description:跳到发布公告页面
     * @author: lzh
     * @Param: [id, request]
     * @Return: java.lang.String
     * @Date: 16:10
     */
    @RequestMapping("/toPublishNotice")
    public String toPublishNotice(@RequestParam("id") Long id, HttpServletRequest request) {
        try {
            DepartMent departMent = new DepartMent();
            List<DepartMent> departments = baseHessianBiz.getDepartMentList(departMent);
            NoticeDepartment noticeDepartment  = new NoticeDepartment();
            noticeDepartment.setNoticeId(id);
            List<NoticeDepartment> noticeDepartments = noticeDepartmentBiz.find(null, GenerateSqlUtil.getSql(noticeDepartment));
            Notice notice = noticeBiz.findById(id);
            request.setAttribute("departments", gson.toJson(departments));
            request.setAttribute("noticeDepartments", gson.toJson(noticeDepartments));
            request.setAttribute("notice", notice);
        } catch(Exception e) {
            logger.error("NoticeController.publishNotice", e);
            return this.setErrorPath(request, e);
        }
        return toPublishNotice;
    }

    @RequestMapping("/publishNotice")
    @ResponseBody
    public Map<String, Object> publishNotice(@RequestParam("id") Long id, @RequestParam("ids[]") Long [] ids) {
        Map<String,Object> resultMap = null;
        try {
            noticeBiz.tx_publish(id, ids);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "发布成功", null);
        } catch (Exception e) {
            logger.error("NewsController.publishNotice", e);
            resultMap = this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


}
