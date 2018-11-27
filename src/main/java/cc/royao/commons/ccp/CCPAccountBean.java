package cc.royao.commons.ccp;

import java.util.Date;

public class CCPAccountBean {
	private String subAccountSid;
	private String voipAccount;
	private Date dateCreated;
	private String voipPwd;
	private String subToken;
	private String statusCode;
	private String data;
	public String getSubAccountSid() {
		return subAccountSid;
	}
	public void setSubAccountSid(String subAccountSid) {
		this.subAccountSid = subAccountSid;
	}
	public String getVoipAccount() {
		return voipAccount;
	}
	public void setVoipAccount(String voipAccount) {
		this.voipAccount = voipAccount;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getVoipPwd() {
		return voipPwd;
	}
	public void setVoipPwd(String voipPwd) {
		this.voipPwd = voipPwd;
	}
	public String getSubToken() {
		return subToken;
	}
	public void setSubToken(String subToken) {
		this.subToken = subToken;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "CCPAccountBean [subAccountSid=" + subAccountSid
				+ ", voipAccount=" + voipAccount + ", dateCreated="
				+ dateCreated + ", voipPwd=" + voipPwd + ", subToken="
				+ subToken + ", statusCode=" + statusCode + ", data=" + data
				+ "]";
	}
	
	

}
