package com.oa.word;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.redis.RedisCache;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.conference.OaMeetingTopicBiz;
import com.oa.biz.letter.LetterModelBiz;
import com.oa.biz.letter.OaApprovalBiz;
import com.oa.biz.word.TaoHongBiz;
import com.oa.biz.workflow.OaLetterBiz;
import com.oa.common.FileUtils;
import com.oa.controller.message.MessageController;
import com.oa.entity.conference.OaMeetingTopic;
import com.oa.entity.letter.LetterModel;
import com.oa.entity.letter.OaApproval;
import com.oa.entity.word.TaoHong;
import com.oa.entity.workflow.OaLetter;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.wordwriter.DataRegion;
import com.zhuozhengsoft.pageoffice.wordwriter.WordDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 实现套红
 * Created by xiangdong on 2018/5/30 0030.
 */
@Controller
public class OpenWordController extends BaseController {
    private final static String oa_word = "/word/opWord";
    private final static String oa_oaIssuesWord = "/word/oaIssuesWord";
    private final static String oa_history_word = "/word/oaHistoryWord";
    private final static String oa_open_word = "/word/openWord";
    private static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private TaoHongBiz taoHongBiz;
    @Autowired
    private OaApprovalBiz oaApprovalBiz;
    @Autowired
    private LetterModelBiz letterModelBiz;
    @Autowired
    private OaMeetingTopicBiz oaMeetingTopicBiz;
    @Autowired
    private OaLetterBiz oaLetterBiz;

