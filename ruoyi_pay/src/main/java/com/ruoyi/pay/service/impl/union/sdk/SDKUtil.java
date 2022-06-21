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
package com.ruoyi.pay.service.impl.union.sdk;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.PublicKey;
import java.util.*;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import static com.ruoyi.pay.service.impl.union.sdk.SDKConstants.*;

/**
 * 
 * @ClassName SDKUtil
 * @Description acpsdk工具类
 * @date 2016-7-22 下午4:06:18
 * 声明：以下代码只是为了方便接入方测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障
 */
@Slf4j
public class SDKUtil {


	/**
	 * 全渠道5.0、二维码signType=01用。
	 * 1. 按ascii排序。【注意不是字母顺序】
	 * 2. 对1的结果sha1得到byte数组。
	 * 3. 对2的结果用16进制小写字符串表示。【注意必须是小写】
	 * 4. 对3的结果取byte数组。【注意不是16进制字符串转byte数组，而是当普通字符串转】
	 * 5. 对4的结果用私钥算签名，算法为sha1withrsa。
	 * 6. 对5的结果做base64，得到一个字符串就是签名。
	 * @param data
	 * @param certPath
	 * @param certPwd
	 * @param encoding
	 * @return
	 */
    public static String signRsa(Map<String, String> data, String certPath, String certPwd, String encoding) {
        try{
            String stringData = createLinkString(data, true, false, encoding);
			log.info("打印排序后待签名请求报文串（交易返回11验证签名失败时可以用来同正确的进行比对）:[" + stringData + "]");
            byte[] sha1 = SecureUtil.sha1(stringData.getBytes(encoding));
            String sha1Hex = byteArrayToHexString(sha1).toLowerCase();
			log.info("sha1结果（交易返回11验证签名失败可以用来同正确的进行比对）:[" + sha1Hex + "]");
            return Base64.encodeBase64String(SecureUtil.getSignature(CertUtil.getSignCertPrivateKeyByStoreMap(certPath, certPwd), sha1Hex.getBytes()));
        } catch (Exception e) {
			log.error("calcSignRsa Error", e);
            return null;
        }
    }

	/**
	 * 全渠道5.1signMethod=01、二维码signType=02（以及少数接口）用。
	 * 1. 按ascii排序。【注意不是字母顺序】
	 * 2. 对1的结果sha256得到byte数组。
	 * 3. 对2的结果用16进制小写字符串表示。【注意必须是小写】
	 * 4. 对3的结果取byte数组。【注意不是16进制字符串转byte数组，而是当普通字符串转】
	 * 5. 对4的结果用私钥算签名，算法为sha256withrsa。
	 * 6. 对5的结果做base64，得到一个字符串就是签名。
	 * @param data
	 * @param certPath
	 * @param certPwd
	 * @param encoding
	 * @return
	 */
    public static String signRsa2(Map<String, String> data, String certPath, String certPwd, String encoding) {
        try {
            String stringData = createLinkString(data, true, false, encoding);
			log.info("打印排序后待签名请求报文串（交易返回11验证签名失败时可以用来同正确的进行比对）:[" + stringData + "]");
            byte[] sha256 = SecureUtil.sha256(stringData.getBytes(encoding));
            String sha256Hex = byteArrayToHexString(sha256).toLowerCase();
			log.info("sha256（交易返回11验证签名失败可以用来同正确的进行比对）:[" + sha256Hex + "]");
            return Base64.encodeBase64String(SecureUtil.getSignatureSHA256(CertUtil.getSignCertPrivateKeyByStoreMap(certPath, certPwd), sha256Hex.getBytes()));
        } catch (Exception e) {
			log.error("calcSignRsa2 Error", e);
            return null;
        }
    }

	/**
	 * 全渠道5.1signMethod=11用。
	 * @param data
	 * @param encoding
	 * @return
	 */
    public static String signSha256(Map<String, String> data, String secureKey, String encoding) {
        String sorted = createLinkString(data, true, false, encoding);
		log.info("排序串:[" + sorted + "]");
        return SDKUtil.calcSignSha256(sorted, secureKey, encoding);
    }


	/**
	 * 全渠道5.1signMethod=12用。
	 * @param data
	 * @param encoding
	 * @return
	 */
    public static String signSm3(Map<String, String> data, String secureKey, String encoding) {
        String sorted = createLinkString(data, true, false, encoding);
		log.info("排序串:[" + sorted + "]");
        return SDKUtil.calcSignSm3(sorted, secureKey, encoding);
    }

