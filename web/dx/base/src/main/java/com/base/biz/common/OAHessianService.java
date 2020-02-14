package com.base.biz.common;

import java.util.List;
import java.util.Map;

/**
 * Created by xiangdong on 2018-6-2.
 */
public interface OAHessianService {
    /**
     * 获取待处理数量
     * @param userId
     * @return
     */
    public Integer approvalNum (Long userId);

    /**
     * 获取待审批的公文列表
     * @param userId
     * @return
     */
    List<Map<String, Object>> getOaApprovalList(Long userId);

    /**
     * 签收
     * @param userId
     * @return
     */
    void taskClaim(String taskId,Long userId);

    /**
     * 获取未读公文列表
     * @param userId
     * @return
     */
    List<Map<String, String>> getUnreadOaApprovalList(Long userId);
    /**
     * 查询套红
     * @param processDefinitionId
     * @return
     */
    Map<String, String> getOaTaoHong (String  processDefinitionId);
}