package com.houqin.biz.natural;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.a_268.base.util.SysUserUtils;
import com.houqin.biz.common.BaseHessianBiz;
import com.houqin.dao.natural.NaturalDao;
import com.houqin.entity.natural.Natural;
import com.houqin.entity.natural.NaturalType;
import com.houqin.entity.natural.NaturalVO;
import com.houqin.entity.sysuser.SysUser;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 天燃气
 *
 * @author ccl
 * @create 2017-05-18-17:20
 */
@Service
public class NaturalBiz extends BaseBiz<Natural, NaturalDao> {
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private NaturalTypeBiz naturalTypeBiz;


    public List<NaturalVO> getOilDtoList(Pagination pagination, String whereSql) {
        List<Natural> naturals = this.find(pagination, whereSql);
        List<NaturalVO> vos = new ArrayList<>();

        if (ObjectUtils.isNotNull(naturals)) {
            for (Natural natural : naturals) {
                // 没有录入人姓名依然显示
                SysUser sysUser = baseHessianBiz.getSysUserById(natural.getUserId());
                NaturalType naturalType = naturalTypeBiz.findById(natural.getType());
                NaturalVO naturalVO = new NaturalVO();
                if (ObjectUtils.isNotNull(sysUser)) {
                    naturalVO.setName(sysUser.getUserName());
                }
                if (ObjectUtils.isNotNull(naturalType)) {
                    naturalVO.setTypeName(naturalType.getType());
                }
                // 拼装拓展对象
                naturalVO = convertOilToDto(natural, naturalVO);
                vos.add(naturalVO);
            }
        }
        return vos;
    }

    /**
     * 拓展柴油类
     *
     * @param natural
     * @return
     */
    public NaturalVO convertOilToDto(Natural natural, NaturalVO naturalVO) {
        BeanUtils.copyProperties(natural, naturalVO);
        return naturalVO;
    }


    public String naturalImport(MultipartFile myFile, HttpServletRequest request) throws Exception {

        StringBuffer msg = new StringBuffer();
        HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
        HSSFSheet sheet = wookbook.getSheetAt(0);
        Natural natural = new Natural();

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
                natural.setUserId(userId);
                natural.setMonthTime(monthTime);
                natural.setType(Long.valueOf(typeId));
                natural.setPreRead(Double.valueOf(previousDegrees));
                natural.setCurRead(Double.valueOf(currentDegrees));
                natural.setPrice(new BigDecimal(price));
                BigDecimal aDouble =  new BigDecimal(currentDegrees).subtract(new BigDecimal(previousDegrees)).setScale(2);

                natural.setAmount(aDouble);
                BigDecimal eleFee = aDouble.multiply(new BigDecimal(price)).setScale(2);
                natural.setEleFee(eleFee);
                this.save(natural);
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
