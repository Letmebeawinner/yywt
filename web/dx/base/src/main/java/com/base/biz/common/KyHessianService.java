package com.base.biz.common;

import java.util.List;
import java.util.Map;

/**
 * Created by xiangdong on 2018-6-2.
 */
public interface KyHessianService {
    /**
     * 查询待我审批的
     *
     * @param userId userId
     * @param roleIds  roleId
     * @return 成果列表
     */
    List<Map<String, String>> queryResultListByRoleId(Long userId, String roleIds,Integer resultType);
}