package com.zaixianxuexi.common;

import com.a_268.base.util.ObjectUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by caichenglong on 2017/10/17.
 */
public class HttpUtil {


    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public HttpUtil() {
    }

    public static String doGet(String url, String queryString) {
        String response = null;
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(url);
        method.getParams().setParameter("http.protocol.content-charset", "UTF-8");

        try {
            if(StringUtils.isNotBlank(queryString)) {
                method.setQueryString(URIUtil.encodeQuery(queryString));
            }

            client.executeMethod(method);
            if(method.getStatusCode() == 200) {
                response = method.getResponseBodyAsString();
            }
        } catch (URIException var10) {
            ;
        } catch (IOException var11) {
            ;
        } finally {
            method.releaseConnection();
        }

        return response;
    }

    public static String doPost(String url, Map<String, String> params) {
        logger.debug("Post Url:" + url + ",Params:" + params);
        StringBuffer result = new StringBuffer();
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        method.getParams().setParameter("http.protocol.content-charset", "UTF-8");
        if(!ObjectUtils.isNull(params)) {
            NameValuePair[] e = new NameValuePair[params.size()];
            int str = 0;

            Map.Entry entry;
            for(Iterator i$ = params.entrySet().iterator(); i$.hasNext(); e[str++] = new NameValuePair((String)entry.getKey(), (String)entry.getValue())) {
                entry = (Map.Entry)i$.next();
            }

            method.setRequestBody(e);
        }

        try {
            client.executeMethod(method);
            if(method.getStatusCode() == 200) {
                BufferedReader var14 = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8"));
                String var15 = null;

                while((var15 = var14.readLine()) != null) {
                    result.append(var15);
                }
            }
        } catch (IOException var12) {
            ;
        } finally {
            method.releaseConnection();
        }

        logger.debug("Post Return:" + result.toString());
        return result.toString();
    }
}
