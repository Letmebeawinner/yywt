package com.jiaowu.entity.registerDeadline;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * Created by 李帅雷 on 2017/8/2.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RegisterDeadline extends BaseEntity{
    private static final long serialVersionUID = 5443058196287434631L;
    //截止时间
    private Date deadline;
}
