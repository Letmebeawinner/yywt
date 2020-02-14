package com.yizhilu.os.res.controller;

import com.a_268.base.controller.BaseController;
import com.yizhilu.os.res.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传Controller
 *
 * @author s.li
 * @create 2016-12-15-11:37
 */
@Controller
@RequestMapping("/upload/res/file")
public class FileUploadController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @Description: 文件上传
     * @author: s.li
     * @Param: [request, imageFiles]
     * @Return: void
     * @Date: 2016/12/15
     */
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    public void uploadFile(HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam(value="files",required = true)MultipartFile[] files){
        try{
            List<String> fileUrls = FileUtil.saveSimpleFile(request,files,FileUtil.ALL_FILE);
            response.getWriter().print(gson.toJson(fileUrls));
        }catch(Exception e){
            logger.error("uploadFile()--error",e);
        }
    }
}
