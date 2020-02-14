package com.oa.controller.archive;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.archivetype.ArchiveTypeBiz;
import com.oa.entity.archivetype.ArchiveType;
import com.oa.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/oa")
public class ArchiveTypeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(ArchiveTypeController.class);

    @Autowired
    private ArchiveTypeBiz archiveTypeBiz;

    @InitBinder("archiveType")
    public void initBinderArchiveType(HttpServletRequest request, ServletRequestDataBinder binder) {
        binder.setFieldDefaultPrefix("archiveType.");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    /**
     * 查询档案分类
     *
     * @param archiveType
     * @param pagination
     * @return
     */
    @RequestMapping("/queryArchiveTypeList")
    public ModelAndView queryArchiveTypeList(@ModelAttribute("archiveType") ArchiveType archiveType, @ModelAttribute("pagination") Pagination pagination) {
        ModelAndView modelAndView = new ModelAndView("/archive/archiveType_list");
        try {
            String whereSql = GenerateSqlUtil.getSql(archiveType);
            List<ArchiveType> archiveTypeList = archiveTypeBiz.find(pagination, whereSql);
            modelAndView.addObject("archiveTypeList", archiveTypeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    /**
     * 查询档案分类列表
     * @return
     */
    @RequestMapping("/ajax/queryArchiveTypeList")
    @ResponseBody
    public Map<String, Object> ajaxQueryArchiveTypeList() {
        Map<String, Object> json = null;
        try {
            List<ArchiveType> archiveTypes = archiveTypeBiz.findAll();
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, archiveTypes);
        } catch (Exception e) {
            logger.error("ArchiveTypeController.ajaxQueryArchiveTypeList");
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 查询档案分类名
     * @return
     */
    @RequestMapping("/ajax/archive/getName")
    @ResponseBody
    public Map<String, Object> ajaxQueryArchiveTypeName(@RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            ArchiveType archiveType = archiveTypeBiz.findById(id);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, archiveType);
        } catch (Exception e) {
            logger.error("ArchiveTypeController.ajaxQueryArchiveTypeName");
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    @RequestMapping("/toAddArchiveType")
    public String toAddArchiveType() {
        return "/archive/archiveType_add";
    }

    /**
     * 添加档案类型
     *
     * @param archiveType
     * @return
     */
    @RequestMapping("/addArchiveType")
    @ResponseBody
    public Map<String, Object> addArchiveType(@ModelAttribute("archiveType") ArchiveType archiveType) {
        Map<String, Object> json = null;
        try {
            archiveTypeBiz.save(archiveType);
            json = this.resultJson(ErrorCode.SUCCESS, "添加成功", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 去修改
     *
     * @return
     */
    @RequestMapping("/toUpdateArchiveType")
    public ModelAndView toUpdateArchiveType(@RequestParam("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/archive/archiveType_update");
        try {
            ArchiveType archiveType = archiveTypeBiz.findById(id);
            modelAndView.addObject("archiveType", archiveType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    /**
     * 修改
     *
     * @param archiveType
     * @return
     */
    @RequestMapping("/updateArchiveType")
    @ResponseBody
    public Map<String, Object> updateArchiveType(@ModelAttribute("archiveType") ArchiveType archiveType) {
        Map<String, Object> json = null;
        try {
            archiveTypeBiz.update(archiveType);
            json = this.resultJson(ErrorCode.SUCCESS, "修改成功", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 删除档案类型
     * @param id
     * @return
     */
    @RequestMapping("/deleteArchiveType")
    @ResponseBody
    public Map<String, Object> deleteArchiveType(@RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            archiveTypeBiz.deleteById(id);
            json = this.resultJson(ErrorCode.SUCCESS, "删除成功", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


}
