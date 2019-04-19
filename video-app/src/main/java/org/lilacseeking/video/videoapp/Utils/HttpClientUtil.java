package org.lilacseeking.video.videoapp.Utils;

import com.ctrip.framework.apollo.core.utils.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HttpClientUtil {

    private static int poolManagerMaxTotal = 200;
    private static int poolManagerMaxPerRoute = 50;
    private static int socketTimeout = 5 * 000;
    private static int connectTimeout = 3 * 000;

    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(socketTimeout)
            .setConnectTimeout(connectTimeout)
            .setConnectionRequestTimeout(connectTimeout)
            .build();

    private HttpClientUtil() {
    }

    private static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClientPoolManager.getHttpClient(poolManagerMaxTotal, poolManagerMaxPerRoute);
        return httpClient;
    }

    private static CloseableHttpClient getHttpsClient() {
        CloseableHttpClient httpClient = HttpClientPoolManager.getHttpsClient(poolManagerMaxTotal, poolManagerMaxPerRoute);
        return httpClient;
    }

    private static String sendHttpPost(HttpPost httpPost, boolean isHttps) throws Exception {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String responseContent = null;
        try {
            if (isHttps) {
                httpClient = getHttpsClient();
            } else {
                httpClient = getHttpClient();
            }
            httpPost.setConfig(requestConfig);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                responseContent = EntityUtils.toString(resEntity, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    private static String sendHttpGet(HttpGet httpGet, boolean isHttps) throws Exception {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String responseContent = null;
        try {
            if (isHttps) {
                httpClient = getHttpsClient();
            } else {
                httpClient = getHttpClient();
            }
            httpClient = getHttpClient();
            httpGet.setConfig(requestConfig);
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity resEntity = response.getEntity();
                responseContent = EntityUtils.toString(resEntity, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    public static String sendHttpGet(String httpUrl) throws Exception {
        boolean isHttps = false;
        if (StringUtils.startsWithIgnoreCase(httpUrl, "https")) {
            isHttps = true;
        }
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
        return sendHttpGet(httpGet, isHttps);
    }

    public static String sendHttpPost(String httpUrl, String params) throws Exception {
        boolean isHttps = false;
        if (StringUtils.startsWithIgnoreCase(httpUrl, "https")) {
            isHttps = true;
        }
        HttpPost httpPost = new HttpPost(httpUrl);
        StringEntity stringEntity = new StringEntity(params, "UTF-8");
        stringEntity.setContentType("application/json");
        httpPost.setEntity(stringEntity);
        return sendHttpPost(httpPost, isHttps);
    }

    public static String sendHttpPost(String httpUrl, Map<String, String> params) throws Exception {
        boolean isHttps = false;
        if (StringUtils.startsWithIgnoreCase(httpUrl, "https")) {
            isHttps = true;
        }
        HttpPost httpPost = new HttpPost(httpUrl);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                String value = params.get(key);
                nameValuePairs.add(new BasicNameValuePair(key, value));
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        return sendHttpPost(httpPost, isHttps);
    }

    static class SendThread implements Runnable {
        Map<String, String> paramMap = new HashMap<>();
        String url = "https://chenjh.iask.in/springdemo/receive";
        SendThread() {
            paramMap.put("param1","aaa");
        }

        @Override
        public void run() {
            try {
                String result = HttpClientUtil.sendHttpPost(url, paramMap);
                System.out.println(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(20);
//        executorService.submit(new SendThread());
//        executorService.submit(new SendThread());
//        executorService.submit(new SendThread());
//        executorService.submit(new SendThread());
//        executorService.submit(new SendThread());
//        executorService.submit(new SendThread());
//        executorService.submit(new SendThread());
//        executorService.submit(new SendThread());
//        executorService.submit(new SendThread());
//        executorService.submit(new SendThread());
//        executorService.submit(new SendThread());
//        executorService.submit(new SendThread());
//        executorService.submit(new SendThread());
//        executorService.submit(new SendThread());
//        executorService.submit(new SendThread());
//        executorService.submit(new SendThread());
//        executorService.submit(new SendThread());
//        executorService.submit(new SendThread());
//        executorService.submit(new SendThread());
//        executorService.submit(new SendThread());
//    }

}