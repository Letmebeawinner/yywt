package com.oa.entity.teacher;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 教师表
 *
 * @author ccl
 * @create 2017-01-20-17:11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Teacher extends BaseEntity{


    /*** 名称*/
    private String name;
    /*** 手机号*/
    private String mobile;
    /*** 邮箱*/
    private String email;
    public Teacher(){}
    public Teacher(Map<String,String> map){
        this.setId(Long.valueOf(map.get("id")));
        this.setName(map.get("name"));
        this.setMobile(map.get("mobile"));
        this.setEmail(map.get("email"));
    }


}
