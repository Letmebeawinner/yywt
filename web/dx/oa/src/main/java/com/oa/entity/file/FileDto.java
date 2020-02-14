package com.oa.entity.file;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件拓展类
 *
 * @author ccl
 * @create 2017-03-08-16:48
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FileDto extends File {

    private String sysUserName;//用户名

}
