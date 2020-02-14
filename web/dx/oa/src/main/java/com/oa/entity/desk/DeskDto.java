package com.oa.entity.desk;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 桌面拓展表
 *
 * @author ccl
 * @create 2017-01-20-15:03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeskDto extends Desk {
    private Long userId;

    private String name;//标题

    private String link;//链接

    private Long sort;//排序

}
