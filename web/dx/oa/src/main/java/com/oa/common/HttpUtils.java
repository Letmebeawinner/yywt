package com.oa.common;

import com.a_268.base.util.ObjectUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
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
import java.util.Map.Entry;

public class HttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public HttpUtils() {
    }

    public static String doGet(String url, String queryString) {
        String response = null;
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        method.getParams().setParameter("http.protocol.content-charset", "UTF-8");

        try {
            if (StringUtils.isNotBlank(queryString)) {
                method.setQueryString(URIUtil.encodeQuery(queryString));
            }

            client.executeMethod(method);
            if (method.getStatusCode() == 200) {
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
        if (!ObjectUtils.isNull(params)) {
            NameValuePair[] data = new NameValuePair[params.size()];
            int i = 0;

            Entry entry;
            for(Iterator i$ = params.entrySet().iterator(); i$.hasNext(); data[i++] = new NameValuePair((String)entry.getKey(), (String)entry.getValue())) {
                entry = (Entry)i$.next();
            }

            method.setRequestBody(data);
        }

        try {
            client.executeMethod(method);
            if (method.getStatusCode() == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), "UTF-8"));
                String str = null;

                while((str = reader.readLine()) != null) {
                    result.append(str);
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
