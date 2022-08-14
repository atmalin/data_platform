package cn.iecas.springboot.framework.util;

import net.sf.json.util.JSONUtils;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class UrlUtil {
    public static boolean testUrl(String urlString){
        if (urlString.startsWith("http")) {
            return testHttpUrl(urlString);
        } else {
            return testHttpsUrl(urlString, null, null);
        }
    }

    public static boolean testUrlWithTimeOut(String urlString,int timeOutMillSeconds){
        URL url;
        try {
            url = new URL(urlString);
            URLConnection co =  url.openConnection();
            co.setConnectTimeout(timeOutMillSeconds);
            co.connect();
            return true;
        } catch (Exception e1) {
            return false;
        }
    }

    public static boolean testHttpUrl(String urlString) {
        URL url;
        try {
            url = new URL(urlString);
            InputStream in = url.openStream();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean testHttpsUrl(String url, Map<String, String> body, Map<String, String> header) {

        CloseableHttpResponse response = null;
        // 处理请求路径
        url = UriComponentsBuilder.fromHttpUrl(url)
                .toUriString();
        //创建httpclient对象
        CloseableHttpClient client = null;
        String respBody;
        try {
            client = HttpClients.custom()
                    .setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom()
                            //忽略掉对服务器端证书的校验
                            .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                            .build(), NoopHostnameVerifier.INSTANCE))
                    .build();
            //创建post方式请求对象
            HttpPost httpPost = new HttpPost(url);
            // 请求头设置
            httpPost.setHeader("Content-Type", "application/json");
            if (header != null) {
                for(String s:header.keySet()){
                    httpPost.setHeader(s,header.get(s));
                }
            }
            // 情求体设置
            if (body != null) {
                httpPost.setEntity(new StringEntity(JSONUtils.valueToString(body), "utf-8"));
            }
            //执行请求操作，并拿到结果
            response = client.execute(httpPost);
            //获取结果实体
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (client != null) {
                    client.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
