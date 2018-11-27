package cc.royao.commons.utils;

import java.net.URLDecoder;

import cc.royao.commons.CrypConfig;

/**
 * 公共执行方法类
 * @author yangx
 * @className 
 * @date 2016年3月18日上午10:31:31
 */
public class MainUtils {
	
//	public static void main(String[] args) {
//		String inputStr = "liujingping";
//		String key;
//		try {
//			// key = CryptoUtil.initDesKey();
//			key = CrypConfig.get("ACCOUNT_KEY");
//			System.out.println("原文:" + inputStr);
//			System.out.println("密钥:" + key);
//			
//			//初始化字节
//			byte[] inputData = inputStr.getBytes();
//			//进行加密处理
//			inputData = CryptoUtil.encrypt(inputData, key);
//
//			System.out.println("加密后:" +inputData);
//			//解密操作
//			byte[] outputData = CryptoUtil.decrypt(inputData, key);
//			String outputStr = new String(outputData);
//			
//			System.out.println("解密后:" + outputStr);
//		
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
	
	
	public static void main(String[] args) {
	String inputStr = "1000";
	String key;
	try {
		// key = CryptoUtil.initDesKey();
		key = CrypConfig.get("ACCOUNT_KEY");
		System.out.println("原文:" + inputStr);
		System.out.println("密钥:" + key);
		
		//初始化字节
		String inputData ="";
		//进行加密处理
		inputData = CryptoUtil.encrypt(inputStr, key);

		System.out.println("加密后:" +inputData);
		//解密操作
		String outputData = CryptoUtil.decrypt(inputData, "123456");
		
		System.out.println("解密后:" + outputData);

		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	

}

}
