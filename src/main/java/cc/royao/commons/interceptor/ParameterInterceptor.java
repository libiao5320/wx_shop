package cc.royao.commons.interceptor;

import java.security.SecureRandom;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ParameterInterceptor extends HandlerInterceptorAdapter {
    private String version;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Random random = new SecureRandom();
        request.setAttribute("__random", random.nextInt());
        request.setAttribute("__version", this.version);
        request.setAttribute("__server_name", "http://"+request.getServerName() + (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()));
        request.setAttribute("__referer", request.getHeader("referer"));
        return true;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
