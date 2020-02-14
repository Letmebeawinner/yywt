package com.keyanzizheng.controller.result;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.keyanzizheng.biz.award.AwardBiz;
import com.keyanzizheng.biz.result.ResultFormBiz;
import com.keyanzizheng.entity.award.Award;
import com.keyanzizheng.entity.result.ResultForm;
import com.keyanzizheng.utils.BeanUtil;
import com.keyanzizheng.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.a_268.base.constants.ErrorCode.*;

/**
 * 获奖申报
 *
 * @author YaoZhen
 * @date 11-14, 13:40, 2017.
 */
@Controller
@RequestMapping("/admin/ky/award")
public class AwardDeclarationController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(AwardDeclarationController.class);
    /**
     * 当前线程的req
     */
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private AwardBiz awardBiz;
    @Autowired
    private ResultFormBiz resultFormBiz;

    /**
     * 数据绑定到实体
     *
     * @param binder 自定义数据类型绑定
     */
    @InitBinder({"award"})
    public void awardInit(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("award.");
    }

    /**
     * 列表
     * 新增二级查询
     *
     * @param award      标题
     * @param pagination 分页
     * @return 列表
     */
    @RequestMapping("list")
    public ModelAndView listAward(@ModelAttribute("award") Award award,
                                  @ModelAttribute("pagination") Pagination pagination) {
        ModelAndView mv = new ModelAndView("/award/list_award");
        try {
            // 修复调研报告
            repairResearch(award);

            String whereSql = GenerateSqlUtil.getSql(award);
            pagination.setRequest(request);
            List<Award> awards = awardBiz.find(pagination, whereSql);

            List<AwardVO> vos = awards.stream().map(e ->
                    {
                        AwardVO vo = new AwardVO();
                        BeanUtil.copyProperties(e, vo);
                        return vo;
                    }
            ).collect(Collectors.toList());
            mv.addObject("awards", vos);

            // 显示成果形式
            List<ResultForm> resultForms = resultFormBiz.findAll();
            mv.addObject("resultForms", resultForms);
        } catch (Exception e) {
            logger.error("AwardDeclarationController.listAward", e);
        }
        return mv;
    }

    private void repairResearch(Award award) {
        Integer awardForm = award.getResultForm();

        if (ObjectUtils.isNotNull(awardForm) && awardForm == 999) {
            award.setResultForm(5);
        }
    }

    /**
     * 审核
     * @param id
     * @return
     */
    @RequestMapping("audit")
    public ModelAndView audit(Long id) {
        ModelAndView mv = new ModelAndView("/award/award_info");
        try {
            Award award = awardBiz.findById(id);
            mv.addObject("award", award);
            // 根据标识显示审批按钮
            mv.addObject("flag", 0);
        } catch (Exception e) {
            logger.error("AwardDeclarationController.awardInfo", e);
        }
        return mv;
    }

    /**
     * 通过审核
     *
     * @param id     获奖id
     * @param status 审核状态
     * @return 0未审核 1审核通过 2审核未通过
     */
    @RequestMapping("doAudit")
    @ResponseBody
    public Map<String, Object> doAudit(@RequestParam("id") Long id,
                                       @RequestParam("status") Integer status) {
        Map<String, Object> objectMap;
        try {
            Award award = new Award();
            award.setId(id);
            award.setStatus(status);
            awardBiz.update(award);
            objectMap = this.resultJson(SUCCESS, SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("AwardDeclarationController.audit", e);
            objectMap = this.resultJson(SYS_ERROR_MSG, SYS_ERROR_MSG, null);
        }
        return objectMap;
    }

    /**
     * 获奖详情
     *
     * @param id 获奖申报id
     * @return 获奖
     */
    @RequestMapping("awardInfo")
    public ModelAndView awardInfo(Long id) {
        ModelAndView mv = new ModelAndView("/award/award_info");
        try {
            Award award = awardBiz.findById(id);
            mv.addObject("award", award);
        } catch (Exception e) {
            logger.error("AwardDeclarationController.awardInfo", e);
        }
        return mv;
    }

    /**
     * remove award by id
     */
    @RequestMapping("/del")
    @ResponseBody
    public Map<String, Object> delAward(Long id) {
        Map<String, Object> objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        try {
            Award award = awardBiz.findById(id);
            if (award != null) {
                awardBiz.deleteById(id);
                objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            }
        } catch (Exception e) {
            logger.error("AwardDeclarationController.delAward", e);
        }
        return objMap;
    }
}
