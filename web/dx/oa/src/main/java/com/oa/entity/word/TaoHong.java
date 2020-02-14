package com.oa.entity.word;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by xiangdong on 2018/5/31 0031.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TaoHong extends BaseEntity {
    /**
     * status 1代表已读2代表未读
     **/
    private Long sysUserId;//用户id
    private String pathUrl;//路径
    private String processDefinitionId;//文档流id
    private Integer readStatus;//读取状态
    private Integer type;//模板类型


}
