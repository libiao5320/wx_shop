package cc.royao.wx.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import cc.royao.commons.ResponseJson;
import cc.royao.commons.auth.AuthService;
import cc.royao.commons.ResponseJson;
import cc.royao.commons.auth.AuthService;
import cc.royao.commons.formbean.MapVo;
import cc.royao.commons.httpClient.HttpUtil;
import cc.royao.commons.httpClient.RequestContent;
import cc.royao.commons.httpClient.ResponseContent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author yangx
 *
 * @className 商品显示主入口
 * @date 2016年1月4日 下午5:22:56
 */
@Controller
@RequestMapping("/goods")
public class GoodsCtrl {
	
	private Logger loger  = Logger.getLogger(this.getClass());
	@Autowired
	private AuthService authService;
	
	/**
	 * 商品订单页入口
	 * @param goodsId
	 * @param map
	 * @return
	 */
	@RequestMapping("/goodsOrder/{goodsId}.htm")
	public String goodsOrder(@PathVariable Long goodsId, ModelMap map){
		
		if(null ==goodsId || "".equals(goodsId)){
			return "goods/goodsOrder.htm";
		}
		loger.info("商品订单:"+goodsId);
		
		/**判断该会员是否登录**/
		if(this.authService.getAuth().getId()==null || "".equals(this.authService.getAuth().getId())){
			loger.info("用户未注册");
			return "login";
		}
		
		Long storeId=1L;//默认取1
		Long isBook=1L;//默认取1 订单类型
		String isPtRed="Y";
		Long memberId=this.authService.getAuth().getId();
		
		HashMap<String , Object> hashMap = new HashMap<String, Object>();
		MapVo mapVo = new MapVo();
		
		/****商品详情**/
		String urlPath = "/goods/detail.htm";//商品详情数据
		hashMap.put("goodsId", goodsId);
		
		mapVo.setMap(hashMap);
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
	
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			
			if(null != responseContent.getBody()){
				JSONObject result = (JSONObject) responseContent.getBody();
				//获取产品值
				JSONObject goods = (JSONObject) result.get("dgoods");
				
				if(StringUtils.isNotBlank(goods.get("storeId")+"") && !"".equals(goods.get("storeId")+"")
						&& !"null".equals(goods.get("storeId")+"") && !"NULL".equals(goods.get("storeId")+"")){
					storeId= Long.parseLong(goods.get("storeId").toString());//店铺id
				}
				if(StringUtils.isNotBlank(goods.get("isBook")+"") && !"".equals(goods.get("isBook")+"")
						&& !"null".equals(goods.get("isBook")+"") && !"NULL".equals(goods.get("isBook")+"")){
					
					isBook= Long.parseLong(goods.get("isBook").toString());//订单类型id
				}
				if(StringUtils.isNotBlank(goods.get("isPtRed")+"") && !"".equals(goods.get("isPtRed")+"")
						&& !"null".equals(goods.get("isPtRed")+"") && !"NULL".equals(goods.get("isPtRed")+"")){
					
					isPtRed=goods.get("isPtRed").toString();//订单类型id
				}
				
				map.put("goods", goods);
			}else{
				map.put("goods", null);
			}
		} catch (Exception e) {
			loger.error("异常："+e.getMessage());
		}
		
		/****商品详情end**/
		
		/**商户可使用红包数**/
		loger.info("storeId:"+storeId);
		
