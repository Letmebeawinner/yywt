package com.base.controller.user;

import com.a_268.base.controller.BaseController;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.base.biz.common.OAHessianService;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 实现套红
 * Created by xiangdong on 2018/5/30 0030.
 */
@Controller
public class OpenWordController extends BaseController {


    @Autowired
    private OAHessianService oaHessianService;
    private final static String oa_word = "/word/opWord";
    private final static String oa_history_word = "/word/oaHistoryWord";

    /**
     * @param request
     * @param processDefinitionId 工作流id
     * @Description: 预览
     * @author: xiangdong.chang
     * @create: 2017/12/8 0008 14:20
     * @return:
     */
   /* @RequestMapping("/open/oaHistoryWord")
    public String oaHistoryWord(HttpServletRequest request,
                                @RequestParam(value = "processDefinitionId") String processDefinitionId
            , @RequestParam(value = "type") Integer type) {
        try {
            Long sysUserId = SysUserUtils.getLoginSysUserId(request);
            System.out.println(processDefinitionId + "-=======================processDefinitionId");
            Map<String, String> oaTaoHong = oaHessianService.getOaTaoHong(processDefinitionId);
            System.out.println(oaTaoHong + "-=======================oaTaoHong");
            String pathUrl = "";
            //获取保留word
            if (ObjectUtils.isNotNull(oaTaoHong)) {
                //公文（红头）修改状态
               *//* if (2 == type) {
                    List<OaApproval> oaApprovals = oaApprovalBiz.find(null, " sysUserId=" + sysUserId + " and timeStamp=" + processDefinitionId);
                    if (ObjectUtils.isNotNull(oaApprovals)) {
                        OaApproval oaApproval = new OaApproval();
                        oaApproval.setId(oaApprovals.get(0).getId());
                        oaApproval.setApprovalStatus(1);
                        oaApprovalBiz.update(oaApproval);
                    }
                }*//*
                pathUrl = request.getSession().getServletContext().getRealPath(oaTaoHong.get("pathUrl"));
            }
            String realPath = request.getServletContext().getRealPath("");
            String substring = realPath.substring(realPath.lastIndexOf("\\") + 1);
            if (!substring.equals("webapp")) {
                pathUrl = "file://" + pathUrl;
            }
            Map<String, String> map = SysUserUtils.getLoginSysUser(request);
            PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
            poCtrl.setServerPage(request.getContextPath() + "/poserver.zz");
            if(1==type){
                poCtrl.addCustomToolButton("显示编辑痕迹", "showRevision", 9);
                poCtrl.addCustomToolButton("隐藏编辑痕迹", "hideRevision", 18);
            }
            poCtrl.setAllowCopy(false);//禁止拷贝
            poCtrl.setMenubar(false);//隐藏菜单栏
            poCtrl.setOfficeToolbars(false);//隐藏Office工具条
            poCtrl.setJsFunction_AfterDocumentOpened("onProgressComplete()");
            poCtrl.webOpen(pathUrl, OpenModeType.docReadOnly, map.get("userName"));
            request.setAttribute("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
            request.setAttribute("type",type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oa_history_word;
    }*/

    /*// 拷贝文件
    public void copyFile(HttpServletRequest request, String oldPath, String newPath) {

        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时 
                InputStream inStream = new FileInputStream(oldPath); //读入原文件 
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小 
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }

    }

    @RequestMapping("/ajax/judgmentContent")
    @ResponseBody
    public Map<String, Object> judgmentContent(@RequestParam("processDefinitionId") String processDefinitionId) {
        List<TaoHong> taoHongs = taoHongBiz.find(null, " processDefinitionId=" + processDefinitionId);
        if (ObjectUtils.isNull(taoHongs)) {
            return resultJson(ErrorCode.SUCCESS, "请打开文档编辑内容", null);
        }
        return resultJson("1", null, null);
    }

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }*/
}
