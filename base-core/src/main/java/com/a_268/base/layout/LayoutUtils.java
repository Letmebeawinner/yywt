package com.a_268.base.layout;

import com.a_268.base.util.PropertyUtil;
import com.a_268.base.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

/**
 * 页面布局工具类
 * @author s.li
 */
@Component
public class LayoutUtils {
    private static Logger logger = LoggerFactory.getLogger(LayoutUtils.class);

    /**
     * 项目启动完成，自动初始化页面布局
     */
    @PostConstruct
    public void initLayout(){
        String layoutRoot,adminKey,webKey,ucKey,rootDir;
        logger.info(".................................................init layout files");
        //资源读取工具类
        PropertyUtil propertyUtil = PropertyUtil.getInstance("config");
        boolean isLocation = Boolean.parseBoolean(propertyUtil.getProperty("layout.location"));
        try{
            layoutRoot = propertyUtil.getProperty("layout.root");
        }catch (Exception e){
            logger.info("project layout root is null ----> layout.root  is null,layout.root is requisite");
            layoutRoot = null;
            if(!isLocation){
                return;
            }
        }
        try{
            adminKey = propertyUtil.getProperty("layout.root.admin");
        }catch (MissingResourceException e){
            logger.info("project layout root admin key is null ----> layout.root.admin  is null,layout.root.admin is not requisite");
            adminKey = null;
        }
        try{
            webKey = propertyUtil.getProperty("layout.root.web");
        }catch (MissingResourceException e){
            logger.info("project layout root web key is null ----> layout.root.web  is null,layout.root.web is not requisite");
            webKey = null;
        }
        try{
            ucKey = propertyUtil.getProperty("layout.root.uc");
        }catch (MissingResourceException e){
            logger.info("project layout root uc key is null ----> layout.root.uc  is null,layout.root.uc is not requisite");
            ucKey = null;
        }
        try{
            rootDir = propertyUtil.getProperty("layout.root.dir");
        }catch (MissingResourceException e){
            logger.info("project layout root dir is null ----> layout.root.dir  is null,layout.root.dir is requisite");
            rootDir=null;
            return;
        }

        if(!StringUtils.isTrimEmpty(adminKey)){
            initAdminLayout(layoutRoot,rootDir,adminKey,isLocation);
        }
        if(!StringUtils.isTrimEmpty(webKey)){
            initWebLayout(layoutRoot,rootDir,webKey,isLocation);
        }
        if(!StringUtils.isTrimEmpty(ucKey)){
            initUcLayout(layoutRoot,rootDir,ucKey,isLocation);
        }
    }

    /**
     * 生成后台布局
     */
    private void initAdminLayout(String layoutRoot,String rootDir, String adminKey,boolean isLocation){
        try{
            String fileUrl =null;
            if(isLocation){
                fileUrl = rootDir+"/layout/admin/default.vm";
            }else{
                fileUrl = layoutRoot+"/layout/admin/default.vm";
            }
            String saveFile=rootDir+"/WEB-INF/layout/"+adminKey.trim()+"/default.jsp";
            createLayoutFile(fileUrl,saveFile,isLocation);
            //生成后台公共文件
            createAdminCommonFile(layoutRoot,rootDir,isLocation);
        }catch (Exception e){
            logger.error("initAdminLayout()--error",e);
        }
    }

    /**
     * 生成前台布局
     */
    private void initWebLayout(String layoutRoot,String rootDir, String webKey,boolean isLocation){
        try{
            String fileUrl =null;
            if(isLocation){
                fileUrl = rootDir+"/layout/web/default.vm";
            }else{
                fileUrl = layoutRoot+"/layout/web/default.vm";
            }
            String saveFile=rootDir+"/WEB-INF/layout/"+webKey.trim()+"/default.jsp";
            createLayoutFile(fileUrl,saveFile,isLocation);
        }catch (Exception e){
            logger.error("initWebLayout()--error",e);
        }
    }

