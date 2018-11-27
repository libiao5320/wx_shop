package cc.royao.wx.ctrl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.royao.commons.CConfig;
import cc.royao.commons.ResponseJson;
import cc.royao.commons.auth.AuthService;
import cc.royao.commons.auth.AuthUser;
import cc.royao.commons.auth.WxAuthUtil;
import cc.royao.commons.ccp.CCPAccountBean;
import cc.royao.commons.ccp.CCPCoreHelper;
import cc.royao.commons.ccp.CCPStatus;
import cc.royao.commons.formbean.MapVo;
import cc.royao.commons.httpClient.HttpUtil;
import cc.royao.commons.httpClient.RequestContent;
import cc.royao.commons.httpClient.ResponseContent;
import cc.royao.commons.sms.SmsHelper;
import cc.royao.commons.utils.MD5Util;
import cc.royao.commons.utils.RandCode;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/registerred")
public class RegisterRedCtrl {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private AuthService authService;
	
	@Autowired
    private WxAuthUtil wxAuthUtil;
	
	@SuppressWarnings({ "unused", "rawtypes" })
	@RequestMapping("/newuser.htm")
	public String newuser(HttpServletRequest request, HttpServletResponse response, ModelMap map){
		HttpSession session = request.getSession();
		
		Object recommendCode = request.getParameter("recommendCode");
		String url = CConfig.sharlink;
		try {
			wxAuthUtil.checkUtil(request, response, url+"?recommendCode="+recommendCode);
			
			Map snsUserInfo  = (Map) session.getAttribute("snsUserInfo");
		} catch (Exception e) {
			logger.info("获取openID失败",e);
		}
		map.put("recommendCode", recommendCode);
		
		return "user/newuser/newuser";
	}
	
	/**
	 * 
	 * @Description: 发送验证码
	 * @param @param phone
	 * @param @param request
	 * @param @return   
	 * @return ResponseJson  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年2月29日
	 */
	@ResponseBody
	@RequestMapping(value = "/sendRegValidCode.ajax")
	public ResponseJson bindMemberValidCode(@RequestParam String phone,
			HttpServletRequest request) {

		HashMap<String, Object> hmap = new HashMap<String, Object>();
		hmap.put("phone", phone);
		RequestContent content = new RequestContent();
		content.setBody(hmap);
		
		try {
			//验证手机号是否存在
			ResponseContent responseContent = HttpUtil.getInputStreamByPost("/member/getByPhone.htm",content, "utf-8");
			
			if(null != responseContent.getBody()){
				
				JSONObject member = (JSONObject) responseContent.getBody();
				
				if( member != null){//已存在
					return ResponseJson.body(false, "手机号已被注册！");
				}
				
			}
		} catch (Exception e) {
			
			logger.error("异常："+e);
			return ResponseJson.body(false, "网络忙，请稍后重试！");
		}
		
		try {
			//验证微信TOKEN是否存在
			ResponseContent responseContent = HttpUtil.getInputStreamByPost("/member/getByWxCode.htm",content, "utf-8");
			
			if(null != responseContent.getBody()){
				
				JSONObject member = (JSONObject) responseContent.getBody();
				
				if( member != null){//已存在
					return ResponseJson.body(false, "该微信号已注册！");
				}
				
			}
		} catch (Exception e) {
			
			logger.error("异常："+e);
			return ResponseJson.body(false, "网络忙，请稍后重试！");
		}
		
		StringBuffer buffer = new StringBuffer("你的短信验证码是：");
		String code = RandCode.get(4, true);
		buffer.append(code);
		request.getSession().setAttribute("regValidCode", code);

		String msg = null;

		msg = buffer.toString();
		SmsHelper.getInstance().sendSms(phone, msg);
		
		//simpleSmsSender.send(phone, msg);

		return ResponseJson.body(true, "succes");
	}
	
