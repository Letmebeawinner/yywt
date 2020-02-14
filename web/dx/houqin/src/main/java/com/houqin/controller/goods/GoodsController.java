package com.houqin.controller.goods;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.SysUserUtils;
import com.google.common.collect.Lists;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.goods.GoodsBiz;
import com.houqin.biz.goodstype.GoodstypeBiz;
import com.houqin.biz.goodsunit.GoodsunitBiz;
import com.houqin.biz.storage.StorageBiz;
import com.houqin.biz.storehouse.StoreHouseBiz;
import com.houqin.entity.goods.Goods;
import com.houqin.entity.goods.GoodsDto;
import com.houqin.entity.goodstype.Goodstype;
import com.houqin.entity.goodsunit.Goodsunit;
import com.houqin.entity.storage.Storage;
import com.houqin.entity.storehouse.StoreHouse;
import com.houqin.entity.sysuser.SysUser;
import com.houqin.utils.GenerateSqlUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 物品管理
 *
 * @author ccl
 * @create 2016-12-19-15:23
 */
@Controller
@RequestMapping("/admin/houqin")
public class GoodsController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    private static final String createGoods = "/goods/add-goods";
    private static final String createGoodsBatch = "/goods/add-goods-batch";
    private static final String resultBack = "/goods/result-back";
    private static final String toUpdateGoods = "/goods/update-goods";
    private static final String detailGoods = "/goods/detail-goods";
    private static final String goodsList = "/goods/goods_list";


    @Autowired
    private GoodsBiz goodsBiz;
    @Autowired
    private GoodsunitBiz goodsunitBiz;
    @Autowired
    private GoodstypeBiz goodstypeBiz;
    @Autowired
    private StoreHouseBiz storehouseBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private StorageBiz storageBiz;

    @InitBinder({"goods"})
    public void initGoods(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("goods.");
    }


    /**
     * @Description:查询所有的物品
     * @author: ccl
     * @Param: [request, pagination]
     * @Return: java.lang.String
     * @Date: 2016-12-19
     */
    @RequestMapping("/queryAllGoods")
    public String queryAllGoods(HttpServletRequest request,
                                @ModelAttribute("pagination") Pagination pagination,
                                @ModelAttribute("goods") Goods goods) {
        try {

            List<Goodstype> goodTypeList = goodstypeBiz.getAllGoodstype();
            request.setAttribute("goodTypeList", goodTypeList);
            String whereSql = GenerateSqlUtil.getSql(goods);
            pagination.setRequest(request);

            List<GoodsDto> goodsList = goodsBiz.getAllGoodsDto(pagination, whereSql);
            request.setAttribute("goodsList", goodsList);
            request.setAttribute("goods", goods);
        } catch (Exception e) {
            logger.error("GoodsController.queryAllGoods", e);
            return this.setErrorPath(request, e);
        }
        return goodsList;
    }


    /**
     * 去添加物品
     *
     * @Description:
     * @author: ccl
     * @Param: [request]
     * @Return: java.lang.String
     * @Date: 2016-12-19
     */
    @RequestMapping("/toAddGoods")
    public String toAddGoods(HttpServletRequest request) {
        try {
            //物品单位
            List<Goodsunit> goodsunitList = goodsunitBiz.getAllGoodsunit();
            request.setAttribute("goodsunitList", goodsunitList);

            //物品类型
            List<Goodstype> goodTypeList = goodstypeBiz.getAllGoodstype();
            request.setAttribute("goodTypeList", goodTypeList);

            //获取当前人
            Map<String, String> user = SysUserUtils.getLoginSysUser(request);
            request.setAttribute("user", user);

            // 库房列表
            List<Storage> storageList = storageBiz.find(null, "1=1");
            request.setAttribute("storageList", storageList);
        } catch (Exception e) {
            logger.error("GoodsController.toAddGoods", e);
            return this.setErrorPath(request, e);
        }
        return createGoods;
    }

    /**
     * 批量入库页面
     * @param request
     * @return
     */
    @RequestMapping("/toAddGoodsBatch")
    public String toAddGoodsBatch(HttpServletRequest request){
        return createGoodsBatch;
    }

    /**
     * 批量入库
     *
     * @param request
     * @param myFile
     * @return
     */
    @RequestMapping("/importGoodsBatch")
    public String importGoodsBatch(HttpServletRequest request, @RequestParam("myFile") MultipartFile myFile) {
        
        try {
            List<Goods> list = parseExcelToList(myFile);
            if (ObjectUtils.isEmpty(list)){
                request.setAttribute("success",false);
                request.setAttribute("message","excel中缺少数据------------------------------------------------");
            }else {
                Long userId = SysUserUtils.getLoginSysUserId(request);
                saveGoodsList(list, userId);
                request.setAttribute("success", true);
                request.setAttribute("message", "批量入库成功");
            }
        } catch (RuntimeException e){
            request.setAttribute("success",false);
            request.setAttribute("message",e.getMessage());
        } catch (Exception e){
            logger.error("GoodsController.importGoodsBatch", e);
            request.setAttribute("success",false);
            request.setAttribute("message","系统异常");
        }
        return resultBack;
    }

    /**
     * 解析xml中的信息
     * @param multipartFile
     * @return
     */
    private List<Goods> parseExcelToList(MultipartFile multipartFile) throws RuntimeException {
        int flag = 0;
        String error="";
        List<Goods> list = Lists.newArrayList();
        try {
            // datalist拼装List<String[]> datalist,
            HSSFWorkbook wookbook = new HSSFWorkbook(multipartFile.getInputStream());
            HSSFSheet sheet = wookbook.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();// 指的行数，一共有多少行+

            List<Goodsunit> goodsUnitList = goodsunitBiz.getAllGoodsunit();
            
            for (int i = 1; i <= rows; i++) {
                flag = i;
                HSSFRow row = sheet.getRow(i);
                // 行不为空
                if (row != null) {
                    Goods goods = new Goods();
                    error="库房id";
                    String storageId = getCellValue(row.getCell(0)); //库房id
                    goods.setStorageId(parseValue(storageId));
                    
                    error="分类id";
                    String typeId = getCellValue(row.getCell(1)); //分类id
                    goods.setTypeId(parseValue(typeId));
                    
                    error="物品名称";
                    String name = getCellValue(row.getCell(2)); //商品名称
                    goods.setName(name);
                    
                    error="物品数量";
                    String num = getCellValue(row.getCell(3)); //商品数量
                    goods.setNum(parseValue(num));
                    
                    error="物品单位";
                    String unitName = getCellValue(row.getCell(4)); //商品单位
                    Goodsunit goodsunitByName = getGoodsunitByName(goodsUnitList, unitName);
                    goods.setUnitId(goodsunitByName.getId());
                    
                    error="规格型号";
                    String model = getCellValue(row.getCell(5)); //规格型号
                    goods.setModel(model);
                    
                    error="物品价格";
                    String price = row.getCell(6).toString(); //价   格
                    goods.setPrice(new BigDecimal(price));
                    list.add(goods);
                }
                
            }

        } catch (IOException e) {
            throw new RuntimeException("excel文件错误");
        } catch (NumberFormatException e){
            throw new RuntimeException("第" + flag + "行" +error +"错误");
        }
        return list;
    }

    /**
     * 判断当前名称的单位是否存在,如果不存在则创建一个新的单位
     * @param list
     * @param name
     * @return
     */
    private Goodsunit getGoodsunitByName(List<Goodsunit> list,String name){
        for (Goodsunit goodsunit : list) {
            if (name.equals(goodsunit.getName())){
                return goodsunit;
            }
        }
        Goodsunit goodsunit = new Goodsunit();
        goodsunit.setName(name);
        goodsunitBiz.save(goodsunit);
        list.add(goodsunit);
        return goodsunit;
    }

    /**
     * 解析xml中得到的数字 为合法数字
     * @param str
     * @return
     * @throws NumberFormatException
     */
    private Long parseValue(String  str) throws NumberFormatException{
        Long value = Long.parseLong(str);
        if (value <= 0){
            throw new NumberFormatException();
        }
        return value;
    }
    
    /**
     * 获得Hsscell内容
     *
     * @param cell
     * @return
     */
    public String getCellValue(HSSFCell cell) {
        String value = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_FORMULA:
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    DecimalFormat df = new DecimalFormat("0");
                    value = df.format(cell.getNumericCellValue());
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue().trim();
                    break;
                default:
                    value = "";
                    break;
            }
        }
        return value.trim();
    }


    private void saveGoodsList(List<Goods> list,Long userId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String code = sdf.format(new Date());
        for (Goods goods : list) {
            goods.setUserId(userId);
            goods.setCode(code);
            StoreHouse storehouse = new StoreHouse();
            if (!ObjectUtils.isEmpty(goods)) {
                storehouse.setTypeId(goods.getTypeId());
                storehouse.setUnitId(goods.getUnitId());
                storehouse.setUserId(userId);
                storehouse.setName(goods.getName());
                storehouse.setCode(goods.getCode());
                storehouse.setPrice(goods.getPrice());
                storehouse.setNum(goods.getNum());
                storehouse.setStorageId(goods.getStorageId());
                storehouse.setStorageNum("RK" + goods.getCode());
            }
            storehouseBiz.tx_addStoreHouse(goods, storehouse);
        }
    }










    /**
     * @Description:添加保存
     * @author: ccl
     * @Param: [request, goods]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-19
     */

    @RequestMapping("/addSaveGoods")
    @ResponseBody
    public Map<String, Object> addSaveGoods(HttpServletRequest request, @ModelAttribute("goods") Goods goods) {
        try {
            // 刷新页面后 会导致下拉框为0 同时通过js验证
            if (goods.getStorageId() == 0 || goods.getTypeId() == 0) {
                return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.ERROR_PARAMETER_VERIFY, null);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String code = sdf.format(new Date());
            Long userId = SysUserUtils.getLoginSysUserId(request);
            goods.setUserId(userId);
            goods.setCode(code);

            StoreHouse storehouse = new StoreHouse();
            if (!ObjectUtils.isEmpty(goods)) {
                storehouse.setTypeId(goods.getTypeId());
                storehouse.setUnitId(goods.getUnitId());
                storehouse.setUserId(userId);
                storehouse.setName(goods.getName());
                storehouse.setCode(goods.getCode());
                storehouse.setPrice(goods.getPrice());
                storehouse.setNum(goods.getNum());
                storehouse.setStorageId(goods.getStorageId());
                storehouse.setStorageNum("RK" + goods.getCode());
            }
            storehouseBiz.tx_addStoreHouse(goods, storehouse);
        } catch (Exception e) {
            logger.error("GoodsController.addSaveGoods", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);

        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }


    /**
     * @Description:去修改
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2016-12-19
     */
    @RequestMapping("/toUpdateGoods/{id}")
    public String toUpdateGoods(HttpServletRequest request, @PathVariable("id") Long id) {
        try {

            List<Goodstype> goodTypeList = goodstypeBiz.getAllGoodstype();
            request.setAttribute("goodTypeList", goodTypeList);

            List<Goodsunit> goodsunitList = goodsunitBiz.getAllGoodsunit();
            request.setAttribute("goodsunitList", goodsunitList);

            Goods goods = goodsBiz.findById(id);
            request.setAttribute("goods", goods);
        } catch (Exception e) {
            logger.error("GoodsController.toUpdateGoods", e);
            return this.setErrorPath(request, e);
        }
        return toUpdateGoods;
    }

    /**
     * @Description:修改
     * @author: ccl
     * @Param: [request, goods]
     * @Return: java.lang.String
     * @Date: 2016-12-19
     */
    @RequestMapping("/updateGoods")
    public String updateGoods(HttpServletRequest request, @ModelAttribute("goods") Goods goods) {
        try {
            goodsBiz.update(goods);
        } catch (Exception e) {
            logger.error("GoodsController.updateGoods", e);
            return this.setErrorPath(request, e);
        }
        return "redirect:/admin/houqin/queryAllGoods.json";
    }


    /**
     * @Description:删除物品
     * @author: ccl
     * @Param: [request]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Date: 2016-12-19
     */
    @RequestMapping("/delGoods")
    @ResponseBody
    public Map<String, Object> delGoods(HttpServletRequest request) {
        try {
            String goodsId = request.getParameter("id");
            storehouseBiz.tx_deleteStoreByGoodsId(Long.parseLong(goodsId));

        } catch (Exception e) {
            logger.error("GoodsController.delGoods", e);
            return this.resultJson(ErrorCode.ERROR_DATA, ErrorCode.SYS_ERROR_MSG, null);

        }
        return this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
    }

    /**
     * @Description:物品详情
     * @author: ccl
     * @Param: [request, id]
     * @Return: java.lang.String
     * @Date: 2016-12-20
     */
    @RequestMapping("/detailGoods/{id}")
    public String detailGoods(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            Goods goods = goodsBiz.findById(id);
            List<Goodsunit> goodsUnitList = goodsunitBiz.getAllGoodsunit();
            SysUser sysUser = baseHessianBiz.getSysUserById(goods.getUserId());
            List<Goodstype> goodTypeList = goodstypeBiz.getAllGoodstype();

            request.setAttribute("goodTypeList", goodTypeList);
            request.setAttribute("goodsUnitList", goodsUnitList);
            request.setAttribute("goods", goods);
            request.setAttribute("sysUser", sysUser);

            // 库房列表
            List<Storage> storageList = storageBiz.find(null, "1=1");
            request.setAttribute("storageList", storageList);
        } catch (Exception e) {
            logger.error("GoodsController.detailGoods", e);
            return this.setErrorPath(request, e);
        }
        return detailGoods;
    }


}
