package cc.royao.commons.freemarker;

import java.io.Writer;

import org.apache.log4j.Logger;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * Freemarker异常处理类
 * 
 * @author Administrator
 *
 */
public class FreemarkerExceptionHandler implements TemplateExceptionHandler {

	private static final Logger log = Logger.getLogger(FreemarkerExceptionHandler.class);

	public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException {
		try {
			out.write("[Freemarker Error: " + te.getMessage() + "]");
			log.warn("Freemarker Error: ", te);
		} catch (Exception e) {
			log.warn(e.getMessage());
			throw new TemplateException("Failed to print error message. Cause: " + e, env);
		}
	}

}
