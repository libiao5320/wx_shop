package cc.royao.commons.ccp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cloopen.rest.sdk.CCPRestSDK;


public class CCPCoreHelper {
	private Logger loger = Logger.getLogger(this.getClass());
	private static CCPRestSDK restAPI;
	private static CCPCoreHelper singletonInstance = new CCPCoreHelper();
	
	private CCPCoreHelper(){
		restAPI = new CCPRestSDK();
		restAPI.init(CCPConfig.CPP_SERVER_IP, CCPConfig.CPP_SERVER_PORT);// 初始化服务器地址和端口
		restAPI.setAccount(CCPConfig.CPP_ACCOUNT_SID,CCPConfig.CPP_ACCOUNT_TOKEN);// 初始化主帐号和主帐号TOKEN
		restAPI.setAppId(CCPConfig.CCP_APPID);// 初始化应用ID
	}
	
	public static CCPCoreHelper getInstance(){
		if(singletonInstance == null){
			singletonInstance = new CCPCoreHelper();
		}
    	return singletonInstance;
    }
	
	/**
	 * 创建容联帐号
	 * @Description
	 * @author jk
	 * @param subName
	 * @return
	 * @return CCPAccountBean
	 * @date   2016年3月7日
	 */
	public CCPAccountBean CPPCreateSub(String subName){
		HashMap<String, Object> result = null;
		CCPAccountBean accBean = new CCPAccountBean();
		result = restAPI.createSubAccount(subName);
		if(result!=null){
			if(CCPStatus.CCP_OK.equals(result.get("statusCode"))){
				accBean.setStatusCode(result.get("statusCode").toString());
				HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
				List<Map<?, ?>> SubAccount = (List<Map<?, ?>>) data.get("SubAccount");
				if(SubAccount.size()> 0){
					accBean.setSubAccountSid(SubAccount.get(0).get("subAccountSid").toString());
					accBean.setSubToken(SubAccount.get(0).get("subToken").toString());
					accBean.setVoipAccount(SubAccount.get(0).get("voipAccount").toString());
					accBean.setVoipPwd(SubAccount.get(0).get("voipPwd").toString());
					
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                
						Date date;
						date = sdf.parse(SubAccount.get(0).get("dateCreated").toString());
						accBean.setDateCreated(date);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}       
					
				}
				return accBean;
			}else{
				if(result.get("statusCode")!=null)
					accBean.setStatusCode(result.get("statusCode").toString());
				//异常返回输出错误码和错误信息
				loger.info("SDKTestCreateSubAccount result=" + result);
				loger.info("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
				
				return accBean;
			}
		}
		return accBean;
		
	}
	
	/**
	 * 查询容联帐号
	 * @Description
	 * @author jk
	 * @param subName
	 * @return
	 * @return CCPAccountBean
	 * @date   2016年3月7日
	 */
	public CCPAccountBean CPPQuerySubAccount(String subName){
		HashMap<String, Object> result = null;
		CCPAccountBean accBean = new CCPAccountBean();
		result = restAPI.querySubAccount(subName);
		if(CCPStatus.CCP_OK.equals(result.get("statusCode"))){
			accBean.setStatusCode(result.get("statusCode").toString());
			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
			List<Map<?, ?>> SubAccount = (List<Map<?, ?>>) data.get("SubAccount");
			if(SubAccount.size()> 0){
				accBean.setSubAccountSid(SubAccount.get(0).get("subAccountSid").toString());
				accBean.setSubToken(SubAccount.get(0).get("subToken").toString());
				accBean.setVoipAccount(SubAccount.get(0).get("voipAccount").toString());
				accBean.setVoipPwd(SubAccount.get(0).get("voipPwd").toString());
				
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                
					Date date;
					date = sdf.parse(SubAccount.get(0).get("dateCreated").toString());
					accBean.setDateCreated(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}       
				
			}
			return accBean;
		}else{
			//异常返回输出错误码和错误信息
			if(result.get("statusCode")!=null)
				accBean.setStatusCode(result.get("statusCode").toString());
			loger.info("SDKTestCreateSubAccount result=" + result);
			loger.info("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
			return accBean;
		}
	}
	
	public static void main(String[] args) {
		//CCPAccountBean accBean = CCPCoreHelper.getInstance().CPPCreateSub(StringUtil.encrypeMD5("11273183783"));
		//System.out.println(accBean.toString());
		//CCPCoreHelper.getInstance().CPPQuerySubAccount(StringUtil.encrypeMD5("13873183787"));
		//System.out.println(StringUtil.encrypeMD5("13873183787"));
	}

}

