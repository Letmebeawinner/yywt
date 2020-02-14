package com.oa.entity.notice;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知公告拓展实体
 *
 * @author ccl
 * @create 2017-02-09-17:29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NoticeDto extends Notice{

    private String typeName;

}
