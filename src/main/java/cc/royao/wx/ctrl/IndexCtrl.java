package cc.royao.wx.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.royao.commons.CConfig;
import cc.royao.commons.formbean.MapVo;

import com.alibaba.fastjson.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

import cc.royao.commons.httpClient.HttpUtil;
import cc.royao.commons.httpClient.RequestContent;
import cc.royao.commons.httpClient.ResponseContent;

import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


/**
 * 启动项公共方法
 * @author yangx
 * @className 
 * @date 2016年3月1日上午11:31:04
 */
@Controller
public class IndexCtrl {




	private Logger loger  = Logger.getLogger(this.getClass());



	@RequestMapping("/index.htm")
	public String wap( ModelMap modelMap ,HttpServletRequest request) {

		String urlPaht = "/index/index.htm";
		RequestContent content = new RequestContent();
		Map params = new HashMap();

//		Map snsUserInfo  = (Map) request.getSession().getAttribute("snsUserInfo");
//		String wxCode = (String) snsUserInfo.get("openId");


		ServletContext  servletContext  =  request.getSession().getServletContext();

		/*基础图片公共地址*/
		if( null == servletContext.getAttribute("http_img") || "".equals(servletContext.getAttribute("http_img")) )
		{
			servletContext.setAttribute("http_img", CConfig.pImgUrl);
			
		}
		/*店铺图片公共地址*/
		if( null == servletContext.getAttribute("STORE_IMG_URL") || "".equals(servletContext.getAttribute("STORE_IMG_URL")) )
		{
			servletContext.setAttribute("STORE_IMG_URL", CConfig.storeImgUrl);
			
		}
		/*健康师图片公共地址*/
		if( null == servletContext.getAttribute("TECHIE_IMG_URL") || "".equals(servletContext.getAttribute("TECHIE_IMG_URL")) )
		{
			servletContext.setAttribute("TECHIE_IMG_URL", CConfig.techieImgUrl);
			
		}
		/*公共图片公共地址*/
		if( null == servletContext.getAttribute("PUBLIC_IMG_URL") || "".equals(servletContext.getAttribute("PUBLIC_IMG_URL")) )
		{
			servletContext.setAttribute("PUBLIC_IMG_URL", CConfig.publicImgUrl);
			
		}
		/*商品图片公共地址*/
		if( null == servletContext.getAttribute("GOODS_IMG_URL") || "".equals(servletContext.getAttribute("GOODS_IMG_URL")) )
		{
			servletContext.setAttribute("GOODS_IMG_URL", CConfig.goodsImgUrl);
			
		}
		
//		content.setBody(params);
//		try {
//			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPaht,content, "utf-8");
//
//			if(null != responseContent.getBody()){
//				JSONObject result = (JSONObject) responseContent.getBody();
//				modelMap.put("result",result);
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return "index";
	}


	@RequestMapping( value = "/indexSearch.htm")
	public String indexSearch( ModelMap modelMap, MapVo mapVo )
	{

//		String urlPath = "/goods/list.htm";
//
//		RequestContent content = new RequestContent();
//
//		HashMap<String,Object> hashMap  = new HashMap<String, Object>();
//
//		/****鍒嗙被**/
//		urlPath = "/groupbuy/list.htm";//鍒嗙被鍒楄〃
//
//		hashMap = new HashMap<String, Object>();
//		hashMap.put("parentId", 1);//1-浜у搧鍒嗙被
//
//
//		content = new RequestContent();
//		content.setBody(hashMap);
//		try{
//			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
//			List<JSONObject> list=null;
//			if(null != responseContent.getBody()){
//				list = (List<JSONObject>) responseContent.getBody();
//			}
//			modelMap.put("search",mapVo.getMap().get("goodsName"));
//			/*澶ap*/
//			modelMap.put("gclist", list);
//
//		}catch(Exception e){
//			loger.info("鍟嗗鍒楄〃鑾峰彇澶辫触锛�",e);
//		}
//
//		/****鍒嗙被end**/
//
//
//
//
//		return "goods/list";


		modelMap.put("search" , mapVo.getMap().get("goodsName"));
		return "goods/list";
	}

	@ResponseBody
	@RequestMapping( value = "/guessYouLove.ajax")
	public JSONObject guessYouLove(HttpServletRequest request , MapVo mapVo){

		String urlPaht = "/index/guessYouLove.htm";
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
		JSONObject  jsonObject = new JSONObject();
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPaht,content, "utf-8");
			if(null != responseContent.getBody()){
				JSONArray result = (JSONArray) responseContent.getBody();
				jsonObject.put("result",result);
//				modelMap.put("list", result);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;

	}


	@RequestMapping (value = "/queryGoodByClass/{classId}.htm")
	public String goodsListByType(@PathVariable String classId , ModelMap modelMap)
	{
		String urlPaht = "/goods/queryByClass.htm";

		RequestContent content = new RequestContent();
		
		HashMap<String,Object> hashMap  = new HashMap<String, Object>();
		if(classId!=null){
			hashMap.put("classId", classId);
			
			urlPaht="/groupbuy/queryById.htm";
			
			content = new RequestContent();
			content.setBody(hashMap);
			try{
				ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPaht,content, "utf-8");
				
				if(null != responseContent.getBody()){
					JSONObject groupbuy = (JSONObject) responseContent.getBody();
					
					modelMap.put("groupbuy", groupbuy);
				}
				
			}catch(Exception e){
				loger.info("商家列表获取失败！",e);
			}
		}

		/****鍒嗙被**/
		urlPaht = "/groupbuy/list.htm";//鍒嗙被鍒楄〃

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", 1);//1-浜у搧鍒嗙被


		content = new RequestContent();
		content.setBody(params);
		try{
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPaht,content, "utf-8");
			List<JSONObject> list=null;
			if(null != responseContent.getBody()){
				list = (List<JSONObject>) responseContent.getBody();
			}

			/*澶ap*/
			modelMap.put("gclist", list);

			modelMap.put("class",classId);


		}catch(Exception e){
			loger.info("鍟嗗鍒楄〃鑾峰彇澶辫触锛�", e);
		}
		return "goods/list";
	}





}