    /**
     * @param request
     * @param processDefinitionId 工作流id
     * @Description: 打开红头公文
     * @author: xiangdong.chang
     * @create: 2017/12/8 0008 14:20
     * @return:
     */
    @RequestMapping("/open/oaWord")
    public String oaWord(HttpServletRequest request,
                         @RequestParam(value = "processDefinitionId", required = false) String processDefinitionId
    ) {
        try {
            List<TaoHong> taoHongs = taoHongBiz.find(null, " processDefinitionId=" + processDefinitionId + " order by createTime desc");
            String fileName = "";
            String templatePath = "";
            String letterModeId = request.getParameter("letterModeId");
            String realPath = request.getServletContext().getRealPath("");
            String substring = realPath.substring(realPath.lastIndexOf("\\") + 1);
            PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
            if (letterModeId != null) {
                LetterModel letterModel = letterModelBiz.findById(Long.valueOf(letterModeId));
                // 复制模板，命名为正式发文的文件名：zhengshi.doc
                fileName = "zhengshi.doc";
                templatePath = request.getSession().getServletContext().getRealPath(letterModel.getFileUrl());
                String filePath = request.getSession().getServletContext().getRealPath("TaoHong/doc/" + fileName);
                copyFile(request, templatePath, filePath);
                // 正文内容到“zhengshi.doc”
                WordDocument doc = new WordDocument();
                DataRegion sTextS = doc.openDataRegion("PO_STextS");
                String url = request.getSession().getServletContext().getRealPath("TaoHong/doc/test.doc");
                if (ObjectUtils.isNotNull(taoHongs)) {
                    url = request.getSession().getServletContext().getRealPath(taoHongs.get(0).getPathUrl());
                }
                if (!substring.equals("webapp")) {
                    url = "file://" + url;
                }
                System.out.println("url============================url" + url);
                sTextS.setValue("[word]" + url + "[/word]");
                poCtrl.setWriter(doc);
            } else {
                //首次加载时，加载正文内容：test.doc
                fileName = "test.doc";
            }

            poCtrl.setServerPage(request.getContextPath() + "/poserver.zz");
            poCtrl.addCustomToolButton("保存关闭文件", "saveAndClose", 1);
            poCtrl.addCustomToolButton("隐藏编辑痕迹", "hideRevision", 18);

            /*poCtrl.addCustomToolButton("加盖印章", "InsertSeal()", 2);*/
            poCtrl.addCustomToolButton("显示编辑痕迹", "showRevision", 9);
            poCtrl.addCustomToolButton("打印", "ShowPrintDlg", 6);
            poCtrl.setJsFunction_AfterDocumentOpened("onProgressComplete()");
            poCtrl.setSaveFilePage("/saveWord/copy.json?processDefinitionId=" + processDefinitionId + "&type=" + letterModeId);

            String pathUrl = "";
            //选取的红头模版
            if (StringUtils.isEmpty(templatePath)) {
                pathUrl = request.getSession().getServletContext().getRealPath("TaoHong/doc/" + fileName);
            } else {
                pathUrl = templatePath;
            }
            //如果没有套红操作打开保存文档
            if (ObjectUtils.isNotNull(taoHongs) && letterModeId == null) {
                pathUrl = request.getSession().getServletContext().getRealPath(taoHongs.get(0).getPathUrl());
            }
            logger.info("substring:=========" + substring);
            if (!substring.equals("webapp")) {
                pathUrl = "file://" + pathUrl;
            }
            Map<String, String> map = SysUserUtils.getLoginSysUser(request);
            System.out.println("pathUrl============================pathUrl" + pathUrl);
            poCtrl.webOpen(pathUrl, OpenModeType.docRevisionOnly, map.get("userName"));
            //获取红头下拉框
            List<LetterModel> letterModelList = letterModelBiz.find(null, "1=1 order by sort desc");
            request.setAttribute("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
            request.setAttribute("processDefinitionId", processDefinitionId);
            if (ObjectUtils.isNotNull(taoHongs)) {
                letterModeId = taoHongs.get(0).getType().toString();
            }
            request.setAttribute("letterModeId", letterModeId);
            request.setAttribute("letterModelList", letterModelList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oa_word;
    }

    /**
     * @param request
     * @param processDefinitionId 工作流id
     * @Description: 预览
     * @author: xiangdong.chang
     * @create: 2017/12/8 0008 14:20
     * @return:
     */
    @RequestMapping("/open/oaHistoryWord")
    public String oaHistoryWord(HttpServletRequest request,
                                @RequestParam(value = "processDefinitionId") String processDefinitionId
            , @RequestParam(value = "type") Integer type) {
        try {
            Long sysUserId = SysUserUtils.getLoginSysUserId(request);
            List<TaoHong> taoHongs = taoHongBiz.find(null, " processDefinitionId=" + processDefinitionId);
            String pathUrl = "";
            //获取保留word
            if (ObjectUtils.isNotNull(taoHongs)) {
                //公文（红头）修改状态
                if (2 == type) {
                    List<OaApproval> oaApprovals = oaApprovalBiz.find(null, " sysUserId=" + sysUserId + " and timeStamp=" + processDefinitionId);
                    if (ObjectUtils.isNotNull(oaApprovals)) {
                        OaApproval oaApproval = new OaApproval();
                        oaApproval.setId(oaApprovals.get(0).getId());
                        oaApproval.setApprovalStatus(1);
                        oaApprovalBiz.update(oaApproval);
                    }
                }
                pathUrl = request.getSession().getServletContext().getRealPath(taoHongs.get(0).getPathUrl());
            }
            String realPath = request.getServletContext().getRealPath("");
            String substring = realPath.substring(realPath.lastIndexOf("\\") + 1);
            logger.info("substring:=========" + substring);
            if (!substring.equals("webapp")) {
                pathUrl = "file://" + pathUrl;
            }
            Map<String, String> map = SysUserUtils.getLoginSysUser(request);
            PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
            poCtrl.setServerPage(request.getContextPath() + "/poserver.zz");
            if (1 == type) {
                poCtrl.addCustomToolButton("显示编辑痕迹", "showRevision", 9);
                poCtrl.addCustomToolButton("隐藏编辑痕迹", "hideRevision", 18);
            }
            poCtrl.addCustomToolButton("打印", "ShowPrintDlg", 6);
            poCtrl.setAllowCopy(false);//禁止拷贝
            poCtrl.setMenubar(false);//隐藏菜单栏
            poCtrl.setOfficeToolbars(false);//隐藏Office工具条

            poCtrl.setJsFunction_AfterDocumentOpened("onProgressComplete()");
            poCtrl.webOpen(pathUrl, OpenModeType.docReadOnly, map.get("userName"));
            request.setAttribute("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
            request.setAttribute("type", type);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return oa_history_word;
    }


    /**
     * @param request
     * @param modelId 模板id
     * @Description: 预览
     * @author: xiangdong.chang
     * @create: 2017/12/8 0008 14:20
     * @return:
     */
    @RequestMapping("/open/oaModelWord")
    public String oaHistoryWord(HttpServletRequest request,
                                @RequestParam(value = "modelId") Long modelId) {
        try {
            LetterModel letterModel = letterModelBiz.findById(modelId);
            String pathUrl = request.getServletContext().getRealPath(letterModel.getFileUrl());
            String realPath = request.getServletContext().getRealPath("");
            String substring = realPath.substring(realPath.lastIndexOf("\\") + 1);
            logger.info("substring:=========" + substring);
            if (!substring.equals("webapp")) {
                pathUrl = "file://" + pathUrl;
            }
            Map<String, String> map = SysUserUtils.getLoginSysUser(request);
            PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
            poCtrl.setServerPage(request.getContextPath() + "/poserver.zz");
            poCtrl.setSaveFilePage("/saveModelWord/copy.json?modelId=" + modelId);
            poCtrl.webOpen(pathUrl, OpenModeType.docAdmin, map.get("userName"));
            request.setAttribute("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return oa_open_word;
    }

    // 拷贝文件
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
    }

    /**
     * @param request
     * @param processDefinitionId 工作流id
     * @Description: 打开议题
     * @author: xiangdong.chang
     * @create: 2017/12/8 0008 14:20
     * @return:
     */
    @RequestMapping("/open/oaIssuesWord")
    public String oaIssuesWord(HttpServletRequest request,
                               @RequestParam(value = "timeStamp", required = false) String processDefinitionId,
                               @RequestParam(value = "type", required = false, defaultValue = "1") Integer type
    ) {
        try {
            List<OaMeetingTopic> oaMeetingTopics = oaMeetingTopicBiz.find(null, " timeStamp=" + processDefinitionId);
            String fileName = "";
            PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
            //首次加载时，加载正文内容：test.doc
            fileName = "yiti.doc";
            if (type == 1) {
                poCtrl.addCustomToolButton("保存文件", "save", 1);
            }
            if (type != 1) {
                poCtrl.setAllowCopy(false);//禁止拷贝
                poCtrl.setMenubar(false);//隐藏菜单栏
                poCtrl.setOfficeToolbars(false);//隐藏Office工具条
            }
            poCtrl.setServerPage(request.getContextPath() + "/poserver.zz");
            poCtrl.setSaveFilePage("/saveWord/oaIssues/copy.json?processDefinitionId=" + processDefinitionId);
            String realPath = request.getServletContext().getRealPath("");
            String substring = realPath.substring(realPath.lastIndexOf("\\") + 1);
            String pathUrl = request.getSession().getServletContext().getRealPath("TaoHong/doc/" + fileName);
            if (FileUtils.exists(request.getSession().getServletContext().getRealPath("issues/" + processDefinitionId + "/" + fileName))) {
                pathUrl = request.getSession().getServletContext().getRealPath("issues/" + processDefinitionId + "/" + fileName);
            }
            //获取保留word
            if (ObjectUtils.isNotNull(oaMeetingTopics)) {
                pathUrl = request.getSession().getServletContext().getRealPath(oaMeetingTopics.get(0).getFileUrl());
            }
            logger.info("substring:=========" + substring);
            if (!substring.equals("webapp")) {
                pathUrl = "file://" + pathUrl;
            }
            Map<String, String> map = SysUserUtils.getLoginSysUser(request);
            if (type != 1) {
                poCtrl.setAllowCopy(true);
                poCtrl.webOpen(pathUrl, OpenModeType.docReadOnly, map.get("userName"));
            } else {
                poCtrl.webOpen(pathUrl, OpenModeType.docRevisionOnly, map.get("userName"));
            }
            request.setAttribute("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oa_oaIssuesWord;
    }

    /**
     * @param request
     * @param processDefinitionId 工作流id
     * @Description: 内部公文
     * @author: xiangdong.chang
     * @create: 2017/12/8 0008 14:20
     * @return:
     */
    @RequestMapping("/open/internalOfficialDocument")
    public String internalOfficialDocument(HttpServletRequest request,
                                           @RequestParam(value = "timeStamp", required = false) String processDefinitionId,
                                           @RequestParam(value = "type", required = false, defaultValue = "1") Integer type
    ) {
        try {
            List<OaLetter> oaLetters = oaLetterBiz.find(null, " timeStamp=" + processDefinitionId);
            String fileName = "";
            PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
            //首次加载时，加载正文内容：test.doc
            fileName = "neibugongwen.doc";
            if (type == 1) {
                poCtrl.addCustomToolButton("保存文件", "save", 1);
            }
            if (type != 1) {
                poCtrl.setAllowCopy(false);//禁止拷贝
                poCtrl.setMenubar(false);//隐藏菜单栏
                poCtrl.setOfficeToolbars(false);//隐藏Office工具条
            }
            poCtrl.setServerPage(request.getContextPath() + "/poserver.zz");
            poCtrl.setSaveFilePage("/saveWord/internalOfficialDocument/copy.json?processDefinitionId=" + processDefinitionId);
            String realPath = request.getServletContext().getRealPath("");
            String substring = realPath.substring(realPath.lastIndexOf("\\") + 1);
            String pathUrl = request.getSession().getServletContext().getRealPath("TaoHong/doc/" + fileName);
            if (FileUtils.exists(request.getSession().getServletContext().getRealPath("internalOfficialDocument/" + processDefinitionId + "/" + fileName))) {
                pathUrl = request.getSession().getServletContext().getRealPath("internalOfficialDocument/" + processDefinitionId + "/" + fileName);
            }
            //获取保留word
            if (ObjectUtils.isNotNull(oaLetters)) {
                pathUrl = request.getSession().getServletContext().getRealPath(oaLetters.get(0).getFileUrl());
            }
            logger.info("substring:=========" + substring);
            if (!substring.equals("webapp")) {
                pathUrl = "file://" + pathUrl;
            }
            Map<String, String> map = SysUserUtils.getLoginSysUser(request);
            if (type != 1) {
                poCtrl.webOpen(pathUrl, OpenModeType.docReadOnly, map.get("userName"));
            } else {
                poCtrl.webOpen(pathUrl, OpenModeType.docRevisionOnly, map.get("userName"));
            }
            request.setAttribute("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oa_oaIssuesWord;
    }
}
