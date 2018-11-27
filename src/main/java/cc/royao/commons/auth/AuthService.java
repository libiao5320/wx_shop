package cc.royao.commons.auth;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cc.royao.commons.encry.Encrypt;
import cc.royao.commons.utils.Validate;

public class AuthService {

	public Integer LOGIN_EXPIRE_TIME;

	public String LOGIN_COOKIE_SID;

	public String LOGIN_PLATFORM;

	public String LOGIN_COOKIE_DOMAIN;
	
	
	public void setLOGIN_EXPIRE_TIME(Integer LOGIN_EXPIRE_TIME) {
		this.LOGIN_EXPIRE_TIME = LOGIN_EXPIRE_TIME;
	}

	public void setLOGIN_COOKIE_SID(String LOGIN_COOKIE_SID) {
		this.LOGIN_COOKIE_SID = LOGIN_COOKIE_SID;
	}

	public void setLOGIN_PLATFORM(String lOGIN_PLATFORM) {
		LOGIN_PLATFORM = lOGIN_PLATFORM;
	}

	public void setLOGIN_COOKIE_DOMAIN(String lOGIN_COOKIE_DOMAIN) {
		LOGIN_COOKIE_DOMAIN = lOGIN_COOKIE_DOMAIN;
	}
	
	public <T> String setExtend(String sid, T object) {
        return sid;
    }
	
	public void setAuthorities(String sid, String authorities) {
//      System.out.println("LOGIN_SIGN:" + sid + ":authorities:" + LOGIN_PLATFORM);
  }

	public String getUserCookieSid() {

		HttpServletRequest request = this.getRequest();
		String value = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {

			for (Cookie c : cookies) {

				if (c.getName().equalsIgnoreCase(LOGIN_COOKIE_SID)) {
					value = c.getValue();
					return value;
				}
			}
		}
		return value;
	}
	
	public String setAuth(String sid, AuthUser authUser) {
        if (authUser.getData() != null) {
            this.setExtend(sid, authUser.getData());
            authUser.setData(null);
        }


        this.setAuthorities(sid, authUser.getAuthorities());

        authUser.setSid(sid);
        return sid;
    }

	
	/**
	 * 生成用户密码
	 * 
	 * @param loginPwd
	 *            用户登录使用的密码，是经过 sha1加密过的
	 * @return
	 */
	public String genPwd(String loginPwd, String secret, boolean needSha1) {
		if (loginPwd == null) {
			return "";
		}
		if (needSha1) {
			loginPwd = Encrypt.sha1(loginPwd);
		}
		secret = Encrypt.md5(secret.toUpperCase());
		return Encrypt.md5(loginPwd.toUpperCase() + "@" + secret);
	}

	public void setAuth(AuthUser authUser) {
		this.getSession().setAttribute("LOGIN_SIGN", authUser);
	}

	/**
	 * 获取登录用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public AuthUser getAuth() {
		AuthUser authUser = (AuthUser) this.getSession().getAttribute("LOGIN_SIGN");
		if (authUser == null) {
			authUser = new AuthUser(null, "Anonymous", "匿名用户", null, this.getIPAddr(), null);
		}
		return authUser;
	}

	public boolean isAuth() {
		boolean auth = false;

		AuthUser authUser = this.getAuth();
		if (authUser != null && authUser.getId() != null) {
			auth = true;
		}
		return auth;
	}

	/**
	 * 销毁登录用户信息
	 * 
	 * @throws Exception
	 */

	public void destroyAuth() {

		this.getSession().setAttribute("LOGIN_SIGN", null);

	}

	/**
	 * 将验证码写入到缓存中
	 * 
	 * @param captcha
	 */
	public void setCaptcha(String captcha) {
		//com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY
		this.getSession().setAttribute("com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY", captcha);

	}

	/**
	 * 比较验证码
	 * 
	 * @param sRand
	 */
	public boolean compareCaptcha(String captcha) {
		//com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY
		String sRand = (String) this.getSession().getAttribute("com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY");

		return captcha != null && sRand != null && captcha.trim().toLowerCase().equals(sRand.trim().toLowerCase());
	}

	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	public HttpSession getSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getSession();
	}

	/**
	 * Created on 2014-9-4
	 * <p>
	 * Discription:获取访问用户的IP地址
	 * </p>
	 * 
	 * @author:[创建者中文名字]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return
	 */
	public String getIPAddr() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String ip = request.getHeader("X-Real-IP");
		if (!Validate.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!Validate.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}
}
