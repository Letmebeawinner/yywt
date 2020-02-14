package com.keyanzizheng.biz.result;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.keyanzizheng.biz.subsection.SubSectionBiz;
import com.keyanzizheng.constant.ResultFormConstants;
import com.keyanzizheng.dao.result.ResultFormDao;
import com.keyanzizheng.entity.result.QueryResult;
import com.keyanzizheng.entity.result.Result;
import com.keyanzizheng.entity.result.ResultForm;
import com.keyanzizheng.entity.subsection.SubSection;
import com.keyanzizheng.utils.FileExportImportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 成果类型Biz
 *
 * @author 268
 */
@Service
public class ResultFormBiz extends BaseBiz<ResultForm, ResultFormDao> {
    @Autowired
    private ResultBiz resultBiz;
    @Autowired
    private SubSectionBiz subSectionBiz;


    /**
     * 添加
     *
     * @param resultForm
     */
    public void addResultForm(ResultForm resultForm) {
        resultForm.setStatus(1);
        this.save(resultForm);
    }

    /**
     * 修改
     *
     * @param resultForm
     */
    public void updateResultForm(ResultForm resultForm) {
        this.update(resultForm);
    }

    /**
     * 查询成果形式列表
     */
    public List<ResultForm> getResultFormList(ResultForm resultForm) {
        String whereSql = " 1=1";
        if (!StringUtils.isTrimEmpty(resultForm.getName())) {
            whereSql += " and name like '%" + resultForm.getName() + "%'";
        }
        whereSql += " order by id asc";
        List<ResultForm> resultFormList = this.find(null, whereSql);
        return resultFormList;
    }

    public ResultForm getResultFormById(Long id) {
        return this.findById(id);
    }


    /**
     * @param request     请求
     * @param response    相应
     * @param queryResult 查询条件
     */
    public void xlsExcelExport(
            HttpServletRequest request, HttpServletResponse response,
            QueryResult queryResult) throws Exception {
        String dir = request
                .getSession()
                .getServletContext()
                .getRealPath("/excelfile/result");

        String[] headName;
        String rsName;
        Integer rsForm = queryResult.getResultForm();
        switch (rsForm) {
            case ResultFormConstants.PAPER:
                rsName = "论文";
                headName = new String[]{"论文名称", "所属处室", "是否公开",
                        "刊物性质", "级别", "发表刊物", "刊号", "发表时间",
                        "作者名称", "课题组成员", "字数", "备注"};
                break;
            case ResultFormConstants.BOOK:
                rsName = "著作";
                headName = new String[]{"著作名称", "所属处室", "类别", "级别",
                        "出版社", "出版时间", "主编", "参编组成员", "主编字数", "副主编",
                        "副主编字数", "参编章节信息", "备注"};
                break;
            case ResultFormConstants.QUESTION:
                rsName = "课题";
                headName = new String[]{"课题名称", "所属处室", "级别", "课题发布单位", "开始时间", "结束时间",
                        "课题负责人", "课题组成员", "字数", "备注"};
                break;
            case ResultFormConstants.INTERNAL_PUBLICATION:
                rsName = "内刊";
                headName = new String[]{"内刊名称", "所属处室", "是否公开",
                        "刊物性质", "级别", "发表刊物", "刊号", "发表时间",
                        "作者名称", "课题组成员", "字数", "备注"};
                break;
            case ResultFormConstants.OTHER:
                rsName = "其他";
                headName = new String[]{"成果名称", "所属处室", "是否公开",
                        "刊物性质", "级别", "发表刊物", "刊号", "发表时间",
                        "作者名称", "课题组成员", "字数", "备注"};
                break;

            default:
                rsName = "其他";
                headName = new String[]{"成果名称", "所属处室", "是否公开",
                        "刊物性质", "级别", "发表刊物", "刊号", "发表时间",
                        "作者名称", "课题组成员", "字数", "备注"};
        }

        String xlsName = rsName + "成果汇总-" +
                new SimpleDateFormat("yyyy-M-d").format(new Date());

        List<File> fileList = this.queryForExcel(queryResult, dir, headName, xlsName);
        // 生成的多excel的压缩包
        FileExportImportUtil.createRar(response, dir, fileList, xlsName);
    }

