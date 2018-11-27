package cc.royao.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class RequestUtils {
	
	/**
	 * 设置COOKIE
	 * 
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name,
			String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		
		String serverName = request.getServerName();
		if(!isIPAddr(serverName)){
			String domain = getDomainOfServerName(serverName);
			if(domain!=null && domain.indexOf('.')!=-1){
				cookie.setDomain('.' + domain);
			}
		}
	
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	/**
	 * 获取用户访问URL中的根域名
	 * 例如: www.pktuan.com -> pktuan.com
	 * @param req
	 * @return
	 */
	public static String getDomainOfServerName(String host){
		if(isIPAddr(host))
			return null;
		String[] names = StringUtils.split(host, '.');
		int len = names.length;
		if(len>=2)
			return names[len-2]+'.'+names[len-1];
		return host;
	}
	
	/**
	 * 判断字符串是否是一个IP地址
	 * 
	 * @param addr
	 * @return
	 */
	public static boolean isIPAddr(String addr) {
		if (StringUtils.isEmpty(addr))
			return false;
		String[] ips = StringUtils.split(addr, '.');
		if (ips.length != 4)
			return false;
		try {
			int ipa = Integer.parseInt(ips[0]);
			int ipb = Integer.parseInt(ips[1]);
			int ipc = Integer.parseInt(ips[2]);
			int ipd = Integer.parseInt(ips[3]);
			return ipa >= 0 && ipa <= 255 && ipb >= 0 && ipb <= 255 && ipc >= 0
					&& ipc <= 255 && ipd >= 0 && ipd <= 255;
		} catch (Exception e) {
		}
		return false;
	}

}
