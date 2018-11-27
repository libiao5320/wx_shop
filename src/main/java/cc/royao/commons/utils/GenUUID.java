package cc.royao.commons.utils;



import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

/**
 * 
 * Created on 2014年4月12日
 * <p>Copyright:   Copyright (c) 2014</p>
 * @version        1.0
 */
public class GenUUID {
    public static void main(String[] agrs){
        System.out.println(get());
    }
    /** 
     * 获得一个UUID 
     * @return String UUID 
     */
    public static String get() {
        try {
            // Generate a byte array containing a session identifier
            Random random = new SecureRandom(); // 取随机数发生器, 默认是SecureRandom
            byte[] bytes = new byte[64];

            random.nextBytes(bytes); // 产生SESSION_ID_BYTES字节的byte

            bytes = MessageDigest.getInstance("SHA").digest(bytes); // 取摘要,默认是"MD5"算法

            // Render the result as a String of hexadecimal digits
            StringBuffer result = new StringBuffer();

            for (int i = 0; i < bytes.length; i++) { // 转化为16进制字符串

                byte b1 = (byte) ((bytes[i] & 0xf0) >> 4);

                byte b2 = (byte) (bytes[i] & 0x0f);

                if (b1 < 10)
                    result.append((char) ('0' + b1));
                else
                    result.append((char) ('a' + (b1 - 10)));

                if (b2 < 10)
                    result.append((char) ('0' + b2));
                else
                    result.append((char) ('a' + (b2 - 10)));
            }
            return (result.toString());
        } catch (Exception e) {
            return null;
        }
    }
}
