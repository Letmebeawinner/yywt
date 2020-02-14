package com.oa.common;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 流工具
 *
 * @author lzh
 * @create 2018-03-08-16:36
 */
public class StreamUtil {

    public static <T, R> List<R> map(List<T> data, Function<T, R> mapFunction) {
        if (data == null || data.size() == 0) {
            return null;
        }
        return data.stream().map(mapFunction).collect(Collectors.toList());
    }
}