    private static String calcSignSm3(String sorted, String secureKey, String encoding) {
        try {
            String s = sorted + SDKConstants.AMPERSAND
                    + byteArrayToHexString(SecureUtil.sm3(secureKey.getBytes(encoding)));
            return byteArrayToHexString(SecureUtil.sm3(s.getBytes(encoding)));
        } catch (Exception e) {
			log.error("calcSignSm3 Error", e);
            return null;
        }
    }

    private static String calcSignSha256(String sorted, String secureKey, String encoding) {
        try {
            String s = sorted + SDKConstants.AMPERSAND
                    + byteArrayToHexString(SecureUtil.sha256(secureKey.getBytes(encoding)));
            return byteArrayToHexString(SecureUtil.sha256(s.getBytes(encoding)));
        } catch (Exception e) {
			log.error("calcSignSha256 Error", e);
            return null;
        }
    }

    /**
     * 全渠道5.0接口、二维码signType=01用。
     * @param resData
     * @param encoding
     * @return
     */
    public static boolean verifyRsa(Map<String, String> resData, PublicKey pubKey, String encoding) {
        try{
            String stringSign = resData.remove(SDKConstants.param_signature);
        	if(isEmpty(stringSign)) {
				log.error("signature is null. verifyRsa fail.");
                return false;
        	}
			log.info("签名原文：[" + stringSign + "]");
            String stringData = createLinkString(resData, true, false, encoding);
			log.info("待验签排序串：[" + stringData + "]");
            byte[] sha1 = SecureUtil.sha1(stringData.getBytes(encoding));
            String sha1Hex = byteArrayToHexString(sha1).toLowerCase();
			log.info("sha1结果:[" + sha1Hex + "]");
            return SecureUtil.verifySignature(pubKey,
                    sha1Hex.getBytes(encoding),
                    Base64.decodeBase64(stringSign));
        } catch (Exception e) {
			log.error("verifyRsa fail." + e.getMessage(), e);
            return false;
        }
    }

    /**
     * 全渠道5.1、二维码signType=02用。
     * @param resData
     * @param encoding
     * @param publicKey
     * @return
     */
    public static boolean verifyRsa2(Map<String, String> resData, PublicKey publicKey, String encoding) {
        try{
            String stringSign = resData.remove(SDKConstants.param_signature);
        	if(isEmpty(stringSign)) {
				log.error("signature is null. verifyRsa2 fail.");
                return false;
        	}
			log.info("签名原文：[" + stringSign + "]");
            String stringData = createLinkString(resData, true, false, encoding);
			log.info("待验签排序串：[" + stringData + "]");
            byte[] sha256 = SecureUtil.sha256(stringData.getBytes(encoding));
            String sha256Hex = byteArrayToHexString(sha256).toLowerCase();
			log.info("sha256结果:[" + sha256Hex + "]");
            boolean result = SecureUtil.verifySignatureSHA256(publicKey,
                    sha256Hex.getBytes(encoding),
                    Base64.decodeBase64(stringSign));
			log.info("验证签名" + (result ? "成功" : "失败"));
            return result;
        } catch (Exception e) {
			log.error("verifyRsa2 fail." + e.getMessage(), e);
            return false;
        }
    }

    public static boolean verifySha256(Map<String, String> resData, String secureKey, String encoding){
        try{
	    	if(isEmpty(secureKey)) {
				log.error("secureKey is null. verifySha256 fail.");
	            return false;
	    	}
	        String sign = resData.remove(SDKConstants.param_signature);
	    	if(isEmpty(sign)) {
				log.error("signature is null. verifySha256 fail.");
	            return false;
	    	}
			log.info("签名原文：[" + sign + "]");
	        String sorted = createLinkString(resData, true, false, encoding);
			log.info("排序串：[" + sorted + "]");
	        String expectedSign = calcSignSha256(sorted, secureKey, encoding);
	        boolean result = sign.equals(expectedSign);
	        if(!result) log.error("验签失败，签名预期：" + expectedSign + "， 签名实际：" + sign);
	        else log.info("验签成功");
	        return result;
        } catch (Exception e) {
			log.error("verifySha256 fail." + e.getMessage(), e);
            return false;
        }
    }

