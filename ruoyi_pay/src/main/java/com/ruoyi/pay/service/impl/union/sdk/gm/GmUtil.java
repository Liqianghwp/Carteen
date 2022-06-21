package com.ruoyi.pay.service.impl.union.sdk.gm;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jcajce.spec.SM2ParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/**
 * need jars:
 * bcpkix-jdk15on-160.jar
 * bcprov-jdk15on-160.jar
 *
 * ref:
 * https://tools.ietf.org/html/draft-shen-sm2-ecdsa-02
 * http://gmssl.org/docs/oid.html
 * http://www.jonllen.com/jonllen/work/164.aspx
 *
 * 用BC的注意点：
 * 这个版本的BC对SM3withSM2的结果为asn1格式的r和s，如果需要直接拼接的r||s需要自己转换。下面rsAsn1ToPlainByteArray、rsPlainByteArrayToAsn1就在干这事。
 * 1.60、1.61版本的BC对SM2的结果为C1||C2||C3，据说为旧标准，新标准为C1||C3||C2，用新标准的需要自己转换。下面（被注释掉的）changeC1C2C3ToC1C3C2、changeC1C3C2ToC1C2C3就在干这事。1.62版本开始加上了C1C3C2，初始化时“ SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);”即可。
 * 
 * 按要求国密算法仅允许使用加密机，本demo国密算法仅供学习使用，请不要用于生产用途。
 */
public class GmUtil {

    private static X9ECParameters x9ECParameters = GMNamedCurves.getByName("sm2p256v1");
    private static ECDomainParameters ecDomainParameters = new ECDomainParameters(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN());
    private static ECParameterSpec ecParameterSpec = new ECParameterSpec(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN());

    static {
		if (Security.getProvider("BC") == null) {
			Security.addProvider(new BouncyCastleProvider());
		} else {
			Security.removeProvider("BC"); //解决eclipse调试时tomcat自动重新加载时，BC存在不明原因异常的问题。
			Security.addProvider(new BouncyCastleProvider());
		}
    }

    /**
     *
     * @param msg
     * @param userId
     * @param privateKey
     * @return r||s，直接拼接byte数组的rs
     */
    public static byte[] signSm3WithSm2(byte[] msg, byte[] userId, PrivateKey privateKey){
        return rsAsn1ToPlainByteArray(signSm3WithSm2Asn1Rs(msg, userId, privateKey));
    }

