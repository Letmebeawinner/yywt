package com.menhu.controller.article;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.google.common.reflect.TypeToken;
import com.menhu.biz.article.ArticleBiz;
import com.menhu.biz.article.ArticleTypeBiz;
import com.menhu.entity.article.Article;
import com.menhu.entity.article.ArticleApp;
import com.menhu.entity.article.ArticleType;
import com.menhu.enums.ArticleEnum;
import com.menhu.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.soap.Addressing;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * app资讯
 */
@Controller
@RequestMapping("/api/app/menhu/article")
public class AppArticleController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(AppArticleController.class);

    @Resource
    private ArticleBiz articleBiz;
    @Autowired
    private ArticleTypeBiz articleTypeBiz;

    @InitBinder({"article"})
    public void initArticle(WebDataBinder binder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.setFieldDefaultPrefix("article.");
    }

    /**
     * @Description:app获得首页轮播资讯
     */
    @RequestMapping("/queryBannerArticle")
    @ResponseBody
    public Map<String,Object> queryBannerArticle(HttpServletRequest request, HttpServletResponse response){
        List<ArticleApp> articleAppList;
        try{
            StringBuffer url = request.getRequestURL();
            String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).toString();

            String whereSql = " status="+Status.正常数据.getStatus() +" and isIndex = "+  ArticleEnum.轮播显示.getType()+"  order by clickTimes desc ";
            List<Article> articleList = articleBiz.find(null,whereSql);
            articleAppList = articleBiz.initArticle(articleList,tempContextUrl);
        }catch (Exception e){
            logger.info("ArticleTypeController--queryBannerArticle",e);
            return  this.resultJson(ErrorCode.ERROR_DATA,ErrorCode.SYS_ERROR_MSG,null);

        }
        return  this.resultJson(ErrorCode.SUCCESS,ErrorCode.SUCCESS_MSG,articleAppList);
    }

    /**
     * @Description:app获得首页资讯
     * @author: Licong
     * @Param: [request, response]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016/12/22
     */
    @RequestMapping("/queryIndexArticle")
    @ResponseBody
    public Map<String,Object> queryArticle(HttpServletRequest request){
        List<ArticleApp> articleAppList;
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            StringBuffer url = request.getRequestURL();
            String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).toString();

            String whereSql = " status="+Status.正常数据.getStatus() +"  order by createTime desc limit 10";
            List<Article> articleList = articleBiz.find(null,whereSql);
            articleAppList = articleBiz.initArticle(articleList,tempContextUrl);
            map.put("articleAppList",articleAppList);
        }catch (Exception e){
            logger.info("ArticleTypeController--queryArticle",e);
            return  this.resultJson(ErrorCode.ERROR_DATA,ErrorCode.SYS_ERROR_MSG,null);
        }
        return  this.resultJson(ErrorCode.SUCCESS,ErrorCode.SUCCESS_MSG,map);
    }

    /**
     * @Description:app获得首页资讯详情
     * @author: Licong
     * @Param: [request, response, id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016/12/22
     */
    @RequestMapping("/queryArticleInfo")
    @ResponseBody
    public Map<String,Object> queryArticleInfo(HttpServletRequest request, @RequestParam("id")Long id){
         ArticleApp  articleApp;
        try{
            Article article = articleBiz.findById(id);
            articleApp=new ArticleApp(article);
            List<String> list=gson.fromJson(article.getPicture(),new TypeToken<List<String>>(){}.getType());
            articleApp.setPictureList(list);
            StringBuffer url = request.getRequestURL();
            String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).toString();
            if(articleApp!=null){
                articleBiz.checkImg(articleApp, tempContextUrl);
            }
        }catch (Exception e){
            logger.info("ArticleTypeController--queryArticleInfo",e);
            return  this.resultJson(ErrorCode.ERROR_DATA,ErrorCode.SYS_ERROR_MSG,null);
        }
        return  this.resultJson(ErrorCode.SUCCESS,ErrorCode.SUCCESS_MSG,articleApp);
    }

    /**
     * @Description:app获得首页资讯详情的相关资讯
     * @author: Licong
     * @Param: [request, response, id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016/12/22
     */
    @RequestMapping("/queryArticleCorrelation")
    @ResponseBody
    public Map<String,Object> queryArticleCorrelation(HttpServletRequest request ,@RequestParam("typeId")Long typeId,Pagination  pagination){
        List<ArticleApp> articleAppList;
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            StringBuffer url = request.getRequestURL();
            String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).toString();

            String whereSql = " status="+Status.正常数据.getStatus() +" and typeId ="+typeId  +" order by clickTimes desc ";
            List<Article> articleList = articleBiz.find(pagination,whereSql);
            articleAppList = articleBiz.initArticle(articleList,tempContextUrl);
            map.put("articleAppList",articleAppList);
            map.put("pagination",pagination);
        }catch (Exception e){
            logger.info("ArticleTypeController--queryArticleCorrelation",e);
            return  this.resultJson(ErrorCode.ERROR_DATA,ErrorCode.SYS_ERROR_MSG,null);
        }
        return  this.resultJson(ErrorCode.SUCCESS,ErrorCode.SUCCESS_MSG,map);
    }



    /**
     * 查看通知公告
     *
     * @param id
     * @return
     */
    @RequestMapping("/queryArticleList")
    @ResponseBody
    public Map<String, Object> queryNoticeList(HttpServletRequest request,@RequestParam("id") Long id, @ModelAttribute("pagination") Pagination pagination) {
        Map<String, Object> json = null;
        List<ArticleApp> articleAppList;
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            StringBuffer url = request.getRequestURL();
            String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).toString();

            pagination.setPageSize(10);
            String sql="status=0";
            if(id!=null && id>0){
                sql+=" and typeId=" + id;
            }
            List<Article> articleList = articleBiz.find(pagination, sql);
            articleAppList = articleBiz.initArticle(articleList,tempContextUrl);
            map.put("articleAppList",articleAppList);
            map.put("pagination",pagination);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, map);
        } catch (Exception e) {
            e.printStackTrace();
            json = this.resultJson(ErrorCode.ERROR_DATA,ErrorCode.SYS_ERROR_MSG,null);
        }
        return json;
    }

    /**
     * 查看文章详细通过id
     *
     * @param id
     * @return
     */
    @RequestMapping("/queryArticleById")
    @ResponseBody
    public Map<String, Object> queryArticleById(HttpServletRequest request,@RequestParam("id") Long id) {
        Map<String, Object> json = null;
        ArticleApp  articleApp;
        try {
            Article article = articleBiz.findById(id);
            StringBuffer url = request.getRequestURL();
            String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).toString();
            articleApp=new ArticleApp(article);
            List<String> list=gson.fromJson(article.getPicture(),new TypeToken<List<String>>(){}.getType());
            articleApp.setPictureList(list);
            if(articleApp!=null){
                articleBiz.checkImg(articleApp, tempContextUrl);
            }
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, articleApp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }




