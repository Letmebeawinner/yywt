package com.houqin.biz.elepower;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.houqin.dao.elepower.ElePowerDao;
import com.houqin.entity.elepower.ElePower;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * 供电局抄表数
 *
 * @author YaoZhen
 * @date 05-24, 09:38, 2018.
 */
@Service
public class ElePowerBiz extends BaseBiz<ElePower, ElePowerDao> {

    public void saveEle(ElePower elePower, Map<String, String> user) {
        Long userId = Long.valueOf(user.get("id"));
        String userName = user.get("userName");
        elePower.setUserId(userId);
        elePower.setUserName(userName);
        super.save(elePower);
    }

    public String elepowerImport(MultipartFile myFile, HttpServletRequest request) throws Exception {

        StringBuffer msg = new StringBuffer();
        HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
        HSSFSheet sheet = wookbook.getSheetAt(0);
        ElePower elepower = new ElePower();

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
                elepower.setUserId(userId);
                elepower.setMonthTime(monthTime);
                elepower.setTypeId(Long.valueOf(typeId));
                elepower.setSecTypeId(Long.valueOf(secTypeId));
                elepower.setPreviousDegrees(Double.valueOf(previousDegrees));
                elepower.setCurrentDegrees(Double.valueOf(currentDegrees));
                elepower.setRate(Integer.valueOf(rate));
                elepower.setPrice(new BigDecimal(price));
                BigDecimal aDouble =  new BigDecimal(currentDegrees).subtract(new BigDecimal(previousDegrees));
                BigDecimal degrees = aDouble.multiply(new BigDecimal(rate)).setScale(2);

                elepower.setDegrees(degrees.doubleValue());
                BigDecimal eleFee = degrees.multiply(new BigDecimal(price)).setScale(2);
                elepower.setEleFee(eleFee);
                this.save(elepower);
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
