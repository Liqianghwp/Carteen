/**
 *
 * Licensed Property to China UnionPay Co., Ltd.
 * 
 * (C) Copyright of China UnionPay Co., Ltd. 2010
 *     All Rights Reserved.
 *
 * 
 * Modification History:
 * =============================================================================
 *   Author         Date          Description
 *   ------------ ---------- ---------------------------------------------------
 *   xshu       2014-05-28       证书工具类.
 * =============================================================================
 */
package com.ruoyi.pay.service.impl.union.sdk;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.*;
import java.security.spec.RSAPublicKeySpec;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.ruoyi.pay.service.impl.union.sdk.SDKConstants.*;
import static com.ruoyi.pay.service.impl.union.sdk.SDKUtil.isEmpty;

/**
 * @ClassName: CertUtil
 * @Description: acpsdk证书工具类，主要用于对证书的加载和使用
 * @date 2016-7-22 下午2:46:20
 * 声明：以下代码只是为了方便接入方测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障
 */
@Slf4j
public class CertUtil {


	/** 验签中级证书 */
	private static X509Certificate middleCert = null;
	/** 验签根证书 */
	private static X509Certificate rootCert = null;
    /** 5.0磁道加密公钥 */
    private static PublicKey encryptTrackKey = null;
	/** 签名私钥map：证书路径，私钥 */
	private final static Map<String, Cert> signCerts = new ConcurrentHashMap<String, Cert>();
    /** 5.0接口验签证书Map：certId，公钥 */
    private static Map<String, PublicKey> verifyCerts = new ConcurrentHashMap<String, PublicKey>();
    /** 加密证书  */
    private static Cert encryptCert = null;
    /** 5.1接口验签证书Map：证书完整字符串，公钥 */
    private static Map<String, PublicKey> verifyCerts510 = new ConcurrentHashMap<String, PublicKey>();
    /** 6.0统一支付加密pin用，其他接口请勿使用 */
    private static Cert pinEncryptCert = null;

    protected static class Cert {
    	protected String certId;
    	protected PublicKey pubKey;
    	protected PrivateKey priKey;
    }

	static {
        addProvider();//向系统添加BC provider
		init();
	}
	
	/**
	 * 初始化所有证书.
	 */
	public static void init() {
		try {
			initSignCert();//初始化签名私钥证书
			initMiddleCert();//初始化验签证书的中级证书
			initRootCert();//初始化验签证书的根证书
			initEncryptCert();//初始化加密公钥
            initPinEncryptCert();//初始化pin加密公钥
			initTrackKey();//构建磁道加密公钥
			initValidateCertFromDir();//初始化所有的验签证书
		} catch (Exception e) {
            log.error("init失败。", e);
		}
	}
	
	/**
	 * 添加签名，验签，加密算法提供者
	 */
	private static void addProvider(){
		if (Security.getProvider("BC") == null) {
            log.debug("add BC provider");
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} else {
			Security.removeProvider("BC"); //解决eclipse调试时tomcat自动重新加载时，BC存在不明原因异常的问题。
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            log.debug("re-add BC provider");
		}
		printSysInfo();
	}

