package com.keyanzizheng.controller.result;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.*;
import com.google.gson.JsonSyntaxException;
import com.keyanzizheng.biz.award.AwardBiz;
import com.keyanzizheng.biz.category.CategoryBiz;
import com.keyanzizheng.biz.common.BaseHessianBiz;
import com.keyanzizheng.biz.employee.EmployeeBiz;
import com.keyanzizheng.biz.result.ResultBiz;
import com.keyanzizheng.biz.result.ResultFormBiz;
import com.keyanzizheng.biz.result.TaskChangeBiz;
import com.keyanzizheng.biz.result.TaskEmployeeBiz;
import com.keyanzizheng.biz.subsection.SubSectionBiz;
import com.keyanzizheng.common.EcologicalCivilizationConstants;
import com.keyanzizheng.constant.ApprovalStatusConstants;
import com.keyanzizheng.constant.DeptHeadMapConstants;
import com.keyanzizheng.entity.award.Award;
import com.keyanzizheng.entity.category.Category;
import com.keyanzizheng.entity.employee.Employee;
import com.keyanzizheng.entity.result.*;
import com.keyanzizheng.entity.subsection.SubSection;
import com.keyanzizheng.entity.user.SysUser;
import com.keyanzizheng.utils.BeanUtil;
import com.keyanzizheng.utils.FileExportImportUtil;
import com.keyanzizheng.utils.StringUtil;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.a_268.base.constants.ErrorCode.SUCCESS;
import static com.a_268.base.constants.ErrorCode.SYS_ERROR_MSG;
import static com.keyanzizheng.constant.PaginationConstants.PAGE_SIZE;
import static com.keyanzizheng.constant.ResultFormConstants.*;
import static com.keyanzizheng.constant.ResultTypeConstants.KE_YAN;
import static com.keyanzizheng.constant.ResultTypeConstants.ZI_ZHENG;
import static com.keyanzizheng.constant.StatusConstants.DONE;
import static com.keyanzizheng.constant.StatusConstants.NEGATE;

/**
 * Created by 268 on 2016/12/6.
 */
@Controller
@RequestMapping("/admin/ky")
public class ResultController extends BaseController {

    /**
     * URL标识
     */
    private static final int KE_YAN_ID = 1;
    /**
     * URL标识
     */
    private static final int ZI_ZHENG_ID = 2;

    private static Logger logger = LoggerFactory.getLogger(ResultController.class);
    @Autowired
    private ResultBiz resultBiz;
    @Autowired
    private TaskEmployeeBiz taskEmployeeBiz;
    @Autowired
    private TaskChangeBiz taskChangeBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private EmployeeBiz employeeBiz;
    @Autowired
    private ResultFormBiz resultFormBiz;
    @Autowired
    private CategoryBiz categoryBiz;
    @Autowired
    private SubSectionBiz subSectionBiz;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private AwardBiz awardBiz;


    @InitBinder("result")
    public void initResult(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("result.");
    }

    @InitBinder("employee")
    public void initEmployee(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("employee.");
    }

    @InitBinder("resultForm")
    public void initResultForm(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("resultForm.");
    }

    @InitBinder("taskEmployee")
    public void inittaskEmployee(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("taskEmployee.");
    }

    @InitBinder("queryResult")
    public void initqueryResult(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("queryResult.");
    }


