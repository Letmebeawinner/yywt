package com.oa.controller.file;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.File.FileBiz;
import com.oa.entity.file.File;
import com.oa.entity.file.FileDto;
import com.oa.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.util.URIUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 文件管理
 *
 * @author ccl
 * @create 2017-01-04-9:46
 */
@Controller
@RequestMapping("/admin/oa")
public class FileController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileBiz fileBiz;

    @InitBinder({"file"})
    public void initFile(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("file.");
    }

    private static final String createFile = "/file/file_add";
    private static final String toUpdateFile = "/file/file_update";
    private static final String fileList = "/file/file_list";

    private static final String fileShareList="/file/file_share_list";

    /**
     * @Description:查询所有文件
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2017-01-04
     */
    @RequestMapping("/queryAllFile")
    public String queryAllFile(HttpServletRequest request, @ModelAttribute("file") File file, @ModelAttribute("pagination") Pagination pagination) {
        try {
            String whereSql = GenerateSqlUtil.getSql(file);
            pagination.setRequest(request);
            List<File> fileList = fileBiz.find(pagination, whereSql);
            request.setAttribute("fileList", fileList);
            request.setAttribute("file",file);
        } catch (Exception e) {
            logger.error("FileController--queryAllFile", e);
            return this.setErrorPath(request, e);
        }
        return fileList;
    }

    /**
     * @Description:
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2017-01-04
     */
    @RequestMapping("/toAddFile")
    public String toAddFile(HttpServletRequest request) {
        try {

        } catch (Exception e) {
            logger.error("FileController--toAddFile", e);
            return this.setErrorPath(request, e);
        }
        return createFile;
    }

    /**
     * @Description:添加
     * @author: ccl
     * @Param: [request, file]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-01-04
     */
    @RequestMapping("/addSaveFile")
    @ResponseBody
    public Map<String, Object> addSaveFile(HttpServletRequest request, @ModelAttribute("file") File file) {
        Map<String, Object> resultMap = null;
        try {
            Long userId= SysUserUtils.getLoginSysUserId(request);
            file.setUserId(userId);
            fileBiz.save(file);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("FileController--addSaveFile", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:修改文件
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2017-01-04
     */
    @RequestMapping("/toUpdateFile")
    public String toUpdateFile(HttpServletRequest request, @RequestParam(value = "id", required = true) Long id) {
        try {
            File file = fileBiz.findById(id);
            request.setAttribute("file", file);
        } catch (Exception e) {
            logger.error("FileController--toUpdateFile", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateFile;
    }


    /**
     * @Description:修改
     * @author: ccl
     * @Param: [file]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-01-04
     */
    @RequestMapping("/updateFile")
    @ResponseBody
    public Map<String, Object> updateFile(@ModelAttribute("file") File file) {
        Map<String, Object> resultMap = null;
        try {
            fileBiz.update(file);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("FileController--updateFile", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:删除文件
     * @author: ccl
     * @Param: [id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-01-04
     */
    @RequestMapping("/deleteFile")
    @ResponseBody
    public Map<String, Object> deleteFile(@RequestParam(value = "id") Long id) {
        Map<String, Object> resultMap = null;
        try {
            fileBiz.deleteById(id);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("FileController--deleteFile", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }


    /**
     * @Description:
     * @author: ccl
     * @Param: [request, pagination, file]
     * @Return: java.lang.String
     * @Date: 2017-03-08
     */
    @RequestMapping("/shareFile")
    public String shareFile(HttpServletRequest request,@ModelAttribute("pagination") Pagination pagination,@ModelAttribute("file") File file){
        try{
            pagination.setRequest(request);
            List<FileDto> fileList = fileBiz.getFilesDtos(pagination, file);
            request.setAttribute("fileList", fileList);
        }catch (Exception e){
            logger.error("FileController--shareFile",e);
            return  this.setErrorPath(request,e);
        }
        return fileShareList;
    }
    
    
    
    /**
     * @Description:下载文件
     * @author: ccl
     * @Param: []
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2017-01-04
     */
    @RequestMapping("/downloadFile")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam("fileUrl") String fileUrl) {
        ServletOutputStream outStream = null;
        InputStream inputStream = null;// 创建inputStream流
        try {
            String url =fileUrl;
            // 获得httpclient 建立http连接 获得图片流
            HttpClient httpclient = new HttpClient();
            GetMethod method = new GetMethod(url);
            method.getParams().setParameter("http.protocol.content-charset", "UTF-8");// 设置参数编码

            if (url!=null && url.trim().length()>0){
                method.setQueryString(URIUtil.encodeQuery(url));
                httpclient.executeMethod(method);
                if(method.getStatusCode() == 200) {// 状态200正常
                    inputStream = method.getResponseBodyAsStream();
                } else {
                    return null;
                }
            }
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "application/octet-stream");
            int _index = url.lastIndexOf("/");
            response.setHeader("Content-disposition", "attachment;filename="+url.trim().substring(_index+1,url.trim().length()));
            outStream = response.getOutputStream();
            byte[] block = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(block)) != -1) {
                outStream.write(block, 0, len);
            }
            outStream.flush();
            method.abort();
        } catch (IOException e) {
            logger.error("downloadFile", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException ex) {
                logger.error("downloadFile--------", ex);
            }
        }
        return null;
    }


}
