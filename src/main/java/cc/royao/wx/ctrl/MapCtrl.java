package cc.royao.wx.ctrl;

import java.text.DecimalFormat;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cc.royao.commons.formbean.MapVo;
import cc.royao.commons.httpClient.HttpUtil;
import cc.royao.commons.httpClient.RequestContent;
import cc.royao.commons.httpClient.ResponseContent;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/map")
public class MapCtrl {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 
	 * @Description: 商家地图
	 * @param @param id
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月11日
	 */
	@RequestMapping("/storemap.htm")
	public String storeMap(ModelMap map,Long id,String distance){
		
		String urlPath = "/store/detail.htm";//商家详情
		
		HashMap<String , Object> hashMap = new HashMap<String, Object>();
		hashMap.put("id", id);
		MapVo mapVo = new MapVo();
		mapVo.setMap(hashMap);
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
		
		try{
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			JSONObject json=null;
			if(null != responseContent.getBody()){
				json = (JSONObject) responseContent.getBody();
			}
			map.put("store", json);
		}catch(Exception e){
			logger.info("商家地图，信息获取失败", e);
		}
		
		Double dist = null;
		if(StringUtils.isNotBlank(distance)){
			DecimalFormat df = new DecimalFormat("######0.00");
			dist = Double.valueOf(distance);
			dist = dist/1000;
			
			map.put("distance", df.format(dist).toString());
		}else{
			map.put("distance", null);
		}
		
		
		return "map/storemap";
	}
	
	@RequestMapping("/goodsmap.htm")
	public String goodsMap(ModelMap map,Long id,String lbsX, String lbsY){
		
		String urlPath = "/store/detail.htm";//商家详情
		
		HashMap<String , Object> hashMap = new HashMap<String, Object>();
		hashMap.put("id", id);
		MapVo mapVo = new MapVo();
		mapVo.setMap(hashMap);
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
		
		try{
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			JSONObject json=null;
			if(null != responseContent.getBody()){
				json = (JSONObject) responseContent.getBody();
				
				map.put("store", json);
				/*
				System.out.println(json.get("lbsX"));
				System.out.println(json.get("lbsY"));
				System.out.println(lbsX);
				System.out.println(lbsY);
				*/
			}else
				map.put("store", json);
		}catch(Exception e){
			logger.info("商家地图，信息获取失败", e);
		}
		
		map.put("lbsX", lbsX);
		map.put("lbsY", lbsY);
		
		return "map/goodsmap";
	}
}
