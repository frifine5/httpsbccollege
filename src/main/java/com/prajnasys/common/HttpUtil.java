package com.prajnasys.common;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpUtil {
    
    private HttpUtil() {}

    private static final SSLSocketFactory sslSocketFactory = initSSLSocketFactory();
    private static final TrustAnyHostnameVerifier trustAnyHostnameVerifier = new HttpUtil().new TrustAnyHostnameVerifier();

    private static SSLSocketFactory initSSLSocketFactory() {
        try {
            TrustManager[] tm = {new HttpUtil().new TrustAnyTrustManager() };
            SSLContext sslContext = SSLContext.getInstance("TLS");	// ("TLS", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String authGet(String url, String username, String password) {
        return authGet(url, null, null, username, password);
    }
    
    public static String authGet(String url, Map<String, String> queryParas, String username, String password) {
        return authGet(url, queryParas, null, username, password);
    }
    
    public static String authGet(String url, Map<String, String> queryParas, Map<String, String> headers, String username, String password) {
        setAuthenticator(username, password);
        return get(url, queryParas, headers);
    }
    
    public static String get(String url, Map<String, String> queryParas) {
        return get(url, queryParas, null);
    }

    public static String get(String url) {
        return get(url, null, null);
    }

    public static String get(String url, Map<String, String> queryParas, Map<String, String> headers) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), "GET", headers);
            conn.connect();
            return readResponseString(conn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static String authPost(String url, String data, Map<String, String> headers, String username, String password) {
        return authPost(url, null, data, headers, username, password);
    }
    
    public static String authPost(String url, Map<String, String> queryParas, String data, Map<String, String> headers, String username, String password) {
        setAuthenticator(username, password);
        return post(url, queryParas, data, headers);
    }
    
    public static String post(String url, String data, Map<String, String> headers) {
        return post(url, null, data, headers);
    }

    public static String post(String url, Map<String, String> queryParas, String data, Map<String, String> headers) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), "POST", headers);
            conn.connect();
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes("utf-8"));
            out.flush();
            out.close();
            return readResponseString(conn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
    
    public static String uploadFile(String url, String filePath){
        return uploadFile(url, filePath, null);
    }
    
    public static String uploadFile(String url, String filePath, Map<String, String> queryParas){
        return uploadFile(url, filePath, queryParas, null);
    }
    
    public static String uploadFile(String url, String filePath, Map<String, String> queryParas, Map<String, String> headers){
        HttpURLConnection conn = null;
        try {
            if(headers == null){
                headers = new HashMap<String, String>();
            }
            final String newLine = "\r\n";
            final String boundaryPrefix = "--";
            String BOUNDARY = "========7d4a6d158c9";
            // 设置文件传输的header
            headers.put("connection", "Keep-Alive");
            headers.put("Charsert", "UTF-8");
            headers.put("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), "POST", headers);
            conn.setUseCaches(false);
            conn.connect();
            
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            File file = new File(filePath);
            StringBuilder sb = new StringBuilder();
            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);
            sb.append("Content-Disposition: form-data;name=\"uploadFile\";filename=\"").append(file.getName()).append("\"" + newLine);
            sb.append("Content-Type: application/octet-stream");
            sb.append(newLine).append(newLine);
            // 写入参数
            out.write(sb.toString().getBytes());
            // 写入文件
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            int size = 0;
            while((size = in.read(buffer)) != -1){
                out.write(buffer, 0, size);
            }
//            out.write(newLine.getBytes());
            in.close();
            
            //定义最后数据分割线
            byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine).getBytes();
            out.write(end_data);
            out.flush();
            out.close();
            return readResponseString(conn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
    
    /**
     * 从URL下载文件
     * @param url url地址
     * @param savePath 下载后的文件保存位置
     * @return 
     */
    public static File downloadFile(String url, String savePath){
        return downloadFile(url, savePath, null, null);
    }
    
    public static File downloadFile(String url, String savePath, Map<String, String> queryParas){
        return downloadFile(url, savePath, queryParas, null);
    }
    
    public static File downloadFile(String url, String savePath, Map<String, String> queryParas, Map<String, String> headers){
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), "POST", headers);
            //防止屏蔽程序抓取而返回403错误  
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.connect();
            
            File outFile = new File(savePath);
            if(!outFile.getParentFile().exists()){
                outFile.getParentFile().mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(outFile);
            
            InputStream in = conn.getInputStream();
            int size = 0;
            byte[] buffer = new byte[1024];
            while((size = in.read(buffer)) != -1){
                fos.write(buffer, 0, size);
            }
            fos.close();
            in.close();
            return outFile;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
    
    private static void setAuthenticator(final String username, final String password){
        Authenticator.setDefault(new Authenticator() { 
            @Override
            protected PasswordAuthentication getPasswordAuthentication() { 
              return new PasswordAuthentication(username, password.toCharArray()); 

          } 
        });
    }

    private static HttpURLConnection getHttpConnection(String url, String method, Map<String, String> headers) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        URL _url = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection) conn).setSSLSocketFactory(sslSocketFactory);
            ((HttpsURLConnection) conn).setHostnameVerifier(trustAnyHostnameVerifier);
        }
        conn.setRequestMethod(method);
        conn.setDoOutput(true);
        conn.setDoInput(true);

        conn.setConnectTimeout(20000);
        conn.setReadTimeout(60000);

        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        return conn;
    }
    
    public static String buildQueryString(Map<String, String> queryParas) {
        if (queryParas == null || queryParas.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (Map.Entry<String, String> entry : queryParas.entrySet()) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append("&");
            }

            String key = entry.getKey();
            String value = entry.getValue();
            if (!StringUtil.isNull(value)) {
                try {
                    value = URLEncoder.encode(value, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            sb.append(key).append("=").append(value);
        }
        return sb.toString();
    }

    private static String buildUrlWithQueryString(String url, Map<String, String> queryParas) {
        if (queryParas == null || queryParas.isEmpty()) {
            return url;
        }

        StringBuilder sb = new StringBuilder(url);
        boolean isFirst;
        if (url.indexOf("?") == -1) {
            isFirst = true;
            sb.append("?");
        } else {
            isFirst = false;
        }

        for (Map.Entry<String, String> entry : queryParas.entrySet()) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append("&");
            }

            String key = entry.getKey();
            String value = entry.getValue();
            if (!StringUtil.isNull(value)) {
                try {
                    value = URLEncoder.encode(value, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            sb.append(key).append("=").append(value);
        }
        return sb.toString();
    }

    private static String readResponseString(HttpURLConnection conn) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }
    
     /**
     * https 域名校验
     */
    private class TrustAnyHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * https 证书管理
     */
    private class TrustAnyTrustManager implements X509TrustManager {

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }
}
