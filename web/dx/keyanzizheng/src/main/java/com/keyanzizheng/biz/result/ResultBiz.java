package com.keyanzizheng.biz.result;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.*;
import com.keyanzizheng.biz.approvebill.ApproveBillBiz;
import com.keyanzizheng.biz.category.CategoryBiz;
import com.keyanzizheng.biz.common.BaseHessianBiz;
import com.keyanzizheng.biz.common.HrHessianBiz;
import com.keyanzizheng.biz.subsection.SubSectionBiz;
import com.keyanzizheng.common.EcologicalCivilizationConstants;
import com.keyanzizheng.constant.*;
import com.keyanzizheng.dao.result.ResultDao;
import com.keyanzizheng.entity.approvebill.ApproveBill;
import com.keyanzizheng.entity.category.Category;
import com.keyanzizheng.entity.employee.Employee;
import com.keyanzizheng.entity.result.QueryResult;
import com.keyanzizheng.entity.result.Result;
import com.keyanzizheng.entity.result.ResultFormStatistic;
import com.keyanzizheng.entity.result.TaskChange;
import com.keyanzizheng.entity.subsection.SubSection;
import com.keyanzizheng.entity.user.SysUser;
import com.keyanzizheng.utils.BeanUtil;
import com.keyanzizheng.utils.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.keyanzizheng.constant.ResultTypeConstants.KE_YAN;
import static com.keyanzizheng.constant.StatusConstants.NEGATE;

/**
 * 成果Biz
 *
 * @author 268
 */
@Service
public class ResultBiz extends BaseBiz<Result, ResultDao> {

    /**
     * SimpleDateFormat 的创建是比较昂贵的, 所以要避免频繁创建它.
     * 用完 ThreadLocal 后, 要直接删除 (调用ThreadLocal 的 remove() 方法), 避免PermGen 泄漏
     * {@link SimpleDateFormat}
     */
    private static final ThreadLocal<DateFormat> DATE_FORMAT_THREAD_LOCAL =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy/MM/dd"));


    @Autowired
    private ApproveBillBiz approveBillBiz;
    @Autowired
    private TaskEmployeeBiz taskEmployeeBiz;
    @Autowired
    private TaskChangeBiz taskChangeBiz;
    @Autowired
    private BaseHessianBiz baseHessianBiz;
    @Autowired
    private HrHessianBiz hrHessianBiz;
    @Autowired
    private ResultDao resultDao;
    @Autowired
    private CategoryBiz categoryBiz;
    @Autowired
    private SubSectionBiz subSectionBiz;

    /**
     * 添加课题
     */
    public void addResult(Result result) {
        this.save(result);
    }

    /**
     * 分页查询
     */
    public List<QueryResult> getResultList(Pagination pagination, QueryResult result) {
        String whereSql = " 1=1";
        whereSql += " and status!=2";

        Long id = result.getId();
        if (id != null && id > 0) {
            whereSql += " and id=" + id;
        }

        Long employeeId = result.getEmployeeId();
        if (ObjectUtils.isNotNull(employeeId)) {
            whereSql += " and employeeId=" + employeeId;
        }

        Integer passStatus = result.getPassStatus();
        if (passStatus != null && passStatus > 0) {
            whereSql += " and passStatus=" + passStatus;
        } else {
            /**
             * 查询课题列表需要判断用户所在角色 从而查询相应权限的列表
             * @see com.keyanzizheng.constant.ApprovalStatusConstants
             */
            Integer roleId = result.getRoleId();
            if (ObjectUtils.isNotNull(roleId)) {
                switch (roleId) {
                    case ResearchConstants.DEPT_HEAD:
                        whereSql += " and (passStatus = 2 or passStatus = 3)";
                        break;
                    case ResearchConstants.RES_DEPT:
                        whereSql += " and (passStatus = 4 or passStatus = 5 or passStatus = 8 or passStatus = 9)";
                        break;
                    case ResearchConstants.RES_LEADER:
                        whereSql += " and (passStatus = 6 or passStatus = 7 or passStatus = 8 or passStatus = 9)";
                        break;
                    default:
                }
            }
        }
        Integer ifFile = result.getIfFile();
        if (ifFile != null && ifFile >= 0) {
            whereSql += " and ifFile=" + ifFile;
        }
        Integer resultType = result.getResultType();
        if (resultType != null && resultType > 0) {
            whereSql += " and resultType=" + resultType;
        }
        String name = result.getName();
        if (!StringUtils.isTrimEmpty(name)) {
            whereSql += " and name like '%" + name + "%'";
        }
        Long intoStorage = result.getIntoStorage();
        if (intoStorage != null && intoStorage > 0) {
            whereSql += " and intoStorage=" + intoStorage;
        }
        Long sysUserId = result.getSysUserId();
        if (sysUserId != null && sysUserId > 0) {
            whereSql += " and sysUserId=" + sysUserId;
        }
        Date storageTime = result.getStorageTime();
        if (ObjectUtils.isNotNull(storageTime)) {
            whereSql += " and left(storageTime,4) = " + DateUtils.format(storageTime, "yyyy");
        }
        Date datTime = result.getDatTime();
        if (ObjectUtils.isNotNull(datTime)) {
            whereSql += " and left(datTime,4) = " + DateUtils.format(datTime, "yyyy");
        }
        Integer resultForm = result.getResultForm();
        if (resultForm != null && resultForm > 0) {
            whereSql += " and resultForm=" + resultForm;
        }

        Integer jn = result.getJournalNature();
        if (jn != null) {
            whereSql += " and journalNature=" + jn;
        }
        String publish = result.getPublish();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(publish)) {
            whereSql += " and publish like '%" + publish + "%'";
        }
        Integer teacherResearch = result.getTeacherResearch();
        if (teacherResearch != null) {
            whereSql += " and teacherResearch=" + teacherResearch;
        }

        Integer yearOrMonthly = result.getYearOrMonthly();
        if (yearOrMonthly != null) {
            whereSql += " and yearOrMonthly=" + yearOrMonthly;
        }

        Date addTime = result.getAddTime();
        if (addTime != null) {
            whereSql += " and regTime >= '" + DateUtils.format(addTime, "yyyy-MM-dd HH:mm:ss") + "'";
        }

