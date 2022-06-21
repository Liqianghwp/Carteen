package com.ruoyi.pay.service.impl.union.sdk;


import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * http：
 * 直接用get和post方法。
 * 
 * 单向https用默认信任库验证证书：
 * 直接用get和post方法。
 * 
 * 单向https不验证证书：
 * 用get和post方法时地址前面加个u，比如uhttps://101.231.204.80:5000/xxxx。
 * 
 * 双向https：
 * 先调addSslConf加客户端证书和信任库。
 * 用get和post方法时地址前面加个自己写的tag替换掉https，比如cloudpos://101.231.204.80:5000/xxxx。
 */
@Slf4j
public class HttpsUtil {
	
//	static {//商户如果通过代理访问需要的改动点
//		System.setProperty("proxyType", "4");
//		System.setProperty("proxyPort", "8080");
//		System.setProperty("proxyHost", "172.16.1.245");
//		System.setProperty("proxySet", "true");
//	}


    public static byte[] send(String urlStr, byte[] data, Map<String, String> reqHeader, String requestMethod){

        String tag = null;
        if (!urlStr.startsWith("https://") && !urlStr.startsWith("http://")) {
        	int idx = urlStr.indexOf("://");
        	if(idx <= 0) {
                log.error("errurl [" + urlStr + "]");
        	}
            tag = urlStr.substring(0, idx); //取tag
            urlStr = "https" + urlStr.substring(idx); //地址转回https
		}
        
        if(data != null)
            log.debug(requestMethod + " to [" + urlStr + "]: " + new String(data));
        else
            log.debug(requestMethod + " to [" + urlStr + "]");

        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(urlStr);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(15000);// 连接超时时间
            httpURLConnection.setReadTimeout(90000);// 读取结果超时时间
            httpURLConnection.setDoInput(true); // 可读
            httpURLConnection.setDoOutput(true); // 可写
            httpURLConnection.setUseCaches(false);// 取消缓存

            for(Entry<String, String> kv : reqHeader.entrySet()){
            	httpURLConnection.setRequestProperty(kv.getKey(), kv.getValue());
            }
            
            httpURLConnection.setRequestMethod(requestMethod);

            if (tag != null) {
                HttpsURLConnection husn = (HttpsURLConnection) httpURLConnection;
                if(ssLSocketFactoryMap.containsKey(tag)) {
                	husn.setSSLSocketFactory(ssLSocketFactoryMap.get(tag));
                }
                if(ifVerifyHostnameMap.get(tag) == false) {
                    husn.setHostnameVerifier(trustAllHostnameVerifier);
                }
            }
            httpURLConnection.connect();

            if(data != null) {
                httpURLConnection.getOutputStream().write(data);
                httpURLConnection.getOutputStream().flush();
            }
            int httpCode = httpURLConnection.getResponseCode();
            String encoding = httpURLConnection.getContentEncoding();
            if(encoding == null) encoding = "UTF-8";

            byte[] respBody;
            if(httpCode != HttpURLConnection.HTTP_OK){
                respBody = read(httpURLConnection.getErrorStream());
                log.error("HTTP RESP[" + httpCode + "]: " + new String(respBody, encoding));
            } else {
                respBody = read(httpURLConnection.getInputStream());
                log.debug("HTTP RESP[" + httpCode + "]: " + new String(respBody, encoding));
            }
            return respBody;
        } catch (Exception e) {
            log.error("[" + urlStr + "] " + e.getMessage() + " <- " + e.getClass().getName(), e);
        } finally {
            if(httpURLConnection != null)
                httpURLConnection.disconnect();
        }
        return null;
    }

    public static byte[] post(String url, byte[] data, Map<String, String> reqHeader){
        return send(url, data, reqHeader, "POST");
    }

    public static byte[] post(String url, byte[] data){
        return post(url, data, new TreeMap<String, String>(){
			private static final long serialVersionUID = -2761292859751696874L;
			{
				put("Content-Type", "application/x-www-form-urlencoded");
			}
		});
    }

    public static byte[] get(String url, Map<String, String> reqHeader){
        return send(url, null, reqHeader, "GET");
    }

    public static byte[] get(String url){
        return send(url, null, new TreeMap<String, String>(){
			private static final long serialVersionUID = -7225134807859941048L;
			{
	            put("Content-Type", "application/x-www-form-urlencoded");
	        }
		}, "GET");
    }

    private static byte[] read(InputStream in) throws IOException {
        byte[] buf = new byte[1024];
        int length = 0;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        while ((length = in.read(buf, 0, buf.length)) > 0) {
            bout.write(buf, 0, length);
        }
        bout.flush();
        return bout.toByteArray();
    }

	private static Map<String, SSLSocketFactory> ssLSocketFactoryMap = new TreeMap<String, SSLSocketFactory>();
    private static Map<String, Boolean> ifVerifyHostnameMap = new TreeMap<String, Boolean>();

    private static final X509TrustManager trustAllX509TrustManager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain,
                String authType) throws CertificateException {
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain,
                String authType) throws CertificateException {
        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };

    private static final HostnameVerifier trustAllHostnameVerifier = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

	/**
	 * 
	 * @param tag
	 * @param keyStoreJksPath 客户端证书库jks。设空时效果等同为单向https。
	 * @param keyStoreJksPwd
	 * @param trustStoreJksPath 信任库jks。设空时效果等同都信任。
	 * @param trustStoreJksPwd
	 * @param verifyHostname 是否需要验证hostname。
	 * @return
	 */
	public static boolean addSslConf(String tag,
			String keyStoreJksPath, String keyStoreJksPwd,
			String trustStoreJksPath, String trustStoreJksPwd,
                                     boolean verifyHostname) {

        if(ssLSocketFactoryMap.containsKey(tag)) {
            log.warn("addSslConf err: [" + tag + "] has been added");
            return false;
        }

		try {
            KeyManagerFactory kmf = null;
            if(keyStoreJksPath != null) {
		        KeyStore keyStore = loadKeyStore(keyStoreJksPath, keyStoreJksPwd, "JKS");
		        if(keyStore == null) {
		        	return false;
		        }
                kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		        kmf.init(keyStore, keyStoreJksPwd.toCharArray());
		    }

            TrustManagerFactory tmf = null;
			if(trustStoreJksPath != null) {
		        KeyStore trustStore = loadKeyStore(trustStoreJksPath, trustStoreJksPwd, "JKS");
		        if(trustStore == null) {
		        	return false;
		        }
                tmf = TrustManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		        tmf.init(trustStore);
	        }

            SSLContext sslContext = SSLContext.getInstance("TLS");
            KeyManager[] keyManagers = kmf == null ? null : kmf.getKeyManagers();
            TrustManager[] trustManagers = tmf == null ? new TrustManager[]{trustAllX509TrustManager} : tmf.getTrustManagers(); //不知道为啥双向不验服务器证书的时候TrustManager必须设置，但单向的直接null也可以不验。
            sslContext.init(keyManagers, trustManagers, SecureRandom.getInstance("SHA1PRNG"));
            ssLSocketFactoryMap.put(tag, sslContext.getSocketFactory());
            ifVerifyHostnameMap.put(tag, verifyHostname);
            log.info("addSslConf succeed: [" + tag + "]" + (tmf == null ? "，但没验服务器证书哦" : "") + "。");
	        return true;
		} catch (Exception e) {
            log.error("addSslConf fail. ", e);
        	return false;
		}
	}

    static {
        addSslConf("uhttps", null, null, null, null, false);
    }

	private static KeyStore loadKeyStore(String path, String pwd, String type) { 
		FileInputStream fis = null;
		try {
			KeyStore ks = KeyStore.getInstance(type);
			fis = new FileInputStream(path);
			char[] nPassword = null;
			nPassword = null == pwd || "".equals(pwd.trim()) ? null : pwd.toCharArray();
			if (null != ks) {
				ks.load(fis, nPassword);
			}
			return ks;
		} catch (Exception e) {
            log.error("loadKeyStore Error", e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
//					e.printStackTrace();
				}
			}
		}
		return null;
	}


    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException {
    	HttpsUtil.addSslConf("cloudpos", "d:/certs/cloudpos/01003320-------001-----.jks", "000000", "d:/certs/cloudpos/trust.jks", "000000", false);
    	for(int i=0;i<10000;i++){
    		post("uhttps://gateway.test.95516.com/gateway/api/appTransReq.do", "accessType=0&bizType=000000&txnSubType=00&signature=ff3e9d82a5528ad51c5b3bde5fd2d62a8e016a68d28abb584140ad2d2baa2c2a&orderId=2019112215532700000154&reqReserved=%7Btestcase%3DTestCase_90_DF%7D&txnTime=20191122155327&txnType=00&merId=777290058110018&encoding=UTF-8&version=5.1.0&signMethod=12".getBytes());
    		post("cloudpos://180.169.111.145:10009/CloudPosPayment/InsTransServlet", "{\"TrxIndCd\":\"SPK00461694051982042817\",\"TrxTp\":\"SPK004\",\"Version\":\"100\",\"FirmTermId\":\"52079000\",\"CerVer\":\"02\",\"FirmMchntId\":\"001332060120003\",\"EncryptData\":\"e8r/n/VkA1nQ+eOVRL5iHBwvgw0q/KYEyeARox+dtPFAKFsXmY064x6UU/pTBX3OLcA9PjEy3ATUmnxMwCb0GTRyn4Qh3OvotOh2YytbNt4g9gii5movpX04+eKCcbU2eIH5D4Bx+TTOv0t94LvMgf4FMN5djZ5XOjfnx9mpajrMKQEKBdwp97hOTv49QZJqW/AzMaagfErxBKqEeC+yG1pyvg23Xg+Scq/qheJ/w2XvCLKFoeZjKWN5bevpVkHS7KZdAqCx3T4wHEmZd0wo5YF3WdHwBT53LHZ6l6m0Ja6HpTxyaGccGULgcyhxxttWyqzoVKQCFU3ZlfX3b5Yxdg==\"}".getBytes());
    	}
        addSslConf("test", null, null, null, null, true);
    	post("test://gateway.test.95516.com/gateway/api/appTransReq.do", "accessType=0&bizType=000000&txnSubType=00&signature=ff3e9d82a5528ad51c5b3bde5fd2d62a8e016a68d28abb584140ad2d2baa2c2a&orderId=2019112215532700000154&reqReserved=%7Btestcase%3DTestCase_90_DF%7D&txnTime=20191122155327&txnType=00&merId=777290058110018&encoding=UTF-8&version=5.1.0&signMethod=12".getBytes());
    }
			
}















