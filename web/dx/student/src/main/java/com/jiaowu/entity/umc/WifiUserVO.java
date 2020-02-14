package com.jiaowu.entity.umc;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 展示
 *
 * @author YaoZhen
 * @date 03-30, 11:45, 2018.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WifiUserVO extends WifiUser {
    private String overTime;
}
