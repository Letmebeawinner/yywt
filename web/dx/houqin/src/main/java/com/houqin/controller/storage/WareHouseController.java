package com.houqin.controller.storage;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.storage.WareHouseBiz;
import com.houqin.entity.storage.WareHouse;
import com.houqin.utils.GenerateSqlUtil;
import org.apache.commons.lang.StringUtils;
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
 * 库房类型
 *
 * @author wanghailong
 * @create 2017-05-19-上午 11:35
 */
@Controller
@RequestMapping("/admin/houqin")
public class WareHouseController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(WareHouseController.class);
    //添加库房类型
    private static final String addwareHouse = "/storage/add_wareHouse";
    //修改库房类型
    private static final String toUpdatewareHouse = "/storage/update_wareHouse";
    //库房类型列表
    private static final String wareHouseList = "/storage/wareHouse_list";

    @Autowired
    private WareHouseBiz wareHouseBiz;

    /**
     * 绑数据
     */
    @InitBinder({"wareHouse"})
    public void initWareHouse(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("wareHouse.");
    }

    /**
     * 跳转到添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddWareHouse")
    public String toAddWareHouse(HttpServletRequest request) {
        return addwareHouse;
    }

    /**
     * 进行添加数据
     *
     * @param request
     * @param wareHouse
     * @return
     */
    @RequestMapping("/addWareHouse")
    @ResponseBody
    public Map<String, Object> addWareHouse(HttpServletRequest request,
                                          @ModelAttribute("wareHouse") WareHouse wareHouse) {
        Map<String, Object> resultMap = null;
        try {
            wareHouseBiz.save(wareHouse);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("wareHouseController--addWareHouse", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * 查询库房的所有信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/wareHouseList")
    public String wareHouseList(HttpServletRequest request, @ModelAttribute("pagination") com.a_268.base.core.Pagination pagination,
                              @ModelAttribute("wareHouse") WareHouse wareHouse) {
        try {
            String whereSql = GenerateSqlUtil.getSql(wareHouse);
            pagination.setPageSize(10);
            List<WareHouse> wareHouseList = wareHouseBiz.find(pagination, whereSql);
            request.setAttribute("wareHouseList", wareHouseList);
        } catch (Exception e) {
            logger.error("wareHouseController--wareHouseList", e);
        }
        return wareHouseList;
    }

    /**
     * 跳转到更新页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toUpdateWareHouse")
    public String toUpdateWareHouse(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            WareHouse wareHouse = wareHouseBiz.findById(id);
            request.setAttribute("wareHouse", wareHouse);
        } catch (Exception e) {
            logger.error("wareHouseController--toUpdateWareHouse", e);
        }
        return toUpdatewareHouse;
    }

    /**
     * 更新信息
     *
     * @param request
     * @param wareHouse
     * @return
     */
    @RequestMapping("/updateWareHouse")
    @ResponseBody
    public Map<String, Object> updateWareHouse(HttpServletRequest request,
                                             @ModelAttribute("wareHouse") WareHouse wareHouse) {
        Map<String, Object> resultMap = null;
        try {
            wareHouseBiz.update(wareHouse);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("wareHouseController--updateWareHouse", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * 进行删除数据
     *
     * @param request
     * @return
     */
    @RequestMapping("/deleteWareHouse")
    @ResponseBody
    public Map<String, Object> deleteWareHouse(HttpServletRequest request) {
        Map<String, Object> resultMap = null;
        try {
            String id = request.getParameter("id");
            wareHouseBiz.deleteById(Long.parseLong(id));
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("wareHouseController--deleteWareHouse", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

}