    public static boolean verifySm3(Map<String, String> resData, String secureKey, String encoding){
        try{
	    	if(isEmpty(secureKey)) {
				log.error("secureKey is null. verifySm3 fail.");
	            return false;
	    	}
	        String sign = resData.remove(SDKConstants.param_signature);
	    	if(isEmpty(sign)) {
				log.error("signature is null. verifySm3 fail.");
	            return false;
	    	}
			log.info("签名原文：[" + sign + "]");
			String sorted = createLinkString(resData, true, false, encoding);
			log.info("排序串：[" + sorted + "]");
	        String expectedSign = calcSignSm3(sorted, secureKey, encoding);
	        boolean result = sign.equals(expectedSign);
	        if(!result) log.error("验签失败，签名预期：" + expectedSign + "， 签名实际：" + sign);
	        else log.info("验签成功");
	        return result;
	    } catch (Exception e) {
			log.error("verifySm3 fail." + e.getMessage(), e);
	        return false;
	    }
    }

	/**
	 * 把请求要素按照“参数=参数值”的模式用“&”字符拼接成字符串
	 *
	 * @param para
	 *            请求要素
	 * @param sort
	 *            是否需要根据key值作升序排列
	 * @param encode
	 *            是否需要URL编码
	 * @return 拼接成的字符串
	 */
	public static String createLinkString(Map<String, String> para, boolean sort, boolean encode, String charset) {

		List<String> keys = new ArrayList<String>(para.keySet());

		if (sort)
			Collections.sort(keys);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = para.get(key);

			if (encode && value != null) {
				try {
					value = URLEncoder.encode(value, charset);
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(charset + "送错了.");
				}
			}

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				sb.append(key).append("=").append(value);
			} else {
				sb.append(key).append("=").append(value).append("&");
			}
		}
		return sb.toString();
	}

	/**
	 * 解析应答字符串，生成应答要素。
	 * 解析全渠道5.0、5.1，二维码的应答报文时不要用这个方法哦。
	 * @param str 需要解析的字符串
	 * @param charset
	 * @return 解析的结果map
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> parseQString(String str, String charset) {
		if (str == null || str.length() == 0 ) return new HashMap<String, String>();
		Map<String, String> map = new HashMap<String, String>();
		int len = str.length();
		StringBuilder temp = new StringBuilder();
		char curChar;
		String key = null;
		boolean isKey = true;

		for (int i = 0; i < len; i++) {// 遍历整个带解析的字符串
			curChar = str.charAt(i);// 取当前字符

			if (curChar == '&') {// 如果读取到&分割符
				putKeyValueToMap(temp, isKey, key, map, true, charset);
				temp.setLength(0);
				isKey = true;
			} else {
				if (isKey) {// 如果当前生成的是key
					if (curChar == '=') {// 如果读取到=分隔符
						key = temp.toString();
						temp.setLength(0);
						isKey = false;
					} else {
						temp.append(curChar);
					}
				} else {// 如果当前生成的是value
					temp.append(curChar);
				}
			}
		}
		putKeyValueToMap(temp, isKey, key, map, true, charset);
		return map;
	}

	/**
	 * 解析应答字符串，生成应答要素。
	 * 处理全渠道应答报文那种不带url编码又可能在value里成对出现括号且括号里带&和=的情况。
	 * 报文解析工具本身用不到，给验签的小工具用。
	 *
	 * @param str 需要解析的字符串
	 * @return 解析的结果map
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> parseRespString(String str) {
		if (str == null || str.length() == 0 ) return new HashMap<String, String>();
		Map<String, String> map = new HashMap<String, String>();
		int len = str.length();
		StringBuilder temp = new StringBuilder();
		char curChar;
		String key = null;
		boolean isKey = true;
		boolean isOpen = false;//值里有嵌套
		char openName = 0;
		for (int i = 0; i < len; i++) {// 遍历整个带解析的字符串
			curChar = str.charAt(i);// 取当前字符
			if (isKey) {// 如果当前生成的是key

				if (curChar == '=') {// 如果读取到=分隔符
					key = temp.toString();
					temp.setLength(0);
					isKey = false;
				} else {
					temp.append(curChar);
				}
			} else  {// 如果当前生成的是value
				if(isOpen){
					if(curChar == openName){
						isOpen = false;
					}

				}else{//如果没开启嵌套
					if(curChar == '{'){//如果碰到，就开启嵌套
						isOpen = true;
						openName ='}';
					}
					if(curChar == '['){
						isOpen = true;
						openName =']';
					}
				}

				if (curChar == '&' && !isOpen) {// 如果读取到&分割符,同时这个分割符不是值域，这时将map里添加
					putKeyValueToMap(temp, isKey, key, map, false, null);
					temp.setLength(0);
					isKey = true;
				} else {
					temp.append(curChar);
				}
			}
		}
		putKeyValueToMap(temp, isKey, key, map, false, null);
		return map;
	}

	private static void putKeyValueToMap(StringBuilder temp, boolean isKey, String key, Map<String, String> map,
										 boolean decode, String charset) {
		try {
			if (decode) {
				key = URLDecoder.decode(key, charset);
			}
			if (isKey) {
				key = temp.toString();
				map.put(key, "");
			} else {
				if (decode) {
					String value = URLDecoder.decode(temp.toString(), charset);
					map.put(key, value);
				} else {
					map.put(key, temp.toString());
				}
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("编码有问题: " + charset);
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
		if (isEmpty(strCert) || isEmpty(certType))
			return -1;
		if (CERTTYPE_01.equals(certType)) {
			// 更新敏感信息加密公钥
            return CertUtil.resetEncryptCertPublicKey(strCert);
		} else if (CERTTYPE_02.equals(certType)) {
			// 更新pin敏感信息加密公钥
			return CertUtil.resetPinEncryptCertPublicKey(strCert);
		} else {
			log.info("unknown cerType:"+certType);
			return -1;
		}
	}

	/**
	 * 过滤请求报文中的空字符串或者空字符串
	 * @param contentData
	 * @return
	 */
	public static Map<String, String> filterBlank(Map<String, String> contentData){
		Map<String, String> submitFromData = new HashMap<String, String>();
		Set<String> keyset = contentData.keySet();
		
		for(String key : keyset){
			String value = contentData.get(key);
			if (!isEmpty(value)) {
				submitFromData.put(key, value.trim()); //不知道为啥一直有个trim，如果值里自带空格岂不是要出bug……但一直就这样，先 不管它吧。
			}
		}
		return submitFromData;
	}
	
	/**
	 * 解压缩.
	 * 
	 * @param inputByte
	 *            byte[]数组类型的数据
	 * @return 解压缩后的数据
	 * @throws IOException
	 */
	public static byte[] inflater(final byte[] inputByte) throws IOException {
		int compressedDataLength = 0;
		Inflater compresser = new Inflater(false);
		compresser.setInput(inputByte, 0, inputByte.length);
		ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
		byte[] result = new byte[1024];
		try {
			while (!compresser.finished()) {
				compressedDataLength = compresser.inflate(result);
				if (compressedDataLength == 0) {
					break;
				}
				o.write(result, 0, compressedDataLength);
			}
		} catch (Exception ex) {
			log.error("Data format error!", ex);
		} finally {
			o.close();
		}
		compresser.end();
		return o.toByteArray();
	}

	/**
	 * 压缩.
	 * 
	 * @param inputByte
	 *            需要解压缩的byte[]数组
	 * @return 压缩后的数据
	 * @throws IOException
	 */
	public static byte[] deflater(final byte[] inputByte) throws IOException {
		int compressedDataLength = 0;
		Deflater compresser = new Deflater();
		compresser.setInput(inputByte);
		compresser.finish();
		ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
		byte[] result = new byte[1024];
		try {
			while (!compresser.finished()) {
				compressedDataLength = compresser.deflate(result);
				o.write(result, 0, compressedDataLength);
			}
		} finally {
			o.close();
		}
		compresser.end();
		return o.toByteArray();
	}
	
	/**
	 * 判断字符串是否为NULL或空
	 * 
	 * @param s
	 *            待判断的字符串数据
	 * @return 判断结果 true-是 false-否
	 */
	public static boolean isEmpty(String s) {
		return null == s || "".equals(s.trim());
	}

    /**
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStringToByteArray(String hexString){
        try {
            return Hex.decodeHex(hexString.toCharArray());
        } catch (DecoderException e) {
			log.error("非正常16进制字符串" + hexString, e);
            return null;
        }
    }

    /**
     *
     * @param bytes
     * @return 16进制字符串，小写
     */
    public static String byteArrayToHexString(byte[] bytes){
        return new String(Hex.encodeHex(bytes, true));
    }
}