	/**
	 * 用户注册
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/register.ajax")
	public ResponseJson register(HttpServletRequest request, MapVo mapVo) {
		
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost("/member/getByPhone.htm",content, "utf-8");
			
			if(null != responseContent.getBody()){
				
				JSONObject member = (JSONObject) responseContent.getBody();
				
				if( member != null){//已存在
					return ResponseJson.body(false, "手机号已被注册！");
				}
				
			}
		} catch (Exception e) {
			
			logger.error("异常："+e);
			return ResponseJson.body(false, "网络忙，请稍后重试！");
		}
		
		String urlPaht = "/member/register.htm";
		// 从SESSION中获取验证码
		String code = (String) request.getSession()
				.getAttribute("regValidCode");
		
		Map<String,Object> params = mapVo.getMap();
		Map<?,?> snsUserInfo = (Map<?,?>) request.getSession().getAttribute("snsUserInfo");
		
		if(code == null || !mapVo.getMap().get("validCode").equals(code)){
			return ResponseJson.body(false, "验证码错误");
		}
		params.remove("validCode");
		
		String wxCode = (String) snsUserInfo.get("openId");
		String wxName = (String) snsUserInfo.get("nickName");
		String headImg = (String) snsUserInfo.get("headImg");
		/*
		String wxCode = "owTHvt2FaEbAzjkBX-0kMH5w_4U8";
		String wxName = "aaaa阿辉";
		String headImg = "http://wx.qlogo.cn/mmopen/TrKpuLA4FxOUv0916zpr54Im0y5dI914qKJnlWIHGGb1dLaOsRnwzPEHo1A4DfiaGxecPmjgFopB6NmlCGThqQBl30VZypHoS/0";
*/
		params.put("wxTokenId", wxCode);
		params.put("memberName", wxName);
		params.put("memberAvatar", headImg);

		content = new RequestContent();
		content.setBody(params);
		try {
			ResponseContent responseContent = HttpUtil
					.getInputStreamByPost(urlPaht, content, "utf-8");

			if (null != responseContent.getBody()) {
				JSONObject result = (JSONObject) responseContent.getBody();
				AuthUser auth = new AuthUser();
				auth.setWxCode((String) result.get("wxTokenId"));
				auth.setMemberName((String) result.get("memberName"));
				auth.setId(Long.valueOf("" + result.get("memberId")));
				auth.setHeadImg((String) result.get("memberAvatar"));
				this.authService.setAuth(auth);

				params.put("memberId", auth.getId());
				content.setBody(params);
				
				if (auth.getId() != null && StringUtils.isNotBlank(result.get("memberMobile")+"")) {
					
					// 新建容联帐号
					CCPAccountBean ccpBean = new CCPAccountBean();

					ccpBean = CCPCoreHelper.getInstance().CPPCreateSub(
							MD5Util.MD5(result.get("memberMobile").toString()));
					if (CCPStatus.CCP_OK.equals(ccpBean.getStatusCode())) {
						params.put("subAccountSid",ccpBean.getSubAccountSid());
						params.put("subToken",ccpBean.getSubToken());
						params.put("voipAccount",ccpBean.getVoipAccount());
						params.put("voipPwd",ccpBean.getVoipPwd());
					} else if (CCPStatus.CCP_ERROR_CA_REPEAT.equals(ccpBean
							.getStatusCode())) {
						// 查询容联帐号
						ccpBean = CCPCoreHelper.getInstance().CPPQuerySubAccount(
								MD5Util.MD5(result.get("memberMobile").toString()));
						if (CCPStatus.CCP_OK.equals(ccpBean.getStatusCode())) {
							params.put("subAccountSid",ccpBean.getSubAccountSid());
							params.put("subToken",ccpBean.getSubToken());
							params.put("voipAccount",ccpBean.getVoipAccount());
							params.put("voipPwd",ccpBean.getVoipPwd());
						}
					}
					
					responseContent = HttpUtil.getInputStreamByPost(
							"/member/updateRlById.htm", content, "utf-8");
					if (null != responseContent.getBody()) {
						result = (JSONObject) responseContent.getBody();
						if (null != result	&& result.equals(true)) {
							// 跟新成功
						} else {
							logger.info("跟新更新容联子帐号信息失败");
						}
					} else {
						logger.info("跟新更新容联子帐号信息失败");
					}
				}
				
				//领取红包
				params.put("memberId", auth.getId());
				responseContent = HttpUtil.getInputStreamByPost("/distribute/regReceive.htm", content, "utf-8");
				if (null != responseContent.getBody()) {
					JSONObject red = (JSONObject) responseContent.getBody();
					if(red != null){
						return ResponseJson.body(true, "success",red);
					}
				} else {
					return ResponseJson.body(true, "error");
				}
			}else{
				ResponseJson.body(false, "注册失败");
			}
		} catch (Exception e) {
			logger.info("注册领红包出错", e);
		}

		return ResponseJson.body(false, "网络忙，请稍后重试");

	}
	
	/**
	 * 
	 * @Description: 领取失败
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年2月29日
	 */
	@RequestMapping("/receivefail.htm")
	public String receivefail(){
		
		return "user/newuser/receivefail";
	}
}