    /**
     *
     * @param path
     * @param pwd
     * @return
     */
    private static Cert addSignCert(String path, String pwd) {

        if (isEmpty(path) || isEmpty(pwd)) {
            log.warn("签名证书路径或证书密码为空。 停止加载签名私钥证书。");
            return null;
        }

        final String type = "PKCS12"; //实际BC只支持PKCS12，不支持JKS，就不去管JKS了……

        log.info("加载签名私钥证书==>" + path);
        FileInputStream fis = null;
        try {
            KeyStore ks = KeyStore.getInstance(type, "BC");
            log.debug("Load RSA CertPath=[" + path + "],Pwd=["+ pwd + "]");
            fis = new FileInputStream(path);
            char[] nPassword = null;
            nPassword = null == pwd || "".equals(pwd.trim()) ? null: pwd.toCharArray();
            if (null != ks) {
                ks.load(fis, nPassword);
            }
            Enumeration<String> aliasenum = null;
            aliasenum = ks.aliases();
            String keyAlias = null;
            if (aliasenum.hasMoreElements()) {
                keyAlias = aliasenum.nextElement();
            }
            X509Certificate cert = (X509Certificate) ks.getCertificate(keyAlias);

            Cert c = new Cert();
            c.certId = cert.getSerialNumber().toString(10);
            c.priKey = (PrivateKey) ks.getKey(keyAlias, pwd.toCharArray());
            c.pubKey = cert.getPublicKey();
            signCerts.put(path, c);
            log.info("addSignCert Successful. CertId=[" + c.certId + "]");
            return c;
        } catch (Exception e) {
            log.error("addSignCert Error", e);
        } finally {
            if(null!=fis)
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }


    /**
     * 指定路径读个x509证书
     * @param path
     * @return
     */
    public static X509Certificate readX509Cert(String path) {
        X509Certificate cert = null;
        CertificateFactory cf = null;
        FileInputStream in = null;
        try {
            cf = CertificateFactory.getInstance("X.509", "BC");
            in = new FileInputStream(path);
            cert = (X509Certificate) cf.generateCertificate(in);
        } catch (FileNotFoundException e) {
            log.error("readX509Cert Error File Not Found: " + path, e);
        } catch (Exception e) {
            log.error("readX509Cert Error", e);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return cert;
    }
	/**
	 * 用配置文件acp_sdk.properties中配置的私钥路径和密码 加载签名证书，会清空重新加载，一般仅第一次加载时调用即可
	 */
	private static void initSignCert() {
        signCerts.clear();
        String path = SDKConfig.getConfig().getSignCertPath();
        String pwd = SDKConfig.getConfig().getSignCertPwd();
        if(isEmpty(path) || isEmpty(pwd)) {
            log.warn(SDKConfig.SDK_SIGNCERT_PATH + " or " + SDKConfig.SDK_SIGNCERT_PWD + " is empty");
            return;
        }
        Cert cert = addSignCert(path, pwd);
        log.info("读取配置文件默认签名证书==>" + path + (cert != null ?"成功":"失败"));
	}

	/**
	 * 用配置文件acp_sdk.properties配置路径 加载5.1验签证书中级证书
	 */
	private static void initMiddleCert() {
        String path = SDKConfig.getConfig().getMiddleCertPath();
        if(isEmpty(path)){
            log.warn(SDKConfig.SDK_MIDDLECERT_PATH + " is empty");
            return;
        }
        middleCert = readX509Cert(path);
        log.info("加载中级证书==>" + path + (middleCert != null ?"成功":"失败"));
	}

	/**
     * 用配置文件acp_sdk.properties配置路径 加载5.1验签证书根证书
	 */
	private static void initRootCert() {
        String path = SDKConfig.getConfig().getRootCertPath();
        if(isEmpty(path)){
            log.warn(SDKConfig.SDK_ROOTCERT_PATH + " is empty");
            return;
        }
        rootCert = readX509Cert(path);
        log.info("加载根证书==>" + path + (rootCert != null ?"成功":"失败"));
	}
	
	/**
	 * 用配置文件acp_sdk.properties配置路径 加载磁道公钥
	 */
	private static void initTrackKey() {

        String modulus = SDKConfig.getConfig().getEncryptTrackKeyModulus();
        String exponent = SDKConfig.getConfig().getEncryptTrackKeyExponent();
        if(isEmpty(modulus) || isEmpty(exponent)){
            log.warn(SDKConfig.SDK_ENCRYPTTRACKKEY_MODULUS + " or " + SDKConfig.SDK_ENCRYPTTRACKKEY_EXPONENT + " is empty");
            return;
        }
        encryptTrackKey = getPublicKey(modulus, exponent);
        log.info("加载5.0磁道公钥==>" + (encryptTrackKey != null ?"成功":"失败"));
	}

	/**
	 * 用配置文件acp_sdk.properties配置路径 加载验签证书
	 */
	private static void initValidateCertFromDir() {

		verifyCerts.clear();

		String dir = SDKConfig.getConfig().getValidateCertDir();
        if (isEmpty(dir)) {
            log.error("WARN: acpsdk.validateCert.dir is empty");
            return;
        }

        log.info("加载验证签名证书目录==>" + dir +" 注：如果请求报文中version=5.1.0那么此验签证书目录使用不到，可以不需要设置（version=5.0.0必须设置）。");
		File fileDir = new File(dir);
		File[] files = fileDir.listFiles(new CerFilter());
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			try {
                X509Certificate verifyCert = readX509Cert(file.getAbsolutePath());
				if(verifyCert == null) {
					continue;
				}
                String certId = verifyCert.getSerialNumber().toString(10);
				verifyCerts.put(certId, verifyCert.getPublicKey());
                log.info("[" + file.getAbsolutePath() + "][CertId=" + certId + "]");
			} catch (Exception e) {
                log.error("Load verify cert error, " + file.getAbsolutePath(), e);
			}
		}
        log.info("LoadVerifyCert Finish");
	}

    /**
     * 用配置文件acp_sdk.properties配置路径 加载敏感信息加密证书
     */
    private static void initEncryptCert() {
        String path = SDKConfig.getConfig().getEncryptCertPath();
        if(isEmpty(path)){
            log.warn(SDKConfig.SDK_ENCRYPTCERT_PATH + " is empty");
            return;
        }
        X509Certificate encryptCert = readX509Cert(path);
        log.info("加载敏感信息加密证书==>" + path + (encryptCert != null ?"成功":"失败"));
        if(encryptCert != null) {
            Cert c = new Cert();
            c.certId = encryptCert.getSerialNumber().toString(10);
            c.pubKey = encryptCert.getPublicKey();
            CertUtil.encryptCert = c;
        }
    }

    /**
     * 用配置文件acp_sdk.properties配置路径 加载6.0统一支付产品pin加密证书
     */
    private static void initPinEncryptCert() {
        String path = SDKConfig.getConfig().getPinEncryptCertPath();
        if(isEmpty(path)){
            log.warn(SDKConfig.SDK_PINENCRYPTCERT_PATH + " is empty");
            return;
        }
        X509Certificate encryptCert = readX509Cert(path);
        log.info("加载6.0统一支付产品pin加密证书==>" + path + (encryptCert != null ?"成功":"失败"));
        if(encryptCert != null) {
            Cert c = new Cert();
            c.certId = encryptCert.getSerialNumber().toString(10);
            c.pubKey = encryptCert.getPublicKey();
            CertUtil.pinEncryptCert = c;
        }
    }
    
    /**
     *
     */
	private static Cert getSignCert() {
        String path = SDKConfig.getConfig().getSignCertPath();
        String pwd = SDKConfig.getConfig().getSignCertPwd();
        if(isEmpty(path) || isEmpty(pwd)) {
            log.error("未配置默认签名证书时无法调用此方法。");
            return null;
        }
        return getSignCert(path, pwd);
	}

    /**
     *
     * @param path
     * @param pwd
     * @return
     */
    private static Cert getSignCert(String path, String pwd) {
        if(isEmpty(path) || isEmpty(pwd)) {
            log.error("传入的签名路径或密码为空。");
            return null;
        }
        if(!signCerts.containsKey(path)){
            addSignCert(path, pwd);
        }
        Cert c = signCerts.get(path);
        if(c == null) {
            log.error("未成功获取签名证书。");
            return null;
        }
        return c;
	}

	/**
	 * 获取敏感信息加密证书PublicKey
	 * 
	 * @return
	 */
    protected static Cert getEncryptCert() {
    	if(CertUtil.encryptCert == null) {
            initEncryptCert();
    	}
    	return CertUtil.encryptCert;
	}

	/**
	 * 获取敏感信息加密证书PublicKey
	 * 
	 * @return
	 */
    protected static Cert getPinEncryptCert() {
    	if(CertUtil.pinEncryptCert == null) {
            initPinEncryptCert();
    	}
    	return CertUtil.pinEncryptCert;
	}

	/**
	 * 重置敏感信息加密证书公钥。
	 */
	public static int resetEncryptCertPublicKey(String strCert) {
        if (isEmpty(strCert)) {
            log.error("传入证书信息为空。");
            return -1;
        }

        X509Certificate x509Cert = CertUtil.genCertificateByStr(strCert);
        // 没换，不需要更新
        if (CertUtil.getEncryptCert().certId.equals(
                x509Cert.getSerialNumber().toString(10))) {
            log.info("返回证书和原证书一样，不用更新。");
            return 0;
        }

        final String localCertPath = SDKConfig.getConfig().getEncryptCertPath(); 
        if(isEmpty(localCertPath)){
            log.error("未配置加密证书路径，无法执行此方法。");
            return -1;
        }
        File f = new File(localCertPath);
        if(!f.exists()) {
            log.warn("原加密证书不存在：" + localCertPath);
        } else {
            // 将本地证书进行备份存储
            int i = localCertPath.lastIndexOf(POINT);
            String leftFileName = localCertPath.substring(0, i);
            String rightFileName = localCertPath.substring(i + 1);
            String newFileName = leftFileName + "_backup" + POINT + rightFileName;
            try {
                FileUtils.copyFile(f, new File(newFileName));
                log.info("原加密证书备份成功。");
            } catch (IOException e) {
                log.error("原加密证书备份失败，停止改证书。", e);
                return -1;
            }
        }
        // 备份成功,进行新证书的存储
        try {
            FileUtils.writeByteArrayToFile(f, strCert.getBytes(), false);
            log.info("加密证书更新成功。");
            initEncryptCert();
            return 1;
        } catch (IOException e) {
            log.error("加密证书更新失败。", e);
            return -1;
        }
    }

    /**
     * 重置pin敏感信息加密证书公钥。
     */
    public static int resetPinEncryptCertPublicKey(String strCert) {
        if (isEmpty(strCert)) {
            log.error("传入证书信息为空。");
            return -1;
        }

        X509Certificate x509Cert = CertUtil.genCertificateByStr(strCert);
        // 没换，不需要更新
        if (CertUtil.getPinEncryptCert().certId.equals(
                x509Cert.getSerialNumber().toString(10))) {
            log.info("返回证书和原证书一样，不用更新。");
            return 0;
        }

        final String localCertPath = SDKConfig.getConfig().getPinEncryptCertPath();
        if(isEmpty(localCertPath)){
            log.error("未配置加密证书路径，无法执行此方法。");
            return -1;
        }
        File f = new File(localCertPath);
        if(!f.exists()) {
            log.warn("原加密证书不存在：" + localCertPath);
        } else {
            // 将本地证书进行备份存储
            int i = localCertPath.lastIndexOf(POINT);
            String leftFileName = localCertPath.substring(0, i);
            String rightFileName = localCertPath.substring(i + 1);
            String newFileName = leftFileName + "_backup" + POINT + rightFileName;
            try {
                FileUtils.copyFile(f, new File(newFileName));
                log.info("原加密证书备份成功。");
            } catch (IOException e) {
                log.error("原加密证书备份失败，停止改证书。", e);
                return -1;
            }
        }
        // 备份成功,进行新证书的存储
        try {
            FileUtils.writeByteArrayToFile(f, strCert.getBytes(), false);
            log.info("加密证书更新成功。");
            initPinEncryptCert();
            return 1;
        } catch (IOException e) {
            log.error("加密证书更新失败。", e);
            return -1;
        }
    }
	
	/**
	 * 获取磁道加密证书PublicKey
	 * 
	 * @return
	 */
	public static PublicKey getEncryptTrackPublicKey() {
		if (null == encryptTrackKey) {
			initTrackKey();
		}
		return encryptTrackKey;
	}
	
	/**
	 * 通过certId获取验签证书Map中对应证书PublicKey
	 * 
	 * @param certId 证书物理序号
	 * @return 通过证书编号获取到的公钥
	 */
	public static PublicKey getValidatePublicKey(String certId) {
        if(certId == null) {
            log.error("没有传入certId.");
            return null;
        }
		if (!verifyCerts.containsKey(certId)) {
            initValidateCertFromDir();
		}
        PublicKey result = verifyCerts.get(certId);
        if(result == null) {
            log.error("缺少certId=[" + certId + "]对应的验签证书.");
            return null;
        }
        return result;
	}
	
	/**
	 * 获取配置文件acp_sdk.properties中配置的签名私钥证书certId
	 * 
	 * @return 证书的物理编号
	 */
	public static String getSignCertId() {
        Cert c = getSignCert();
        if(c == null) return null;
        return c.certId;
	}

    /**
     * 获取配置文件acp_sdk.properties中配置的签名私钥证书私钥
     *
     * @return 证书的物理编号
     */
    public static PrivateKey getSignCertPrivateKey() {
        Cert c = getSignCert();
        if(c == null) return null;
        return c.priKey;
    }

    /**
     *
     * @param path
     * @param pwd
     * @return
     */
    public static String getCertIdByKeyStoreMap(String path, String pwd) {
        Cert c = getSignCert(path, pwd);
        if(c == null) return null;
        return c.certId;
    }
    /**
     *
     * @param path
     * @param pwd
     * @return
     */
    public static PrivateKey getSignCertPrivateKeyByStoreMap(String path, String pwd) {
        Cert c = getSignCert(path, pwd);
        if(c == null) return null;
        return c.priKey;
    }

//	/**
//	 * 获取敏感信息加密证书的certId
//	 *
//	 * @return
//	 */
//	public static String getEncryptCertId() {
//        Cert c = getEncryptCert();
//        if(c == null) return null;
//        return c.certId;
//	}
//
//
//    public static PublicKey getEncryptCertPublicKey(){
//        Cert c = getEncryptCert();
//        if(c == null) return null;
//        return c.pubKey;
//    }
//
//	/**
//	 * 获取6.0统一支付产品pin加密证书的certId
//	 *
//	 * @return
//	 */
//	public static String getPinEncryptCertId() {
//        Cert c = getPinEncryptCert();
//        if(c == null) return null;
//        return c.certId;
//	}
//
//
//    public static PublicKey getPinEncryptCertPublicKey(){
//        Cert c = getEncryptCert();
//        if(c == null) return null;
//        return c.pubKey;
//    }
	
	/**
	 * 使用模和指数生成RSA公钥 注意：此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同
	 * 
	 * @param modulus
	 *            模
	 * @param exponent
	 *            指数
	 * @return
	 */
	private static PublicKey getPublicKey(String modulus, String exponent) {
		try {
			BigInteger b1 = new BigInteger(modulus);
			BigInteger b2 = new BigInteger(exponent);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
			return keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
            log.error("构造RSA公钥失败：" + e);
			return null;
		}
	}
	
	/**
	 * 将字符串转换为X509Certificate对象.
	 * 
	 * @param x509CertString
	 * @return
	 */
	public static X509Certificate genCertificateByStr(String x509CertString) {
		X509Certificate x509Cert = null;
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC"); 
			InputStream tIn = new ByteArrayInputStream(
					x509CertString.getBytes("ISO-8859-1"));
			x509Cert = (X509Certificate) cf.generateCertificate(tIn);
		} catch (Exception e) {
            log.error("gen certificate error", e);
		}
		return x509Cert;
	}
	
