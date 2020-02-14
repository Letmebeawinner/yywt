package com.menhu.enums;

/**
 * 数据资讯类型枚举
 */
public enum ArticleEnum {
    不轮播(1),
    轮播显示(2),




    通知公告(1),
    公文处理(2),
    科研咨政(3),
    新闻资讯(4),
    信息公开(5),
    政策法规(6),
    教学动态(7),
    在线交流(8);
    private int type;

    ArticleEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}