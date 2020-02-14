package com.houqin.entity.lock;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * Created by caichenglong on 2017/10/25.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BedRoom{


    private Long id;

    private String name;

    private String code;

    private Date createTime;

    private Date updateTime;

    private Long status;

    //0是空的      1是住1人     2是住两人
    private int type;

}
