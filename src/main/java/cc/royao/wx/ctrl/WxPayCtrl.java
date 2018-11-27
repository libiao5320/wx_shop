package cc.royao.wx.ctrl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cc.royao.commons.CConfig;
import cc.royao.commons.auth.AuthUser;
import cc.royao.commons.httpClient.HttpUtil;
import cc.royao.commons.httpClient.RequestContent;
import cc.royao.commons.httpClient.ResponseContent;
import cc.royao.commons.logger.SysLogger;
import cc.royao.commons.utils.RandCode;
import cc.royao.commons.weixinpay.GetWxOrderno;
import cc.royao.commons.weixinpay.RequestHandler;
import cc.royao.commons.weixinpay.Sha1Util;
import cc.royao.commons.weixinpay.XMLUtil;


@Controller
@RequestMapping
public class WxPayCtrl {
			
			@SysLogger
			private Logger logger;
			
	
			/**
			 * @param sn
			 * @param reqeust
			 * @return
			 */
			@RequestMapping("/bd/wxpay/{sn}.htm")
			public String wxpay(@PathVariable String sn,HttpServletRequest request,HttpServletResponse response,ModelMap model){
				RequestContent content = new RequestContent();
				
				HashMap<String,Object> map  = new HashMap<String, Object>();
				map.put("sn",sn);
				
				content.setHead(null);
				content.setBody(map);
				long money = 0l;
				String wxopenId = "";
				try {
					ResponseContent res = HttpUtil.getInputStreamByPost("/wxpay/refer.htm",content, "utf-8");
					if(null != res.getBody()){
						Map<?,?> maps = (Map<?, ?>) res.getBody();
						money = Long.parseLong(maps.get("money").toString());
						wxopenId = maps.get("openid").toString();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				// 附加数据 原样返回
				 String attach = sn;
				 
				// 总金额以分为单位，不带小数点
				 String totalFee = money+"";
				
				// 订单生成的机器 IP
				String spbill_create_ip = request.getRemoteAddr();
				logger.info("request.getLocalAddr()"+request.getLocalAddr());
				// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
				String notify_url = CConfig.wxUrl+CConfig.NOTIFY_URL;
				
				String trade_type = "JSAPI";
				
				// 随机字符串
				String nonce_str = RandCode.getNonceStr();
				
				String body ="融耀平台";
				
				SortedMap<String, String> packageParams = new TreeMap<String, String>();
				packageParams.put("appid", CConfig.APPID);
				packageParams.put("body", body);
				packageParams.put("mch_id", CConfig.MCHID);
				packageParams.put("nonce_str", nonce_str);
				packageParams.put("notify_url", notify_url);
				packageParams.put("attach", attach);
				packageParams.put("out_trade_no", sn);
				packageParams.put("openid", wxopenId);
				packageParams.put("spbill_create_ip", spbill_create_ip);
				// 这里写的金额为1 分到时修改
				packageParams.put("total_fee", totalFee);
				packageParams.put("trade_type", trade_type);
				
				RequestHandler reqHandler = new RequestHandler(request, response);
				reqHandler.init(CConfig.APPID, CConfig.SECRET, CConfig.KEY);
				
				String sign = reqHandler.createSign(packageParams);
				String xml = "<xml>" + "<appid>" + CConfig.APPID + "</appid>" + "<body><![CDATA[" + body + "]]></body>" + "<mch_id>" + CConfig.MCHID + "</mch_id>" + "<notify_url>"
						+ notify_url + "</notify_url>" + "<nonce_str>" + nonce_str + "</nonce_str>" + "<out_trade_no>" + sn + "</out_trade_no>" + "<openid>" + wxopenId + "</openid>"
						+ "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>" + "<sign>" + sign + "</sign>" + "<total_fee>" + totalFee + "</total_fee>"
					    + "<attach>" + attach + "</attach>"
						+ "<trade_type>" + trade_type + "</trade_type>" + "</xml>";
				
				String prepay_id = "";
				String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
				
				
				prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);

				// 获取prepay_id后，拼接最后请求支付所需要的package

				SortedMap<String, String> finalpackage = new TreeMap<String, String>();
				String timestamp = Sha1Util.getTimeStamp();
				String packages = "prepay_id=" + prepay_id;
				finalpackage.put("appId", CConfig.APPID);
				finalpackage.put("nonceStr", nonce_str);
				finalpackage.put("package", packages);
				finalpackage.put("signType", "MD5");
				finalpackage.put("timeStamp", timestamp);
				
				// 签名
				String finalsign = reqHandler.createSign(finalpackage);

				model.addAttribute("appId", CConfig.APPID);
				model.addAttribute("timeStamp", timestamp);
				model.addAttribute("nonceStr", nonce_str);
				model.addAttribute("packageValue", packages);
				model.addAttribute("signType", "MD5");
				model.addAttribute("finalsign", finalsign);
				model.addAttribute("openid", wxopenId);
				model.addAttribute("orderId", sn);
				model.addAttribute("money", totalFee);
				
				return "/wxpay";
			}
			
			
			
			
			@RequestMapping("/bd/wxpay_notify.ajax")
			public Object weixin_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
				InputStream inStream = request.getInputStream();
				ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = inStream.read(buffer)) != -1) {
					outSteam.write(buffer, 0, len);
				}
				outSteam.close();
				inStream.close();
				String resultStr = new String(outSteam.toByteArray(), "utf-8");
				Map<String, String> resultMap = XMLUtil.doXMLParse(resultStr);
				
				
				String out_trade_no = resultMap.get("out_trade_no");
				String return_code = resultMap.get("return_code");
				String transaction_id = resultMap.get("transaction_id");
				
				String attach = resultMap.get("attach");
				
				logger.info("微信异步通知返回的订单号："+out_trade_no+"微信返回的状态值："+return_code+"微信支付订单号："+transaction_id+"原样返回的用户id："+attach);
				
				if ("SUCCESS".equals(return_code)){
					RequestContent content = new RequestContent();
					
					HashMap<String,Object> map  = new HashMap<String, Object>();
					map.put("sn",out_trade_no);
					map.put("wxpay_no", transaction_id);
					
					content.setHead(null);
					content.setBody(map);
					
					ResponseContent res = HttpUtil.getInputStreamByPost("/wxpay/refer.htm",content, "utf-8");
					
					if(null != res.getBody()){
						Map<?,?>  maps = (Map<?, ?>) res.getBody();
						int state = Integer.parseInt(maps.get("state").toString());
						if(state == 1){
							ResponseContent res_1 = HttpUtil.getInputStreamByPost("/wxpay/wx_update.htm",content, "utf-8");
							if(null != res_1.getBody()){
								Map<?,?> map_1 = (Map<?, ?>) res_1.getBody();
								String state_1 = map_1.get("state").toString();
								/*yangx add  更改用户vip等级 start*/
								String rvip=map_1.get("rvip").toString();
								AuthUser auth = new AuthUser();
								auth.setVipRankId(Long.valueOf(""+rvip));//保存到缓存中
								/*yangx add  更改用户vip等级 end*/
								
								logger.info("修改数据库DbalanceLogs和Dmember的金额返回的状态："+state_1);
							}
						}
					}
				}
				
				// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
				response.getWriter().write(
						"<xml><return_code><![CDATA[SUCCESS]]></return_code> <return_msg><![CDATA[OK]]></return_msg></xml>");
				return "SUCCESS";
			}
}
