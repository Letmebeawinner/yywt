package com.houqin.controller.transfer;

import com.a_268.base.controller.BaseController;
import com.houqin.biz.transfer.TransferBiz;
import com.houqin.entity.transfer.Transfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 资产调拨
 *
 * @author YaoZhen
 * @date 01-25, 19:39, 2018.
 */
@Controller
@RequestMapping("admin/houqin")
public class TransferController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(TransferController.class);

    @Autowired private TransferBiz transferBiz;

    public @RequestMapping("/queryForTransferList") ModelAndView queryForTransferList(Long id) {
        ModelAndView mv = new ModelAndView("/transfer/transfer_list");
        try {
            List<Transfer> transferList = transferBiz.find(null, " propertyMessageId = " + id);
            mv.addObject("transferList", transferList);
        } catch (Exception e) {
            logger.error("TransferController.queryForTransferList", e);
        }
        return mv;
    }
}
