package cc.royao.commons.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class ExceptionResolver extends SimpleMappingExceptionResolver {
	
	private Log errLogger;
	
	public void setErrorLogCategory(String loggerName) {
		this.errLogger = LogFactory.getLog(loggerName);
	}

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		String viewName = determineViewName(ex, request);
		if (viewName != null) {
			Integer statusCode = determineStatusCode(request, viewName);
			
			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode);
				if(statusCode!=403 && statusCode!=404 && statusCode !=200 &&
						this.errLogger != null && this.errLogger.isErrorEnabled()){
					this.errLogger.error(buildLogMessage(ex, request), ex);
				}
			}
			return getModelAndView(viewName, ex, request);
		}else {
			return null;
		}
	}
	
	protected String buildLogMessage(Exception ex, HttpServletRequest request) {
		return "系统出现异常了："+ex.getMessage();
	}
}
