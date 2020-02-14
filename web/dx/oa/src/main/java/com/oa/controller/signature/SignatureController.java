package com.oa.controller.signature;

import com.a_268.base.constants.ErrorCode;
import com.a_268.base.controller.BaseController;
import com.oa.biz.signature.SignatureBiz;
import com.oa.entity.signature.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author lzh
 * @create 2017-12-26-10:04
 */
@Controller
@RequestMapping("/admin/oa")
public class SignatureController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SignatureController.class);

    @Autowired
    private SignatureBiz signatureBiz;


    @InitBinder("signature")
    public void initBinder(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("signature.");
    }

    /**
     * 保存签章信息
     * @param signature
     * @return
     */
    @RequestMapping("/signature/save")
    @ResponseBody
    public Map<String, Object> saveSignature(@ModelAttribute("signature")Signature signature) {
        Map<String, Object> json = null;
        String sql = " signatureId = '" + signature.getSignatureId()  + "' and documentType=" + signature.getDocumentType();
        try {
            List<Signature> signatures = signatureBiz.find(null, sql);
            if (signatures == null || signatures.size() == 0) {
                signatureBiz.save(signature);
            }
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch(Exception e) {
            logger.error("SignatureController.saveSignature", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }


    /**
     * 获取所有的印章信息
     * @return
     */
    @RequestMapping("/ajax/signature/get")
    @ResponseBody
    public Map<String, Object> getSignature(@RequestParam("documentId") String documentId,@RequestParam("documentType") Integer documentType) {
        Map<String, Object> json = null;
        String sql = " documentId = '" + documentId + "' and documentType=" + documentType + " and status = 0";
        try {
            List<Signature> signatureList = signatureBiz.find(null, sql);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, signatureList);
        } catch(Exception e) {
            logger.error("SignatureController.getSignature", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 修改签章信息
     * @param signature
     * @return
     */
    @RequestMapping("/signature/update")
    @ResponseBody
    public Map<String, Object> updateSignature(@ModelAttribute("signature")Signature signature) {
        Map<String, Object> json = null;
        String sql = " documentId= '" + signature.getDocumentId() + "' and documentType=" + signature.getDocumentType()
                + " and signatureId = '" + signature.getSignatureId() + "'";
        try {
            signatureBiz.updateByStrWhere(signature, sql);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch(Exception e) {
            logger.error("SignatureController.updateSignature", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }

    /**
     * 删除签章信息
     * @param signature
     * @return
     */
    @RequestMapping("/signature/delete")
    @ResponseBody
    public Map<String, Object> deleteSignature(@ModelAttribute("signature")Signature signature) {
        Map<String, Object> json = null;
        String sql = " documentId= '" + signature.getDocumentId() + "' and documentType=" + signature.getDocumentType()
                + " and signatureId = '" + signature.getSignatureId() + "'";
        try {
            signatureBiz.updateByStrWhere(signature, sql);
            json = this.resultJson(ErrorCode.SUCCESS, ErrorCode.SUCCESS_MSG, null);
        } catch(Exception e) {
            logger.error("SignatureController.deleteSignature", e);
            json = this.resultJson(ErrorCode.ERROR_SYSTEM, ErrorCode.SYS_ERROR_MSG, null);
        }
        return json;
    }
}
