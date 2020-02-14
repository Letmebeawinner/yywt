package com.houqin.biz.electricity;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.biz.electricityType.EleSecTypeBiz;
import com.houqin.dao.electricity.ElectricityDao;
import com.houqin.entity.electricity.Electricity;
import com.houqin.entity.electricity.ElectricityDto;
import com.houqin.entity.sysuser.SysUser;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 电表
 *
 * @author lianyuchao
 *         Created by Administrator on 2016/12/15.
 */
@Service
public class ElectricityBiz extends BaseBiz<Electricity, ElectricityDao> {
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private EleSecTypeBiz eleSecTypeBiz;

    public List<ElectricityDto> getElectricityList(Pagination pagination, String whereSql) {
        List<Electricity> electricityList = this.find(pagination, whereSql);
        List<ElectricityDto> list = new ArrayList<>();

        Map<Long, String> secTypes = eleSecTypeBiz.findAllMap();

        if (electricityList != null && electricityList.size() > 0) {
            for (Electricity electricity : electricityList) {
                ElectricityDto electricityDto = new ElectricityDto();
                SysUser sysUser = baseHessianBiz.getSysUserById(electricity.getUserId());
                if (ObjectUtils.isNotNull(sysUser)) {
                    electricityDto.setUserName(sysUser.getUserName());
                }
                electricityDto.setContext(electricity.getContext());
                electricityDto.setDegrees(electricity.getDegrees());
                electricityDto.setPrice(electricity.getPrice());
                electricityDto.setCreateTime(electricity.getCreateTime());
                electricityDto.setId(electricity.getId());
                electricityDto.setTypeId(electricity.getTypeId());
                electricityDto.setAffirm(electricity.getAffirm());

                String secTypeName = secTypes.get(electricity.getSecTypeId());
                electricityDto.setSecTypeName(secTypeName == null ? "未选择" : secTypeName);
                list.add(electricityDto);
            }
        }
        return list;
    }

    /**
     * @Description: 将电拓展成拓展类
     * @author: lzh
     * @Param: [electricity, price]
     * @Return: com.houqin.entity.electricity.ElectricityDto
     * @Date: 11:56
     */
    public ElectricityDto convertWaterToDto(Electricity electricity, Double price) {
        ElectricityDto electricityDto = new ElectricityDto();
        BeanUtils.copyProperties(electricity, electricityDto);
//        electricityDto.setPrice(price);
        return electricityDto;
    }

    public String electricityImport(MultipartFile myFile, HttpServletRequest request) throws Exception {

        StringBuffer msg = new StringBuffer();
        HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
        HSSFSheet sheet = wookbook.getSheetAt(0);
        Electricity electricity = new Electricity();

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
                String typeId = getCellValue(row.getCell((short) 1));//用电区域
                String secTypeId = getCellValue(row.getCell((short) 2));//二级用电区域
                String previousDegrees = row.getCell((short) 3).toString();//上期读数
                if (StringUtils.isTrimEmpty(previousDegrees)) {
                    msg.append("第" + i + "行上期读数不能为空;");
                    continue;
                }
                String currentDegrees = row.getCell((short) 4).toString();//本期读数
                if (StringUtils.isTrimEmpty(currentDegrees)) {
                    msg.append("第" + i + "行本期读数不能为空;");
                    continue;
                }
                String rate = row.getCell((short) 5).toString();//倍率
                if (StringUtils.isTrimEmpty(rate)) {
                    msg.append("第" + i + "行倍率不能为空;");
                    continue;
                }
                String price = row.getCell((short) 6).toString();//单价
                if (StringUtils.isTrimEmpty(price)) {
                    msg.append("第" + i + "行单价不能为空;");
                    continue;
                }
                if (!StringUtils.isEmpty(msg)) {
                    return msg.toString();
                }
                Long userId = SysUserUtils.getLoginSysUserId(request);
                electricity.setUserId(userId);
                electricity.setMonthTime(monthTime);
                electricity.setTypeId(Long.valueOf(typeId));
                electricity.setSecTypeId(Long.valueOf(secTypeId));
                electricity.setPreviousDegrees(Double.valueOf(previousDegrees));
                electricity.setCurrentDegrees(Double.valueOf(currentDegrees));
                electricity.setRate(Integer.valueOf(rate));
                electricity.setPrice(new BigDecimal(price));

                BigDecimal aDouble =  new BigDecimal(currentDegrees).subtract(new BigDecimal(previousDegrees));
                BigDecimal degrees = aDouble.multiply(new BigDecimal(rate)).setScale(2);

                electricity.setDegrees(degrees.doubleValue());
                BigDecimal eleFee = degrees.multiply(new BigDecimal(price)).setScale(2);
                electricity.setEleFee(eleFee);
                this.save(electricity);
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
