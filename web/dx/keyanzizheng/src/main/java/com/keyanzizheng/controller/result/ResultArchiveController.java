package com.keyanzizheng.controller.result;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.keyanzizheng.biz.category.CategoryBiz;
import com.keyanzizheng.biz.common.BaseHessianBiz;
import com.keyanzizheng.biz.result.ResultBiz;
import com.keyanzizheng.biz.result.ResultFormBiz;
import com.keyanzizheng.biz.subsection.SubSectionBiz;
import com.keyanzizheng.constant.ApprovalStatusConstants;
import com.keyanzizheng.constant.ResultTypeConstants;
import com.keyanzizheng.entity.category.Category;
import com.keyanzizheng.entity.result.Result;
import com.keyanzizheng.entity.result.ResultForm;
import com.keyanzizheng.entity.subsection.SubSection;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 归档
 *
 * @author YaoZhen
 * @create 11-21, 10:43, 2017.
 */
@Controller
@RequestMapping("/admin/ky/")
public class ResultArchiveController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ResultArchiveController.class);

    /**
     * 入库
     */
    private static final int INTO_STORAGE = 2;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ResultBiz resultBiz;
    @Autowired
    private ResultFormBiz resultFormBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private CategoryBiz categoryBiz;
    @Autowired
    private SubSectionBiz subSectionBiz;

    @InitBinder("result")
    public void initResult(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 允许课题的addTime和endTime为空字符串
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("result.");
    }

    /**
     * 入库
     *
     * @param result 包含自增id 和 已入库状态
     * @return json
     */
    @RequestMapping("/saveArchiveResult")
    @ResponseBody
    public Map<String, Object> saveArchiveResult(@ModelAttribute("result") Result result) {
        Map<String, Object> objectMap;
        Integer resultType = null;
        // 有flag意味着归档
        try {
            if (result.getIntoStorage() != null && result.getIntoStorage() == INTO_STORAGE) {
                result.setStorageTime(new Date());
                Long userId = SysUserUtils.getLoginSysUserId(request);
                resultBiz.updatePassStatusRecard(result,userId);

                // 入库 返回成果类型 科研/咨政
                Result r = resultBiz.findById(result.getId());
                if (ObjectUtils.isNotNull(r)) {
                    resultType = r.getResultType();
                }
            }
            objectMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", resultType);
        } catch (Exception e) {
            logger.error("ResultArchiveController.updateResult", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", result);
        }
        return objectMap;

    }


    /**
     * 归档
     *
     * @param result 包含自增id 和 已入库状态
     * @param flag   不为null时, 意味着归档
     * @return json
     */
    @RequestMapping("/doFileArchive")
    @ResponseBody
    public Map<String, Object> doFileArchive(@ModelAttribute("result") Result result,
                                             @RequestParam(value = "flag") Integer flag) {
        Map<String, Object> objectMap;
        // 有flag意味着归档
        try {
            if (ObjectUtils.isNotNull(flag)) {
                result.setArchiveNo(getArchiveNo(flag));
                Long userId = SysUserUtils.getLoginSysUserId(request);
                resultBiz.updatePassStatusRecard(result,userId);
            }
            objectMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("ResultArchiveController.updateResult", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", result);
        }
        return objectMap;

    }


    /**
     * 添加档案
     *
     * @param resultType 成果的类型 1: 科研 2:生态文明所
     * @return 添加档案
     */
    @RequestMapping("/saveFile")
    public ModelAndView saveFile(@RequestParam Integer resultType) {
        ModelAndView mv = new ModelAndView("/reconstruction/result_file_add");
        try {
            // 根据权限的参数 判断科研/生态文明所
            if (resultType == ResultTypeConstants.KE_YAN) {
                mv.addObject("resultTypeName", ResultTypeConstants.KE_YAN_NAME);
            }
            if (resultType == ResultTypeConstants.ZI_ZHENG) {
                mv.addObject("resultTypeName", ResultTypeConstants.ZI_ZHENG_NAME);
            }

            // 所有的成果类型
            List<ResultForm> resultFormList = resultFormBiz.getResultFormList(new ResultForm());
            mv.addObject("resultFormList", resultFormList);

            // 传值
            mv.addObject("resultType", resultType);
        } catch (Exception e) {
            logger.error("ResultFileController.saveFile", e);
        }
        return mv;
    }

    /**
     * 新建档案
     */
    @RequestMapping("doSaveFile")
    @ResponseBody
    public Map<String, Object> doSaveFile(@ModelAttribute("result") Result result,
                                          @RequestParam("flag") Integer flag) {
        Map<String, Object> objMap;
        try {
            // 新建成果
            Long userId = SysUserUtils.getLoginSysUserId(request);
            // 获取教职工id 1为管理员 2为教职工 3为学员
            Long linkId = baseHessianBiz.queryEmployeeIdBySysUserId(userId);
            if (ObjectUtils.isNotNull(linkId)) {
                result.setEmployeeId(linkId);
            }

            result.setSysUserId(userId);
            // 默认
            result.setStatus(1);
            // 已入库
            result.setIntoStorage(2L);
            // 已结项
            result.setPassStatus(ApprovalStatusConstants.FINISH);
            // 已归档
            result.setIfFile(2);
            // 生成档号
            result.setArchiveNo(getArchiveNo(flag));
            // 新建档案
            resultBiz.addResult(result);
            objMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", result.getResultType());
        } catch (Exception e) {
            logger.error("ResultArchiveController.doSaveFile", e);
            objMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return objMap;
    }

    /**
     * 成果档案列表 编辑档号
     *
     * @param id 成果id
     * @return 成果详情
     */
    @RequestMapping("mergeResult")
    public ModelAndView mergeResult(Long id) {
        ModelAndView mv = new ModelAndView("/reconstruction/result_merge");
        try {
            Result r = resultBiz.findById(id);
            mv.addObject("result", r);

            Long sysUserId=SysUserUtils.getLoginSysUserId(request);
            // 查询处室
            List<SubSection> subSectionList = subSectionBiz.querySubSectionListBySysUserId(sysUserId);
            mv.addObject("subSectionList", subSectionList);

            // 二级菜单
            List<Category> categoryList = categoryBiz.find(null,
                    "resultFormId = " + r.getResultForm() + " order by sort desc");
            mv.addObject("categoryList", categoryList);
        } catch (Exception e) {
            logger.error("ResultArchiveController.mergeResult", e);
        }
        return mv;
    }

    /**
     * 修改档号
     *
     * @param result 成果
     * @return 成果类型 拼装档案URL参数
     */
    @RequestMapping("doMergeResult")
    @ResponseBody
    public Map<String, Object> doMergeResult(@ModelAttribute("result") Result result) {
        Map<String, Object> objectMap;
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            resultBiz.updatePassStatusRecard(result,userId);
            objectMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, result);
        } catch (Exception e) {
            logger.error("ResultArchiveController.doMergeResult", e);
            objectMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, ErrorCode.SYS_ERROR_MSG, result);
        }
        return objectMap;
    }


    /**
     * 生成归档编号
     *
     * @param flag 2:短期归档 3:长期归档
     * @return 归档编号
     */
    private String getArchiveNo(Integer flag) {
        //生成归档编号
        //(规则072-w【年份】-ky2-001）、件号
        //a）072 党校代号
        //b）w【年份】
        //c）ky2 长期 ky3 短期
        //4）001 自增编号，对应件号
        StringBuilder sb = new StringBuilder("072-w");
        Calendar date = Calendar.getInstance();
        sb.append(date.get(Calendar.YEAR)).append("-ky").append(flag);
        // 查询已归档的文件数量
        Integer count = resultBiz.count("ifFile =2");
        // 文档号自增 数字前面补0
        String suffix = String.format("-%04d", count + 1);
        sb.append(suffix);
        return sb.toString();
    }
}
