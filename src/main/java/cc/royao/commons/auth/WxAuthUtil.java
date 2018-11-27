package cc.royao.commons.auth;

import cc.royao.commons.CConfig;
import cc.royao.commons.logger.SysLogger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ntz on 2015/11/26.
 */
public class WxAuthUtil {


	//   private static final String __TOKEN__ = "beiduo";
	//   private static final String __APPID__ = "wx207ed4f9c91308ec";
	//   private static final String __SECRET__ = "9307df26ad62072bb634df670849dcb2";
	//   private static final String __OAUTHURL__ = "http://w.beiduo.cc/index.htm";


    //公众号
    private static final String __TOKEN__ = CConfig.TOKEN;
    private static final String __APPID__ = CConfig.APPID;
    private static final String __SECRET__ = CConfig.SECRET;
    private static final String __OAUTHURL__ = CConfig.OAUTHURL;


    @SysLogger
    private Logger logger;

    

    /**
     * 检查微信信息
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public  boolean checkSignature(String signature, String timestamp, String nonce) {

        String token = __TOKEN__;
        String[] paramArr = new String[]{token, timestamp, nonce};
        Arrays.sort(paramArr);

        String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);

        String ciphertext = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            ciphertext = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // sha1鎻
        return ciphertext != null ? ciphertext.equals(signature.toUpperCase()) : false;
    }

    /**
     * 浜岃繘鍒惰浆String
     *
     * @param byteArray
     * @return
     */
    private  String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }


    /**
     * ?????????????????????
     *
     * @param mByte
     * @return
     */
    private  String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }


    public  String processRequest(HttpServletRequest request) {
        // xml????????????
        String respXml = "???";
//        try {
//            // ????parseXml???????????????
//            Map<String, String> requestMap = parseXml(request);
//            // ????????
//            String fromUserName = requestMap.get("FromUserName");
//            // ??????????
//            String toUserName = requestMap.get("ToUserName");
//            // ???????
//            String msgType = requestMap.get("MsgType");
//            String eventKey = requestMap.get("EventKey");
//            TbWxTextMessage textMessage = new TbWxTextMessage();
//            textMessage.setToUserName(fromUserName);
//            textMessage.setFromUserName(toUserName);
//            textMessage.setCreateTime(new Date().getTime());
//            textMessage.setMsgType(WxCommonUtil.RESP_MESSAGE_TYPE_TEXT);
//            // ???????
//            if (msgType.equals(WxCommonUtil.REQ_MESSAGE_TYPE_EVENT)) {
//                // ???????
//                String eventType = requestMap.get("Event");
//                // ????
//                if (eventType.equals(WxCommonUtil.EVENT_TYPE_SUBSCRIBE)) {
//                    textMessage.setContent("??????????????????\r\r\n"+"<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd344f7727524a314&redirect_uri=http%3A%2F%2Fwx.haoxihuan.wang%2Fhxh%2FindexAction%21init.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\">"+ "??????"+"</a>\r\n"+"<a href=\"tel:4000999159\">????锟斤拷??4000999159</a>");
//                    // ??????????????xml
//                    respXml = WxCommonUtil.messageToXml(textMessage);
//                }else if("SHOW_PHONE".equals(eventKey)){
////                    textMessage.setContent("??????????????????\r\r\n\n"+"<a href=\"tel:4000999159\">????锟斤拷??4000999159</a>");
//                    textMessage.setContent("??????????????????\r\r\n"+"<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd344f7727524a314&redirect_uri=http%3A%2F%2Fwx.haoxihuan.wang%2Fhxh%2FindexAction%21init.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect\">"+ "??????"+"</a>\r\n"+"<a href=\"tel:4000999159\">????锟斤拷??4000999159</a>");
//
//                    // ??????????????xml
//                    respXml = WxCommonUtil.messageToXml(textMessage);
//                }
//     /*           else if("NEW_ADVICE".equals(eventKey)){
//                    List<TbWxArticle> articleList = new ArrayList<TbWxArticle>();
//                    TbWxNewsMessage newsMessage = new TbWxNewsMessage();
//                    newsMessage.setToUserName(fromUserName);
//                    newsMessage.setFromUserName(toUserName);
//                    newsMessage.setCreateTime(new Date().getTime());
//                    newsMessage.setMsgType(WxCommonUtil.RESP_MESSAGE_TYPE_NEWS);
//                    //newsMessage.setFuncFlag(0);
//                    TbWxArticle tbWxArticle = new TbWxArticle();
//                    tbWxArticle.setTitle("????????????");
//                    tbWxArticle.setDescription("???????????????");
//                    tbWxArticle.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
//                    tbWxArticle.setUrl("http://blog.csdn.net/lyq8479");
//                    articleList.add(tbWxArticle);
//                    // ??????????????
//                    newsMessage.setArticleCount(articleList.size());
//                    // ??????????????????????
//                    newsMessage.setTbWxArticles(articleList);
//                    // ?????????????????xml?????
//                   // respMessage = WxCommonUtil.messageToXml(newsMessage);
//                }*/
//              /*  // ???????
//                else if (eventType.equals(WxCommonUtil.EVENT_TYPE_UNSUBSCRIBE)) {
//                    textMessage.setContent("???????锟斤拷?????????????????????");
//                    // ??????????????xml
//                    respXml = WxCommonUtil.messageToXml(textMessage);
//                }*/
//             /*   // ?????????????
//                else if (eventType.equals(WxCommonUtil.EVENT_TYPE_CLICK)) {
//                    // ???KEY?????????????key????
//                    String eventKey = requestMap.get("EventKey");
//
//                    // ????key??锟斤拷???????????
//                    if (eventKey.equals("?????")) {
//                      textMessage.setContent("???????????????4000-999-159??");
//                    // ??????????????xml
//                    respXml = WxCommonUtil.messageToXml(textMessage);
//                    }
//                }*/
//            }
//            // ???????????
//            else {
//                String msg = "??????????????????\r\r\n"+"<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd344f7727524a314&redirect_uri=http%3A%2F%2Fwx.haoxihuan.wang%2Fhxh%2FindexAction%21init.action&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect'>"+"??????"+"</a>";
//                textMessage.setContent(msg);
//                respXml = messageToXml(textMessage);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return respXml;
    }


    private  Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // ??????????锟斤拷??HashMap??
        Map<String, String> map = new HashMap<String, String>();

        // ??????
        InputStream inputStream = request.getInputStream();
        // ???SAX??????????
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // ???xml?????
        Element root = document.getRootElement();
        // ?????????????????
        List<Element> elementList = root.elements();

        // ????????????
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }

        // ??????
        inputStream.close();
        inputStream = null;

        return map;
    }

    /**
     * 检查微信 拦截器
     * @param request
     * @param response
     * @param url
     * @throws Exception
     */
    public  void checkUtil(HttpServletRequest request ,HttpServletResponse response, String url)throws Exception
    {

        JSONObject result = new JSONObject();

        HttpSession session = request.getSession();

        // 鑾峰彇鐢ㄦ埛寰俊code
        String code = request.getParameter("code");


        logger.info("code==**********************=========" + code);
        String codeAtribute = "";
        if (null == request.getSession().getAttribute("code")) {
            codeAtribute = "";
        } else {
            codeAtribute = request.getSession().getAttribute("code").toString();
            System.out.println("codeAtribute===========" + codeAtribute);
        }


        //???session锟斤拷????????????????????????
        if (code== null && session.getAttribute("snsUserInfo") ==null) {




            StringBuilder oauth_url = new StringBuilder();
            oauth_url.append("https://open.weixin.qq.com/connect/oauth2/authorize?");
            oauth_url.append("appid=").append(__APPID__);
//
            if( StringUtils.isNotBlank(url) ){
            	oauth_url.append("&redirect_uri=").append(urlEncodeUTF8(url));
            }else
            	oauth_url.append("&redirect_uri=").append(urlEncodeUTF8(__OAUTHURL__));
            oauth_url.append("&response_type=code");
            oauth_url.append("&scope=snsapi_userinfo");
            oauth_url.append("&state=1#wechat_redirect");
            System.out.println("oauth_url.toString():" + oauth_url.toString());

            logger.info("============================================");
            logger.info(" 鑾峰彇寰俊璁よ瘉 >>>>>>>>>>>>>>>>>>>>>>>>>>>" + code);
            logger.info("============================================");


            response.sendRedirect( oauth_url.toString() ) ;



        }
        //????????????????????session??????????OAUTH???????????//
        //  if ((code !=null && session.getAttribute("snsUserInfo") == null)&& codeAtribute.equals(code) == false) {
        if ( code != null && codeAtribute.equals(code) == false) {


        	logger.info("------__APPID__-----------"+__APPID__);
        	logger.info("------__SECRET__-----------"+__SECRET__);

            Map oAuthMap = getOauth2AccessToken(__APPID__,__SECRET__ , code);

           

            String accessToken = (String) oAuthMap.get("accessToken");
            String openId = (String) oAuthMap.get("openId");
            Map snsUserInfo = getSNSUserInfo(accessToken, openId);
            session.setAttribute("snsUserInfo", snsUserInfo);
            session.setAttribute("code", code);


//            WxCommonUtil.getJSConfig(snsUserInfo, request, response, session);
        }





    }


    private static Map getOauth2AccessToken(String appId, String appSecret, String code) {
        Map  wat = null;

        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);

        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                wat = new HashMap();
                wat.put("accessToken",jsonObject.getString("access_token"));
                wat.put("expiresIn", jsonObject.getInt("expires_in"));
                wat.put("refreshToken", jsonObject.getString("refresh_token"));
                wat.put("openId", jsonObject.getString("openid"));
                wat.put("scope", jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                //    logger.info("oauth????,"+"???????"+errorCode+"?????????"+errorMsg);

            }
        }
        return wat;
    }


    private static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {

            TrustManager[] tm = new TrustManager[]{new TbWxMyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());

            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            conn.setRequestMethod(requestMethod);


            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();

                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }


            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }


            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            //  logger.error("????????{}", ce);
        } catch (Exception e) {
            //  logger.error("https????????{}", e);
        }
        return jsonObject;
    }
    /**
     * URL????utf-8??
     *
     * @param source
     * @return
     */
    private  String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


    private  Map getSNSUserInfo(String accessToken, String openId) {

        Map snsUserInfo = null;

        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                snsUserInfo = new HashMap();
                logger.info("WYLB getSNSUserInfo");
                logger.info("====================================");
                logger.info("WxAuth:" +jsonObject.getString("openid"));
                logger.info("====================================");
                // ????
                snsUserInfo.put("openId", jsonObject.getString("openid"));
                // ???
                snsUserInfo.put("nickName", jsonObject.getString("nickname"));
                // ???
                snsUserInfo.put("sex", jsonObject.getInt("sex"));
                // ????
                snsUserInfo.put("county", jsonObject.getString("country"));
                // ???
                snsUserInfo.put("provice", jsonObject.getString("province"));
                // ????
                snsUserInfo.put("city", jsonObject.getString("city"));
                // ???url
                snsUserInfo.put("headImg",jsonObject.getString("headimgurl"));

                snsUserInfo.put("privilege", JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
            } catch (Exception e) {
                snsUserInfo = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                //    logger.info(errorMsg);
            }
        }
        return snsUserInfo;
    }
}
