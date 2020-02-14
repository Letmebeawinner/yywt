package com.houqin.controller.equipment;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.houqin.biz.equipment.EquipmentBiz;
import com.houqin.biz.goodsunit.GoodsunitBiz;
import com.houqin.entity.equipment.Equipment;
import com.houqin.entity.goodsunit.Goodsunit;
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
 * 器材管理
 * Created by Administrator on 2016/12/16.
 */
@Controller
@RequestMapping("/admin/houqin")
public class EquipmentController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(EquipmentController.class);
    //添加消防器材
    private static final String createEquipment = "/equipment/add-equipment";
    //更新消防器材
    private static final String toUpdateEquipment = "/equipment/update-equipment";
    //消防器材列表
    private static final String equipmentList = "/equipment/equipment_list";

    @Autowired
    private EquipmentBiz equipmentBiz;
    @Autowired
    private GoodsunitBiz goodsunitBiz;

    @InitBinder("equipment")
    public void initEquipment(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("equipment.");
    }

    /**
     * 查询器材列表
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/queryAllEquipment")
    public String queryAllEquipment(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            String whereSql = "1=1";
            String id = request.getParameter("typeId");
            if (!StringUtils.isTrimEmpty(id) && Integer.parseInt(id) > 0) {
                whereSql += " and id=" + id;
            }
            String status = request.getParameter("status");
            if (!StringUtils.isTrimEmpty(status) && Integer.parseInt(status) > -1) {
                whereSql += " and status=" + status;
            }
            String name = request.getParameter("name");
            if (!StringUtils.isTrimEmpty(name)) {
                whereSql += " and name like '%" + name.trim() + "%'";
            }
            pagination.setRequest(request);
            List<Equipment> equipmentList = equipmentBiz.find(pagination, whereSql);
            for (Equipment equipment : equipmentList) {
                Goodsunit goodsunitBizById = goodsunitBiz.findById(equipment.getUnitId());
                if (ObjectUtils.isNotNull(goodsunitBizById)) {
                    equipment.setUnitName(goodsunitBizById.getName());
                }
            }
            request.setAttribute("equipmentList", equipmentList);
            request.setAttribute("name", name);
            request.setAttribute("status", status);
            request.setAttribute("id", id);
        } catch (Exception e) {
            logger.info("EquipmentController--queryAllEquipment", e);
            return this.setErrorPath(request, e);
        }
        return equipmentList;
    }


    /**
     * 去添加器材页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddEquipment")
    public String toAddEquipment(HttpServletRequest request) {
        try {
            //单位
            List<Goodsunit> goodsunitList = goodsunitBiz.getAllGoodsunit();
            request.setAttribute("goodsunitList", goodsunitList);
        } catch (Exception e) {
            logger.info("EquipmentController--toAddEquipment", e);
            return this.setErrorPath(request, e);
        }
        return createEquipment;
    }

    /**
     * 添加器材
     *
     * @param request
     * @param equipment
     * @return
     */
    @RequestMapping("/addSaveEquipment")
    @ResponseBody
    public Map<String, Object> addSaveEquipment(HttpServletRequest request, @ModelAttribute("equipment") Equipment equipment) {
        try {
            equipmentBiz.save(equipment);
        } catch (Exception e) {
            logger.info("EquipmentController--addSaveEquipment", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * 去更新器材页面
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateEquipment")
    public String toUpdateEquipment(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        try {
            //单位
            List<Goodsunit> goodsunitList = goodsunitBiz.getAllGoodsunit();
            request.setAttribute("goodsunitList", goodsunitList);
            Equipment equipment = equipmentBiz.findById(id);
            request.setAttribute("equipment", equipment);
        } catch (Exception e) {
            logger.info("EquipmentController--toUpdateEquipment", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateEquipment;
    }

    /**
     * 修改器材
     *
     * @param request
     * @param equipment
     * @return
     */
    @RequestMapping("/updateEquipment")
    @ResponseBody
    public Map<String, Object> updateEquipment(HttpServletRequest request, @ModelAttribute("equipment") Equipment equipment) {
        Map<String, Object> resultMap = null;
        try {
            equipmentBiz.update(equipment);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("EquipmentController--updateEquipment", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * 根据id删除器材
     *
     * @param request
     * @return
     */
    @RequestMapping("/delEquipment")
    @ResponseBody
    public Map<String, Object> delEquipment(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            equipmentBiz.deleteById(Long.parseLong(id));
        } catch (Exception e) {
            logger.info("EquipmentController--delEquipment", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);
        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

}
