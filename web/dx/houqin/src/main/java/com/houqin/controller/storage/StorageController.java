package com.houqin.controller.storage;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.stock.StockRecordBiz;
import com.houqin.biz.storage.StorageBiz;
import com.houqin.entity.stock.StockRecord;
import com.houqin.entity.stock.StockRecordDto;
import com.houqin.entity.storage.Storage;
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
 * 库房类型
 *
 * @author wanghailong
 * @create 2017-05-19-上午 11:35
 */
@Controller
@RequestMapping("/admin/houqin")
public class StorageController extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(StorageController.class);
    //添加库房类型
    private static final String addStorage = "/storage/add_storage";
    //修改库房类型
    private static final String toUpdateStorage = "/storage/update_storage";
    //库房类型列表
    private static final String storageList = "/storage/storage_list";
    //获取系统用户
    private static final String sysuser_list = "/storage/sysuser_list";

    @Autowired
    private StorageBiz storageBiz;

    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private StockRecordBiz stockRecordBiz;

    /**
     * 绑数据
     */
    @InitBinder({"storage"})
    public void initRepairType(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("storage.");
    }

    @InitBinder("stockRecord")
    public void initStockRecord(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("stockRecord.");
    }

    /**
     * 跳转到添加页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toAddStorage")
    public String toAddStorage(HttpServletRequest request) {
        return addStorage;
    }

    /**
     * 进行添加数据
     *
     * @param request
     * @param storage
     * @return
     */
    @RequestMapping("/addStorage")
    @ResponseBody
    public Map<String, Object> addStorage(HttpServletRequest request,
                                          @ModelAttribute("storage") Storage storage) {
        Map<String, Object> resultMap = null;
        try {
            storageBiz.save(storage);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "添加成功", null);
        } catch (Exception e) {
            logger.error("StorageController--addStorage", e);
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
    @RequestMapping("/storageList")
    public String storageList(HttpServletRequest request, @ModelAttribute("pagination") Pagination pagination, @ModelAttribute("storage") Storage storage) {
        try {
            Long userId = SysUserUtils.getLoginSysUserId(request);
            //查询用户角色
            String roleIds = baseHessianBiz.queryUserRolesByUserId(userId);
            if (userId != 1 && roleIds.indexOf("31") == -1) {
                storage.setSysId(userId);
            }
            String whereSql = GenerateSqlUtil.getSql(storage);
            pagination.setPageSize(10);
            List<Storage> storageList = storageBiz.find(pagination, whereSql);
            request.setAttribute("storageList", storageList);
            request.setAttribute("storage", storage);
        } catch (Exception e) {
            logger.error("StorageController--storageList", e);
        }
        return storageList;
    }

    /**
     * 跳转到更新页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/toUpdateStorage")
    public String toUpdateStorage(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        try {
            Storage storage = storageBiz.findById(id);
            request.setAttribute("storage", storage);
        } catch (Exception e) {
            logger.error("StorageController--toUpdateStorage", e);
        }
        return toUpdateStorage;
    }

    /**
     * 更新信息
     *
     * @param request
     * @param storage
     * @return
     */
    @RequestMapping("/updateStorage")
    @ResponseBody
    public Map<String, Object> updateStorage(HttpServletRequest request,
                                             @ModelAttribute("storage") Storage storage) {
        Map<String, Object> resultMap = null;
        try {
            storageBiz.update(storage);
            resultMap = this.resultJson(ErrorCode.SUCCESS, "修改成功", null);
        } catch (Exception e) {
            logger.error("StorageController--updateStorage", e);
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
    @RequestMapping("/deleteStorage")
    @ResponseBody
    public Map<String, Object> deleteStorage(HttpServletRequest request) {
        Map<String, Object> resultMap = null;
        try {
            String id = request.getParameter("id");
            storageBiz.deleteById(Long.parseLong(id));
            resultMap = this.resultJson(ErrorCode.SUCCESS, "删除成功", null);
        } catch (Exception e) {
            logger.error("StorageController--deleteStorage", e);
            return this.resultJson(ErrorCode.ERROR_DATA, "系统错误，请稍后再操作", null);
        }
        return resultMap;
    }

    /**
     * 获取系统用户
     *
     * @param request
     * @param pagination
     * @param where
     * @return
     */
    @RequestMapping("/storageGetSystemUserList")
    public String storageGetSystemUserList(HttpServletRequest request, @ModelAttribute("pagination") com.a_268.base.core.Pagination pagination, String where) {
        try {
            //List<SysUser> sysUserList = baseHessianBiz.getSysUserList(new SysUser());
            Map<String, Object> map = baseHessianBiz.querySysUserList(null, " userType=2");
            List<Map<String, String>> sysUserList = (List<Map<String, String>>) map.get("userList");
            request.setAttribute("sysUserList", sysUserList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sysuser_list;
    }


    /**
     * 入库记录列表
     *
     * @return
     */
    @RequestMapping("/storageReport")
    public String storageReport(HttpServletRequest request, Pagination pagination, @ModelAttribute("stockRecord") StockRecord stockRecord) {
        try {
            List<StockRecordDto> stockRecordDtos = stockRecordBiz.getStockRecordDto(pagination, stockRecord);
            request.setAttribute("stockRecordDtos", stockRecordDtos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/stockRecord/join_stock_list";
    }

    /**
     * 入库记录列表
     *
     * @return
     */
    @RequestMapping("/record/joinStock")
    public String joinStockRecord(HttpServletRequest request, Pagination pagination, @ModelAttribute("stockRecord") StockRecord stockRecord) {
        try {
            pagination.setRequest(request);
            List<StockRecordDto> stockRecordDtos = stockRecordBiz.getStockRecordDto(pagination, stockRecord);
            request.setAttribute("stockRecordDtos", stockRecordDtos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/stockRecord/join_stock_list";
    }

    @RequestMapping("/join/storage/detail")
    public String joinStorageDetail(@RequestParam("id") Long id, HttpServletRequest request) {
        try {
            StockRecordDto stockRecordDto = stockRecordBiz.getStockDtoById(id);
            request.setAttribute("stock", stockRecordDto);
        } catch (Exception e) {
            logger.error("StorageController.joinStorageDetail", e);
            return this.setErrorPath(request, e);
        }
        return "/stockRecord/detail_joinStock";
    }


}