//    /**
//     * @Description:app获得资讯列表
//     * @author: Licong
//     * @Param: [request, response]
//     * @Return: java.util.Map<java.lang.String,java.lang.Object>
//     * @Date: 2016/12/22
//     */
//    @RequestMapping("/queryArticleList")
//    @ResponseBody
//    public Map<String,Object> queryArticleList(HttpServletRequest request, HttpServletResponse response,Pagination  pagination){
//        List<ArticleApp> articleAppList;
//        Map<String,Object> map = new HashMap<String,Object>();
//        try{
//            String whereSql = " status="+Status.正常数据.getStatus() +"  order by createTime desc limit 2";
//            List<Article> articleList = articleBiz.find(pagination,whereSql);
//            articleAppList = articleBiz.initArticle(articleList);
//            map.put("articleAppList",articleAppList);
//            map.put("pagination",pagination);
//        }catch (Exception e){
//            logger.info("ArticleTypeController--queryArticleList",e);
//            return  this.resultJson(ErrorCode.ERROR_DATA,ErrorCode.SYS_ERROR_MSG,null);
//        }
//        return  this.resultJson(ErrorCode.SUCCESS,ErrorCode.SUCCESS_MSG,map);
//    }

    /**
     * @Description: 资讯类型列表
     */
    @RequestMapping("/articleTypeList")
    @ResponseBody
    public Map<String,Object> articleTypeList() {
        try {
            Map<String,Object> map = new HashMap<String,Object>();
            String whereSql = "status=" + Status.正常数据.getStatus();
            List<ArticleType> articleTypeList = articleTypeBiz.find(null, whereSql);
            map.put("articleTypeList",articleTypeList);
            return  this.resultJson(ErrorCode.SUCCESS,ErrorCode.SUCCESS_MSG,map);
        } catch (Exception e) {
            logger.info("ArticleController--articleTypeList", e);
            return  this.resultJson(ErrorCode.ERROR_DATA,ErrorCode.SYS_ERROR_MSG,null);
        }
    }

}


