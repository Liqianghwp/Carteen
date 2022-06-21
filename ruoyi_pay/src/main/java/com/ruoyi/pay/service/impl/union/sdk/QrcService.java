package com.ruoyi.pay.service.impl.union.sdk;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.PublicKey;
import java.util.Map;

import static com.ruoyi.pay.service.impl.union.sdk.SDKConstants.*;

/**
 * @ClassName QrcService
 * @Description sdk接口服务类，接入商户集成请可以直接参考使用本类中的方法
 * @date 2018-12-10 下午2:44:37
 * 声明：以下代码只是为了方便接入方测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障
 */
@Slf4j
public class QrcService {


	/**
	 * 请求报文签名(使用配置文件中配置的私钥证书或者对称密钥签名)<br>
	 * 功能：对请求报文进行签名,并计算赋值certid,signature字段并返回<br>
	 * @param reqData 请求报文map<br>
	 * @param encoding 上送请求报文域encoding字段的值<br>
	 * @return　签名后的map对象<br>
	 */
	public static Map<String, String> sign(Map<String, String> reqData,String encoding) {
        return signByCertInfo(reqData, SDKConfig.getConfig().getSignCertPath(), SDKConfig.getConfig().getSignCertPwd(), encoding);
	}
	
	/**
	 * 多证书签名(通过传入私钥证书路径和密码签名）<br>
	 * 功能：如果有多个商户号接入银联,每个商户号对应不同的证书可以使用此方法:传入私钥证书和密码(并且在acp_sdk.properties中 配置 acpsdk.singleMode=false)<br>
	 * @param reqData 请求报文map<br>
	 * @param certPath 签名私钥文件（带路径）<br>
	 * @param certPwd 签名私钥密码<br>
	 * @param encoding 上送请求报文域encoding字段的值<br>
	 * @return　签名后的map对象<br>
	 */
	public static Map<String, String> signByCertInfo(Map<String, String> reqData, String certPath, 
			String certPwd, String encoding) {

        Map<String, String> data = SDKUtil.filterBlank(reqData);

        if (SDKUtil.isEmpty(encoding)) {
            encoding = "UTF-8";
        }
        if (SDKUtil.isEmpty(certPath) || SDKUtil.isEmpty(certPwd)) {
			log.error("CertPath or CertPwd is empty");
            return data;
        }

        String signType = data.get(SDKConstants.param_signType);
        String version = data.get(SDKConstants.param_version);
        String reqType = data.get(SDKConstants.param_reqType);

        if (SDKUtil.isEmpty(signType)) {
        	signType = QRC_SIGNTYPE_SHA1WITHRSA;
        }

		try {
	        if (VERSION_1_0_0.equals(version)) {
	        	//被扫脱机码两个接口无视配置固定按sha256withrsa处理。下面两个ifelse别改变顺序。
	        	if (QRC_SIGNTYPE_SHA256WITHRSA.equals(signType) 
						|| "0420000903".equals(reqType)
						|| "0410000903".equals(reqType)) {
					data.put(SDKConstants.param_certId, CertUtil.getCertIdByKeyStoreMap(certPath, certPwd));
					data.put(SDKConstants.param_signature, SDKUtil.signRsa2(data, certPath, certPwd, encoding));
					return data;
				} else if (QRC_SIGNTYPE_SHA1WITHRSA.equals(signType)) {
					data.put(SDKConstants.param_certId, CertUtil.getCertIdByKeyStoreMap(certPath, certPwd));
					data.put(SDKConstants.param_signature, SDKUtil.signRsa(data, certPath, certPwd, encoding));
					return data;
				} else if (QRC_SIGNTYPE_SM3WITHSM2.equals(signType)) {
					log.error("国密算法按要求必须通过加密机签名，本sdk不提供。");
					return data;
				}
	        }
			log.error("未实现签名方法, version=" + version + ", signType=" + signType);
            return data;
        } catch (Exception e) {
			log.error("Sign Error", e);
            return data;
        }
	}

