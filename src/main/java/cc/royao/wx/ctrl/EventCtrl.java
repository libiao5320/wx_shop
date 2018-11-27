package cc.royao.wx.ctrl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.royao.commons.ResponseJson;
import cc.royao.commons.auth.AuthService;
import cc.royao.commons.formbean.MapVo;
import cc.royao.commons.httpClient.HttpUtil;
import cc.royao.commons.httpClient.RequestContent;
import cc.royao.commons.httpClient.ResponseContent;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * ClassName: EventCtrl 
 * @Description: 商家活动
 * @author Liu Pinghui
 * @date 2016年1月4日
 */
@Controller
@RequestMapping("/event")
public class EventCtrl {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private AuthService authService;
//	/goods/goodsOrder/${goods.goodsId}.htm
	
	
	/**
	 * 商品订单页入口
	 * @param goodsId
	 * @param map
	 * @return
	 */
	@RequestMapping("/eventOrder/{eventId}.htm")
	public String goodsOrder(@PathVariable Long eventId, ModelMap map){

		if(null ==eventId || "".equals(eventId)){
			return "event/list.htm";
		}
		logger.info("活动订单:"+eventId);
		/**判断该会员是否登录**/
		if(this.authService.getAuth().getId()==null || "".equals(this.authService.getAuth().getId())){
			logger.info("用户未注册");
			return "login";
		}
		Long storeId=1L;//默认取1
		String isPtRed="Y";
		Long memberId=this.authService.getAuth().getId();
		
		HashMap<String , Object> hashMap = new HashMap<String, Object>();
		MapVo mapVo = new MapVo();
		
		/****活动详情**/
		String urlPath = "/event/detail.htm";//活动详情数据
		hashMap.put("id", eventId);//活动id
		
		mapVo.setMap(hashMap);
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());

		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			
			if(null != responseContent.getBody()){
				JSONObject event = (JSONObject) responseContent.getBody();
				
				if(StringUtils.isNotBlank(event.get("storeId")+"") && !"".equals(event.get("storeId")+"")
						&& !"null".equals(event.get("storeId")+"") && !"NULL".equals(event.get("storeId")+"")){
					storeId= Long.parseLong(event.get("storeId").toString());//店铺id
				}
				if(StringUtils.isNotBlank(event.get("isPtRed")+"") && !"".equals(event.get("isPtRed")+"")
						&& !"null".equals(event.get("isPtRed")+"") && !"NULL".equals(event.get("isPtRed")+"")){
					
					isPtRed=event.get("isPtRed").toString();//订单类型id
				}
				map.put("event", event);
			}else{
				map.put("event", null);
			}
		} catch (Exception e) {
			logger.error("异常："+e.getMessage());
		}
		
		/****商品详情end**/
		
		/**商户可使用红包数**/
		logger.info("商户可使用红包数  storeId: "+storeId);
		 
		urlPath = "/distribute/queryBymemberIdAndKy.htm";//商品详情数据
		hashMap.put("memberId",memberId);
		hashMap.put("storeId",storeId);
		hashMap.put("isPtRed", isPtRed);
		
		mapVo.setMap(hashMap);
		//条件参数
		content = new RequestContent();
		content.setBody(mapVo.getMap());
	
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			
			if(null != responseContent.getBody()){
				logger.info("普通红包");
				List<JSONObject> red = (List<JSONObject>) responseContent.getBody();
				map.put("ptRed", red);//普通红包
			}else{
				map.put("ptRed", null);//普通红包
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("异常："+e.getMessage());
		}
		
		/**商户可使用红包数end**/
		
		return "event/eventOrder";
	}
	
	/**
	 * 
	 * @Description: 获取活动列表
	 * @param @param map
	 * @param @param mapVo
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月13日
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/list.htm")
	public String list(ModelMap map,MapVo mapVo){
		
		String urlPaht = "/event/list.htm";
		
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
	
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPaht,content, "utf-8");
			
			if(null != responseContent.getBody()){
				List<JSONObject> json = (List<JSONObject>) responseContent.getBody();
				
				map.put("list", json);
			}else{
				map.put("list", null);
			}
		} catch (Exception e) {
			logger.info("请求活动列表失败", e);
		}
		
		return "event/list";
	}
	
	@ResponseBody
	@RequestMapping("/list.ajax")
	public ResponseJson listajax(MapVo mapVo){
		
		String urlPaht = "/event/list.htm";
		
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
	
		System.out.println(mapVo.getMap().get("pageNo"));
		
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPaht,content, "utf-8");
			
			if(null != responseContent.getBody()){
				List<JSONObject> json = (List<JSONObject>) responseContent.getBody();
				return ResponseJson.body(true, "success", json);
			}else{
				return ResponseJson.body(true, "已经没有更多了");
			}
		} catch (Exception e) {
			logger.info("请求活动列表失败", e);
			return ResponseJson.body(false, "网络忙，请稍后再试");
		}
	}
	
	/**
	 * 
	 * @Description: 获取活动详情
	 * @param @param id
	 * @param @param map
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月13日
	 */
	@RequestMapping("/detail.htm")
	public String detail(Long id  , ModelMap map){
		
		String urlPath = "/event/detail.htm";
		Long storeId=1L;//默认取1
		
		HashMap<String , Object> hashMap = new HashMap<String, Object>();
		hashMap.put("id", id);
		MapVo mapVo = new MapVo();
		mapVo.setMap(hashMap);
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
	
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			
			if(null != responseContent.getBody()){
				JSONObject event = (JSONObject) responseContent.getBody();
				
				if(StringUtils.isNotBlank(event.get("storeId")+"") && !"".equals(event.get("storeId")+"")
						&& !"null".equals(event.get("storeId")+"") && !"NULL".equals(event.get("storeId")+"")){
					storeId= Long.parseLong(event.get("storeId").toString());//店铺id
				}
				
				map.put("event", event);
			}else{
				//为空时做特殊处理
				map.put("event", null);
			}
		} catch (Exception e) {
			logger.info("请求活动详情失败", e);
		}
		/**活动end**/
		
		Long memberId = null;// 获取当前登录的用户ID
		logger.info("memberId:"+this.authService.getAuth().getId());
		if (null != this.authService.getAuth()) {
			memberId = this.authService.getAuth().getId();
		}
		
		if (null == memberId) {
			map.put("isKnow", false);
		}else{
			
			hashMap.put("memberId", memberId);
			try {
				ResponseContent responseContent = HttpUtil.getInputStreamByPost("/member/eventIsKnow.htm",content, "utf-8");
				
				if(null != responseContent.getBody()){
					JSONObject event = (JSONObject) responseContent.getBody();
					
					if(StringUtils.isNotBlank(event.get("eventIsKnow")+"") && event.get("eventIsKnow").equals("Y")){
						map.put("isKnow", true);
					}
				}else{
					map.put("isKnow", false);
				}
			} catch (Exception e) {
				logger.info("请求活动是否知道失败", e);
			}
		}
		
		return "event/detail";
	}
	
	/**
	 * 
	 * @Description: 我知道了-分享有礼
	 * @param @return   
	 * @return ResponseJson  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月25日
	 */
	@ResponseBody
	@RequestMapping("/isKnow.ajax")
	public ResponseJson isknow(){
		
		Long memberId = null;// 获取当前登录的用户ID
		logger.info("memberId:"+this.authService.getAuth().getId());
		if (null != this.authService.getAuth()) {
			memberId = this.authService.getAuth().getId();
		}
		
		if (null == memberId) {
			return ResponseJson.body(false, "login");
		}else{
			HashMap<String , Object> hashMap = new HashMap<String, Object>();
			hashMap.put("memberId", memberId);
			RequestContent content = new RequestContent();
			content.setBody(hashMap);
			try {
				ResponseContent responseContent = HttpUtil.getInputStreamByPost("/member/eventIknow.htm",content, "utf-8");
				
				if(null != responseContent.getBody()){
					JSONObject event = (JSONObject) responseContent.getBody();
					
					if(event != null && event.get("result").equals(true)){
						return ResponseJson.body(true, "success");
					}else{
						return ResponseJson.body(false, "error");
					}
				}else{
					return ResponseJson.body(false, "error");
				}
			} catch (Exception e) {
				logger.info("请求活动详情失败", e);
			}
		}
		return ResponseJson.body(false, "error");
	}
	
	/**
	 * 
	 * @Description: 请求活动评价列表
	 * @param @param map
	 * @param @param eventId
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月26日
	 */
	@RequestMapping("/commentlist.htm")
	public String commentlist(ModelMap map,Long eventId){
		
		String urlPath = "/event/commentlist.htm";
		
		HashMap<String , Object> hashMap = new HashMap<String, Object>();
		hashMap.put("eventId", eventId);
		RequestContent content = new RequestContent();
		content.setBody(hashMap);
	
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			
			if(null != responseContent.getBody()){
				List<JSONObject> list =  (List<JSONObject>) responseContent.getBody();
				if(list != null && list.size() > 0){
					map.put("list", list);
				}
			}
		}catch(Exception e){
			logger.info("获取评论列表失败", e);
		}
		
		map.put("eventId", eventId);
		return "/event/commentlist";
	}
	
	/**
	 * 
	 * @Description: 请求活动评价列表
	 * @param @param map
	 * @param @param eventId
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月26日
	 */
	@ResponseBody
	@RequestMapping("/commentlist.ajax")
	public ResponseJson commentlistajax(MapVo mapVo){
		
		String urlPath = "/event/commentlist.htm";
		
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
		
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			
			if(null != responseContent.getBody()){
				List<JSONObject> list =  (List<JSONObject>) responseContent.getBody();
				if(list != null && list.size() > 0){
					return ResponseJson.body(true, "success", list);
				}
			}
		}catch(Exception e){
			logger.info("获取评论列表失败", e);
		}
		return ResponseJson.body(false, "网络忙，请稍后重试");
	}
}
