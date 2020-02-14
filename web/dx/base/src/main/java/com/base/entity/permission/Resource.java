package com.base.entity.permission;

import com.a_268.base.core.BaseEntity;
import com.a_268.base.enums.StateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 权限资源Entity
 *
 * @author s.li
 */
@Data
@EqualsAndHashCode
public class Resource extends BaseEntity {

    private static final long serialVersionUID = 5029370083968183113L;
    /**
     * 权限资源名
     */
    private String resourceName;
    /**
     * 权限资源描述
     */
    private String resourceDesc;
    /**
     * 权限资源路径
     */
    private String resourcePath;
    /**
     * 权限资源类型，1菜单，2功能
     */
    private Integer resourceType;
    /**
     * 权限资源所属站点
     */
    private String resourceSite;
    /**
     * 父ID
     */
    private Long parentId;
    /**
     * 排序优先级
     */
    private Integer resourceOrder;

    /**
     * 样式名
     **/
    private String styleName;
    /**
     * 子级权限
     */
    private List<Resource> childList;
    /**
     * 根据角色查询权限时判断角色是否拥有该权限
     * {@link StateEnum#AVAILABLE} 拥有
     * {@link StateEnum#NOT_AVAILABLE} 未拥有
     */
    private int roleHas = StateEnum.NOT_AVAILABLE.getState();
}
