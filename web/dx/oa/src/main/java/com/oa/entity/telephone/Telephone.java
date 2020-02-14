package com.oa.entity.telephone;

import com.a_268.base.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 电话本
 *
 * @author ccl
 * @create 2016-12-28-15:58
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Telephone extends BaseEntity{
    private String name;//姓名q

    private Long sex;//0男  1女

    private String telephone;//手机号码

    private String unit;//单位名称

    private String position;//单位职位

    private String email;//邮箱

    private int flag=0;

    public Telephone() {}

    public Telephone(Map<String, String> map) {
        this.name = map.get("name");
        this.setSex(map.get("sex")==null?null:Long.parseLong(map.get("sex")));
        this.telephone = map.get("telephone");
        this.unit = map.get("unit");
        this.position = map.get("position");
        this.email=map.get("email");
    }



}
