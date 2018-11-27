package cc.royao.wx.ctrl;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.royao.commons.ResponseJson;
import cc.royao.commons.auth.AuthService;
import cc.royao.commons.httpClient.HttpUtil;
import cc.royao.commons.httpClient.RequestContent;
import cc.royao.commons.httpClient.ResponseContent;
import cc.royao.commons.logger.SysLogger;
import cc.royao.commons.utils.MoneyUtil;
import cc.royao.commons.utils.RandCode;
import cc.royao.commons.utils.Validate;

/**
 * @author 杨鑫
 *@RongYaoCardCtrl.java
 *@email 450160147@qq.com
 *@time 2016年1月6日
 */
@Controller
@RequestMapping("/vip")
public class RongYaoCardCtrl {
		
	
	
		@SysLogger
		private Logger logger;
		
		@Autowired
		private AuthService authService;
		
		/**
		 * 进入充值首页
		 * @param model
		 * @return
		 */
		@RequestMapping("/vip_index.htm")
		public String index(ModelMap model){
			
			RequestContent content = new RequestContent();
			
			HashMap<String,Object> map  = new HashMap<String, Object>();
			map.put("status", "normal");
			//map.put("userId", "2");
			
			content.setHead(null);
			content.setBody(map);
		
			try {
				ResponseContent responseContent = HttpUtil.getInputStreamByPost("/vip/list.htm",content, "utf-8");
				if(null != responseContent.getBody()){
					
					System.out.println("数据中心返回的荣耀vip卡等级参数："+responseContent.getBody());
					
					Map<?,?> json = (Map<?, ?>) responseContent.getBody();
					
					
					System.out.println("用户返回的页面的json数据："+json);
					
					model.addAttribute("vipList", json);
				}else{
					model.addAttribute("vipList", null);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "recharge/recharge";
		}
		
		
		/**
		 * 判断用户输入支付密码是否正确
		 * @param password
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value="/judge_pay.ajax")
		public ResponseJson judge_pay(String password){
			if(Validate.isEmpty(password)){
				return ResponseJson.body(false, "支付密码不能为空！请重新输入。。");
			}
			
			//password = MD5.MD5Encode(password);
			
			RequestContent content = new RequestContent();
			
			HashMap<String,Object> map  = new HashMap<String, Object>();
			map.put("wxCode",this.authService.getAuth().getWxCode());
			
			content.setHead(null);
			content.setBody(map);
			
			try {
				ResponseContent responseContent = HttpUtil.getInputStreamByPost("/vip/judgepaypwd.htm",content, "utf-8");
				if(null != responseContent.getBody()){
					Map<?,?> maps = (Map<?, ?>) responseContent.getBody();
					String secret = maps.get("memberPaysecret").toString();
					
					password = this.authService.genPwd(password, secret, true);
					
					String passwords = maps.get("memberPaypwd").toString();
					
					if(password.equals(passwords)){
						return ResponseJson.body(true,"用户支付密码正确！");
					}else{
						return ResponseJson.body(false,"支付密码输入错误，请重新输入！");
					}
				}else{
					return ResponseJson.body(false,"用户信息为空！");
				}
			} catch (Exception e) {
				return ResponseJson.body(false,"系统繁忙，请稍候再试。。");
			}
		}
		
		
		/**
		 * 充值向数据表插入状态为未支付的数据
		 * @param money
		 * @return
		 */
		@ResponseBody
		@RequestMapping("/save_logs.ajax")
		public Object save_logs(String money,String greade){
			if(Validate.isEmpty(money)){
				return ResponseJson.body(false, "金额不能为空！");
			}
			RequestContent content = new RequestContent();
			
			HashMap<String,Object> map  = new HashMap<String, Object>();
			
			String sn = RandCode.getOrderNum();
			
			map.put("wxCode", this.authService.getAuth().getWxCode());
			map.put("money", MoneyUtil.getDollarToCent(money));
			map.put("type", "WXPAY");
			map.put("sn", sn);
			map.put("explain", "充值");
			map.put("greade", greade);
			
			content.setHead(null);
			content.setBody(map);
			
			try {
				ResponseContent responseContent = HttpUtil.getInputStreamByPost("/vip/save_logs.htm",content, "utf-8");
				if(null != responseContent.getBody()){
					logger.info("充值插入数据返回参数："+responseContent.getBody());
					Map<?,?> maps = (Map<?, ?>) responseContent.getBody();
					String state = maps.get("state").toString();
					if("Y".equals(state)){
						return ResponseJson.body(true, sn, "wxpay");
					}
				}
				return ResponseJson.body(false,"充值失败！请稍候再试。。");
			} catch (Exception e) {
				return ResponseJson.body(false,"系统繁忙，请稍候再试。。");
			}
		}
		
		
		@ResponseBody
		@RequestMapping("/judge_vip.ajax")
		public Object judge_vip(String money){
			
			if(Validate.isEmpty(money)){
				return ResponseJson.body(false, "请输入金额。。");
			}
			try{
				double _money = Double.parseDouble(money);
				RequestContent content = new RequestContent();
				
				HashMap<String,Object> map  = new HashMap<String, Object>();
				map.put("status", "normal");
				/*map.put("wxCode",this.authService.getAuth().getWxCode());*/
				
				content.setHead(null);
				content.setBody(map);
				String vip = "VIP0";String greade = "0";
				try {
					ResponseContent res = HttpUtil.getInputStreamByPost("/vip/list.htm",content, "utf-8");
					if(null != res.getBody()){
						Map<?,?> maps = (Map<?, ?>) res.getBody();
						System.out.println("VIP等级"+maps.toString());
						@SuppressWarnings("unchecked")
						List<JSONObject> json = (List<JSONObject>) maps.get("vipCard");
						for (int i = 0; i < json.size(); i++) {
							JSONObject _json = json.get(i);
							double money_1 = Double.parseDouble(_json.get("require").toString());
							logger.info("当前金额："+money_1+"当前等级："+_json.get("name").toString());
							if(_money >= money_1){
								vip = _json.get("name").toString();
								greade = _json.getString("greade");
								logger.info("当前充值的vip卡等级"+vip+"greade:"+greade);
							}
							/*if(i >=1){
								if(_money > Integer.parseInt(json.get(i-1).getString("require"))){
									vip = json.get(i-1).getString("name");
									greade = json.get(i-1).getString("greade");
									logger.info("当前充值的vip卡等级"+vip+"greade:"+greade);
								}
							}
							
							if(_money > money_1 && (i+1) <= json.size()){
								vip = _json.get("name").toString();
								greade = _json.getString("greade");
								logger.info("当前充值等级："+vip+"greade:"+greade);
							}*/
						}
					}
					logger.info("最后比对vip等级为："+vip);
					return ResponseJson.body(true, vip,greade);
				} catch (Exception e) {
					return ResponseJson.body(false, "系统繁忙，稍候再试。。。");
				}
			}catch(Exception e){
				return ResponseJson.body(false);
			}
		}
		
		/**
		 * 进入充值支付页面
		 * @param money
		 * @param model
		 * @return
		 */
		@RequestMapping("/pay/{sn}.htm")
		public String pay(@PathVariable String sn,ModelMap model,String url){
			RequestContent content = new RequestContent();
			
			HashMap<String,Object> map  = new HashMap<String, Object>();
			map.put("sn", sn);
			content.setBody(map);
			content.setHead(null);
			try {
				ResponseContent res = HttpUtil.getInputStreamByPost("/vip/querySn.htm",content, "utf-8");
				if(null != res.getBody()){
					Map<?,?> maps = (Map<?, ?>) res.getBody();
					model.addAttribute("vipList", maps);
				}
				model.addAttribute("url", URLDecoder.decode(url,"UTF-8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "recharge/recharge_pay";
		}
		
		
		
	 	@RequestMapping("/querybalanceByUser.htm")
	    public String querybalanceByUser( ModelMap modelMap) {
	       /* Map<String,Object> paramMap = new HashMap<String,Object>();
	        RequestContent content = new RequestContent();
	        paramMap.put("state",2);
	        paramMap.put("memberId",2);
	        paramMap.put("page", 1);
	        content.setBody(paramMap);
	        try {
	            ResponseContent responseContent = HttpUtil.getInputStreamByPost("/vip/lanceList.htm",content, "utf-8");

	            if(null != responseContent.getBody()){
	                JSONArray result = (JSONArray)  responseContent.getBody();
	                modelMap.put("result",result);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }*/
	        return "user/yueMingxi";
	    }
	 	
	 	@SuppressWarnings("unchecked")
	 	@ResponseBody
		@RequestMapping("/querybalanceByUser.ajax")
	    public Object querybalance(Integer page) {
	        Map<String,Object> paramMap = new HashMap<String,Object>();
	        RequestContent content = new RequestContent();
	        paramMap.put("state",2);
	        paramMap.put("wxCode",this.authService.getAuth().getWxCode());
	        paramMap.put("page", page);
	        content.setBody(paramMap);
	        try {
	            ResponseContent responseContent = HttpUtil.getInputStreamByPost("/vip/lanceList.htm",content, "utf-8");

	            if(null != responseContent.getBody()){
	            	System.out.println(responseContent.getBody());
	                List<JSONArray> result = (List<JSONArray>) responseContent.getBody();
	                return ResponseJson.body(true, "", result);
	            }
	            return ResponseJson.body(false, "没有相关数据！");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseJson.body(false, "数据请求失败！");
	        }
	    }
}
