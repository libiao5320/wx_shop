package cc.royao.wx.ctrl;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.royao.commons.CConfig;
import cc.royao.commons.encry.EncrySHA1;

/**
 * 
 * @author yangx
 * @className  微信授权首页
 * @date 2016年3月9日上午8:58:33
 */
@Controller
@RequestMapping("/weixin")
public class WeixinIndexCtr {

	private Logger loger  = Logger.getLogger(this.getClass());
	  private static final String __TOKEN__ = CConfig.TOKEN;
	
	/**
	 * token 验证
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/index.htm")
	public String wap( ModelMap modelMap ,HttpServletRequest request,HttpServletResponse response) {
		System.out.println("微信 检查");	
		
		String echostr1="";
		try {
				loger.info("微信 检查");
				
				loger.info(request.getParameter("signature"));
		        // 微信加密签名
		        String signature = request.getParameter("signature");
		        // 随机字符串
		        String echostr = request.getParameter("echostr");
		        // 时间戳
		        String timestamp = request.getParameter("timestamp");
		        // 随机数
		        String nonce = request.getParameter("nonce");
		
		        String[] str = {__TOKEN__, timestamp, nonce };
		        Arrays.sort(str); // 字典序排序
		        String bigStr = str[0] + str[1] + str[2];
		        // SHA1加密
		        String digest = new EncrySHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
		
		        // 确认请求来至微信
		        if (digest.equals(signature)) {
		        	echostr1=echostr;
		        	System.out.println("echostr:"+echostr);
//						response.getWriter().print(echostr);
		
		        }
		} catch (Exception e) {
			System.out.println("err ");
			e.printStackTrace();
		}
     	System.out.println("echostr1:"+echostr1);
		return echostr1;
	}

	

}