	/**
	 * 从配置文件acp_sdk.properties中获取验签公钥使用的中级证书
	 * @return
	 */
    private static X509Certificate getMiddleCert() {
        String path = SDKConfig.getConfig().getMiddleCertPath();
        if (isEmpty(path)) {
            log.error("未配置中级证书时无法调用此方法。");
            return null;
        }
        if(middleCert == null) {
            initMiddleCert();
        }
		return middleCert;
	}

    /**
     * 从配置文件acp_sdk.properties中获取验签公钥使用的根证书
     * @return
     */
    private static X509Certificate getRootCert() {
        String path = SDKConfig.getConfig().getRootCertPath();
        if (isEmpty(path)) {
            log.error("未配置根证书时无法调用此方法。");
            return null;
        }
        if(rootCert == null) {
            initRootCert();
        }
        return rootCert;
    }

	/**
	 * 获取证书的CN
	 * @param aCert
	 * @return
	 */
	public static String getIdentitiesFromCertficate(X509Certificate aCert) {
		String tDN = aCert.getSubjectDN().toString(); 
		String tPart = "";
		if ((tDN != null)) {
			String tSplitStr[] = tDN.substring(tDN.indexOf("CN=")).split("@");
			if (tSplitStr != null && tSplitStr.length > 2
					&& tSplitStr[2] != null)
				tPart = tSplitStr[2];
		}
		return tPart;
	}
	
