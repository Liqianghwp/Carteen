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
 * xshu       2014-05-28     报文加密解密等操作的工具类
 * =============================================================================
 */
package com.ruoyi.pay.service.impl.union.sdk;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.digests.SM3Digest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.*;
import java.util.Arrays;

/**
 *
 * @ClassName SecureUtil
 * @Description acpsdk安全算法工具类
 * @date 2016-7-22 下午4:08:32
 */
@Slf4j
public class SecureUtil {


    /**
     * @param bytes
     * @return
     */
    public static byte[] sha1(byte[] bytes) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(bytes);
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            log.error("SHA1计算失败", e);
            return null;
        }
    }

    /**
     * @param bytes
     * @return
     */
    public static byte[] sha256(byte[] bytes) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bytes);
            return messageDigest.digest();
        } catch (Exception e) {
            log.error("SHA256计算失败", e);
            return null;
        }
    }

    /**
     * @param bytes
     * @return
     */
    public static byte[] sm3(byte[] bytes) {
        SM3Digest sm3 = new SM3Digest();
        sm3.update(bytes, 0, bytes.length);
        byte[] result = new byte[sm3.getDigestSize()];
        sm3.doFinal(result, 0);
        return result;
    }

    public static byte[] getSignature(PrivateKey priKey, byte[] digest) {
        byte[] mesDigest;
        Signature sig;
        try {
            sig = Signature.getInstance("SHA1withRSA");
            sig.initSign(priKey);
            sig.update(digest);
            mesDigest = sig.sign();
            return mesDigest;
        } catch (Exception e) {
            log.error("签名计算失败", e);
            return null;
        }
    }

    public static byte[] getSignatureSHA256(PrivateKey priKey, byte[] digest) {
        byte[] mesDigest;
        Signature sig;
        try {
            sig = Signature.getInstance("SHA256withRSA");
            sig.initSign(priKey);
            sig.update(digest);
            mesDigest = sig.sign();
            return mesDigest;
        } catch (Exception e) {
            log.error("签名计算失败", e);
            return null;
        }
    }


    public static boolean verifySignature(PublicKey pubKey, byte[] digest, byte[] signature) {
        try {
            Signature sig = Signature.getInstance("SHA1withRSA");

            sig.initVerify(pubKey);
            sig.update(digest);

            boolean ok = sig.verify(signature);
            return ok;
        } catch (Exception e) {
            log.error("验签异常", e);
            return false;
        }
    }


    public static boolean verifySignatureSHA256(PublicKey pubKey, byte[] digest, byte[] signature) {
        if (pubKey == null || digest == null || signature == null) {
            if (pubKey == null) {
                log.error("验签时pubKey传入了空值，验签失败");
            } else if (digest == null) {
                log.error("验签时digest传入了空值，验签失败");
            } else if (signature == null) {
                log.error("验签时signature传入了空值，验签失败");
            } else {
                log.error("验签时传入了空值，验签失败");
            }
            return false;
        }
        try {
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify(pubKey);
            sig.update(digest);
            boolean ok = sig.verify(signature);
            return ok;
        } catch (Exception e) {
            log.error("验签异常", e);
            return false;
        }
    }

    public static byte[] encrypt(Key key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            log.error("加密失败", e);
            return null;
        }
    }

    public static byte[] decrypt(Key Key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, Key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            log.error("解密失败", e);
            return null;
        }
    }

    /**
     * ANSIX9.8格式（带主账号信息）pinblock
     * @param pan 卡号
     * @param pin
     * @return
     */
    public static byte[] pinblock(String pan, String pin) {

        if (SDKUtil.isEmpty(pan) || SDKUtil.isEmpty(pin)) {
            log.error("卡号或pin为空，无法算pinblock。");
            return null;
        }
        pan = pan.trim();
        pin = pin.trim();
        if (!pan.matches("^[0-9]{13,19}$")) {
            log.error("卡号格式不对，无法算pinblock。");
            return null;
        }
        if (!pin.matches("^[0-9]{4,6}$")) {
            log.error("pin格式不对，无法算pinblock。");
            return null;
        }

        pan = ("0000") + pan.substring(pan.length() - 13, pan.length() - 1);
        int blockLen = 8;
        try {
            pin = "0" + pin.length() + pin;
            byte[] pinbyte = Arrays.copyOf(Hex.decodeHex(pin.toCharArray()), blockLen);
            Arrays.fill(pinbyte, pin.length() / 2, blockLen, (byte) 0xff);
            byte[] panbyte = Hex.decodeHex(pan.toCharArray());
            byte[] tempPin = new byte[blockLen];
            for (int i = 0; i < blockLen; i++) {
                tempPin[i] = (byte) (pinbyte[i] ^ panbyte[i]);
            }
            return tempPin;
        } catch (Exception e) {
            log.error("pinblock计算异常啦……", e);
            return null;
        }
    }

