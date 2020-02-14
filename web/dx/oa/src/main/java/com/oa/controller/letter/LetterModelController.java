package com.oa.controller.letter;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.letter.LetterBiz;
import com.oa.biz.letter.LetterModelBiz;
import com.oa.biz.letter.UserLetterBiz;
import com.oa.biz.workflow.OaLetterBiz;
import com.oa.entity.letter.Letter;
import com.oa.entity.letter.LetterModel;
import com.oa.entity.letter.UserLetter;
import com.oa.entity.workflow.OaLetter;
import com.oa.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 红头公文模板
 *
 * @author jin shuo
 * @create 2018-07-31-16:32
 */
@Controller
@RequestMapping("/admin/oa")
public class LetterModelController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(LetterModelController.class);

    @Autowired
    private LetterModelBiz letterModelBiz;

    @InitBinder({"letterModel"})
    public void initLetterModel(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("letterModel.");
    }


    private static final String toAddLetterModel = "/letter/letter_model_add";//添加公文模板
    private static final String toUpdateLetterModel = "/letter/letter_model_update";//修改公文模板
    private static final String letterModelList = "/letter/letter_model_list";//公文模板列表


    /**
     * @Description:查询所有公文模板
     * @author: ccl
     * @Param: [request, pagination, letter]
     * @Return: java.lang.String
     * @Date: 2017-03-08
     */
    @RequestMapping("/letterModelList")
    public String letterModelList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination) {
        try {
            String whereSql = "1=1";
            List<LetterModel> letterModelList = letterModelBiz.find(pagination, whereSql);
            request.setAttribute("letterModelList", letterModelList);
        } catch (Exception e) {
            logger.error("LetterController--letterModelList", e);
            return this.setErrorPath(request, e);
        }
        return letterModelList;
    }


    /**
     * @Description:去添加公文模板
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2017-03-08
     */
    @RequestMapping("/toAddLetterModel")
    public String toAddLetterModel(HttpServletRequest request) {
        try {

        } catch (Exception e) {
            logger.error("LetterController--toAddLetterModel", e);
            return this.setErrorPath(request, e);
        }
        return toAddLetterModel;
    }


    /**
     * @Description:添加保存模板
     * @author: ccl
     * @Param: [request, letter]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-03-08
     */
    @RequestMapping("/addLetterModel")
    @ResponseBody
    public Map<String, Object> addLetterModel(HttpServletRequest request,
                                              @RequestParam(value = "file") MultipartFile file) {
        Map<String, Object> resultMap = null;
        try {
            // 判断文件是否为空
            if (!file.isEmpty()) {
                LetterModel letterModel = new LetterModel();
                String modelName = request.getParameter("modelName").toString();
                Integer sort = Integer.valueOf(request.getParameter("sort"));
                letterModel.setModelName(modelName);
                letterModel.setSort(sort);
                letterModel.setFileName(file.getOriginalFilename());
                letterModelBiz.save(letterModel);
                // 文件保存路径
                String fileUrl = request.getSession().getServletContext().getRealPath("/") + "TaoHong/doc/" + letterModel.getId() + "/" + file.getOriginalFilename();
                // 转存文件
                File file1 = new File(fileUrl);
                if (!file1.exists()) {
                    file1.mkdirs();
                }
                file.transferTo(file1);
                fileUrl= "/TaoHong/doc/" + letterModel.getId() + "/" + file.getOriginalFilename();
                letterModel.setFileUrl(fileUrl);
                letterModelBiz.update(letterModel);
            }
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("LetterController--addLetterModel", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:去修改公文模板
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2017-03-08
     */
    @RequestMapping("/toUpdateLetterModel")
    public String toUpdateLetterModel(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            LetterModel letterModel = letterModelBiz.findById(id);
            request.setAttribute("letterModel", letterModel);
        } catch (Exception e) {
            logger.error("LetterController--toUpdateLetterModel", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateLetterModel;
    }


    /**
     * @Description:修改公文模板
     * @author: ccl
     * @Param: [request, letter]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-03-08
     */
    @RequestMapping("updateLetterModel")
    @ResponseBody
    public Map<String, Object> updateLetterModel(HttpServletRequest request, @ModelAttribute("letterModel") LetterModel letterModel) {
        Map<String, Object> resultMap = null;
        try {
            letterModelBiz.update(letterModel);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("LetterController--updateLetterModel", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:删除公文模板
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-03-08
     */
    @RequestMapping("/delLetterModel")
    @ResponseBody
    public Map<String, Object> delLetterModel(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            letterModelBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("LetterController--delLetterModel", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

}
