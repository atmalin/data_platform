package cn.iecas.springboot.framework.util;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequestUtil {
    /**
     * 向指定URL发送HTTP请求
     */
    public static String request(String strUrl, String method, Object object, String bearer) {
        BufferedReader reader = null;
        String res = "";
        HttpURLConnection connection = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            URL url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(360000);
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.setRequestProperty("Accept", "application/json;charset=utf-8");
            connection.setRequestMethod(method);

            if (bearer == null) {
                connection.setRequestProperty("Authorization", "Bearer mF_9.B5f-4.1JqM");
            } else if (bearer.contains(" ")) {
                connection.setRequestProperty("Authorization", bearer);
            } else {
                connection.setRequestProperty("Authorization", "Bearer " + bearer);
            }


            if (object != null) {
                outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
                outputStreamWriter.append(JSONObject.fromObject(object).toString());
                outputStreamWriter.flush();
            }

            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            res = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(res);
        return res;
    }


    /**
     * 向指定URL发送HTTP PUT
     */
    public static String put(String strUrl, Object object, String bearer) {
        return request(strUrl, "PUT", object, bearer);
    }


    /**
     * 向指定URL发送HTTP POST请求
     */
    public static String post(String strUrl, Object object, String bearer) {
        return request(strUrl, "POST", object, bearer);
    }

    /**
     * 向指定URL发送HTTP DELETE请求
     */
    public static String delete(String strUrl, Object object, String bearer) {
        return request(strUrl, "DELETE", object, bearer);
    }


    /**
     * 向指定URL发送HTTP GET请求
     */
    public static String get(String strUrl, String bearer) {
        return request(strUrl, "GET", null, bearer);
    }

    /**
     * 向指定URL发送HTTP GET请求
     *
     * @param strUrl
     * @return
     */
    public static String get(String strUrl) {
        BufferedReader reader = null;
        String res = "";
        try {
            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            res = connection.getResponseMessage();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            res = stringBuilder.toString();
            reader.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(res);
        return res;
    }


    public static String postFormUrlEncoded(String path, NameValuePair[] data, String bearer) throws IOException {
        PostMethod postMethod = new PostMethod(path);
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        if (bearer == null) {
            postMethod.setRequestHeader("Authorization", "Bearer mF_9.B5f-4.1JqM");
        } else {
            postMethod.setRequestHeader("Authorization", bearer);
        }
        postMethod.setRequestBody(data);
        HttpClient httpClient = new HttpClient();
        httpClient.executeMethod(postMethod);
        String result = postMethod.getResponseBodyAsStream().toString();
        return result;
    }
}
