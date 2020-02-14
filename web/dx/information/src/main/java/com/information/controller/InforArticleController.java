package com.information.controller;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.information.dao.InfoArticleDao;
import com.information.entity.InfoArticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

@Controller
public class InforArticleController extends BaseController {

    @Autowired
    private InfoArticleDao infoArticleDao;

    private static Logger logger = LoggerFactory.getLogger(InforArticleController.class);

    /**
     * 添加
     *
     * @return
     */
    @RequestMapping("/addInfoArticle")
    @ResponseBody
    public Map<String, Object> addInfoArticle(@ModelAttribute("infoArticle") InfoArticle infoArticle) {
        Map<String, Object> json = null;
        try {
            infoArticle.setItemID(0);
            infoArticle.setTableName("");
            infoArticle.setLinkUrl("");
            infoArticle.setFileUrl("");
            infoArticle.setInputer("");
            infoArticle.setEditor("");
            infoArticle.setHits(0);
            infoArticle.setDayHits(0);
            infoArticle.setWeekHits(0);
            infoArticle.setMonthHits(0);
            infoArticle.setAddTime(new Date());
            infoArticle.setCreateTime(new Date());
            infoArticle.setUpdateTime(new Date());
            infoArticle.setStatus(6);
            infoArticle.setLastHitTime(new Date());
            infoArticle.setTitleFontColor("");
            infoArticle.setTitleFontType("");
            infoArticle.setIncludePic("");
            infoArticle.setFilePath("");
            infoArticle.setFileName("");
            infoArticle.setFileType("");
            infoArticle.setEliteLevel(0);
            infoArticle.setIsADLink(0);
            infoArticle.setADLink("");
            infoArticle.setTopImage("");
            infoArticle.setLangID(0);
            infoArticle.setSubTitle("");
            infoArticle.setSmallContent("");
            infoArticle.setIsHot(0);
            infoArticle.setIsRecommend(0);
            infoArticle.setIsKommentar(0);
            infoArticle.setIsReplaceKeyWord(0);
            infoArticle.setIsAutoPage(0);
            infoArticle.setAutoPageStrNum(0);
            infoArticleDao.addInfoArticle(infoArticle);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, infoArticle);
        } catch (Exception e) {
            logger.error("InfoArticleCotroller.addInfoArticle", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    @RequestMapping("/article/del")
    @ResponseBody
    public Map<String, Object> delArticleById(@RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            infoArticleDao.delInfoArticle(id);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("InfoArticleController.delArticleById", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


}