    /**
	 * 验证签名<br>
	 * @param resData 返回报文数据<br>
	 * @param encoding 上送请求报文域encoding字段的值<br>
	 * @return true 通过 false 未通过<br>
	 */
	public static boolean validate(Map<String, String> resData, String encoding) {
		log.info("验签处理开始");
        if (SDKUtil.isEmpty(encoding)) {
            encoding = "UTF-8";
        }
        String certId = resData.get(SDKConstants.param_certId);
		log.info("对返回报文串验签使用的验签公钥序列号：[" + certId + "]");
        PublicKey verifyKey = CertUtil.getValidatePublicKey(certId);
        if(verifyKey == null) {
			log.error("未找到此序列号证书。");
            return false;
        }
		String signType = resData.get(SDKConstants.param_signType);
        String version = resData.get(SDKConstants.param_version);
        String reqType = resData.get(SDKConstants.param_reqType);

        if (SDKUtil.isEmpty(signType)) {
        	signType = QRC_SIGNTYPE_SHA1WITHRSA;
        }
        
        try {
	        if (VERSION_1_0_0.equals(version)) {
	        	//被扫脱机码两个接口无视配置固定按sha256withrsa处理。下面两个ifelse别改变顺序。
	        	if (QRC_SIGNTYPE_SHA256WITHRSA.equals(signType)
						|| "0420000903".equals(reqType)
						|| "0410000903".equals(reqType)) { 
		            boolean result = SDKUtil.verifyRsa2(resData, verifyKey, encoding);
					log.info("验签" + (result? "成功":"失败") + "。");
					return result;
				} else if (QRC_SIGNTYPE_SHA1WITHRSA.equals(signType)) {
		            boolean result = SDKUtil.verifyRsa(resData, verifyKey, encoding);
					log.info("验签" + (result? "成功":"失败") + "。");
					return result;
				} else if (QRC_SIGNTYPE_SM3WITHSM2.equals(signType)) {
					log.error("国密算法按要求必须通过加密机签名，本sdk不提供。");
					return false;
				}
	        }
			log.error("未实现验签方法, version=" + version + ", signType=" + signType);
            return false;
        } catch (Exception e) {
			log.error(e.getMessage(), e);
        }
        return false;
	}

	/**
	 * 密码加密并做base64<br>
	 * @param accNo 卡号<br>
	 * @param pin 密码<br>
	 * @param encoding<br>
	 * @return 加密的内容<br>
	 */
	public static String encryptPin(String accNo, String pin, String encoding) {
        return AcpService.encryptPin(accNo, pin, encoding);
	}
	
	/**
	 * 敏感信息加密并做base64(卡号，手机号，cvn2,有效期）<br>
	 * @param data 送 phoneNo,cvn2,有效期<br>
	 * @param encoding<br>
	 * @return 加密的密文<br>
	 */
	public static String encryptData(String data, String encoding) {
        return AcpService.encryptData(data, encoding);
    }
	
	/**
	 * 敏感信息解密，使用配置文件acp_sdk.properties解密<br>
	 * @param base64EncryptedInfo 加密信息<br>
	 * @param encoding<br>
	 * @return 解密后的明文<br>
	 */
	public static String decryptData(String base64EncryptedInfo, String encoding) {
        return AcpService.decryptData(base64EncryptedInfo, encoding);
    }

	/**
	 * 敏感信息解密,通过传入的私钥解密<br>
	 * @param base64EncryptedInfo 加密信息<br>
	 * @param certPath 私钥文件（带全路径）<br>
	 * @param certPwd 私钥密码<br>
	 * @param encoding<br>
	 * @return
	 */
	public static String decryptData(String base64EncryptedInfo, String certPath, 
			String certPwd, String encoding) {
		return AcpService.decryptData(base64EncryptedInfo, certPath, certPwd, encoding);
	}
	
	/**
	 * 获取敏感信息加密证书的物理序列号<br>
	 * @return
	 */
	public static String getEncryptCertId(){
		return CertUtil.getEncryptCert().certId;
	}
	
	/**
	 * 功能：后台交易提交请求报文并接收同步应答报文<br>
	 * @param reqData 请求报文<br>
	 * @param reqUrl  请求地址<br>
	 * @param encoding<br>
	 * @return 应答http 200返回true ,其他false<br>
	 */
	public static Map<String,String> post(Map<String, String> reqData, String reqUrl,String encoding) {
		return AcpService.post(reqData, reqUrl, encoding);
	}
	
