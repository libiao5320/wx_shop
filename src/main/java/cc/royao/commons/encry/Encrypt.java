package cc.royao.commons.encry;

public class Encrypt {
    /**
     * 获取MD5加密后的字符，算法不可以逆
     * 
     * @param str
     * @return
     */
    public static String md5(String str) {
        return new EncryMD5().getMD5ofStr(str);
    }

    /**
     * 获取SHA1加密后的字符，算法不可以逆
     * 
     * @param str
     * @return
     */
    public static String sha1(String str) {
        return new EncrySHA1().getDigestOfString(str.getBytes());
    }

    /**
     * 获取DES加密后的字符，此加密方法可逆
     * @param str
     * @return
     * @throws Exception
     */
    public static String encodeDES(String str, String secret) {
        try {
            return new EncryDES(secret).encrypt(str).toString();
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     *  获取DES解密后的字符，此加密方法可逆
     * @param str
     * @return
     * @throws Exception
     */
    public static String decodeDES(String str, String secret) {
        try {
            return new EncryDES(secret).decrypt(str).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Encrypt.encodeDES("admin","aCa2szq9"));
        System.out.println(Encrypt.decodeDES("422ebe9d706e75dc","aCa2szq9"));
    }
}
