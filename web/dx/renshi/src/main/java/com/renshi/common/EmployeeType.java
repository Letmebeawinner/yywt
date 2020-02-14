package com.renshi.common;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @author YaoZhen
 * @date 06-08, 20:12, 2018.
 */
public class EmployeeType {
    public static Map<Long, String> empType = new ImmutableMap.Builder<Long, String>()
            .put(2L, "县级非领导")
            .put(3L, "校领导")
            .put(4L, "中层干部")
            .put(5L, "一般干部")
            .put(6L, "技术工人")
            .build();
}
