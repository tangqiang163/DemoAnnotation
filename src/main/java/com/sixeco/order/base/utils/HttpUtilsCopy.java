package com.sixeco.order.base.utils;

import ch.qos.logback.classic.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

@SuppressWarnings("deprecation")
public class HttpUtilsCopy {
    static Logger log = (Logger) LoggerFactory.getLogger(HttpUtils.class);

    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 10000; // 链接超时判断
    private static final int MAX_LOAD_TIMEOUT = 12000; // 处理超时时间
    private static final int MAX_PROCESS_TIMEOUT = 120000; // 最大处理操作时间

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_LOAD_TIMEOUT);
        // 设置从客户端到服务器获取响应的时间
        configBuilder.setSocketTimeout(MAX_PROCESS_TIMEOUT);
        requestConfig = configBuilder.build();
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param apiUrl
     * @param json
     *            json对象
     * @return
     */
    public static String doPostCopy(String apiUrl, Object json) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        Long startTime = System.currentTimeMillis();
        try {
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json.toString(), Charset.forName("UTF-8"));// 解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
            httpPost.addHeader("Accept","application/json");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            log.info("远程接口调用地址:[{}]调用时间:[{}]",apiUrl,System.currentTimeMillis() - startTime);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                httpStr = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (IOException e) {
            log.info("远程接口调用地址:[{}]调用时间:[{}]",apiUrl,System.currentTimeMillis() - startTime);
            e.printStackTrace();
        } finally {
            closeByPost(response, httpPost, httpClient);
        }
        return httpStr;
    }
    /**
     * 正常关闭post请求
     *
     * @author tanxin
     * @param response
     * @param httpPost
     * @param httpClient
     */
    private static void closeByPost(CloseableHttpResponse response, HttpPost httpPost, CloseableHttpClient httpClient) {
        if (response != null) {
            try {
                EntityUtils.consume(response.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (httpPost != null) {
            try {
                httpPost.abort();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
