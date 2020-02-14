package com.jiaowu.biz.partySpirit;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jiaowu.biz.common.JiaoWuHessianService;
import com.jiaowu.dao.partySpirit.PartySpiritDao;
import com.jiaowu.entity.classes.Classes;
import com.jiaowu.entity.partySpirit.PartySpirit;
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
 * Created by 李帅雷 on 2017/8/9.
 */
@Service
public class PartySpiritBiz extends BaseBiz<PartySpirit,PartySpiritDao> {


    /**
     * JSON工具类
     */
    protected static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Autowired
    private JiaoWuHessianService jiaoWuHessianService;


    public String batchImportPartySpirit(MultipartFile myFile, HttpServletRequest request) throws Exception {
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
                PartySpirit partySpirit = new PartySpirit();
//                个人党性分析材料
                String personMaterial = getCellValue(row.getCell((short) 2));
                double personMaterialValue = 0;
                if (StringUtils.isTrimEmpty(personMaterial) && !isNumeric(personMaterial)) {
                    msg.append("第" + i + "行个人党性分析材料不能为空，且为数字;");
                    continue;
                } else {
                    BigDecimal personMaterialBg = new BigDecimal(personMaterial);
                    personMaterialValue = personMaterialBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    partySpirit.setPersonMaterial(personMaterialValue);
                }

                //组织纪律
                String organisation = getCellValue(row.getCell((short) 3));
                double organisationValue = 0;
                if (StringUtils.isTrimEmpty(organisation)) {
                    msg.append("第" + i + "行组织纪律不能为空;");
                    continue;
                } else {
                    BigDecimal organisationBg = new BigDecimal(organisation);
                    organisationValue = organisationBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    partySpirit.setOrganisation(organisationValue);
                }
                //综合表现
                String allPerformance = getCellValue(row.getCell((short) 4));
                double allPerformanceValue;
                if (StringUtils.isTrimEmpty(allPerformance)) {
                    msg.append("第" + i + "行综合表现不能为空，且大于0;");
                    continue;
                } else {
                    BigDecimal allPerformanceBg = new BigDecimal(allPerformance);
                    allPerformanceValue = allPerformanceBg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    partySpirit.setAllPerformance(allPerformanceValue);
                }

                String note = getCellValue(row.getCell((short) 5));//备注
                if (!StringUtils.isTrimEmpty(note)) {
                    partySpirit.setNote(note);
                }

                partySpirit.setUserId(user.getId());
                partySpirit.setName(user.getName());
                partySpirit.setClassId(classes.getId());
                partySpirit.setClassTypeId(classes.getClassTypeId());
                partySpirit.setTotal(partySpirit.getPersonMaterial()*0.4+partySpirit.getOrganisation()*0.4+partySpirit.getAllPerformance()*0.2);
                List<PartySpirit> partySpiritList = this.find(null, " userId=" + user.getId() + " and classId=" + classes.getId());
                if (ObjectUtils.isNotNull(partySpiritList)) {
                    PartySpirit partySpirit1 = partySpiritList.get(0);
                    partySpirit1.setPersonMaterial(personMaterialValue);
                    partySpirit1.setOrganisation(organisationValue);
                    partySpirit1.setAllPerformance(allPerformanceValue);
                    partySpirit1.setUserId(user.getId());
                    partySpirit1.setName(user.getName());
                    partySpirit1.setClassId(classes.getId());
                    partySpirit1.setClassTypeId(classes.getClassTypeId());
                    partySpirit.setTotal(personMaterialValue*0.4+organisationValue*0.4+allPerformanceValue*0.2);
                    if (!StringUtils.isTrimEmpty(note)) {
                        partySpirit1.setNote(note);
                    }
                    this.update(partySpirit1);
                } else {
                    this.save(partySpirit);
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
