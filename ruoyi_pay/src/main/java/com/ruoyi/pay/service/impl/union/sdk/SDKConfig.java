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
 *   xshu       2014-05-28       MPI基本参数工具类
 * =============================================================================
 */
package com.ruoyi.pay.service.impl.union.sdk;


import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Properties;

/**
 * 
 * @ClassName SDKConfig
 * @Description acpsdk配置文件acp_sdk.properties配置信息类
 * @date 2016-7-22 下午4:04:55
 * 声明：以下代码只是为了方便接入方测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障
 */
@Slf4j
public class SDKConfig {


	public static final String FILE_NAME = "acp_sdk.properties";
	/** 前台请求URL. */
	private String frontRequestUrl;
	/** 后台请求URL. */
	private String backRequestUrl;
	/** 二维码统一下单请求URL. */
	private String orderRequestUrl;
	/** 单笔查询 */
	private String singleQueryUrl;
	/** 批量查询 */
	private String batchQueryUrl;
	/** 批量交易 */
	private String batchTransUrl;
	/** 文件传输 */
	private String fileTransUrl;
	/** 签名证书路径. */
	private String signCertPath;
	/** 签名证书密码. */
	private String signCertPwd;
	/** 签名证书类型. */
	private String signCertType;
	/** 加密公钥证书路径. */
	private String encryptCertPath;
	/** 验证签名公钥证书目录. */
	private String validateCertDir;
	/** 按照商户代码读取指定签名证书目录. */
	private String signCertDir;
//	/** 磁道加密证书路径. */
//	private String encryptTrackCertPath;
	/** 磁道加密公钥模数. */
	private String encryptTrackKeyModulus;
	/** 磁道加密公钥指数. */
	private String encryptTrackKeyExponent;
	/** 6.0.0统一支付产品加密pin公钥证书路径. */
	private String pinEncryptCertPath;
	/** 有卡交易. */
	private String cardRequestUrl;
	/** app交易 */
	private String appRequestUrl;
	/** 证书使用模式(单证书/多证书) */
	private String singleMode;
	/** 安全密钥(SHA256和SM3计算时使用) */
	private String secureKey;
	/** 中级证书路径  */
	private String middleCertPath;
	/** 根证书路径  */
	private String rootCertPath;
	/** 是否验证验签证书CN，除了false都验  */
	private boolean ifValidateCNName = true;
	/** 是否验证https证书，默认都不验  */
	private boolean ifValidateRemoteCert = false;
	/** signMethod，没配按01吧  */
	private String signMethod = "01";
	/** version，没配按5.0.0  */
	private String version = "5.0.0";
	/** frontUrl  */
	private String frontUrl;
	/** backUrl  */
	private String backUrl;
	/** frontFailUrl  */
	private String frontFailUrl;
	
	/*缴费相关地址*/
	private String jfFrontRequestUrl;
	private String jfBackRequestUrl;
	private String jfSingleQueryUrl;
	private String jfCardRequestUrl;
	private String jfAppRequestUrl;
	
	//二维码
	private String qrcBackTransUrl;
	private String qrcB2cIssBackTransUrl;
	private String qrcB2cMerBackTransUrl;
	private String qrcB2cMerBackSynTransUrl;
	
	//综合认证
	private String zhrzFrontRequestUrl;
	private String zhrzBackRequestUrl;
	private String zhrzSingleQueryUrl;
	private String zhrzCardRequestUrl;
	private String zhrzAppRequestUrl;
	private String zhrzFaceRequestUrl;

	/** acp6 */
	private String transUrl;
	
	
	/** 配置文件中的前台URL常量. */
	public static final String SDK_FRONT_URL = "acpsdk.frontTransUrl";
	/** 配置文件中的后台URL常量. */
	public static final String SDK_BACK_URL = "acpsdk.backTransUrl";
	/** 配置文件中的统一下单URL常量. */
	public static final String SDK_ORDER_URL = "acpsdk.orderTransUrl";
	/** 配置文件中的单笔交易查询URL常量. */
	public static final String SDK_SIGNQ_URL = "acpsdk.singleQueryUrl";
	/** 配置文件中的批量交易查询URL常量. */
	public static final String SDK_BATQ_URL = "acpsdk.batchQueryUrl";
	/** 配置文件中的批量交易URL常量. */
	public static final String SDK_BATTRANS_URL = "acpsdk.batchTransUrl";
	/** 配置文件中的文件类交易URL常量. */
	public static final String SDK_FILETRANS_URL = "acpsdk.fileTransUrl";
	/** 配置文件中的有卡交易URL常量. */
	public static final String SDK_CARD_URL = "acpsdk.cardTransUrl";
	/** 配置文件中的app交易URL常量. */
	public static final String SDK_APP_URL = "acpsdk.appTransUrl";

