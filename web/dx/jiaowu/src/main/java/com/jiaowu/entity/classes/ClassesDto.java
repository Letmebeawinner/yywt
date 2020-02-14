package com.jiaowu.entity.classes;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 班次表
 *
 * @author 李帅雷
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ClassesDto extends Classes {
    /**
     * 相差多少天
     */
    private Integer comparDays = 0;
}
