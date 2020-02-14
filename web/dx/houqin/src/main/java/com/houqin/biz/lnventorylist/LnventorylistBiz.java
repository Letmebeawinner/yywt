package com.houqin.biz.lnventorylist;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.StringUtils;
import com.houqin.dao.lnventorylist.LnventorylistDao;
import com.houqin.entity.lnventorylist.Lnventorylist;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/11/14 0014.
 */
@Service
public class LnventorylistBiz extends BaseBiz<Lnventorylist, LnventorylistDao> {

    public String batchImport(MultipartFile myFile, HttpServletRequest request, Long meetingId) throws Exception {

        StringBuffer msg = new StringBuffer();
        HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
        HSSFSheet sheet = wookbook.getSheetAt(0);

        // 指的行数，一共有多少行+
        int rows = sheet.getLastRowNum();
        for (int i = 1; i < rows + 1; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null) {
                String name = getCellValue(row.getCell((short) 0));
                String type = getCellValue(row.getCell((short) 1));
                String unit = getCellValue(row.getCell((short) 2));
                String count = getCellValue(row.getCell((short) 3));
                String ysStatus = getCellValue(row.getCell((short) 4));

                Lnventorylist lnventorylist = new Lnventorylist();
                if (StringUtils.isTrimEmpty(name)) {
                    msg.append("第" + i + "行,设备设施名称,格式错误;");
                    continue;
                } else {
                    lnventorylist.setName(name);
                }


                if (StringUtils.isTrimEmpty(type)) {
                    msg.append("第" + i + "行,规格型号,格式错误;");
                    continue;
                } else {
                    lnventorylist.setType(type);
                }

                if (StringUtils.isTrimEmpty(unit)) {
                    msg.append("第" + i + "行,单位,格式错误;");
                    continue;
                } else {
                    lnventorylist.setUnit(unit);
                }

                if (!com.houqin.utils.StringUtils.isNumber(count)) {
                    msg.append("第" + i + "行,数量,格式错误;");
                    continue;
                } else {
                    lnventorylist.setCount(Long.parseLong(count));
                }

                if (StringUtils.isTrimEmpty(ysStatus)) {
                    msg.append("第" + i + "行,验收状态,格式错误;");
                    continue;
                } else {
                    lnventorylist.setYsStatus(ysStatus);
                }
                lnventorylist.setMeetingId(meetingId);
                lnventorylist.setStatus(0);
                super.save(lnventorylist);
            }
        }
        return msg.toString();
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
