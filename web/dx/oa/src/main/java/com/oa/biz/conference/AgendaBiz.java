package com.oa.biz.conference;

import com.a_268.base.core.BaseBiz;
import com.oa.dao.conference.AgendaDao;
import com.oa.entity.conference.Agenda;
import com.oa.entity.conference.OaMeetingTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 议程
 *
 * @author YaoZhen
 * @create 10-23, 11:29, 2017.
 */
@Service
public class AgendaBiz extends BaseBiz<Agenda, AgendaDao> {

    @Autowired
    private OaMeetingTopicBiz oaMeetingTopicBiz;
    /**
     * 保存议程，同时，修改议题状态
     * @param agenda
     */
    public void tx_saveAgenda(Agenda agenda) {
        this.save(agenda);
        if (agenda.getTopicId() != null) {
            OaMeetingTopic oaMeetingTopic = new OaMeetingTopic();
            //设置成已使用状态
            oaMeetingTopic.setStatus(2);
            oaMeetingTopic.setId(agenda.getTopicId());
            oaMeetingTopicBiz.update(oaMeetingTopic);
        }
    }

    public void tx_updateAgenda(Agenda agenda, Long oldTopicId) {
        this.update(agenda);
        if (agenda.getTopicId() != null) {
            OaMeetingTopic oaMeetingTopic = new OaMeetingTopic();
            if (oldTopicId != null) {
                //修改后，将之前的议题设置成未使用状态
                oaMeetingTopic.setStatus(0);
                oaMeetingTopic.setId(oldTopicId);
                oaMeetingTopicBiz.update(oaMeetingTopic);
            }
            //设置成已使用状态
            oaMeetingTopic.setStatus(2);
            oaMeetingTopic.setId(agenda.getTopicId());
            oaMeetingTopicBiz.update(oaMeetingTopic);

        }
    }

}
