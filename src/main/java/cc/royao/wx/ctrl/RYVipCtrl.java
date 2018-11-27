package cc.royao.wx.ctrl;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cc.royao.commons.ResponseJson;
import cc.royao.commons.CConfig;
import cc.royao.commons.auth.AuthService;
import cc.royao.commons.auth.AuthUser;
import cc.royao.commons.httpClient.HttpUtil;
import cc.royao.commons.httpClient.RequestContent;
import cc.royao.commons.httpClient.ResponseContent;
import cc.royao.commons.logger.SysLogger;
import cc.royao.commons.tencent.MD5;
import cc.royao.commons.utils.Validate;
import cc.royao.commons.weixinpay.RequestUtil;
import cc.royao.commons.weixinpay.Sha1Util;

@Controller
@RequestMapping("/ryvip")
public class RYVipCtrl {

		@SysLogger
		private Logger logger;
		
		@Autowired
		private AuthService authService;
		
		/**
		 * 进入融耀vip卡首页
		 * @param model
		 * @return
		 */
		@RequestMapping("/index.htm")
		public String ryvip_index(ModelMap model){
			String wxCode= this.authService.getAuth().getWxCode();
			RequestContent  content = new RequestContent();
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("wxCode", wxCode);
			content.setBody(map);
			
			try {
				ResponseContent response = HttpUtil.getInputStreamByPost("/member/getMyInfo.htm",content, "utf-8");
				if(null != response.getBody()){
					logger.info("Center返回的数据："+response.getBody());
					JSONObject _map = (JSONObject) response.getBody();
					model.addAttribute("user", _map);
				}
				return "ryvip/index";
			} catch (Exception e) {
				e.printStackTrace();
				return "common/errors/404";
			}
		}
		
		
		/**
		 * 充值卡充值
		 * @param card
		 * @return
		 */
		@ResponseBody
		@RequestMapping("/cardpay.ajax")
		public Object card_pay(String card){
			
			if(Validate.isEmpty(card)){
				return ResponseJson.body(false, "充值卡密码不能为空！");
			}
			
		//	card = MD5.MD5Encode(card);
			
			RequestContent content = new RequestContent();
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("card", card);
			map.put("wxCode", this.authService.getAuth().getWxCode());
			content.setBody(map);
			
			try {
				ResponseContent res = HttpUtil.getInputStreamByPost("/ryvip/cardpay.htm",content, "utf-8");
				if(null != res.getBody()){
					logger.info("return DATA<--"+res.getBody()+"-->");
					JSONObject  json = (JSONObject) res.getBody();
					String msg = json.getString("msg");
					if("SUCCESS".equals(msg)){
						
						
						AuthUser auth = this.authService.getAuth();
						auth.setVipRankId(Long.valueOf(json.getString("newGrade")));
						return ResponseJson.body(true,"充值成功！");
						
						
						
					}else if("CARD_NOT_EXIST".equals(msg)){
						return ResponseJson.body(false,"充值卡密码错误！");
					}else if("CARD_USED".equals(msg)){
						return ResponseJson.body(false,"充值卡已被使用！");
					}else if("CARD_PAST".equals(msg)){
						return ResponseJson.body(false,"充值卡已过期！");
					}
				}
				return ResponseJson.body(false, "充值失败，请稍候再试。。");
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseJson.body(false, "系统繁忙，请稍候再试。。");
			}
			
		}
		
		
		/**
		 * 调用微信扫一扫
		 * @param request
		 * @param response
		 * @param model
		 * @return
		 * @throws Exception 
		 */
		@RequestMapping("/scanQrcode.htm")
		public String scanQrcode(HttpServletRequest request,HttpServletResponse response,ModelMap model) throws Exception{
			logger.info("url:"+request.getRequestURL());
			String url = request.getRequestURL().toString();
			String nonceStr = Sha1Util.getNonceStr();
			String timestamp = Sha1Util.getTimeStamp();
			String paramAccessToken = "grant_type=client_credential&appid="+CConfig.APPID+"&secret="+CConfig.SECRET;
			
			String tokenJsonStr = RequestUtil.SendGET(CConfig.weixinJssdkAcceTokenUrl, paramAccessToken);
			
			logger.info("accessToken:"+tokenJsonStr);
			
			Map<?, ?> tokenMap = JSON.parseObject(tokenJsonStr);
			String accessToken = tokenMap.get("access_token").toString();
			logger.info("access_token："+accessToken);
			String ticketParam = "access_token="+accessToken+"&type=jsapi";
			
			String ticketJsonStr = RequestUtil.SendGET(CConfig.weixinJssdkTicketUrl, ticketParam);
			logger.info("ticketJsonStr:"+ticketJsonStr);
			
			Map<?, ?> ticketMap = JSON.parseObject(ticketJsonStr);
			
			String jsapi_ticket = ticketMap.get("ticket").toString();
			logger.info("jsapi_ticket:"+jsapi_ticket);
			SortedMap<String, String> signParam = new TreeMap<String, String>();
			signParam.put("noncestr", nonceStr);
			signParam.put("jsapi_ticket", jsapi_ticket);
			signParam.put("timestamp", timestamp);
			signParam.put("url", url);
			
			logger.info("nonceStr:====="+nonceStr);
			logger.info("jsapi_ticket:====="+jsapi_ticket);
			logger.info("timestamp:====="+timestamp);
			logger.info("url:====="+url);
			
			String SignStr = "jsapi_ticket="+jsapi_ticket+"&noncestr="+nonceStr+"&timestamp="+timestamp+"&url="+url;
			String sign = Sha1Util.getSha1(SignStr);
			/*	
			RequestHandler reqHandler = new RequestHandler(request, response);
			reqHandler.init(CConfig.APPID, CConfig.APPSECRET, CConfig.KEY);*/
			//String sign = Sha1Util.createSHA1Sign(signParam);/*reqHandler.createSign(signParam);*/
			logger.info("签名为："+sign);
			
			model.addAttribute("nonceStr", nonceStr);
			model.addAttribute("timestamp", timestamp);
			model.addAttribute("signature", sign);
			model.addAttribute("appid", CConfig.APPID);
			logger.info("wxOpenid="+this.authService.getAuth().getWxCode());
			
			model.addAttribute("wxOpenid", this.authService.getAuth().getWxCode());
			return "wx_richscan";
		}
		
		
		@ResponseBody
		@RequestMapping("/{password}.htm")
		public Object recharge(@PathVariable String password,String wxOpenid,ModelMap model){
			
			/*logger.info("获取的参数为password："+password);
			
			model.addAttribute("password", password);
			
			return "redirect:/ryvip/index.htm";*/
			password = MD5.MD5Encode(password);
			
			RequestContent content = new RequestContent();
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("card", password);
			map.put("wxCode",wxOpenid);
			content.setBody(map);
			
			try {
				ResponseContent res = HttpUtil.getInputStreamByPost("/ryvip/cardpay.htm",content, "utf-8");
				if(null != res.getBody()){
					logger.info("return DATA<--"+res.getBody()+"-->");
					JSONObject  json = (JSONObject) res.getBody();
					String msg = json.getString("msg");
					if("SUCCESS".equals(msg)){
						/*model.addAttribute("msg", "充值成功！");*/
						return ResponseJson.body(true, "充值成功！");
					}else if("CARD_NOT_EXIST".equals(msg)){
						/*model.addAttribute("msg", "充值卡密码错误！");*/
						return ResponseJson.body(false, "充值卡密码错误！");
					}else if("CARD_USED".equals(msg)){
						/*model.addAttribute("msg", "充值卡已被使用！");*/
						return ResponseJson.body(false, "充值卡已被使用！");
					}
				}
				return ResponseJson.body(false, "充值卡不存在！");
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseJson.body(false, "充值卡失败！");
			}
		}
}
