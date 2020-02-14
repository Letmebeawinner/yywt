package com.jiaowu.controller.word;

import com.a_268.base.controller.BaseController;
import com.jiaowu.biz.uploadExcelHistory.UploadExcelHistoryBiz;
import com.jiaowu.common.CommonConstants;
import com.jiaowu.common.FileUtils;
import com.jiaowu.entity.uploadExcelHistory.UploadExcelHistory;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by My_东 on 2018/10/31.
 */
@Controller
@RequestMapping("")
public class OpenExcelController extends BaseController {
    @Autowired
    private UploadExcelHistoryBiz uploadExcelHistoryBiz;

    /**
     * @param request
     * @param id      文件id
     * @Description: 打开excel
     * @create: 2017/12/8 0008 14:20
     * @return:
     */
    @RequestMapping("/open/assetsPossessionExcel")
    public String openAssetsPossessionExcel(HttpServletRequest request,
                                            @RequestParam("id") Long id) {
        UploadExcelHistory uploadExcelHistory = uploadExcelHistoryBiz.findById(id);
        String dirPath = uploadExcelHistory.getFileUrl();
        String filePath = dirPath.substring(dirPath.indexOf("/") + 2);
        filePath = filePath.substring(filePath.indexOf("/") + 1);
        String realPath = CommonConstants.fileRootDir;
        String pathUrl = realPath + "/" + filePath;
        String path = pathUrl;
        if (!FileUtils.fileExists(pathUrl)) {
            FileUtils.getInternetRes(realPath + "/" + filePath, dirPath, dirPath.substring((dirPath.lastIndexOf("/") + 1)));
        }
        String substring = realPath.substring(realPath.lastIndexOf("\\") + 1);
        if (!substring.equals("webapp")) {
            pathUrl = "file://" + pathUrl;
        }
        //打开excel
        PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
        //设置服务页面
        poCtrl.setServerPage(request.getContextPath() + "/poserver.zz");
        //设置用户并发以分钟为单位
       /* poCtrl.setTimeSlice(1000);
        poCtrl.setMenubar(false);*/
        //添加保存按钮
        poCtrl.addCustomToolButton("保存文件", "save", 1);
        poCtrl.setSaveFilePage("/saveUploadExcel.json?path=" + path);
        poCtrl.webOpen(pathUrl, OpenModeType.xlsNormalEdit, "");
        request.setAttribute("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
        return "/admin/excel/open/opExcel";
    }

    /**
     * 动态生成word
     *
     * @author: xiangdong.chang
     * @create: 2017/12/26 0026 16:36
     * @return:
     */
    @RequestMapping("/saveUploadExcel")
    public void saveUploadExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("path") String path) {
        try {
            FileSaver fs = new FileSaver(request, response);
            fs.saveToFile(path);
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
