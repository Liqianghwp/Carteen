/**
 * Licensed Property to China UnionPay Co., Ltd.
 * <p>
 * (C) Copyright of China UnionPay Co., Ltd. 2010
 * All Rights Reserved.
 * <p>
 * <p>
 * Modification History:
 * =============================================================================
 * Author         Date          Description
 * ------------ ---------- ---------------------------------------------------
 * xshu       2014-05-28       MPI基本参数工具类
 * =============================================================================
 */
package com.ruoyi.pay.service.impl.union.sdk.gm;

import com.ruoyi.pay.service.impl.union.sdk.SDKConfig;
import com.ruoyi.pay.service.impl.union.sdk.SDKConstants;
import com.ruoyi.pay.service.impl.union.sdk.SDKUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * 声明：以下代码只是为了方便接入方测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障
 */
@Slf4j
public class GmSDKConfig extends SDKConfig {


    /** 签名证书路径. */
    private String signCertPath;
    /** 签名证书密码. */
    private String signCertPwd;
    /** 签名证书类型. */
    private String signCertType;
    /** 加密公钥证书路径. */
    private String encryptCertPath;
    /** 加密公钥证书路径. */
    private String pinEncryptCertPath;
    /** 验证签名公钥证书目录. */
    private String validateCertDir;
    /** 按照商户代码读取指定签名证书目录. */
    private String signCertDir;
    /** 中级证书路径  */
    private String middleCertPath;
    /** 根证书路径  */
    private String rootCertPath;
    /** 是否验证验签证书CN，除了false都验  */
    private boolean ifValidateCNName = true;

    /** 配置文件中签名证书路径常量. */
    public static final String SDK_SIGNCERT_PATH = "acpsdk.sm2.signCert.path";
    /** 配置文件中签名证书密码常量. */
    public static final String SDK_SIGNCERT_PWD = "acpsdk.sm2.signCert.pwd";
    /** 配置文件中签名证书类型常量. */
    public static final String SDK_SIGNCERT_TYPE = "acpsdk.sm2.signCert.type";
    /** 配置文件中密码加密证书路径常量. */
    public static final String SDK_ENCRYPTCERT_PATH = "acpsdk.sm2.encryptCert.path";
    /** 配置文件中6.0.0统一支付产品加密pin证书路径常量. */
    public static final String SDK_PINENCRYPTCERT_PATH = "acpsdk.sm2.pinEncryptCert.path";
    /** 配置文件中磁道加密证书路径常量. */
    public static final String SDK_ENCRYPTTRACKCERT_PATH = "acpsdk.sm2.encryptTrackCert.path";
    /** 配置文件中磁道加密公钥模数常量. */
    public static final String SDK_ENCRYPTTRACKKEY_MODULUS = "acpsdk.sm2.encryptTrackKey.modulus";
    /** 配置文件中磁道加密公钥指数常量. */
    public static final String SDK_ENCRYPTTRACKKEY_EXPONENT = "acpsdk.sm2.encryptTrackKey.exponent";
    /** 配置文件中验证签名证书目录常量. */
    public static final String SDK_VALIDATECERT_DIR = "acpsdk.sm2.validateCert.dir";
    /** 配置文件中安全密钥 */
    public static final String SDK_SECURITYKEY = "acpsdk.sm2.secureKey";
    /** 配置文件中根证书路径常量  */
    public static final String SDK_ROOTCERT_PATH = "acpsdk.sm2.rootCert.path";
    /** 配置文件中根证书路径常量  */
    public static final String SDK_MIDDLECERT_PATH = "acpsdk.sm2.middleCert.path";
    /** 配置是否需要验证验签证书CN，除了false之外的值都当true处理 */
    public static final String SDK_IF_VALIDATE_CN_NAME = "acpsdk.ifValidateCNName";


    /** 操作对象. */
    private static GmSDKConfig config = null;
    /** 属性文件对象. */
    protected Properties properties;

    private GmSDKConfig() {
        super();
    }

