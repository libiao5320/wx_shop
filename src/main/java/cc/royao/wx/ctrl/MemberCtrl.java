package cc.royao.wx.ctrl;

import cc.royao.commons.CConfig;
import cc.royao.commons.ResponseJson;
import cc.royao.commons.auth.AuthService;
import cc.royao.commons.auth.AuthUser;
import cc.royao.commons.ccp.CCPAccountBean;
import cc.royao.commons.ccp.CCPCoreHelper;
import cc.royao.commons.ccp.CCPStatus;
import cc.royao.commons.formbean.MapVo;
import cc.royao.commons.httpClient.HttpUtil;
import cc.royao.commons.httpClient.RequestContent;
import cc.royao.commons.httpClient.ResponseContent;
import cc.royao.commons.sms.SmsHelper;
import cc.royao.commons.utils.EmojiFilter;
import cc.royao.commons.utils.MD5Util;
import cc.royao.commons.utils.QRCodeUtil;
import cc.royao.commons.utils.RandCode;
import cc.royao.commons.utils.Validate;
import cc.royao.model.Dmember;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by libia on 2016/1/5.
 */
@Controller
public class MemberCtrl {

	Logger logger = Logger.getLogger(MemberCtrl.class);

	@Autowired
	private AuthService authService;

	@ResponseBody
	@RequestMapping("/getMemberById.ajax")
	public ResponseJson getMember(MapVo mapVo) {

		logger.info("  获取会员信息  ");
		/** 判断该会员是否登录 **/
		if (this.authService.getAuth().getId() == null
				|| "".equals(this.authService.getAuth().getId())) {
			logger.info("用户未注册");
			return ResponseJson.body(false, "login");
		}
		/** 完成查询订单 start **/
		RequestContent content = new RequestContent();

		Map<String, Object> premMap = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();

		premMap.put("memberId", authService.getAuth().getId());// 会员ID

		content.setBody(premMap);

		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(
					"/member/getById.htm", content, "utf-8");

			if (null != responseContent.getBody()) {
				JSONObject member = (JSONObject) responseContent.getBody();
				logger.info("数据查询成功");

				map.put("message", "数据查询成功");
				map.put("availablePredeposit",
						member.get("availablePredeposit"));// 可用预存款
				map.put("freezePredeposit", member.get("freezePredeposit"));// 冻结预存款

				return ResponseJson.body(true, "SUCCESS", map);

			} else {
				map.isEmpty();
				map.put("message", "数据查询成功");
				return ResponseJson.body(false, "FILE", map);
			}
		} catch (Exception e) {
			logger.error("异常：" + e.getMessage());
		}
		return ResponseJson.body(false, "FILE", map);
	}

	/**
	 * 检查用户支付密码
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/checkUserPayPwd.htm")
	public String checkUserPayPwd(ModelMap modelMap) {
		String urlPaht = "/member/ucCenter.htm";
		RequestContent content = new RequestContent();
		String page = null;

		AuthUser authUser = this.authService.getAuth();
		Map<String,Object> params = new HashMap<String,Object>();

		params.put("wxCode", authUser.getWxCode());
		// params.put("wxCode","owTHvt4CEOAgQDfte3lHJwCSF-qg");

		content.setBody(params);

		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(
					urlPaht, content, "utf-8");
			if (null != responseContent.getBody()) {
				JSONObject result = (JSONObject) responseContent.getBody();
				Object memberInfo = result.get("memberInfo");
				if (null != memberInfo) {
					modelMap.put("result", result);
					page = "user/userCenter";
				} else {
					page = "user/bindphone";
				}
			} else {
				page = "user/bindphone";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	@RequestMapping("/userCenter.htm")
	public String userCenter(ModelMap modelMap) {
		String urlPaht = "/member/ucCenter.htm";
		RequestContent content = new RequestContent();
		String page = null;

		AuthUser authUser = this.authService.getAuth();
		Map<String,Object> params = new HashMap<String,Object>();

		params.put("wxCode", authUser.getWxCode());
		// params.put("wxCode","owTHvt4CEOAgQDfte3lHJwCSF-qg");

		content.setBody(params);

		try {
//			ResponseContent responseContent = HttpUtil.getInputStreamByPost(
//					urlPaht, content, "utf-8");
//			if (null != responseContent.getBody()) {
//				JSONObject result = (JSONObject) responseContent.getBody();
//				logger.info("userInfo=="+result.toJSONString());
				Object memberInfo =  null;
				if (null != memberInfo) {
//					modelMap.put("result", result);
					page = "user/userCenter";
				} else {
					page = "user/bindphone";
				}
//			} else {
//				page = "user/bindphone";
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	@RequestMapping("/myOrder/{status}.htm")
	public String myOrder(ModelMap modelMap, @PathVariable Integer status) {
		logger.info("根据状态查询订单记录信息");

		String urlPaht = "/member/orderlist.htm";
		Map<String, Object> paramMap = new HashMap<String, Object>();

		RequestContent content = new RequestContent();
		paramMap.put("status", status);
		paramMap.put("pageNo", 1);
		paramMap.put("memberId", this.authService.getAuth().getId());
		content.setBody(paramMap);

		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(
					urlPaht, content, "utf-8");

			if (null != responseContent.getBody()) {
				JSONArray result = (JSONArray) responseContent.getBody();
				System.out.println("-------------"+result.toJSONString());
				modelMap.put("result", result);
				modelMap.put("status", status);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "order/orderlist";
	}

	@ResponseBody
	@RequestMapping("/myOrder.ajax")
	public JSONArray myOrderAjax(Integer status, Integer pageNo) {
		String urlPaht = "/member/orderlist.htm";
		Map<String, Object> paramMap = new HashMap<String, Object>();

		RequestContent content = new RequestContent();
		paramMap.put("status", status);
		paramMap.put("memberId", this.authService.getAuth().getId());
		paramMap.put("pageNo", pageNo);
		content.setBody(paramMap);
		JSONArray result = null;
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPaht, content, "utf-8");
			if (null != responseContent.getBody()) {
				result = (JSONArray) responseContent.getBody();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping("/myCollect/{type}/{pageNo}.htm")
	public String myCollect(ModelMap modelMap, @PathVariable Integer type,
			@PathVariable Integer pageNo) {

		String urlPaht = "/member/collectlist.htm";
		RequestContent content = new RequestContent();
		AuthUser authUser = this.authService.getAuth();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("wxCode", authUser.getWxCode());
		// params.put("wxCode","owTHvt4CEOAgQDfte3lHJwCSF-qg");
		params.put("type", type);
		params.put("pageNo", pageNo);
		content.setBody(params);
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(
					urlPaht, content, "utf-8");
			if (null != responseContent.getBody()) {
				JSONObject result = (JSONObject) responseContent.getBody();
				logger.info("userCenter:"+responseContent.getBody());
				modelMap.put("result", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (type == 1)
			return "user/mygoodcollect";
		else
			return "user/mystorecollect";

	}

	/***-------------------start*/
	@ResponseBody
	@RequestMapping("/myCollect/{type}.ajax")
	public Object myCollectAjax(ModelMap modelMap, @PathVariable Integer type, Integer pageNo){
		String urlPaht = "/member/collectlist.htm";
		RequestContent content = new RequestContent();
		AuthUser authUser = this.authService.getAuth();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("wxCode", authUser.getWxCode());
		// params.put("wxCode","owTHvt4CEOAgQDfte3lHJwCSF-qg");
		params.put("type", type);
		params.put("pageNo", pageNo);
		content.setBody(params);
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(
					urlPaht, content, "utf-8");
			if (null != responseContent.getBody()) {
				JSONObject result = (JSONObject) responseContent.getBody();
				logger.info("userCenter:"+responseContent.getBody());
				return ResponseJson.body(true, "",result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  null;
	}
	/***-------------------end*/
	
	@RequestMapping("/myConsumption.htm")
	public String myConsumption(ModelMap modelMap) {

		String urlPaht = "/member/consumptionlist.htm";
		RequestContent content = new RequestContent();
		AuthUser authUser = this.authService.getAuth();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pageNo", 1);
		// params.put("wxCode","owTHvt4CEOAgQDfte3lHJwCSF-qg");

		params.put("wxCode", authUser.getWxCode());

		content.setBody(params);

		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(
					urlPaht, content, "utf-8");

			if (null != responseContent.getBody()) {
				JSONArray result = (JSONArray) responseContent.getBody();
				modelMap.put("result", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user/consumption/index";

	}

	/**
	 * 进入支付管理
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/pwdManger.htm")
	public String pwdManger(ModelMap modelMap) {
		return "user/paymentPwd/pwdManger";
	}

	/**
	 * 进入修改密码
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/toPaymentPwd.htm")
	public String paymentPwd(ModelMap modelMap) {
		// 查询用户是否已经设置支付密码
		String urlPath = "/member/getMyInfo.htm";
		RequestContent content = new RequestContent();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("wxCode",this.authService.getAuth().getWxCode());
		content.setBody(map);
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(
					urlPath, content, "utf-8");

			if (null != responseContent.getBody()) {
				JSONObject json = (JSONObject) responseContent.getBody();
				Dmember member = (Dmember) JSONObject.toJavaObject(json,
						Dmember.class);
				if (member.getMemberPaypwd() != null) {
					// 已有支付密码，跳入到确认原密码页面
					return "user/paymentPwd/oldPwd";
				} else {
					// 未设置支付密码，跳入的设置页面
					return "user/paymentPwd/paymentPsd";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "user/paymentPwd/pwdManger";

	}

	/**
	 * 发送验证码
	 * 
	 * @param mobile
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendr.ajax")
	public Object sendr(String mobile) {
		if (mobile != null) {
			if (!Validate.isMobile(mobile)) {
				return ResponseJson.body(false, "手机格式不正确，请重新输入");
			}
		} else {
			return ResponseJson.body(false, "请输入手机号!");
		}

		String code = RandCode.get(4, true);
		AuthUser auth = this.authService.getAuth();
		Map<String, Object> map = new HashMap<String, Object>();

		System.out.print("=======================");
		System.out.print("The  ValidCode is ：" + code);
		System.out.print("=======================");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		map.put("code", code);
		map.put("mobile", mobile);
		request.getSession().setAttribute("code", code);
		auth.setData(map);
		this.authService.setAuth(auth);
		
		SmsHelper.getInstance().sendSms(mobile, "您的验证码为：" + code
				+ "。请在15分钟内填写，关闭该页面会导致验证码失效。");
		//this.simpleSmsSender.send(mobile, "您的验证码为：" + code
		//		+ "。请在15分钟内填写，关闭该页面会导致验证码失效。");
		return ResponseJson.body(true, "短信已发送");
	}

	/**
	 * 进入找回密码
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/tofindPwd.htm")
	public String tofindPwd(ModelMap modelMap) {
		// 查询用户是否已经设置支付密码
		String urlPath = "/member/getMyInfo.htm";
		RequestContent content = new RequestContent();
		Map<String, Object> map = new HashMap<String, Object>();

		/** 判断该会员是否登录 **/
		if (this.authService.getAuth().getId() == null
				|| "".equals(this.authService.getAuth().getId())) {
			logger.info("用户未注册");
			// 跳转到未注册页面
			return "/userCenter";
		}
		map.put("wxCode", authService.getAuth().getWxCode());

		content.setBody(map);
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(
					urlPath, content, "utf-8");

			if (null != responseContent.getBody()) {
				JSONObject json = (JSONObject) responseContent.getBody();
				Dmember member = (Dmember) JSONObject.toJavaObject(json,
						Dmember.class);
				if (member.getMemberMobile() != null
						&& member.getMemberMobileBind() == 1) {
					modelMap.put("moblie", member.getMemberMobile());
					return "user/paymentPwd/findPwd";
				} else {
					// 邮箱发送验证码

					// 跳转到发送成功并填写验证码页面
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "user/paymentPwd/pwdManger";

	}

	/**
	 * 短信验证码确认
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/codeConfrim.ajax")
	public Object codeConfrim(ModelMap modelMap, String code) {
		// 从缓存获取发送的手机短信
		AuthUser auth = this.authService.getAuth();
		Map<String, Object> map = auth.getData();
		if (map != null) {
			// 验证码验证成功，进入修改页面
			if (code.equals(map.get("code"))) {
				// 密码一致，跳入的修改设置密码页面
				return ResponseJson.body(true, "请重新设置您的支付密码!");
			} else {
				// 密码不一致，进入到支付管理页面
				return ResponseJson.body(false, "您输入的验证码有误!");
			}
		} else {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes()).getRequest();
			String codes = (String) request.getSession().getAttribute("code");
			// 验证码验证成功，进入修改页面
			if (code.equals(codes)) {
				// 密码一致，跳入的修改设置密码页面
				return ResponseJson.body(true, "请重新设置您的支付密码!");
			} else {
				// 密码不一致，进入到支付管理页面
				return ResponseJson.body(false, "您输入的验证码有误!");
			}
		}

	}

	/**
	 * 原密码确认验证
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/oldConfrim.ajax")
	public Object oldConfrim(ModelMap modelMap, Dmember dmember) {

		logger.info("原密码确认验证[]:" + dmember.getMemberPaypwd());
		// 根据微信code查询会员秘钥
		String urlPath = "/member/getMyInfo.htm";
		Map<String, Object> map = new HashMap<String, Object>();
		RequestContent content = new RequestContent();

		// 从缓存获取发送的手机短信
		AuthUser auth = this.authService.getAuth();
		String wxCode = auth.getWxCode();

		map.put("wxCode", wxCode);
		content.setBody(map);
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(
					urlPath, content, "utf-8");

			if (null != responseContent.getBody()) {
				JSONObject json = (JSONObject) responseContent.getBody();
				Dmember member = (Dmember) JSONObject.toJavaObject(json,
						Dmember.class);
				// 根据秘钥生成加密密码
				String passwords = this.authService.genPwd(
						dmember.getMemberPaypwd(), member.getMemberPaysecret(),
						true);
				if (passwords != null
						&& passwords.equals(member.getMemberPaypwd())) {
					// 密码一致，跳入的修改设置密码页面
					return ResponseJson.body(true, "请重新设置您的支付密码!");
				} else {
					// 密码不一致，进入到支付管理页面
					return ResponseJson.body(false, "您输入的密码不正确!");
				}
			}
		} catch (Exception e) {
			logger.info("异常：" + e.getMessage());
		}
		return "user/paymentPwd/pwdManger";
	}

	/**
	 * 跳到支付密码设置
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/paymentPsd.htm")
	public String password(ModelMap modelMap, String isdefault) {
		logger.info(" 跳到支付密码设置");
		modelMap.put("isdefault", isdefault);
		return "user/paymentPwd/paymentPsd";

	}

	/**
	 * 跳转到再次输入密码
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/toRePaymentPsd.htm")
	public String passwordFirst(ModelMap map, Dmember dmember) {
		map.put("memberPaypwd", dmember.getMemberPaypwd());
		return "user/paymentPwd/confirmPsd";
	}

	/**
	 * 设置支付密码
	 * 
	 * @param request
	 * @parm1 支付密码
	 * @return
	 */

	@RequestMapping(value = "/setPayPsd.htm")
	public String setPayPassword(ModelMap modelMap, String parm1) {
		logger.info("支付密码：" + parm1);
		String secret = RandCode.get(4, true);
		String pwd = this.authService.genPwd(parm1, secret, true);

		String urlPaht = "/member/updatePayment.ajax";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		RequestContent content = new RequestContent();
		paramMap.put("memberPaysecret", secret);
		paramMap.put("memberPaypwd", pwd);
		paramMap.put("wxCode", this.authService.getAuth().getWxCode());
		content.setBody(paramMap);
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(
					urlPaht, content, "utf-8");
			if (null != responseContent.getBody()) {
				JSONObject result = (JSONObject) responseContent.getBody();
				modelMap.put("result", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return "user/paymentPwd/pwdManger";
		//个人中心修改密码的，跳转到个人中心
			String urlPaht2 = "/member/ucCenter.htm";
			RequestContent content2 = new RequestContent();
			String page = null;
	
			AuthUser authUser = this.authService.getAuth();
			Map<String,Object> params = new HashMap<String,Object>();
	
			params.put("wxCode", authUser.getWxCode());
	
			content2.setBody(params);
	
			try {
				ResponseContent responseContent = HttpUtil.getInputStreamByPost(
						urlPaht2, content2, "utf-8");
				if (null != responseContent.getBody()) {
					JSONObject result = (JSONObject) responseContent.getBody();
					logger.info("userInfo=="+result.toJSONString());
					Object memberInfo = result.get("memberInfo");
					if (null != memberInfo) {
						modelMap.put("result", result);
						page = "user/userCenter";
					} else {
						page = "user/bindphone";
					}
				} else {
					page = "user/bindphone";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return page;
	}
	
	@ResponseBody
	@RequestMapping(value = "/setPayPsd.ajax")//下单页面改密码的，跳转回下单页面
	public Object setPayPassword(String parm1,String url) {
		logger.info("支付密码：" + parm1);
		String secret = RandCode.get(4, true);
		String pwd = this.authService.genPwd(parm1, secret, true);

		String urlPaht = "/member/updatePayment.ajax";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		RequestContent content = new RequestContent();
		paramMap.put("memberPaysecret", secret);
		paramMap.put("memberPaypwd", pwd);
		paramMap.put("wxCode", this.authService.getAuth().getWxCode());
		content.setBody(paramMap);
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(
					urlPaht, content, "utf-8");
			if (null != responseContent.getBody()) {
				JSONObject result = (JSONObject) responseContent.getBody();
				return ResponseJson.body(true, url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseJson.body(true, "修改密码失败");
	}

	@RequestMapping("/myRedPack.htm")
	public String myRedPack(ModelMap modelMap) {

		String urlPaht = "/member/redpacklist.htm";
		RequestContent content = new RequestContent();

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pageNo", 1);
		// params.put("wxCode","owTHvt4CEOAgQDfte3lHJwCSF-qg");
		AuthUser authUser = this.authService.getAuth();
		params.put("wxCode", authUser.getWxCode());
		content.setBody(params);
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(
					urlPaht, content, "utf-8");

			if (null != responseContent.getBody()) {
				JSONArray result = (JSONArray) responseContent.getBody();
				modelMap.put("list", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redpackets/myredpackets";

	}

	@RequestMapping(value = "/userInfo.htm")
	public String userInfo(ModelMap modelMap) {
		String urlPath = "/member/userInfo.htm";

		AuthUser authUser = this.authService.getAuth();
		Map<String,Object> params = new HashMap<String,Object>();

		// params.put("wxCode","owTHvt4CEOAgQDfte3lHJwCSF-qg");
		params.put("wxCode", authUser.getWxCode());
		RequestContent content = new RequestContent();
		content.setBody(params);

		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(
					urlPath, content, "utf-8");
			if (null != responseContent.getBody()) {
				logger.info("userinf===="+responseContent.getBody());
				JSONObject result = (JSONObject) responseContent.getBody();
				modelMap.put("result", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user/userInfo";

	}

	@ResponseBody
	@RequestMapping(value = "sendRegValidCode")
	public ResponseJson bindMemberValidCode(@RequestParam String phone,
			HttpServletRequest request) {
		
		HashMap<String, Object> hmap = new HashMap<String, Object>();
		hmap.put("phone", phone);
		RequestContent content = new RequestContent();
		content.setBody(hmap);

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
		
		StringBuffer buffer = new StringBuffer("你的短信验证码是：");
		String code = RandCode.get(4, true);
		AuthUser auth = this.authService.getAuth();
		buffer.append(code);
		request.getSession().setAttribute("regValidCode", code);

		System.out.print("======================="+auth);
		System.out.print("The  ValidCode is ：" + code);
		System.out.print("=======================");

		String msg = null;

		msg = buffer.toString();

		SmsHelper.getInstance().sendSms(phone, msg);
		//simpleSmsSender.send(phone, msg);

		return ResponseJson.body(true, "注册码已经发送,请注意查收");
	}

	@ResponseBody
	@RequestMapping(value = "authRegValidCode")
	public String authRegValidCode(String inputCode, HttpServletRequest request) {

		String validCode = (String) request.getSession().getAttribute(
				"regValidCode");
		String flag = "0";

		System.out
				.println("==================================================");
		System.out.println("用户输入code:" + inputCode);
		System.out.println("Session中的validCode:" + validCode);
		System.out
				.println("==================================================");
		if (null != inputCode && !"".equals(inputCode))
			if (inputCode.equals(validCode))
				flag = "1";

		return flag;
	}

	/**
	 * 用户注册
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/register.htm")
	public ResponseJson register(HttpServletRequest request, MapVo mapVo) {
		String urlPaht = "/member/register.htm";
		// 从SESSION中获取验证码
		String code = (String) request.getSession()
				.getAttribute("regValidCode");
		String msg = null;
		boolean flag = true;
		Map<String,Object> params = mapVo.getMap();
		Map snsUserInfo = (Map) request.getSession()
				.getAttribute("snsUserInfo");
		// if( null == snsUserInfo)
		// {
		// msg = "用户微信信息验证失败";
		// flag = false;
		// }
		if (flag) {
			params.remove("validCode");
			
			String wxCode = (String) snsUserInfo.get("openId");
			String wxName = (String) snsUserInfo.get("nickName");
			wxName = EmojiFilter.filterEmoji(wxName);
			String headImg = (String) snsUserInfo.get("headImg");
			/*	
			String wxCode = "owTHvt2FaEbAzjkBX-0kMH5w_4U8";
			String wxName = "aaaa阿辉";
			String headImg = "http://wx.qlogo.cn/mmopen/TrKpuLA4FxOUv0916zpr54Im0y5dI914qKJnlWIHGGb1dLaOsRnwzPEHo1A4DfiaGxecPmjgFopB6NmlCGThqQBl30VZypHoS/0";
			*/
			params.put("wxTokenId", wxCode);
			params.put("memberName", wxName);
			params.put("memberAvatar", headImg);

			RequestContent content = new RequestContent();
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
					auth.setRecommendCode((String) result.get("recommendCode"));
					auth.setVipRankId(Long.valueOf(""+ result.get("vipRankId")));
					this.authService.setAuth(auth);

					if (auth.getId() != null && StringUtils.isNotBlank(result.get("memberMobile")+"")) {
						params.put("memberId", auth.getId());
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
							if (null != result	&& result.get("result").equals(true)) {
								// 跟新成功
							} else {
								logger.info("跟新更新容联子帐号信息失败");
							}
						} else {
							logger.info("跟新更新容联子帐号信息失败");
						}
					}
					
					params.put("memberId", auth.getId());
					content.setBody(params);
					//领取红包
					responseContent = HttpUtil.getInputStreamByPost("/distribute/regReceive.htm", content, "utf-8");
					if (null != responseContent.getBody()) {
						JSONObject red = (JSONObject) responseContent.getBody();
						if(red != null){
							return ResponseJson.body(true, "red",red);
						}
					} else {
						return ResponseJson.body(true, "member");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return ResponseJson.body(false, "error");
	}

	
	@ResponseBody
	@RequestMapping("/userDeleteOrder.ajax")
	public Object userDeleteOrder(Long[] xzkk_n){
		
		/** 完成查询订单 start **/
		RequestContent content = new RequestContent();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", xzkk_n);
		
		content.setBody(map);
		
		try {
			ResponseContent res = HttpUtil.getInputStreamByPost("/member/deleteUserOrder.htm", content, "utf-8");
			if(null != res.getBody()){
				logger.info("rerponse数据："+res.getBody());
				JSONObject json = (JSONObject) res.getBody();
				String msg = json.getString("RETURN_MSG");
				if("SUCCESS".equals(msg)){
					return ResponseJson.body(true, "删除成功！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseJson.body(true, "删除失败！");
	}
	
	
	@RequestMapping("/user_share.htm")
	public String user_share(String img,ModelMap model,HttpServletRequest request,String codes){
		AuthUser user = this.authService.getAuth();
		String LocationUrl = request.getSession().getServletContext().getRealPath("/");
		try {
			QRCodeUtil.encode(CConfig.sharlink+"?recommendCode="+codes, img,LocationUrl+CConfig.imgUserQrade ,user.getId()+"",true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("img_url", CConfig.imgUserQrade+"/"+user.getId()+".jpg");
		model.addAttribute("img", img);
		return "user/user_share";
	}
	
	/**
	 * 
	 * @Description: 贝多协议
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年3月18日
	 */
	@RequestMapping("/protocol.htm")
	public String protocol(){
		
		return "user/protocol";
	}
}
