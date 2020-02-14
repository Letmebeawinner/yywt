package com.oa.biz.workflow;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.core.Pagination;
import com.oa.biz.common.BaseHessianBiz;
import com.oa.biz.letter.UserLetterBiz;
import com.oa.common.StreamUtil;
import com.oa.dao.workflow.OaLetterDao;
import com.oa.entity.letter.UserLetter;
import com.oa.entity.sysuser.SysUser;
import com.oa.entity.workflow.OaLetter;
import com.oa.entity.workflow.OaLetterDto;
import com.oa.utils.GenerateSqlUtil;
import org.activiti.engine.TaskService;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * oa公文业务层
 *
 * @author lzh
 * @create 2017-03-16-11:42
 */
@Service
public class OaLetterBiz extends BaseBiz<OaLetter, OaLetterDao> {

    private static Logger logger = LoggerFactory.getLogger(OaLetterBiz.class);
    @Autowired
    private WorkflowFormBiz workflowFormBiz;
    @Autowired
    private UserLetterBiz userLetterBiz;
    @Autowired
    private TaskService taskService;
    @Autowired
    private BaseHessianBiz baseHessianBiz;

    /**
     * 开启公文工作流
     * @param oaLetter
     * @param processDefinitionId
     * @param userId
     * @return
     */
    public String tx_startLetterProcess(OaLetter oaLetter, String processDefinitionId, Long userId) {
        return startLetterProgress(oaLetter, processDefinitionId, userId, null);
    }

    /**
     * 内部公文
     * @param oaLetter
     * @param processDefinitionId
     * @param userId
     * @param userIds
     * @return
     */
    public String tx_startLetterProcess(OaLetter oaLetter, String processDefinitionId, Long userId, String userIds) {
        return startLetterProgress(oaLetter, processDefinitionId, userId, userIds);
    }

    private String startLetterProgress(OaLetter oaLetter, String processDefinitionId, Long userId, String userIds) {
        Map<String, Object> map = new HashMap<>();
        if (!com.a_268.base.util.StringUtils.isTrimEmpty(userIds)) {
            map.put("ok", true);
            map.put("audit", userIds);
        } else {
            map.put("ok", false);
        }
        oaLetter.setApplyId(userId);
        oaLetter.setApplyTime(new Date());
        oaLetter.setAudit(0);
        this.save(oaLetter);
        Long id = oaLetter.getId();
        String businessKey = "oaLetterBiz." + id;
        String processInstanceId = workflowFormBiz.startWorkFlowById(userId, businessKey, processDefinitionId, map);
        oaLetter.setProcessInstanceId(processInstanceId);
        this.update(oaLetter);
        return processInstanceId;
    }

    /**
     * @Description: 根据流程实例id返回值
     * @author: lzh
     * @Param: [processInstanceId]
     * @Return: com.oa.entity.workflow.OaLetter
     * @Date: 10:54
     */
    public OaLetter getOaLetterByProcessInstanceId(String processInstanceId) {
        String sql = " processInstanceId = " + processInstanceId;
        List<OaLetter> oaLetters = this.find(null, sql);
        return oaLetters.get(0);
    }

    /**
     * 开始审批
     * @param oaLetter
     * @param taskId
     * @param comment
     */
    public void tx_startLetterApplyAudit(OaLetter oaLetter, String taskId, String comment, Long userId) {
        String sql = " processInstanceId = " + oaLetter.getProcessInstanceId();
        this.updateByStrWhere(oaLetter, sql);
        workflowFormBiz.tx_startAudit(taskId, oaLetter.getProcessInstanceId(), comment, oaLetter.getAudit(), userId);
    }

    /**
     * 开始审批
     * @param oaLetter
     * @param taskId
     * @param comment
     */
    public void tx_startLetterApplyAudit(OaLetter oaLetter, String taskId, String comment, Long userId, Long approvalId) {
        String sql = " processInstanceId = " + oaLetter.getProcessInstanceId();
        this.updateByStrWhere(oaLetter, sql);
        workflowFormBiz.tx_startAudit(taskId, oaLetter.getProcessInstanceId(), comment, oaLetter.getAudit(), userId,approvalId);
    }

