package cc.royao.wx.ctrl;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cc.royao.commons.ResponseJson;
import cc.royao.commons.auth.AuthService;
import cc.royao.commons.formbean.MapVo;
import cc.royao.commons.httpClient.HttpUtil;
import cc.royao.commons.httpClient.RequestContent;
import cc.royao.commons.httpClient.ResponseContent;

/**
 * 
 * ClassName: CollectCtrl 
 * @Description: 用户收藏
 * @author Liu Pinghui
 * @date 2016年1月13日
 */
@Controller
@RequestMapping("/collect")
public class CollectCtrl {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	AuthService authService;
	/**
	 * 
	 * @Description: 收藏店铺
	 * @param @param id
	 * @param @return   
	 * @return ResponseJson  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月13日
	 */
	@ResponseBody
	@RequestMapping("/store.ajax")
	public ResponseJson store(Long id){
		
		Long memberId = 1L;
		
		if(null != this.authService.getAuth()){
			memberId = this.authService.getAuth().getId();
		}else{
			return ResponseJson.body(false, "login");
		}
		
		if(null == memberId){
			return ResponseJson.body(false, "login");
		}
		
		HashMap<String , Object> hashMap = new HashMap<String, Object>();
		hashMap.put("id", id);
		hashMap.put("memberId", memberId);
		hashMap.put("type", "store");
		MapVo mapVo = new MapVo();
		mapVo.setMap(hashMap);
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
		
		try{
			ResponseContent responseContent = HttpUtil.getInputStreamByPost("/collect/collect.htm",content, "utf-8");
			if(null != responseContent.getBody()){
				JSONObject json = (JSONObject) responseContent.getBody();
				if(json != null){
					return ResponseJson.body(true, "success",json);
				}else{
					return ResponseJson.body(false, "error");
				}
			}
		}catch (Exception e) {
			logger.info("收藏店铺失败",e);
		}
		return ResponseJson.body(false,"收藏失败");
	}
	/**
	 * 
	 * @Description: 收藏商品
	 * @param @param id
	 * @param @return   
	 * @return ResponseJson  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月13日
	 */
	@ResponseBody
	@RequestMapping("/goods.ajax")
	public ResponseJson goods(Long id){
		
		Long memberId = 1L;
		
		if(null != this.authService.getAuth()){
			memberId = this.authService.getAuth().getId();
		}else{
			return ResponseJson.body(false, "login");
		}
		
		if(null == memberId){
			return ResponseJson.body(false, "login");
		}
		
		HashMap<String , Object> hashMap = new HashMap<String, Object>();
		hashMap.put("id", id);
		hashMap.put("memberId", memberId);
		hashMap.put("type", "goods");
		MapVo mapVo = new MapVo();
		mapVo.setMap(hashMap);
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
		
		try{
			ResponseContent responseContent = HttpUtil.getInputStreamByPost("/collect/collect.htm",content, "utf-8");
			
			if(null != responseContent.getBody()){
				JSONObject json = (JSONObject) responseContent.getBody();
				if(json != null){
					return ResponseJson.body(true, "success",json);
				}else{
					return ResponseJson.body(false, "error");
				}
			}
		}catch (Exception e) {
			logger.info("收藏店铺失败",e);
		}
		return ResponseJson.body(false,"收藏失败");
	}
	
	/**
	 * 
	 * @Description: 取消收藏
	 * @param @param id
	 * @param @return   
	 * @return ResponseJson  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月13日
	 */
	@ResponseBody
	@RequestMapping("/uncollect.ajax")
	public ResponseJson uncollect(Long id){
		
		HashMap<String , Object> hashMap = new HashMap<String, Object>();
		hashMap.put("id", id);
		RequestContent content = new RequestContent();
		content.setBody(hashMap);
		
		try{
			ResponseContent responseContent = HttpUtil.getInputStreamByPost("/collect/uncollect.htm",content, "utf-8");
			
			if(null != responseContent.getBody()){
				JSONObject json = (JSONObject) responseContent.getBody();
				if(StringUtils.isNotBlank(json.get("result")+"")){
					if(json.get("result").equals(true)){
						return ResponseJson.body(true, "success");
					}else {
						return ResponseJson.body(false, "error");
					}
				}
			}
		}catch (Exception e) {
			logger.info("收藏店铺失败",e);
		}
		return ResponseJson.body(false,"收藏失败");
	}
	
	@ResponseBody
	@RequestMapping("/deteleCollect.ajax")
	public ResponseJson deteleCollect(String[] xzkk_n){
		
		HashMap<String , Object> hashMap = new HashMap<String, Object>();
		hashMap.put("id", xzkk_n);
		RequestContent content = new RequestContent();
		content.setBody(hashMap);
		
		try{
			ResponseContent responseContent = HttpUtil.getInputStreamByPost("/collect/deteleCollect.htm",content, "utf-8");
			
			if(null != responseContent.getBody()){
				JSONObject json = (JSONObject) responseContent.getBody();
				if(StringUtils.isNotBlank(json.get("result")+"")){
					if(json.get("result").equals(true)){
						return ResponseJson.body(true, "删除成功");
					}else {
						return ResponseJson.body(false, "删除失败");
					}
				}
			}
		}catch (Exception e) {
			logger.info("收藏店铺失败",e);
		}
		
		return ResponseJson.body(false, "删除失败");
	}
	
}
