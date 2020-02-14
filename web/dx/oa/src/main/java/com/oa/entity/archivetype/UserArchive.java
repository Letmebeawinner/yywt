package com.oa.entity.archivetype;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户档案关联信息
 *
 * @author lzh
 * @create 2017-11-28 19:09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserArchive extends BaseEntity{
    //被操作者的用户id （即被分配借阅档案权限的人）
    private Long userId;
    //档案id
    private Long archiveId;
    //当前操作这id
    private Long createId;
}
