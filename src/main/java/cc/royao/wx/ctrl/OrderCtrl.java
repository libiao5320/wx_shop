package cc.royao.wx.ctrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.royao.commons.auth.AuthService;
import cc.royao.commons.auth.AuthUser;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.royao.commons.CConfig;
import cc.royao.commons.ResponseJson;
import cc.royao.commons.formbean.MapVo;
import cc.royao.commons.httpClient.HttpUtil;
import cc.royao.commons.httpClient.RequestContent;
import cc.royao.commons.httpClient.ResponseContent;
import cc.royao.commons.logger.SysLogger;
import cc.royao.commons.utils.QRCodeUtil;
import cc.royao.commons.utils.Validate;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @ClassName: OrderCtrl
 * @Description: TODO(创建订单)
 * @author yangx
 * @date 2016年1月8日
 *
 */
@Controller
@RequestMapping("/order")
public class OrderCtrl {

	@SysLogger
	private Logger logger;
	@Autowired
	private AuthService authService;
	
	public static String check_consumption_url=CConfig.checkConsumptionUrl;//B端核销地址

	@ResponseBody
	@RequestMapping(value = "/createdOrder.ajax", method = RequestMethod.GET)
	public ResponseJson createdOrder(MapVo mapVo,HttpServletRequest request) {
		
		logger.info("orderType:"+mapVo.getMap().get("orderType"));

		logger.info("totalNumber:"+mapVo.getMap().get("totalNumber"));
		
		/**判断该会员是否登录**/
		if(this.authService.getAuth().getId()==null || "".equals(this.authService.getAuth().getId())){
			logger.info("用户未注册");
			return ResponseJson.body(false, "login");
		}
		//memberId set 值
		mapVo.getMap().put("memberId",this.authService.getAuth().getId());
		
		/**验证用户支付密码start**/
		
		RequestContent mContent = new RequestContent();
		
		HashMap<String,Object> mapp  = new HashMap<String, Object>();
		mapp.put("wxCode",this.authService.getAuth().getWxCode());
		logger.info(this.authService.getAuth().getWxCode());
		
		mContent.setHead(null);
		mContent.setBody(mapp);
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost("/vip/judgepaypwd.htm",mContent, "utf-8");
			if(null != responseContent.getBody()){
				Map<?,?> maps = (Map<?, ?>) responseContent.getBody();
				
				String secret ="";
				if(maps.get("memberPaysecret")!=null && !"".equals(maps.get("memberPaysecret")) && 
						!"null".equals(maps.get("memberPaysecret"))){
					secret = maps.get("memberPaysecret").toString();
				}else{
					logger.info("该用户没有设置支付密码");
					return ResponseJson.body(false,"noPayPwd");
				}
			}else{
				return ResponseJson.body(false,"用户信息为空！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统繁忙，请稍候再试。。");
			return ResponseJson.body(false,"系统繁忙，请稍候再试。。");
		}
		
		/**验证用户支付密码end**/
		
		//订单类型set 值
		int orderType=Integer.parseInt(mapVo.getMap().put("orderType",mapVo.getMap().get("orderType"))+"");
		logger.info("orderType:"+mapVo.getMap().get("orderType"));
		
		if(orderType==3){//活动订单
			logger.info("活动订单:"+mapVo.getMap().get("orderType"));
			String urlPath = "/order/createdEventOrder.htm";
			RequestContent content = new RequestContent();
			
			content.setBody(mapVo.getMap());
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath, content, "utf-8");
				if (null != responseContent.getBody()) {
					JSONObject event = (JSONObject) responseContent.getBody();
					String status = (String) event.get("status");
					String total_order_num = event.getString("total_order_num");
					String availablePredeposit = event.getString("availablePredeposit");
					String freezePredeposit = event.getString("freezePredeposit");
					if (status != null && "true".equals(status)) {
						
						logger.info("根据 total_order_num  查询订单数据");
						//根据 total_order_num  查询订单数据
						urlPath = "/order/queryOrderByPaySn.htm";
						content = new RequestContent();
						mapVo.getMap().put("orderState",1);//未支付状态
						mapVo.getMap().put("paySn",total_order_num);
						content.setBody(mapVo.getMap());
						responseContent = HttpUtil.getInputStreamByPost(urlPath, content, "utf-8");
						
						Map<String,Object> listmap=new HashMap<String, Object>();
					
						/*生成二维码  start*/
						if (null != responseContent.getBody()) {
							logger.info(" JSONArray");
							
							List<Map<String,Object>> orderList = (List<Map<String,Object>>) responseContent.getBody();
							for(int i=0;i<orderList.size();i++){
//								logger.info(orderList.get(i).get("qrCode"));
								logger.info(orderList.get(i).get("consumptionCode"));
//								String qrCode=""+orderList.get(i).get("qrCode");
								logger.info("项目的绝对路径为："+request.getSession().getServletContext().getRealPath("/"));
								String LocationUrl = request.getSession().getServletContext().getRealPath("/");
								String consumptionCode=""+orderList.get(i).get("consumptionCode");
								QRCodeUtil.encode(check_consumption_url+consumptionCode,LocationUrl+CConfig.imgTicketHost,consumptionCode,true);
								logger.info("二维码http地址："+check_consumption_url+consumptionCode);
								
							}
						
						}
						/*生成二维码 end*/
						
						map.put("total_order_num", total_order_num);
						map.put("availablePredeposit", availablePredeposit);//可用预存款
						map.put("freezePredeposit",freezePredeposit);//冻结预存款
						
						return ResponseJson.body(true, "", map);
					} else {
						this.logger.info("保存订单数据出错!");
						return ResponseJson.body(true, "系统繁忙,请稍后重试!");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseJson.body(false, "数据错误,请稍后重试!");
			}
			return null;
		}
		
		String urlPaht = "/order/createdOrder.htm";
		RequestContent content = new RequestContent();

		AuthUser authUser = this.authService.getAuth();
		mapVo.getMap().put("memberId", authUser.getId());
		
		content.setBody(mapVo.getMap());
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPaht, content, "utf-8");
			if (null != responseContent.getBody()) {
				JSONObject event = (JSONObject) responseContent.getBody();
				String status = (String) event.get("status");
				String total_order_num = event.getString("total_order_num");
				String availablePredeposit = event.getString("availablePredeposit");
				String freezePredeposit = event.getString("freezePredeposit");

				if (status != null && "true".equals(status)) {
					//根据 total_order_num  查询订单数据
				    urlPaht = "/order/queryOrderByPaySn.htm";
					content = new RequestContent();
					mapVo.getMap().put("orderState",1);//未支付状态
					mapVo.getMap().put("paySn",total_order_num);
					content.setBody(mapVo.getMap());
					responseContent = HttpUtil.getInputStreamByPost(urlPaht, content, "utf-8");
					
					Map<String,Object> listmap=new HashMap<String, Object>();
				
					/*生成二维码  start*/
					if (null != responseContent.getBody()) {
						logger.info(" JSONArray");
						
						List<Map<String,Object>> orderList = (List<Map<String,Object>>) responseContent.getBody();
						for(int i=0;i<orderList.size();i++){
//							logger.info(orderList.get(i).get("qrCode"));
							logger.info(orderList.get(i).get("consumptionCode"));
//							String qrCode=""+orderList.get(i).get("qrCode");
							logger.info("项目的绝对路径为："+request.getSession().getServletContext().getRealPath("/"));
							String LocationUrl = request.getSession().getServletContext().getRealPath("/");
							String consumptionCode=""+orderList.get(i).get("consumptionCode");
							QRCodeUtil.encode(check_consumption_url+consumptionCode,LocationUrl+CConfig.imgTicketHost,consumptionCode,true);
							logger.info("二维码http地址："+check_consumption_url+consumptionCode);
							
						}
					
					}
					/*生成二维码 end*/
					
					
					map.put("total_order_num", total_order_num);
					map.put("availablePredeposit", availablePredeposit);//可用预存款
					map.put("freezePredeposit",freezePredeposit);//冻结预存款
					return ResponseJson.body(true, "", map);
				} else if(status != null && "noLogin".equals(status)){
					logger.info("数据库无该用户数据");
					return ResponseJson.body(false, "login");
				}else{
					return ResponseJson.body(false, "系统繁忙,请稍后重试!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseJson.body(false, "数据错误,请稍后重试!");
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/pay.ajax")
	public ResponseJson pay(MapVo mapVo,ModelMap map) {
		logger.info("提交付款页面");
	
		/**判断该会员是否登录**/
		if(this.authService.getAuth().getId()==null || "".equals(this.authService.getAuth().getId())){
			logger.info("用户未注册");
			return ResponseJson.body(false, "login");
		}
		String password="";
		String orderId="";
		int orderState=1;//默认为普通订单 3为 已经消费未付余款
		try{
			password=mapVo.getMap().get("payPwd").toString();
			orderId=mapVo.getMap().get("orderId").toString();
			if( null!=orderId && !"".equals(orderId)){
				orderState=Integer.parseInt(mapVo.getMap().get("orderState").toString());
			}
					
			logger.info("orderId:"+orderId +" password:"+password);
		}catch(Exception e){
			logger.error("password异常");
			return ResponseJson.body(false, "支付密码不能为空！请重新输入。。");
		}
		if(Validate.isEmpty(password)){
			return ResponseJson.body(false, "支付密码不能为空！请重新输入。。");
		}
		
		RequestContent content = new RequestContent();
		
		HashMap<String,Object> mapp  = new HashMap<String, Object>();
		mapp.put("wxCode",this.authService.getAuth().getWxCode());
		logger.info(this.authService.getAuth().getWxCode());
		
		
		content.setHead(null);
		content.setBody(mapp);
		
		/**验证用户支付密码start**/
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost("/vip/judgepaypwd.htm",content, "utf-8");
			if(null != responseContent.getBody()){
				Map<?,?> maps = (Map<?, ?>) responseContent.getBody();
				
				String secret ="";
				if(maps.get("memberPaysecret")!=null && !"".equals(maps.get("memberPaysecret")) && 
						!"null".equals(maps.get("memberPaysecret"))){
					secret = maps.get("memberPaysecret").toString();
				}else{
					logger.info("该用户没有设置支付密码");
					return ResponseJson.body(false,"noPayPwd");
				}
				password = this.authService.genPwd(password, secret, true);
				
				String passwords = maps.get("memberPaypwd").toString();
				
				if(!password.equals(passwords)){
					logger.info("支付密码输入错误，请重新输入！");
					return ResponseJson.body(false,"支付密码输入错误，请重新输入！");
				}
			}else{
				return ResponseJson.body(false,"用户信息为空！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统繁忙，请稍候再试。。");
			return ResponseJson.body(false,"系统繁忙，请稍候再试。。");
		}
		
		/**验证用户支付密码end**/
		
		/** 完成扣款操作  start **/
		String urlPaht = "/order/pay.htm";
		if(orderState==1){
			logger.info("当前 订单状态为1");
		}else if(orderState==3){//已经消费未付余款
			urlPaht = "/order/payBookFinalPayment.htm";
			logger.info("当前 订单状态为3");
		}else{
			logger.error("订单状态有误。");
			return ResponseJson.body(false,"订单状态有误");
		}
	    content.setBody(mapVo.getMap());
		//memberId set 值
		mapVo.getMap().put("memberId", this.authService.getAuth().getId());
		
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPaht, content, "utf-8");
			if (null != responseContent.getBody()) {
				JSONObject param = (JSONObject) responseContent.getBody();
				String status = (String) param.get("status");
				String message = (String) param.get("message");
				
				if ("true".equals(status)) {
					return ResponseJson.body(true,"success",param);
				} else {
					logger.info("message:"+message);
					return ResponseJson.body(false,message);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/** 完成扣款操作  end **/
		logger.error("系统繁忙，请稍候再试。。");
		return ResponseJson.body(false,"系统繁忙，请稍候再试。。");
	}
	

	@RequestMapping("/success.htm")
	public String submitOrder(MapVo mapVo,ModelMap map) {
		
		logger.info(" 付款成功页面 ");
		
		/**判断该会员是否登录**/
		if(this.authService.getAuth().getId()==null || "".equals(this.authService.getAuth().getId())){
			logger.info("用户未注册");
			return"/Center.htm";
		}
		
		
		//memberId set 值
		mapVo.getMap().put("memberId", this.authService.getAuth().getId());
		mapVo.getMap().put("OrderState", 2);
		
		/** 完成查询订单  start **/
		String urlPath = "/order/queryOrderByPaySn.htm";
		RequestContent content = new RequestContent();
		
		Long pdAmount=0L;
		Long paySn=0L;
		
		if(mapVo.getMap().get("paySn")!=null && !"".equals(mapVo.getMap().get("paySn"))){
			paySn=Long.parseLong(mapVo.getMap().get("paySn")+"");
		}
		
		//mapVo.getMap().put("paySn",paySn);//支付单号 371452648702515
	
		content.setBody(mapVo.getMap());
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost(urlPath,content, "utf-8");
			
			if(null != responseContent.getBody()){
				List<JSONObject>  list = (List<JSONObject>) responseContent.getBody();
				logger.info("数据查询成功");
				logger.info("数据："+list.toString());
				map.put("orderList", list);
			}else{
				map.put("goods", null);
			}
			map.addAttribute("paySn", paySn);
		} catch (Exception e) {
			logger.error("异常：" + e.getMessage());
		}
		
		return "/order/success";
	}

	@RequestMapping (value  ="/toComment/{orderId}/{goodsName}.htm")
	public String toComment ( @PathVariable String orderId ,@PathVariable String goodsName  ,HttpServletRequest httpServletRequest ,HttpServletResponse httpServletResponse , ModelMap modelMap)
	{

		HashMap<String , Object> hashMap = new HashMap<String, Object>();
		MapVo mapVo = new MapVo();
		hashMap.put("id", orderId);//订单id

		mapVo.setMap(hashMap);
		RequestContent content = new RequestContent();
		content.setBody(mapVo.getMap());

		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost("/order/detail/detail.htm",content, "utf-8");

			if(null != responseContent.getBody()){
				JSONObject result = (JSONObject) responseContent.getBody();

				JSONObject order = (JSONObject) result.get("order");

				modelMap.put("order", order);

				if (Integer.parseInt(""+order.get("orderState")) == 5 )
					return "order/detail";
				else {
					return "order/comment";
				}

			}else{
				modelMap.put("order", null);
			}

		} catch (Exception e) {
			logger.error("获取订单详情异常："+e);
		}


		return "order/comment";

	}

	@RequestMapping (value  ="/toComplain/{orderId}/{consumptionId}/{goodsName}.htm")
	public String toComplain ( @PathVariable String orderId  ,@PathVariable String consumptionId ,@PathVariable String goodsName  ,HttpServletRequest httpServletRequest ,HttpServletResponse httpServletResponse , ModelMap modelMap)
	{




		modelMap.put("orderId",orderId);
		modelMap.put("goodsName",goodsName);
		modelMap.put("consumptionId",consumptionId);
		return "order/complain";
	}


	@RequestMapping (value  ="/complain.htm")
	public String complain( MapVo mapVo , HttpServletRequest httpServletRequest ,HttpServletResponse httpServletResponse){


		RequestContent mContent = new RequestContent();


		mContent.setHead(null);
		mContent.setBody(mapVo.getMap());

		String orderId=""+mapVo.getMap().get("orderId");
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost("/order/complain.htm",mContent, "utf-8");

			if( null != responseContent &&  null !=  responseContent.getBody() )
			{
				JSONObject jsonObject = (JSONObject) responseContent.getBody();
				if( null != jsonObject )
				{
					Boolean result = (Boolean) jsonObject.get("result");
					if ( result )
					logger.info("订单投诉成功 ");
					return "redirect:/order/detail/"+orderId+".htm";
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
			logger.info("订单投诉异常：[]"+e.getMessage());
		}
		
		return "redirect:/order/detail/"+orderId+".htm";
	}

	/**
	 * 
	 * @Description: 订单评价
	 * @param @param mapVo
	 * @param @param httpServletRequest
	 * @param @param httpServletResponse
	 * @param @param modelMap
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author Liu Pinghui
	 * @date 2016年3月7日
	 */
	@RequestMapping (value  ="/comment.htm")
	public String comment ( MapVo mapVo  , HttpServletRequest httpServletRequest ,HttpServletResponse httpServletResponse ,ModelMap modelMap)
	{
		RequestContent mContent = new RequestContent();

		String imgStr = (String) mapVo.getMap().get("img");
		mapVo.getMap().remove("img");
		String [] arr = imgStr.split("[\\[.*\\]]+");
		List imgList  =  new ArrayList();

		for( int i =  0  ;  i< arr.length ;i++ )
		{
			if(null != arr[i] && !"".equals(arr[i]))
				imgList.add(arr[i]);
		}
		Map<String,Object> params = new HashMap();
		params.put("commentInfo",mapVo.getMap());
		params.put("imgList",imgList);
		mContent.setHead(null);
		mContent.setBody(params);
		try {
			ResponseContent responseContent = HttpUtil.getInputStreamByPost("/order/comment.htm",mContent, "utf-8");

			if( null != responseContent &&  null !=  responseContent.getBody() )
			{
				JSONObject jsonObject = (JSONObject) responseContent.getBody();
				if( null != jsonObject )
				{
					Boolean result = (Boolean) jsonObject.get("result");
					if( result ) {
						modelMap.put("orderId",mapVo.getMap().get("orderId"));
						return "/order/commentsuc";
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "order/comment";
	}
	
}