	/** 以下缴费产品使用，其余产品用不到，无视即可 */
	// 前台请求地址
	public static final String JF_SDK_FRONT_TRANS_URL= "acpsdk.jfFrontTransUrl";
	// 后台请求地址
	public static final String JF_SDK_BACK_TRANS_URL="acpsdk.jfBackTransUrl";
	// 单笔查询请求地址
	public static final String JF_SDK_SINGLE_QUERY_URL="acpsdk.jfSingleQueryUrl";
	// 有卡交易地址
	public static final String JF_SDK_CARD_TRANS_URL="acpsdk.jfCardTransUrl";
	// App交易地址
	public static final String JF_SDK_APP_TRANS_URL="acpsdk.jfAppTransUrl";
	// 人到人
	public static final String QRC_BACK_TRANS_URL="acpsdk.qrcBackTransUrl";
	// 人到人
	public static final String QRC_B2C_ISS_BACK_TRANS_URL="acpsdk.qrcB2cIssBackTransUrl";
	// 人到人
	public static final String QRC_B2C_MER_BACK_TRANS_URL="acpsdk.qrcB2cMerBackTransUrl";
	
	public static final String QRC_B2C_MER_BACK_SYN_TRANS_URL="acpsdk.qrcB2cMerBackSynTransUrl";
	
	/** 以下综合认证产品使用，其余产品用不到，无视即可 */
	// 前台请求地址
	public static final String ZHRZ_SDK_FRONT_TRANS_URL= "acpsdk.zhrzFrontTransUrl";
	// 后台请求地址
	public static final String ZHRZ_SDK_BACK_TRANS_URL="acpsdk.zhrzBackTransUrl";
	// 单笔查询请求地址
	public static final String ZHRZ_SDK_SINGLE_QUERY_URL="acpsdk.zhrzSingleQueryUrl";
	// 有卡交易地址
	public static final String ZHRZ_SDK_CARD_TRANS_URL="acpsdk.zhrzCardTransUrl";
	// App交易地址
	public static final String ZHRZ_SDK_APP_TRANS_URL="acpsdk.zhrzAppTransUrl";
	// 图片识别交易地址
	public static final String ZHRZ_SDK_FACE_TRANS_URL="acpsdk.zhrzFaceTransUrl";

	// acp6
	public static final String TRANS_URL="acpsdk.transUrl";

	/** 配置文件中签名证书路径常量. */
	public static final String SDK_SIGNCERT_PATH = "acpsdk.signCert.path";
	/** 配置文件中签名证书密码常量. */
	public static final String SDK_SIGNCERT_PWD = "acpsdk.signCert.pwd";
	/** 配置文件中签名证书类型常量. */
	public static final String SDK_SIGNCERT_TYPE = "acpsdk.signCert.type";
	/** 配置文件中加密证书路径常量. */
	public static final String SDK_ENCRYPTCERT_PATH = "acpsdk.encryptCert.path";
//	/** 配置文件中磁道加密证书路径常量. */
//	public static final String SDK_ENCRYPTTRACKCERT_PATH = "acpsdk.encryptTrackCert.path";
	/** 配置文件中5.0.0有卡产品磁道加密公钥模数常量. */
	public static final String SDK_ENCRYPTTRACKKEY_MODULUS = "acpsdk.encryptTrackKey.modulus";
	/** 配置文件中5.0.0有卡产品磁道加密公钥指数常量. */
	public static final String SDK_ENCRYPTTRACKKEY_EXPONENT = "acpsdk.encryptTrackKey.exponent";
	/** 配置文件中验证签名证书目录常量. */
	public static final String SDK_VALIDATECERT_DIR = "acpsdk.validateCert.dir";
	/** 配置文件中6.0.0统一支付产品加密pin证书路径常量. */
	public static final String SDK_PINENCRYPTCERT_PATH = "acpsdk.pinEncryptCert.path";

