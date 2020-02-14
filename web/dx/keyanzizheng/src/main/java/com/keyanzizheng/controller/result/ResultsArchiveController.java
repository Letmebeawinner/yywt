package com.keyanzizheng.controller.result;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.SysUserUtils;
import com.keyanzizheng.biz.common.BaseHessianBiz;
import com.keyanzizheng.biz.common.OAHessianService;
import com.keyanzizheng.biz.result.ResultBiz;
import com.keyanzizheng.constant.StatusConstants;
import com.keyanzizheng.entity.oa.Archive;
import com.keyanzizheng.entity.result.Result;
import com.keyanzizheng.entity.user.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 成果归档
 * <p><p>
 *
 * @author YaoZhen
 * @date 01-03, 18:22, 2018.
 */
@Controller
@RequestMapping("/admin/ky")
public class ResultsArchiveController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ResultsArchiveController.class);
    /**
     * 当前线程的req
     */
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ResultBiz resultBiz;
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
     * 归档表单
     *
     * @param resultId 隐藏域复制
     * @return 归档到OA
     */
    @RequestMapping("archiveResult")
    public ModelAndView archiveResult(Long resultId) {
        ModelAndView mv = new ModelAndView("/result/filed_result");
        try {
            List<Map<String, String>> linkedHashMapList = oaHessianService.listArchiveType();
            mv.addObject("linkedHashMapList", linkedHashMapList);
            mv.addObject("resultId", resultId);
        } catch (Exception e) {
            logger.error("ResultsArchiveController.archiveResult", e);
        }
        return mv;
    }

    /**
     * 添加
     *
     * @param archive 归档
     * @return json
     */
    @RequestMapping("doSaveResultArchive")
    @ResponseBody
    public Map<String, Object> doSaveResultArchive(@ModelAttribute("archive") Archive archive,
                                                   Long resultId) {
        Map<String, Object> hashMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);;
        try {
            SysUser department = baseHessianBiz.querySysUserById(SysUserUtils.getLoginSysUserId(request));
            Long departmentId = department.getDepartmentId();
            archive.setDepartId(departmentId);
            archive.setStockFlag(StatusConstants.NEGATE);
            Long archiveId = oaHessianService.saveArchive(gson.toJson(archive));

            if (archiveId != null) {
                // 级联科研成果
                Result result = new Result();
                result.setId(resultId);
                result.setIfFile(StatusConstants.DONE);
                result.setOaArchiveId(archiveId);
                Long userId = SysUserUtils.getLoginSysUserId(request);
                resultBiz.updatePassStatusRecard(result,userId);
                hashMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, resultBiz.findById(resultId));
            }
        } catch (Exception e) {
            logger.error("ResultsArchiveController.doSaveResultArchive", e);
        }
        return hashMap;
    }

}
