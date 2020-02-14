package com.oa.controller.letter;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.oa.biz.letter.LetterTypeBiz;
import com.oa.entity.letter.LetterType;
import com.oa.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 公文类型
 *
 * @author ccl
 * @create 2017-02-07-11:45
 */
@Controller
@RequestMapping("/admin/oa")
public class LetterTypeController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(LetterTypeController.class);
    @Autowired
    private LetterTypeBiz letterTypeBiz;

    @InitBinder({"letterType"})
    public void initLetterType(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("letterType.");
    }

    private static final String toAddLetterType = "/letter/letterType_add";
    private static final String toUpdateLetterType = "/letter/letterType_update";
    private static final String letterTypeList = "/letter/letterType_list";

    /**
     * 查询所有的类型
     *
     * @author: ccl
     * @Param: [request, pagination, letterType]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/queryAllLetterType")
    public String queryAllLetterType(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("letterType") LetterType letterType) {
        try {
            String whereSql = GenerateSqlUtil.getSql(letterType);
            whereSql += " order by sort desc";
            pagination.setRequest(request);
            List<LetterType> letterTypeList = letterTypeBiz.find(pagination, whereSql);
            request.setAttribute("letterTypeList", gson.toJson(letterTypeList));
            request.setAttribute("letterType", letterType);
        } catch (Exception e) {
            logger.info("letterTypeController--queryAllLetterType", e);
            return this.setErrorPath(request, e);
        }
        return letterTypeList;
    }


    /**
     * @Description:去添加类型
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-29
     */
    @RequestMapping("/toAddLetterType")
    public String toAddLetterType(HttpServletRequest request) {
        try {
            List<LetterType> letterTypeList = letterTypeBiz.letterTypeList();
            request.setAttribute("letterTypeList", letterTypeList);
        } catch (Exception e) {
            logger.info("letterTypeController--toAddLetterType", e);
            return this.setErrorPath(request, e);
        }
        return toAddLetterType;
    }

    /**
     * @Description:
     * @author: ccl
     * @Param: [letterType]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/addSaveLetterType")
    @ResponseBody
    public Map<String, Object> addSaveLetterType(@ModelAttribute("letterType") LetterType letterType) {
        Map<String, Object> resultMap = null;
        try {
            letterTypeBiz.save(letterType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("letterTypeController--addSaveLetterType", e);
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
    @RequestMapping("/toUpdateLetterType")
    public String toUpdateLetterType(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        try {
            LetterType letterType = letterTypeBiz.findById(id);
            request.setAttribute("letterType", letterType);

            List<LetterType> letterTypeList = letterTypeBiz.letterTypeList();
            request.setAttribute("letterTypeList", letterTypeList);
        } catch (Exception e) {
            logger.error("letterTypeController--toUpdateLetterType", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateLetterType;
    }


    /**
     * @Description:修改公告类型
     * @author: ccl
     * @Param: [request, letterType]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-29
     */
    @RequestMapping("/updateLetterType")
    @ResponseBody
    public Map<String, Object> updateLetterType(HttpServletRequest request, @ModelAttribute("letterType") LetterType letterType) {
        Map<String, Object> resultMap = null;
        try {
            letterTypeBiz.update(letterType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("letterTypeController--updateLetterType", e);
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
    @RequestMapping("/deleteLetterType")
    public String deleteLetterType(HttpServletRequest request, @RequestParam("ids") String ids) {
        try {
            String [] id=ids.split(",");
            List<Long>  longList= new ArrayList<>();
            for (int i=0;i<id.length;i++){
                longList.add(Long.parseLong(id[i]));
            }
            letterTypeBiz.deleteByIds(longList);
        } catch (Exception e) {
            logger.error("letterTypeController-deleteLetterType", e);
        }
        return "redirect:/admin/oa/queryAllLetterType.json";
    }

}
