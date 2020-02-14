package com.houqin.entity.partroom;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PartRoom extends BaseEntity{

    private String roomCard;//房间id

    private Long classesId;//班级id

    private Long userId;//用户id

}