	/**
	 * 验证书链。
	 * @param cert
	 * @return
	 */
	public static boolean verifyCertificateChain(X509Certificate cert, X509Certificate middleCert, X509Certificate rootCert){
		
		if (null == cert) {
            log.error("cert must Not null");
			return false;
		}
		
		if (null == middleCert) {
            log.error("middleCert must Not null");
			return false;
		}

		if (null == rootCert) {
            log.error("rootCert or cert must Not null");
			return false;
		}
		
		try {
		
	        X509CertSelector selector = new X509CertSelector();
	        selector.setCertificate(cert);
	        
	        Set<TrustAnchor> trustAnchors = new HashSet<TrustAnchor>();
	        trustAnchors.add(new TrustAnchor(rootCert, null));
	        PKIXBuilderParameters pkixParams = new PKIXBuilderParameters(
			        trustAnchors, selector);
	
	        Set<X509Certificate> intermediateCerts = new HashSet<X509Certificate>();
	        intermediateCerts.add(rootCert);
	        intermediateCerts.add(middleCert);
	        intermediateCerts.add(cert);
	        
	        pkixParams.setRevocationEnabled(false);
	
	        CertStore intermediateCertStore = CertStore.getInstance("Collection",
	                new CollectionCertStoreParameters(intermediateCerts), "BC");
	        pkixParams.addCertStore(intermediateCertStore);
	
	        CertPathBuilder builder = CertPathBuilder.getInstance("PKIX", "BC");
	        
        	@SuppressWarnings("unused")
			PKIXCertPathBuilderResult result = (PKIXCertPathBuilderResult) builder
                .build(pkixParams);
            log.info("verify certificate chain succeed.");
			return true;
        } catch (java.security.cert.CertPathBuilderException e){
            log.error("verify certificate chain fail.", e);
		} catch (Exception e) {
            log.error("verify certificate chain exception: ", e);
		}
		return false;
	}

