package com.keyanzizheng.controller.importexcel;

import com.a_268.base.controller.BaseController;
import com.keyanzizheng.biz.result.ResultBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 导入excel
 *
 * @author YaoZhen
 * @date 01-10, 17:05, 2018.
 */
@Controller
@RequestMapping("/admin/ky")
public class ImportExcelController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ImportExcelController.class);
    private final ResultBiz resultBiz;
    private final HttpServletRequest request;


    @Autowired
    public ImportExcelController(ResultBiz resultBiz, HttpServletRequest request) {
        this.resultBiz = resultBiz;
        this.request = request;
    }

    /**
     * 科研处成果导入
     * @return 模板下载页面
     */
    @RequestMapping("resultXlsExcelImport")
    public String resultXlsExcelImport() {
        return "/importexcel/import_excel_result_keyan";
    }

    /**
     * 科研处批量导入
     */
    @RequestMapping("doImportResultXls")
    public ModelAndView doImportResultXls(@RequestParam("myFile") MultipartFile myFile, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/admin/ky/resultXlsExcelImport.json");
        try {
            String errorInfo = resultBiz.batchImport(myFile, request);
            if (errorInfo == null) {
                errorInfo = "导入成功, 请在成果列表查看";
            }
            redirectAttributes.addFlashAttribute("errorInfo", errorInfo);
        } catch (Exception e) {
            logger.error("ImportExcelController.doImportResultXls", e);
        }
        return mv;
    }

    /**
     * 生态所成果导入
     */
    @RequestMapping("questionXlsImport")
    public String questionXlsImport() {
        return "/importexcel/import_excel_result_zizheng";
    }

    /**
     * 生态所成果批量导入
     */
    @RequestMapping("doQuestionXlsImport")
    public ModelAndView doQuestionXlsImport(@RequestParam("myFile") MultipartFile myFile,
                                            RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView("redirect:/admin/ky/questionXlsImport.json");
        try {
            String errorInfo = resultBiz.batchImportQuestion(myFile, request);
            if (errorInfo == null) {
                errorInfo = "导入成功, 请在课题列表查看";
            }
            redirectAttributes.addFlashAttribute("errorInfo", errorInfo);
        } catch (Exception e) {
            logger.error("ImportExcelController.doQuestionXlsImport", e);
        }
        return mv;
    }
}
