package com.jiaowu.controller.keyan;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.DateUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.google.common.reflect.TypeToken;
import com.google.gson.JsonSyntaxException;
import com.jiaowu.biz.common.BaseHessianBiz;
import com.jiaowu.biz.common.KeYanHessianService;
import com.jiaowu.entity.result.QueryResult;
import com.jiaowu.entity.result.Result;
import com.jiaowu.util.HessianUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 科研成果
 *
 * @author YaoZhen
 * @version 11-01, 11:00, 2017.
 */
@RequestMapping("/admin/jiaowu/ky")
@Controller
public class ScientificController extends BaseController {
    private static final Logger logger = Logger.getLogger(ScientificController.class);

    /**
     * URL标识
     */
    private static final int KE_YAN_ID = 1;
    /**
     * URL标识
     */
    private static final int ZI_ZHENG_ID = 2;

    private final KeYanHessianService keYanHessianService;
    private final HttpServletRequest request;
    private final BaseHessianBiz baseHessianBiz;

    @Autowired
    public ScientificController(KeYanHessianService keYanHessianService, HttpServletRequest request, BaseHessianBiz baseHessianBiz) {
        this.keYanHessianService = keYanHessianService;
        this.request = request;
        this.baseHessianBiz = baseHessianBiz;
    }


    @InitBinder("result")
    public void initResult(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("result.");
    }

    /**
     * 跳转成果添加页面
     *
     * @return 科研或者生态文明所页面
     */
    @RequestMapping("/toAddResult")
    public ModelAndView toAddResult(@ModelAttribute("result") Result result) {
        ModelAndView mv = new ModelAndView();
        try {
            // 形式
            Map<String, String> resultFormJson = keYanHessianService.scientificResearchResults();
            String formJson = resultFormJson.get("resultFormList");

            // 解析列表
            List<Map<String, String>> formList = gson.fromJson(formJson, new TypeToken<List<Map<String, String>>>() {
            }.getType());
            mv.addObject("resultFormList", formList);

            String nowDate = DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            mv.addObject("nowDate", nowDate);

            if (result.getResultType() == KE_YAN_ID) {
                mv.setViewName("/admin/result/result_add");
            }
            if (result.getResultType() == ZI_ZHENG_ID) {
                mv.setViewName("/admin/result/result_zz_add");
            }
        } catch (Exception e) {
            logger.error("toAddResult", e);
        }
        return mv;
    }