    /**
     * 获取config对象.
     * @return
     */
    public static SDKConfig getConfig() {
        if (config == null) {
            log.warn("未主动调用loadPropertiesFromSrc或loadPropertiesFromPath方法，默认调loadPropertiesFromSrc初始化");
            GmSDKConfig.loadPropertiesFromSrc();
        }
        if (config == null) {
            log.error("初始化失败");
            config = new GmSDKConfig();
        }
        return config;
    }

    protected void loadProperties1(Properties pro) {

        if (pro == null) {
            log.error("loadProperties input is null");
            return;
        }

        if (this.properties == null) {
            this.properties = pro;
        }

        log.info("开始从属性文件中加载配置项");
        String value = null;

        value = pro.getProperty(SDK_SIGNCERT_PATH);
        if (!SDKUtil.isEmpty(value)) {
            this.signCertPath = value.trim();
            log.info("配置项：私钥签名证书路径==>" + SDK_SIGNCERT_PATH + "==>" + value + " 已加载");
        }
        value = pro.getProperty(SDK_SIGNCERT_PWD);
        if (!SDKUtil.isEmpty(value)) {
            this.signCertPwd = value.trim();
            log.info("配置项：私钥签名证书密码==>" + SDK_SIGNCERT_PWD + " 已加载");
        }
        value = pro.getProperty(SDK_SIGNCERT_TYPE);
        if (!SDKUtil.isEmpty(value)) {
            this.signCertType = value.trim();
            log.info("配置项：私钥签名证书类型==>" + SDK_SIGNCERT_TYPE + "==>" + value + " 已加载");
        }
        value = pro.getProperty(SDK_ENCRYPTCERT_PATH);
        if (!SDKUtil.isEmpty(value)) {
            this.encryptCertPath = value.trim();
            log.info("配置项：敏感信息加密证书==>" + SDK_ENCRYPTCERT_PATH + "==>" + value + " 已加载");
        }
        value = pro.getProperty(SDK_PINENCRYPTCERT_PATH);
        if (!SDKUtil.isEmpty(value)) {
            this.pinEncryptCertPath = value.trim();
            log.info("配置项：6.0统一支付产品pin加密证书==>" + SDK_PINENCRYPTCERT_PATH + "==>" + value + " 已加载");
        }
        value = pro.getProperty(SDK_VALIDATECERT_DIR);
        if (!SDKUtil.isEmpty(value)) {
            this.validateCertDir = value.trim();
            log.info("配置项：验证签名证书路径(这里配置的是目录，不要指定到公钥文件)==>" + SDK_VALIDATECERT_DIR + "==>" + value + " 已加载");
        }
        value = pro.getProperty(SDK_ROOTCERT_PATH);
        if (!SDKUtil.isEmpty(value)) {
            this.rootCertPath = value.trim();
        }
        value = pro.getProperty(SDK_MIDDLECERT_PATH);
        if (!SDKUtil.isEmpty(value)) {
            this.middleCertPath = value.trim();
        }

        value = pro.getProperty(SDK_IF_VALIDATE_CN_NAME);
        if (!SDKUtil.isEmpty(value)) {
            if (SDKConstants.FALSE_STRING.equals(value.trim()))
                this.ifValidateCNName = false;
        }

        super.loadProperties1(pro);
    }

    /**
     * 根据传入的 {@link Properties}对象设置配置参数
     *
     * @param pro
     */
    public static void loadProperties(Properties pro) {
        config = new GmSDKConfig();
        config.loadProperties1(pro);
    }

    /**
     * 从classpath路径下加载配置参数
     */
    public static void loadPropertiesFromSrc() {
        Properties properties = getPropertiesFromSrc();
        loadProperties(properties);
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

    public String getSignCertDir() {
        return signCertDir;
    }

    public String getMiddleCertPath() {
        return middleCertPath;
    }

    public String getRootCertPath() {
        return rootCertPath;
    }

    public boolean isIfValidateCNName() {
        return ifValidateCNName;
    }

    public String getPinEncryptCertPath() {
        return pinEncryptCertPath;
    }

}
