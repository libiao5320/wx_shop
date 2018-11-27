package cc.royao.commons.interceptor;

import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.mail.Session;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




import cc.royao.commons.auth.*;
import cc.royao.commons.httpClient.HttpUtil;
import cc.royao.commons.httpClient.RequestContent;
import cc.royao.commons.httpClient.ResponseContent;
import cc.royao.commons.logger.SysLogger;

import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleIfStatement;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cc.royao.util.RequestUtils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class AnnotationInterceptor extends HandlerInterceptorAdapter {
    private String loginUrl;

    @SysLogger
    private Logger logger ;

    @Autowired
    private AuthService authService;
    @Autowired
    private WxAuthUtil wxAuthUtil;

//
//    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        //微信校驗
        //wxAuthUtil.checkUtil(request,response,null);
        Map snsUserInfo  = (Map) session.getAttribute("snsUserInfo");

        AuthUser auth = new AuthUser();

        if( snsUserInfo != null ) {
//            AuthUser auth = new AuthUser();
            if (this.authService.getAuth().getId() == null) {
                RequestContent content = new RequestContent();
                Map paramMap = new HashMap();
                String urlPath = "/member/getByWxCode.htm";
                paramMap.put("wxCode", snsUserInfo.get("openId"));
                content.setBody(paramMap);
                ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath, content, "utf-8");
                if (null != responseContent.getBody()) {
                    JSONObject result = (JSONObject) responseContent.getBody();
                    logger.info("============================================");
                    logger.info("GET USER ID >>>>>>>>>>>>>>>>>>>>>>>>>>>" + snsUserInfo.get("openId"));
                    logger.info("============================================");
                    if (null != result) {
                        auth.setId(Long.valueOf("" + result.get("memberId")));
                        auth.setVipRankId(Long.valueOf("" + result.get("vipRankId")));


                        /**
                         * 将荣连信息放入Cookie
                         */
                        JSONObject jsonObject  = new JSONObject();
                        jsonObject.put("userId",result.get("memberId"));
                        jsonObject.put("name",result.get("memberName"));
                        jsonObject.put("openid", result.get("wxTokenId"));
                        jsonObject.put("photo", result.get("memberAvatar"));
                        jsonObject.put("phone",result.get("memberMobile") );
                        jsonObject.put("rl_acc",result.get("rlAcc"));
                        jsonObject.put("rl_acc_passwd",result.get("rlAccPasswd"));
                        jsonObject.put("rl_voip",result.get("rlVoip"));
                        jsonObject.put("rl_voip_passwd",result.get("rlVoipPasswd"));
                        jsonObject.put("sex",result.get("memberSex"));
                        jsonObject.put("birthday",result.get("memberTime"));
                        jsonObject.put("focus_office","");
                        jsonObject.put("info_1","1,2,");
                        jsonObject.put("info_2","1,10,66,");
                        jsonObject.put("info_3","\\u671b\\u57ce\\u53bf\\u4eba\\u6c11\\u533b\\u9662,,");
                        jsonObject.put("info_4","1,");
                        jsonObject.put("info_5","1,");
                        jsonObject.put("info_6","1");
                        jsonObject.put("info_7","1");
                        jsonObject.put("info_8","1,2,");
                        jsonObject.put("info_9","1,2,");
                        jsonObject.put("info_10","\\u53d1");
                        jsonObject.put("info_11","1,2,");
                        jsonObject.put("info_12","1");
                        jsonObject.put("is_full_healthinfo","1");
                        
                        //刘金平加入
                        Cookie cookie = new Cookie("java_token",jsonObject.toString());
                        String serverName = request.getServerName();
                		if(!RequestUtils.isIPAddr(serverName)){
                			String domain = RequestUtils.getDomainOfServerName(serverName);
                			if(domain!=null && domain.indexOf('.')!=-1){
                				cookie.setDomain('.' + domain);
                			}
                		}
                		cookie.setPath("/");
                        response.addCookie(cookie);

//                    auth.setId(Long.valueOf("38"));
//                    auth.setWxCode("owTHvt4CEOAgQDfte3lHJwCSF-qg");
//                    auth.setMemberName("彪");


                    } else {
                        logger.info("============================================");
                        logger.info("ERROR : GET USER INFO >>>>>>>>>>>>>>>>>>>>>>>>>>> NULL");
                        logger.info("============================================");
                    }
                }


                auth.setWxCode((String) snsUserInfo.get("openId"));
                auth.setMemberName((String) snsUserInfo.get("nickName"));
                this.authService.setAuth(auth);
            }
            else
            {
                auth = this.authService.getAuth();
                this.authService.setAuth(auth);
            }


        }


        	/***本地测试数据***/
        /**
        AuthUser auth = new AuthUser();
                            auth.setId(Long.valueOf("165"));
                    auth.setWxCode("owTHvt0SNunPhyOuSpzwvKRHaL94");
                    auth.setMemberName("杨星");
                    auth.setVipRankId(2L);

        this.authService.setAuth(auth);
**/
        return true;


    }


//
    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
}
