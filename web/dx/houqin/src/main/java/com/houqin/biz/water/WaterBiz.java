package com.houqin.biz.water;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.dao.water.WaterDao;
import com.houqin.entity.sysuser.SysUser;
import com.houqin.entity.water.Water;
import com.houqin.entity.water.WaterDto;
import com.houqin.entity.water.WaterType;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 水表
 *
 * @author lianyuchao
 *         Created by Administrator on 2016/12/15.
 */
@Service
public class WaterBiz extends BaseBiz<Water, WaterDao> {

    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private WaterTypeBiz waterTypeBiz;

    public List<WaterDto> getWaterDtoList(Pagination pagination, String whereSql) {
        List<Water> waterList = this.find(pagination, whereSql);
        List<WaterDto> list = new ArrayList<>();
        if (waterList != null && waterList.size() > 0) {
            for (Water water : waterList) {
                WaterDto waterDto = new WaterDto();
                SysUser sysUser = baseHessianBiz.getSysUserById(water.getUserId());
                WaterType waterType=waterTypeBiz.findById(water.getWaterType());
                waterDto.setContext(water.getContext());
                if (!ObjectUtils.isEmpty(sysUser)) {
                    waterDto.setUserName(sysUser.getUserName());
                }
                if(!ObjectUtils.isEmpty(waterType)){
                    waterDto.setWaterTypeName(waterType.getType());
                }
                waterDto.setCreateTime(water.getCreateTime());
                waterDto.setContext(water.getContext());
                waterDto.setPrice(water.getPrice());
                waterDto.setTunnage(water.getTunnage());
                waterDto.setId(water.getId());
                waterDto.setUserId(water.getUserId());
                waterDto.setAffirm(water.getAffirm());
                waterDto.setMonthTime(water.getMonthTime());
                list.add(waterDto);
            }
        }
        return list;
    }

    /**
     * @Description: 将水变成拓展类, 同时加上价格
     * @author: lzh
     * @Param: [water, price]
     * @Return: com.houqin.entity.water.WaterDto
     * @Date: 11:54
     */
    public WaterDto convertWaterToDto(Water water, Double price) {
        WaterDto waterDto = new WaterDto();
        BeanUtils.copyProperties(water, waterDto);
//        waterDto.setPrice(price);
        return waterDto;
    }


    public String waterImport(MultipartFile myFile, HttpServletRequest request) throws Exception {

        StringBuffer msg = new StringBuffer();
        HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
        HSSFSheet sheet = wookbook.getSheetAt(0);
        Water water = new Water();

        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        for (int i = 1; i < rows + 1; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null) {
                String monthTime = getCellValue(row.getCell((short) 0));//月份
                if (StringUtils.isTrimEmpty(monthTime)) {
                    msg.append("第" + i + "行月份不能为空;");
                    continue;
                }
                String typeId = getCellValue(row.getCell((short) 1));//用水区域
                String previousDegrees = row.getCell((short) 2).toString();//上期读数
                if (StringUtils.isTrimEmpty(previousDegrees)) {
                    msg.append("第" + i + "行上期读数不能为空;");
                    continue;
                }
                String currentDegrees = row.getCell((short) 3).toString();//本期读数
                if (StringUtils.isTrimEmpty(currentDegrees)) {
                    msg.append("第" + i + "行本期读数不能为空;");
                    continue;
                }
                String price = row.getCell((short) 4).toString();//单价
                if (StringUtils.isTrimEmpty(price)) {
                    msg.append("第" + i + "行单价不能为空;");
                    continue;
                }
                if (!StringUtils.isEmpty(msg)) {
                    return msg.toString();
                }
                Long userId = SysUserUtils.getLoginSysUserId(request);
                water.setUserId(userId);
                water.setMonthTime(monthTime);
                water.setWaterType(Long.valueOf(typeId));
                water.setPreRead(Double.valueOf(previousDegrees));
                water.setCurRead(Double.valueOf(currentDegrees));
                water.setPrice(new BigDecimal(price));
                BigDecimal aDouble =  new BigDecimal(currentDegrees).subtract(new BigDecimal(previousDegrees)).setScale(2);

                water.setTunnage(aDouble.doubleValue());
                BigDecimal eleFee = aDouble.multiply(new BigDecimal(price)).setScale(2);
                water.setEleFee(eleFee);
                this.save(water);
            }
        }
        return null;
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
}
