package com.houqin.controller.goodstype;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.houqin.biz.goodstype.GoodstypeBiz;
import com.houqin.entity.goodstype.Goodstype;
import com.houqin.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 物品分类
 *
 * @author lianyuchao
 *         Created by Administrator on 2016/12/15.
 */
@Controller
@RequestMapping("/admin/houqin")
public class GoodstypeController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(GoodstypeController.class);

    //添加物品分类
    private static final String createGoodstype = "/goodstype/add-goodstype";
    //更新物品分类
    private static final String toUpdateGoodstype = "/goodstype/update-goodstype";
    //物品分类列表
    private static final String goodstypeList = "/goodstype/goodstype_list";

    @Autowired
    private GoodstypeBiz goodstypeBiz;

    @InitBinder({"goodstype"})
    public void initGoodstype(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("goodstype.");
    }


    /**
     * 查询全部物品分类
     *
     * @param request
     * @param pagination
     * @return
     */
    @RequestMapping("/queryAllGoodstype")
    public String queryAllGoodstype(HttpServletRequest request,
                                    @ModelAttribute("pagination") Pagination pagination,
                                    @ModelAttribute("goodstype") Goodstype goodstype) {
        try {
            String whereSql = GenerateSqlUtil.getSql(goodstype);
            whereSql = whereSql + " order by sort desc";
            pagination.setRequest(request);
            List<Goodstype> goodsTypeList = goodstypeBiz.find(pagination, whereSql);
            request.setAttribute("goodstypeList", goodsTypeList);
        } catch (Exception e) {
            logger.error("GoodstypeController--queryAllGoodstype", e);
            return this.setErrorPath(request, e);
        }
        return goodstypeList;
    }


    /**
     * 去添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddGoodstype")
    public String toAddGoodstype(HttpServletRequest request) {
        try {

        } catch (Exception e) {
            logger.error("GoodstypeController--toAddGoodstype", e);
            return this.setErrorPath(request, e);
        }
        return createGoodstype;

    }

    /**
     * 添加分类
     *
     * @param request
     * @param goodstype
     * @return
     */
    @RequestMapping("/addSaveGoodstype")
    @ResponseBody
    public Map<String, Object> addSaveGoodstype(HttpServletRequest request, @ModelAttribute("goodstype") Goodstype goodstype) {
        Map<String, Object> resultMap = null;
        try {
            goodstypeBiz.save(goodstype);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("GoodstypeController--addSaveGoodstype", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * 去添加页面
     *
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateGoodstype")
    public String toUpdateGoodstype(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            Goodstype goodstype = goodstypeBiz.findById(id);
            request.setAttribute("goodstype", goodstype);
        } catch (Exception e) {
            logger.error("GoodstypeController--toUpdateGoodstype", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateGoodstype;
    }

    /**
     * 修改物品分类
     *
     * @param request
     * @param goodstype
     * @return
     */
    @RequestMapping("/updateGoodstype")
    @ResponseBody
    public Map<String, Object> updateGoodstype(HttpServletRequest request, @ModelAttribute("goodstype") Goodstype goodstype) {
        Map<String, Object> resultMap = null;
        try {
            goodstypeBiz.update(goodstype);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("GoodstypeController--updateGoodstype", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * 根据id删除物品分类
     *
     * @param request
     * @return
     */
    @RequestMapping("/delGoodstype")
    @ResponseBody
    public Map<String, Object> delGoodstype(HttpServletRequest request) {
        Map<String, Object> resultMap = null;
        try {
            String id = request.getParameter("id");
            goodstypeBiz.deleteById(Long.parseLong(id));
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("GoodstypeController--delGoodstype", e);
            resultMap = this.resultJson(ErrorCode.SYS_ERROR_MSG, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }
}
