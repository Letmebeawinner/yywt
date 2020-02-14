package com.yizhilu.os.res.util;

import com.a_268.base.util.DateUtils;
import com.a_268.base.util.StringUtils;
import com.yizhilu.os.res.commmon.CommonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * 
 * @description 资源上传工具类
 * @author : s.li
 * @Create Date : 2013-12-13 下午2:49:02
 */
public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    public static String IMAGE="image";
    public static String ALL_FILE="allFile";
    private static String IMG_PX_DIR="/upload/res/image";
    private static String ALL_FILE_PX_DIR="/upload/res/file";

    /**
     * @Description: 保存文件
     * @author: s.li
     * @Param: [request, files, fileType]
     * @Return: java.util.List<java.lang.String>
     * @Date: 2016/12/16
     */
    public static List<String> saveSimpleFile(HttpServletRequest request,MultipartFile[] files,String fileType) throws Exception{
        List<String> fileUrls = new ArrayList<>();
        for(int i=0;i<files.length;i++){
            String[] savePath = FileUtil.getImageSavePath(request,files[i],fileType);
            if(!FileUtil.chackFileName(files[i].getOriginalFilename(),fileType)){
                return null;
            }
            File saveDir = new File(savePath[0]);
            if(!saveDir.getParentFile().exists()){
                saveDir.getParentFile().mkdirs();
            }
            files[i].transferTo(saveDir);
            fileUrls.add(savePath[1]);
        }
        //修改文件夹的权限
        Runtime.getRuntime().exec("chmod 755 -R /data/htdocs/static/");
        return fileUrls;
    }

    /**
     * 验证文件名是否合法
     * @param fileName 文件名
     * @param fileTyp 文件类型【IMAGE,ZIP_FILE,WORD_FILE,ALL_FILE】
     * @return true合法，false不合法
     */
    public static boolean chackFileName(String fileName,String fileTyp){
        HashMap<String, String> extMap = new HashMap<String, String>();
        extMap.put("image", "gif,jpg,jpeg,png,bmp,pdf");
        extMap.put("allFile", "");

        if(extMap.get(fileTyp)!=null){
            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if(fileTyp.equals(IMAGE)){
                if (!Arrays.<String> asList(extMap.get(fileTyp).split(",")).contains(fileExt)) {
                    return false;
                }
            }else if(fileTyp.equals(ALL_FILE)){

            }
            return true;
        }
        return false;
    }

    /**
     * 获取文件后缀
     * @param fileName 文件名
     * @return 返回文件的后缀
     */
    public static String getFormatName(String fileName){
        return fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()).toLowerCase();
    }

    /**
     * 获取文件的保存路径
     * @param request HttpServletRequest
     * @param file  MultipartFile
     * @param fileType  文件类型
     * @return 返因文件路径[0:文件保存的目录，1：文件访问的路径]
     */
    public static String[] getImageSavePath(HttpServletRequest request, MultipartFile file,String fileType){

        if(file!=null){
            String baseDir = request.getParameter("dir");
            if(!StringUtils.isTrimEmpty(baseDir)){
                baseDir = "/"+baseDir;
            }else{
                baseDir="/common";
            }
            String[] urls = new String[2];
            //获取webapp根目录
            String rootDir = CommonConstants.uploadRootDir;
            String savePath="";
            if(fileType.equals(IMAGE)){
                savePath = IMG_PX_DIR+baseDir+"/"+ DateUtils.format(new Date(),"yyyyMMdd")+"/"+new Date().getTime()+"."+FileUtil.getFormatName(file.getOriginalFilename());
            }else if(fileType.equals(ALL_FILE)){
                savePath = ALL_FILE_PX_DIR+baseDir+"/"+ DateUtils.format(new Date(),"yyyyMMdd")+"/"+new Date().getTime()+"."+FileUtil.getFormatName(file.getOriginalFilename());
            }
            urls[0]=rootDir+savePath;
            urls[1]= CommonConstants.imagePath+savePath;
            return urls;
        }
        return null;
    }

}