    public static PublicKey verifyAndGetVerifyPubKey(String x509CertString){

        if(isEmpty(x509CertString)) {
            log.error("验签公钥证书传了空。");
            return null;
        }
        if(verifyCerts510.containsKey(x509CertString))
            return verifyCerts510.get(x509CertString);

        log.debug("验签公钥证书：["+x509CertString+"]");
        X509Certificate x509Cert = CertUtil.genCertificateByStr(x509CertString);
        if (x509Cert == null) {
            log.error("convert signPubKeyCert failed");
            return null;
        }
        // 验证证书链
        if (!CertUtil.verifyCertificate(x509Cert)) {
            log.error("验证公钥证书失败，证书信息：[" + x509CertString + "]");
            return null;
        }
        log.info("验证公钥验证成功：[" + x509Cert.getSerialNumber().toString(10) + "]");
        PublicKey publicKey = x509Cert.getPublicKey();
        verifyCerts510.put(x509CertString, publicKey);
        return publicKey;
    }

    /**
     *
     * @param cert
     * @return
     */
    private static boolean verifyCertificate(X509Certificate cert) {
		
		if ( null == cert) {
            log.error("cert must Not null");
			return false;
		}
		try {
			cert.checkValidity();//验证有效期
			if(!verifyCertificateChain(cert, CertUtil.getMiddleCert(), CertUtil.getRootCert())){
				return false;
			}
		} catch (Exception e) {
            log.error("verifyCertificate fail", e);
			return false;
		}
		
		if(SDKConfig.getConfig().isIfValidateCNName()){
			// 验证公钥是否属于银联
			if(!UNIONPAY_CNNAME.equals(CertUtil.getIdentitiesFromCertficate(cert))) {
                log.error("cer owner is not CUP:" + CertUtil.getIdentitiesFromCertficate(cert));
				return false;
			}
		} else {
			// 验证公钥是否属于银联
			if(!UNIONPAY_CNNAME.equals(CertUtil.getIdentitiesFromCertficate(cert)) 
					&& !"00040000:SIGN".equals(CertUtil.getIdentitiesFromCertficate(cert))) {
                log.error("cer owner is not CUP:" + CertUtil.getIdentitiesFromCertficate(cert));
				return false;
			}
		}
		return true;		
	}