    /**
     * @param queryResult 查询语句
     * @param dir         输出文件夹
     * @param headName    表头
     * @param xlsName     文件名
     * @return 多文件
     */
    private List<File> queryForExcel(QueryResult queryResult, String dir, String[] headName, String xlsName) throws Exception {
        Pagination pagination = new Pagination();
        pagination.setPageSize(10000);
        resultBiz.queryForExcel(pagination, queryResult);
        int num = pagination.getTotalPages();


        List<File> fileList = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            pagination.setCurrentPage(i);
            List<Result> resultList = resultBiz.queryForExcel(pagination, queryResult);
            List<List<String>> voList = convert(resultList, queryResult.getResultForm());
            File file = FileExportImportUtil.createExcel(headName, voList, xlsName + "-" + i, dir);
            fileList.add(file);
        }
        return fileList;
    }

    /**
     * 转换单元格
     *
     * @param resultList 成果列表
     * @param resultForm 成果类别
     * @return List<List<String>>
     */
    private List<List<String>> convert(List<Result> resultList, Integer resultForm) {
        List<List<String>> list = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (ObjectUtils.isNotNull(resultList)) {
            // 查询处室列表
            List<SubSection> sectionList = subSectionBiz.findAll();
            Map<Integer, String> integerStringMap = new HashMap<>(20);
            sectionList.forEach(s -> integerStringMap.put(s.getId().intValue(), s.getName()));
            // level
            Map<Integer, String> levelMap = new HashMap<>(4);
            levelMap.put(1, "国家级");
            levelMap.put(2, "省部级");
            levelMap.put(3, "市级");
            levelMap.put(4, "校级");

            if (resultForm == ResultFormConstants.PAPER
                    || resultForm == ResultFormConstants.INTERNAL_PUBLICATION
                    || resultForm == ResultFormConstants.OTHER) {
                resultList.forEach(r -> {
                    List<String> small = new ArrayList<>();
                    list.add(small);
                    String name = r.getName();
                    Integer research = r.getTeacherResearch();
                    String researchName = integerStringMap.get(research);
                    Integer resultSwitch = r.getResultSwitch();
                    String journalNatureName = r.getJournalNatureName();
                    String levelName = levelMap.get(r.getLevel());
                    String publish = r.getPublish();
                    String publishNumber = r.getPublishNumber();
                    Date publishTime = r.getPublishTime();
                    String taskForceMember = r.getTaskForceMembers();
                    String workName = r.getWorkName();
                    Long wordsNumber = r.getWordsNumber();
                    String remark = r.getRemark();

                    small.add(name != null ? name : "");
                    small.add(researchName != null ? researchName : "");
                    small.add(resultSwitch != null ? (resultSwitch == 1 ? "公开" : "不公开") : "");
                    small.add(journalNatureName != null ? journalNatureName : "");
                    small.add(levelName != null ? levelName : "");
                    small.add(publish != null ? publish : "");
                    small.add(publishNumber != null ? publishNumber : "");
                    small.add(publishTime != null ? df.format(publishTime) : "");
                    small.add(workName != null ? workName : "");
                    small.add(taskForceMember != null ? taskForceMember : "");
                    small.add(wordsNumber != null ? wordsNumber.toString() : "");
                    small.add(remark != null ? remark : "");
                });
            }

            if (resultForm == ResultFormConstants.BOOK) {
                resultList.forEach(r -> {
                    List<String> small = new ArrayList<>();
                    list.add(small);
                    String name = r.getName();
                    Integer research = r.getTeacherResearch();
                    String researchName = integerStringMap.get(research);
                    String journalNatureName = r.getJournalNatureName();
                    String levelName = levelMap.get(r.getLevel());
                    String publish = r.getPublish();
                    Date publishTime = r.getPublishTime();
                    String taskForceMember = r.getTaskForceMembers();
                    String workName = r.getWorkName();
                    Long wordsNumber = r.getWordsNumber();
                    String associateEditor = r.getAssociateEditor();
                    Long associateNumber = r.getAssociateNumber();
                    String chapter = r.getChapter();
                    String remark = r.getRemark();

                    small.add(name != null ? name : "");
                    small.add(researchName != null ? researchName : "");
                    small.add(journalNatureName != null ? journalNatureName : "");
                    small.add(levelName != null ? levelName : "");
                    small.add(publish != null ? publish : "");
                    small.add(publishTime != null ? df.format(publishTime) : "");
                    small.add(taskForceMember != null ? taskForceMember : "");
                    small.add(workName != null ? workName : "");
                    small.add(wordsNumber != null ? wordsNumber.toString() : "");
                    small.add(associateEditor != null ? associateEditor : "");
                    small.add(associateNumber != null ? associateNumber.toString() : "");
                    small.add(chapter != null ? chapter : "");
                    small.add(remark != null ? remark : "");
                });
            }

            if (resultForm == ResultFormConstants.QUESTION) {
                resultList.forEach(r -> {
                    List<String> small = new ArrayList<>();
                    list.add(small);
                    String name = r.getName();
                    Integer research = r.getTeacherResearch();
                    String researchName = integerStringMap.get(research);
                    String levelName = levelMap.get(r.getLevel());
                    String publish = r.getPublish();
                    Date addTime = r.getAddTime();
                    Date endTime = r.getEndTime();
                    String taskForceMember = r.getTaskForceMembers();
                    String workName = r.getWorkName();
                    Long wordsNumber = r.getWordsNumber();
                    String remark = r.getRemark();

                    small.add(name != null ? name : "");
                    small.add(researchName != null ? researchName : "");
                    small.add(levelName != null ? levelName : "");
                    small.add(publish != null ? publish : "");
                    small.add(addTime != null ? df.format(addTime) : "");
                    small.add(endTime != null ? df.format(endTime) : "");
                    small.add(taskForceMember != null ? taskForceMember : "");
                    small.add(workName != null ? workName : "");
                    small.add(wordsNumber != null ? wordsNumber.toString() : "");
                    small.add(remark != null ? remark : "");
                });
            }
        }
        return list;
    }

    /**
     * 生态所成果列表导出
     */
    public void xlsQuestionExport(HttpServletRequest request, HttpServletResponse response, QueryResult queryResult) throws Exception {
        // 目录
        String dir = request.getSession().getServletContext().getRealPath("/excelfile/result");
        // 表头
        String[] headName = new String[]{"课题名称", "课题类型",
                "所属处室", "级别", "开始时间", "结束时间", "课题组成员",
                "课题负责人", "字数", "手机号", "邮箱", "备注"};
        // 文件名
        String xlsName = "成果汇总-" +
                new SimpleDateFormat("yyyy-M-d").format(new Date());
        // 文件
        List<File> fileList = this.queryForQuestionExport(queryResult, dir, headName, xlsName);
        // 生成的多excel的压缩包
        FileExportImportUtil.createRar(response, dir, fileList, xlsName);
    }

    /**
     * 导出课题列表
     *
     * @param queryResult 导出课题
     * @param dir         目录
     * @param headName    表头
     * @param xlsName     文件名
     * @return 文件
     */
    private List<File> queryForQuestionExport(QueryResult queryResult, String dir, String[] headName, String xlsName) throws Exception {

        Pagination pagination = new Pagination();
        pagination.setPageSize(10000);
        resultBiz.queryForQuestionExcel(pagination, queryResult);
        int fileNum = pagination.getTotalPages();
        List<File> fileList = new ArrayList<>();
        for (int i = 1; i <= fileNum; i++) {
            pagination.setCurrentPage(i);
            List<Result> resultList = resultBiz.queryForQuestionExcel(pagination, queryResult);
            List<List<String>> voList = convertQuestion(resultList);
            File file = FileExportImportUtil.createExcel(headName, voList, xlsName + "-" + i, dir);
            fileList.add(file);
        }
        return fileList;
    }

    /**
     * 生态所的成果列表
     *
     * @param resultList 成果列表
     * @return 可导出集合
     */
    private List<List<String>> convertQuestion(List<Result> resultList) {
        List<List<String>> list = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 20个处室
        List<SubSection> sectionList = subSectionBiz.findAll();
        Map<Integer, String> subsectionMap = new HashMap<>(20);
        sectionList.forEach(s -> subsectionMap.put(s.getId().intValue(), s.getName()));

        resultList.forEach(r -> {
            List<String> small = new ArrayList<>();
            list.add(small);

            // 课题名称
            String name = r.getName();
            // 课题类型
            Integer yearMonth = r.getYearOrMonthly();
            // 所属处室名称
            String researchName = subsectionMap.get(r.getTeacherResearch());
            // 级别
            String levelName = "校级";
            // 开始时间
            Date addTime = r.getAddTime();
            // 结束时间
            Date endTime = r.getEndTime();
            // 课题组成员
            String taskForceMembers = r.getTaskForceMembers();
            // 课题负责人
            String workName = r.getWorkName();
            // 字数
            Long wordsNumber = r.getWordsNumber();
            // 手机号
            String phoneNum = r.getPhoneNumber();
            // 邮箱
            String mail = r.getMailbox();
            // 备注
            String remark = r.getRemark();

            small.add(name != null ? name : "");
            small.add(yearMonth == 1 ? "年度" : "月度");
            small.add(researchName != null ? researchName : "");
            small.add(levelName);
            small.add(addTime != null ? df.format(addTime): "");
            small.add(endTime != null ? df.format(endTime): "");
            small.add(taskForceMembers != null ? taskForceMembers: "");
            small.add(workName != null ? workName: "");
            small.add(wordsNumber != null ? wordsNumber.toString(): "");
            small.add(phoneNum != null ? phoneNum: "");
            small.add(mail != null ? mail: "");
            small.add(remark != null ? remark: "");
        });

        return list;
    }
}
