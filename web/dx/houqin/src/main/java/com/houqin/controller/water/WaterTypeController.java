package com.houqin.controller.water;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.houqin.biz.water.WaterTypeBiz;
import com.houqin.entity.water.WaterType;
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
public class WaterTypeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(WaterTypeController.class);
    //添加用水区域
    private static final String addWaterType = "/water/add_waterType";
    //修改用水区域
    private static final String updateWaterType = "/water/update_waterType";
    //用水区域列表
    private static final String listWaterType = "/water/waterType_list";

    //自动注入
    @Autowired
    private WaterTypeBiz waterTypeBiz;

    //绑定表单
    @InitBinder
    public void initWaterType(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("waterType.");
    }

    /**
     * 分类列表
     *
     * @param request
     * @param pagination
     * @param waterType
     * @return
     */
    @RequestMapping("/queryAllWaterType")
    public String queryAllWaterType(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination,
                                    @ModelAttribute("waterType") WaterType waterType) {
        try {
            String whereSql = GenerateSqlUtil.getSql(waterType);
            pagination.setRequest(request);
            List<WaterType> typeList = waterTypeBiz.find(pagination, whereSql);
            request.setAttribute("typeList", typeList);
            request.setAttribute("waterType", waterType);
        } catch (Exception e) {
            logger.error("WaterTypeController.queryAllWaterType()--error", e);
            return this.setErrorPath(request, e);
        }
        return listWaterType;
    }

    /**
     * 跳转到添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddWaterType")
    public String toAddWaterType(HttpServletRequest request) {
        return addWaterType;
    }

    /**
     * 添加用水区域
     *
     * @param waterType
     * @return
     */
    @RequestMapping("/addWaterType")
    @ResponseBody
    public Map<String, Object> addWaterType(@ModelAttribute("waterType") WaterType waterType) {
        Map<String, Object> resultMap = null;
        try {
            waterTypeBiz.save(waterType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("addWaterTypeController.addWaterType()--error", e);
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
    @RequestMapping("/delWaterType")
    @ResponseBody
    public Map<String, Object> delWaterType(@RequestParam(value = "id", required = true) Long id) {
        Map<String, Object> resultMap = null;
        try {
            waterTypeBiz.deleteById(id);
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
    @RequestMapping("/toUpdateWaterType")
    public String toUpdateWaterType(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            WaterType waterType = waterTypeBiz.findById(id);
            request.setAttribute("waterType", waterType);
        } catch (Exception e) {
            logger.error("WaterTypeController.toUpdateWaterType()--error", e);
            return this.setErrorPath(request, e);
        }
        return updateWaterType;
    }

    /**
     * 更新区域类型
     *
     * @param waterType
     * @return
     */
    @RequestMapping("/updateWaterType")
    @ResponseBody
    public Map<String, Object> updateWaterType(@ModelAttribute("waterType") WaterType waterType) {
        Map<String, Object> resultMap = null;
        try {
            waterTypeBiz.update(waterType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            resultMap = this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return resultMap;
    }
}