    /**
     *
     * @param msg
     * @param userId
     * @param privateKey
     * @return rs in <b>asn1 format</b>
     */
    public static byte[] signSm3WithSm2Asn1Rs(byte[] msg, byte[] userId, PrivateKey privateKey){
        try {
            SM2ParameterSpec parameterSpec = new SM2ParameterSpec(userId);
            Signature signer = Signature.getInstance("SM3withSM2", "BC");
            signer.setParameter(parameterSpec);
            signer.initSign(privateKey, new SecureRandom());
            signer.update(msg, 0, msg.length);
            byte[] sig = signer.sign();
            return sig;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param msg
     * @param userId
     * @param rs r||s，直接拼接byte数组的rs
     * @param publicKey
     * @return
     */
    public static boolean verifySm3WithSm2(byte[] msg, byte[] userId, byte[] rs, PublicKey publicKey){
    	if(rs == null || msg == null || userId == null) return false;
        if(rs.length != RS_LEN * 2) return false;
        return verifySm3WithSm2Asn1Rs(msg, userId, rsPlainByteArrayToAsn1(rs), publicKey);
    }

    /**
     *
     * @param msg
     * @param userId
     * @param rs in <b>asn1 format</b>
     * @param publicKey
     * @return
     */
    public static boolean verifySm3WithSm2Asn1Rs(byte[] msg, byte[] userId, byte[] rs, PublicKey publicKey){
        try {
            SM2ParameterSpec parameterSpec = new SM2ParameterSpec(userId);
            Signature verifier = Signature.getInstance("SM3withSM2", "BC");
            verifier.setParameter(parameterSpec);
            verifier.initVerify(publicKey);
            verifier.update(msg, 0, msg.length);
            return verifier.verify(rs);
        } catch (Exception e) {
//            throw new RuntimeException(e);
			return false;
        }
    }

    /**
     * bc加解密使用旧标c1||c2||c3，此方法在加密后调用，将结果转化为c1||c3||c2
     * @param c1c2c3
     * @return
     */
    private static byte[] changeC1C2C3ToC1C3C2(byte[] c1c2c3) {
        final int c1Len = (x9ECParameters.getCurve().getFieldSize() + 7) / 8 * 2 + 1; //sm2p256v1的这个固定65。可看GMNamedCurves、ECCurve代码。
        final int c3Len = 32; //new SM3Digest().getDigestSize();
        byte[] result = new byte[c1c2c3.length];
        System.arraycopy(c1c2c3, 0, result, 0, c1Len); //c1
        System.arraycopy(c1c2c3, c1c2c3.length - c3Len, result, c1Len, c3Len); //c3
        System.arraycopy(c1c2c3, c1Len, result, c1Len + c3Len, c1c2c3.length - c1Len - c3Len); //c2
        return result;
    }


    /**
     * bc加解密使用旧标c1||c3||c2，此方法在解密前调用，将密文转化为c1||c2||c3再去解密
     * @param c1c3c2
     * @return
     */
    private static byte[] changeC1C3C2ToC1C2C3(byte[] c1c3c2) {
        final int c1Len = (x9ECParameters.getCurve().getFieldSize() + 7) / 8 * 2 + 1; //sm2p256v1的这个固定65。可看GMNamedCurves、ECCurve代码。
        final int c3Len = 32; //new SM3Digest().getDigestSize();
        byte[] result = new byte[c1c3c2.length];
        System.arraycopy(c1c3c2, 0, result, 0, c1Len); //c1: 0->65
        System.arraycopy(c1c3c2, c1Len + c3Len, result, c1Len, c1c3c2.length - c1Len - c3Len); //c2
        System.arraycopy(c1c3c2, c1Len, result, c1c3c2.length - c3Len, c3Len); //c3
        return result;
    }

    /**
     * c1||c3||c2
     * @param data
     * @param key
     * @return
     */
    public static byte[] sm2Decrypt(byte[] data, PrivateKey key){
        try {
        	return sm2DecryptOld(changeC1C3C2ToC1C2C3(data), key);
        } catch (Exception e) {
//          throw new RuntimeException(e);
			return null;
      }
    }

    /**
     * c1||c3||c2
     * @param data
     * @param key
     * @return
     */

    public static byte[] sm2Encrypt(byte[] data, PublicKey key){
        return changeC1C2C3ToC1C3C2(sm2EncryptOld(data, key));
    }

    /**
     * c1||c2||c3
     * @param data
     * @param key
     * @return
     */
    public static byte[] sm2EncryptOld(byte[] data, PublicKey key){
        BCECPublicKey localECPublicKey = (BCECPublicKey) key;
        ECPublicKeyParameters ecPublicKeyParameters = new ECPublicKeyParameters(localECPublicKey.getQ(), ecDomainParameters);
        SM2Engine sm2Engine = new SM2Engine();
        sm2Engine.init(true, new ParametersWithRandom(ecPublicKeyParameters, new SecureRandom()));
        try {
            return sm2Engine.processBlock(data, 0, data.length);
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * c1||c2||c3
     * @param data
     * @param key
     * @return
     */
    public static byte[] sm2DecryptOld(byte[] data, PrivateKey key){
        BCECPrivateKey localECPrivateKey = (BCECPrivateKey) key;
        ECPrivateKeyParameters ecPrivateKeyParameters = new ECPrivateKeyParameters(localECPrivateKey.getD(), ecDomainParameters);
        SM2Engine sm2Engine = new SM2Engine();
        sm2Engine.init(false, ecPrivateKeyParameters);
        try {
            return sm2Engine.processBlock(data, 0, data.length);
        } catch (Exception e) {
//            throw new RuntimeException(e);
			return null;
        }
    }
    
    public static byte[] sm4EncryptECB(byte[] keyBytes, byte[] plain, String algo){
        if(keyBytes.length != 16) throw new RuntimeException("err key length");
        if(plain.length % 16 != 0) throw new RuntimeException("err data length");

        try {
            Key key = new SecretKeySpec(keyBytes, "SM4");
            Cipher out = Cipher.getInstance(algo, "BC");
            out.init(Cipher.ENCRYPT_MODE, key);
            return out.doFinal(plain);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    public static byte[] sm4Decrypt(byte[] keyBytes, byte[] cipher){
//    	return sm4Decrypt(keyBytes, cipher, NO_PADDING);
//    }

    public static byte[] sm4DecryptECB(byte[] keyBytes, byte[] cipher, String algo){
        if(keyBytes.length != 16) throw new RuntimeException("err key length");
        if(cipher.length % 16 != 0) throw new RuntimeException("err data length");

        try {
            Key key = new SecretKeySpec(keyBytes, "SM4");
            Cipher in = Cipher.getInstance(algo, "BC");
            in.init(Cipher.DECRYPT_MODE, key);
            return in.doFinal(cipher);

        } catch (Exception e) {
//            throw new RuntimeException(e);
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

    private final static int RS_LEN = 32;

    private static byte[] bigIntToFixexLengthBytes(BigInteger rOrS){
        // for sm2p256v1, n is 00fffffffeffffffffffffffffffffffff7203df6b21c6052b53bbf40939d54123,
        // r and s are the result of mod n, so they should be less than n and have length<=32
        byte[] rs = rOrS.toByteArray();
        if(rs.length == RS_LEN) return rs;
        else if(rs.length == RS_LEN + 1 && rs[0] == 0) return Arrays.copyOfRange(rs, 1, RS_LEN + 1);
        else if(rs.length < RS_LEN) {
            byte[] result = new byte[RS_LEN];
            Arrays.fill(result, (byte)0);
            System.arraycopy(rs, 0, result, RS_LEN - rs.length, rs.length);
            return result;
        } else {
            throw new RuntimeException("err rs: " + Hex.toHexString(rs));
        }
    }

    /**
     * BC的SM3withSM2签名得到的结果的rs是asn1格式的，这个方法转化成直接拼接r||s
     * @param rsDer rs in asn1 format
     * @return sign result in plain byte array
     */
    private static byte[] rsAsn1ToPlainByteArray(byte[] rsDer){
        ASN1Sequence seq = ASN1Sequence.getInstance(rsDer);
        byte[] r = bigIntToFixexLengthBytes(ASN1Integer.getInstance(seq.getObjectAt(0)).getValue());
        byte[] s = bigIntToFixexLengthBytes(ASN1Integer.getInstance(seq.getObjectAt(1)).getValue());
        byte[] result = new byte[RS_LEN * 2];
        System.arraycopy(r, 0, result, 0, r.length);
        System.arraycopy(s, 0, result, RS_LEN, s.length);
        return result;
    }

    /**
     * BC的SM3withSM2验签需要的rs是asn1格式的，这个方法将直接拼接r||s的字节数组转化成asn1格式
     * @param sign in plain byte array
     * @return rs result in asn1 format
     */
    private static byte[] rsPlainByteArrayToAsn1(byte[] sign){
        if(sign.length != RS_LEN * 2) throw new RuntimeException("err rs. ");
        BigInteger r = new BigInteger(1, Arrays.copyOfRange(sign, 0, RS_LEN));
        BigInteger s = new BigInteger(1, Arrays.copyOfRange(sign, RS_LEN, RS_LEN * 2));
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.add(new ASN1Integer(r));
        v.add(new ASN1Integer(s));
        try {
            return new DERSequence(v).getEncoded("DER");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static KeyPair generateKeyPair(){
        try {
            KeyPairGenerator kpGen = KeyPairGenerator.getInstance("EC", "BC");
            kpGen.initialize(ecParameterSpec, new SecureRandom());
            KeyPair kp = kpGen.generateKeyPair();
            return kp;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static BCECPrivateKey getPrivatekeyFromD(BigInteger d){
        ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(d, ecParameterSpec);
        return new BCECPrivateKey("EC", ecPrivateKeySpec, BouncyCastleProvider.CONFIGURATION);
    }

    public static BCECPublicKey getPublickeyFromXY(BigInteger x, BigInteger y){
        ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(x9ECParameters.getCurve().createPoint(x, y), ecParameterSpec);
        return new BCECPublicKey("EC", ecPublicKeySpec, BouncyCastleProvider.CONFIGURATION);
    }

    public static PublicKey getPublickeyFromX509File(File file){
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
            FileInputStream in = new FileInputStream(file);
            X509Certificate x509 = (X509Certificate) cf.generateCertificate(in);
            return x509.getPublicKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static class Sm2Cert {
    	private PrivateKey privateKey;
    	private PublicKey publicKey;
    	private String certId;
		public PrivateKey getPrivateKey() {
			return privateKey;
		}
		public void setPrivateKey(PrivateKey privateKey) {
			this.privateKey = privateKey;
		}
		public PublicKey getPublicKey() {
			return publicKey;
		}
		public void setPublicKey(PublicKey publicKey) {
			this.publicKey = publicKey;
		}
		public String getCertId() {
			return certId;
		}
		public void setCertId(String certId) {
			this.certId = certId;
		}
    }

    private static byte[] toByteArray(int i) {
        byte[] byteArray = new byte[4];
        byteArray[0] = (byte) (i >>> 24);
        byteArray[1] = (byte) ((i & 0xFFFFFF) >>> 16);
        byteArray[2] = (byte) ((i & 0xFFFF) >>> 8);
        byteArray[3] = (byte) (i & 0xFF);
        return byteArray;
    }

    /**
     * 字节数组拼接
     *
     * @param params
     * @return
     */
    private static byte[] join(byte[]... params) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] res = null;
        try {
            for (int i = 0; i < params.length; i++) {
                baos.write(params[i]);
            }
            res = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 密钥派生函数
     *
     * @param Z
     * @param klen
     *            生成klen字节数长度的密钥
     * @return
     */
    private static byte[] KDF(byte[] Z, int klen) {
        int ct = 1;
        int end = (int) Math.ceil(klen * 1.0 / 32);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            for (int i = 1; i < end; i++) {
                baos.write(GmUtil.sm3(join(Z, toByteArray(ct))));
                ct++;
            }
            byte[] last = GmUtil.sm3(join(Z, toByteArray(ct)));
            if (klen % 32 == 0) {
                baos.write(last);
            } else
                baos.write(last, 0, klen % 32);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
//    public static byte[] sm4Decrypt(byte[] keyBytes, byte[] cipher, byte[] iv){
//    	return sm4Decrypt(keyBytes, cipher, iv, NO_PADDING);
//    }
	
    public static byte[] sm4DecryptCBC(byte[] keyBytes, byte[] cipher, byte[] iv, String algo){
        try {
            Key key = new SecretKeySpec(keyBytes, "SM4");
            Cipher in = Cipher.getInstance(algo, "BC");
			if(iv == null) iv = zeroIv(algo);
            in.init(Cipher.DECRYPT_MODE, key, getIV(iv));
            return in.doFinal(cipher);

        } catch (Exception e) {
//            throw new RuntimeException(e);
			return null;
        }
    }
    
//    public static byte[] sm4Encrypt(byte[] keyBytes, byte[] data, byte[] iv){
//    	return sm4Encrypt(keyBytes, data, iv, NO_PADDING);
//    }
    
    public static byte[] sm4EncryptCBC(byte[] keyBytes, byte[] data, byte[] iv, String algo){
        try {
            Key key = new SecretKeySpec(keyBytes, "SM4");
            Cipher in = Cipher.getInstance(algo, "BC"); //
			if(iv == null) iv = zeroIv(algo);
            in.init(Cipher.ENCRYPT_MODE, key, getIV(iv));
            return in.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    public static final String NO_PADDING = "NoPadding";
//    public static final String PKCS7_PADDING = "Pkcs7Padding";
    
    public static final String SM4_ECB_NOPADDING = "SM4/ECB/NoPadding";
    public static final String SM4_CBC_NOPADDING = "SM4/CBC/NoPadding";
    public static final String SM4_CBC_PKCS7PADDING = "SM4/CBC/PKCS7Padding";   
    public static final String SM4_ECB_PKCS5PADDING = "SM4/ECB/PKCS5Padding";   
    
    /**
     * cfca官网CSP沙箱导出的sm2文件
     * @param pem 二进制原文
     * @param pwd 密码
     * @return
     */
    public static Sm2Cert readSm2File(byte[] pem, String pwd){
    	
    	Sm2Cert sm2Cert = new Sm2Cert();
    	try {
	    	ASN1Sequence asn1Sequence = (ASN1Sequence) ASN1Primitive.fromByteArray(pem);
//	    	ASN1Integer asn1Integer = (ASN1Integer) asn1Sequence.getObjectAt(0); //version=1
	    	ASN1Sequence priSeq = (ASN1Sequence) asn1Sequence.getObjectAt(1);//private key
	    	ASN1Sequence pubSeq = (ASN1Sequence) asn1Sequence.getObjectAt(2);//public key and x509 cert
	    	
//	    	ASN1ObjectIdentifier sm2DataOid = (ASN1ObjectIdentifier) priSeq.getObjectAt(0);
//	    	ASN1ObjectIdentifier sm4AlgOid = (ASN1ObjectIdentifier) priSeq.getObjectAt(1);
	    	ASN1OctetString priKeyAsn1 = (ASN1OctetString) priSeq.getObjectAt(2);
	    	byte[] key = KDF(pwd.getBytes(), 32);
	    	byte[] priKeyD = sm4DecryptCBC(Arrays.copyOfRange(key, 16, 32),
	    			priKeyAsn1.getOctets(),
	                Arrays.copyOfRange(key, 0, 16), SM4_CBC_PKCS7PADDING);
	    	sm2Cert.privateKey = getPrivatekeyFromD(new BigInteger(1, priKeyD)); 
//	    	System.out.println(Hex.toHexString(priKeyD));
	    			
//	    	ASN1ObjectIdentifier sm2DataOidPub = (ASN1ObjectIdentifier) pubSeq.getObjectAt(0);
	    	ASN1OctetString pubKeyX509 = (ASN1OctetString) pubSeq.getObjectAt(1);
	    	CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
	    	X509Certificate x509 = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(pubKeyX509.getOctets()));
	    	sm2Cert.publicKey = x509.getPublicKey();
	    	sm2Cert.certId = x509.getSerialNumber().toString();
	    	return sm2Cert;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param cert
     * @return
     */
    public static Sm2Cert readSm2X509Cert(byte[] cert){
    	Sm2Cert sm2Cert = new Sm2Cert();
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
            X509Certificate x509 = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(cert));
            sm2Cert.publicKey = x509.getPublicKey();
	    	sm2Cert.certId = x509.getSerialNumber().toString();
	    	return sm2Cert;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static IvParameterSpec getIV(byte[] iv){
        return new IvParameterSpec(iv);
    }

    public static byte[] zeroIv(final String algo) {

        try {
            Cipher cipher = Cipher.getInstance(algo);
            int blockSize = cipher.getBlockSize();
            byte[] iv = new byte[blockSize];
            Arrays.fill(iv, (byte)0);
            return iv;
        } catch (Exception e) {
        	throw new RuntimeException(e);
        }
    }
	
	/**
	 * 后补0到位数为unitLength的整数倍
	 * @param value 
	 * @return 
	 */
	public static byte[] rightPadZero(byte[] value, final int unitLength){
		if (value.length % unitLength == 0)
			return value;
		int len = (value.length/unitLength + 1) * unitLength;
		return Arrays.copyOf(value, len);
	}

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CertPathBuilderException, InvalidKeyException, SignatureException, CertificateException {

//        // 随便看看 ---------------------
//        System.out.println("GMNamedCurves: ");
//        for(Enumeration e = GMNamedCurves.getNames(); e.hasMoreElements();) {
//            System.out.println(e.nextElement());
//        }
//        System.out.println("sm2p256v1 n:"+x9ECParameters.getN());
//        System.out.println("sm2p256v1 nHex:"+Hex.toHexString(x9ECParameters.getN().toByteArray()));


        // 生成公私钥对 ---------------------
        KeyPair kp = generateKeyPair();

        System.out.println(Hex.toHexString(kp.getPrivate().getEncoded()));
        System.out.println(Hex.toHexString(kp.getPublic().getEncoded()));

        System.out.println(kp.getPrivate().getAlgorithm());
        System.out.println(kp.getPublic().getAlgorithm());

        System.out.println(kp.getPrivate().getFormat());
        System.out.println(kp.getPublic().getFormat());

        System.out.println("private key d: " + ((BCECPrivateKey)kp.getPrivate()).getD());
        System.out.println("public key q:" + ((BCECPublicKey)kp.getPublic()).getQ()); //{x, y, zs...}

        byte[] msg = "message digest".getBytes();
        byte[] userId = "userId".getBytes();
        byte[] sig = signSm3WithSm2(msg, userId, kp.getPrivate());
        System.out.println(Hex.toHexString(sig));
        System.out.println(verifySm3WithSm2(msg, userId, sig, kp.getPublic()));


//        // 由d生成私钥 ---------------------
//        BigInteger d = new BigInteger("097b5230ef27c7df0fa768289d13ad4e8a96266f0fcb8de40d5942af4293a54a", 16);
//        BCECPrivateKey bcecPrivateKey = getPrivatekeyFromD(d);
////        System.out.println(bcecPrivateKey.getParameters());
////        System.out.println(Hex.toHexString(bcecPrivateKey.getEncoded()));
////        System.out.println(bcecPrivateKey.getAlgorithm());
////        System.out.println(bcecPrivateKey.getFormat());
////        System.out.println(bcecPrivateKey.getD());
////        System.out.println(bcecPrivateKey instanceof java.security.interfaces.ECPrivateKey);
////        System.out.println(bcecPrivateKey instanceof ECPrivateKey);
////        System.out.println(bcecPrivateKey.getParameters());
////

////        公钥X坐标PublicKeyXHex: 59cf9940ea0809a97b1cbffbb3e9d96d0fe842c1335418280bfc51dd4e08a5d4
////        公钥Y坐标PublicKeyYHex: 9a7f77c578644050e09a9adc4245d1e6eba97554bc8ffd4fe15a78f37f891ff8
//        PublicKey publicKey = getPublickeyFromX509File(new File("/Users/xxx/Downloads/xxxxx.cer"));
//        System.out.println(publicKey);
////        PublicKey publicKey1 = getPublickeyFromXY(new BigInteger("59cf9940ea0809a97b1cbffbb3e9d96d0fe842c1335418280bfc51dd4e08a5d4", 16), new BigInteger("9a7f77c578644050e09a9adc4245d1e6eba97554bc8ffd4fe15a78f37f891ff8", 16));
////        System.out.println(publicKey1);
////        System.out.println(publicKey.equals(publicKey1));
////        System.out.println(publicKey.getEncoded().equals(publicKey1.getEncoded()));
//


//        // sm2 encrypt and decrypt test ---------------------
//        KeyPair kp = generateKeyPair();
//        PublicKey publicKey2 = kp.getPublic();
//        PrivateKey privateKey2 = kp.getPrivate();
//        byte[]bs = sm2Encrypt("s".getBytes(), publicKey2);
//        System.out.println(Hex.toHexString(bs));
//        bs = sm2Decrypt(bs, privateKey2);
//        System.out.println(new String(bs));



//        // sm4 encrypt and decrypt test ---------------------
//        //0123456789abcdeffedcba9876543210 + 0123456789abcdeffedcba9876543210 -> 681edf34d206965e86b3e94f536e4246
//        byte[] plain = Hex.decode("0123456789abcdeffedcba98765432100123456789abcdeffedcba98765432100123456789abcdeffedcba9876543210");
//        byte[] key = Hex.decode("0123456789abcdeffedcba9876543210");
//        byte[] cipher = Hex.decode("595298c7c6fd271f0402f804c33d3f66");
//        byte[] bs = sm4Encrypt(key, plain);
//        System.out.println(Hex.toHexString(bs));;
//        bs = sm4Decrypt(key, bs);
//        System.out.println(Hex.toHexString(bs));
      
        String sm2 = "MIIDHQIBATBHBgoqgRzPVQYBBAIBBgcqgRzPVQFoBDDW5/I9kZhObxXE9Vh1CzHdZhIhxn+3byBU\nUrzmGRKbDRMgI3hJKdvpqWkM5G4LNcIwggLNBgoqgRzPVQYBBAIBBIICvTCCArkwggJdoAMCAQIC\nBRA2QSlgMAwGCCqBHM9VAYN1BQAwXDELMAkGA1UEBhMCQ04xMDAuBgNVBAoMJ0NoaW5hIEZpbmFu\nY2lhbCBDZXJ0aWZpY2F0aW9uIEF1dGhvcml0eTEbMBkGA1UEAwwSQ0ZDQSBURVNUIFNNMiBPQ0Ex\nMB4XDTE4MTEyNjEwMTQxNVoXDTIwMTEyNjEwMTQxNVowcjELMAkGA1UEBhMCY24xEjAQBgNVBAoM\nCUNGQ0EgT0NBMTEOMAwGA1UECwwFQ1VQUkExFDASBgNVBAsMC0VudGVycHJpc2VzMSkwJwYDVQQD\nDCAwNDFAWnRlc3RAMDAwMTAwMDA6U0lHTkAwMDAwMDAwMTBZMBMGByqGSM49AgEGCCqBHM9VAYIt\nA0IABDRNKhvnjaMUShsM4MJ330WhyOwpZEHoAGfqxFGX+rcL9x069dyrmiF3+2ezwSNh1/6YqfFZ\nX9koM9zE5RG4USmjgfMwgfAwHwYDVR0jBBgwFoAUa/4Y2o9COqa4bbMuiIM6NKLBMOEwSAYDVR0g\nBEEwPzA9BghggRyG7yoBATAxMC8GCCsGAQUFBwIBFiNodHRwOi8vd3d3LmNmY2EuY29tLmNuL3Vz\nL3VzLTE0Lmh0bTA4BgNVHR8EMTAvMC2gK6AphidodHRwOi8vdWNybC5jZmNhLmNvbS5jbi9TTTIv\nY3JsNDI4NS5jcmwwCwYDVR0PBAQDAgPoMB0GA1UdDgQWBBREhx9VlDdMIdIbhAxKnGhPx8FcHDAd\nBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwQwDAYIKoEcz1UBg3UFAANIADBFAiEAgWvQi3h6\niW4jgF4huuXfhWInJmTTYr2EIAdG8V4M8fYCIBixygdmfPL9szcK2pzCYmIb6CBzo5SMv50Odycc\nVfY6";
    	byte[] bs = Base64.decodeBase64(sm2);
    	String pwd = "cfca1234";
    	Sm2Cert sm2Cert = GmUtil.readSm2File(bs, pwd);
    	System.out.println(sm2Cert.getPublicKey());
    	System.out.println(Hex.toHexString(((BCECPrivateKey)sm2Cert.getPrivateKey()).getD().toByteArray()));
    	System.out.println(sm2Cert.getPrivateKey());
    	System.out.println(sm2Cert.getCertId());
    }
}
