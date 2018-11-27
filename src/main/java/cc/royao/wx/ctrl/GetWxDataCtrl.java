package cc.royao.wx.ctrl;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.royao.commons.Config;
import cc.royao.commons.ResponseJson;
import cc.royao.commons.CConfig;
import cc.royao.commons.logger.SysLogger;
import cc.royao.commons.weixinpay.RequestUtil;
import cc.royao.commons.weixinpay.Sha1Util;

import com.alibaba.fastjson.JSON;


@Controller
@RequestMapping
public class GetWxDataCtrl {
		
		@SysLogger
		private Logger logger;
	
	
		@ResponseBody
		@RequestMapping("/getWxData.ajax")
		public Object getWxData(HttpServletRequest request,HttpServletResponse response){
			
			Map<String,Object> maps = new HashMap<String, Object>();
			logger.info("url:"+request.getHeader("referer"));
			String url = request.getHeader("referer");
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
			
			logger.info("签名为："+sign);
			
			maps.put("noncestr", nonceStr);
			maps.put("timestamp", timestamp);
			maps.put("appid", CConfig.APPID);
			maps.put("sign", sign);
			
			return ResponseJson.body(true, "",maps);
		}
}
