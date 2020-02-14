package com.base.enums;

/**
 * 权限资源所属站点类型
 */
public enum ResourceSite {
    基础系统("BASE"),
    档案系统("DA"),
    后勤系统("HQ"),
    干部系统("GB"),
    教务管理("JW"),
    学员管理("XY"),
    OA系统("OA"),
    人事系统("HR"),
    科研管理("KY"),
    咨政管理("ZZ"),
    门户系统("MH"),
    短信系统("SMS"),
    统计分析("TJFX"),
    掌上校园("APP"),
    在线学习("ZXXY");

    private String siteKey;

    ResourceSite(String siteKey){
        this.siteKey = siteKey;
    }

    public String getSiteKey() {
        return siteKey;
    }

    public void setSiteKey(String siteKey) {
        this.siteKey = siteKey;
    }
}
