package org.lilacseeking.video.videoapp.Utils;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;

public class HttpClientPoolManager {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientPoolManager.class);

    public static PoolingHttpClientConnectionManager clientConnectionManager = null;

    public static SSLConnectionSocketFactory sslsf = null;

    private static int defaultMaxTotal = 200;

    private static int defaultMaxPerRoute = 25;

    public synchronized static void getInstance(int maxTotal, int maxPerRoute) {
        try {
            //信任所有ssl
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (xcs, string) -> true).build();
            sslsf = new SSLConnectionSocketFactory(sslContext);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", sslsf)
                    .build();
            clientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            if (maxTotal > 0) {
                clientConnectionManager.setMaxTotal(maxTotal);
            } else {
                clientConnectionManager.setMaxTotal(defaultMaxTotal);
            }
            if (maxPerRoute > 0) {
                clientConnectionManager.setDefaultMaxPerRoute(maxPerRoute);
            } else {
                clientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("初始化HttpClientConnectionManager失败：", ex);
        }
    }

    public static CloseableHttpClient getHttpClient(int maxTotal, int maxPerRoute) {
        if (clientConnectionManager == null) {
            getInstance(maxTotal, maxPerRoute);
        }
        return HttpClients.custom().setConnectionManager(clientConnectionManager).setConnectionManagerShared(true).build();
    }

    public static CloseableHttpClient getHttpsClient(int maxTotal, int maxPerRoute) {
        if (clientConnectionManager == null) {
            getInstance(maxTotal, maxPerRoute);
        }
        return HttpClients.custom().setSSLSocketFactory(sslsf).setConnectionManager(clientConnectionManager).setConnectionManagerShared(true).build();
    }
}