		urlPath = "/distribute/queryBymemberIdAndKy.htm";//商品详情数据
		hashMap.put("memberId",memberId);
		hashMap.put("storeId", storeId);
		hashMap.put("isPtRed", isPtRed);
		
		
		mapVo.setMap(hashMap);
		//条件参数
		content = new RequestContent();
		content.setBody(mapVo.getMap());
	
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			
			if(null != responseContent.getBody()){
				loger.info("普通红包");
				List<JSONObject> red = (List<JSONObject>) responseContent.getBody();
				map.put("ptRed", red);//普通红包
			}else{
				map.put("ptRed", null);//普通红包
			}
		} catch (Exception e) {
			e.printStackTrace();
			loger.error("异常："+e.getMessage());
		}
		
		/**商户可使用红包数end**/
		
		if(isBook!=null && isBook==2 ){
			
			loger.info("预定订单"+isBook);
			/**预定订单**/
			return "goods/ydGoodsOrder";
		}
		
		loger.info("普通订单"+isBook);
		return "goods/ptGoodsOrder";
	}
	
	@RequestMapping("/goodsIndex.htm")
	public String goodsIndex(ModelMap map) {
		
		loger.info("商品主页面");
		String urlPath = "/index/test.htm";
		
		RequestContent content = new RequestContent();
		
		HashMap<String,Object> hashMap  = new HashMap<String, Object>();
		MapVo mapVo = new MapVo();
		
		/**查询商品列表 **/
		//参数
		hashMap.put("areaId",1);
		hashMap.put("areaName","sss");
		
		content.setHead(null);
		content.setBody(hashMap);
	
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");

			if(null != responseContent.getBody()){
				JSONObject darea =  (JSONObject) responseContent.getBody();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/**查询商品列表 end **/
		
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
			/*大map*/
			map.put("gclist", list);
			
		}catch(Exception e){
			loger.info("商家列表获取失败！",e);
		}
		/****分类end**/
		
		return "index";
	}
	
	/**
	 * 商品列表页
	 * @param map
	 * @param mapVo
	 * @return
	 */
	@RequestMapping("/list.htm")
	public String index(ModelMap map,MapVo mapVo,Long classIds){
		
		
		String urlPath = "";//分类列表

		RequestContent content = new RequestContent();

		HashMap<String,Object> hashMap  = new HashMap<String, Object>();

		if(classIds!=null){
			hashMap.put("classId", classIds);
			
			urlPath="/groupbuy/queryById.htm";
			
			content = new RequestContent();
			content.setBody(hashMap);
			try{
				ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
				
				if(null != responseContent.getBody()){
					JSONObject groupbuy = (JSONObject) responseContent.getBody();
					
					map.put("groupbuy", groupbuy);
				}
				
			}catch(Exception e){
				loger.info("商家列表获取失败！",e);
			}
		}
		
		/****分类**/
		/**
		urlPath = "/groupbuy/list.htm";//分类列表
		hashMap = new HashMap<String, Object>();
		hashMap.put("parentId", 1);//1-产品分类

		
		content = new RequestContent();
		content.setBody(hashMap);
		try{
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			List<JSONObject> list=null;
			if(null != responseContent.getBody()){
				list = (List<JSONObject>) responseContent.getBody();
			}
			map.put("gclist", list);
			
		}catch(Exception e){
			loger.info("商家列表获取失败！",e);
		}
		**/
		/****分类end**/
		
		return "goods/list";
	}

	@ResponseBody
	@RequestMapping("/list.ajax")
	public Map indexAjax( MapVo mapVo ){

		Map result = new HashMap();
		String  pageNo = (String) mapVo.getMap().get("pageNo");
		loger.info("商品列表页面");
		int noPage=Integer.parseInt(cc.royao.commons.utils.StringUtils.replaceNull(pageNo+"","1"));
		loger.info("商品列表页面  noPage："+noPage);
		String urlPath = "/goods/list.htm";
		RequestContent content = new RequestContent();
		//set 参数到body
		content.setBody(mapVo.getMap());
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");

			if(null != responseContent.getBody()){
				List<JSONObject> json = (List<JSONObject>) responseContent.getBody();

				result.put("list", json);

			}else{
				result.put("list", null);
			}
		} catch (Exception e) {
			loger.error("异常信息：" + e.getMessage());
		}





		return result;
	}
	
	/**
	 * 商品详情页入口
	 * @param goodsId
	 * @param map
	 * @return
	 */
	@RequestMapping("/detail.htm")
	public String detail(Long goodsId  , ModelMap map, String distance, HttpServletRequest request){
		/**判断该会员是否登录**/
		/**
		if(this.authService.getAuth().getId()==null || "".equals(this.authService.getAuth().getId())){
			loger.info("用户未注册");
			return "redirect:/userCenter.htm";
		}
		**/
		Long memberId = this.authService.getAuth().getId();//获取当前登录的用户ID
		
		String urlPath = "/goods/detail.htm";
		
		HashMap<String , Object> hashMap = new HashMap<String, Object>();
		hashMap.put("goodsId", goodsId);
		MapVo mapVo = new MapVo();
		mapVo.setMap(hashMap);
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
	
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			
			if(null != responseContent.getBody()){
				JSONObject result = (JSONObject) responseContent.getBody();


				JSONObject goods = (JSONObject) result.get("dgoods");
				map.put("goods", goods);
				map.put("imgList", result.get("imgList"));
				map.put("evalList", result.get("evalList"));
				map.put("goodsSet", result.get("goodsSet"));
				
				/*****判断是否已经收藏******/
				if(null != memberId){
					hashMap.put("memberId", memberId);
					hashMap.put("type", "goods");
					hashMap.put("commonId", goods.get("goodsId"));
					mapVo.setMap(hashMap);
					content.setBody(mapVo.getMap());
					responseContent = HttpUtil.getInputStreamByPost("/collect/isCollect.htm",content, "utf-8");
					if(null != responseContent.getBody()){
						List<JSONObject> jsonList = (List<JSONObject>)responseContent.getBody();
						//说明已经收藏了
						if(jsonList != null && jsonList.size() > 0 && StringUtils.isNotBlank(jsonList.get(0).get("id")+"")){
							map.put("isCollect", true);
							map.put("collectId", jsonList.get(0).get("id"));
						}else{
							map.put("isCollect", false);
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
			}else{
				map.put("goods", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//距离，用于在地图中显示
		map.put("distance", distance);
		
		//分享链接
		map.put("shareurl", request.getRequestURL().toString() + "?goodsId=" + goodsId);
		
		return "goods/detail";
	}

	/**
	 * 商品分类
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping( value = "/class.htm")
	public String goodClass( HttpServletRequest request ,ModelMap modelMap)
	{
		 String urlPaht = "/groupbuy/list.htm";
		 HashMap<String , Object> hashMap = new HashMap<String, Object>();
		 MapVo mapVo = new MapVo();
		RequestContent content = new RequestContent();
		hashMap.put("parentId", 1);
		mapVo.setMap(hashMap);
		content.setBody(mapVo.getMap());
        try {
            ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPaht, content, "utf-8");
            if(null != responseContent.getBody()){
                JSONArray result = (JSONArray) responseContent.getBody();
                modelMap.put("result",result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
			
			return "goods/goodclass";
	}
	/**
	 * 查二级产品分类
	 * @param mapVo
	 * @param classId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/class.ajax")
	public Map goodClassAjax(MapVo mapVo,Long classId ){

		Map result = new HashMap();
		String  parent = (String) mapVo.getMap().get("parentId");
		String  pageNo = (String) mapVo.getMap().get("pageNo");
		int noPage=Integer.parseInt(cc.royao.commons.utils.StringUtils.replaceNull(pageNo+"","1"));
		String urlPath = "/groupbuy/list.htm";
		HashMap<String , Object> hashMap = new HashMap<String, Object>();
		hashMap.put("parentId", parent);
		//set参数值
		hashMap.put("pageNo", noPage);//当前页
		mapVo.setMap(hashMap);
		RequestContent content = new RequestContent();
		//set 参数到body
		content.setBody(mapVo.getMap());
	
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");

			if(null != responseContent.getBody()){
				JSONArray json = (JSONArray) responseContent.getBody();

				result.put("list", json);

			}else{
				result.put("list", null);
			}
		} catch (Exception e) {
			loger.error("异常信息：" + e.getMessage());
		}
		return result;
	}


	/**
	 * 查询用户常用的产品分类
	 * @param mapVo
	 * @param classId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryMostClass.ajax")
	public Map queryMostClass(MapVo mapVo){

		Map result = new HashMap();
   	    Long memberId = null;//获取当前登录的用户ID
 	    memberId = this.authService.getAuth().getId();
		String urlPath = "/groupbuy/queryMostClass.htm";
		HashMap<String , Object> hashMap = new HashMap<String, Object>();
//		hashMap.put("memberId", 1);	
		hashMap.put("memberId", memberId);	
		mapVo.setMap(hashMap);
		RequestContent content = new RequestContent();
		//set 参数到body
		content.setBody(mapVo.getMap());
	
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");

			if(null != responseContent.getBody()){
				JSONArray json = (JSONArray) responseContent.getBody();

				result.put("list", json);

			}else{
				result.put("list", null);
			}
		} catch (Exception e) {
			loger.error("异常信息："+e.getMessage());
		}
		return result;
	}
	/**
	 * 更多商品详情页入口
	 * @param goodsId
	 * @param map
	 * @return
	 */
	@RequestMapping("/gdDetail.htm")
	public String gdDetail(Long goodsId  , ModelMap map){
		/**判断该会员是否登录**/
//		if(this.authService.getAuth().getId()==null || "".equals(this.authService.getAuth().getId())){
//			loger.info("用户未注册");
//			return "login";
//		}
//		Long memberId = this.authService.getAuth().getId();//获取当前登录的用户ID
		
		
		String urlPath = "/goods/gdDetail.htm";
		
		HashMap<String , Object> hashMap = new HashMap<String, Object>();
		
		/**商品 start**/
		hashMap.put("goodsId", goodsId);
		
		MapVo mapVo = new MapVo();
		mapVo.setMap(hashMap);
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
	
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			
			if(null != responseContent.getBody()){
				JSONObject json = (JSONObject) responseContent.getBody();
				map.put("goods", json.get("goods"));
				map.put("imgList", json.get("imgList"));
			}else{
				map.put("goods", null);
				map.put("imgList", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/**查询商品列表 end **/

		return "goods/gdDetail";
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
			ResponseContent responseContent = HttpUtil.getInputStreamByPost("/goods/share.htm",content, "utf-8");
			if(null != responseContent.getBody()){//说明得到一个分享红包
				return ResponseJson.body(true, "恭喜您获得一个分享红包，去我的红包即可查看");
			}else{
				return ResponseJson.body(false, "分享成功");
			}
		}catch(Exception e){
			loger.info("分享商品获取红包时出错", e);
		}
		
		return ResponseJson.body(false, "分享成功");
	}



	@ResponseBody
	@RequestMapping ("/goodGroupBuy.ajax")
	public List<JSONObject> goodGroupBuyAjax ()
	{
		String urlPaht = "/groupbuy/list.htm";//分类列表
		Map params = new HashMap<String, Object>();
		params.put("parentId", 1);//1-产品分类
		RequestContent content = new RequestContent();
		content = new RequestContent();
		content.setBody(params);
		List<JSONObject> list=null;
		try{
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPaht,content, "utf-8");
			if(null != responseContent.getBody()){
				list = (List<JSONObject>) responseContent.getBody();
			}
		}catch(Exception e){
			loger.info("获取产品类型失败！", e);
		}
		return list;
	}
}
