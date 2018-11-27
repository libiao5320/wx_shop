package cc.royao.wx.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cc.royao.commons.ResponseJson;
import cc.royao.commons.auth.AuthService;
import cc.royao.commons.httpClient.HttpUtil;
import cc.royao.commons.httpClient.RequestContent;
import cc.royao.commons.httpClient.ResponseContent;
import cc.royao.commons.logger.SysLogger;
import cc.royao.commons.utils.Validate;


@Controller
@RequestMapping("/ption")
public class ConsumptionCtrl {

			@SysLogger
			private Logger logger;
		
			@Autowired
			private AuthService authService;
			
			
			@SuppressWarnings("unchecked")
			@RequestMapping("/index.htm")
			public String index(ModelMap model){
				RequestContent  content = new RequestContent();
				Map<String,Object> map = new HashMap<String, Object>();
				
				map.put("page",1);
				map.put("wxCode", this.authService.getAuth().getWxCode());
				System.out.println("1111111111111111111=-"+this.authService.getAuth().getWxCode());
				content.setBody(map);
				
				try {
					ResponseContent res = HttpUtil.getInputStreamByPost("/consumption/list.htm",content, "utf-8");
					if(null != res.getBody()){
						logger.info("Center数据中心返回的消费券数据："+res.getBody());
						List<JSONArray> list = (List<JSONArray>) res.getBody();
						logger.info("list数据长度："+list.size());
						model.addAttribute("result", list);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return "user/consumption/index";
			}
			
			
			
			/**
			 * 加载消费券信息
			 * @param page
			 * @return
			 */
			@SuppressWarnings("unchecked")
			@ResponseBody
			@RequestMapping("/onload.ajax")
			public Object onload(Integer page){
				
				if(Validate.isEmpty(this.authService.getAuth().getWxCode())){
					return ResponseJson.body(false);
				}
				
				RequestContent  content = new RequestContent();
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("page",page);
				map.put("wxCode", this.authService.getAuth().getWxCode());
				content.setBody(map);
				
				try {
					ResponseContent res = HttpUtil.getInputStreamByPost("/consumption/list.htm",content, "utf-8");
					if(null != res.getBody()){
						logger.info("Center数据中心返回的消费券数据："+res.getBody());
						List<JSONArray> list = (List<JSONArray>) res.getBody();
						logger.info("list数据长度："+list.size());
						return ResponseJson.body(true, "", list);
					}
					return ResponseJson.body(false);
				} catch (Exception e) {
					e.printStackTrace();
					return ResponseJson.body(false);
				}
			}
			
			
			@RequestMapping("/{goodsId}.htm")
			public String queryGoods(String time,@PathVariable String goodsId,String orderType,ModelMap model){
				RequestContent  content = new RequestContent();
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("page",1);
				if((null != time)&&(!"".equals(time))){
					map.put("paySn", time);
				}else{
					map.put("goodsId", goodsId);
					map.put("orderType", orderType);
				}
				map.put("wxCode", this.authService.getAuth().getWxCode());
				content.setBody(map);
				
				try {
					ResponseContent res = HttpUtil.getInputStreamByPost("/consumption/detail.htm",content, "utf-8");
					if(null != res.getBody()){
						System.out.println("Center返回数据："+res.getBody());
						@SuppressWarnings("unchecked")
						List<JSONArray> list = (List<JSONArray>) res.getBody();
						model.addAttribute("result", list);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				model.addAttribute("time", time);
				model.addAttribute("goods_id", goodsId);
				model.addAttribute("order_type", orderType);
				return "user/consumption/detail_list";
			}
			
			
			
			@ResponseBody
			@RequestMapping("/{goodsId}.ajax")
			public Object queryGoodsajax(String time,@PathVariable String goodsId,String orderType,Integer page){
				RequestContent  content = new RequestContent();
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("page",page);
				if((null != time)&&(!"".equals(time))){
					map.put("paySn", time);
				}else{
					map.put("goodsId", goodsId);
					map.put("orderType", orderType);
				}
				map.put("wxCode", this.authService.getAuth().getWxCode());
				content.setBody(map);
				
				try {
					ResponseContent res = HttpUtil.getInputStreamByPost("/consumption/detail.htm",content, "utf-8");
					if(null != res.getBody()){
						System.out.println("ajaxCenter 返回的数据:"+res.getBody());
						@SuppressWarnings("unchecked")
						List<JSONArray> list = (List<JSONArray>) res.getBody();
						//model.addAttribute("result", list);
						return ResponseJson.body(true, "", list);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return ResponseJson.body(false,"");
			}
}