    /**
     * 添加成果
     *
     * @param result 与科研对应
     * @return json
     */
    @RequestMapping("/addResult")
    @ResponseBody
    public Map<String, Object> addResult(@ModelAttribute("result") Result result) {
        Map<String, Object> objectMap;
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            // 获取教职工id 1为管理员 2为教职工 3为学员
            Long linkId = baseHessianBiz.queryEmployeeIdBySysUserId(userId);
            if (ObjectUtils.isNotNull(linkId)) {
                result.setEmployeeId(linkId);
            }
            result.setSysUserId(userId);

            Boolean flag = keYanHessianService.addScientificResearchResults(gson.toJson(result));

            if (flag) {
                objectMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", result);
            } else {
                objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
            }
        } catch (Exception e) {
            logger.error("addResult", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }


    /**
     * 查看自己提交过的所有的课题
     *
     * @param pagination    分页
     * @param resultType    1:科研 2:生态
     * @param yearOrMonthly 生态的课题分为年度/月度
     * @param resultName    成果名称
     * @return 成果列表
     */
    @RequestMapping("/myTaskResultList")
    public ModelAndView myTaskResultList(@ModelAttribute("pagination") Pagination pagination,
                                         @RequestParam("resultType") Integer resultType,
                                         @RequestParam(value = "yearOrMonthly", required = false) Integer yearOrMonthly,
                                         @RequestParam(value = "resultName", required = false) String resultName) {
        ModelAndView mv = new ModelAndView();
        try {
            Long sysUserId = SysUserUtils.getLoginSysUserId(request);
            pagination.setPageSize(10);
            Map<String, String> result = keYanHessianService
                    .queryResultListByUserId(pagination, sysUserId, resultType, yearOrMonthly, resultName);

            // 解析列表
            List<QueryResult> resultList = gson.fromJson(result.get("list"), new TypeToken<List<QueryResult>>() {
            }.getType());

            // 环回分页
            pagination = gson.fromJson(result.get("pagination"), Pagination.class);
            pagination.setRequest(request);
            // 用hession传来的分页 覆盖ModelAttribute暴露的分页对象
            mv.addObject("pagination", pagination);

            if (!CollectionUtils.isEmpty(resultList)) {
                mv.addObject("resultList", resultList);
            }

            if (yearOrMonthly != null) {
                mv.addObject("yom", yearOrMonthly);
            }

            if (resultName != null) {
                mv.addObject("resultName", resultName);
            }

            if (resultType == KE_YAN_ID) {
                mv.setViewName("/admin/result/my_result_task_list");
            }
            if (resultType == ZI_ZHENG_ID) {
                mv.setViewName("/admin/result/my_result_zz_task_list");
            }
            mv.addObject("resultType", resultType);
        } catch (Exception e) {
            logger.error("ScientificController.myTaskResultList", e);
        }
        return mv;
    }

    /**
     * 查看课题详情
     */
    @RequestMapping("/getResultInfo")
    public ModelAndView getResultInfo(@RequestParam("id") Long id) {
        ModelAndView mv = new ModelAndView("/admin/result/result_info");
        try {
            String resultJson = keYanHessianService.getResultById(id);
            Result result = gson.fromJson(resultJson, Result.class);
            mv.addObject("result", result);

            // 形式
            Map<String, String> resultFormJson = keYanHessianService.scientificResearchResults();
            String formJson = resultFormJson.get("resultFormList");

            // 解析列表
            List<Map<String, String>> formList = gson.fromJson(formJson, new TypeToken<List<Map<String, String>>>() {
            }.getType());

            mv.addObject("resultFormList", formList);
        } catch (JsonSyntaxException e) {
            logger.error("ScientificController.getResultInfo", e);
        }
        return mv;
    }

    /**
     * 上传课题结项申报
     */
    @RequestMapping("/problemStatementDeclaration")
    public ModelAndView problemStatementDeclaration(@RequestParam("resultId") Long resultId) {
        ModelAndView mv = new ModelAndView("/admin/result/final_declaration");
        try {
            mv.addObject("resultId", resultId);
        } catch (Exception e) {
            logger.error("ScientificController.problemStatementDeclaration", e);
        }
        return mv;
    }

    /**
     * 上传课题结项申报
     *
     * @param fileUrlDeclaration 附件的地址
     * @param resultId           申报课题的ID
     */
    @RequestMapping("/problemStatementDeclaration/add")
    @ResponseBody
    public Map<String, Object> addProblemStatementDeclaration(@RequestParam("fileUrlDeclaration") String fileUrlDeclaration,
                                                              @RequestParam("resultId") Long resultId) {
        Map<String, Object> objectMap;
        try {
            byte[] byUrl = HessianUtil.serialize(fileUrlDeclaration);
            byte[] byId = HessianUtil.serialize(resultId);
            Boolean flag = keYanHessianService.addProblemStatementDeclaration(byUrl, byId);
            if (flag) {
                objectMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            } else {
                objectMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
            }
        } catch (Exception e) {
            logger.error("ScientificController.addProblemStatementDeclaration", e);
            objectMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return objectMap;
    }

    /**
     * 获奖申报
     */
    @RequestMapping("/awardDeclaration")
    public ModelAndView awardDeclaration() {
        ModelAndView mv = new ModelAndView("/admin/result/award_declaration");
        try {
        } catch (Exception e) {
            logger.error("ScientificController.awardDeclaration", e);
        }
        return mv;
    }

    /**
     * 上传获奖申报
     *
     * @param fileUrlAward 附件的地址
     */
    @RequestMapping("/awardDeclaration/add")
    @ResponseBody
    public Map<String, Object> addAwardDeclaration(@RequestParam("fileUrlAward") String fileUrlAward,
                                                   @RequestParam("awardTitle") String awardTitle,
                                                   @RequestParam("resultForm") Integer resultForm,
                                                   @RequestParam("awardSituation") Integer awardSituation,
                                                   @RequestParam("digest") String digest) {
        Map<String, Object> objectMap;
        try {
            // 获取用户id
            Map<String, String> loginUser = SysUserUtils.getLoginSysUser(request);
            // 获取用户名
            String userName = loginUser.get("userName");
            String userId = loginUser.get("id");
            Map<String, Object> params = new HashMap<>(16);
            params.put("userName", userName);
            params.put("userId", userId);
            params.put("title", awardTitle);
            params.put("resultForm", resultForm);
            params.put("awardSituation", awardSituation);
            params.put("digest", digest);
            params.put("url", fileUrlAward);
            Boolean flag = keYanHessianService.addAwardDeclaration(params);
            if (flag) {
                objectMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            } else {
                objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, ErrorCode.SYS_ERROR_MSG, null);
            }
        } catch (Exception e) {
            logger.error("ScientificController.addProblemStatementDeclaration", e);
            objectMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return objectMap;
    }

    /**
     * 我的申报记录
     *
     * @return 列表
     */
    @RequestMapping("myAwardDeclaration")
    public ModelAndView myAwardDeclaration() {
        ModelAndView mv = new ModelAndView("/admin/result/my_award_declaration");
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            byte[] id = HessianUtil.serialize(userId);
            String listJson = keYanHessianService.listAwardByUserId(id);
            List<Map<String, String>> awards = gson.fromJson(listJson,
                    new TypeToken<List<Map<String, String>>>() {
                    }.getType());
            mv.addObject("awards", awards);
        } catch (IOException e) {
            logger.error("ScientificController.myAwardDeclaration", e);
        }
        return mv;
    }

    /**
     * 查询
     *
     * @param id 获奖id
     * @return 详情页
     */
    @RequestMapping("awardInfo")
    public ModelAndView awardInfo(Long id) {
        ModelAndView mv = new ModelAndView("/admin/result/award_info");
        try {
            Map<String, String> map = keYanHessianService.findAwardById(id);
            mv.addObject("map", map);
        } catch (Exception e) {
            logger.error("ScientificController.awardInfo", e);
        }
        return mv;
    }


}