	/** 配置文件中是否加密cvn2常量. */
	public static final String SDK_CVN_ENC = "acpsdk.cvn2.enc";
	/** 配置文件中是否加密cvn2有效期常量. */
	public static final String SDK_DATE_ENC = "acpsdk.date.enc";
	/** 配置文件中是否加密卡号常量. */
	public static final String SDK_PAN_ENC = "acpsdk.pan.enc";
	/** 配置文件中证书使用模式 */
	public static final String SDK_SINGLEMODE = "acpsdk.singleMode";
	/** 配置文件中安全密钥 */
	public static final String SDK_SECURITYKEY = "acpsdk.secureKey";
	/** 配置文件中根证书路径常量  */
	public static final String SDK_ROOTCERT_PATH = "acpsdk.rootCert.path";
	/** 配置文件中根证书路径常量  */
	public static final String SDK_MIDDLECERT_PATH = "acpsdk.middleCert.path";
	/** 配置是否需要验证验签证书CN，除了false之外的值都当true处理 */
	public static final String SDK_IF_VALIDATE_CN_NAME = "acpsdk.ifValidateCNName";
	/** 配置是否需要验证https证书，除了true之外的值都当false处理 */
	public static final String SDK_IF_VALIDATE_REMOTE_CERT = "acpsdk.ifValidateRemoteCert";
	/** signmethod */
	public static final String SDK_SIGN_METHOD ="acpsdk.signMethod";
	/** version */
	public static final String SDK_VERSION = "acpsdk.version";
	/** 后台通知地址  */
	public static final String SDK_BACKURL = "acpsdk.backUrl";
	/** 前台通知地址  */
	public static final String SDK_FRONTURL = "acpsdk.frontUrl";
	/** 前台失败通知地址  */
	public static final String SDK_FRONT_FAIL_URL = "acpsdk.frontFailUrl";
	
	/** 操作对象. */
	private static SDKConfig config = null;
	/** 属性文件对象. */
	protected Properties properties;

	protected SDKConfig() {
		super();
	}

	/**
	 * 获取config对象.
	 * @return
	 */
	public static SDKConfig getConfig() {
		if(config == null) {
			log.warn("未主动调用loadPropertiesFromSrc或loadPropertiesFromPath方法，默认调loadPropertiesFromSrc初始化");
			SDKConfig.loadPropertiesFromSrc();
		}
		if(config == null) {
			log.error("初始化失败");
			config = new SDKConfig();
		}
		return config;
	}

	/**
	 * 从properties文件加载
	 * 
	 * @param rootPath
	 *            不包含文件名的目录.
	 */
	public static void loadPropertiesFromPath(String rootPath) {
		Properties properties = getPropertiesFromPath(rootPath);
		loadProperties(properties);
	}
	
