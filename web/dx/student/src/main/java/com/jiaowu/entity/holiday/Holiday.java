package com.jiaowu.entity.holiday;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.tools.ant.types.resources.Last;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by 李帅雷 on 2017/8/28.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Holiday extends BaseEntity {
    private static final long serialVersionUID = -5369066683966965335L;
    //班次ID
    private Long classId;
    //班次名称
    private String className;
    //用户ID
    private Long userId;
    //3是学员,2是教师.
    private Integer type;
    //用户名称
    private String userName;
    //开始时间
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date beginTime;
    //结束时间
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date endTime;
    //假期时间长度
    private String length;
    //原因
    private String reason;
    //请假类型
    private String leaType;
    //上午还是下午开始
    private Integer beginLastAfternoon;
    //上午还是下午结束
    private Integer endLastAfternoon;

    //status=4是删除


}
