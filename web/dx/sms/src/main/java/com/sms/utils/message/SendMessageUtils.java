package com.sms.utils.message;

import com.google.gson.Gson;
import com.sms.common.SubmitServiceImplServiceStub;
import com.sms.common.SubmitServiceImplServiceStub.ArrayOfString;
import com.sms.common.SubmitServiceImplServiceStub.ReqHeader;
import com.sms.common.SubmitServiceImplServiceStub.SmsSubmit;
import com.sms.common.SubmitServiceImplServiceStub.SmsSubmit3;
import com.sms.common.SubmitServiceImplServiceStub.SmsSubmitE;
import com.sms.common.SubmitServiceImplServiceStub.SmsSubmitResponseE;
import com.sms.utils.util.MD5;
import com.sms.utils.util.Util;

/**
 * Created by Administrator on 2016/12/22.
 */
public class SendMessageUtils {

    private static Gson gson = new Gson();

    /**
     * @param mobiles          接收者手机号
     * @param context 内容
     * @return
     */
    @SuppressWarnings("unchecked")
    private static String sendMessage(String mobiles, String context) {
        try {
            String serverAddress = "http://218.201.202.146:20/msgService/services/submitService?wsdl";

            //设置请求消息头
            ReqHeader header = new SubmitServiceImplServiceStub.ReqHeader();

            header.setReqno(Util.getSequence());
            header.setSysid("851510135509");//由企业通信助手分配
            String password = "gyswdx583";//由企业通信助手分配
            String magic = "hsgwbudhw";//由企业通信助手分配
            header.setAuthCode(new MD5().getMD5ofStr(password + magic));

            //设置请求消息体
            SmsSubmitE smsSubmit = new SmsSubmitE();
            smsSubmit.setSourceAddr("1065733303095");//发送地址
            ArrayOfString param = new ArrayOfString();//手机号码数组，一次可发送多个号码

            String[] mobile=mobiles.split(",");
            if(mobile!=null){
                for(int i=0;i<mobile.length;i++){
                    param.addString(mobile[i].toString());
                }
            }
            smsSubmit.setDest(param);
            smsSubmit.setContent(context);//短信内容

            //组装消息
            SmsSubmit req = new SmsSubmit();
            req.setArg0(header);
            req.setArg1(smsSubmit);

            SmsSubmit3 submit3 = new SmsSubmit3();
            submit3.setSmsSubmit(req);

            //发送消息
            SubmitServiceImplServiceStub stub = new SubmitServiceImplServiceStub(serverAddress);
            SmsSubmitResponseE res = stub.smsSubmit(submit3);

            String result = res.getSmsSubmitResponse().get_return().getResultCode();

            System.out.println("发送结果[result=" + result + "]");
            if(result.toString().equals("0")) {
            }
        } catch (java.rmi.RemoteException remoteException) {
            remoteException.printStackTrace();
        }

        return null;
    }

    /**
     * 发送短信
     *
     * @param mobiles  接收者手机号
     * @param context 内容
     * @return
     */
    public static String sendMsg( String mobiles, String context) {
        return sendMessage(mobiles,context);
    }
}
