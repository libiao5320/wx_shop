package cc.royao.wx.ctrl;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cc.royao.commons.CConfig;
import cc.royao.commons.ResponseJson;
import cc.royao.commons.auth.AuthService;
import cc.royao.commons.formbean.MapVo;
import cc.royao.commons.httpClient.HttpUtil;
import cc.royao.commons.httpClient.RequestContent;
import cc.royao.commons.httpClient.ResponseContent;

/**
 * 
 * ClassName: StoreCtrl 
 * @Description: 商家管理
 * @author Liu Pinghui
 * @date 2016年1月5日
 */
@Controller
@RequestMapping("/store")
public class StoreCtrl {

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping("/list.htm")
	public String list(ModelMap map){
		
		/****商家列表***/
		String urlPath = "/store/list.htm";//商家列表
		
		HashMap<String , Object> hashMap = new HashMap<String, Object>();
		hashMap.put("status", "normal");//正常
		hashMap.put("storeState", "1");//开启
		hashMap.put("pageNo", 1);
		MapVo mapVo = new MapVo();
		mapVo.setMap(hashMap);
		
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
		/*try{
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			List<JSONObject> list=null;
			if(null != responseContent.getBody()){
				list = (List<JSONObject>) responseContent.getBody();
			}
			map.put("list", list);
		}catch(Exception e){
			logger.info("商家列表获取失败！",e);
		}
		
		***商家列表 end***/
		
		/****分类**/
		urlPath = "/groupbuy/list.htm";//分类列表
		
		hashMap = new HashMap<String, Object>();
		hashMap.put("parentId", 1);//1-产品分类
		mapVo = new MapVo();
		mapVo.setMap(hashMap);
		
		content = new RequestContent();
		content.setBody(mapVo.getMap());
		try{
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			List<JSONObject> list=null;
			if(null != responseContent.getBody()){
				list = (List<JSONObject>) responseContent.getBody();
			}
			map.put("gclist", list);
		}catch(Exception e){
			logger.info("商家列表获取失败！",e);
		}
		
		/****分类end**/
		map.put("GIU", CConfig.storeImgUrl);
		return "store/list";
	}
	
