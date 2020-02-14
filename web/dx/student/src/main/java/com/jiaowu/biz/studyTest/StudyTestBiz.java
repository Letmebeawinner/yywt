package com.jiaowu.biz.studyTest;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jiaowu.biz.common.JiaoWuHessianService;
import com.jiaowu.dao.studyTest.StudyTestDao;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.studyTest.StudyTest;
import com.jiaowu.entity.user.User;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by 李帅雷 on 2017/8/23.
 */
@Service
public class StudyTestBiz extends BaseBiz<StudyTest, StudyTestDao> {

    /**
     * JSON工具类
     */
    protected static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Autowired
    private JiaoWuHessianService jiaoWuHessianService;

    public String batchImportStudyTest(MultipartFile myFile, HttpServletRequest request) throws Exception {
        StringBuffer msg = new StringBuffer();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(myFile.getInputStream());
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
        for (int i = 1; i < rows + 1; i++) {
            // 读取左上端单元格
            HSSFRow row = sheet.getRow(i);
            // 行不为空
            if (row != null) {
                //用户名
                String name = getCellValue(row.getCell((short) 0));
                if (StringUtils.isTrimEmpty(name)) {
                    msg.append("第" + i + "行用户名不能为空;");
                    continue;
                }
                name = name.trim().replaceAll(" ", "");
                //班级名称
                String classesName = getCellValue(row.getCell((short) 1));
                if (StringUtils.isTrimEmpty(classesName)) {
                    msg.append("第" + i + "行班级名称不能为空;");
                    continue;
                }
                classesName = classesName.trim().replaceAll(" ", "");
                User user;
                //班级
                Map<String, Object> classesMap = jiaoWuHessianService.queryClassesList(null, " name='" + classesName.trim() + "'");
                String classesStr = gson.toJson(classesMap.get("classesList"));
                List<Classes> classesList = gson.fromJson(classesStr, new TypeToken<List<Classes>>() {
                }.getType());
                Classes classes;
                if (ObjectUtils.isNull(classesList)) {
                    msg.append("第" + i + "行该班次已不存在;");
                    continue;
                } else {
                    classes = classesList.get(0);
                    //培训人员列表
                    String whereSql = "classId=" + classes.getId() + " and name = '" + name.trim() + "'";
                    Map<String, Object> map = jiaoWuHessianService.userList(null, whereSql);
                    String userList1 = gson.toJson(map.get("userList"));
                    List<User> userList2 = gson.fromJson(userList1, new TypeToken<List<User>>() {
                    }.getType());
                    if (ObjectUtils.isNull(userList2)) {
                        msg.append("第" + i + "行该学员不在当前班级;");
                        continue;
                    } else {
                        user = userList2.get(0);
                    }
                }
                StudyTest studyTest = new StudyTest();
                //毕（结）业证号
                String graduateNumber = getCellValue(row.getCell((short) 2));
                if (StringUtils.isTrimEmpty(graduateNumber) && !isNumeric(graduateNumber)) {
                    msg.append("第" + i + "行毕（结）业证号不能为空，且为数字;");
                    continue;
                } else {
                    studyTest.setGraduateNumber(graduateNumber);
                }
                //在线学习
                String onlineStudy = getCellValue(row.getCell((short) 3));
                double onlineStudyValue = 0;
                if (StringUtils.isTrimEmpty(onlineStudy)) {
                    msg.append("第" + i + "行在线学习不能为空;");
                    continue;
                } else {
                    BigDecimal onlineStudyBg = new BigDecimal(onlineStudy);
                    onlineStudyValue = onlineStudyBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    studyTest.setOnlineStudy(onlineStudyValue);
                }
                //调研报告
                String searchReport = getCellValue(row.getCell((short) 4));
                double searchReportValue;
                if (StringUtils.isTrimEmpty(searchReport)) {
                    msg.append("第" + i + "行调研报告不能为空，且大于0;");
                    continue;
                } else {
                    BigDecimal searchReportBg = new BigDecimal(searchReport);
                    searchReportValue = searchReportBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    studyTest.setSearchReport(searchReportValue);
                }
                String graduateTest = getCellValue(row.getCell((short) 5));//毕业考试
                double graduateTestValue;
                if (StringUtils.isTrimEmpty(graduateTest)) {
                    msg.append("第" + i + "行毕业考试不能为空，且大于0;");
                    continue;
                } else {
                    BigDecimal graduateTestBg = new BigDecimal(graduateTest);
                    graduateTestValue = graduateTestBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    studyTest.setGraduateTest(graduateTestValue);
                }
                String note = getCellValue(row.getCell((short) 6));//备注
                if (!StringUtils.isTrimEmpty(note)) {
                    studyTest.setNote(note);
                }
                studyTest.setUserId(user.getId());
                studyTest.setName(user.getName());
                studyTest.setClassId(classes.getId());
                studyTest.setClassTypeId(classes.getClassTypeId());
                studyTest.setTotal(onlineStudyValue * 0.2 + searchReportValue * 0.3 + graduateTestValue * 0.5);
                List<StudyTest> studyTestList = this.find(null, " userId=" + user.getId() + " and classId=" + classes.getId());
                if (ObjectUtils.isNotNull(studyTestList)) {
                    StudyTest studyTest1 = studyTestList.get(0);
                    //BeanUtils.copyProperties(studyTest, studyTest1);
                    studyTest1.setGraduateNumber(graduateNumber);
                    studyTest1.setOnlineStudy(onlineStudyValue);
                    studyTest1.setSearchReport(searchReportValue);
                    studyTest1.setGraduateTest(graduateTestValue);
                    studyTest1.setUserId(user.getId());
                    studyTest1.setName(user.getName());
                    studyTest1.setClassId(classes.getId());
                    studyTest1.setClassTypeId(classes.getClassTypeId());
                    studyTest1.setTotal(onlineStudyValue * 0.2 + searchReportValue * 0.3 + graduateTestValue * 0.5);
                    if (!StringUtils.isTrimEmpty(note)) {
                        studyTest1.setNote(note);
                    }
                    this.update(studyTest1);
                } else {
                    this.save(studyTest);
                }
            }
        }
        return msg.toString();
    }

    /**
     * 判断字符穿是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.equals("")) {
            return false;
        }
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
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
