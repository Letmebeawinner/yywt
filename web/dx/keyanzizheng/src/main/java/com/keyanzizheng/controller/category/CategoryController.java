package com.keyanzizheng.controller.category;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.keyanzizheng.biz.category.CategoryBiz;
import com.keyanzizheng.biz.result.ResultFormBiz;
import com.keyanzizheng.constant.PaginationConstants;
import com.keyanzizheng.constant.StatusConstants;
import com.keyanzizheng.entity.category.Category;
import com.keyanzizheng.entity.category.CategoryDTO;
import com.keyanzizheng.entity.result.ResultForm;
import com.keyanzizheng.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 类别下的二级类型
 * <p>CRUD<p>
 *
 * @author YaoZhen
 * @date 12-20, 15:22, 2017.
 */
@Controller
@RequestMapping("admin/ky")
public class CategoryController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    /**
     * 当前线程的req
     */
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CategoryBiz categoryBiz;
    @Autowired
    private ResultFormBiz resultFormBiz;

    /**
     * 数据绑定到实体
     *
     * @param binder 自定义数据类型绑定
     */
    @InitBinder("category")
    public void categoryInit(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.setFieldDefaultPrefix("category.");
    }

    /**
     * 填写类别
     *
     * @return 添加表单
     */
    @RequestMapping("saveCategory")
    public ModelAndView saveCategory() {
        ModelAndView mv = new ModelAndView("/category/save_category");
        try {
            // 显示成果形式
            final CopyOnWriteArrayList<ResultForm> cowList = getResultForms();
            mv.addObject("resultForms", cowList);
        } catch (Exception e) {
            logger.error("CategoryController.saveCategory", e);
        }

        return mv;
    }

    /**
     * 添加类别
     *
     * @param category 二级分类
     * @return map
     */
    @RequestMapping("doSaveCategory")
    @ResponseBody
    public Map<String, Object> doSaveCategory(@ModelAttribute("category") Category category) {
        Map<String, Object> objMap;
        try {
            objMap = verification(category);
            if (objMap != null) {
                return objMap;
            }
            categoryBiz.save(category);
            objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("CategoryController.doSaveCategory", e);
            objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return objMap;
    }

    /**
     * 列表
     *
     * @param pagination 分页
     * @param category   类型 包含形式id
     * @return 页面
     */
    @RequestMapping("listCategory")
    public ModelAndView listCategory(@ModelAttribute("pagination") Pagination pagination,
                                     @ModelAttribute("category") Category category) {
        ModelAndView mv = new ModelAndView("/category/list_category");
        try {
            final CopyOnWriteArrayList<ResultForm> cowList = getResultForms();
            mv.addObject("resultForms", cowList);

            pagination.setPageSize(PaginationConstants.PAGE_SIZE);
            pagination.setRequest(request);
            List<CategoryDTO> categoryDTOList = categoryBiz.findDTOList(pagination, category);
            mv.addObject("categoryDTOList", categoryDTOList);
        } catch (Exception e) {
            logger.error("CategoryController.listCategory", e);
        }
        return mv;
    }

    private CopyOnWriteArrayList<ResultForm> getResultForms() {
        // 显示成果形式
        List<ResultForm> resultForms = resultFormBiz.findAll();
        // 移除内刊和课题
        final CopyOnWriteArrayList<ResultForm> cowList = new CopyOnWriteArrayList<>(resultForms);
        for (ResultForm rf : cowList) {
            if ("课题".equals(rf.getName()) || "其他".equals(rf.getName())) {
                cowList.remove(rf);
            }
        }
        return cowList;
    }

    /**
     * 删除
     *
     * @param id 类型id
     * @return 假删
     */
    @RequestMapping("removeCategory")
    @ResponseBody
    public Map<String, Object> removeCategory(Long id) {
        Map<String, Object> objMap;
        try {
            Category category = categoryBiz.findById(id);
            if (category != null) {
                category.setStatus(StatusConstants.DONE);
                categoryBiz.update(category);
            }
            objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("CategoryController.removeCategory", e);
            objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return objMap;
    }

    /**
     * 修改表单
     *
     * @param id 自增主键
     * @return 回显
     */
    @RequestMapping("updateCategory")
    public ModelAndView updateCategory(Long id) {
        ModelAndView mv = new ModelAndView("/category/update_category");
        try {
            final CopyOnWriteArrayList<ResultForm> cowList = getResultForms();
            mv.addObject("resultFormList", cowList);

            Category category = categoryBiz.findById(id);
            mv.addObject("category", category);
        } catch (Exception e) {
            logger.error("CategoryController.updateCategory", e);
        }
        return mv;
    }

    /**
     * 修改成果
     *
     * @param category 绑定表单
     * @return json
     */
    @RequestMapping("/doUpdateCategory")
    @ResponseBody
    public Map<String, Object> doUpdateCategory(@ModelAttribute("category") Category category) {
        Map<String, Object> objMap;
        try {
            objMap = verification(category);
            if (objMap != null) {
                return objMap;
            }
            categoryBiz.update(category);
            objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch (Exception e) {
            logger.error("CategoryController.doUpdateCategory", e);
            objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return objMap;
    }

    /**
     * 验证表单
     *
     * @param category 实体类
     * @return 数据库也非空
     */
    public Map<String, Object> verification(Category category) {
        if (category.getResultFormId() == null) {
            return this.resultJson(ErrorCode.ERROR_PARAMETER_NULL, "请选择成果类别", null);
        }
        if (StringUtil.isBlank(category.getName())) {
            return this.resultJson(ErrorCode.ERROR_PARAMETER_NULL, "请填写类型名称", null);
        }
        return null;
    }

    /**
     * 二级联动
     *
     * @param selectedId 选择的父级id
     * @return 子集列表
     */
    @RequestMapping(value = "/secondaryLinkage")
    @ResponseBody
    public Map<String, Object> secondaryLinkage(Integer selectedId) {
        Map<String, Object> objMap;
        try {
            List<Category> categoryList = categoryBiz.find(null,
                    "resultFormId = " + selectedId + " order by sort desc");
            objMap = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, categoryList);
        } catch (Exception e) {
            logger.error("AchievementController.secondaryLinkage", e);
            objMap = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return objMap;
    }

}
