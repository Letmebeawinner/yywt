package com.oa.controller.activiti;

import com.oa.common.ProcessConstantsEnum;

/**
 * 流程工厂(简单工厂模式)
 *
 * @author lzh
 * @create 2018-01-02-10:56
 */
public class ProcessFactory {

    public static State createProcessFactory(String key) {
        if (ProcessConstantsEnum.LEAVE.getUrl().equals(key) || (key.indexOf(ProcessConstantsEnum.LEAVE.getKey()) > -1)) {
            //请假申请
            return new LeaveState();
        } else if (ProcessConstantsEnum.LETTER.getUrl().equals(key)
                || (key.indexOf(ProcessConstantsEnum.LETTER.getKey()) > -1)
                || ProcessConstantsEnum.INNER_LETTER.getUrl().equals(key)
                || key.indexOf(ProcessConstantsEnum.INNER_LETTER.getKey()) > -1) {
            return new LetterState();
        } else if (ProcessConstantsEnum.CAR.getUrl().equals(key) || (key.indexOf(ProcessConstantsEnum.CAR.getKey()) > -1)) {
            //用车申请
            return new CarState();
        } else if (ProcessConstantsEnum.NEWS.getUrl().equals(key) || (key.indexOf(ProcessConstantsEnum.NEWS.getKey()) > -1)) {
            return new NewsState();
        } else if (ProcessConstantsEnum.MEETING.getUrl().equals(key) || (key.indexOf(ProcessConstantsEnum.MEETING.getKey()) > -1)) {
            //会议室申请
            return null;
        } else if (ProcessConstantsEnum.ARCHIVE.getUrl().equals(key) || (key.indexOf(ProcessConstantsEnum.ARCHIVE.getKey()) > -1)) {
            //档案申请
            return new ArchiveState();
        } else if ((ProcessConstantsEnum.CONFERENCE_TOPIC.getUrl().equals(key) || ProcessConstantsEnum.SIMPLE_CONFERENCE_TOPIC.getUrl().equals(key))
                || (key.indexOf(ProcessConstantsEnum.CONFERENCE_TOPIC.getKey()) > -1)) {
            //议题申请
            return new ConferenceTopicState();
        } else if ((ProcessConstantsEnum.AGENDA.getUrl().equals(key) || (key.indexOf(ProcessConstantsEnum.AGENDA.getKey()) > -1))) {
            //议程申请
            return new OaMeetingAgendaState();
        }else if ((ProcessConstantsEnum.SEAL.getUrl().equals(key) || ProcessConstantsEnum.SEAL.getUrl().equals(key))
                || (key.indexOf(ProcessConstantsEnum.SEAL.getKey()) > -1)) {
            //印章申请
            return new SealState();
        } else {
            return null;
        }
    }
}
