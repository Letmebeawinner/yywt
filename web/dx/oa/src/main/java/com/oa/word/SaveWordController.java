package com.oa.word;

import com.a_268.base.controller.BaseController;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.SysUserUtils;
import com.oa.biz.letter.LetterModelBiz;
import com.oa.biz.word.TaoHongBiz;
import com.oa.common.FileUtils;
import com.oa.entity.letter.LetterModel;
import com.oa.entity.word.TaoHong;
import com.zhuozhengsoft.pageoffice.FileSaver;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiangdong.chang on 2017/12/26 0026.
 */
@Controller
public class SaveWordController extends BaseController {

    @Autowired
    private TaoHongBiz taoHongBiz;
    @Autowired
    private LetterModelBiz letterModelBiz;

    /**
     * 动态生成word
     *
     * @author: xiangdong.chang
     * @create: 2017/12/26 0026 16:36
     * @return:
     */
    @RequestMapping("/saveWord/copy")
    public void saveWord(HttpServletRequest request, HttpServletResponse response, @RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("type") String type) throws IOException {
        try {
            Long sysUserId = SysUserUtils.getLoginSysUserId(request);
            FileSaver fs = new FileSaver(request, response);
            String pathUrl = "TaoHong/dx/" + processDefinitionId + "/" + fs.getFileName();
            String url = request.getSession().getServletContext().getRealPath(pathUrl);
            FileUtils.createFile(url);
            fs.saveToFile(url);
            fs.close();
            //判断是否存在保留路径
            TaoHong taoHong = new TaoHong();
            taoHong.setPathUrl(pathUrl);
            taoHong.setSysUserId(sysUserId);
            System.out.println(processDefinitionId + "processDefinitionId======");
            taoHong.setProcessDefinitionId(processDefinitionId);
            List<TaoHong> taoHongs = taoHongBiz.find(null, " processDefinitionId=" + processDefinitionId + " order by createTime desc " /*+ " and sysUserId=" + sysUserId*/);
            if (ObjectUtils.isNotNull(taoHongs)) {
                taoHong.setId(taoHongs.get(0).getId());
                taoHong.setType(Integer.valueOf(type));
            }
            taoHongBiz.saveOrupdateTaoHong(taoHong);
            //fs.showPage(300,300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态生成word
     *
     * @author: xiangdong.chang
     * @create: 2017/12/26 0026 16:36
     * @return:
     */
    @RequestMapping("/saveModelWord/copy")
    public void saveModelWord(HttpServletRequest request, HttpServletResponse response, @RequestParam("modelId") Long modelId) {
        try {
            FileSaver fs = new FileSaver(request, response);
            LetterModel letterModel = letterModelBiz.findById(modelId);
            String url = request.getSession().getServletContext().getRealPath(letterModel.getFileUrl());
            FileUtils.createFile(url);
            fs.saveToFile(url);
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态生成word
     *
     * @author: xiangdong.chang
     * @create: 2017/12/26 0026 16:36
     * @return:
     */
    @RequestMapping("/saveWord/oaIssues/copy")
    public void saveoaIssWord(HttpServletRequest request, HttpServletResponse response, @RequestParam("processDefinitionId") Long processDefinitionId) {
        try {
            FileSaver fs = new FileSaver(request, response);
            String pathUrl = "issues/" + processDefinitionId + "/" + fs.getFileName();
            Map<String,Object> map =  new HashMap<>(2);
            map.put("fileUrl",pathUrl);
            map.put("fileName",fs.getFileName());
            redisCache.set("oa_" + processDefinitionId, map, 60 * 60);
            String url = request.getSession().getServletContext().getRealPath(pathUrl);
            FileUtils.createFile(url);
            fs.saveToFile(url);
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 动态生成word
     *
     * @author: xiangdong.chang
     * @create: 2017/12/26 0026 16:36
     * @return:
     */
    @RequestMapping("/saveWord/internalOfficialDocument/copy")
    public void internalOfficialDocument(HttpServletRequest request, HttpServletResponse response, @RequestParam("processDefinitionId") Long processDefinitionId) {
        try {
            FileSaver fs = new FileSaver(request, response);
            String pathUrl = "internalOfficialDocument/" + processDefinitionId + "/" + fs.getFileName();
            Map<String,Object> map =  new HashMap<>(2);
            map.put("fileUrl",pathUrl);
            map.put("fileName",fs.getFileName());
            redisCache.set("internal_" + processDefinitionId, map, 60 * 60);
            String url = request.getSession().getServletContext().getRealPath(pathUrl);
            FileUtils.createFile(url);
            fs.saveToFile(url);
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
