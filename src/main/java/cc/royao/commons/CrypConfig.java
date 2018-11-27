package cc.royao.commons;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.apache.log4j.Logger;
/**
 * 
 * @author yangx
 * @className  密码配置文件
 * @date 2016年3月18日上午10:47:51
 */
public class CrypConfig {
	private final static Logger logger = Logger.getLogger(Config.class);

    /**
     * 资源文件名称
     */
    private final static String CRYP_PROPERTY_FILENAME = "cryp.properties";
    
    private static Properties properties;
    
    static {
        try {
            loadConfig();



        } catch (IOException e) {
            throw new IllegalStateException("load message resource " + CRYP_PROPERTY_FILENAME + " error", e);
        }
    }

    public static String get(String name) {
        String message = properties.getProperty(name);
//        if (args == null || args.length == 0) {
            return message;
//        }
//        return MessageFormat.format(message, args);
    }

    private static void loadConfig() throws IOException {
        if (properties != null) {
            return;
        }
        properties = new Properties();
        properties.load(getInputStream());
    }

    /**
     * 加载资源文件
     * @return
     */
    private static InputStream getInputStream() {

        // 从当前类加载器中加载资源
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(CRYP_PROPERTY_FILENAME);
        if (is != null) {
            logger.info("load message file " + CRYP_PROPERTY_FILENAME + " from current thread class loader context");
            return is;
        }

        logger.info("cannot load message file " + CRYP_PROPERTY_FILENAME + " from current thread class loader context");

        // 从系统类加载器中加载资源
        is = ClassLoader.getSystemResourceAsStream(CRYP_PROPERTY_FILENAME);
        if (is != null) {
            logger.info("load message file " + CRYP_PROPERTY_FILENAME + " from system class loader context");
            return is;
        }
        throw new IllegalStateException("cannot find " + CRYP_PROPERTY_FILENAME + " anywhere");
    }
}
