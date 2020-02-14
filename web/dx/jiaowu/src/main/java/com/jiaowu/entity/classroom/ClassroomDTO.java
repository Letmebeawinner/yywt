package com.jiaowu.entity.classroom;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by 李帅雷 on 2018/1/2.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ClassroomDTO extends Classroom {
    private static final long serialVersionUID = 3884624528920917825L;
    //主持人名字
    private String compereName;
    //主持人职务
    private String compereJob;

    public ClassroomDTO(){

    }
    public ClassroomDTO(Classroom classroom){
        if(classroom!=null) {
            this.setId(classroom.getId());
            this.setStatus(classroom.getStatus());
            this.setCreateTime(classroom.getCreateTime());
            this.setUpdateTime(classroom.getUpdateTime());
            this.setPosition(classroom.getPosition());
            this.setNum(classroom.getNum());
            this.setNote(classroom.getNote());
            this.setType(classroom.getType());
            this.setCompereId(classroom.getCompereId());
        }
    }
}
