package com.menhu.controller.index;

import com.a_268.base.controller.BaseController;
import com.menhu.biz.article.ArticleBiz;
import com.menhu.biz.common.JiaoWuHessianBiz;
import com.menhu.biz.common.OAHessianBiz;
import com.menhu.entity.article.Article;
import com.menhu.enums.ArticleEnum;
import com.menhu.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class FrontIndexController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(FrontIndexController.class);


    private static final String index = "index/index";
    private static final String digitalLibrary = "index/digital_library";


    @Resource
    private ArticleBiz articleBiz;
    @Resource
    private OAHessianBiz oaHessianBiz;
    @Resource
    private JiaoWuHessianBiz jiaoWuHessianBiz;


    /**
     * 首页获取网站首页数据
     *
     * @Description:
     * @author: Licong
     * @Param: [request, response]
     * @Return: java.lang.String
     * @Date: 2016/12/29
     */
    @RequestMapping("/index")
    public String getIndexPage(HttpServletRequest request, HttpServletResponse response) {

        try {
            String whereSql = " status=" + Status.正常数据.getStatus();
            List<Article> newsList = articleBiz.find(null, whereSql + " and typeId =" + ArticleEnum.新闻资讯.getType() + " order by createTime desc limit 6");//新闻资讯
            List<Article> publicList = articleBiz.find(null, whereSql + " and typeId =" + ArticleEnum.信息公开.getType() + " order by createTime desc limit 6");//信息公开
            List<Article> KYList = articleBiz.find(null, whereSql + " and typeId =" + ArticleEnum.科研咨政.getType() + " order by createTime desc limit 6");//科研资政
            List<Article> onlineList = articleBiz.find(null, whereSql + " and typeId =" + ArticleEnum.在线交流.getType() + " order by createTime desc limit 6");//在线交流
            List<Article> articleBannerList = articleBiz.find(null, whereSql + " and isIndex =" + ArticleEnum.轮播显示.getType() + " order by createTime desc  ");//banner
            List<Article> noticeList = articleBiz.find(null, whereSql + " and typeId =" + ArticleEnum.通知公告.getType() + " order by createTime desc limit 5");//在线交流
            List<Article> ruleList = articleBiz.find(null, whereSql + " and typeId =" + ArticleEnum.政策法规.getType() + " order by createTime desc limit 6");//政策法规
            List<Article> teachingList = articleBiz.find(null, whereSql + " and typeId =" + ArticleEnum.教学动态.getType() + " order by createTime desc limit 5");//教学动态


            request.setAttribute("onlineList", onlineList);
            request.setAttribute("teachingList", teachingList);
            request.setAttribute("noticeList", noticeList);
            request.setAttribute("ruleList", ruleList);
            request.setAttribute("articleBannerList", articleBiz.initArticle(articleBannerList));
            request.setAttribute("newsList", newsList);
            request.setAttribute("publicList", publicList);
            request.setAttribute("KYList", KYList);
        } catch (Exception e) {
            logger.info("FrontIndexController-getIndexPage", e);
            return this.setErrorPath(request, e);
        }
        return index;
    }

    /**
     * 数字图书馆
     *
     * @Description:
     * @author: Licong
     * @Param: [request, response]
     * @Return: java.lang.String
     * @Date: 2018/10/24
     */
    @RequestMapping("/digitalLibrary")
    public String digitalLibrary(HttpServletRequest request, HttpServletResponse response) {

        try {

        } catch (Exception e) {
            logger.info("FrontIndexController-digitalLibrary", e);
            return this.setErrorPath(request, e);
        }
        return digitalLibrary;
    }

}