	/**
	 * 
	 * @Description: 商家详情
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月6日
	 */
	@RequestMapping("detail.htm")
	public String detail(ModelMap map,Long id, String distance, HttpServletRequest request){
		
		Long memberId = this.authService.getAuth().getId();
		
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
				
				/*****判断是否已经收藏******/
				if(null != memberId){
					hashMap.put("memberId", memberId);
					hashMap.put("type", "store");
					hashMap.put("commonId", json.get("storeId"));
					mapVo.setMap(hashMap);
					content.setBody(mapVo.getMap());
					responseContent = HttpUtil.getInputStreamByPost("/collect/isCollect.htm",content, "utf-8");
					if(null != responseContent.getBody()){
						List<JSONObject> jsonList = (List<JSONObject>)responseContent.getBody();
						//说明已经收藏了
						map.put("isCollect", true);
						
						if(jsonList != null && jsonList.size() > 0 && StringUtils.isNotBlank(jsonList.get(0).get("id")+"")){
							map.put("collectId", jsonList.get(0).get("id"));
						}
						
					}else{
						map.put("isCollect", false);
						map.put("collectId", null);
					}
				}else{
					map.put("isCollect", false);
					map.put("collectId", null);
				}
				/*****判断是否已经收藏******/
			}
			map.put("store", json);
		}catch(Exception e){
			
		}
		
		if(null != memberId){
			urlPath = "/member/getById.htm";
			hashMap.put("memberId", memberId);
			mapVo.setMap(hashMap);
			content.setBody(mapVo.getMap());
			
			try{
				ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
				JSONObject json=null;
				if(null != responseContent.getBody()){
					json = (JSONObject) responseContent.getBody();
					if(null != json){
						map.put("vip", json.get("vipRankId"));
					}else{
						map.put("vip", null);
					}
				}
				
			}catch(Exception e){
				map.put("vip", null);
			}
		}else{
			map.put("vip", null);
		}
		
		map.put("distance", distance);
		//分享链接
		map.put("shareurl",request.getRequestURL().toString()+"?id="+id);
	
		return "store/detail";
	}
	
	/**
	 * 
	 * @Description: 商家界面-所有商品
	 * @param @param map
	 * @param @param id
	 * @param @param storeName
	 * @param @param pageNo
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月26日
	 */
	@RequestMapping("/goodslist.htm")
	public String goodslist(ModelMap map,Long id,String storeName,Integer pageNo){
		
		Long memberId = null;//获取当前登录的用户ID
		
		if(null != this.authService.getAuth()){
			memberId = this.authService.getAuth().getId();
		}else{
			return "/userCenter.htm";//重定向到用户中心
		}
		
		if(null == memberId){
			return "/userCenter.htm";//重定向到用户中心
		}
		
		String urlPath = "/store/goodslist.htm";//商家所有产品
		
		HashMap<String , Object> hashMap = new HashMap<String, Object>();
		hashMap.put("id", id);
		hashMap.put("goodsState", 2);//上架的产品
		hashMap.put("pageNo", pageNo);//
		MapVo mapVo = new MapVo();
		mapVo.setMap(hashMap);
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
		
		try{
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			List<JSONObject> list=null;
			if(null != responseContent.getBody()){
				list = (List<JSONObject>) responseContent.getBody();
			}
			
			map.put("list", list);
			map.put("storeId", id);
		}catch(Exception e){
			
		}
		
		//获取用户信息
		urlPath = "/member/getById.htm";
		hashMap.put("memberId", memberId);
		mapVo.setMap(hashMap);
		content.setBody(mapVo.getMap());
		
		try{
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			JSONObject json=null;
			if(null != responseContent.getBody()){
				json = (JSONObject) responseContent.getBody();
				if(null != json){
					map.put("vip", json.get("vipRankId"));
				}else{
					map.put("vip", null);
				}
			}
			
		}catch(Exception e){
			
		}
		
		map.put("storeName", storeName);
		return "store/goodslist";
	}
	
	/**
	 * 
	 * @Description: 商家界面-全部评论
	 * @param @param map
	 * @param @param id
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月26日
	 */
	@RequestMapping("/evaluatelist.htm")
	public String evaluatelist(ModelMap map,Long id){
		
		String urlPath = "/evaluate/list.htm";//商家所有产品
		
		HashMap<String , Object> hashMap = new HashMap<String, Object>();
		hashMap.put("storeId", id);//商家ID
		hashMap.put("isShow", "Y");
		//hashMap.put("goodsState", 2);//上架的产品
		//hashMap.put("pageNo", pageNo);//分页信息
		MapVo mapVo = new MapVo();
		mapVo.setMap(hashMap);
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
		
		try{
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			List<JSONObject> list=null;
			if(null != responseContent.getBody()){
				list = (List<JSONObject>) responseContent.getBody();
			}
			
			map.put("list", list);
		}catch(Exception e){
			logger.info("请求评论列表出错", e);
		}
		
		map.put("storeId", id);
		return "store/evaluatelist";
	}
	
	/**
	 * 
	 * @Description: 商家界面-全部评论
	 * @param @param map
	 * @param @param id
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月26日
	 */
	@ResponseBody
	@RequestMapping("/evaluatelist.ajax")
	public ResponseJson evaluatelistajax(MapVo mapVo){
		
		String urlPath = "/evaluate/list.htm";//商家所有产品
		
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
		
		try{
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			List<JSONObject> list=null;
			if(null != responseContent.getBody()){
				list = (List<JSONObject>) responseContent.getBody();
				if(list != null && list.size() > 0){
					return ResponseJson.body(true, "success", list);
				}
			}
			
		}catch(Exception e){
			logger.info("请求评论列表出错", e);
		}
		
		return ResponseJson.body(false, "没有更多了");
	}
	
	/**
	 * 
	 * @Description: 组合条件查询
	 * @param @param map
	 * @param @param id
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月13日
	 */
	@ResponseBody
	@RequestMapping(value="/list.ajax",method=RequestMethod.POST)
	public ResponseJson list(MapVo map){
		
		String urlPath = "/store/nearby.htm";
		
		RequestContent content = new RequestContent();
		content.setBody(map.getMap());
		
		try{
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			List<JSONObject> list=null;
			if(null != responseContent.getBody()){
				list = (List<JSONObject>) responseContent.getBody();
				if(list != null && list.size() > 0){
					return ResponseJson.body(true,"success",list);
				}else{
					return ResponseJson.body(false, "没有更多数据了");
				}
			}
			return ResponseJson.body(false, "没有更多数据了");
		}catch(Exception e){
			logger.info("请求评论列表出错", e);
		}
		
		return ResponseJson.body(false, "网络忙，请稍后重试");
	}
	
	@RequestMapping("/text/{id}.htm")
	public String text(ModelMap map,@PathVariable Long id){
		
		RequestContent content = new RequestContent();
		HashMap<String, Object> hmap = new HashMap<String, Object>();
		hmap.put("id", id);
		MapVo mapVo = new MapVo();
		mapVo.setMap(hmap);
		content.setBody(mapVo.getMap());
		
		try{
			ResponseContent responseContent = HttpUtil.getInputStreamByPost("/store/text.htm",content, "utf-8");
			JSONObject json=null;
			if(null != responseContent.getBody()){
				json = (JSONObject) responseContent.getBody();
				map.put("json", json);
			}else{
				map.put("json", null);
			}
		}catch(Exception e){
			logger.info("请求图文详情出错", e);
		}
		
		return "store/textdetail";
	}
	
	@ResponseBody
	@RequestMapping("/share.ajax")
	public ResponseJson share(){
		
		Long memberId = null;//获取当前登录的用户ID
		
		if(null != this.authService.getAuth()){
			memberId = this.authService.getAuth().getId();
		}else{
			return ResponseJson.body(false, "login");
		}
		
		if(null == memberId){
			return ResponseJson.body(false, "login");
		}
		
		RequestContent content = new RequestContent();
		HashMap<String, Object> hmap = new HashMap<String, Object>();
		hmap.put("memberId", memberId);
		MapVo mapVo = new MapVo();
		mapVo.setMap(hmap);
		content.setBody(mapVo.getMap());
		
		try{
			ResponseContent responseContent = HttpUtil.getInputStreamByPost("/store/share.htm",content, "utf-8");
			if(null != responseContent.getBody()){//说明得到一个分享红包
				return ResponseJson.body(true, "恭喜您获得一个分享红包，去我的红包即可查看");
			}else{
				return ResponseJson.body(false, "分享成功");
			}
		}catch(Exception e){
			logger.info("分享店铺获取红包时出错", e);
		}
		
		return ResponseJson.body(false, "分享成功");
	}
	
}
