package com.oa.entity.letter;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户公文关联表
 *
 * @author lzh
 * @create 2017-03-14-11:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserLetter extends BaseEntity{
    private Long userId;//系统用户
    private Long letterId;//公文id
    private Long createId;//创建者id(即发送人)
}
