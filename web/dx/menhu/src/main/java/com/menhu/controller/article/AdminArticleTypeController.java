package com.menhu.controller.article;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.menhu.biz.article.ArticleTypeBiz;
import com.menhu.entity.article.ArticleType;
import com.menhu.enums.Status;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 资讯类型控制类
 *
 * @author guoshiqi
 * @create 2016-12-10-15:26
 */
@Controller
@RequestMapping("/admin/menhu/articleType")
public class AdminArticleTypeController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(AdminArticleTypeController.class);

    private static final String type_add = "admin/articleType/type_add";
    private static final String type_update = "admin/articleType/type_update";
    private static final String type_list = "admin/articleType/type_list";

    @Resource
    private ArticleTypeBiz articleTypeBiz;


    @InitBinder("articleType")
    public void initArticleTypes(WebDataBinder binder){
        binder.setFieldDefaultPrefix("articleType.");
    }

    /**
     * @Description: 跳转到添加页面
     * @author: Guoshiqi
     * @Param: [request, response]
     * @Return: java.lang.String
     * @Date: 2016/12/10
     */
    @RequestMapping("/toAddArticleType")
    public String toArticleType(HttpServletRequest request){
        try{
            String whereSql = "status="+ Status.正常数据.getStatus()+" and parentId=0";
            List<ArticleType> articleTypeList = articleTypeBiz.find(null,whereSql);
            request.setAttribute("articleTypeList",articleTypeList);
        }catch (Exception e){
            logger.info("ArticleTypeController--updateArticleType",e);
            return  this.setErrorPath(request,e);
        }
        return type_add;
    }


    /**
     * @Description: 添加资讯类型
     * @author: Guoshiqi
     * @Param: [request, response, archivesType]
     * @Return: Map
     * @Date: 2016/12/10
     */
    @RequestMapping("/addArticleType")
    @ResponseBody
    public Map<String,Object> addArticleType(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("articleType")ArticleType articleType){
        try{
            articleType.setStatus(0);
            articleTypeBiz.save(articleType);
        }catch (Exception e){
            logger.info("ArticleTypeController--updateArticleType",e);
            return  this.resultJson(ErrorCode.ERROR_DATA,ErrorCode.SYS_ERROR_MSG,null);
        }
        return  this.resultJson(ErrorCode.SUCCESS,ErrorCode.SUCCESS_MSG,"/admin/menhu/articleType/queryArticleType.json");
    }



    /**
     * @Description: 跳转到修改页面
     * @author: Guoshiqi
     * @Param: [request, response, id]
     * @Return: java.lang.String
     * @Date: 2016/12/12
     */
    @RequestMapping("/toUpdateArticleType")
    public String toUpdateArticleType(HttpServletRequest request,HttpServletResponse response,@RequestParam("id")Long id){
        try{
            String whereSql = "status="+ Status.正常数据.getStatus();
            List<ArticleType> articleTypeList = articleTypeBiz.find(null,whereSql);
            ArticleType articleType = articleTypeBiz.findById(id);
            request.setAttribute("articleType",articleType);
            request.setAttribute("articleTypeList",articleTypeList);
        }catch (Exception e){
            logger.info("ArticleTypeController--updateArticleType",e);
            return  this.setErrorPath(request,e);
        }
        return type_update;
    }


   /**
    * @Description: 修改资讯类型
    * @author: Guoshiqi
    * @Param: [request, response, archivesType]
    * @Return: Map
    * @Date: 2016/12/10
    */
   @RequestMapping("/updateArticleType")
   @ResponseBody
    public Map<String,Object> updateArticleType(HttpServletRequest request,HttpServletResponse response,@ModelAttribute("articleType")ArticleType articleType){
        try{
            articleTypeBiz.update(articleType);
        }catch (Exception e){
            logger.info("ArticleTypeController--updateArticleType",e);
            return this.resultJson(ErrorCode.ERROR_DATA,ErrorCode.SYS_ERROR_MSG,null);
        }
       return  this.resultJson(ErrorCode.SUCCESS,ErrorCode.SUCCESS_MSG,"/admin/menhu/articleType/queryArticleType.json");
    }


    /**
     * @Description: 删除资讯类型
     * @author: Guoshiqi
     * @Param: [request, response, id]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016/12/10
     */
    @RequestMapping("/delArticleType")
    @ResponseBody
    public Map<String,Object> delArticleType(HttpServletRequest request,HttpServletResponse response,@RequestParam("id")Long id){
        try{
            ArticleType articleType = articleTypeBiz.findById(id);
            articleType.setStatus(Status.删除数据.getStatus());
            articleTypeBiz.update(articleType);
        }catch (Exception e){
            logger.info("ArticleTypeController.java--delArchivesType",e);
            return  this.resultJson(ErrorCode.ERROR_DATA,ErrorCode.SYS_ERROR_MSG,null);
        }
        return this.resultJson(ErrorCode.SUCCESS,ErrorCode.SUCCESS_MSG,"/articleType/type_list");
    }

    /**
     * @Description: 查询类型列表
     * @author: Guoshiqi
     * @Param: [request, response, archivesType, pagination]
     * @Return: java.lang.String
     * @Date: 2016/12/12
     */
    @RequestMapping("/queryArticleType")
    public String queryArticleType(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("articleType")ArticleType articleType, @ModelAttribute("pagination") Pagination pagination){
        try{
            String whereSql = " status="+ Status.正常数据.getStatus();
            if(articleType!=null){
                if(StringUtils.isNotBlank(articleType.getName())){
                    whereSql+=" and name like '%"+articleType.getName()+"%'";
                }
                if(articleType.getId()!=null && articleType.getId()>0){
                    whereSql+=" and id ="+articleType.getId();
                }
            }
            List<ArticleType> list = articleTypeBiz.find(pagination,whereSql);
            request.setAttribute("typeList",list);
            pagination.setRequest(request);
            request.setAttribute("pagination",pagination);
        }catch (Exception e){
            logger.info("ArticleTypeController--queryArticleType",e);
            return  this.setErrorPath(request,e);
        }
        return type_list;
    }
}
