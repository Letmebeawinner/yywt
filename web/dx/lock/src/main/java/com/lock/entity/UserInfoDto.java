package com.lock.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfoDto {

    private Long userId;

    private String userName;

    private Long bedchamberId;

    private Long  id;

    private String name;

}
