package cc.royao.wx.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.royao.commons.ResponseJson;
import cc.royao.commons.formbean.MapVo;
import cc.royao.commons.httpClient.HttpUtil;
import cc.royao.commons.httpClient.RequestContent;
import cc.royao.commons.httpClient.ResponseContent;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * ClassName: TechieCtrl 
 * @Description: 私人健康师
 * @author Liu Pinghui
 * @date 2016年1月8日
 */
@Controller
@RequestMapping("/techie")
public class TechieCtrl {

	Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 
	 * @Description: 请求私人健康师列表
	 * @param @param map
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月8日
	 */
	@RequestMapping("/list.htm")
	public String list(ModelMap map){
		
		return "techie/list";
	}
	
	@ResponseBody
	@RequestMapping ("/techieGroupBuy.ajax")
	public List<JSONObject> techieGroupBuy ()
	{
		String urlPaht = "/groupbuy/list.htm";//分类列表
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentId", 2);//健康师类别
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
			logger.info("获取产品类型失败！", e);
		}
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="/list.ajax",method=RequestMethod.POST)
	public Map listajax(MapVo mapVo){
		
		Map<String,Object> result = new HashMap<String,Object>();
		String  pageNo = (String) mapVo.getMap().get("pageNo");
		
		String urlPath = "/techie/list.htm";
		
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
			logger.error("异常信息：" + e);
		}

		return result;
		
		/*****私人健康师列表******		
		String urlPath = "/techie/list.htm";
		
		HashMap<String , Object> hashMap = new HashMap<String, Object>();
		hashMap.put("iisjob", "on");
		hashMap.put("techieClassId", fwId);
		hashMap.put("titleScId", zcId);
		hashMap.put("pageNo", pageNo);
		MapVo mapVo = new MapVo();
		mapVo.setMap(hashMap);
		
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
	
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			List<JSONObject> json = null;
			if(null != responseContent.getBody()){
				json = (List<JSONObject>) responseContent.getBody();
			}
			return ResponseJson.body(true,"success",json);
		} catch (Exception e) {
			logger.info("请求私人健康师出错", e);
			ResponseJson.body(false,"网络忙，请稍后重试！");
		}
		
		*****私人健康师列表end******/
	}
	
	/**
	 * 
	 * @Description: 健康师详情页
	 * @param @param map
	 * @param @param id
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月8日
	 */
	@RequestMapping("/detail/{id}.htm")
	public String detail(ModelMap map,@PathVariable Long id){
		
		String urlPath = "/techie/detail.htm";//获取健康师信息,包括该健康师所关联的产品
		
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
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
			map.put("techie", json);
		}catch(Exception e){
			logger.info("健康师主页获取失败！",e);
		}
		
		return "techie/detail";
	}
	
	/**
	 * 
	 * @Description: 私人健康师评价列表
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月11日
	 */
	@RequestMapping("/evaluatelist.htm")
	public String evaluatelist(ModelMap map,Long id){
		
		String urlPath = "/techieEvaluate/list.htm";//获取健康师评论列表
		
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("techieId", id);
		hashMap.put("isShow", "Y");
		MapVo mapVo = new MapVo();
		mapVo.setMap(hashMap);
		
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());
		try{
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			List<JSONObject> json=null;
			if(null != responseContent.getBody()){
				json = (List<JSONObject>) responseContent.getBody();
			}
			map.put("list", json);
		}catch(Exception e){
			logger.info("健康师评价列表获取失败！",e);
		}
		
		return "techie/evaluatelist";
	}
}
