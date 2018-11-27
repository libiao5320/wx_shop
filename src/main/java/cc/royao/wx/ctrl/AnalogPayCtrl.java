package cc.royao.wx.ctrl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.royao.commons.ResponseJson;
import cc.royao.commons.auth.AuthService;
import cc.royao.commons.auth.AuthUser;
import cc.royao.commons.httpClient.HttpUtil;
import cc.royao.commons.httpClient.RequestContent;
import cc.royao.commons.httpClient.ResponseContent;
import cc.royao.commons.logger.SysLogger;

/**
 * 模拟支付
 * @author 杨鑫
 *@AnalogPayCtrl.java
 *@email 450160147@qq.com
 *@time 2016年2月3日
 */
@Controller
@RequestMapping
public class AnalogPayCtrl {

	
			
		@SysLogger
		private Logger logger; 
		
		@Autowired
		private AuthService authService;
		
		
		@ResponseBody
		@RequestMapping("/analog_pay/{sn}.ajax")
		public Object analog(@PathVariable String sn,HttpServletRequest request,HttpServletResponse response){
			RequestContent content = new RequestContent();
			
			AuthUser auth = this.authService.getAuth();
			
			HashMap<String,Object> map  = new HashMap<String, Object>();
			map.put("sn",sn);
			map.put("wxpay_no", "MN"+System.currentTimeMillis());
			
			content.setHead(null);
			content.setBody(map);
			try{
				ResponseContent res = HttpUtil.getInputStreamByPost("/wxpay/refer.htm",content, "utf-8");
				
				if(null != res.getBody()){
					Map<?,?>  maps = (Map<?, ?>) res.getBody();
					int state = Integer.parseInt(maps.get("state").toString());
					if(state == 1){
						ResponseContent res_1 = HttpUtil.getInputStreamByPost("/wxpay/wx_update.htm",content, "utf-8");
						if(null != res_1.getBody()){
							Map<?,?> map_1 = (Map<?, ?>) res_1.getBody();
							String state_1 = map_1.get("state").toString();
							logger.info("修改数据库DbalanceLogs和Dmember的金额返回的状态："+state_1);
							if("Y".equals(state_1)){
								long ryvip = Long.parseLong(map_1.get("rvip").toString());
								this.authService.destroyAuth();
								auth.setVipRankId(ryvip);
								this.authService.setAuth(auth);
								return ResponseJson.body(true, "充值成功");
							}
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return ResponseJson.body(false, "充值失败");
		}
}