        Date endTime = result.getEndTime();
        if (endTime != null) {
            whereSql += " and regTime <= '" + DateUtils.format(endTime, "yyyy-MM-dd HH:mm:ss") + "'";
        }

        String workName = result.getWorkName();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(workName)) {
            whereSql += " and workName like '%" + workName + "%'";
        }

        whereSql += " order by id desc";
        List<Result> results = this.find(pagination, whereSql);
        List<QueryResult> resultList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(results)) {
            for (Result _result : results) {
                QueryResult queryResult = this.getResultById(_result.getId());
                resultList.add(queryResult);
            }
        }
        return resultList;
    }

    /**
     * 根据id查询
     */
    public QueryResult getResultById(Long id) {
        Result result = this.findById(id);
        if (ObjectUtils.isNull(result)) {
            return null;
        }
        QueryResult queryResult = new QueryResult();
        BeanUtil.copyProperties(result, queryResult);
        ApproveBill approveBill = new ApproveBill();
        approveBill.setResultId(id);
        List<ApproveBill> approveBillList = approveBillBiz.getApproveBillList(null, approveBill);
        if (!CollectionUtils.isEmpty(approveBillList)) {
            queryResult.setApproveBill(approveBillList.get(0));
        }

        SysUser sysUser = baseHessianBiz.querySysUserById(queryResult.getSysUserId());
        if (ObjectUtils.isNotNull(sysUser)) {
            if (sysUser.getUserType() == 2) {
                Employee employee = hrHessianBiz.queryEmployeeById(sysUser.getLinkId());
                if (ObjectUtils.isNotNull(employee)) {
                    queryResult.setEmployeeName(employee.getName());
                }
            } else {
                queryResult.setEmployeeName(sysUser.getUserName());
            }
        }
        return queryResult;
    }

    /**
     * 成果到期归档定时器
     */
    public void resultFile() {
        QueryResult queryResult = new QueryResult();
        queryResult.setIntoStorage(1L);
        queryResult.setIfFile(1);
        queryResult.setStorageTime(new Date());
        List<QueryResult> resultList = getResultList(null, queryResult);
        if (!CollectionUtils.isEmpty(resultList)) {
            for (QueryResult _queryResult : resultList) {
                Result result = new Result();
                result.setId(_queryResult.getId());
                result.setIfFile(2);
                update(result);
                TaskChange taskChange = new TaskChange();
                taskChange.setTaskId(_queryResult.getId());
                taskChange.setOperate("课题成果归档！");
                taskChange.setStatus(1);
                taskChangeBiz.save(taskChange);
            }
        }
    }

    public List<ResultFormStatistic> queryResultForm(String whereTime) {
        return resultDao.queryResultForm(whereTime);
    }


    /**
     * 科研处导出excel的查询
     * 成果在新建时 默认的passStatus=1
     * 但课题应只导出已结项的(passStatus=8)
     *
     * @param pagination 分页
     * @param result     成果
     * @return 与成果列表一致的数据
     */
    List<Result> queryForExcel(Pagination pagination, QueryResult result) {
        result.setResultType(KE_YAN);
        String whereSql = " 1=1";
        whereSql += " and status!=2";

        Long id = result.getId();
        if (id != null && id > 0) {
            whereSql += " and id=" + id;
        }

        Long employeeId = result.getEmployeeId();
        if (ObjectUtils.isNotNull(employeeId)) {
            whereSql += " and employeeId=" + employeeId;
        }

        /** 科研处课题只导出已结项的 **/
        if (result.getResultForm() == ResultFormConstants.QUESTION) {
            whereSql += " and passStatus=8";
        }

        Integer ifFile = result.getIfFile();
        if (ifFile != null && ifFile >= 0) {
            whereSql += " and ifFile=" + ifFile;
        }
        Integer resultType = result.getResultType();
        if (resultType != null && resultType > 0) {
            whereSql += " and resultType=" + resultType;
        }
        String name = result.getName();
        if (!StringUtils.isTrimEmpty(name)) {
            whereSql += " and name like '%" + name + "%'";
        }
        Long intoStorage = result.getIntoStorage();
        if (intoStorage != null && intoStorage > 0) {
            whereSql += " and intoStorage=" + intoStorage;
        }
        Long sysUserId = result.getSysUserId();
        if (sysUserId != null && sysUserId > 0) {
            whereSql += " and sysUserId=" + sysUserId;
        }
        Date storageTime = result.getStorageTime();
        if (ObjectUtils.isNotNull(storageTime)) {
            whereSql += " and left(storageTime,4) = " + DateUtils.format(storageTime, "yyyy");
        }
        Date datTime = result.getDatTime();
        if (ObjectUtils.isNotNull(datTime)) {
            whereSql += " and left(datTime,4) = " + DateUtils.format(datTime, "yyyy");
        }
        Integer resultForm = result.getResultForm();
        if (resultForm != null && resultForm > 0) {
            whereSql += " and resultForm=" + resultForm;
        }

        Integer jn = result.getJournalNature();
        if (jn != null) {
            whereSql += " and journalNature=" + jn;
        }
        String publish = result.getPublish();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(publish)) {
            whereSql += " and publish like '%" + publish + "%'";
        }
        Integer teacherResearch = result.getTeacherResearch();
        if (teacherResearch != null) {
            whereSql += " and teacherResearch=" + teacherResearch;
        }

        Integer yearOrMonthly = result.getYearOrMonthly();
        if (yearOrMonthly != null) {
            whereSql += " and yearOrMonthly=" + yearOrMonthly;
        }

        Date addTime = result.getAddTime();
        if (addTime != null) {
            whereSql += " and addTime >= '" + DateUtils.format(addTime, "yyyy-MM-dd HH:mm:ss") + "'";
        }

        Date endTime = result.getEndTime();
        if (endTime != null) {
            whereSql += " and endTime <= '" + DateUtils.format(endTime, "yyyy-MM-dd HH:mm:ss") + "'";
        }
        whereSql += " order by id desc";

        return this.find(pagination, whereSql);
    }

    /**
     * 生态所导出excel的查询
     * 成果在新建时 默认的passStatus=1
     * 但课题应只导出已结项的(passStatus=4)
     *
     * @param pagination 分页
     * @param result     成果
     * @return 与成果列表一致的数据
     */
    List<Result> queryForQuestionExcel(Pagination pagination, QueryResult result) {
        String whereSql = " 1=1";
        whereSql += " and status!=2";

        Long id = result.getId();
        if (id != null && id > 0) {
            whereSql += " and id=" + id;
        }

        Long employeeId = result.getEmployeeId();
        if (ObjectUtils.isNotNull(employeeId)) {
            whereSql += " and employeeId=" + employeeId;
        }

        /* 生态所课题只导出已结项的**/
        whereSql += " and passStatus=4";

        /* 生态所课题只导出未归档的**/
        whereSql += " and ifFile=" + StatusConstants.NEGATE;

        /* 生态所课题只导出资政课题**/
        whereSql += " and resultType=" + ResultTypeConstants.ZI_ZHENG;

        String name = result.getName();
        if (!StringUtils.isTrimEmpty(name)) {
            whereSql += " and name like '%" + name + "%'";
        }
        Long intoStorage = result.getIntoStorage();
        if (intoStorage != null && intoStorage > 0) {
            whereSql += " and intoStorage=" + intoStorage;
        }
        Long sysUserId = result.getSysUserId();
        if (sysUserId != null && sysUserId > 0) {
            whereSql += " and sysUserId=" + sysUserId;
        }
        Date storageTime = result.getStorageTime();
        if (ObjectUtils.isNotNull(storageTime)) {
            whereSql += " and left(storageTime,4) = " + DateUtils.format(storageTime, "yyyy");
        }
        Date datTime = result.getDatTime();
        if (ObjectUtils.isNotNull(datTime)) {
            whereSql += " and left(datTime,4) = " + DateUtils.format(datTime, "yyyy");
        }

        /* 生态所课题只导出课题**/
        whereSql += " and resultForm=" + ResultFormConstants.QUESTION;

        Integer jn = result.getJournalNature();
        if (jn != null) {
            whereSql += " and journalNature=" + jn;
        }
        String publish = result.getPublish();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(publish)) {
            whereSql += " and publish like '%" + publish + "%'";
        }
        Integer teacherResearch = result.getTeacherResearch();
        if (teacherResearch != null) {
            whereSql += " and teacherResearch=" + teacherResearch;
        }

        Integer yearOrMonthly = result.getYearOrMonthly();
        if (yearOrMonthly != null) {
            whereSql += " and yearOrMonthly=" + yearOrMonthly;
        }

        Date addTime = result.getAddTime();
        if (addTime != null) {
            whereSql += " and addTime >= '" + DateUtils.format(addTime, "yyyy-MM-dd HH:mm:ss") + "'";
        }

        Date endTime = result.getEndTime();
        if (endTime != null) {
            whereSql += " and endTime <= '" + DateUtils.format(endTime, "yyyy-MM-dd HH:mm:ss") + "'";
        }
        whereSql += " order by id desc";

        return this.find(pagination, whereSql);
    }


    public String batchImport(MultipartFile myFile, HttpServletRequest request) throws Exception {

        HSSFWorkbook workbook = new HSSFWorkbook(myFile.getInputStream());
        HSSFSheet sheet = workbook.getSheetAt(0);

        // 根据表头来判断导入的是哪种成果
        HSSFRow firstRow = sheet.getRow(0);
        String resultForm = firstRow.getCell((short) 0).getStringCellValue();
        String errorMsg;
        switch (StringUtil.trim(resultForm)) {
            case "论文名称":
                errorMsg = paperExcel(sheet, request);
                break;
            case "著作名称":
                errorMsg = bookExcel(sheet, request);
                break;
            case "课题名称":
                errorMsg = questionExcel(sheet, request);
                break;
            case "内刊名称":
                errorMsg = internalExcel(sheet, request);
                break;
            case "成果名称":
                errorMsg = otherExcel(sheet, request);
                break;
            default:
                return "模板错误, 请核对后再试";
        }

        return errorMsg;
    }

    private String otherExcel(HSSFSheet sheet, HttpServletRequest request) {
        StringBuilder errorMsg = new StringBuilder();
        int totalRowNum = sheet.getLastRowNum();

        // 是否公开
        Map<String, Integer> isPublicMap = new HashMap<>(2);
        isPublicMap.put("公开", 1);
        isPublicMap.put("不公开", 2);

        // 级别
        Map<String, Integer> levelMap = new HashMap<>(4);
        levelMap.put("国家级", 1);
        levelMap.put("省部级", 2);
        levelMap.put("市级", 3);
        levelMap.put("校级", 4);

        // 类型
        List<Category> categoryList = categoryBiz.findAll();
        Map<Long, String> categoryMap = new HashMap<>(16);
        categoryList.forEach(c -> categoryMap.put(c.getId(), c.getName()));

        Integer isPublicNum = 1;
        Long journalNatureLong = 0L;
        String journalNatureName = "";
        Integer levelInt = 1;
        Date publishTimeDate = null;

        for (int currentRowNum = 1; currentRowNum < totalRowNum; currentRowNum++) {

            HSSFRow currentRow = sheet.getRow(currentRowNum);
            if (currentRow != null) {
                // 其他成果名称
                String name = getCellValue(currentRow.getCell((short) 0));
                if (StringUtil.isBlank(name)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 成果名称为空, 请填写;");
                }

                // 是否公开
                String isPublicStr = getCellValue(currentRow.getCell((short) 1));
                if (StringUtil.isBlank(isPublicStr)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 是否公开为空, 请填写;");
                } else {
                    isPublicNum = isPublicMap.get(isPublicStr);
                    if (isPublicNum == null) {
                        errorMsg.append("第").append(currentRowNum).append("行, 是否公开填写错误;");
                    }
                }

                // 刊物性质
                String journalNature = getCellValue(currentRow.getCell((short) 2));
                if (!StringUtil.isNumeric(journalNature)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 刊物性质填写错误;");
                } else {
                    journalNatureLong = Long.parseLong(journalNature);
                    journalNatureName = categoryMap.get(journalNatureLong);
                    if (journalNatureName == null) {
                        errorMsg.append("第").append(currentRowNum).append("行, 刊物性质填写错误, 请确认数字合法;");
                    }
                }

                // 级别
                String level = getCellValue(currentRow.getCell((short) 3));
                if (StringUtil.isBlank(level)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 级别填写错误;");
                } else {
                    levelInt = levelMap.get(level);
                    if (levelInt == null) {
                        errorMsg.append("第").append(currentRowNum).append("行, 级别填写错误;");
                    }
                }

                // 发表刊物
                String publish = getCellValue(currentRow.getCell((short) 4));
                // 刊号
                String publishNumber = getCellValue(currentRow.getCell((short) 5));

                // 发表时间
                String publishTime = getCellValue(currentRow.getCell((short) 6));
                try {
                    publishTimeDate = DATE_FORMAT_THREAD_LOCAL.get().parse(publishTime);
                } catch (Exception e) {
                    errorMsg.append("第").append(currentRowNum).append("行, 发表时间填写错误;");
                } finally {
                    DATE_FORMAT_THREAD_LOCAL.remove();
                }

                // 作者名称
                String workName = getCellValue(currentRow.getCell((short) 7));
                // 课题组成员
                String taskForceMembers = getCellValue(currentRow.getCell((short) 8));

                // 字数
                String wordsNumber = getCellValue(currentRow.getCell((short) 9));
                if (!StringUtil.isNumeric(wordsNumber)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 字数填写错误;");
                }

                // 备注
                String remark = getCellValue(currentRow.getCell((short) 10));

                if (StringUtil.isNotBlank(errorMsg)) {
                    return errorMsg.toString();
                }

                /*
                开始添加入数据库
                 */
                Result rs = new Result();

                // 获取处室id
                int myDeptId = this.getDeptIdByReq(request);

                // 科研其他成果
                rs.setResultType(KE_YAN);
                rs.setResultForm(ResultFormConstants.OTHER);
                // 未入库未归档未删除
                rs.setIntoStorage(1L);
                rs.setIfFile(StatusConstants.NEGATE);
                rs.setStatus(1);
                // 只有课题的passStatus才有意义
                rs.setPassStatus(0);
                // 导入
                rs.setTeacherResearch(myDeptId);
                rs.setName(name);
                rs.setResultSwitch(isPublicNum);
                rs.setJournalNature(journalNatureLong.intValue());
                rs.setJournalNatureName(journalNatureName);
                rs.setLevel(levelInt);
                rs.setPublish(publish);
                rs.setPublishNumber(publishNumber);
                rs.setPublishTime(publishTimeDate);
                rs.setWorkName(workName);
                rs.setTaskForceMembers(taskForceMembers);
                rs.setWordsNumber(Long.parseLong(wordsNumber));
                rs.setTaskForceMembers(taskForceMembers);
                rs.setRemark(remark);

                super.save(rs);
            }
        }
        return null;
    }

    private String internalExcel(HSSFSheet sheet, HttpServletRequest request) {
        StringBuilder errorMsg = new StringBuilder();
        int totalRowNum = sheet.getLastRowNum();

        // 是否公开
        Map<String, Integer> isPublicMap = new HashMap<>(2);
        isPublicMap.put("公开", 1);
        isPublicMap.put("不公开", 2);

        // 级别
        Map<String, Integer> levelMap = new HashMap<>(4);
        levelMap.put("国家级", 1);
        levelMap.put("省部级", 2);
        levelMap.put("市级", 3);
        levelMap.put("校级", 4);

        // 类型
        List<Category> categoryList = categoryBiz.findAll();
        Map<Long, String> categoryMap = new HashMap<>(16);
        categoryList.forEach(c -> categoryMap.put(c.getId(), c.getName()));

        Integer isPublicNum = 1;
        Long journalNatureLong = 0L;
        String journalNatureName = "";
        Integer levelInt = 1;
        Date publishTimeDate = null;

        for (int currentRowNum = 1; currentRowNum < totalRowNum; currentRowNum++) {

            HSSFRow currentRow = sheet.getRow(currentRowNum);
            if (currentRow != null) {
                // 内刊名称
                String name = getCellValue(currentRow.getCell((short) 0));
                if (StringUtil.isBlank(name)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 内刊名称为空, 请填写;");
                }

                // 是否公开
                String isPublicStr = getCellValue(currentRow.getCell((short) 1));
                if (StringUtil.isBlank(isPublicStr)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 是否公开为空, 请填写;");
                } else {
                    isPublicNum = isPublicMap.get(isPublicStr);
                    if (isPublicNum == null) {
                        errorMsg.append("第").append(currentRowNum).append("行, 是否公开填写错误;");
                    }
                }

                // 刊物性质
                String journalNature = getCellValue(currentRow.getCell((short) 2));
                if (!StringUtil.isNumeric(journalNature)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 刊物性质填写错误;");
                } else {
                    journalNatureLong = Long.parseLong(journalNature);
                    journalNatureName = categoryMap.get(journalNatureLong);
                    if (journalNatureName == null) {
                        errorMsg.append("第").append(currentRowNum).append("行, 刊物性质填写错误, 请确认数字合法;");
                    }
                }

                // 级别
                String level = getCellValue(currentRow.getCell((short) 3));
                if (StringUtil.isBlank(level)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 级别填写错误;");
                } else {
                    levelInt = levelMap.get(level);
                    if (levelInt == null) {
                        errorMsg.append("第").append(currentRowNum).append("行, 级别填写错误;");
                    }
                }

                // 发表刊物
                String publish = getCellValue(currentRow.getCell((short) 4));
                // 刊号
                String publishNumber = getCellValue(currentRow.getCell((short) 5));

                // 发表时间
                String publishTime = getCellValue(currentRow.getCell((short) 6));
                try {
                    publishTimeDate = DATE_FORMAT_THREAD_LOCAL.get().parse(publishTime);
                } catch (Exception e) {
                    errorMsg.append("第").append(currentRowNum).append("行, 发表时间填写错误;");
                } finally {
                    DATE_FORMAT_THREAD_LOCAL.remove();
                }

                // 作者名称
                String workName = getCellValue(currentRow.getCell((short) 7));
                // 课题组成员
                String taskForceMembers = getCellValue(currentRow.getCell((short) 8));

                // 字数
                String wordsNumber = getCellValue(currentRow.getCell((short) 9));
                if (!StringUtil.isNumeric(wordsNumber)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 字数填写错误;");
                }

                // 备注
                String remark = getCellValue(currentRow.getCell((short) 10));

                if (StringUtil.isNotBlank(errorMsg)) {
                    return errorMsg.toString();
                }

                /*
                开始添加入数据库
                 */
                Result rs = new Result();

                // 获取处室id
                int myDeptId = this.getDeptIdByReq(request);

                // 科研内刊
                rs.setResultType(KE_YAN);
                rs.setResultForm(ResultFormConstants.INTERNAL_PUBLICATION);
                // 未入库未归档未删除
                rs.setIntoStorage(1L);
                rs.setIfFile(StatusConstants.NEGATE);
                rs.setStatus(1);
                // 只有课题的passStatus才有意义
                rs.setPassStatus(0);
                // 导入
                rs.setTeacherResearch(myDeptId);
                rs.setName(name);
                rs.setResultSwitch(isPublicNum);
                rs.setJournalNature(journalNatureLong.intValue());
                rs.setJournalNatureName(journalNatureName);
                rs.setLevel(levelInt);
                rs.setPublish(publish);
                rs.setPublishNumber(publishNumber);
                rs.setPublishTime(publishTimeDate);
                rs.setWorkName(workName);
                rs.setTaskForceMembers(taskForceMembers);
                rs.setWordsNumber(Long.parseLong(wordsNumber));
                rs.setTaskForceMembers(taskForceMembers);
                rs.setRemark(remark);

                super.save(rs);
            }
        }
        return null;
    }

    private String questionExcel(HSSFSheet sheet, HttpServletRequest request) {
        StringBuilder errorMsg = new StringBuilder();
        String name;
        Integer level = 0;
        String publish;
        Date addTime = null;
        Date endTime = null;
        String workName;
        String taskForceMembers;
        Long wordsNumber = 0L;
        String remark;

        // 级别
        Map<String, Integer> levelMap = new HashMap<>(4);
        levelMap.put("国家级", 1);
        levelMap.put("省部级", 2);
        levelMap.put("市级", 3);
        levelMap.put("校级", 4);

        int totalRowNum = sheet.getLastRowNum();
        for (int currentRowNum = 1; currentRowNum < totalRowNum; currentRowNum++) {
            HSSFRow currentRow = sheet.getRow(currentRowNum);
            if (currentRow != null) {
                name = getCellValue(currentRow.getCell((short) 0));
                String levelStr = getCellValue(currentRow.getCell((short) 1));
                publish = getCellValue(currentRow.getCell((short) 2));
                String addTimeStr = getCellValue(currentRow.getCell((short) 3));
                String endTimeStr = getCellValue(currentRow.getCell((short) 4));
                workName = getCellValue(currentRow.getCell((short) 5));
                taskForceMembers = getCellValue(currentRow.getCell((short) 6));
                String wordsNumberStr = getCellValue(currentRow.getCell((short) 7));
                remark = getCellValue(currentRow.getCell((short) 8));

                /* 验证必填项 */
                if (StringUtil.isBlank(name)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 课题名称为空, 请填写;");
                }
                if (StringUtil.isBlank(levelStr)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 级别填写错误;");
                } else {
                    level = levelMap.get(levelStr);
                    if (level == null) {
                        errorMsg.append("第").append(currentRowNum).append("行, 级别填写错误;");
                    }
                }
                try {
                    addTime = DATE_FORMAT_THREAD_LOCAL.get().parse(addTimeStr);
                } catch (Exception e) {
                    errorMsg.append("第").append(currentRowNum).append("行, 开始时间填写错误;");
                } finally {
                    DATE_FORMAT_THREAD_LOCAL.remove();
                }
                try {
                    endTime = DATE_FORMAT_THREAD_LOCAL.get().parse(endTimeStr);
                } catch (Exception e) {
                    errorMsg.append("第").append(currentRowNum).append("行, 结束时间填写错误;");
                } finally {
                    DATE_FORMAT_THREAD_LOCAL.remove();
                }

                if (StringUtil.isBlank(workName)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 课题负责人填写错误;");
                }

                // 必填 且必须为数字
                if (StringUtil.isBlank(wordsNumberStr) || !StringUtil.isNumeric(wordsNumberStr)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 字数填写错误;");
                } else {
                    wordsNumber = Long.parseLong(wordsNumberStr);
                }

                if (StringUtil.isNotBlank(errorMsg)) {
                    return errorMsg.toString();
                }

                Result rs = new Result();
                // 获取处室id
                int myDeptId = this.getDeptIdByReq(request);
                // 科研著作
                rs.setResultType(KE_YAN);
                rs.setResultForm(ResultFormConstants.QUESTION);
                // 未入库未归档未删除
                rs.setIntoStorage(1L);
                rs.setIfFile(StatusConstants.NEGATE);
                rs.setStatus(1);

                /*
                  已结项
                 */
                rs.setPassStatus(ApprovalStatusConstants.FINISH);

                // 开始导入
                rs.setName(name);
                rs.setLevel(level);
                rs.setPublish(publish);
                rs.setAddTime(addTime);
                rs.setEndTime(endTime);
                rs.setTeacherResearch(myDeptId);
                rs.setWorkName(workName);
                rs.setTaskForceMembers(taskForceMembers);
                rs.setWordsNumber(wordsNumber);
                rs.setRemark(remark);
                super.save(rs);

                TaskChange taskChange = new TaskChange();
                taskChange.setTaskId(rs.getId());
                taskChange.setOperate("导入课题成果");
                taskChange.setStatus(1);
                taskChangeBiz.save(taskChange);
            }
        }


        return null;
    }

    private String bookExcel(HSSFSheet sheet, HttpServletRequest request) {
        StringBuilder errorMsg = new StringBuilder();
        String name;
        Long journalNatureLong = 0L;
        String journalNatureName = "";
        Integer levelInt = 1;
        String publish;
        Date publishTime = null;
        String workName;
        String taskForceMembers;
        Long wordsNumber = 0L;
        String associateEditor;
        Long associateNumber = 0L;
        String chapter;
        String remark;


        // 级别
        Map<String, Integer> levelMap = new HashMap<>(4);
        levelMap.put("国家级", 1);
        levelMap.put("省部级", 2);
        levelMap.put("市级", 3);
        levelMap.put("校级", 4);

        // 类型
        List<Category> categoryList = categoryBiz.findAll();
        Map<Long, String> categoryMap = new HashMap<>(16);
        categoryList.forEach(c -> categoryMap.put(c.getId(), c.getName()));

        int totalRowNum = sheet.getLastRowNum();
        for (int currentRowNum = 1; currentRowNum < totalRowNum; currentRowNum++) {
            HSSFRow currentRow = sheet.getRow(currentRowNum);
            if (currentRow != null) {
                // 著作名称
                name = getCellValue(currentRow.getCell((short) 0));
                if (StringUtil.isBlank(name)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 著作名称为空, 请填写;");
                }

                // 类型
                String journalNature = getCellValue(currentRow.getCell((short) 1));
                if (!StringUtil.isNumeric(journalNature)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 类别填写错误;");
                } else {
                    journalNatureLong = Long.parseLong(journalNature);
                    journalNatureName = categoryMap.get(journalNatureLong);
                    if (journalNatureName == null) {
                        errorMsg.append("第").append(currentRowNum).append("行, 类别填写错误, 请确认数字合法;");
                    }
                }

                // 级别
                String level = getCellValue(currentRow.getCell((short) 2));
                if (StringUtil.isBlank(level)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 级别填写错误;");
                } else {
                    levelInt = levelMap.get(level);
                    if (levelInt == null) {
                        errorMsg.append("第").append(currentRowNum).append("行, 级别填写错误;");
                    }
                }

                // 出版社
                publish = getCellValue(currentRow.getCell((short) 3));
                if (StringUtil.isBlank(publish)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 出版社填写错误;");
                }

                // 出版时间
                String publishTimeStr = getCellValue(currentRow.getCell((short) 4));
                try {
                    publishTime = DATE_FORMAT_THREAD_LOCAL.get().parse(publishTimeStr);
                } catch (Exception e) {
                    errorMsg.append("第").append(currentRowNum).append("行, 出版时间填写错误;");
                } finally {
                    DATE_FORMAT_THREAD_LOCAL.remove();
                }

                // 主编
                workName = getCellValue(currentRow.getCell((short) 5));
                // 参编组成员
                taskForceMembers = getCellValue(currentRow.getCell((short) 6));
                // 主编字数
                String wordsNumberStr = getCellValue(currentRow.getCell((short) 7));
                if (StringUtil.isNotBlank(wordsNumberStr)) {
                    if (StringUtil.isNumeric(wordsNumberStr)) {
                        wordsNumber = Long.parseLong(wordsNumberStr);
                    } else {
                        errorMsg.append("第").append(currentRowNum).append("行, 主编字数填写错误;");
                    }
                }
                // 副主编
                associateEditor = getCellValue(currentRow.getCell((short) 8));
                // 副主编字数
                String associateNumberStr = getCellValue(currentRow.getCell((short) 9));
                if (StringUtil.isNotBlank(associateNumberStr)) {
                    if (StringUtil.isNumeric(associateNumberStr)) {
                        associateNumber = Long.parseLong(associateNumberStr);
                    } else {
                        errorMsg.append("第").append(currentRowNum).append("行, 副主编字数填写错误;");
                    }
                }
                // 参编章节信息
                chapter = getCellValue(currentRow.getCell((short) 10));
                if (StringUtil.isBlank(chapter)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 参编章节信息填写错误;");
                }
                // 备注
                remark = getCellValue(currentRow.getCell((short) 10));

                if (StringUtil.isNotBlank(errorMsg)) {
                    return errorMsg.toString();
                }

                Result rs = new Result();
                // 获取处室id
                int myDeptId = this.getDeptIdByReq(request);
                // 科研著作
                rs.setResultType(KE_YAN);
                rs.setResultForm(ResultFormConstants.BOOK);
                // 未入库未归档未删除
                rs.setIntoStorage(1L);
                rs.setIfFile(StatusConstants.NEGATE);
                rs.setStatus(1);
                rs.setPassStatus(0);

                // 开始导入
                rs.setTeacherResearch(myDeptId);
                rs.setName(name);
                rs.setJournalNature(journalNatureLong.intValue());
                rs.setJournalNatureName(journalNatureName);
                rs.setLevel(levelInt);
                rs.setPublish(publish);
                rs.setPublishTime(publishTime);
                rs.setWorkName(workName);
                rs.setTaskForceMembers(taskForceMembers);
                rs.setWordsNumber(wordsNumber);
                rs.setAssociateEditor(associateEditor);
                rs.setAssociateNumber(associateNumber);
                rs.setChapter(chapter);
                rs.setRemark(remark);

                super.save(rs);
            }
        }


        return null;
    }

    private String paperExcel(HSSFSheet sheet, HttpServletRequest request) {
        StringBuilder errorMsg = new StringBuilder();
        int totalRowNum = sheet.getLastRowNum() + 1;

        // 是否公开
        Map<String, Integer> isPublicMap = new HashMap<>(2);
        isPublicMap.put("公开", 1);
        isPublicMap.put("不公开", 2);

        // 级别
        Map<String, Integer> levelMap = new HashMap<>(4);
        levelMap.put("国家级", 1);
        levelMap.put("省部级", 2);
        levelMap.put("市级", 3);
        levelMap.put("校级", 4);

        // 类型
        List<Category> categoryList = categoryBiz.findAll();
        Map<Long, String> categoryMap = new HashMap<>(16);
        categoryList.forEach(c -> categoryMap.put(c.getId(), c.getName()));

        Integer isPublicNum = 1;
        Long journalNatureLong = 0L;
        String journalNatureName = "";
        Integer levelInt = 1;
        Date publishTimeDate = null;

        for (int currentRowNum = 1; currentRowNum < totalRowNum; currentRowNum++) {

            HSSFRow currentRow = sheet.getRow(currentRowNum);
            if (currentRow != null) {
                // 论文名称
                String name = getCellValue(currentRow.getCell((short) 0));
                if (StringUtil.isBlank(name)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 论文名称为空, 请填写;");
                }

                // 是否公开
                String isPublicStr = getCellValue(currentRow.getCell((short) 1));
                if (StringUtil.isBlank(isPublicStr)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 是否公开为空, 请填写;");
                } else {
                    isPublicNum = isPublicMap.get(isPublicStr);
                    if (isPublicNum == null) {
                        errorMsg.append("第").append(currentRowNum).append("行, 是否公开填写错误;");
                    }
                }

                // 刊物性质
                String journalNature = getCellValue(currentRow.getCell((short) 2));
                if (!StringUtil.isNumeric(journalNature)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 刊物性质填写错误;");
                } else {
                    journalNatureLong = Long.parseLong(journalNature);
                    journalNatureName = categoryMap.get(journalNatureLong);
                    if (journalNatureName == null) {
                        errorMsg.append("第").append(currentRowNum).append("行, 刊物性质填写错误, 请确认数字合法;");
                    }
                }

                // 级别
                String level = getCellValue(currentRow.getCell((short) 3));
                if (StringUtil.isBlank(level)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 级别填写错误;");
                } else {
                    levelInt = levelMap.get(level);
                    if (levelInt == null) {
                        errorMsg.append("第").append(currentRowNum).append("行, 级别填写错误;");
                    }
                }

                // 发表刊物
                String publish = getCellValue(currentRow.getCell((short) 4));
                // 刊号
                String publishNumber = getCellValue(currentRow.getCell((short) 5));

                // 发表时间
                String publishTime = getCellValue(currentRow.getCell((short) 6));
                try {
                    publishTimeDate = DATE_FORMAT_THREAD_LOCAL.get().parse(publishTime);
                } catch (Exception e) {
                    errorMsg.append("第").append(currentRowNum).append("行, 发表时间填写错误;");
                } finally {
                    DATE_FORMAT_THREAD_LOCAL.remove();
                }

                // 作者名称
                String workName = getCellValue(currentRow.getCell((short) 7));
                // 课题组成员
                String taskForceMembers = getCellValue(currentRow.getCell((short) 8));

                // 字数
                String wordsNumber = getCellValue(currentRow.getCell((short) 9));
                if (!StringUtil.isNumeric(wordsNumber)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 字数填写错误;");
                }

                // 备注
                String remark = getCellValue(currentRow.getCell((short) 10));

                if (StringUtil.isNotBlank(errorMsg)) {
                    return errorMsg.toString();
                }

                /*
                开始添加入数据库
                 */
                Result rs = new Result();

                // 获取处室id
                int myDeptId = this.getDeptIdByReq(request);

                // 科研论文
                rs.setResultType(KE_YAN);
                rs.setResultForm(ResultFormConstants.PAPER);
                // 未入库未归档未删除
                rs.setIntoStorage(1L);
                rs.setIfFile(StatusConstants.NEGATE);
                rs.setStatus(1);
                // 只有课题的passStatus才有意义
                rs.setPassStatus(0);
                // 导入
                rs.setTeacherResearch(myDeptId);
                rs.setName(name);
                rs.setResultSwitch(isPublicNum);
                rs.setJournalNature(journalNatureLong.intValue());
                rs.setJournalNatureName(journalNatureName);
                rs.setLevel(levelInt);
                rs.setPublish(publish);
                rs.setPublishNumber(publishNumber);
                rs.setPublishTime(publishTimeDate);
                rs.setWorkName(workName);
                rs.setTaskForceMembers(taskForceMembers);
                rs.setWordsNumber(Long.parseLong(wordsNumber));
                rs.setTaskForceMembers(taskForceMembers);
                rs.setRemark(remark);

                super.save(rs);
            }
        }
        return null;
    }


    private Integer getDeptIdByReq(HttpServletRequest request) {
        // 获取处室id
        Integer myDeptId = 0;
        Map<String, String> userMap = SysUserUtils.getLoginSysUser(request);
        String deptId = userMap.get("departmentId");
        if (deptId != null) {
            Map<String, String> deptMap =
                    baseHessianBiz.queryDepartemntById(Long.parseLong(deptId));
            String deptName = deptMap.get("departmentName");
            if (deptName != null) {
                List<SubSection> s = subSectionBiz.find(null, "name like '%" + deptName + "%'");
                if (!CollectionUtils.isEmpty(s)) {
                    myDeptId = s.get(0).getId().intValue();
                }
            }
        }

        return myDeptId;
    }

    /**
     * 获得Hsscell内容
     */
    private String getCellValue(HSSFCell cell) {
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

    /**
     * 批量导入资政成果
     */
    public String batchImportQuestion(MultipartFile myFile, HttpServletRequest request) throws IOException {
        StringBuilder errorMsg = new StringBuilder();
        HSSFWorkbook workbook = new HSSFWorkbook(myFile.getInputStream());
        HSSFSheet sheet = workbook.getSheetAt(0);
        // 总行数
        int totalRowNum = sheet.getLastRowNum() + 1;

        // 课题类型
        Map<String, Integer> yearMonthMap = new HashMap<>(2);
        yearMonthMap.put("年度", 1);
        yearMonthMap.put("月度", 2);

        Integer yearMonthNum;
        Date addTimeDate = null;
        Date endTimeDate = null;
        Long wordNum = null;
        for (int currentRowNum = 1; currentRowNum < totalRowNum; currentRowNum++) {
            HSSFRow currentRow = sheet.getRow(currentRowNum);
            if (currentRow != null) {
                // 课题名称
                String name = getCellValue(currentRow.getCell((short) 0));
                // 课题类型
                String yearMonth = getCellValue(currentRow.getCell((short) 1));
                // 开始时间
                String addTime = getCellValue(currentRow.getCell((short) 2));
                // 结束时间
                String endTime = getCellValue(currentRow.getCell((short) 3));
                // 课题组成员
                String taskForceMembers = getCellValue(currentRow.getCell((short) 4));
                // 课题负责人
                String workName = getCellValue(currentRow.getCell((short) 5));
                // 字数
                String wordsNumber = getCellValue(currentRow.getCell((short) 6));
                // 手机号
                String phoneNumber = getCellValue(currentRow.getCell((short) 7));
                // 邮箱
                String mailbox = getCellValue(currentRow.getCell((short) 8));
                // 备注
                String remark = getCellValue(currentRow.getCell((short) 9));

                /** 校验 **/
                if (StringUtil.isBlank(name)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 课题名称为空, 请重新填写; ");
                }

                yearMonthNum = yearMonthMap.get(yearMonth);
                if (yearMonthNum == null) {
                    errorMsg.append("第").append(currentRowNum).append("行, 课题类型不正确, 请重新填写; ");
                }

                try {
                    addTimeDate = DATE_FORMAT_THREAD_LOCAL.get().parse(addTime);
                } catch (ParseException e) {
                    errorMsg.append("第").append(currentRowNum).append("行, 开始时间不正确, 请重新填写; ");
                } finally {
                    DATE_FORMAT_THREAD_LOCAL.remove();
                }

                if (StringUtil.isNotBlank(endTime)) {
                    try {
                        endTimeDate = DATE_FORMAT_THREAD_LOCAL.get().parse(endTime);
                    } catch (ParseException e) {
                        errorMsg.append("第").append(currentRowNum).append("行, 结束时间不正确, 请重新填写; ");
                    } finally {
                        DATE_FORMAT_THREAD_LOCAL.remove();
                    }
                }

                if (StringUtil.isBlank(workName)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 课题负责人为空, 请重新填写; ");
                }

                if (!StringUtil.isNumeric(wordsNumber)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 字数填写不正确, 请重新填写; ");
                } else {
                    wordNum = Long.parseLong(wordsNumber);
                }

                if (!StringUtil.isNumeric(phoneNumber)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 手机号填写不正确, 请重新填写; ");
                }

                if (StringUtil.isBlank(mailbox)) {
                    errorMsg.append("第").append(currentRowNum).append("行, 邮箱为空, 请重新填写; ");
                }

                if (StringUtil.isNotBlank(errorMsg)) {
                    return errorMsg.toString();
                }

                /* 持久化 **/
                Result result = new Result();
                // 处室
                int myDeptId = this.getDeptIdByReq(request);
                // 等级 只有校级
                int level = 4;
                // 账号id
                Long userId = SysUserUtils.getLoginSysUserId(request);
                // 获取教职工id 1为管理员 2为教职工 3为学员
                Long linkId = baseHessianBiz.queryEmployeeIdBySysUserId(userId);
                if (ObjectUtils.isNotNull(linkId)) {
                    result.setEmployeeId(linkId);
                }

                result.setName(name);
                result.setYearOrMonthly(yearMonthNum);
                result.setAddTime(addTimeDate);
                result.setEndTime(endTimeDate);
                result.setTaskForceMembers(taskForceMembers);
                result.setWorkName(workName);
                result.setWordsNumber(wordNum);
                result.setPhoneNumber(phoneNumber);
                result.setMailbox(mailbox);
                result.setRemark(remark);
                result.setTeacherResearch(myDeptId);
                result.setLevel(level);

                result.setSysUserId(userId);
                result.setStatus(1);
                result.setIntoStorage(1L);
                result.setPassStatus(EcologicalCivilizationConstants.CONFIRM_FILE);
                result.setIfFile(NEGATE);
                result.setResultForm(ResultFormConstants.QUESTION);
                result.setResultType(ResultTypeConstants.ZI_ZHENG);

                this.addResult(result);

                TaskChange taskChange = new TaskChange();
                taskChange.setTaskId(result.getId());
                taskChange.setOperate("导入课题成果");
                taskChange.setStatus(1);
                taskChangeBiz.save(taskChange);

            }
        }

        return null;
    }


    /**
     * 查询待处理的课题审批
     */
    public List<QueryResult> getProcessResultList(Long userId,String roleIds,Integer resultType) {
        String whereSql = " status!=2 and resultForm=3 and resultType="+resultType;
        if(resultType==1){
//            if (!userId.equals(DeptHeadMapConstants.REAL_LEADER)) {
//                Integer subSectionId = DeptHeadMapConstants.deptMap.get(userId);
//                if (ObjectUtils.isNotNull(subSectionId)) {
//                    whereSql += " and teacherResearch=" + subSectionId;
//                }
//            }
            /**
             * 查询课题列表需要判断用户所在角色 从而查询相应权限的列表
             * @see com.keyanzizheng.constant.ApprovalStatusConstants
             */
            whereSql +=" and ( passStatus=0";
            if(roleIds.indexOf(","+ResearchConstants.DEPT_HEAD+",")>-1){
                whereSql += " or ( passStatus = 1";
                Integer subSectionId = DeptHeadMapConstants.deptMap.get(userId);
                if (ObjectUtils.isNotNull(subSectionId)) {
                    whereSql += " and teacherResearch=" + subSectionId;
                }
                whereSql+=" ) ";
            }
            if(roleIds.indexOf(","+ResearchConstants.RES_DEPT+",")>-1){
                whereSql += " or passStatus = 2 or (passStatus = 7 and fileUrlDeclaration is not null and fileUrlDeclaration !='')";
            }
            if(roleIds.indexOf(","+ResearchConstants.RES_LEADER+",")>-1){
                whereSql += " or passStatus = 2 or passStatus = 4 or (passStatus = 7 and fileUrlDeclaration is not null and fileUrlDeclaration !='')";
            }
        }else if(resultType==2){
            whereSql +=" and ( passStatus=0";
            if(roleIds.indexOf(","+ResearchConstants.RES_ECOLOGY_FIRST+",")>-1){
                whereSql += " or passStatus = 1";
            }
            if(roleIds.indexOf(","+ResearchConstants.RES_ECOLOGY_SECOND+",")>-1){
                whereSql += " or ( passStatus = 2 and fileUrlDeclaration is not null and fileUrlDeclaration !='')";
            }
        }
        whereSql += " ) order by id desc";
        List<Result> results = this.find(null, whereSql);
        List<QueryResult> resultList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(results)) {
            for (Result _result : results) {
                QueryResult queryResult = this.getResultById(_result.getId());
                resultList.add(queryResult);
            }
        }
        return resultList;
    }


    public Integer updatePassStatusRecard(Result result,Long userId){
        Result before=this.findById(result.getId());
        if(result.getPassStatus()!=null && result.getPassStatus()!=before.getPassStatus()){
            String operations=before.getOperations()==null || "".equals(before.getOperations()) ? "," : before.getOperations();
            result.setOperations(operations+userId+",");
        }
        return this.update(result);
    }

}
