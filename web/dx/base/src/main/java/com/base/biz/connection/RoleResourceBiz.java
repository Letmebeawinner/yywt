package com.base.biz.connection;

import com.a_268.base.core.BaseBiz;
import com.a_268.base.util.ObjectUtils;
import com.a_268.base.util.StringUtils;
import com.base.dao.connection.RoleResourceDao;
import com.base.entity.connection.RoleResource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 角色权限关联Biz
 *
 * @author s.li
 * @create 2016-12-13-16:59
 */
@Service
public class RoleResourceBiz extends BaseBiz<RoleResource, RoleResourceDao> {


    /**
     * 更新指定角色拥有的权限.<br>
     *
     * @param roleId         指定角色的id
     * @param resourceIdsStr 指定角色拥有的权限。使用","分割，如1, 2, 3
     */
    public void tx_updateRoleResources(Long roleId, String resourceIdsStr) {
        String where = " roleId=" + roleId;
        List<RoleResource> oldRoleResourceList = find(null, where);
        if (ObjectUtils.isNotNull(oldRoleResourceList)) {
            if (oldRoleResourceList.size() == 1) {
                delete(oldRoleResourceList.get(0));
            } else {
                List<Long> roleResourceIdsList = new LinkedList<>();
                oldRoleResourceList.forEach(roleResource -> roleResourceIdsList.add(roleResource.getId()));
                deleteByIds(roleResourceIdsList);
            }
        }
        // resourceIdStr不为空时更新角色拥有的权限
        if (!StringUtils.isTrimEmpty(resourceIdsStr)) {
            String[] resourceIdsStrArray = resourceIdsStr.split(",");
            List<RoleResource> newRoleResourceList = new LinkedList<>();
            Arrays.asList(resourceIdsStrArray).forEach(resourceId -> {
                RoleResource roleResource = new RoleResource();
                roleResource.setRoleId(roleId);
                roleResource.setResourceId(Long.valueOf(resourceId));
                newRoleResourceList.add(roleResource);
            });
            saveBatch(newRoleResourceList);
        }
    }
}
