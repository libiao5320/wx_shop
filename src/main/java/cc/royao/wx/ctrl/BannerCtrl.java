package cc.royao.wx.ctrl;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

import cc.royao.commons.formbean.MapVo;
import cc.royao.commons.httpClient.HttpUtil;
import cc.royao.commons.httpClient.RequestContent;
import cc.royao.commons.httpClient.ResponseContent;

@Controller
@RequestMapping("/banner")
public class BannerCtrl {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping("/index.htm")
	public String index(ModelMap map,Long id){
		
		String urlPath = "/banner/getById.htm";
		
		HashMap<String , Object> hashMap = new HashMap<String, Object>();
		hashMap.put("id", id);
		MapVo mapVo = new MapVo();
		mapVo.setMap(hashMap);
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
		
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			
			if(null != responseContent.getBody()){
				JSONObject json = (JSONObject) responseContent.getBody();
				
				if(json != null){
					try{
						
						if(StringUtils.isNotBlank(json.get("redpacketsId")+"")){
							//说明该活动为红包活动
							hashMap.put("id", json.get("redpacketsId"));
							content.setBody(hashMap);
							urlPath = "/redpackets/getById.htm";//请求红包信息
							responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
							if(null != responseContent.getBody()){
								map.put("redpackets", json);
							}else{
								map.put("redpackets", null);
							}
							return "redpackets/homeEvent";	
						}else{
							//说明该活动为商家自定义活动
							return "redirect:"+json.get("url");
						}
					}catch (Exception e) {
						logger.info("Json转banner错误/n",e);
					}
				}
			}
		} catch (Exception e) {
			logger.info("Json转banner错误/n",e);
		}
		
		return null;//可以跳转到错误处理页面页面
	}
}
