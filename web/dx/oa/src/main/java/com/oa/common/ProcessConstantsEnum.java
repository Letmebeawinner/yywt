package com.oa.common;

/**
 * 流程的定义常量
 *
 * @author lzh
 * @create 2017-12-29-18:13
 */
public enum ProcessConstantsEnum {

    LEAVE("oa_leave_apply", "oa_leave", "请假流程"),
    CAR("oa_car_apply", "oa_car", "用车流程"),
    LETTER("oa_letter_apply", "oa_letter", "公文流程"),
    INNER_LETTER("oa_inner_letter", "oa_inner", "内部公文流程"),
    MEETING("oa_meeting_apply", "oa_meeting", "会议流程"),
    ARCHIVE("oa_archive_apply", "oa_archive", "档案申请流程"),
    AGENDA("oa_agenda_apply", "oa_agenda", "议程申请流程"),
    CONFERENCE_TOPIC("oa_conference_topic_apply", "oa_conference", "议题申请流程"),
    SIMPLE_CONFERENCE_TOPIC("oa_simple_conference_apply", "oa_simple_conference", "简单议题申请流程"),
    NEWS("oa_news_apply", "oa_news", "信息发布申请流程"),
    SEAL("oa_seal_apply", "oa_seal", "印章申请流程");

    private String url;
    private String key;
    private String description;

    ProcessConstantsEnum(String url, String key, String description) {
        this.url = url;
        this.description = description;
        this.key = key;
    }

    public String getUrl() {
        return url;
    }


    public String getDescription() {
        return description;
    }

    public String getKey() {
        return key;
    }

}