	/**
	 * 打印系统环境信息
	 */
	private static void printSysInfo() {
        log.info("================= SYS INFO begin====================");
        log.info("os_name:" + System.getProperty("os.name"));
        log.info("os_arch:" + System.getProperty("os.arch"));
        log.info("os_version:" + System.getProperty("os.version"));
        log.info("java_vm_specification_version:"
				+ System.getProperty("java.vm.specification.version"));
        log.info("java_vm_specification_vendor:"
				+ System.getProperty("java.vm.specification.vendor"));
        log.info("java_vm_specification_name:"
				+ System.getProperty("java.vm.specification.name"));
        log.info("java_vm_version:"
				+ System.getProperty("java.vm.version"));
        log.info("java_vm_name:" + System.getProperty("java.vm.name"));
        log.info("java.version:" + System.getProperty("java.version"));
        log.info("java.vm.vendor=[" + System.getProperty("java.vm.vendor") + "]");
        log.info("java.version=[" + System.getProperty("java.version") + "]");
		printProviders();
        log.info("================= SYS INFO end=====================");
	}
	
	/**
	 * 打jre中印算法提供者列表
	 */
	private static void printProviders() {
        log.info("Providers List:");
		Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; i++) {
            log.info(i + 1 + "." + providers[i].getName());
		}
	}

	/**
	 * 证书文件过滤器
	 * 
	 */
	static class CerFilter implements FilenameFilter {
		public boolean isCer(String name) {
            return name.toLowerCase().endsWith(".cer");
		}
		public boolean accept(File dir, String name) {
			return isCer(name);
		}
	}

    public static Collection<PublicKey> getVerifySignPubKeys(){
        return verifyCerts.values();
    }
}
