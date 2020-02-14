package com.keyanzizheng.controller.exportexcel;

import com.a_268.base.controller.BaseController;
import com.keyanzizheng.biz.result.ResultFormBiz;
import com.keyanzizheng.entity.result.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 成果导出
 *
 * @author YaoZhen
 * @date 01-16, 16:22, 2018.
 */
@Controller
@RequestMapping("admin/ky")
public class ExportExcelController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ExportExcelController.class);

    private final HttpServletRequest request;
    private final ResultFormBiz resultFormBiz;

    @Autowired
    public ExportExcelController(HttpServletRequest request, ResultFormBiz resultFormBiz) {
        this.request = request;
        this.resultFormBiz = resultFormBiz;
    }

    @InitBinder("queryResult")
    public void initqueryResult(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("queryResult.");
    }

    /**
     * 科研系统导出
     *
     * @param response 响应路径
     */
    @RequestMapping("xlsExcelExport")
    public void xlsExcelExport(
            HttpServletResponse response,
            @ModelAttribute("queryResult") QueryResult queryResult) {
        try {
            resultFormBiz.xlsExcelExport(request, response, queryResult);
        } catch (Exception e) {
            logger.error("ExportExcelController.xlsExcelExport", e);
        }
    }

    /**
     * 生态系统导出
     */
    @RequestMapping("xlsQuestionExport")
    public void xlsQuestionExport(HttpServletResponse response,
                                  @ModelAttribute("queryResult") QueryResult queryResult) {
        try {
            resultFormBiz.xlsQuestionExport(request, response, queryResult);
        } catch (Exception e) {
            logger.error("ExportExcelController.xlsQuestionExport", e);
        }

    }


}
