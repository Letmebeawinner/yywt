package com.sms.word;

import com.a_268.base.controller.BaseController;
import com.sms.biz.info.InfoRecordBiz;
import com.sms.biz.info.InfoUserReceiveBiz;
import com.sms.biz.info.InfoUserRecordBiz;
import com.sms.entity.info.InfoRecord;
import com.sms.entity.info.InfoUserReceive;
import com.sms.entity.info.InfoUserRecord;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PDFCtrl;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import org.apache.commons.httpclient.util.HttpURLConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by My_东 on 2018/11/23.
 */
@Controller
@RequestMapping("/admin")
public class OpenDocumentController extends BaseController {
    @Autowired
    private InfoUserReceiveBiz infoUserReceiveBiz;
    @Autowired
    private InfoRecordBiz infoRecordBiz;

    @RequestMapping("/sms/open/infoRecordOfficialDocument")
    public String infoRecordOfficialDocument(HttpServletRequest request, @RequestParam("infoRecordId") Long infoRecordId
            , @RequestParam(value = "type", required = false, defaultValue = "0") Integer type

    ) {
        System.out.println("-=======进来了");
        String dirPath = "";
        if (type == 1) {
            InfoRecord infoRecord = infoRecordBiz.findById(infoRecordId);
            dirPath = infoRecord.getFileUrl();
        } else {
            InfoUserReceive infoUserReceive = infoUserReceiveBiz.findById(infoRecordId);
            dirPath = infoUserReceive.getFileUrl();
        }
        System.out.println(dirPath + "-=======进来了dirPath");
        String filePath = dirPath.substring(dirPath.indexOf("/") + 2);
        filePath = filePath.substring(filePath.indexOf("/") + 1);
        filePath = filePath.substring(0, filePath.indexOf("."));
        String realPath = request.getServletContext().getRealPath("");
        String pathUrl = realPath + "/" + filePath + "/" + dirPath.substring((dirPath.lastIndexOf("/") + 1), dirPath.length());
        System.out.println(pathUrl + "pathUrl=======");
        String path = pathUrl;
        if (!this.fileExists(pathUrl)) {
            this.getInternetRes(realPath + "/" + filePath, dirPath, dirPath.substring((dirPath.lastIndexOf("/") + 1), dirPath.length()));
        }
        String substring = realPath.substring(realPath.lastIndexOf("\\") + 1);
        if (!substring.equals("webapp")) {
            pathUrl = "file://" + pathUrl;
        }
        String suffix = path.substring(path.lastIndexOf(".") + 1);
        //poCtrl.setSaveFilePage("/saveUploadExcel.json?path=" + path);
        if ("pdf".equals(suffix)) {
            System.out.println("我进来了啊===============" + suffix);
            System.out.println("pathUrl===============555555" + pathUrl);
            PDFCtrl poCtrl1 = new PDFCtrl(request);
            poCtrl1.setServerPage(request.getContextPath() + "/poserver.zz"); //此行必须
            poCtrl1.addCustomToolButton("-", "", 0);
            poCtrl1.addCustomToolButton("实际大小", "SetPageReal()", 16);
            poCtrl1.addCustomToolButton("适合页面", "SetPageFit()", 17);
            poCtrl1.addCustomToolButton("适合宽度", "SetPageWidth()", 18);
            poCtrl1.addCustomToolButton("-", "", 0);
            poCtrl1.addCustomToolButton("首页", "FirstPage()", 8);
            poCtrl1.addCustomToolButton("上一页", "PreviousPage()", 9);
            poCtrl1.addCustomToolButton("下一页", "NextPage()", 10);
            poCtrl1.addCustomToolButton("尾页", "LastPage()", 11);
            poCtrl1.addCustomToolButton("-", "", 0);
            request.setAttribute("type", "1");
            poCtrl1.webOpen(pathUrl);
            poCtrl1.setTagId("PageOfficeCtrl1");
        } else {
            //打开excel
            PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
            //设置服务页面
            poCtrl.setServerPage(request.getContextPath() + "/poserver.zz");
            if ("doc".equals(suffix) || "docx".equals(suffix)) {
                System.out.println(suffix + "================");
                poCtrl.webOpen(pathUrl, OpenModeType.docReadOnly, "");
            } else if ("xls".equals(suffix) || "xlsx".equals(suffix)) {
                System.out.println(suffix + "xls==========");
                poCtrl.webOpen(pathUrl, OpenModeType.xlsReadOnly, "");
            }
            request.setAttribute("type", "2");
            request.setAttribute("PageOfficeCtrl1", poCtrl.getHtmlCode("PageOfficeCtrl1"));
        }


        return "info/word/opDocumentl";
    }

    public boolean fileExists(String filename) {
        return new File(filename).exists();
    }

    public void getInternetRes(String newUrl, String oldUrl, String fileName) {
        URL url = null;
        HttpURLConnection con = null;
        InputStream in = null;
        FileOutputStream out = null;
        try {
            url = new URL(oldUrl);
            //建立http连接，得到连接对象
            URLConnection co = url.openConnection();
            //输入流读取文件
            in = co.getInputStream();
            //转化为byte数组
            byte[] data = getByteData(in);
            //建立存储的目录、保存的文件名
            File file = new File(newUrl);
            if (!file.exists()) {
                file.mkdirs();
            }
            //修改文件名   用id重命名
            File res = new File(file + File.separator + fileName);
            //写入输出流
            out = new FileOutputStream(res);
            out.write(data);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (null != out)
                    out.close();
                if (null != in)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Title: getByteData
     * @Description: 从输入流中获取字节数组
     * @author zml
     * @date Sep 12, 2017 7:38:57 PM
     */
    private byte[] getByteData(InputStream in) throws IOException {
        byte[] b = new byte[1024];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len = 0;
        while ((len = in.read(b)) != -1) {
            bos.write(b, 0, len);
        }
        if (null != bos) {
            bos.close();
        }
        return bos.toByteArray();
    }

}
