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
 * 图片上传Controller
 * @author s.li
 * @create 2016-12-12-16:07
 */
@RequestMapping("/upload/res/image")
@Controller
public class ImageUploadController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * @Description: 上传图片
     * @author: s.li
     * @Param: [request, imageFiles]
     * @Date: 2016/12/12
     */
    @RequestMapping(value="/uploadImage",method = RequestMethod.POST)
    public void uploadImage(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam(value="imageFiles",required = true)MultipartFile[] imageFiles){
        try{
            List<String> fileUrls = FileUtil.saveSimpleFile(request,imageFiles,FileUtil.IMAGE);
            response.getWriter().print(gson.toJson(fileUrls));
        }catch(Exception e){
            logger.error("uploadImage()--error",e);
        }
    }
}
