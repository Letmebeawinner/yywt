package com.houqin.controller.electricityType;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.houqin.biz.electricityType.EleSecTypeBiz;
import com.houqin.biz.electricityType.ElectricityTypeBiz;
import com.houqin.entity.electricityType.EleSecType;
import com.houqin.utils.GenerateSqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * 用电区域二级列表
 *
 * @author YaoZhen
 * @date 06-21, 16:00, 2018.
 */
@Slf4j
@Controller
@RequestMapping("/admin/houqin")
public class EleSecTypeController extends BaseController {

    @Autowired private ElectricityTypeBiz electricityTypeBiz;
    @Autowired private EleSecTypeBiz eleSecTypeBiz;

    @RequestMapping("saveEleSecType")
    public ModelAndView querySaveEleSecType() {
        ModelAndView mv = new ModelAndView("/electricityType/add_electricitySecType");
        try {
            mv.addObject("electricityTypeList", electricityTypeBiz.findAll());
        } catch (Exception e) {
            log.error("EleSecTypeController.querySaveEleSecType", e);
        }
        return mv;
    }

    @RequestMapping("/doSaveEleType")
    @ResponseBody
    public Map<String, Object> doSaveEleType(EleSecType eleSecType) {
        Map<String, Object> objMap;
        try {
            eleSecTypeBiz.save(eleSecType);
            objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            log.error("EleSecTypeController.doSaveEleType", e);
            objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return objMap;
    }

    @RequestMapping("eleTypeList")
    public ModelAndView eleTypeListService(
            @ModelAttribute("pagination")Pagination pagination,
            EleSecType eleSecType) {
        ModelAndView mv = new ModelAndView("/electricityType/eleSecType_list");
        try {
            String whereSql = GenerateSqlUtil.getSql(eleSecType);
            List eleSecTypes = eleSecTypeBiz.findVOS(pagination, whereSql);
            mv.addObject("eleSecTypes", eleSecTypes);
            mv.addObject("eleSecType", eleSecType);
        } catch (Exception e) {
            log.error("EleSecTypeController.eleTypeListService", e);
        }
        return mv;
    }

    @RequestMapping("updEleSecType")
    public ModelAndView updEleSecType(Long id) {
        ModelAndView mv = new ModelAndView("/electricityType/update_eleSecType");

        try {
            mv.addObject("electricityTypeList", electricityTypeBiz.findAll());
            mv.addObject("eleSecType", eleSecTypeBiz.findById(id));
        } catch (Exception e) {
            log.error("EleSecTypeController.updEleSecType", e);
        }
        return mv;
    }

    @RequestMapping("/updEleSecTypeService")
    @ResponseBody
    public Map<String, Object> updEleSecTypeService(EleSecType eleSecType) {
        Map<String, Object> objMap;
        try {
            eleSecTypeBiz.update(eleSecType);
            objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            log.error("EleSecTypeController.updEleSecTypeService", e);
            objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return objMap;
    }


    @RequestMapping("/removeEleSecType")
    @ResponseBody
    public Map<String, Object> removeEleSecType(Long id) {
        Map<String, Object> objMap;
        try {

            EleSecType eleSecType = eleSecTypeBiz.findById(id);
            if ( eleSecType != null) {
                eleSecType.setStatus(1);
                eleSecTypeBiz.update(eleSecType);
                objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            } else {
                objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
            }
        } catch (Exception e) {
            log.error("EleSecTypeController.removeEleSecType", e);
            objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return objMap;
    }
}
