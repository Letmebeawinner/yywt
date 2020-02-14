package com.oa.entity.file;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件管理
 *
 * @author ccl
 * @create 2017-01-04-9:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class File extends BaseEntity{

    private String title;//标题

    private Long userId;//操作人

    private String fileUrl;//文件地址

    private Long share;//分享状态

}
