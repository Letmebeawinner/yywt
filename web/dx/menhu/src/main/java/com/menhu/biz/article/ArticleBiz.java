package com.menhu.biz.article;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.menhu.biz.common.JiaoWuHessianBiz;
import com.menhu.biz.common.OAHessianBiz;
import com.menhu.dao.article.ArticleDao;
import com.menhu.entity.article.Article;
import com.menhu.entity.article.ArticleApp;
import com.menhu.enums.ArticleEnum;
import com.menhu.enums.Status;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 资讯Biz
 *
 * @author guoshiqi
 * @create 2016-12-09-9:43
 */
@Service
public class ArticleBiz extends BaseBiz<Article,ArticleDao> {
    private static   Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Resource
    private OAHessianBiz oaHessianBiz;

    @Resource
    private JiaoWuHessianBiz jiaoWuHessianBiz;



    /**
     * 资讯列表
     */
    public Map<String,Object> queryArticleList(Pagination pagination,Long  typeId,String title){
        Map<String,Object> objectMap =new HashMap<>();
        try {
            if(typeId.intValue()== ArticleEnum.政策法规.getType()){
                objectMap = oaHessianBiz.getRuleList(pagination,title);
                objectMap.put("articleList",objectMap.get("ruleList"));
                return objectMap;
            }else if(typeId.intValue()== ArticleEnum.通知公告.getType()){
                objectMap = oaHessianBiz.getNoticeList(pagination,title);
                if(ObjectUtils.isNotNull(objectMap)){
                    objectMap.put("articleList",objectMap.get("ruleList"));
                }
                return objectMap;
            }else if(typeId.intValue()== ArticleEnum.教学动态.getType()){
                objectMap = jiaoWuHessianBiz.teachingInfoList(pagination,title);
                objectMap.put("articleList",objectMap.get("teachingInfoList"));
                return objectMap;
            }else{
                String sql="";
                if(!StringUtils.isTrimEmpty(title)){
                    sql=" and title like '%"+title+"%'";
                }
                pagination.setPageSize(6);

                String whereSql = " status="+ Status.正常数据.getStatus() +" and typeId ="+typeId +sql +" ORDER BY  CASE WHEN DATE_SUB(CURDATE(), INTERVAL 7 DAY) < date(createTime) THEN clickTimes ELSE 0 END DESC ";
                List<Article> articleList = find(pagination, whereSql);
                objectMap.put("articleList",articleList);
                objectMap.put("pagination",pagination);
                return objectMap;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获得相关的热门
     */
    public Object hotList(Long typeId){
        if(typeId.intValue()== ArticleEnum.政策法规.getType()){
            Map<String,Object>  map= oaHessianBiz.getRuleRelated();
            return map.get("ruleList");
        }else if(typeId.intValue()== ArticleEnum.通知公告.getType()){
            Map<String,Object>  map=oaHessianBiz.getNoticeRelated();
            return map.get("ruleList");
        }else if(typeId.intValue()== ArticleEnum.教学动态.getType()){
            return jiaoWuHessianBiz.hotTeachingInfoList();
        }else{
            String whereSql = " status="+ Status.正常数据.getStatus() +" and typeId ="+typeId+" order by clickTimes desc limit 10";
            return find(null,whereSql);
        }
    }

    /**
     * 获得某个资讯的内容
     */
    public Object queryArticleInfo(Long id,Long typeId){
        if(typeId.intValue()== ArticleEnum.政策法规.getType()){
            return  oaHessianBiz.getRuleById(id);
        }else if(typeId.intValue()== ArticleEnum.通知公告.getType()){
            return  oaHessianBiz.getNoticeById(id);
        }else if(typeId.intValue()== ArticleEnum.教学动态.getType()){
            return  jiaoWuHessianBiz.getTeachingInfoById(id);
        }else{
            Article article = this.findById(id);//获得信息
            Integer clickTimes = article.getClickTimes()+1;
            article.setClickTimes(clickTimes);
            this.update(article);//更新访问数量
            return article;
        }
    }

    /**
     * 整理数据
     */
    public List<ArticleApp> initArticle(List<Article> articleList, String tempContextUrl){
        try {
            List<ArticleApp>  articleAppList =new ArrayList<>();
            for (Article article: articleList) {
                ArticleApp articleApp=new ArticleApp(article);
                List<String> list=gson.fromJson(article.getPicture(),new TypeToken<List<String>>(){}.getType());
                articleApp.setPictureList(list);
                checkImg(articleApp, tempContextUrl);
                articleAppList.add(articleApp);
            }
            return  articleAppList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 整理数据
     */
    public List<ArticleApp> initArticle(List<Article> articleList){
        try {
            List<ArticleApp>  articleAppList =new ArrayList<>();
            for (Article article: articleList) {
                ArticleApp articleApp=new ArticleApp(article);
                List<String> list=gson.fromJson(article.getPicture(),new TypeToken<List<String>>(){}.getType());
                articleApp.setPictureList(list);
                articleAppList.add(articleApp);
            }
            return  articleAppList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void checkImg(Article article, String tempContextUrl) {
        String content=article.getContent();
        String IMG="<img src=\"";
        if(content!=null && content.indexOf(IMG)>-1){
            int index=0;
            while(index>-1){
                index=content.indexOf(IMG,index);
                if(index>-1){
                    index+=10;
                    String start=content.substring(index,index+7);
                    if(!"http://".equals(start)){
                        content=content.substring(0,index)+tempContextUrl+content.substring(index);
                    }
                }
            }
            article.setContent(content);
        }
    }
}
