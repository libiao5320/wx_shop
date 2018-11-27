package cc.royao.commons;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.apache.log4j.Logger;
/**
 * weixin.properties
 * @author Zhiqiyun-liqi
 *
 */
public class WeixinConfig {
	private final static Logger logger = Logger.getLogger(Config.class);

    /**
     * 资源文件名称
     */
    private final static String WEIXIN_PROPERTY_FILENAME = "weixin.properties";
    
    private static Properties properties;
    
    static {
        try {
            loadConfig();



        } catch (IOException e) {
            throw new IllegalStateException("load message resource " + WEIXIN_PROPERTY_FILENAME + " error", e);
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
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(WEIXIN_PROPERTY_FILENAME);
        if (is != null) {
            logger.info("load message file " + WEIXIN_PROPERTY_FILENAME + " from current thread class loader context");
            return is;
        }

        logger.info("cannot load message file " + WEIXIN_PROPERTY_FILENAME + " from current thread class loader context");

        // 从系统类加载器中加载资源
        is = ClassLoader.getSystemResourceAsStream(WEIXIN_PROPERTY_FILENAME);
        if (is != null) {
            logger.info("load message file " + WEIXIN_PROPERTY_FILENAME + " from system class loader context");
            return is;
        }
        throw new IllegalStateException("cannot find " + WEIXIN_PROPERTY_FILENAME + " anywhere");
    }
}