	protected static Properties getPropertiesFromSrc() {
		InputStream in = null;
		try {
			log.info("从classpath: " +SDKConfig.class.getClassLoader().getResource("").getPath()+" 获取属性文件"+FILE_NAME);
			in = SDKConfig.class.getClassLoader().getResourceAsStream(FILE_NAME);
			if (in == null) {
				log.error(FILE_NAME + "属性文件未能在classpath指定的目录下 "+SDKConfig.class.getClassLoader().getResource("").getPath()+" 找到!");
				return null;
			}
			Properties properties = new Properties();
			properties.load(in);
			return properties;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}
	
	protected static Properties getPropertiesFromPath(String rootPath) {
		if (rootPath == null || "".equals(rootPath.trim())) {
			log.error("rootPath为空");
			return null;
		}
		String path = rootPath + File.separator + FILE_NAME;
		log.info("从路径读取配置文件: " + path);
		File file = new File(path);
		InputStream in = null;
		if (!file.exists()) {
			log.error(rootPath + "不存在" + FILE_NAME);
			return null;
		}
		try {
			in = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(in);
			return properties;
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	/**
	 * 从classpath路径下加载配置参数
	 */
	public static void loadPropertiesFromSrc() {
		Properties properties = getPropertiesFromSrc();
		loadProperties(properties);
	}

	/**
	 * 根据传入的 {@link Properties}对象设置配置参数
	 *
	 * @param pro
	 */
	public static void loadProperties(Properties pro) {
		config = new SDKConfig();
		config.loadProperties1(pro);
	}
	
	/**
	 * 根据传入的 {@link Properties}对象设置配置参数
	 *
	 * @param pro
	 */
	protected void loadProperties1(Properties pro) {

		if(pro == null) {
			log.error("loadProperties input is null");
			return;
		}
		if(this.properties == null) {
			this.properties = pro;
        }
		log.info("开始从属性文件中加载配置项");
		String value = null;

		value = pro.getProperty(SDK_SIGNCERT_PATH);
		if (!SDKUtil.isEmpty(value)) {
			this.signCertPath = value.trim();
			log.info("配置项：私钥签名证书路径==>"+SDK_SIGNCERT_PATH +"==>"+ value+" 已加载");
		}
		value = pro.getProperty(SDK_SIGNCERT_PWD);
		if (!SDKUtil.isEmpty(value)) {
			this.signCertPwd = value.trim();
			log.info("配置项：私钥签名证书密码==>"+SDK_SIGNCERT_PWD +" 已加载");
		}
		value = pro.getProperty(SDK_SIGNCERT_TYPE);
		if (!SDKUtil.isEmpty(value)) {
			this.signCertType = value.trim();
			log.info("配置项：私钥签名证书类型==>"+SDK_SIGNCERT_TYPE +"==>"+ value+" 已加载");
		}
		value = pro.getProperty(SDK_ENCRYPTCERT_PATH);
		if (!SDKUtil.isEmpty(value)) {
			this.encryptCertPath = value.trim();
			log.info("配置项：敏感信息加密证书==>"+SDK_ENCRYPTCERT_PATH +"==>"+ value+" 已加载");
		}
		value = pro.getProperty(SDK_VALIDATECERT_DIR);
		if (!SDKUtil.isEmpty(value)) {
			this.validateCertDir = value.trim();
			log.info("配置项：验证签名证书路径(这里配置的是目录，不要指定到公钥文件)==>"+SDK_VALIDATECERT_DIR +"==>"+ value+" 已加载");
		}
		value = pro.getProperty(SDK_PINENCRYPTCERT_PATH);
		if (!SDKUtil.isEmpty(value)) {
			this.pinEncryptCertPath = value.trim();
			log.info("配置项：6.0.0统一支付产品加密pin证书路径(这里配置的是目录，不要指定到公钥文件)==>"+SDK_PINENCRYPTCERT_PATH +"==>"+ value+" 已加载");
		}
		value = pro.getProperty(SDK_FRONT_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.frontRequestUrl = value.trim();
		}
		value = pro.getProperty(SDK_BACK_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.backRequestUrl = value.trim();
		}
		value = pro.getProperty(SDK_ORDER_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.orderRequestUrl = value.trim();
		}
		value = pro.getProperty(SDK_BATQ_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.batchQueryUrl = value.trim();
		}
		value = pro.getProperty(SDK_BATTRANS_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.batchTransUrl = value.trim();
		}
		value = pro.getProperty(SDK_FILETRANS_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.fileTransUrl = value.trim();
		}
		value = pro.getProperty(SDK_SIGNQ_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.singleQueryUrl = value.trim();
		}
		value = pro.getProperty(SDK_CARD_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.cardRequestUrl = value.trim();
		}
		value = pro.getProperty(SDK_APP_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.appRequestUrl = value.trim();
		}
//		value = pro.getProperty(SDK_ENCRYPTTRACKCERT_PATH);
//		if (!SDKUtil.isEmpty(value)) {
//			this.encryptTrackCertPath = value.trim();
//		}

		value = pro.getProperty(SDK_SECURITYKEY);
		if (!SDKUtil.isEmpty(value)) {
			this.secureKey = value.trim();
		}
		value = pro.getProperty(SDK_ROOTCERT_PATH);
		if (!SDKUtil.isEmpty(value)) {
			this.rootCertPath = value.trim();
		}
		value = pro.getProperty(SDK_MIDDLECERT_PATH);
		if (!SDKUtil.isEmpty(value)) {
			this.middleCertPath = value.trim();
		}

		/**缴费部分**/
		value = pro.getProperty(JF_SDK_FRONT_TRANS_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.jfFrontRequestUrl = value.trim();
		}

		value = pro.getProperty(JF_SDK_BACK_TRANS_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.jfBackRequestUrl = value.trim();
		}
		
		value = pro.getProperty(JF_SDK_SINGLE_QUERY_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.jfSingleQueryUrl = value.trim();
		}
		
		value = pro.getProperty(JF_SDK_CARD_TRANS_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.jfCardRequestUrl = value.trim();
		}
		
		value = pro.getProperty(JF_SDK_APP_TRANS_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.jfAppRequestUrl = value.trim();
		}
		
		value = pro.getProperty(QRC_BACK_TRANS_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.qrcBackTransUrl = value.trim();
		}
		
		value = pro.getProperty(QRC_B2C_ISS_BACK_TRANS_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.qrcB2cIssBackTransUrl = value.trim();
		}
		
		value = pro.getProperty(QRC_B2C_MER_BACK_TRANS_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.qrcB2cMerBackTransUrl = value.trim();
		}
		
		value = pro.getProperty(QRC_B2C_MER_BACK_SYN_TRANS_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.qrcB2cMerBackSynTransUrl = value.trim();
		}
		
		/**综合认证**/
		value = pro.getProperty(ZHRZ_SDK_FRONT_TRANS_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.zhrzFrontRequestUrl = value.trim();
		}

		value = pro.getProperty(ZHRZ_SDK_BACK_TRANS_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.zhrzBackRequestUrl = value.trim();
		}
		
		value = pro.getProperty(ZHRZ_SDK_SINGLE_QUERY_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.zhrzSingleQueryUrl = value.trim();
		}
		
		value = pro.getProperty(ZHRZ_SDK_CARD_TRANS_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.zhrzCardRequestUrl = value.trim();
		}
		
		value = pro.getProperty(ZHRZ_SDK_APP_TRANS_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.zhrzAppRequestUrl = value.trim();
		}
		
		value = pro.getProperty(ZHRZ_SDK_FACE_TRANS_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.zhrzFaceRequestUrl = value.trim();
		}

		value = pro.getProperty(TRANS_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.transUrl = value.trim();
		}

		value = pro.getProperty(SDK_ENCRYPTTRACKKEY_EXPONENT);
		if (!SDKUtil.isEmpty(value)) {
			this.encryptTrackKeyExponent = value.trim();
		}

		value = pro.getProperty(SDK_ENCRYPTTRACKKEY_MODULUS);
		if (!SDKUtil.isEmpty(value)) {
			this.encryptTrackKeyModulus = value.trim();
		}
		
		value = pro.getProperty(SDK_IF_VALIDATE_CN_NAME);
		if (!SDKUtil.isEmpty(value)) {
			if( SDKConstants.FALSE_STRING.equals(value.trim()))
					this.ifValidateCNName = false;
		}
		
		value = pro.getProperty(SDK_IF_VALIDATE_REMOTE_CERT);
		if (!SDKUtil.isEmpty(value)) {
			if( SDKConstants.TRUE_STRING.equals(value.trim()))
					this.ifValidateRemoteCert = true;
		}
		
		value = pro.getProperty(SDK_SIGN_METHOD);
		if (!SDKUtil.isEmpty(value)) {
			this.signMethod = value.trim();
		}
		
		value = pro.getProperty(SDK_SIGN_METHOD);
		if (!SDKUtil.isEmpty(value)) {
			this.signMethod = value.trim();
		}
		value = pro.getProperty(SDK_VERSION);
		if (!SDKUtil.isEmpty(value)) {
			this.version = value.trim();
		}
		value = pro.getProperty(SDK_FRONTURL);
		if (!SDKUtil.isEmpty(value)) {
			this.frontUrl = value.trim();
		}
		value = pro.getProperty(SDK_BACKURL);
		if (!SDKUtil.isEmpty(value)) {
			this.backUrl = value.trim();
		}
		value = pro.getProperty(SDK_FRONT_FAIL_URL);
		if (!SDKUtil.isEmpty(value)) {
			this.frontFailUrl = value.trim();
		}
	}

	public String getFrontRequestUrl() {
		return frontRequestUrl;
	}

	public String getBackRequestUrl() {
		return backRequestUrl;
	}

	public String getOrderRequestUrl() {
		return orderRequestUrl;
	}

	public String getSignCertPath() {
		return signCertPath;
	}

	public String getSignCertPwd() {
		return signCertPwd;
	}

	public String getSignCertType() {
		return signCertType;
	}

	public String getEncryptCertPath() {
		return encryptCertPath;
	}

	public String getValidateCertDir() {
		return validateCertDir;
	}

	public String getSingleQueryUrl() {
		return singleQueryUrl;
	}

	public String getBatchQueryUrl() {
		return batchQueryUrl;
	}

	public String getBatchTransUrl() {
		return batchTransUrl;
	}

	public String getFileTransUrl() {
		return fileTransUrl;
	}

	public String getSignCertDir() {
		return signCertDir;
	}
	
	public Properties getProperties() {
		return properties;
	}

	public String getCardRequestUrl() {
		return cardRequestUrl;
	}

	public String getAppRequestUrl() {
		return appRequestUrl;
	}

//	public String getEncryptTrackCertPath() {
//		return encryptTrackCertPath;
//	}
	
	public String getPinEncryptCertPath() {
		return pinEncryptCertPath;
	}
	
	public String getJfFrontRequestUrl() {
		return jfFrontRequestUrl;
	}

	public String getJfBackRequestUrl() {
		return jfBackRequestUrl;
	}

	public String getJfSingleQueryUrl() {
		return jfSingleQueryUrl;
	}

	public String getJfCardRequestUrl() {
		return jfCardRequestUrl;
	}
	
	public String getJfAppRequestUrl() {
		return jfAppRequestUrl;
	}

	public String getSingleMode() {
		return singleMode;
	}
	
	public String getEncryptTrackKeyExponent() {
		return encryptTrackKeyExponent;
	}

	public String getEncryptTrackKeyModulus() {
		return encryptTrackKeyModulus;
	}
	
	public String getSecureKey() {
		return secureKey;
	}
	
	public String getMiddleCertPath() {
		return middleCertPath;
	}
	
	public boolean isIfValidateCNName() {
		return ifValidateCNName;
	}

	public boolean isIfValidateRemoteCert() {
		return ifValidateRemoteCert;
	}

	public String getSignMethod() {
		return signMethod;
	}
	
	public String getQrcBackTransUrl() {
		return qrcBackTransUrl;
	}

	public String getQrcB2cIssBackTransUrl() {
		return qrcB2cIssBackTransUrl;
	}

	public String getQrcB2cMerBackTransUrl() {
		return qrcB2cMerBackTransUrl;
	}

	public String getQrcB2cMerBackSynTransUrl() {
		return qrcB2cMerBackSynTransUrl;
	}
	
	public String getZhrzFrontRequestUrl() {
		return zhrzFrontRequestUrl;
	}

	public String getZhrzBackRequestUrl() {
		return zhrzBackRequestUrl;
	}

	public String getZhrzSingleQueryUrl() {
		return zhrzSingleQueryUrl;
	}

	public String getZhrzCardRequestUrl() {
		return zhrzCardRequestUrl;
	}

	public String getZhrzAppRequestUrl() {
		return zhrzAppRequestUrl;
	}

	public String getZhrzFaceRequestUrl() {
		return zhrzFaceRequestUrl;
	}

	public String getVersion() {
		return version;
	}

	public String getFrontUrl() {
		return frontUrl;
	}
	
	public String getBackUrl() {
		return backUrl;
	}

	public String getFrontFailUrl() {
		return frontFailUrl;
	}

	public String getRootCertPath() {
		return rootCertPath;
	}
	
    public String getTransUrl() {
        return transUrl;
    }
}
