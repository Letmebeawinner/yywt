package com.jiaowu.controller.uploadExcelHistory;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.jiaowu.biz.uploadExcelHistory.UploadExcelHistoryBiz;
import com.jiaowu.entity.classroom.Classroom;
import com.jiaowu.entity.uploadExcelHistory.UploadExcelHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


/**
 * 上传Excel文件Controller
 *
 * @author 王赛
 */
@Controller
public class UploadExcelHistoryController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(UploadExcelHistoryController.class);

    @InitBinder({"uploadExcelHistory"})
    public void initUploadExcelHistory(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("uploadExcelHistory.");
    }


    @Autowired
    private UploadExcelHistoryBiz uploadExcelHistoryBiz;

    private static final String ADMIN_PREFIX = "/admin/jiaowu/excel";

    /**
     * @param request
     * @return
     * @Description 上传文件历史记录
     */
    @RequestMapping(ADMIN_PREFIX + "/uploadExcelHistoryList")
    public String uploadExcelHistoryList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            pagination.setPageSize(10);
            pagination.setRequest(request);
            List<UploadExcelHistory> uploadExcelHistoryList = uploadExcelHistoryBiz.find(pagination, " status = 1");
            request.setAttribute("uploadExcelHistoryList", uploadExcelHistoryList);
            request.setAttribute("pagination", pagination);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/excel/excel_history_list";
    }

    /**
     * 跳转修改页面
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/toUpdate/{id}")
    public String toUpdate(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            UploadExcelHistory uploadExcelHistory = uploadExcelHistoryBiz.findById(id);
            request.setAttribute("uploadExcelHistory", uploadExcelHistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/excel/excel_history_update";
    }

    /**
     * 修改
     *
     * @param request
     * @param uploadExcelHistory
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/update")
    @ResponseBody
    public Map<String, Object> update(HttpServletRequest request, @ModelAttribute("uploadExcelHistory") UploadExcelHistory uploadExcelHistory) {
        Map<String, Object> json = null;
        try {
            uploadExcelHistoryBiz.update(uploadExcelHistory);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.info("UploadExcelHistoryController.update", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 跳转上传文件页面
     *
     * @param request
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/toAdd")
    public String toAdd(HttpServletRequest request) {
        return "/admin/excel/excel_history_add";
    }

    /**
     * 增加上传记录
     *
     * @param request
     * @param uploadExcelHistory
     * @return
     */
    @RequestMapping(ADMIN_PREFIX + "/add")
    @ResponseBody
    public Map<String, Object> add(HttpServletRequest request, @ModelAttribute("uploadExcelHistory") UploadExcelHistory uploadExcelHistory) {
        Map<String, Object> json = null;
        try {
            uploadExcelHistory.setStatus(1);
            uploadExcelHistory.setCreateTime(new Timestamp(System.currentTimeMillis()));
            uploadExcelHistoryBiz.save(uploadExcelHistory);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.info("UploadExcelHistoryController.update", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * @param request
     * @return java.util.Map
     * @Description 删除上传记录
     */
    @RequestMapping(ADMIN_PREFIX + "/deleteExcelHistoryById")
    @ResponseBody
    public Map<String, Object> deleteExcelHistoryById(HttpServletRequest request,
                                                      @RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            uploadExcelHistoryBiz.deleteById(id);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("UploadExcelHistoryController.deleteExcelHistoryById", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

}
