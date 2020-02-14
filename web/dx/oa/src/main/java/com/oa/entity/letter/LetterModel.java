package com.oa.entity.letter;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 红头公文模板
 *
 * @author jin shuo
 * @create 2018-07-31-16:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LetterModel extends BaseEntity {

    private String modelName;//模板名

    private String fileName;//模板文件名

    private String fileUrl;//模板文件地址

    private Integer sort;//排序值
}
