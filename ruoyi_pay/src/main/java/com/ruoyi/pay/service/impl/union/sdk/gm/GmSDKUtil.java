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
 *   xshu       2014-05-28      MPI工具类
 * =============================================================================
 */
package com.ruoyi.pay.service.impl.union.sdk.gm;

import com.ruoyi.pay.service.impl.union.sdk.SDKConstants;
import com.ruoyi.pay.service.impl.union.sdk.SDKUtil;
import com.ruoyi.pay.service.impl.union.sdk.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.security.PublicKey;
import java.util.Map;

import static com.ruoyi.pay.service.impl.union.sdk.SDKConstants.CERTTYPE_01;
import static com.ruoyi.pay.service.impl.union.sdk.SDKConstants.CERTTYPE_02;

/**
 * 声明：以下代码只是为了方便接入方测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障
 */
@Slf4j
public class GmSDKUtil {


	/**
	 * 全渠道5.0、二维码国密算法用。
	 * 1. 按ascii排序。【注意不是字母顺序】
	 * 2. 对1的结果sm3得到byte数组。
	 * 3. 对2的结果用16进制小写字符串表示。【注意必须是小写】
	 * 4. 对3的结果取byte数组。【注意不是16进制字符串转byte数组，而是当普通字符串转】
	 * 5. 对4的结果用私钥算签名，算法为sm3withsm2。
	 * 6. 对5的结果做base64，得到一个字符串就是签名。
	 * @param data
	 * @param certPath
	 * @param certPwd
	 * @param encoding
	 * @return
	 */
	public static String signSm2(Map<String, String> data, String certPath, String certPwd, String encoding) {
        try{
            String stringData = SDKUtil.createLinkString(data, true, false, encoding);
			log.info("打印排序后待签名请求报文串（交易返回11验证签名失败时可以用来同正确的进行比对）:[" + stringData + "]");
            byte[] sm3 = SecureUtil.sm3(stringData.getBytes(encoding));
            String sm3Hex = SDKUtil.byteArrayToHexString(sm3).toLowerCase();
			log.info("sm3结果（交易返回11验证签名失败可以用来同正确的进行比对）:[" + sm3Hex + "]");
            return Base64.encodeBase64String(GmUtil.signSm3WithSm2(sm3Hex.getBytes(encoding), 
            		GmCertUtil.getCertIdByKeyStoreMap(certPath, certPwd).getBytes(encoding),
            		GmCertUtil.getSignCertPrivateKey(certPath, certPwd)));
        } catch (Exception e) {
			log.error("calcSignRsa Error", e);
            return null;
        }
    }

    /**
     * 全渠道5.0接口、二维码国密验签用。
     * @param resData
     * @param encoding
     * @return
     */
	public static boolean verifySm2(Map<String, String> resData, PublicKey pubKey, String encoding, String certId) {
        try{
            String stringSign = resData.remove(SDKConstants.param_signature);
        	if(SDKUtil.isEmpty(stringSign)) {
				log.error("signature is null. verifyRsa fail.");
                return false;
        	}
			log.info("签名原文：[" + stringSign + "]");
            String stringData = SDKUtil.createLinkString(resData, true, false, encoding);
			log.info("待验签排序串：[" + stringData + "]");
            byte[] sm3 = SecureUtil.sm3(stringData.getBytes(encoding));
            String sm3Hex = SDKUtil.byteArrayToHexString(sm3).toLowerCase();
			log.info("sm3结果:[" + sm3Hex + "]");
            return GmUtil.verifySm3WithSm2(sm3Hex.getBytes(encoding), 
            		certId.getBytes(encoding), 
            		Base64.decodeBase64(stringSign), 
            		pubKey);
        } catch (Exception e) {
			log.error("verifyRsa fail." + e.getMessage(), e);
            return false;
        }
    }

    /**
     *
     * 获取应答报文中的加密公钥证书,并存储到本地,并备份原始证书<br>
     * 更新成功则返回1，无更新返回0，失败异常返回-1。
     *
     * @param strCert
     * @param certType
     * @return
     */
	public static int updateEncryptCert(String strCert, String certType ) {
		if (SDKUtil.isEmpty(strCert) || SDKUtil.isEmpty(certType))
			return -1;
		if (CERTTYPE_01.equals(certType)) {
			// 更新敏感信息加密公钥
            return GmCertUtil.resetEncryptCertPublicKey(strCert);
		} else if (CERTTYPE_02.equals(certType)) {
			// 更新pin敏感信息加密公钥
			return GmCertUtil.resetPinEncryptCertPublicKey(strCert);
		} else {
			log.info("unknown cerType:"+certType);
			return -1;
		}
	}
}
