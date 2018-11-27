package cc.royao.commons;

public class CConfig {

	public static String imgTicketHost = Config.get("img.ticket.host");
	
	public static String imgUserQrade = Config.get("img.user.qrade");
	
	//center地址
	public static String httpUrl = Config.get("http_url");
	
	//p端图片地址
	public static String pImgUrl = Config.get("p_img_url");

	//请求商家图片地址
	public static String storeImgUrl = Config.get("STORE_IMG_URL");
	
	//请求商品图片地址
	public static String goodsImgUrl = Config.get("GOODS_IMG_URL");
	
	//请求健康师图片地址
	public static String techieImgUrl = Config.get("TECHIE_IMG_URL");
	
	//请求公共图片地址
	public static String publicImgUrl = Config.get("PUBLIC_IMG_URL");
	
	//核销示卡消费券 B
	public static String checkConsumptionUrl = Config.get("check_consumption_url");
	
	//微信红包落地链接-不要加任何后缀，不然会失败
	public static String sharlink = Config.get("sharlink");
	
	//weixin
	public static String TOKEN = WeixinConfig.get("TOKEN");
	
	//weixin
	public static String APPID = WeixinConfig.get("APPID");
	
	//weixin
	public static String SECRET = WeixinConfig.get("SECRET");
	
	//weixin
	public static String OAUTHURL = WeixinConfig.get("OAUTHURL");
	
	//微信端URL
	public static String wxUrl = WeixinConfig.get("wx_url");
	
	//
	public static String weixinJssdkAcceTokenUrl = WeixinConfig.get("weixin_jssdk_acceToken_url");
	
	//
	public static String weixinJssdkTicketUrl = WeixinConfig.get("weixin_jssdk_ticket_url");
	
	//商户平台 
	public static String MCHID = WeixinConfig.get("MCHID");
	
	//key值
	public static String KEY = WeixinConfig.get("KEY");
	
	//异步回调地址
	public static String NOTIFY_URL = WeixinConfig.get("NOTIFY_URL");
	
	
	
}