	/**
	 * base64({a=b&c=d})
	 * @param map
	 * @param encoding
	 * @return
	 */
	public static String getKVBase64Field(Map<String, String> map, String encoding){
		StringBuffer sf = new StringBuffer();
        String info = sf.append(SDKConstants.LEFT_BRACE)
        		.append(SDKUtil.createLinkString(map, false, false, encoding))
        		.append(SDKConstants.RIGHT_BRACE).toString();
     	return Base64.encodeBase64String(info.getBytes(Charset.forName(encoding)));
	}
	
	/**
	 * base64(rsa({a=b&c=d}))
	 * @param map
	 * @param encoding
	 * @return
	 */
	public static String getKVEncBase64Field(Map<String, String> map,String encoding){
		StringBuffer sf = new StringBuffer();
        String info = sf.append(SDKConstants.LEFT_BRACE)
        		.append(SDKUtil.createLinkString(map, false, false, encoding))
        		.append(SDKConstants.RIGHT_BRACE).toString();
        return QrcService.encryptData(info, encoding);
	}

	/**
	 * base64({a=b&c=d})
	 * 解析返回报文的payerInfo域，敏感信息不加密时使用：<br>
	 * @param base64data<br>
	 * @param encoding<br>
	 * @return
	 */
	public static Map<String, String> parseKVBase64Field(String base64data, String encoding){
		String data = new String(Base64.decodeBase64(base64data), Charset.forName(encoding));
		data = data.substring(1, data.length() - 1);
		return SDKUtil.parseRespString(data);
	}
	
	/**
	 * base64(rsa({a=b&c=d}))
	 * 解析返回报文的payerInfo域，敏感信息加密时使用：<br>
	 * @param base64data<br>
	 * @param encoding<br>
	 * @return
	 */
	public static Map<String, String> parseKVEncBase64Field(String base64data, String encoding){
		String data = QrcService.decryptData(base64data, encoding);
		data = data.substring(1, data.length() - 1);
		return SDKUtil.parseRespString(data);
	}
	
	/**
	 * base64(rsa({a=b&c=d}))
	 * 解析返回报文中的payerInfo域，敏感信息加密时使用，多证书方式。<br>
	 * @param base64data<br>
	 * @param encoding<br>
	 * @return
	 */
	public static Map<String, String> parseKVEncBase64Field(String base64data, String certPath, 
			String certPwd, String encoding){
		String data = QrcService.decryptData(base64data, certPath, certPwd, encoding);
		data = data.substring(1, data.length() - 1);
		return SDKUtil.parseRespString(data);
	}


	/**
	 * 对字符串做base64<br>
	 * @param rawStr<br>
	 * @param encoding<br>
	 * @return<br>
	 * @throws IOException
	 */
	public static String base64Encode(String rawStr, String encoding){
		return AcpService.base64Encode(rawStr, encoding);
	}

	/**
	 * 对字符串做base64<br>
	 * @param base64Str<br>
	 * @param encoding<br>
	 * @return<br>
	 * @throws IOException
	 */
	public static String base64Decode(String base64Str, String encoding){
		return AcpService.base64Decode(base64Str, encoding);
	}
	
	/**
	 * luhn算法
	 * 
	 * @param number
	 * @return
	 */
	public static int genLuhn(String number) {
		number = number + "0";
		int s1 = 0, s2 = 0;
		String reverse = new StringBuffer(number).reverse().toString();
		for (int i = 0; i < reverse.length(); i++) {
			int digit = Character.digit(reverse.charAt(i), 10);
			if (i % 2 == 0) {// this is for odd digits, they are 1-indexed in //
								// the algorithm
				s1 += digit;
			} else {// add 2 * digit for 0-4, add 2 * digit - 9 for 5-9
				s2 += 2 * digit;
				if (digit >= 5) {
					s2 -= 9;
				}
			}
		}
		int check = 10 - ((s1 + s2) % 10);
		if (check == 10)
			check = 0;
		return check;
	}
}
