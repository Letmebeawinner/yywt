package com.keyanzizheng.controller.result;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.google.gson.reflect.TypeToken;
import com.keyanzizheng.biz.common.BaseHessianBiz;
import com.keyanzizheng.biz.common.OAHessianService;
import com.keyanzizheng.common.StudentHessianService;
import com.keyanzizheng.constant.PaginationConstants;
import com.keyanzizheng.constant.ReportAuditConstants;
import com.keyanzizheng.constant.ResultTypeConstants;
import com.keyanzizheng.entity.oa.Archive;
import com.keyanzizheng.entity.research.ResearchReport;
import com.keyanzizheng.entity.user.SysUser;
import com.keyanzizheng.utils.HessianUtil;
import com.keyanzizheng.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.a_268.base.constants.ErrorCode.*;
import static com.keyanzizheng.constant.StatusConstants.DONE;
import static com.keyanzizheng.constant.StatusConstants.NEGATE;

/**
 * 调研报告
 *
 * @author YaoZhen
 * @date 11-14, 15:33, 2017.
 */
@Controller
@RequestMapping("/admin/ky/rr")
public class ResearchReportController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ResearchReportController.class);
    /**
     * 初次审批
     */
    private static final int FIRST_TIME = 1;
    /**
     * 二次审批
     */
    private static final int SECOND_TIME = 2;
    /**
     * 当前线程的req
     */
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private StudentHessianService studentHessianService;
    @Autowired
    private OAHessianService oaHessianService;
    @Autowired
    private BaseHessianBiz baseHessianBiz;

    /**
     * 数据绑定到实体
     *
     * @param binder 自定义数据类型绑定
     */
    @InitBinder("archive")
    public void archiveInit(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("archive.");
    }

    /**
     * 数据绑定到实体
     *
     * @param binder 自定义数据类型绑定
     */
    @InitBinder("researchReport")
    public void researchReportInit(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("researchReport.");
    }

    /**
     * 科研&&咨政 调研报告列表
     * @param pagination 分页
     * @param resultType 调研报告类型 1:科研 2:咨政
     * @param resultAudit 审批状态 0:未审批 1:初审同意 2:初审拒绝 3:复审同意 4:复审拒绝
     * @param name 填写人名称
     * @param peopleId 教职工id
     * @return 调研报告列表 调研报告初审列表 调研报告二审列表
     */
    @RequestMapping("listResearchReport")
    public ModelAndView listResearchReport(@ModelAttribute("pagination") Pagination pagination,
                                           @RequestParam("resultType") Integer resultType,
                                           @RequestParam(required = false) Integer resultAudit,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) Long peopleId) {
        ModelAndView mv = new ModelAndView("/researchreport/list_research_report");
        try {
            pagination.setPageSize(PaginationConstants.PAGE_SIZE);
            // 未入库 未归档 未删除
            ResearchReport hessianReport = new ResearchReport();
            hessianReport.setPeopleName(name);
            hessianReport.setPeopleId(peopleId);
            hessianReport.setAudit(resultAudit);
            hessianReport.setApprovalDepartment(resultType);
            hessianReport.setStorage(NEGATE);
            hessianReport.setArchive(NEGATE);
            hessianReport.setStatus(NEGATE);
            Map<String, String> result = studentHessianService.listResearchReportByObj(pagination, gson.toJson(hessianReport));
            String listJson = result.get("list");
            List<ResearchReport> rqList = gson.fromJson(listJson, new TypeToken<List<ResearchReport>>() {
            }.getType());
            mv.addObject("rqList", rqList);

            // 环回
            String paginationJson = result.get("pagination");
            pagination = gson.fromJson(paginationJson, Pagination.class);
            pagination.setRequest(request);
            mv.addObject("pagination", pagination);

            mv.addObject("name", name);
            mv.addObject("peopleId", peopleId);
            mv.addObject("resultType", resultType);


            // 咨政区分审批列表和普通列表
            if (resultAudit != null) {
                // 未审核课程列表
                if (resultAudit == ReportAuditConstants.PENDING_APPROVAL) {
                    mv.setViewName("researchreport/first_list_research_report");
                }
                // 通过初审列表
                if (resultAudit == ReportAuditConstants.THROUGH_FIRST_INSTANCE) {
                    mv.setViewName("researchreport/review_list_research_report");
                }
            }

        } catch (Exception e) {
            logger.error("ResearchReportController.listResearchReport", e);
            super.setErrorPath(request, e);
        }
        return mv;
    }

    /**
     * 更改审批状态
     *
     * @return json
     */
    @RequestMapping("audit")
    @ResponseBody
    public Map<String, Object> audit(@ModelAttribute("researchReport") ResearchReport researchReport) {
        Map<String, Object> json;
        try {
            //过审
            Integer flag = studentHessianService.updateResearchReport(gson.toJson(researchReport));
            if (flag > 0) {
                json = this.resultJson(SUCCESS, SUCCESS_MSG, null);
            } else {
                json = this.resultJson(ERROR_PARAMETER_VERIFY, SYS_ERROR_MSG, null);
            }
        } catch (Exception e) {
            logger.error("ResearchReportController.audit", e);
            json = this.resultJson(ERROR_SYSTEM, SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 查看详情
     *
     * @param id 调查报告id
     * @param approval 1: 初次审批 2:二次审批
     * @return 详情页
     */
    @RequestMapping("detailResearchReport")
    public ModelAndView detailResearchReport(@RequestParam("id") Long id,
                                             @RequestParam(value = "approval", required = false) Integer approval) {
        ModelAndView mv = new ModelAndView("/researchreport/query_researchReport");
        try {
            // 字节流
            byte[] byId = HessianUtil.serialize(id);
            byte[] result = studentHessianService.findResearchReportById(byId);
            Object report = HessianUtil.deserialize(result);
            mv.addObject("researchReport", ObjectUtils.objToMap(report));

            // approval不为空进入审批页面
            if (approval != null) {
                mv.setViewName(jumpView(report, approval));
            }
        } catch (IOException e) {
            logger.error("ResearchReportController.detailResearchReport", e);
        }
        return mv;
    }

    /**
     * 新增记录修改时间
     * 新增评级
     * 新增查重率
     * 新增复审查重率
     *
     * @param report   学员处的调研报告
     * @param approval 审批状态
     * @return 应该进入的页面
     */
    private String jumpView(Object report, Integer approval) {
        ResearchReport researchReport = gson.fromJson(gson.toJson(report), ResearchReport.class);
        Integer rt = researchReport.getApprovalDepartment();

        if (rt != null && rt == ResultTypeConstants.KE_YAN) {
            // 初次审批记录修改时间
            if (approval == FIRST_TIME) {
                return "/researchreport/first_researchReport";
            }
            // 二次审批需要评级
            if (approval == SECOND_TIME) {
                return "/researchreport/second_researchReport";
            }
        }

        // 咨政的调研报告新增查重率
        if (rt != null && rt == ResultTypeConstants.ZI_ZHENG) {
            if (approval == FIRST_TIME) {
                return "/researchreport/first_researchReport_zz";
            }
            if (approval == SECOND_TIME) {
                return "/researchreport/second_researchReport_zz";
            }
        }

        return "/researchreport/query_researchReport";
    }

    /**
     * 入库
     *
     * @param rpId 调研报告id
     * @return json
     */
    @RequestMapping("/doStorage")
    @ResponseBody
    public Map<String, Object> doStorage(Long rpId) {
        Map<String, Object> objMap;
        try {
            ResearchReport researchReport = new ResearchReport();
            researchReport.setId(rpId);
            researchReport.setStorage(DONE);
            Integer romNum = studentHessianService.updateResearchReport(gson.toJson(researchReport));
            if (romNum > 0) {
                objMap = this.resultJson(SUCCESS, SUCCESS_MSG, null);
            } else {
                objMap = this.resultJson(ERROR_DATA, "未做出任何修改", null);
            }
        } catch (Exception e) {
            logger.error("ResearchReportController.doStorage", e);
            objMap = this.resultJson(ERROR_SYSTEM, SYS_ERROR_MSG, null);
        }
        return objMap;
    }

    /**
     * approvalDepartment:1 & isArchive :0  科研调研报告库
     * approvalDepartment:1 & isArchive :1  科研调研报告档案列表
     * approvalDepartment:2 & isArchive :0  {生态所}调研报告库
     *
     * @param model 对象模型
     * @param pagination 分页
     * @param approvalDepartment 审批部门 1:科研 2:咨政
     * @param isArchive 是否归档
     * @param name 查询条件
     * @return <b>科研的调研列表 或者 咨政的调研列表</b>
     */
    @RequestMapping("researchReportLibrary")
    public String researchReportLibrary(Model model,
                                        @ModelAttribute("pagination") Pagination pagination,
                                        @RequestParam("resultType") Integer approvalDepartment,
                                        @RequestParam("isArchive") Integer isArchive,
                                        @RequestParam(required = false) String name) {
        try {
            pagination.setPageSize(PaginationConstants.PAGE_SIZE);

            ResearchReport hessianReport = new ResearchReport();
            hessianReport.setPeopleName(name);
            hessianReport.setApprovalDepartment(approvalDepartment);
            hessianReport.setStorage(DONE);
            hessianReport.setArchive(isArchive);
            hessianReport.setStatus(NEGATE);
            Map<String, String> result = studentHessianService.listResearchReportByObj(pagination, gson.toJson(hessianReport));
            String listJson = result.get("list");
            List<ResearchReport> rqList = gson.fromJson(listJson, new TypeToken<List<ResearchReport>>() {
            }.getType());
            model.addAttribute("rqList", rqList);

            // 环回
            String paginationJson = result.get("pagination");
            pagination = gson.fromJson(paginationJson, Pagination.class);
            // 环回参数
            pagination.setRequest(request);
            model.addAttribute("pagination", pagination);

            // 回显查询条件
            if (StringUtil.isNotBlank(name)) {
                model.addAttribute("name", name);
            }
            if (isArchive != null) {
                model.addAttribute("isArchive", isArchive);
            }
            model.addAttribute("resultType", approvalDepartment);
        } catch (Exception e) {
            logger.error("ResearchReportController.researchReportLibrary", e);
        }

        if (approvalDepartment == ResultTypeConstants.ZI_ZHENG) {
            return "researchreport/library_research_report_zz";
        }
        return "researchreport/library_research_report";
    }

    /**
     * 归档
     *
     * @param rqId 调研报告id
     * @return 归档页面
     */
    @RequestMapping("researchReportFiled")
    public String researchReportFiled(Long rqId, Model model) {
        try {
            List<Map<String, String>> linkedHashMapList = oaHessianService.listArchiveType();
            model.addAttribute(linkedHashMapList);

            model.addAttribute("rqId", rqId);
        } catch (Exception e) {
            logger.error("ResearchReportController.researchReportFiled", e);
        }
        return "researchreport/filed_research_report";
    }

    /**
     * 添加
     *
     * @param archive 归档
     * @return json
     */
    @RequestMapping("doSaveArchive")
    @ResponseBody
    public Map<String, Object> doSaveArchive(@ModelAttribute("archive") Archive archive,
                                             Long rqId) {
        Map<String, Object> hashMap;
        try {
            SysUser department = baseHessianBiz.querySysUserById(SysUserUtils.getLoginSysUserId(request));
            Long departmentId = department.getDepartmentId();
            archive.setDepartId(departmentId);
            archive.setStockFlag(NEGATE);
            Long archiveId = oaHessianService.saveArchive(gson.toJson(archive));

            // 级联调研报告
            ResearchReport researchReport = new ResearchReport();
            researchReport.setId(rqId);
            researchReport.setArchive(DONE);
            researchReport.setOaArchiveId(archiveId);
            studentHessianService.updateResearchReport(gson.toJson(researchReport));
            hashMap = this.resultJson(SUCCESS, SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("ResearchReportController.doSaveArchive", e);
            hashMap = this.resultJson(ERROR_SYSTEM, SYS_ERROR_MSG, null);
        }
        return hashMap;
    }


    /**
     * 查看科研档案
     *
     * @param oaArchiveId oa档案id
     * @return hashMap
     */
    @RequestMapping("detailArchive")
    public ModelAndView detailArchive(Long oaArchiveId) {
        ModelAndView mv = new ModelAndView("/researchreport/info_research_report");
        try {
            List<Map<String, String>> linkedHashMapList = oaHessianService.listArchiveType();
            mv.addObject("linkedHashMapList", linkedHashMapList);

            Map<String, String> linkedHashMap = oaHessianService.findArchiveById(oaArchiveId);
            mv.addObject("archive", linkedHashMap);
        } catch (Exception e) {
            logger.error("ResearchReportController.detailArchive", e);
        }
        return mv;
    }

    /**
     * 删除
     *
     * @param id 调研报告id
     * @return json
     */
    @RequestMapping("/removeResearchReport")
    @ResponseBody
    public Map<String, Object> removeResearchReport(Long id) {
        Map<String, Object> objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        try {
            Boolean flag = studentHessianService.removeReportById(id);
            if (flag) {
                objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            }
        } catch (Exception e) {
            logger.error("ResearchReportController.removeResearchReport", e);
        }
        return objMap;
    }
}