    /**
     * 生成个人中心布局
     */
    private void initUcLayout(String layoutRoot,String rootDir, String ucKey,boolean isLocation){
        try{
            String fileUrl =null;
            if(isLocation){
                fileUrl = rootDir+"/layout/uc/default.vm";
            }else{
                fileUrl = layoutRoot+"/layout/uc/default.vm";
            }
            String saveFile=rootDir+"/WEB-INF/layout/"+ucKey.trim()+"/default.jsp";
            createLayoutFile(fileUrl,saveFile,isLocation);
        }catch (Exception e){
            logger.error("initWebLayout()--error",e);
        }
    }

    /**
     * 生成后台公共文件
     * @param layoutRoot 远程域名
     * @param rootDir 项目根目录
     * @param isLocation 是否本地项目，true是，false不是
     */
    private void createAdminCommonFile(String layoutRoot,String rootDir,boolean isLocation) throws Exception{
        String adminPage,error,not_authority,error_400,error_403,error_404,error_500;

        String _dirPath ="";
        if(isLocation){
            _dirPath=rootDir;
        }else{
            _dirPath=layoutRoot;
        }
        adminPage = _dirPath+"/common/adminPage.vm";
        error = _dirPath+"/common/error.vm";
        not_authority = _dirPath+"/common/not_authority.vm";
        error_400 = _dirPath+"/common/400.vm";
        error_403 = _dirPath+"/common/403.vm";
        error_404 = _dirPath+"/common/404.vm";
        error_500 = _dirPath+"/common/500.vm";

        List<String> dirList = new ArrayList<>();
        dirList.add(0,rootDir+"/WEB-INF/view/common/adminPage.jsp");
        dirList.add(1,rootDir+"/WEB-INF/view/common/error.jsp");
        dirList.add(2,rootDir+"/WEB-INF/view/common/not_authority.jsp");
        dirList.add(3,rootDir+"/WEB-INF/view/common/400.jsp");
        dirList.add(4,rootDir+"/WEB-INF/view/common/403.jsp");
        dirList.add(5,rootDir+"/WEB-INF/view/common/404.jsp");
        dirList.add(6,rootDir+"/WEB-INF/view/common/500.jsp");

        List<String> list = new ArrayList<>();
        list.add(0,adminPage);
        list.add(1,error);
        list.add(2,not_authority);
        list.add(3,error_400);
        list.add(4,error_403);
        list.add(5,error_404);
        list.add(6,error_500);

        for(int i=0;i<list.size();i++){
            if(list.get(i)!=null && !list.get(i).trim().equals("")){
                if(isLocation){
                    fileRw(list.get(i),dirList.get(i));
                }else{
                    urlRw(list.get(i),dirList.get(i));
                }
            }
        }
    }

    /**
     * 生成布局文件
     * @param fileUrl 文件HttpUrl
     * @param saveFile 生成文件的路径
     * @param isLocation 是否是本地项目
     * @throws Exception
     */
    private  void createLayoutFile(String fileUrl,String saveFile,boolean isLocation) throws Exception{
        if(isLocation){
            fileRw(fileUrl,saveFile);
        }else{
            urlRw(fileUrl,saveFile);
        }
    }

    /**
     * 操作文件
     * @param fileUrl 文件原路径
     * @param saveFile 新文件路径
     * @throws Exception
     */
    private void fileRw(String fileUrl,String saveFile) throws Exception{
        createaFileDir(saveFile);
        //读取模板内容
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileUrl))));
        //写到目标文件
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(saveFile))));

        String line = null;
        while ((line=br.readLine())!=null){
            bw.write(line);
            bw.newLine();
            bw.flush();
        }
        br.close();
        bw.close();
    }

    /**
     * 远程文件操作
     * @param fileUrl
     * @param saveFile
     * @throws Exception
     */
    private void urlRw(String fileUrl,String saveFile) throws Exception{
        createaFileDir(saveFile);
        //非本地项目，则进行网络获取
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        DataInputStream in = new DataInputStream(connection.getInputStream());
        DataOutputStream out = new DataOutputStream(new FileOutputStream(saveFile));
        byte[] buffer = new byte[4096];
        int count = 0;
        while ((count = in.read(buffer)) > 0) {
            out.write(buffer, 0,count);
        }
        out.close();
        in.close();
    }

    private void createaFileDir(String dir){
        File f = new File(dir);
        if(!f.getParentFile().exists()){
            f.getParentFile().mkdirs();
        }
    }
}
