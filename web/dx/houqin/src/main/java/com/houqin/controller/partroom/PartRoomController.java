package com.houqin.controller.partroom;

import com.a_268.base.controller.BaseController;
import com.houqin.biz.partroom.PartRoomBiz;
import com.houqin.controller.card.CardController;
import com.houqin.entity.partroom.PartRoom;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin/houqin")
public class PartRoomController extends BaseController {

    private static final Logger logger = Logger.getLogger(CardController.class);

    @Autowired
    private PartRoomBiz partRoomBiz;

    /**
     * 查询已开通的用户
     *
     * @param request
     * @return
     */
    @RequestMapping("/queryPartRoom")
    public String queryPartRoom(HttpServletRequest request) {
        try {
            List<PartRoom> partRoomList = partRoomBiz.find(null, "");
            request.setAttribute("", partRoomList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("PartRoomController-queryPartRoom");
        }
        return "";
    }


}
