package com.app.biz.common;

import java.util.List;
import java.util.Map;

/**
 * app模块Hessian接口
 *
 * @author sk
 * @since 2017-02-23
 */
public interface AppHessianService {

    /**
     * 获取指定os平台的app更新信息
     *
     * @param mobileType app os平台类型。 1. Android 2.IOS
     * @return app更新信息。包括版本号(version)、下载链接(updateUrl)等
     */
    Map<String, String> getAppUpdate(Integer mobileType);

    /**
     * 获取所有os平台的app更新信息
     *
     * @return app更新信息。
     * 包括版本号(version)、os平台(mobileType)、下载链接(updateUrl)等
     */
    List<Map<String, String>> listAppUpdate();
}
