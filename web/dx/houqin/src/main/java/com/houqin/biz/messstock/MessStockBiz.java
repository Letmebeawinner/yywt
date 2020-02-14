package com.houqin.biz.messstock;

import com.a_268.base.core.BaseBiz;
import com.houqin.biz.food.FoodTypeBiz;
import com.houqin.biz.goodsunit.GoodsunitBiz;
import com.houqin.dao.messstock.MessStockDao;
import com.houqin.entity.food.FoodType;
import com.houqin.entity.goodsunit.Goodsunit;
import com.houqin.entity.messstock.MessStock;
import com.houqin.entity.outstock.OutStock;
import com.houqin.utils.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 食堂库存列表管理
 *
 * @author wanghailong
 * @create 2017-06-15-上午 10:04
 */
@Service
public class MessStockBiz extends BaseBiz<MessStock, MessStockDao> {
    @Autowired
    private GoodsunitBiz goodsunitBiz;
    @Autowired
    private FoodTypeBiz foodTypeBiz;

    /**
     * 批量添加食堂库存
     *
     * @param myFile
     * @param request
     * @return
     */
    public String batchImportMess(MultipartFile myFile, HttpServletRequest request) throws Exception {

        StringBuffer msg = new StringBuffer();

        // datalist拼装List<String[]> datalist,
        HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
        HSSFSheet sheet = wookbook.getSheetAt(0);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        for (int i = 1; i < rows + 1; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null) {
                String name = getCellValue(row.getCell((short) 0));//商品名称
                String units = getCellValue(row.getCell((short) 1));//单位
                String foodTypeContent = getCellValue(row.getCell((short) 2));//进货名称
                String expirationTime = getCellValue(row.getCell((short) 3));//过期时间
                String content = getCellValue(row.getCell((short) 4));//备注

                if (com.a_268.base.util.StringUtils.isTrimEmpty(name)) {
                    msg.append("第" + i + "行商品名称填写错误;");
                    break;
                }
                if (com.a_268.base.util.StringUtils.isTrimEmpty(units)) {
                    msg.append("第" + i + "行单位填写错误;");
                    break;
                }
                if (com.a_268.base.util.StringUtils.isTrimEmpty(foodTypeContent)) {
                    msg.append("第" + i + "行进货名称填写错误;");
                    break;
                }
                List<Goodsunit> goodsunits = goodsunitBiz.find(null, " name= '" + units +"'");
                if (goodsunits.size() == 0) {
                    msg.append("第" + i + "行单位填写错误;");
                    break;
                }
                List<FoodType> foodTypes = foodTypeBiz.find(null, " content= '" + foodTypeContent+"'");
                if (foodTypes.size() == 0) {
                    FoodType foodType=new FoodType();
                    foodType.setContent(foodTypeContent);
                    foodTypeBiz.save(foodType);
//                    msg.append("第" + i + "行进货名称填写错误;");
//                    break;
                }
                MessStock messStock = new MessStock();
                messStock.setName(name);
                messStock.setFoodTypeContent(foodTypeContent);
                messStock.setUnits(units);
                messStock.setContent(content);
                messStock.setExpirationTime(sdf.parse(expirationTime));
                save(messStock);
            }
        }
        return msg.toString();
    }

    /**
     * @param cell
     * @return
     * @Description 获得Hsscell内容
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

}
