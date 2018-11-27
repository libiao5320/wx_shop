package cc.royao.wx.ctrl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
import cc.royao.commons.logger.SysLogger;

@Controller
@RequestMapping("/redpackets")
public class RedpacketsCtrl {

	@Autowired
	private AuthService authService;

	@SysLogger
	private Logger logger;
	
	/**
	 * 
	 * @Description: 检查红包是否可以正常领取。检测是否过期或是否已领完
	 * @param @return
	 * @return String
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月5日
	 */
	@ResponseBody
	@RequestMapping("/checkRed.ajax")
	public ResponseJson checkRed(String id) {
		
		String urlPath = "/redpackets/getById.htm";// 调取红包信息

		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("id", id);
		MapVo mapVo = new MapVo();
		mapVo.setMap(hashMap);
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());

		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(
					urlPath, content, "utf-8");
			if (null != responseContent.getBody()) {
				
				JSONObject json = (JSONObject) responseContent.getBody();
				
				if (!json.get("quantity").equals("-1")) {//红包是否为无限大红包
					if(json.get("quantity").equals(json.get("receive"))){
					// 说明红包已经领取完
					return ResponseJson.body(false, "红包已经被领取完！");
					}
				}
				
				java.util.Date nowdate=new java.util.Date(); 
				String myString = json.get("strValidityTime").toString();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
				Date d = sdf.parse(myString);

				boolean flag = d.before(nowdate);
				
				if (flag) {
					// 说明红包已过期
					return ResponseJson.body(false, "红包已过期！");
				}
				if (json.get("status").equals("off")) {
					// 说明红包已经下架
					return ResponseJson.body(false, "红包已过期！");
				}

				return ResponseJson.body(true, "success", json);
			} else {
				// 不存在该红包
				return ResponseJson.body(false, "红包已过期！");
			}
		} catch (Exception e) {
			System.out.println(e);
			logger.info("请求红包数据出错：cc.royao.wx.ctrl.RedpacketsCtrl.checkRed",e);
			return ResponseJson.body(false, "网络忙，请稍后重试！");
		}

	}

	@ResponseBody
	@RequestMapping(value = "/shareRedPack/{redpackId}.htm")
	public ResponseJson shareRedPack(@PathVariable String redpackId) {
		String urlPath = "/redpackets/shareRedPack.htm";// 调取红包信息
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		RequestContent content = new RequestContent();
		paramMap.put("packId", redpackId);
		content.setBody(paramMap);

		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(
					urlPath, content, "utf-8");

			if (null != responseContent.getBody()) {
				JSONObject json = (JSONObject) responseContent.getBody();
				if (json.get("quantity").equals(json.get("receive"))) {
					// 说明红包已经领取完
					return ResponseJson.body(false, "红包已经被领取完！");
				}
				
				java.util.Date nowdate=new java.util.Date(); 
				String myString = json.get("validityTime").toString();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
				Date d = sdf.parse(myString);

				boolean flag = d.before(nowdate);
				if (flag) {
					// 说明红包已过期
					return ResponseJson.body(false, "红包已过期！");
				}
				if (json.get("status").equals("off")) {
					// 说明红包已经下架
					return ResponseJson.body(false, "红包已过期！");
				}

				return ResponseJson.body(true, "success", json);
			} else {
				// 不存在该红包
				return ResponseJson.body(false, "红包已过期！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("cc.royao.wx.ctrl.RedpacketsCtrl.shareRedPack",e);
			return ResponseJson.body(false, "网络忙，请稍后重试！");
		}
	}

	/**
	 * 
	 * @Description: 检查红包是否可以正常领取
	 * @param @return
	 * @return String
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月5日
	 */
	@ResponseBody
	@RequestMapping(value = "/checkReceive.ajax", method = RequestMethod.POST)
	public ResponseJson checkReceive(Long id) {

		Long memberId = null;// 获取当前登录的用户ID
		logger.info("memberId:"+this.authService.getAuth().getId());
		if (null != this.authService.getAuth()) {
			memberId = this.authService.getAuth().getId();
		} else {
			return ResponseJson.body(false, "login");
		}
		
		if (null == memberId) {
			return ResponseJson.body(false, "login");
		}
		
		String urlPath = "/distribute/checkReceive.htm";// 检查是否已经领取

		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("id", id);//大红包ID
		hashMap.put("memberId", memberId);
		MapVo mapVo = new MapVo();
		mapVo.setMap(hashMap);
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());

		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(
					urlPath, content, "utf-8");
			JSONObject json = null;
			if (null == responseContent.getBody()) {// 没有领取红包
				urlPath = "/redpackets/getById.htm";// 调取红包信息
				responseContent = HttpUtil.getInputStreamByPost(urlPath,
						content, "utf-8");

				if (null != responseContent.getBody()) {
					json = (JSONObject) responseContent.getBody();
					return ResponseJson.body(true, "success", json);
				} else {
					return ResponseJson.body(false, "红包活动已过期！");// 红包不存在
				}
			} else {
				// 说明该用户已经领取了红包
				json = (JSONObject) responseContent.getBody();
				return ResponseJson.body(false, "您已经领过该红包了！", json);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseJson.body(false, "网络忙，请稍后重试！");
		}

	}

	/**
	 * 
	 * @Description: 领取红包
	 * @param @param id
	 * @param @return
	 * @return ResponseJson
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月7日
	 */
	@ResponseBody
	@RequestMapping(value = "/receive.ajax", method = RequestMethod.POST)
	public ResponseJson receive(Long id) {
		
		Long memberId = null;// 获取当前登录的用户ID
		
		if (null != this.authService.getAuth()) {
			memberId = this.authService.getAuth().getId();
		} else {
			return ResponseJson.body(false, "login");
		}
		
		if (null == memberId) {
			return ResponseJson.body(false, "login");
		}

		String urlPath = "/distribute/receive.htm";// 领取红包

		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("id", id);//大红包ID
		hashMap.put("memberId", memberId);
		MapVo mapVo = new MapVo();
		mapVo.setMap(hashMap);
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());

		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(
					urlPath, content, "utf-8");
			if (null != responseContent.getBody()) {
				JSONObject json = (JSONObject) responseContent.getBody();
				return ResponseJson.body(true, "success", json);
			} else {
				// 说明红包被领取完了
				return ResponseJson.body(false, "慢了一步，红包被领完了。");
			}
		} catch (Exception e) {
			System.out.println("红包领取失败，红包id=" + id);
			e.printStackTrace();
			return ResponseJson.body(false, "网络忙，请稍后重试！");
		}
	}

	/**
	 * 
	 * @Description: 我的红包
	 * @param @return
	 * @return String
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年1月5日
	 */
	@RequestMapping("/myredpackets.htm")
	public String myredpackets(ModelMap map) {

		String sharlink = CConfig.sharlink;
		
		Long memberId = null;// 获取当前登录的用户ID
		logger.info("memberId:"+this.authService.getAuth().getId());
		if (null != this.authService.getAuth()) {
			memberId = this.authService.getAuth().getId();
		} else {
			return "/userCenter.htm";// 重定向到用户中心
		}

		if (null == memberId) {
			return "/userCenter.htm";// 重定向到用户中心
		}
		String urlPath = "/distribute/list.htm";

		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("memberId", memberId);
		hashMap.put("status", "all");// 状态，all-查询所有，used-已使用的，expired-已过期的
		MapVo mapVo = new MapVo();
		mapVo.setMap(hashMap);
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());

		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(
					urlPath, content, "utf-8");

			if (null != responseContent.getBody()) {
				List<JSONObject> json = (List<JSONObject>) responseContent.getBody();
				map.put("list", json);
			} else {
				// 没有红包
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		map.put("sharlink", sharlink+"?recommendCode=G"+memberId);

		return "redpackets/myredpackets";
	}
	
	/**
	 * 
	 * @Description: 分享成功后领取红包
	 * @param @param map
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年2月26日
	 */
	@ResponseBody
	@RequestMapping("/shareSuccessRed.ajax")
	public ResponseJson shareSuccessRed(ModelMap map) {

		Long memberId = null;// 获取当前登录的用户ID
		logger.info("memberId:"+this.authService.getAuth().getId());
		if (null != this.authService.getAuth()) {
			memberId = this.authService.getAuth().getId();
		} else {
			return ResponseJson.body(false, "未登录");
		}

		if (null == memberId) {
			return ResponseJson.body(false, "未登录");
		}
		
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("memberId", memberId);
		hashMap.put("purpose", "fx");// 状态，all-查询所有，used-已使用的，expired-已过期的
		RequestContent content = new RequestContent();
		content.setBody(hashMap);

		try {
			//查询是否已经领取过分享红包
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(
					"/distribute/list.htm", content, "utf-8");

			if (null != responseContent.getBody()) {
				List<JSONObject> rlist = (List<JSONObject>) responseContent.getBody();
				
				if(rlist.size() <= 0){
					//还没领取,继续领取
					
					hashMap.put("type", "infinite");//无限红包
					hashMap.put("status", "on");//状态为上架
					//检查是否有无限大红包(分享红包)
					responseContent = HttpUtil.getInputStreamByPost("/redpackets/list.htm", content, "utf-8");
				
					if(null != responseContent.getBody()){
						List<JSONObject> list = (List<JSONObject>) responseContent.getBody();
						if(list != null && list.size() > 0){
							//有可以用的红包
							hashMap.put("id", list.get(0).get("id"));
							//领取红包
							responseContent = HttpUtil.getInputStreamByPost("/distribute/receive.htm", content, "utf-8");
							if(null != responseContent.getBody()){
								JSONObject json = (JSONObject) responseContent.getBody();
								if(json != null){
									return ResponseJson.body(true, "success", json);
								}
							}
						}
					}
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseJson.body(false, "没有可用红包");
	}
}