    /**
     * 内部公文审核
     */
    public void tx_stsrtInnerLetterApplyAudit(OaLetter oaLetter, String taskId, String comment, Long userId, String userIds) {
        String sql = " processInstanceId = " + oaLetter.getProcessInstanceId();
        if (oaLetter.getAudit().equals(0) && com.a_268.base.util.StringUtils.isTrimEmpty(userIds)) {
            oaLetter.setAudit(1);
        }
        this.updateByStrWhere(oaLetter, sql);
        workflowFormBiz.tx_startAudit(taskId, oaLetter.getProcessInstanceId(), comment, oaLetter.getAudit(), userId, userIds);
    }

    /**
     *
     * @param taskId 任务id
     * @param userIds 需要发送的用户ids
     * @param letterId 公文id
     * @param creatorId 操作者id
     */
    public void saveLetterPublish(String taskId, String userIds, Long letterId, Long creatorId) {
        // 批量发送公文
        if (!StringUtils.isEmpty(userIds)) {
            String[] ids = userIds.split(",");
            List<UserLetter> userLetterList = Arrays.stream(ids)
                  .map(id -> {
                      UserLetter userLetter = new UserLetter();
                      userLetter.setUserId(Long.parseLong(id));
                      userLetter.setCreateId(creatorId);
                      userLetter.setLetterId(letterId);
                      userLetter.setStatus(0);
                      return userLetter;
                  })
                  .collect(Collectors.toList());
            userLetterBiz.saveBatch(userLetterList);
            taskService.complete(taskId);
        }
    }

    public List<OaLetterDto> getOaLetterDtoList(Pagination pagination, OaLetter oaLetter, String applyName) {
        String sql = GenerateSqlUtil.getSql(oaLetter);
        if (StringUtils.isNotEmpty(applyName)) {
            SysUser sysUser = new SysUser();
            sysUser.setUserName(applyName);

            List<SysUser> sysUsers = baseHessianBiz.getSysUserList(sysUser);
            if (sysUsers != null && sysUsers.size() > 0) {
                String userIds = sysUsers.stream().map(sysUser1 -> sysUser1.getId().toString())
                                                  .collect(Collectors.joining(",", "(", ")"));
                sql += " and applyId in " + userIds;
            }
        }
        List<OaLetter> oaLetters = this.find(pagination, sql);
        return StreamUtil.map(oaLetters, (oaLetter1 -> convertToConvert(oaLetter1)));
    }

    private OaLetterDto convertToConvert(OaLetter oaLetter) {
        OaLetterDto oaLetterDto = new OaLetterDto();
        BeanUtils.copyProperties(oaLetter, oaLetterDto);
        if (oaLetter.getApplyId() != null) {
            SysUser sysUser = baseHessianBiz.getSysUserById(oaLetter.getApplyId());
            if (sysUser != null) {
                oaLetterDto.setApplyName(sysUser.getUserName());
            }
        }
        return oaLetterDto;
    }

    public List<Map<String,Object>> getOaLetterListByUserId(Pagination pagination, Long userId) {
        String sql = " 1 = 1";
        if (userId!=null && userId>0) {
            sql += " and applyId = " + userId;
        }
        List<OaLetter> oaLetters = this.find(pagination, sql);
        return StreamUtil.map(oaLetters, (oaLetter -> oaLetterToMap(oaLetter)));
    }

    private Map<String,Object> oaLetterToMap(OaLetter oaLetter) {
        Map<String,Object> map=new HashMap<>();
        if (oaLetter!= null) {
            map.put("id",oaLetter.getId());
            map.put("status",oaLetter.getStatus());
            map.put("createTime",oaLetter.getCreateTime());
            map.put("updateTime",oaLetter.getUpdateTime());
            map.put("sysUserId",oaLetter.getApplyId());
            map.put("title",oaLetter.getTitle());
            map.put("context",oaLetter.getContext());
        }
        return map;
    }

}
