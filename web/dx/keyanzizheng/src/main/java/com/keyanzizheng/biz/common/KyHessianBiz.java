package com.keyanzizheng.biz.common;

import com.a_268.base.core.Pagination;
import com.a_268.base.util.ObjectUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.keyanzizheng.biz.award.AwardBiz;
import com.keyanzizheng.biz.result.ResultBiz;
import com.keyanzizheng.biz.result.ResultFormBiz;
import com.keyanzizheng.biz.result.ResultStatisticsBiz;
import com.keyanzizheng.biz.result.TaskChangeBiz;
import com.keyanzizheng.constant.DeptHeadMapConstants;
import com.keyanzizheng.constant.StatusConstants;
import com.keyanzizheng.entity.award.Award;
import com.keyanzizheng.entity.result.*;
import com.keyanzizheng.utils.HessianUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 科研资政系统对外Hessain的Service实现类
 *
 * @author 268
 */
@Service
public class KyHessianBiz implements KyHessianService {
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    @Autowired
    private ResultBiz resultBiz;
    @Autowired
    private ResultStatisticsBiz resultStatisticsBiz;
    @Autowired
    private ResultFormBiz resultFormBiz;
    @Autowired
    private TaskChangeBiz taskChangeBiz;
    @Autowired
    private AwardBiz awardBiz;

    @Override
    public Map<String, String> queryAllResult(String resultJson, String paginationJson) {
        Map<String, String> map = new HashMap<>();
        QueryResult result = gson.fromJson(resultJson, QueryResult.class);
        Pagination pagination = gson.fromJson(paginationJson, Pagination.class);
        List<QueryResult> taskList = resultBiz.getResultList(pagination, result);
        map.put("taskList", gson.toJson(taskList));
        map.put("pagination", gson.toJson(pagination));
        return map;
    }

    @Override
    public Map<String, Object> resultStatistics(Integer type,Pagination pagination, Integer year, Integer month) {
        Map<String, Object> map = new HashMap<>();
        String m = null;
        if (ObjectUtils.isNotNull(month) && month < 10) m = "0" + month;
        List<ResultStatistics> resultStatisticsList = resultStatisticsBiz.getResultStatistics(type,pagination, year + "", m);
        map.put("resultStatisticsList", ObjectUtils.listObjToListMap(resultStatisticsList));
        map.put("pagination", pagination);
        return map;
    }

    /**
     * 查询成果形式列表
     *
     * @return json
     */
    @Override
    public Map<String, String> scientificResearchResults() {
        Map<String, String> json = new HashMap<>(16);
        List<ResultForm> resultFormList = resultFormBiz.getResultFormList(new ResultForm());
        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        json.put("resultFormList", gson.toJson(resultFormList));
        return json;
    }

    @Override
    public Boolean addScientificResearchResults(String json) {
        Result result = gson.fromJson(json, Result.class);
        Boolean flag;
        try {
            result.setStatus(1);
            result.setIntoStorage(1L);
            result.setPassStatus(1);
            result.setIfFile(StatusConstants.NEGATE);
            resultBiz.addResult(result);
            TaskChange taskChange = new TaskChange();
            taskChange.setTaskId(result.getId());
            taskChange.setOperate("课题申报成果");
            taskChange.setStatus(1);
            taskChangeBiz.save(taskChange);
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 查询个人的成果
     *
     * @param pagination 分页
     * @param sysUserId  userid
     * @return 成果列表
     */
    @Override
    public Map<String, String> queryResultListByUserId(Pagination pagination, Long sysUserId, Integer resultType,
                                                       Integer yearOrMonthly, String resultName) {
        Map<String, String> result = new HashMap<>(16);
        QueryResult queryResult = new QueryResult();
        queryResult.setResultForm(3);
        queryResult.setSysUserId(sysUserId);
        queryResult.setResultType(resultType);
        if (yearOrMonthly != null) {
            queryResult.setYearOrMonthly(yearOrMonthly);
        }
        if (resultName != null) {
            queryResult.setName(resultName);
        }
        List<QueryResult> resultList = resultBiz.getResultList(pagination, queryResult);
        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        result.put("list", gson.toJson(resultList));
        result.put("pagination", gson.toJson(pagination));
        return result;
    }

    /**
     * 查询成果详情
     *
     * @param id id
     * @return result json
     */
    @Override
    public String getResultById(Long id) {
        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(resultBiz.findById(id));
    }

    /**
     * 添加科研报名
     * 领导审批通过后, 由教师提交课题结项报告, 再由科研处审批
     *
     * @param byUrl 字节流
     * @return 是否添加成功
     */
    @Override
    public Boolean addProblemStatementDeclaration(byte[] byUrl, byte[] byId) {
        Boolean flag = true;
        try {
            String fileUrlDeclaration = (String) HessianUtil.deserialize(byUrl);
            Long resultId = (Long) HessianUtil.deserialize(byId);
            Result target = new Result();
            target.setFileUrlDeclaration(fileUrlDeclaration);
            target.setId(resultId);
            Integer i = resultBiz.update(target);
            if (i == 0) {
                flag = false;
            }
        } catch (IOException e) {
            flag = false;
        }
        return flag;
    }

    @Override
    public Boolean addAwardDeclaration(Map<String, Object> params) {
        Boolean flag = true;
        try {
            Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            String json = gson.toJson(params);
            Award award = gson.fromJson(json, Award.class);
            awardBiz.save(award);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    @Override
    public String listAwardByUserId(byte[] id) throws IOException {
        Long userId = Long.parseLong(HessianUtil.deserialize(id).toString());
        List<Award> awards = awardBiz.find(null, "userId = " + userId);
        Gson gson = (new GsonBuilder()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(awards);
    }

    @Override
    public Map<String, String> findAwardById(Long id) {
        Award award = awardBiz.findById(id);
        return ObjectUtils.objToMap(award);
    }

    /**
     * 查询待我审批的
     *
     * @param userId userId
     * @param roleIds  roleId
     * @return 成果列表
     */
    public List<Map<String, String>> queryResultListByRoleId(Long userId, String roleIds,Integer resultType){
        List<QueryResult> resultList= resultBiz.getProcessResultList(userId,roleIds,resultType);
        return ObjectUtils.listObjToListMap(resultList);
    }
}
