package com.houqin.controller.elepower;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.electricityType.EleSecTypeBiz;
import com.houqin.biz.electricityType.ElectricityTypeBiz;
import com.houqin.biz.elepower.ElePowerBiz;
import com.houqin.entity.electricity.Electricity;
import com.houqin.entity.electricityType.EleSecType;
import com.houqin.entity.electricityType.ElectricityType;
import com.houqin.entity.elepower.ElePower;
import com.houqin.utils.GenerateSqlUtil;
import com.houqin.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 供电局抄表数
 *
 * @author YaoZhen
 * @date 05-24, 11:08, 2018.
 */
@Slf4j
@Controller
@RequestMapping("/admin/houqin")
public class ElePowerController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(ElePowerController.class);
    @Autowired
    private ElePowerBiz elePowerBiz;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ElectricityTypeBiz electricityTypeBiz;
    @Autowired
    private EleSecTypeBiz eleSecTypeBiz;

    @RequestMapping("/addElePower")
    public String addElePower() {
        try {
            List<ElectricityType> typeList = electricityTypeBiz.queryAllType();
            request.setAttribute("typeList", typeList);
        } catch (Exception e) {
            logger.error("ElectricityController.addElectricity()--error", e);
            return this.setErrorPath(request, e);
        }
        return "/elepower/add-electricity";
    }

    @RequestMapping("/doSaveElePower")
    @ResponseBody
    public Map<String, Object> doSaveElePower(ElePower elePower) {
        Map<String, Object> objMap;
        try {
            Map<String, String> user = SysUserUtils.getLoginSysUser(request);
            Long userId = Long.valueOf(user.get("id"));
            elePower.setUserId(userId);
            List<ElePower> electricities = elePowerBiz.find(null, " typeId =" + elePower.getTypeId() + " and secTypeId=" + elePower.getSecTypeId() + "   and monthTime=" + "'" + elePower.getMonthTime() + "'");
            if (ObjectUtils.isNotNull(electricities)) {
                return this.resultJson(ErrorCode.ERROR_DATA, "当前月份已添加，请选择其他月份", null);
            }
            elePowerBiz.saveEle(elePower, user);
            objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            log.error("ElePowerController.doSaveElePower", e);
            objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return objMap;
    }

    @RequestMapping("/elePowerList")
    public ModelAndView queryElePowerList(@ModelAttribute("pagination") Pagination pagination,
                                          @ModelAttribute ElePower elePower) {
        ModelAndView mv = new ModelAndView("/elepower/electricity_list");
        try {
            pagination.setRequest(request);
            String sql = GenerateSqlUtil.getSql(elePower);
            sql += " order by createTime desc ";
            List<ElePower> powers = elePowerBiz.find(pagination, sql);
            List<ElectricityType> typeList = electricityTypeBiz.queryAllType();
            EleSecType secType = eleSecTypeBiz.findById(elePower.getSecTypeId() != null ? elePower.getSecTypeId() : 0);
            request.setAttribute("secType", secType);
            mv.addObject("typeList", typeList);
            mv.addObject("powers", powers);
        } catch (Exception e) {
            log.error("ElePowerController.queryElePowerList", e);
        }
        return mv;
    }

    @RequestMapping("/updateElePower")
    public ModelAndView queryUpdateElePower(@RequestParam("id") Long elePowerId) {
        ModelAndView mv = new ModelAndView("/elepower/upd-electricity");
        try {
            ElePower elePower = elePowerBiz.findById(elePowerId);
            List<ElectricityType> typeList = electricityTypeBiz.queryAllType();
            EleSecType secType = eleSecTypeBiz.findById(elePower.getSecTypeId() != null ? elePower.getSecTypeId() : 0);
            request.setAttribute("secType", secType);
            mv.addObject("typeList", typeList);
            mv.addObject("elePower", elePower);
        } catch (Exception e) {
            log.error("ElePowerController.queryUpdateElePower", e);
        }
        return mv;
    }

    @RequestMapping("/doUpdElePower")
    @ResponseBody
    public Map<String, Object> doUpdElePower(ElePower elePower) {
        Map<String, Object> objMap;
        try {
            int row = elePowerBiz.update(elePower);
            if (row > 0) {
                objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            } else {
                objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
            }
        } catch (Exception e) {
            log.error("ElePowerController.doUpdElePower", e);
            objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return objMap;
    }

    @RequestMapping("/delElePower")
    @ResponseBody
    public Map<String, Object> delElePower(Long id) {
        Map<String, Object> objMap;
        try {
            ElePower elePower = elePowerBiz.findById(id);
            if (elePower != null) {
                elePowerBiz.deleteById(id);
                objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            } else {
                objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SYS_ERROR_MSG, null);
            }
        } catch (Exception e) {
            log.error("ElePowerController.delElePower", e);
            objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return objMap;
    }

    @RequestMapping("/query/previousDegrees")
    @ResponseBody
    public Map<String, Object> queryPreviousDegrees(@RequestParam(value = "typeId") Long typeId,
                                                    @RequestParam(value = "secTypeId") Long secTypeId, @RequestParam(value = "monthTime") String monthTime
    ) {
        try {
            List<ElePower> elePowers = elePowerBiz.find(null, " 1=1 and typeId = " + typeId + " and secTypeId=" + secTypeId + " and monthTime=" + "'" + monthTime + "'");
            return resultJson("0", "", ObjectUtils.isNotNull(elePowers) ? StringUtils.isNotEmpty((elePowers.get(0).getCurrentDegrees().toString())) ? elePowers.get(0).getCurrentDegrees().toString() : "" : "");
        } catch (Exception e) {
            log.error("ElePowerController.queryPreviousDegrees", e);
        }
        return resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
    }

    /**
     * 批量导入
     *
     * @param
     * @author: xiangdong.chang
     * @create: 2018/5/16 0016 17:47
     * @return:
     */
    @RequestMapping("/import/toElepowerImport")
    public String toElepowerImport() {
        return "/elepower/elepower-import";
    }

    /**
     * 导入抄表数
     *
     * @param
     * @author: xiangdong.chang
     * @create: 2018/5/16 0016 17:47
     * @return:
     */
    @RequestMapping("/import/elepowerImport")
    public String elepowerImport(HttpServletRequest request, @RequestParam("myFile") MultipartFile myfile) {
        try {
            elePowerBiz.elepowerImport(myfile, request);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ElePowerController.elepowerImport", e);
        }
        return "redirect:/admin/houqin/elePowerList.json";
    }
}
