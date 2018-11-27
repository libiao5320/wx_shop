package cc.royao.wx.ctrl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

import cc.royao.commons.auth.AuthService;
import cc.royao.commons.formbean.MapVo;
import cc.royao.commons.httpClient.HttpUtil;
import cc.royao.commons.httpClient.RequestContent;
import cc.royao.commons.httpClient.ResponseContent;
import cc.royao.commons.logger.SysLogger;

/**
 * 
 * @author yangx
 * @className 退款订单处理 
 * @date 2016年1月27日上午10:32:58
 */
@Controller
@RequestMapping("/refund")
public class RefundOrderCtrl {
	
	@SysLogger
	private Logger logger;
	@Autowired
	private AuthService authService;
	
	/**
	 * 取消退款申请
	 */
	@RequestMapping("/cancelRefund.htm")
	public String cancelRefund(String orderId) {
		
		logger.info(" 取消申请提交 aaa ");
		/**判断该会员是否登录**/
		if(this.authService.getAuth().getId()==null || "".equals(this.authService.getAuth().getId())){
			logger.info("用户未注册");
			return"/Center.htm";
		}		
		Map<String,Object> premMap=new HashMap<String, Object>();
		logger.info(authService.getAuth().getId());
		premMap.put("memberId",authService.getAuth().getId());//会员id
		premMap.put("orderId",orderId);//订单id

		/** 完成查询订单  start **/
		RequestContent content = new RequestContent();
		//给请求set参数
		content.setBody(premMap);

		ResponseContent responseContent;
		try {
			responseContent = HttpUtil.getInputStreamByPost("/refund/cancelRefund.htm",content, "utf-8");
		if(null != responseContent.getBody()){
			
			JSONObject  order =(JSONObject) responseContent.getBody();
			
			String status =order.get("status")+"";
			if("true".equals(status))
				logger.info("成功");
			logger.info("成功");
			return "redirect:/order/detail/"+orderId+".htm";
			}else {
				logger.info("取消退款失败");
				return "order/cancelRefundFail";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("取消退款失败");
		return "order/sqRefundFail";
	}
	
	// /refund/sqRefund.htm
	@RequestMapping("/sqRefund/{orderId}.htm")
	public String submitOrder(ModelMap map,@PathVariable String orderId) {
		
		logger.info("  申请退款页面  ");
		/**判断该会员是否登录**/
		if(this.authService.getAuth().getId()==null || "".equals(this.authService.getAuth().getId())){
			logger.info("用户未注册");
			return"/Center.htm";
		}
		
		/** 完成查询订单  start **/
		RequestContent content = new RequestContent();
		
		Map<String,Object> premMap=new HashMap<String, Object>();
	
		if(orderId!=null && !"".equals(orderId)){
			logger.info("订单id:[]"+orderId);
			
		}else{
			logger.info("订单id为空");
		}
		premMap.put("orderId",orderId);
		content.setBody(premMap);
		
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost("/queryOrder/queryOrderByOrderId.htm",content, "utf-8");
			
			if(null != responseContent.getBody()){
				JSONObject  order =(JSONObject) responseContent.getBody();
				logger.info("数据查询成功");
				map.put("order", order);
			}else{
				map.put("order", null);
			}
		} catch (Exception e) {
			logger.error("异常："+e.getMessage());
		}
		
		return "/order/sqRefund";
	}
	
	/**
	 * 提交退款申请
	 */
	@RequestMapping("/submitRefund.htm")
	public String submitRefund(MapVo mapVo,ModelMap map) {
		
		logger.info(" 退款申请提交 aaa");
		/**判断该会员是否登录**/
		if(this.authService.getAuth().getId()==null || "".equals(this.authService.getAuth().getId())){
			logger.info("用户未注册");
			return"/Center.htm";
		}
		Map<String,Object> premMap=mapVo.getMap();
	 	String orderId =""+premMap.get("orderId");
		logger.info(authService.getAuth().getId()+"orderId:"+orderId);
		premMap.put("memberId",authService.getAuth().getId());//会员id

		/** 完成查询订单  start **/
		RequestContent content = new RequestContent();
		mapVo.setMap(premMap);
		//给请求set参数
		content.setBody(mapVo.getMap());

		ResponseContent responseContent;
		try {
			responseContent = HttpUtil.getInputStreamByPost("/refund/submit.htm",content, "utf-8");
		
		if(null != responseContent.getBody()){
			
			JSONObject  order =(JSONObject) responseContent.getBody();
			
			String status =order.get("status")+"";
			if("true".equals(status)){
				logger.info("成功");
				return "redirect:/order/detail/"+orderId+".htm";
				}else{
					logger.info("退款申请失败");
					return "order/sqRefundFail";
				}
			}else {
				logger.info("退款申请失败");
				return "order/sqRefundFail";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("退款申请失败");
		return "order/sqRefundFail";
	}
	
}
