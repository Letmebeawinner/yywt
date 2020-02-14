package com.jiaowu.entity.courseware;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by 李帅雷 on 2017/10/21.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Courseware extends BaseEntity{
    private static final long serialVersionUID = 6411906128372745432L;
    //名称
    private String title;
    //文件地址
    private String url;
    //教师ID
    private Long teacherId;
    //教师名称
    private String teacherName;
    //教师专题库ID
    private Long teacherLibraryId;
}
