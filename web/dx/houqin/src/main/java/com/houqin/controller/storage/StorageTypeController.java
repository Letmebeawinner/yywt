package com.houqin.controller.storage;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.houqin.biz.storage.StorageTypeBiz;
import com.houqin.entity.storage.StorageType;
import com.houqin.utils.GenerateSqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/houqin")
public class StorageTypeController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(StorageTypeController.class);

    /**
     * 绑数据
     */
    @InitBinder({"storageType"})
    public void initStorageType(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("storageType.");
    }

    @Autowired
    private StorageTypeBiz storageTypeBiz;


    /**
     * 添加出库类型
     *
     * @return
     */
    @RequestMapping("/toAddStorageType")
    public String toAddStorageType() {
        return "/storage/add_storage_type";
    }

    /**
     * 添加出库类型
     *
     * @param request
     * @return
     */
    @RequestMapping("/addStorageType")
    @ResponseBody
    public Map<String, Object> addStorageType(HttpServletRequest request, @ModelAttribute("storageType") StorageType storageType) {
        Map<String, Object> json = null;
        try {
            storageTypeBiz.save(storageType);
            json = this.resultJson(ErrorCode.SUCCESS, "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 去修改出库类型
     *
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateStorageType")
    public String toUpdateStorageType(HttpServletRequest request, @RequestParam("id") Long id) {
        try {
            StorageType storageType = storageTypeBiz.findById(id);
            request.setAttribute("storageType", storageType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/storage/update_storage_type";
    }

    /**
     * 修改出库类型
     *
     * @param request
     * @param storageType
     * @return
     */
    @RequestMapping("/updateStorageType")
    @ResponseBody
    public Map<String, Object> updateStorageType(HttpServletRequest request, @ModelAttribute("storageType") StorageType storageType) {
        Map<String, Object> json = null;
        try {
            storageTypeBiz.update(storageType);
            json = this.resultJson(ErrorCode.SUCCESS, "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping("/deleteStorageType")
    @ResponseBody
    public Map<String, Object> deleteStorageType(@RequestParam("id") Long id) {
        Map<String, Object> json = null;
        try {
            storageTypeBiz.deleteById(id);
            json = this.resultJson(ErrorCode.SUCCESS, "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 出库类型列表
     * @return
     */
    @RequestMapping("/storageTypeList")
    public ModelAndView storageTypeList(HttpServletRequest request, @ModelAttribute("storageType") StorageType storageType, @ModelAttribute("pagination") Pagination pagination){
        ModelAndView mv=new ModelAndView("/storage/storage_type_list");
        try {
            String whereSql= GenerateSqlUtil.getSql(storageType);
            whereSql+=" order by sort desc";
            List<StorageType> storageTypeList=storageTypeBiz.find(pagination,whereSql);
            mv.addObject("storageTypeList",storageTypeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mv;
    }


}
