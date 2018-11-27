package cc.royao.commons.utils;

import java.net.URLDecoder;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 字符串加密公共类
 * @author yangx
 * @className 
 * @date 2016年1月7日下午4:36:54
 */
public class CryptoUtil {
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";
	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";
	public static final String KEY_MAC = "HmacMD5";
	public static final String ALGORITHM = "DES";
	private static byte[] IV_KEY = {(byte)0x12, (byte)0x34, (byte)0x56, (byte)0x78, (byte)0x90, (byte)0xAB, (byte)0xCD, (byte)0xEF};
	
	
	
	
	public static void main(String[] args) {
		String inputStr = "liujingping";
		String key;
		try {
			// key = CryptoUtil.initDesKey();
//			key = "12345678";
//			System.out.println("原文:" + inputStr);
//
//			System.out.println("密钥:" + key);
//
//			byte[] inputData = inputStr.getBytes();
//			inputData = CryptoUtil.encrypt(inputData, key);
//
//			System.out.println("加密后:" + CryptoUtil.encryptBASE64(inputData));
//			System.out.println("加密后:" + CryptoUtil.encrypt(inputStr,key));
//			System.out.println("BASE64:"
//					+ CryptoUtil.encryptBASE64(inputStr.getBytes()));
//
//			byte[] outputData = CryptoUtil.decrypt(inputData, key);
//			String outputStr = new String(outputData);
//
//			System.out.println("解密后:" + outputStr);
//			String des = "xE9QOcy3IZi%2BceKj0EPEiBfyFQirkkm6RnrtMLMpkjGJw0Sl0IKgH4kuyVFuAW4AcRhs6pLIVDhk%0A1VjUWitn5w%3D%3D%0A";
//			System.out.println("解密后:" + CryptoUtil.decrypt(URLDecoder.decode(des), key));
			
	
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	public static byte[] encryptMD5(byte[] data) throws Exception {

		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);

		return md5.digest();

	}

	/**
	 * SHA加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {

		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);

		return sha.digest();

	}

	/**
	 * 初始化HMAC密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String initMacKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);

		SecretKey secretKey = keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * HMAC加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {

		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);

		return mac.doFinal(data);

	}

	/**
	 * 转换密钥
	 * 
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception {
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(dks);

		// 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
		// SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);

		return secretKey;
	}

	/**
	 * 解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, String key) throws Exception {
		// Key k = toKey(decryptBASE64(key));
		Key k = toKey(key.getBytes());
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);

		return cipher.doFinal(data);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, String key) throws Exception {
		// Key k = toKey(decryptBASE64(key));
		Key k = toKey(key.getBytes());
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);

		return cipher.doFinal(data);
	}

	public static String encrypt(String message, String key) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(IV_KEY);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		return encryptBASE64(cipher.doFinal(message.getBytes("UTF-8")));
	}

	public static String decrypt(String message, String key) throws Exception {

		byte[] bytesrc = decryptBASE64(message);// convertHexString(message);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(IV_KEY);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		byte[] retByte = cipher.doFinal(bytesrc);
		return new String(retByte);
	}

	public static byte[] convertHexString(String ss) {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}

		return digest;
	}

	public static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}

		return hexString.toString();
	}

	/**
	 * 生成密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String initDesKey() throws Exception {
		return initDesKey(null);
	}

	/**
	 * 生成密钥
	 * 
	 * @param seed
	 * @return
	 * @throws Exception
	 */
	public static String initDesKey(String seed) throws Exception {
		SecureRandom secureRandom = null;

		if (seed != null) {
			secureRandom = new SecureRandom(decryptBASE64(seed));
		} else {
			secureRandom = new SecureRandom();
		}

		KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
		kg.init(secureRandom);

		SecretKey secretKey = kg.generateKey();

		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * 用私钥对信息生成数字签名 RSA
	 * 
	 * @param data
	 *            加密数据
	 * @param privateKey
	 *            私钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		// 解密由base64编码的私钥
		byte[] keyBytes = decryptBASE64(privateKey);

		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// 取私钥匙对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);

		return encryptBASE64(signature.sign());
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * 
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 * 
	 */
	public static boolean verify(byte[] data, String publicKey, String sign)
			throws Exception {

		// 解密由base64编码的公钥
		byte[] keyBytes = decryptBASE64(publicKey);

		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// 取公钥匙对象
		PublicKey pubKey = keyFactory.generatePublic(keySpec);

		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);

		// 验证签名是否正常
		return signature.verify(decryptBASE64(sign));
	}

	/**
	 * 解密
	 * 
	 * 用私钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String key)
			throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(key);

		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * 解密
	 * 
	 * 用私钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String key)
			throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(key);

		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * 加密
	 * 
	 * 用公钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String key)
			throws Exception {
		// 对公钥解密
		byte[] keyBytes = decryptBASE64(key);

		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * 加密
	 * 
	 * 用私钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String key)
			throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(key);

		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		return cipher.doFinal(data);
	}

	/**
	 * 取得私钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);

		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 取得公钥
	 * 
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);

		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 初始化密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator
				.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);

		KeyPair keyPair = keyPairGen.generateKeyPair();

		// 公钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// 私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		Map keyMap = new HashMap(2);

		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

}
