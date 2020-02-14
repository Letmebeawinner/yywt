package com.houqin.controller.natural;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.houqin.biz.natural.NaturalTypeBiz;
import com.houqin.entity.natural.NaturalType;
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

@Controller
@RequestMapping("/admin/houqin")
public class NaturalTypeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(NaturalTypeController.class);
    //添加用水区域
    private static final String addNaturalType = "/natural/add_naturalType";
    //修改用水区域
    private static final String updateNaturalType = "/natural/update_naturalType";
    //用水区域列表
    private static final String listNaturalType = "/natural/naturalType_list";

    //自动注入
    @Autowired
    private NaturalTypeBiz naturalTypeBiz;

    //绑定表单
    @InitBinder
    public void initNaturalType(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("naturalType.");
    }

    /**
     * 分类列表
     *
     * @param request
     * @param pagination
     * @param naturalType
     * @return
     */
    @RequestMapping("/queryAllNaturalType")
    public String queryAllNaturalType(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination,
                                      @ModelAttribute("naturalType") NaturalType naturalType) {
        try {
            String whereSql = GenerateSqlUtil.getSql(naturalType);
            pagination.setRequest(request);
            List<NaturalType> typeList = naturalTypeBiz.find(pagination, whereSql);
            request.setAttribute("typeList", typeList);
            request.setAttribute("naturalType", naturalType);
        } catch (Exception e) {
            logger.error("naturalTypeController.queryAllNaturalType()--error", e);
            return this.setErrorPath(request, e);
        }
        return listNaturalType;
    }

    /**
     * 跳转到添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddNaturalType")
    public String toAddNaturalType(HttpServletRequest request) {
        return addNaturalType;
    }

    /**
     * 添加用水区域
     *
     * @param naturalType
     * @return
     */
    @RequestMapping("/addNaturalType")
    @ResponseBody
    public Map<String, Object> addNaturalType(@ModelAttribute("naturalType") NaturalType naturalType) {
        Map<String, Object> resultMap = null;
        try {
            naturalTypeBiz.save(naturalType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("addNaturalTypeController.addNaturalType()--error", e);
            resultMap = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return resultMap;
    }

    /**
     * 删除用水区域
     *
     * @param id
     * @return
     */
    @RequestMapping("/delNaturalType")
    @ResponseBody
    public Map<String, Object> delNaturalType(@RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = null;
        try {
            naturalTypeBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            resultMap = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return resultMap;
    }

    /**
     * 跳转到编辑页面
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateNaturalType")
    public String toUpdateNaturalType(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            NaturalType naturalType = naturalTypeBiz.findById(id);
            request.setAttribute("naturalType", naturalType);
        } catch (Exception e) {
            logger.error("naturalTypeController.toUpdateNaturalType()--error", e);
            return this.setErrorPath(request, e);
        }
        return updateNaturalType;
    }

    /**
     * 更新区域类型
     *
     * @param naturalType
     * @return
     */
    @RequestMapping("/updateNaturalType")
    @ResponseBody
    public Map<String, Object> updateNaturalType(@ModelAttribute("naturalType") NaturalType naturalType) {
        Map<String, Object> resultMap = null;
        try {
            naturalTypeBiz.update(naturalType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            resultMap = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return resultMap;
    }


}
