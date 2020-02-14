package com.yizhilu.os.res.controller;

import com.a_268.base.controller.BaseController;
import com.a_268.base.util.StringUtils;
import com.yizhilu.os.res.commmon.CommonConstants;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 下载文件Controller
 *
 * @author s.li
 * @create 2016-12-15-11:51
 */
@Controller
@RequestMapping("/upload/res/downolad")
public class DownloadFileController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @Description: 下载文件
     * @author: s.li
     * @Param: [request, response, fileUrl, fileName]
     * @Return: void
     * @Date: 2016/12/16
     */
    @RequestMapping("/downoladFile")
    public void downoladFile(HttpServletRequest request,
                             HttpServletResponse response,
                               @RequestParam(value = "fileUrl",required = true) String fileUrl,
                               @RequestParam(value = "fileName",required = false) String fileName){
        OutputStream out = null;
        try{
            String dfileName =getFileName(fileUrl,fileName);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + dfileName);


            String rootDir = CommonConstants.uploadRootDir;
            File file = new File(rootDir+fileUrl);

            out = response.getOutputStream();
            out.write(FileUtils.readFileToByteArray(file));

            out.flush();
        }catch(Exception e){
            logger.error("downoladFile()--error",e);
        }finally {
            if(out!=null){
                try{
                    out.close();
                    logger.info("------------------------downolad file close success---------");
                }catch (IOException ie){
                    logger.error("downoladFile()--error",ie);
                }
            }
        }
        //return null;
    }

    /**
     * @Description: 获取文件名
     * @author: s.li
     * @Param: [fileUrl, nowFileName]
     * @Return: java.lang.String
     * @Date: 2016/12/15
     */
    private String getFileName(String fileUrl,String nowFileName) throws  Exception{
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/")+1,fileUrl.lastIndexOf("."));
        String fName = fileUrl.substring(fileUrl.lastIndexOf("."),fileUrl.trim().length());
        if(StringUtils.isTrimEmpty(nowFileName)){
             return new String((fileName+fName).getBytes("UTF-8"), "ISO8859-1");
        }else{
            return new String((nowFileName+fName).getBytes("UTF-8"), "ISO8859-1");
        }
    }

}
