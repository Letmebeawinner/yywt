import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import org.apache.commons.httpclient.util.HttpURLConnection;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by My_东 on 2018/11/23.
 */
public class Test {



    @org.junit.Test
    public void a (){
        String dirPath = "http://10.100.101.1:6695/upload/res/file/myFile/20181119/1542611484196.doc";
        String filePath = dirPath.substring(dirPath.indexOf("/") + 2);
        filePath = filePath.substring(filePath.indexOf("/") + 1);
        filePath = filePath.substring(0, filePath.indexOf("."));
        String realPath = "F:/java2/guiyangdangxiao/dx_20170724/web/dx/sms/src/main/webapp";
        String pathUrl = realPath + "/" + filePath + "/" + dirPath.substring((dirPath.lastIndexOf("/") + 1), dirPath.length());
        String path = pathUrl;
        if (!this.fileExists(pathUrl)) {
            this.getInternetRes(realPath + "/" + filePath, dirPath, dirPath.substring((dirPath.lastIndexOf("/") + 1), dirPath.length()));
        }
        String substring = realPath.substring(realPath.lastIndexOf("\\") + 1);
        if (!substring.equals("webapp")) {
            pathUrl = "file://" + pathUrl;
        }
       /* //打开excel
        PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
        //设置服务页面
        poCtrl.setServerPage(request.getContextPath() + "/poserver.zz");*/
        //设置用户并发以分钟为单位
       /* poCtrl.setTimeSlice(1000);
        poCtrl.setMenubar(false);*/
        //添加保存按钮
        String suffix = path.substring(path.lastIndexOf(".") + 1);
       // poCtrl.setSaveFilePage("/saveUploadExcel.json?path=" + path);
        if ("doc".equals(suffix) || "docx".equals(suffix)) {
            System.out.println("");
            //poCtrl.webOpen(pathUrl, OpenModeType.docReadOnly, "");
        } else if ("xls".equals(suffix) || "xlsx".equals(suffix)) {
            //poCtrl.webOpen(pathUrl, OpenModeType.xlsReadOnly, "");
        }
    }


    public  boolean fileExists(String filename) {
        return new File(filename).exists();
    }

    public  void getInternetRes(String newUrl, String oldUrl, String fileName) {
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
    private  byte[] getByteData(InputStream in) throws IOException {
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
