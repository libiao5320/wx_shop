package cc.royao.wx.ctrl;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cc.royao.commons.formbean.MapVo;
import cc.royao.commons.httpClient.HttpUtil;
import cc.royao.commons.httpClient.RequestContent;
import cc.royao.commons.httpClient.ResponseContent;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * ClassName: OrderDetailCtrl 
 * @Description:处理订单详情
 * @author Liu Pinghui
 * @date 2016年1月15日
 */

@Controller
@RequestMapping("/order/detail")
public class OrderDetailCtrl {

	Logger logger = Logger.getLogger(this.getClass());
	
	
	@RequestMapping("/{id}.htm")
	public String detail(@PathVariable Long id,ModelMap map){
		
		HashMap<String , Object> hashMap = new HashMap<String, Object>();
		MapVo mapVo = new MapVo();
		hashMap.put("id", id);//订单id
		
		mapVo.setMap(hashMap);
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
	
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost("/order/detail/detail.htm",content, "utf-8");
			
			if(null != responseContent.getBody()){
				JSONObject resut = (JSONObject) responseContent.getBody();
				
				map.put("order", resut.get("order"));
				map.put("eval", resut.get("eval"));
				if(resut.get("complain") != null){
					JSONObject complain = (JSONObject) resut.get("complain");
					map.put("complain", complain);
				}
			}else{
				map.put("order", null);
			}
		} catch (Exception e) {
			logger.error("获取订单详情异常："+e);
		}
		
		return "order/detail";
	}
}
