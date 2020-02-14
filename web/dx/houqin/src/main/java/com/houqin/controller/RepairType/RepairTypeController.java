package com.houqin.controller.RepairType;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.houqin.biz.repairType.RepairTypeBiz;
import com.houqin.entity.repairType.RepairType;
import com.houqin.entity.repairType.RepairTypeDto;
import com.houqin.utils.GenerateSqlUtil;
import org.apache.commons.lang.StringUtils;
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
 * 维修类型
 *
 * @author: ccl
 * @Param:
 * @Return:
 * @Date: 2016-12-10
 */
@Controller
@RequestMapping("/admin/houqin")
public class RepairTypeController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(RepairTypeController.class);
    //添加维修类型
    private static final String createRepairType = "/repairType/add-repairType";
    //修改维修类型
    private static final String toUpdateRepairType = "/repairType/update-repairType";
    //维修类型列表
    private static final String repairTypeList = "/repairType/repairType_list";

    @Autowired
    private RepairTypeBiz repairTypeBiz;

    /**
     * 绑定变量
     *
     * @param binder WebDataBinder
     */
    @InitBinder({"repairType"})
    public void initRepairType(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("repairType.");
    }


    /**
     * @Description:查询所有的维修类型
     * @author: ccl
     * @Param: [request, pagination, repairType]
     * @Return: java.lang.String
     * @Date: 2017-02-06
     */
    @RequestMapping("/queryAllRepairType")
    public String queryAllRepairType(HttpServletRequest request) {
        try {
            List<RepairType> repairTypeList = repairTypeBiz.findAll();
            request.setAttribute("repairTypeList", gson.toJson(repairTypeList));
        } catch (Exception e) {
            logger.error("RepairTypeController--queryAllRepairType", e);
            return this.setErrorPath(request, e);
        }
        return repairTypeList;
    }


    /**
     * 初始化添加维修类型页面
     *
     * @param request HttpServletRequest
     * @return 添加页面
     */
    @RequestMapping("/toAddRepairType")
    public String addRepairType(HttpServletRequest request) {
        try {
            List<RepairType> repairTypeList = repairTypeBiz.findAll();
            request.setAttribute("repairTypeList", gson.toJson(repairTypeList));
        } catch (Exception e) {
            logger.error("RepairTypeController--addRepairType", e);
            return this.setErrorPath(request, e);
        }
        return createRepairType;
    }


    /**
     * 添加维修类型
     */
    @RequestMapping("/addSaveRepairType")
    @ResponseBody
    public Map<String, Object> addSaveRepairType(HttpServletRequest request, @ModelAttribute("repairType") RepairType repairType) {
        Map<String, Object> resultMap = null;
        try {
            repairTypeBiz.save(repairType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("RepairTypeController--addSaveRepairType", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * 去修改维修类型
     */
    @RequestMapping("/toUpdateRepairType")
    public String toUpdateRepairType(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            List<RepairType> repairTypeList = repairTypeBiz.findAll();
            request.setAttribute("repairTypeList", gson.toJson(repairTypeList));
            RepairType repairType = repairTypeBiz.findById(id);
            RepairTypeDto repairTypeDto = new RepairTypeDto();
            repairTypeDto.setId(repairType.getId());
            repairTypeDto.setName(repairType.getName());
            repairTypeDto.setSort(repairType.getSort());
            repairTypeDto.setFunctionType(repairType.getFunctionType());
            if(repairType.getParentId()!=0){
                repairTypeDto.setParentId(repairType.getParentId());
                repairTypeDto.setParentName(repairTypeBiz.findById(repairType.getParentId()).getName());
            }else {
                repairTypeDto.setParentId(0L);
                repairTypeDto.setParentName("根目录");
            }
            request.setAttribute("repairTypeDto", repairTypeDto);
        } catch (Exception e) {
            logger.info("toUpdateRepairType--error", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateRepairType;
    }

    /**
     * 修改维修类型
     */
    @RequestMapping("/UpdateRepairType")
    @ResponseBody
    public Map<String, Object> UpdateRepairType(HttpServletRequest request, @ModelAttribute("repairType") RepairType repairType) {
        Map<String, Object> resultMap = null;
        try {
            repairTypeBiz.update(repairType);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("RepairTypeController--UpdateRepairType", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * 修改维修类型
     */

    @RequestMapping("/delRepairType")
    @ResponseBody
    public Map<String, Object> delRepairType(HttpServletRequest request) {
        Map<String, Object> resultMap = null;
        try {
            String ids=request.getParameter("ids");
            if(ids!=null){
                String[] id = ids.split(",");
                for (int i = 0, length = id.length; i < length; i++) {
                    if (id[i] != null) {
                        repairTypeBiz.deleteById(Long.parseLong(id[i]));
                    }
                }
                resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
            }
        } catch (Exception e) {
            logger.error("RepairTypeController--delRepairType", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

}
