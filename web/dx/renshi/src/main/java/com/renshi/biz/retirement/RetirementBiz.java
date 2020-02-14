package com.renshi.biz.retirement;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.CollectionUtils;
import com.a_268.base.util.DateUtils;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.renshi.biz.common.SmsHessianService;
import com.renshi.biz.employee.EmployeeBiz;
import com.renshi.dao.retirement.RetirementDao;
import com.renshi.entity.employee.Employee;
import com.renshi.entity.employee.QueryEmployee;
import com.renshi.entity.retirement.QueryRetirement;
import com.renshi.entity.retirement.Retirement;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 离退休Biz
 *
 * @author 268
 */
@Service
public class RetirementBiz extends BaseBiz<Retirement, RetirementDao> {
    @Autowired
    private EmployeeBiz employeeBiz;

    public void tx_editRetirement(Retirement retirement, Long employeeId) {
        this.save(retirement);
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setStatus(2);
        employeeBiz.update(employee);

    }


    /**
     * 批量倒入退休教职工
     *
     * @param myFile
     * @param request
     * @return
     * @throws Exception
     */
    public String batchImportRetirement(MultipartFile myFile, HttpServletRequest request) throws Exception {

        StringBuffer msg = new StringBuffer();

        // datalist拼装List<String[]> datalist,
        HSSFWorkbook wookbook = new HSSFWorkbook(myFile.getInputStream());
        HSSFSheet sheet = wookbook.getSheetAt(0);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd");

        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        for (int i = 1; i < rows + 1; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null) {
                String name = getCellValue(row.getCell((short) 0));//姓名
                String sex = getCellValue(row.getCell((short) 1));//性别
                String birthday = getCellValue(row.getCell((short) 2));//出生年月
                String nationality = getCellValue(row.getCell((short) 3));//民族
                String workTime = getCellValue(row.getCell((short) 4));//参加工作时间
                String education = getCellValue(row.getCell((short) 5));//全日制学历学位
                String enterPartyTime = getCellValue(row.getCell((short) 6));//入党时间
                String applyTime = getCellValue(row.getCell((short) 7));//退休时间
                String presentPost = getCellValue(row.getCell((short) 8));//离退职务
                String category = getCellValue(row.getCell((short) 9));//类别
                String treatment = getCellValue(row.getCell((short) 10));//待遇


                Retirement retirement = new Retirement();
                retirement.setCategory(category);
                if (StringUtils.isTrimEmpty(applyTime)) {
                    retirement.setApplyTime(null);
                } else {
                    retirement.setApplyTime(sdf.parse(applyTime));
                }
                retirement.setTreatment(treatment);
                retirement.setPresentPost(presentPost);
                retirement.setName(name);
                if (sex.equals("男")) {
                    retirement.setSex(0);
                }
                if (sex.equals("女")) {
                    retirement.setSex(1);
                }
                retirement.setNationality(nationality);
                if (StringUtils.isTrimEmpty(birthday)) {
                    retirement.setBirthday(null);
                } else {
                    retirement.setBirthday(sdf1.parse(birthday.trim()));
                }

                if (StringUtils.isTrimEmpty(enterPartyTime)) {
                    retirement.setEnterpartyTime(null);
                } else {
                    retirement.setEnterpartyTime(sdf.parse(enterPartyTime.trim()));
                }

                if (StringUtils.isTrimEmpty(workTime)) {
                    retirement.setWorkTime(null);
                } else {
                    retirement.setWorkTime(sdf.parse(workTime.trim()));
                }
                retirement.setEducation(education);
                retirement.setStatus(0);
                save(retirement);
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
