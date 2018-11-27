package cc.royao.commons.utils;

import java.security.SecureRandom;
import java.util.Random;

import cc.royao.commons.weixinpay.TenpayUtil;

public class RandCode {
    /**
     * 生成随机码
     * @param len   长度
     * @param isNumber 是否为全数字
     * @return
     */
    public static String get(int len, boolean isNumber) {
        String[] codes = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
        Random random = new SecureRandom();
        String code = "";
        for (int i = 0; i < len; i++) {
            if (isNumber) {
                code += codes[random.nextInt(9)];
            } else {
                code += codes[random.nextInt(61)];
            }
        }
        return code;
    }
    
    /**
     * 生成订单号
     * @return
     */
    public static String getOrderNum() {
		int r1 = (int) (Math.random() * (10));// 产生2个0-9的随机数
		int r2 = (int) (Math.random() * (10));
		long now = System.currentTimeMillis();// 一个13位的时间戳
		String paymentID = String.valueOf(r1) + String.valueOf(r2) + String.valueOf(now);// 订单ID
		return paymentID;
	}
    
    /**
	 * 获取随机字符串
	 * 
	 * @return
	 */
	public static String getNonceStr() {
		// 随机数
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		return strTime + strRandom;
	}
}