//    /**
//    * ANSI X9.8格式（不带主账号信息）pinblock
//    * @param pin
//    * @return
//    */
//    public static byte[] pinblock(String pin){
//
//       if(SDKUtil.isEmpty(pin)){
//			logger.error("卡号或pin为空，无法算pinblock。");
//			return null;
//		}
//       pin = pin.trim();
//       if (!pin.matches("^[0-9]{4,6}$")) {
//           logger.error("pin格式不对，无法算pinblock。");
//           return null;
//       }
//       int blockLen = 8;
//       try {
//           pin = "0" + pin.length() + pin;
//           byte[] pinbyte = Arrays.copyOf(Hex.decodeHex(pin.toCharArray()), blockLen);
//           Arrays.fill(pinbyte, pin.length()/2, blockLen, (byte)0xff);
//           return pinbyte;
//       } catch (Exception e){
//           logger.error("pinblock计算异常啦……", e);
//           return null;
//       }
//   }

    public static byte[] tripleDesEncryptECBPKCS5Padding(byte[] key, byte[] data) {

        try {
            if (data == null || data.length % 8 != 0)
                throw new IllegalArgumentException("data is null or error data length.");

            SecretKey sk = getTripleDesKey(key);

            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, sk);
            return cipher.doFinal(data);

        } catch (Exception e) {
            log.error("加密失败", e);
            return null;
        }
    }

    /**
     * 后补0到位数为unitLength的整数倍
     * @param value
     * @return
     */
    public static byte[] rightPadZero(byte[] value, final int unitLength) {
        if (value.length % unitLength == 0)
            return value;
        int len = (value.length / unitLength + 1) * unitLength;
        return Arrays.copyOf(value, len);
    }

    /**
     * 通过byte数组得到SecretKey类型的密钥
     * @param key
     * @return
     * @throws IllegalArgumentException
     */
    private static SecretKey getTripleDesKey(byte[] key) {

        if (key == null || !(key.length == 8 || key.length == 16 || key.length == 24))
            throw new IllegalArgumentException("key is null or error key length.");

        byte[] specKey = new byte[24];

        try {
            switch (key.length) {
                case 16:
                    System.arraycopy(key, 0, specKey, 0, 16);
                    System.arraycopy(key, 0, specKey, 16, 8);
                    break;
                case 8:
                    System.arraycopy(key, 0, specKey, 0, 8);
                    System.arraycopy(key, 0, specKey, 8, 8);
                    System.arraycopy(key, 0, specKey, 16, 8);
                    break;
                case 24:
                    System.arraycopy(key, 0, specKey, 0, 24);
                    break;
                default:
                    throw new IllegalArgumentException("error key length.");
            }

            DESedeKeySpec ks = new DESedeKeySpec(specKey);
            SecretKey sk = SecretKeyFactory.getInstance("DESede")
                    .generateSecret(ks);
            return sk;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SecurityException("exception in 3des-ecb encryption", e);
        }
    }
}