    /**
     * 跳转成果添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddResult")
    public ModelAndView toAddResult(HttpServletRequest request, @ModelAttribute("result") Result result) {
        ModelAndView mv = new ModelAndView();
        try {

            List<ResultForm> resultFormList = resultFormBiz.getResultFormList(new ResultForm());
            String nowDate = DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            mv.addObject("nowDate", nowDate);
            mv.addObject("resultFormList", resultFormList);

            Long sysUserId = SysUserUtils.getLoginSysUserId(request);
            // 查询处室
            List<SubSection> subSectionList = subSectionBiz.querySubSectionListBySysUserId(sysUserId);
            mv.addObject("subSectionList", subSectionList);

            if (result.getResultType() == KE_YAN) {
                mv.setViewName("/result/result_add");
            }
            if (result.getResultType() == ZI_ZHENG) {
                mv.setViewName("/reconstruction/result_zz_add");
            }

        } catch (Exception e) {
            logger.error("toAddResult", e);
            mv.setViewName(super.setErrorPath(request, e));
        }
        return mv;
    }

    /**
     * 添加成果
     *
     * @param request
     * @param result
     * @return
     */
    @RequestMapping("/addResult")
    @ResponseBody
    public Map<String, Object> addResult(HttpServletRequest request, @ModelAttribute Result result) {
        Map<String, Object> objectMap;
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            // 用户类型：1 管理员 2 教职工 3 学员 4 驾驶员  5 单位报名 6 物业员工
            Long linkId = baseHessianBiz.queryEmployeeIdBySysUserId(userId);
            if (ObjectUtils.isNotNull(linkId)) {
                result.setEmployeeId(linkId);
            }
            result.setSysUserId(userId);
            result.setStatus(1);
            result.setIntoStorage(1L);
            Integer subSectionId = DeptHeadMapConstants.deptMap.get(userId);
            if (ObjectUtils.isNotNull(subSectionId)) {
                result.setPassStatus(2);
            } else {
                result.setPassStatus(1);
            }

            //成果登记直接状态为4
            if(Integer.valueOf(request.getParameter("type")).intValue() == 2){
                result.setPassStatus(4);
            }

            result.setIfFile(NEGATE);
            resultBiz.addResult(result);

            TaskChange taskChange = new TaskChange();
            taskChange.setTaskId(result.getId());
            taskChange.setOperate("课题申报成果");
            taskChange.setStatus(1);
            taskChangeBiz.save(taskChange);
            objectMap = this.resultJson(SUCCESS, "添加成功", result);
        } catch (Exception e) {
            logger.error("addResult", e);
            objectMap = this.resultJson(SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 删除成果
     *
     * @return
     */
    @RequestMapping("/deleteResult")
    @ResponseBody
    public Map<String, Object> addResult(@RequestParam(value = "id") Long id) {
        Map<String, Object> objectMap;
        try {
            Result result = resultBiz.findById(id);
            result.setStatus(2);
            resultBiz.update(result);
            objectMap = this.resultJson(SUCCESS, "删除成功", result);
        } catch (Exception e) {
            logger.error("addResult", e);
            objectMap = this.resultJson(SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 成果详情
     *
     * @param request
     * @return
     */
    @RequestMapping("/getResultInfo")
    public ModelAndView getResultInfo(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/result/result_info");
        try {
            QueryResult result = resultBiz.getResultById(id);
            List<ResultForm> resultFormList = resultFormBiz.getResultFormList(new ResultForm());
            modelAndView.addObject("resultFormList", resultFormList);
            modelAndView.addObject("result", result);
            String nowDate = DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            modelAndView.addObject("nowDate", nowDate);

            Long sysUserId = SysUserUtils.getLoginSysUserId(request);
            // 查询处室
            List<SubSection> subSectionList = subSectionBiz.querySubSectionListBySysUserId(sysUserId);
            modelAndView.addObject("subSectionList", subSectionList);
        } catch (Exception e) {
            logger.error("ResultController.getResultInfo", e);
        }
        return modelAndView;
    }

    /**
     * 成果详情
     *
     * @param request
     * @param queryResult
     * @return
     */
    @RequestMapping("/getResultInfoByResult")
    public ModelAndView getResultInfoByResult(HttpServletRequest request, @ModelAttribute("queryResult") QueryResult queryResult) {
        ModelAndView modelAndView = new ModelAndView("/result/result_info");
        try {
            String nowDate = DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            modelAndView.addObject("nowDate", nowDate);
            List<QueryResult> resultList = resultBiz.getResultList(null, queryResult);
            if (!CollectionUtils.isEmpty(resultList)) {
                queryResult = resultList.get(0);
            }
            modelAndView.addObject("result", queryResult);
        } catch (Exception e) {
            logger.error("ResultController.getResultInfoByResult", e);
        }
        return modelAndView;
    }

    /**
     * 去修改页面
     *
     * @return
     */
    @RequestMapping("/toUpdateResult")
    public ModelAndView toUpdateResult(@RequestParam("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/result/result_update");
        try {
            Result result = resultBiz.findById(id);
            modelAndView.addObject("result", result);
            String nowDate = DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            modelAndView.addObject("nowDate", nowDate);

            // 二级
            List<Category> categoryList = categoryBiz.find(null,
                    "resultFormId = " + result.getResultForm() + " order by sort desc");
            modelAndView.addObject("categoryList", categoryList);

            Long sysUserId = SysUserUtils.getLoginSysUserId(request);
            // 查询处室
            List<SubSection> subSectionList = subSectionBiz.querySubSectionListBySysUserId(sysUserId);
            modelAndView.addObject("subSectionList", subSectionList);
        } catch (Exception e) {
            logger.error("toUpdateResult", e);
        }
        return modelAndView;
    }

    /**
     * 科研成果审批核心
     *
     * @param result 成果
     * @return 包含申请人名称的拓展类
     */
    @RequestMapping("/updateResult")
    @ResponseBody
    public Map<String, Object> updateResult(HttpServletRequest request, @ModelAttribute("result") Result result) {
        Map<String, Object> objectMap;
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            resultBiz.updatePassStatusRecard(result, userId);
            QueryResult queryResult = resultBiz.getResultById(result.getId());
            objectMap = this.resultJson(SUCCESS, "修改成功", queryResult);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updateResult", e);
            objectMap = this.resultJson(SYS_ERROR_MSG, "系统错误，请稍后再操作", result);
        }
        return objectMap;
    }

    /**
     * 成果列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getTaskResultList")
    public ModelAndView getTaskResultList(HttpServletRequest request,
                                          @ModelAttribute("pagination") Pagination pagination,
                                          @ModelAttribute("queryResult") QueryResult queryResult) {
        ModelAndView mv = new ModelAndView("/result/result_task_list");
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            SysUser sysUser = baseHessianBiz.querySysUserById(userId);
            if (sysUser.getUserType() == 2) {
                queryResult.setEmployeeId(sysUser.getLinkId());
            }
            queryResult.setResultForm(3);
            String queryTime = request.getParameter("queryYear");
            if (!StringUtils.isTrimEmpty(queryTime)) {
                Date storageTime = DateUtils.parse(queryTime, "yyyy");
                queryResult.setStorageTime(storageTime);
            }

            // 判断用户是否属于科研角色
            Long userRoleId = baseHessianBiz.queryUserRoleByUserId(userId);
            // 不同的用户类型 查询语句的DepartmentId标识不同
            if (userRoleId != null) {
                queryResult.setRoleId(userRoleId.intValue());
            }

            pagination.setRequest(request);
            pagination.setPageSize(PAGE_SIZE);

            //1:未删除 2:已删除
            queryResult.setStatus(1);
            List<QueryResult> resultList = resultBiz.getResultList(pagination, queryResult);
            if (!CollectionUtils.isEmpty(resultList)) {
                mv.addObject("resultList", resultList);
            }

            // 权限里靠url里的&queryResult.resultType=2区分咨政和科研
            if (queryResult.getResultType() == KE_YAN) {
                mv.setViewName("result/result_task_list");
            }
            if (queryResult.getResultType() == ZI_ZHENG) {
                mv.setViewName("result/result_task_zz_list");
            }

        } catch (Exception e) {
            logger.error("getResultList", e);
            mv.setViewName(super.setErrorPath(request, e));
        }
        return mv;
    }

    /**
     * 初始化成果类型
     */
    @RequestMapping("/toAddResultForm")
    public ModelAndView toAddResultForm() {
        ModelAndView modelAndView = new ModelAndView("/result/resultForm_add");
        try {
            String nowDate = DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            modelAndView.addObject("nowDate", nowDate);
        } catch (Exception e) {
            logger.error("toAddResultForm", e);
        }
        return modelAndView;
    }

    /**
     * 跳转成果类型
     */
    @RequestMapping("/toUpdateResultForm")
    public ModelAndView toUpdateResultForm(HttpServletRequest request, @RequestParam("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/result/resultForm_update");
        try {
            ResultForm resultForm = resultFormBiz.getResultFormById(id);
            modelAndView.addObject("resultForm", resultForm);
        } catch (Exception e) {
            logger.error("toUpdateResultForm", e);
        }
        return modelAndView;
    }

    /**
     * 添加成果类型
     *
     * @param request
     * @param resultForm
     */
    @RequestMapping("/addResultForm")
    @ResponseBody
    public Map<String, Object> addResultForm(HttpServletRequest request, ResultForm resultForm) {
        Map<String, Object> objectMap = null;
        try {
            if (resultForm.getId() != null) {
                resultFormBiz.updateResultForm(resultForm);
                objectMap = this.resultJson(SUCCESS, "修改成功", resultForm);
            } else {
                resultFormBiz.addResultForm(resultForm);
                objectMap = this.resultJson(SUCCESS, "添加成功", resultForm);
            }
        } catch (Exception e) {
            logger.error("addResult", e);
            objectMap = this.resultJson(SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 查询成果形式类型
     *
     * @param request
     * @param resultForm
     */
    @RequestMapping("/getResultFormList")
    public ModelAndView getResultFormList(HttpServletRequest request, @ModelAttribute("resultForm") ResultForm resultForm) {
        ModelAndView modelAndView = new ModelAndView("/result/resultForm_list");
        try {
            List<ResultForm> resultFormList = resultFormBiz.getResultFormList(resultForm);
            request.setAttribute("resultFormList", resultFormList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    /**
     * 科研 或生态所的成果 列表
     *
     * @param pagination  分页
     * @param queryResult 扩展类 主要包含申请人姓名
     * @param storageYear 入库年份
     * @param addTime     课题成果 增加开始时间查询
     * @param endTime     课题成果 增加结束时间查询
     * @return 成果列表
     */
    @RequestMapping("/getResultList")
    public ModelAndView getResultList(@ModelAttribute("pagination") Pagination pagination,
                                      @ModelAttribute("queryResult") QueryResult queryResult,
                                      @RequestParam(value = "storageYear", required = false) String storageYear,
                                      @RequestParam(value = "addTime", required = false) String addTime,
                                      @RequestParam(value = "endTime", required = false) String endTime) {
        ModelAndView mv = new ModelAndView("/result/result_list");
        try {

            // 只查询未归档的成果
            queryResult.setIfFile(NEGATE);

            // 教职工只能看到自己提交的课题
            SysUser sysUser = baseHessianBiz.querySysUserById(SysUserUtils.getLoginSysUserId(request));

            // 这里的问题是如果科研处的人又是教职工, 就没法看到所有的课题了
            // 排除掉所有科研处的教职工
            Long kyRole = baseHessianBiz.queryUserRoleByUserId(sysUser.getId());
            if (new Long(0).equals(kyRole) && sysUser.getUserType() == 2) {
                queryResult.setEmployeeId(sysUser.getLinkId());
            }


            if (ObjectUtils.isNull(queryResult.getResultForm())) {
                queryResult.setResultForm(1);
            }

            if (queryResult.getResultForm() != PAPER &&
                    queryResult.getResultForm() != INTERNAL_PUBLICATION) {
                queryResult.setPublish("");
            }


            Integer resultType = queryResult.getResultType();
            // 科研系统查询内容为课题的成果时, 只查询结项的成果
            if (queryResult.getResultForm() == QUESTION) {
                if (resultType == KE_YAN) {
                    // 结项
                    queryResult.setPassStatus(ApprovalStatusConstants.FINISH);
                }
            }

            // 咨政系统只查询结项课题
            if (resultType == ZI_ZHENG) {
                queryResult.setResultForm(QUESTION);
                queryResult.setPassStatus(EcologicalCivilizationConstants.CONFIRM_FILE);
                mv.setViewName("/result/result_zz_list");
            }

            // 入库年份查询
            if (!StringUtils.isTrimEmpty(storageYear)) {
                Date storageTime = DateUtils.parse(storageYear, "yyyy");
                queryResult.setStorageTime(storageTime);
                mv.addObject("storageYear", storageYear);
            }

            // 增加日期查询
            dateQuery(queryResult, addTime, endTime, mv);

            pagination.setRequest(request);
            pagination.setPageSize(PAGE_SIZE);
            List<QueryResult> resultList = resultBiz.getResultList(pagination, queryResult);
            if (!CollectionUtils.isEmpty(resultList)) {
                mv.addObject("resultList", resultList);
            }
            List<ResultForm> resultFormList = resultFormBiz.getResultFormList(new ResultForm());
            mv.addObject("resultFormList", resultFormList);

            Long sysUserId = SysUserUtils.getLoginSysUserId(request);
            // 查询处室
            List<SubSection> subSectionList = subSectionBiz.querySubSectionListBySysUserId(sysUserId);
            mv.addObject("subSectionList", subSectionList);

            // 新增成果类型
            List<Category> categoryList = categoryBiz.find(null, "resultFormId = " + queryResult.getResultForm());
            mv.addObject("categoryMap",
                    categoryList.stream().collect(Collectors.toMap(e -> e.getId().intValue(), Category::getName)));
        } catch (Exception e) {
            logger.error("getResultList", e);
            mv.setViewName(super.setErrorPath(request, e));
        }
        return mv;
    }

    private void dateQuery(QueryResult queryResult,
                           String addTime, String endTime, ModelAndView mv) throws ParseException {
        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtil.isNotBlank(addTime)) {
            Date addDate = smf.parse(addTime);
            queryResult.setAddTime(addDate);
            mv.addObject("addDate", addDate);
        }
        if (StringUtil.isNotBlank(endTime)) {
            Date endDate = smf.parse(endTime);
            queryResult.setEndTime(endDate);
            mv.addObject("endDate", endDate);
        }
    }


    /**
     * 去导入档案
     *
     * @param request
     * @return
     */
    @RequestMapping("/importResultArchive")
    public ModelAndView importResultArchive(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/result/import_result_archive");
        return modelAndView;
    }


    /**
     * 导入页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/importResult")
    public ModelAndView getResultList(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/result/import_result");
        return modelAndView;
    }


    @RequestMapping("/importResults")
    public String importExcels(HttpServletRequest request, @RequestParam("myFile") MultipartFile myFile) {
        try {
            logger.info("myFile:" + myFile.getName());
            String resultMsg = importResults(myFile, request);
            request.setAttribute("msg", resultMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/ky/getResultList.json?queryResult.resultType=1";
    }


    public String importResults(MultipartFile myFile, HttpServletRequest request) throws Exception {
        String msg = "";
        // datalist拼装List<String[]> datalist,
        HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
        HSSFSheet sheet = wookbook.getSheetAt(0);
        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+

        for (int i = 1; i <= rows + 1; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null) {
                String name = getCellValue(row.getCell((short) 0));//
                String resultFrom = getCellValue(row.getCell((short) 1));//
                String resultType = getCellValue(row.getCell((short) 2));//
                String publish = getCellValue(row.getCell((short) 3));//
                String publishNumber = getCellValue(row.getCell((short) 4));//
                String workName = getCellValue(row.getCell((short) 5));//
                String publishTime = getCellValue(row.getCell((short) 6));//

                if (StringUtils.isEmpty(name)) {
                    msg += "第" + i + "行为空<br/>";
                    continue;
                }
                if (StringUtils.isEmpty(resultFrom)) {
                    msg += "第" + i + "行为空<br/>";
                    continue;
                }
                if (StringUtils.isEmpty(resultType)) {
                    msg += "第" + i + "行为空<br/>";
                    continue;
                }
                if (StringUtils.isEmpty(publish)) {
                    msg += "第" + i + "行为空<br/>";
                    continue;
                }
                if (StringUtils.isEmpty(publishNumber)) {
                    msg += "第" + i + "行为空<br/>";
                    continue;
                }
                if (ObjectUtils.isNull(workName)) {
                    msg += "第" + i + "行为空<br/>";
                    continue;
                }
                if (ObjectUtils.isNull(publishTime)) {
                    msg += "第" + i + "行为空<br/>";
                    continue;
                }

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Result result = new Result();
                result.setSysUserId(SysUserUtils.getLoginSysUserId(request));
                result.setName(name);
                result.setPublish(publish);
                result.setPublishNumber(publishNumber);
                result.setWorkName(workName);
                result.setPublishTime(format.parse(publishTime));
                result.setStorageTime(new Date());
                result.setResultForm(Integer.parseInt(resultFrom));
                result.setResultType(Integer.parseInt(resultType));
                result.setStatus(1);
                resultBiz.addResult(result);
            }
        }
        return msg;
    }


    /**
     * 未审批成果列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/toResultApprovalList")
    public ModelAndView toResultApprovalList(HttpServletRequest request,
                                             @ModelAttribute("pagination") Pagination pagination,
                                             @ModelAttribute("queryResult") QueryResult queryResult) {
        ModelAndView modelAndView = new ModelAndView("/result/result_approval_list");
        try {
            Long sysUserId = SysUserUtils.getLoginSysUserId(request);
            // 除了科研处领导宁宁,  其他部门领导只能看到自己处室的
            setLeaderStatus(queryResult, sysUserId);
            if (ObjectUtils.isNull(queryResult.getResultForm())) {
                queryResult.setResultForm(3);
            }
            pagination.setRequest(request);
            pagination.setPageSize(PAGE_SIZE);
            List<QueryResult> resultList = resultBiz.getResultList(pagination, queryResult);
            if (!CollectionUtils.isEmpty(resultList)) {
                modelAndView.addObject("resultList", resultList);
            }

            if (queryResult.getResultType() == 1) {
                modelAndView.addObject("resultTypeName", "科研处");
            }
            if (queryResult.getResultType() == 2) {
                modelAndView.addObject("resultTypeName", "生态文明所");
            }
            modelAndView.addObject("queryResult", queryResult);

            // 教职工提交结项申请后, 由科研处审批, 通过后结项.
            if (queryResult.getPassStatus() == ApprovalStatusConstants.PASS_LEADER) {
                modelAndView.setViewName("/result/finish_list");
            }

        } catch (Exception e) {
            logger.error("getResultList", e);
        }
        return modelAndView;
    }

    private void setLeaderStatus(@ModelAttribute("queryResult") QueryResult queryResult, Long sysUserId) {
        if (!sysUserId.equals(DeptHeadMapConstants.REAL_LEADER)) {
            Integer subSectionId = DeptHeadMapConstants.deptMap.get(sysUserId);
            if (ObjectUtils.isNotNull(subSectionId)) {
                queryResult.setTeacherResearch(subSectionId);
            }
        }
    }

    /**
     * 去审批页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toApprovalResult")
    public ModelAndView toApprovalResult(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/result/result_approval");
        try {
            QueryResult result = resultBiz.getResultById(id);
            modelAndView.addObject("result", result);

            Long sysUserId = SysUserUtils.getLoginSysUserId(request);
            // 查询处室
            List<SubSection> subSectionList = subSectionBiz.querySubSectionListBySysUserId(sysUserId);
            modelAndView.addObject("subSectionList", subSectionList);
        } catch (Exception e) {
            logger.error("toApprovalResult", e);
        }
        return modelAndView;
    }

    /**
     * 成果库
     *
     * @param request
     * @return
     */
    @RequestMapping("/getResultStorageList")
    public ModelAndView getResultStorageList(HttpServletRequest request,
                                             @ModelAttribute("pagination") Pagination pagination,
                                             @ModelAttribute("queryResult") QueryResult queryResult) {
        ModelAndView modelAndView = new ModelAndView("/result/result_storage_list");
        try {
            if (ObjectUtils.isNull(queryResult.getResultForm())) {
                queryResult.setResultForm(1);
            }
            queryResult.setIntoStorage(2L);
            queryResult.setIfFile(NEGATE);
            // 咨政只显示课程
            if (queryResult.getResultType() != null && queryResult.getResultType() == ZI_ZHENG) {
                queryResult.setResultForm(QUESTION);
                modelAndView.setViewName("/result/result_zz_storage_list");
            }
            pagination.setRequest(request);
            pagination.setPageSize(PAGE_SIZE);
            List<QueryResult> resultList = resultBiz.getResultList(pagination, queryResult);
            if (!CollectionUtils.isEmpty(resultList)) {
                modelAndView.addObject("resultList", resultList);
            }
            List<ResultForm> resultFormList = resultFormBiz.getResultFormList(new ResultForm());
            modelAndView.addObject("resultFormList", resultFormList);
            modelAndView.addObject("queryResult", queryResult);
        } catch (Exception e) {
            logger.error("getTaskList", e);
        }
        return modelAndView;
    }

    /**
     * 成果档案库
     *
     * @param request
     * @return
     */
    @RequestMapping("/getResultFileList")
    public ModelAndView getResultFileList(HttpServletRequest request,
                                          @RequestParam(required = false) String queryYear,
                                          @ModelAttribute("pagination") Pagination pagination,
                                          @ModelAttribute("queryResult") QueryResult queryResult) {
        ModelAndView mv = new ModelAndView("/result/result_file_list");
        try {
            if (ObjectUtils.isNull(queryResult.getResultForm())) {
                queryResult.setResultForm(1);
            }
            Integer resultType = queryResult.getResultType();
            mv.addObject("resultType", resultType);

            if (StringUtil.isNotBlank(queryYear)) {
                Date storageTime = DateUtils.parse(queryYear, "yyyy");
                queryResult.setStorageTime(storageTime);
            }

            // 已归档成果
            queryResult.setIfFile(DONE);

            // 咨政只查询课题
            if (resultType != null && resultType == ZI_ZHENG) {
                queryResult.setResultForm(QUESTION);
                mv.setViewName("/result/result_zz_file_list");
            }
            pagination.setRequest(request);
            pagination.setPageSize(PAGE_SIZE);
            List<QueryResult> resultList = resultBiz.getResultList(pagination, queryResult);
            List<ResultForm> resultFormList = resultFormBiz.getResultFormList(new ResultForm());
            mv.addObject("resultFormList", resultFormList);
            mv.addObject("queryYear", queryYear);
            mv.addObject("queryResult", queryResult);
            if (!CollectionUtils.isEmpty(resultList)) {
                mv.addObject("resultList", resultList);
            }
        } catch (Exception e) {
            logger.error("getTaskList", e);
            mv.setViewName(super.setErrorPath(request, e));
        }
        return mv;
    }

    /**
     * 导出
     *
     * @param request
     * @param response
     * @param pagination
     * @param queryResult
     */
    @RequestMapping("/getResultFileList/export")
    public void userListExport(HttpServletRequest request, HttpServletResponse response,
                               @ModelAttribute("pagination") Pagination pagination,
                               @ModelAttribute("queryResult") QueryResult queryResult) {
        try {
            // 指定文件生成路径
            String dir = request.getSession().getServletContext().getRealPath("/FileList");
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
            String date = df.format(new Date());
            // 文件名
            String expName = "档案信息_" + date;
            // 表头信息
            String[] headName1 = {"档号", "作者", "论文名称", "发表刊物", "刊号", "发表时间"};
            String[] headName2 = {"档号", "著作名称", "出版社", "出版时间", "主编（字数）", "副主编（字数）"};
            String[] headName3 = {"档号", "课题名称", "课题负责人", "字数"};
            if (ObjectUtils.isNull(queryResult.getResultForm())) {
                queryResult.setResultForm(1);
            }
            String queryTime = request.getParameter("queryYear");
            if (!StringUtils.isTrimEmpty(queryTime) && !"0".equals(queryTime)) {
                Date storageTime = DateUtils.parse(queryTime, "yyyy");
                queryResult.setStorageTime(storageTime);
            }
            queryResult.setIfFile(2);
            pagination.setRequest(request);
            List<QueryResult> resultList = resultBiz.getResultList(pagination, queryResult);
            int num = resultList.size();// 总页数
            List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list

            //通过成果类型进行查询对应的列表
            if (queryResult.getResultForm() == 1) {
                List<List<String>> list = userJoint(resultList);
                File file = FileExportImportUtil.createExcel(headName1, list, expName, dir);
                srcfile.add(file);
            }
            if (queryResult.getResultForm() == 2) {
                List<List<String>> list = userJoint1(resultList);
                File file = FileExportImportUtil.createExcel(headName2, list, expName, dir);
                srcfile.add(file);
            }
            if (queryResult.getResultForm() == 3) {
                List<List<String>> list = userJoint2(resultList);
                File file = FileExportImportUtil.createExcel(headName3, list, expName, dir);
                srcfile.add(file);
            }

            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<List<String>> userJoint(List<QueryResult> resultList) throws Exception {
        List<List<String>> list = new ArrayList<List<String>>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (ObjectUtils.isNotNull(resultList)) {
            for (int i = 0; i < resultList.size(); i++) {
                List<String> small = new ArrayList<String>();
                QueryResult queryResult = resultList.get(i);
                small.add((queryResult.getArchiveNo() != null) ? queryResult.getArchiveNo() : "");
                small.add(queryResult.getWorkName() != null ? queryResult.getWorkName() : "");
                small.add(queryResult.getName() != null ? queryResult.getName() : "");
                small.add(queryResult.getPublish() != null ? queryResult.getPublish() : "");
                small.add(queryResult.getPublishNumber() != null ? queryResult.getPublishNumber() : "");
                small.add(queryResult.getPublishTime() != null ? format.format(queryResult.getPublishTime()) : "");
                list.add(small);
            }
        }
        return list;
    }

    public List<List<String>> userJoint1(List<QueryResult> resultList) throws Exception {
        List<List<String>> list = new ArrayList<List<String>>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (ObjectUtils.isNotNull(resultList)) {
            for (int i = 0; i < resultList.size(); i++) {
                List<String> small = new ArrayList<String>();
                QueryResult queryResult = resultList.get(i);
                small.add((queryResult.getArchiveNo() != null) ? queryResult.getArchiveNo() : "");
                small.add(queryResult.getName() != null ? queryResult.getName() : "");
                small.add(queryResult.getPublish() != null ? queryResult.getPublish() : "");
                small.add(queryResult.getPublishTime() != null ? format.format(queryResult.getPublishTime()) : "");
                small.add(queryResult.getWorkName() != null ? queryResult.getWorkName() : "");
                small.add(queryResult.getAssociateEditor() != null ? queryResult.getAssociateEditor() : "");
                list.add(small);
            }
        }
        return list;
    }


    public List<List<String>> userJoint2(List<QueryResult> resultList) throws Exception {
        List<List<String>> list = new ArrayList<List<String>>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (ObjectUtils.isNotNull(resultList)) {
            for (int i = 0; i < resultList.size(); i++) {
                List<String> small = new ArrayList<String>();
                QueryResult queryResult = resultList.get(i);
                small.add((queryResult.getArchiveNo() != null) ? queryResult.getArchiveNo() : "");
                small.add(queryResult.getName() != null ? queryResult.getName() : "");
                small.add(queryResult.getWorkName() != null ? queryResult.getWorkName() : "");
                small.add(queryResult.getWordsNumber() != null ? queryResult.getWordsNumber().toString() : "");
                list.add(small);
            }
        }
        return list;
    }

    /**
     * 课题变更记录
     *
     * @param request
     * @return
     */
    @RequestMapping("/getTaskChangeList")
    public ModelAndView getTaskChangeList(HttpServletRequest request,
                                          @RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView("/result/task_change_list");
        try {
            TaskChange taskChange = new TaskChange();
            taskChange.setTaskId(id);
            List<TaskChange> taskChangeList = taskChangeBiz.getTaskChangeList(taskChange);
            if (!CollectionUtils.isEmpty(taskChangeList)) {
                modelAndView.addObject("taskChangeList", taskChangeList);
            }
        } catch (Exception e) {
            logger.error("getTaskChangeList", e);
        }
        return modelAndView;
    }

    /**
     * 教职工人员选择列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/ajax/result/selectEmployeeList")
    public ModelAndView selectEmployeeList(HttpServletRequest request,
                                           @ModelAttribute("employee") Employee employee) {
        ModelAndView modelAndView = new ModelAndView("/result/select_employee_list");
        try {
            List<Employee> employeeList = employeeBiz.getEmployeeList(null, employee);
            modelAndView.addObject("employeeList", employeeList);
        } catch (Exception e) {
            logger.error("selectEmployeeList", e);
        }
        return modelAndView;
    }

    /**
     * 添加参与课题教职工
     *
     * @param request
     * @param taskEmployee
     * @return
     */
    @RequestMapping("/addTaskEmployee")
    @ResponseBody
    public Map<String, Object> addTaskEmployee(HttpServletRequest request, @ModelAttribute("taskEmployee") TaskEmployee taskEmployee) {
        Map<String, Object> objectMap = null;
        try {
            String employeeIds = request.getParameter("employeeIds");
            employeeIds = employeeIds.trim().replace(' ', ',');
            String[] _employeeIds = employeeIds.split(",");
            for (String employeeId : _employeeIds) {
                TaskEmployee _taskEmployee = new TaskEmployee();
                _taskEmployee.setEmployeeId(Long.valueOf(employeeId));
                _taskEmployee.setTaskId(taskEmployee.getTaskId());
                _taskEmployee.setStatus(1);
                List<TaskEmployee> taskEmployeeList = taskEmployeeBiz.getTaskEmployeeList(_taskEmployee);
                if (CollectionUtils.isEmpty(taskEmployeeList)) {
                    taskEmployeeBiz.save(_taskEmployee);
                }
            }
            objectMap = this.resultJson(SUCCESS, "修改成功", taskEmployee);
        } catch (Exception e) {
            logger.error("addTaskEmployee", e);
            objectMap = this.resultJson(SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 课题人员集合
     *
     * @param request
     * @return
     */
    @RequestMapping("/getResultEmployeeList")
    public ModelAndView getResultEmployeeList(HttpServletRequest request,
                                              @ModelAttribute("pagination") Pagination pagination,
                                              @ModelAttribute("employee") Employee employee
    ) {
        ModelAndView modelAndView = new ModelAndView("/result/result_employee_list");
        try {
            Result result = resultBiz.getResultById(employee.getResultId());
            List<Employee> employeeList = employeeBiz.getEmployeeList(pagination, employee);
            pagination.setRequest(request);
            modelAndView.addObject("employeeList", employeeList);
            modelAndView.addObject("result", result);
        } catch (Exception e) {
            logger.error("getResultEmployeeList", e);
        }
        return modelAndView;
    }

    /**
     * 移除教职工
     *
     * @param request
     * @param taskEmployee
     * @return
     */
    @RequestMapping("/deleteTaskEmployee")
    @ResponseBody
    public Map<String, Object> deleteTaskEmployee(HttpServletRequest request, @ModelAttribute("taskEmployee") TaskEmployee taskEmployee) {
        Map<String, Object> objectMap = null;
        try {
            List<TaskEmployee> taskEmployeeList = taskEmployeeBiz.getTaskEmployeeList(taskEmployee);
            taskEmployee = taskEmployeeList.get(0);
            taskEmployee.setStatus(2);
            taskEmployeeBiz.update(taskEmployee);
            objectMap = this.resultJson(SUCCESS, "移除成功", taskEmployee);
        } catch (Exception e) {
            logger.error("deleteTaskEmployee", e);
            objectMap = this.resultJson(SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objectMap;
    }

    /**
     * 导出成果汇总表
     *
     * @param request
     * @param response
     * @param queryResult
     */
    @RequestMapping("/resultExport")
    public void resultExport(HttpServletRequest request, HttpServletResponse response,
                             @ModelAttribute("queryResult") QueryResult queryResult) {
        try {
            // 指定文件生成路径
            String dir = request.getSession().getServletContext().getRealPath("/excelfile/result");
            String queryTime = request.getParameter("queryYear");
            // 文件名
            String expName = "";
            if (!StringUtils.isTrimEmpty(queryTime)) {
                Date storageTime = DateUtils.parse(queryTime, "yyyy");
                queryResult.setStorageTime(storageTime);
            }
            expName = queryTime + "科研论文统计汇总表";
            if (queryResult.getResultForm() == 2) {
                expName = queryTime + "科研著作统计汇总表";
            }
            if (queryResult.getResultForm() == 3) {
                expName = queryTime + "课题成果统计汇总表";
            }
            // 表头信息
            String[] headName = {"序号", "作者", "论文名称", "发表刊物", "刊号", "发表时间", "字数", "获奖情况", "备注"};
            String[] _headName = {"序号", "著作名称", "出版社", "出版时间", "主编（字数）", "副主编（字数）", "参编人员", "参编章节", "获奖情况", "备注"};
            String[] headName_ = {"序号", "课题负责人", "成果名称", "结项单位", "结项时间", "字数", "课题组成员", "获奖情况", "备注"};

            // 拆分为一万条数据每Excel，防止内存使用太大
            Pagination pagination = new Pagination();
            pagination.setPageSize(1000);
            List<QueryResult> resultList = resultBiz.getResultList(pagination, queryResult);
            int num = pagination.getTotalPages();// 总页数
            List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
            for (int i = 1; i <= num; i++) {// 循环生成num个xls文件
                pagination.setCurrentPage(i);
                List<QueryResult> _resultList = resultBiz.getResultList(pagination, queryResult);
                List<List<String>> list = resultJoint(_resultList, queryResult.getResultForm());
                File file = null;
                if (queryResult.getResultForm() == 2) {
                    file = FileExportImportUtil.createExcel(_headName, list, expName + "_" + i, dir);
                } else if (queryResult.getResultForm() == 1) {
                    file = FileExportImportUtil.createExcel(headName, list, expName + "_" + i, dir);
                } else {
                    file = FileExportImportUtil.createExcel(headName_, list, expName + "_" + i, dir);
                }
                srcfile.add(file);
            }
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 成果excel格式拼接
     *
     * @param resultList
     * @return
     */
    public List<List<String>> resultJoint(List<QueryResult> resultList, Integer type) {
        List<List<String>> list = new ArrayList<List<String>>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < resultList.size(); i++) {
            List<String> small = new ArrayList<String>();
            if (type == 1) {
                small.add((i + 1) + "");
                small.add(resultList.get(i).getWorkName() + "");
                small.add(resultList.get(i).getName() + "");
                small.add(resultList.get(i).getPublish() + "");
                small.add(resultList.get(i).getPublishNumber() + "");
                small.add(DateUtils.format(resultList.get(i).getPublishTime(), "yyyy-MM-dd HH:mm:ss"));
                small.add(resultList.get(i).getWordsNumber() + "");
            } else if (type == 2) {
                small.add((i + 1) + "");
                small.add(resultList.get(i).getName() + "");
                small.add(resultList.get(i).getPublish() + "");
                small.add(DateUtils.format(resultList.get(i).getPublishTime(), "yyyy-MM-dd HH:mm:ss"));
                small.add(resultList.get(i).getWorkName() + "(" + resultList.get(i).getWordsNumber() + "字)");
                small.add(resultList.get(i).getAssociateEditor() + "(" + resultList.get(i).getAssociateNumber() + "字)");
                String workName = "";
                List<Employee> employeeList = resultList.get(i).getEmployeeList();
                if (!CollectionUtils.isEmpty(employeeList)) {
                    for (Employee employee : employeeList) {
                        workName += employee.getName() + ' ';
                    }
                    workName = workName.trim().replace(' ', ',');
                }
                small.add(workName);
                small.add(resultList.get(i).getChapter());
            } else {
                small.add((i + 1) + "");
                small.add(resultList.get(i).getWorkName() + "");
                small.add(resultList.get(i).getName() + "");
                small.add(resultList.get(i).getResultDepartment() + "");
                small.add(resultList.get(i).getWordsNumber() + "");
                small.add(DateUtils.format(resultList.get(i).getDatTime(), "yyyy-MM-dd HH:mm:ss"));
                String workName = "";
                List<Employee> employeeList = resultList.get(i).getEmployeeList();
                if (!CollectionUtils.isEmpty(employeeList)) {
                    for (Employee employee : employeeList) {
                        workName += employee.getName() + ' ';
                    }
                    workName = workName.trim().replace(' ', ',');
                }
                small.add(workName);
            }
            small.add(resultList.get(i).getDigest() + "");
            small.add(resultList.get(i).getRemark() + "");
            list.add(small);
        }
        return list;
    }

    /**
     * 导出工资列表
     *
     * @param request
     * @param response
     */
    @RequestMapping("/resultTaskExport")
    public void resultTaskExport(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 指定文件生成路径
            String dir = request.getSession().getServletContext().getRealPath("/excelfile/result");
            // 文件名
            String expName = "课题模板" + new Date().getTime();
            // 表头信息
            String[] headName = {"序号", "申报人编号", "课题名称", "字数", "结项单位", "课题组成员", "获奖情况", "备注"};
            // 拆分为一万条数据每Excel，防止内存使用太大
            Pagination pagination = new Pagination();
            pagination.setPageSize(10000);
            List<File> srcfile = new ArrayList<File>();// 生成的excel的文件的list
            List<List<String>> list = new ArrayList<>();
            List<String> small = new ArrayList<String>();
            small.add("1");
            small.add("000025");
            small.add("课题名称");
            small.add("30000");
            small.add("科研处");
            small.add("000001,000002,000003");
            small.add("一等奖");
            small.add("干的漂亮");
            list.add(small);
            File file = FileExportImportUtil.createExcel(headName, list, expName, dir);
            srcfile.add(file);
            FileExportImportUtil.createRar(response, dir, srcfile, expName);// 生成的多excel的压缩包
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 执行Excel导入
     *
     * @param request
     * @param myfile
     * @return
     */
    @RequestMapping("/resultImport")
    public ModelAndView importExcel(HttpServletRequest request,
                                    @RequestParam("myFile") MultipartFile myfile) {
        ModelAndView modelandView = new ModelAndView();
        try {
            //记录日常日志
            logger.info("myFile:" + myfile.getName());
            String resultMsg = "";
            String fileName = myfile.getOriginalFilename();
            if (fileName.indexOf(".xls") < 0 && fileName.indexOf(".xlsx") < 0) {
                resultMsg = "文件格式不正确！";
            } else {
                resultMsg = this.updateImportExcel(myfile);
            }
            if (resultMsg != null && resultMsg.trim().trim().length() == 0) {
                modelandView.setViewName("redirect:/admin/rs/getTaskResultList.json");
            } else {
                request.setAttribute("msg", resultMsg);
                modelandView.setViewName("/common/success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelandView;
    }

    /**
     * 导入Excel
     *
     * @param myFile
     * @param
     * @return
     */
    public String updateImportExcel(MultipartFile myFile) throws Exception {
        String msg = "";
        //把获取的xls的数据表读到wookbook中
        HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
        //读取第一页，一般一个Excel文件会有三个工作表，这里获取第一个工作表来进行操作
        HSSFSheet sheet = wookbook.getSheetAt(0);
        //获取一个表最后一条记录的记录号
        int rows = sheet.getLastRowNum();
        //循环遍历表，把每一行的每一个字段遍历出来
        for (int i = 1; i <= rows + 1; i++) {
            //创建一个需要存储导入Excel数据的对象
            Result result = new Result();
            //创建一个行对象
            HSSFRow row = sheet.getRow(i);
            //如果行不为空
            if (row != null) {
                // **读取cell**
                String employeeNo = getCellValue(row.getCell((short) 1)).trim();//教职工编号 Long
                String name = getCellValue(row.getCell((short) 2)).trim();//课题名称
                String wordsNumber = getCellValue(row.getCell((short) 3)).trim();//字数
                String resultDepartment = getCellValue(row.getCell((short) 4)).trim();//结项单位
                String employeeString = getCellValue(row.getCell((short) 5)).trim();//课题组成员
                String digest = getCellValue(row.getCell((short) 6)).trim();//获奖情况
                String remark = getCellValue(row.getCell((short) 7)).trim();//备注
                if (StringUtils.isTrimEmpty(employeeNo)) {
                    msg += "第" + i + "行申报人编号不能为空<br/>";
                    continue;
                } else {
                    SysUser sysUser = employeeBiz.findSysUserByNo(employeeNo);
                    if (ObjectUtils.isNull(sysUser)) {
                        msg += "第" + i + "行未找到编号为" + employeeNo + "的系统用户<br/>";
                        continue;
                    }
                    result.setSysUserId(sysUser.getId());
                }
                if (!StringUtils.isTrimEmpty(name)) {
                    result.setName(name);
                } else {
                    msg += "第" + i + "行名称不能为空<br/>";
                }
                if (!StringUtils.isTrimEmpty(wordsNumber)) {
                    if (!wordsNumber.matches("\\d+(.\\d+)?")) {
                        msg += "第" + i + "行数字格式错误<br/>";
                        continue;
                    }
                    result.setWordsNumber(Long.valueOf(wordsNumber));
                }
                if (!StringUtils.isTrimEmpty(resultDepartment)) {
                    result.setResultDepartment(resultDepartment);
                }

                if (!StringUtils.isTrimEmpty(digest)) {
                    result.setDigest(digest);
                }
                if (!StringUtils.isTrimEmpty(remark)) {
                    result.setRemark(remark);
                }
                QueryResult queryResult = new QueryResult();
                BeanUtil.copyProperties(result, queryResult);
                List<QueryResult> ResultList = resultBiz.getResultList(null, queryResult);
                if (ObjectUtils.isNotNull(ResultList)) {
                    msg += "第" + i + "行数据以存在<br/>";
                    continue;
                }
                result.setStatus(1);
                result.setIntoStorage(1L);
                result.setIfFile(1);
                result.setPassStatus(1);
                result.setResultForm(3);
                resultBiz.save(result);
                if (!StringUtils.isTrimEmpty(employeeString)) {
                    String[] _employeeString = employeeString.trim().split(",");
                    for (String employeeId : _employeeString) {
                        Employee employee = employeeBiz.findEmployeeByEmployeeNo(employeeId);
                        if (ObjectUtils.isNull(employee)) {
                            msg += "第" + i + "行未找到编号为" + employeeId + "的教职工<br/>";
                        } else {
                            TaskEmployee taskEmployee = new TaskEmployee();
                            taskEmployee.setTaskId(result.getId());
                            taskEmployee.setEmployeeId(employee.getId());
                            taskEmployeeBiz.save(taskEmployee);
                        }
                    }
                }
            }
        }
        return msg;
    }

    /**
     * 获得Hsscell内容
     *
     * @param cell
     * @return
     */
    public String getCellValue(HSSFCell cell) {
        String value = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_FORMULA:
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    //判断是否为日期类型
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        //用于转化为日期格式
                        Date d = cell.getDateCellValue();
                        value = DateUtils.format(d, "yyyy-MM-dd HH:mm:ss");
                    } else {
                        DecimalFormat df = new DecimalFormat("0");
                        value = df.format(cell.getNumericCellValue());
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue().trim();
                    break;
                default:
                    value = "";
                    break;
            }
        }
        return value.trim();
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

            QueryResult queryResult = new QueryResult();
            queryResult.setResultForm(3);
            queryResult.setSysUserId(sysUserId);
            queryResult.setResultType(resultType);
            if (yearOrMonthly != null) {
                queryResult.setYearOrMonthly(yearOrMonthly);
            }
            if (resultName != null) {
                queryResult.setName(resultName);
            }
            List<QueryResult> resultList = resultBiz.getResultList(pagination, queryResult);


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
                mv.setViewName("jiaowu/my_result_task_list");
            }
            if (resultType == ZI_ZHENG_ID) {
                mv.setViewName("jiaowu/my_result_zz_task_list");
            }
            mv.addObject("resultType", resultType);
        } catch (Exception e) {
            logger.error("ResultController.myTaskResultList", e);
        }
        return mv;
    }

    /**
     * 查看课题详情
     */
    @RequestMapping("/getResultInfoById")
    public ModelAndView getResultInfoById(@RequestParam("id") Long id) {
        ModelAndView mv = new ModelAndView("/jiaowu/result_info");
        try {
            Result result = resultBiz.getResultById(id);
            mv.addObject("result", result);

            // 形式
            List<ResultForm> resultFormList = resultFormBiz.getResultFormList(new ResultForm());
            mv.addObject("resultFormList", resultFormList);
        } catch (JsonSyntaxException e) {
            logger.error("ResultController.getResultInfoById", e);
        }
        return mv;
    }

    /**
     * 上传课题结项申报
     */
    @RequestMapping("/problemStatementDeclaration")
    public ModelAndView problemStatementDeclaration(@RequestParam("resultId") Long resultId) {
        ModelAndView mv = new ModelAndView("/jiaowu/final_declaration");
        try {
            mv.addObject("resultId", resultId);
        } catch (Exception e) {
            logger.error("ResultController.problemStatementDeclaration", e);
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
            Long sysUserId = SysUserUtils.getLoginSysUserId(request);
            Boolean flag = true;
            Result target = new Result();
            target.setFileUrlDeclaration(fileUrlDeclaration);
            target.setId(resultId);
            Integer i = resultBiz.update(target);
            if (i == 0) {
                flag = false;
            }
            if (flag) {
                objectMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
            } else {
                objectMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
            }
        } catch (Exception e) {
            logger.error("ResultController.addProblemStatementDeclaration", e);
            objectMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return objectMap;
    }


    /**
     * 获奖申报
     */
    @RequestMapping("/awardDeclaration")
    public ModelAndView awardDeclaration() {
        ModelAndView mv = new ModelAndView("/jiaowu/award_declaration");
        try {
        } catch (Exception e) {
            logger.error("ResultController.awardDeclaration", e);
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
            Award award = new Award();
            award.setUserName(userName);
            award.setUserId(Long.valueOf(userId));
            award.setTitle(awardTitle);
            award.setResultForm(resultForm);
            award.setAwardSituation(awardSituation);
            award.setDigest(digest);
            award.setUrl(fileUrlAward);
            awardBiz.save(award);
            objectMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("ResultController.addProblemStatementDeclaration", e);
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
        ModelAndView mv = new ModelAndView("/jiaowu/my_award_declaration");
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            List<Award> awards = awardBiz.find(null, "userId = " + userId);
            mv.addObject("awards", awards);
        } catch (Exception e) {
            logger.error("ResultController.myAwardDeclaration", e);
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
        ModelAndView mv = new ModelAndView("/jiaowu/award_info");
        try {
            Award award = awardBiz.findById(id);
            mv.addObject("map", award);
        } catch (Exception e) {
            logger.error("ResultController.awardInfo", e);
        }
        return mv;
    }

    /**
     * 查看我审批过的课题
     */
    @RequestMapping("/getAlreadyProcessList")
    public ModelAndView getResultInfoById(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination,
                                          @RequestParam("resultType") Integer resultType,
                                          @RequestParam(value = "resultName", required = false) String resultName) {
        ModelAndView mv = new ModelAndView("result/already_process_list");
        try {
            pagination.setPageSize(10);
            Long userId = SysUserUtils.getLoginSysUserId(request);
            String whereSql = " status!=2 and resultType=" + resultType;
            if (ObjectUtils.isNotNull(resultName)) {
                whereSql += " and resultName like '%" + resultName + "%'";
            }
            whereSql += " and operations like '%," + userId + ",%'";
            List<Result> resultList = resultBiz.find(pagination, whereSql);
            mv.addObject("resultList", resultList);
            mv.addObject("resultType", resultType);
            mv.addObject("resultName", resultName);
            mv.addObject("pagination", pagination);

        } catch (JsonSyntaxException e) {
            logger.error("ResultController.getResultInfoById", e);
        }
        return mv;
    }


}
