package com.houqin.controller.electricityType;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.houqin.biz.electricityType.ElectricityTypeBiz;
import com.houqin.entity.electricityType.ElectricityType;
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
 * 用电区域管理
 * Created by Administrator on 2017/6/14 0014.
 */
@Controller
@RequestMapping("/admin/houqin")
public class ElectricityTypeController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(ElectricityTypeController.class);
    //添加用电区域
    private static final String addElectricityType = "/electricityType/add_electricityType";
    //修改用电区域
    private static final String updateElectricityType = "/electricityType/update_electricityType";
    //用电区域列表
    private static final String listElectricityType = "/electricityType/electricityType_list";

    //自动注入
    @Autowired
    private ElectricityTypeBiz electricityTypeBiz;

    //绑定表单
    @InitBinder
    public void initElectricityType(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("electricityType.");
    }

    /**
     * 分类列表
     * @param request
     * @param pagination
     * @param electricityType
     * @return
     */
    @RequestMapping("/queryAllElectricityType")
    public String queryAllElectricity(HttpServletRequest request, @ModelAttribute("pagination")Pagination pagination, @ModelAttribute("electricityType")ElectricityType electricityType) {
        try {
            String whereSql = GenerateSqlUtil.getSql(electricityType);
            pagination.setRequest(request);
            List<ElectricityType> typeList = electricityTypeBiz.find(pagination,whereSql);
            request.setAttribute("typeList", typeList);
            request.setAttribute("electricityType",electricityType);
        } catch (Exception e) {
            logger.error("ElectricityTypeController.queryAllElectricity()--error", e);
            return this.setErrorPath(request, e);
        }
        return listElectricityType;
    }

    /**
     * 跳转到添加页面
     * @param request
     * @return
     */
    @RequestMapping("/toAddElectricityType")
    public String toAddElectricityType(HttpServletRequest request) {
        return addElectricityType;
    }

    /**
     * 添加用电区域
     * @param request
     * @param electricityType
     * @return
     */
    @RequestMapping("/addElectricityType")
    @ResponseBody
    public Map<String,Object> addElectricityType(HttpServletRequest request,
                                                 @ModelAttribute("electricityType")ElectricityType electricityType) {
        Map<String, Object> resultMap = null;
        try {
            electricityTypeBiz.save(electricityType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,null);
        } catch (Exception e) {
            logger.error("ElectricityTypeController.addElectricityType()--error", e);
            resultMap = this.resultJson(ErrorCode.ERROR_DATA,ErrorCode.SYS_ERROR_MSG,null);
        }
        return resultMap;
    }

    /**
     * 删除用电区域
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/delElectricityType")
    @ResponseBody
    public Map<String, Object> delElectricityType(HttpServletRequest request,
                                                  @RequestParam(value = "id", required = true)Long id) {
        Map<String, Object> resultMap = null;
        try {
            electricityTypeBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,null);
        } catch (Exception e) {
            logger.error("ElectricityTypeController.delElectricityType()--error", e);
            resultMap = this.resultJson(ErrorCode.ERROR_DATA,ErrorCode.SYS_ERROR_MSG,null);
        }
        return resultMap;
    }

    /**
     * 跳转到编辑页面
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateElectricityType")
    public String toUpdateElectricityType(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            ElectricityType electricityType = electricityTypeBiz.findById(id);
            request.setAttribute("electricityType", electricityType);
        } catch (Exception e) {
            logger.error("ElectricityTypeController.toUpdateElectricityType()--error", e);
            return this.setErrorPath(request, e);
        }
        return updateElectricityType;
    }

    /**
     * 更新区域类型
     * @param request
     * @param electricityType
     * @return
     */
    @RequestMapping("/updateElectricityType")
    @ResponseBody
    public Map<String, Object> updateElectricityType(HttpServletRequest request,
                                                     @ModelAttribute("electricityType") ElectricityType electricityType) {
        Map<String, Object> resultMap = null;
        try {
            electricityTypeBiz.update(electricityType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG,null);
        } catch (Exception e) {
            logger.error("ElectricityTypeController.updateElectricityType()--error", e);
            resultMap = this.resultJson(ErrorCode.ERROR_DATA,ErrorCode.SYS_ERROR_MSG,null);
        }
        return resultMap;
    }
}
