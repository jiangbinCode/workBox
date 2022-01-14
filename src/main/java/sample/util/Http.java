package sample.util;

import cn.hutool.core.util.StrUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import sample.config.ConfigBasic;

import javax.net.ssl.SSLContext;

import java.io.IOException;
import java.security.*;


/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-14 11:43
 **/
public class Http {

    private static CloseableHttpClient httpClient = getHttpsClient();


    public static String post(String url, String reqData) {
        url = ConfigBasic.ACTION.getReqUrl() + url;
        HttpPost httpPost = new HttpPost(url);
        if (!StrUtil.isBlank(reqData)) {
            StringEntity entity = new StringEntity(reqData, "UTF-8");
            httpPost.setEntity(entity);
        }
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        try {
            CloseableHttpResponse execute = httpClient.execute(httpPost);
            HttpEntity respData = execute.getEntity();
            String data = EntityUtils.toString(respData);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static CloseableHttpClient getHttpsClient() {
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSF);
        // 指定信任密钥存储对象和连接套接字工厂
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            // 信任任何链接
            TrustStrategy anyTrustStrategy = new TrustStrategy() {

                @Override
                public boolean isTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws java.security.cert.CertificateException {
                    // TODO Auto-generated method stub
                    return true;
                }
            };
            SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, anyTrustStrategy).build();
            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https", sslSF);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        // 设置连接管理器
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
        // 构建客户端
        return HttpClientBuilder.create().setConnectionManager(connManager).build();
    }


}
