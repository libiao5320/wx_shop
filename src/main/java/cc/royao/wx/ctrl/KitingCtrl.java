package cc.royao.wx.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.royao.commons.ResponseJson;
import cc.royao.commons.auth.AuthService;
import cc.royao.commons.httpClient.HttpUtil;
import cc.royao.commons.httpClient.RequestContent;
import cc.royao.commons.httpClient.ResponseContent;
import cc.royao.commons.logger.SysLogger;
import cc.royao.commons.utils.MoneyUtil;


@Controller
@RequestMapping("/kiting")
public class KitingCtrl {
	
		@SysLogger
		private Logger logger;
		
		@Autowired
		private AuthService authService;
		
		/**
		 * 判断用户是否可以提现
		 * @param request
		 * @param response
		 * @return
		 */
		@ResponseBody
		@RequestMapping("/index.ajax")
		public Object index(HttpServletRequest request,HttpServletResponse response){
			RequestContent content = new RequestContent();
			Map<String,Object> map  = new HashMap<String, Object>();
			map.put("wxCode",this.authService.getAuth().getWxCode());
			
			content.setBody(map);
			content.setHead(null);
			String goal_money = "50";
			try {
				ResponseContent res = HttpUtil.getInputStreamByPost("/kiting/judge.htm",content, "utf-8");
				if(null != res.getBody()){
					Map<?,?> maps = (Map<?, ?>) res.getBody();
					String status = maps.get("status").toString();
					goal_money = maps.get("goal_money").toString();
					if("Y".equals(status)){
						String money = maps.get("money").toString();
						return ResponseJson.body(true, "",money);
					}
				}
				return ResponseJson.body(false, "您的账户余额大于"+goal_money+"元，暂时不能提现，再去逛逛吧。");
			} catch (Exception e) {
				return ResponseJson.body(false, "系统繁忙，请稍候再试。。");
			}
		}
		
		
		@RequestMapping("/kiting/{money}.htm")
		public String kiting(@PathVariable String money,ModelMap model){
			model.addAttribute("money", MoneyUtil.getCentToDollar(Long.parseLong(money)));
			return "kiting/index";
		}
		
		/**
		 * 提交申请提现
		 * @param money
		 * @param request
		 * @param response
		 * @return
		 */
		@ResponseBody
		@RequestMapping("/save_kiting/{money}.ajax")
		public Object save_kiting(@PathVariable String money,HttpServletRequest request,HttpServletResponse response){
			if("0".equals(money)){
				return ResponseJson.body(false, "提现金额不能为0元！");
			}
			
			RequestContent content = new RequestContent();
			Map<String,Object> map  = new HashMap<String, Object>();
			map.put("wxCode",this.authService.getAuth().getWxCode());
			map.put("money", money);
			
			content.setBody(map);
			content.setHead(null);
			
			String status="",msg="";
			try {
				ResponseContent  res = HttpUtil.getInputStreamByPost("/kiting/save_kiting.htm",content, "utf-8");
				if(null != res.getBody()){
					Map<?,?> resMap = (Map<?, ?>) res.getBody();
					status = resMap.get("status").toString();
					msg = resMap.get("msg").toString();
				}
				if("Y".equals(status)){
					return ResponseJson.body(true);
				}else{
					return ResponseJson.body(false, msg);
				}
			} catch (Exception e) {
				logger.info("连接错误");
				return ResponseJson.body(false, "连接失败。。。");
			}
			
		}
		
		/**
		 * 跳转
		 * @return
		 */
		@RequestMapping("/handle.htm")
		public String handle(){
			return "kiting/handle";
		}
		
		
		@RequestMapping("/record.htm")
		public String recordHtml(HttpServletRequest request,HttpServletResponse response){
			
			return "kiting/record";
		}
		
		/**
		 * 
		 * @param request
		 * @param response
		 * @param page
		 * @return
		 */
		@SuppressWarnings("unchecked")
		@ResponseBody
		@RequestMapping("/record.ajax")
		public Object record(HttpServletRequest request,HttpServletResponse response,Integer page){
			page = page == null?1:page;
			RequestContent content = new RequestContent();
			Map<String,Object> map  = new HashMap<String, Object>();
			map.put("wxCode",this.authService.getAuth().getWxCode());
			map.put("page", page);
			
			content.setBody(map);
			content.setHead(null);
			
			try {
				ResponseContent  res = HttpUtil.getInputStreamByPost("/kiting/kiting_record.htm",content, "utf-8");
				if(null != res.getBody()){
					logger.info("Center返回数据："+res.getBody());
					List<JSONArray> kiting = (List<JSONArray>) res.getBody();
					return ResponseJson.body(true, "", kiting);
				}
				return ResponseJson.body(false, "没有相关数据！");
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseJson.body(false, "数据请求失败！");
			}
		}
}
