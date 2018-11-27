/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2016
 */

package cc.royao.model;


import cc.royao.model.base.BaseEntity;


/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


public class Dmember extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Dmember";
	public static final String ALIAS_MEMBER_ID = "会员id";
	public static final String ALIAS_WX_TOKEN_ID = "微信tokenid";
	public static final String ALIAS_MEMBER_NAME = "会员名称";
	public static final String ALIAS_MEMBER_TRUENAME = "真实姓名";
	public static final String ALIAS_MEMBER_AVATAR = "会员头像";
	public static final String ALIAS_MEMBER_SEX = "会员性别 0男 1女";
	public static final String ALIAS_MEMBER_BIRTHDAY = "生日";
	public static final String ALIAS_MEMBER_PASSWD = "会员密码";
	public static final String ALIAS_MEMBER_PAYPWD = "支付密码";
	public static final String ALIAS_MEMBER_EMAIL = "会员邮箱";
	public static final String ALIAS_MEMBER_EMAIL_BIND = "0未绑定1已绑定";
	public static final String ALIAS_MEMBER_MOBILE = "手机号";
	public static final String ALIAS_MEMBER_MOBILE_BIND = "0未绑定1已绑定";
	public static final String ALIAS_MEMBER_QQ = "qq";
	public static final String ALIAS_MEMBER_LOGIN_NUM = "登录次数";
	public static final String ALIAS_MEMBER_TIME = "会员注册时间";
	public static final String ALIAS_MEMBER_LOGIN_TIME = "当前登录时间";
	public static final String ALIAS_MEMBER_LOGIN_IP = "当前登录ip";
	public static final String ALIAS_MEMBER_POINTS = "会员积分";
	public static final String ALIAS_AVAILABLE_PREDEPOSIT = "预存款可用金额";
	public static final String ALIAS_FREEZE_PREDEPOSIT = "预存款冻结金额";
	public static final String ALIAS_AVAILABLE_RC_BALANCE = "可用充值卡余额";
	public static final String ALIAS_FREEZE_RC_BALANCE = "冻结充值卡余额";
	public static final String ALIAS_MEMBER_STATE = "会员的开启状态 1为开启 0为关闭";
	public static final String ALIAS_MEMBER_AREAID = "地区ID";
	public static final String ALIAS_MEMBER_CITYID = "城市ID";
	public static final String ALIAS_MEMBER_PROVINCEID = "省份ID";
	public static final String ALIAS_MEMBER_AREAINFO = "地区内容";
	public static final String ALIAS_TOP_MAN_STATE = "1不是达人2荣耀达人";
	public static final String ALIAS_MEMBER_SECRET = "会员密钥";
	public static final String ALIAS_MEMBER_PAYSECRET = "支付密钥";
	
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	
	private java.lang.Integer memberId;
	private java.lang.String wxTokenId;
	private java.lang.String memberName;
	private java.lang.String memberTruename;
	private java.lang.String memberAvatar;
	
	private java.lang.Boolean memberSex;
	
	private java.util.Date memberBirthday;
	private java.lang.String memberPasswd;
	private java.lang.String memberPaypwd;
	private java.lang.String memberEmail;
	private Integer memberEmailBind;
	private java.lang.String memberMobile;
	private Integer memberMobileBind;
	private java.lang.String memberQq;
	
	private java.lang.Integer memberLoginNum;
	private java.lang.String memberTime;
	private java.lang.String memberLoginTime;
	private java.lang.String memberLoginIp;
	
	private java.lang.Integer memberPoints;
	
	private java.lang.Long availablePredeposit;
	
	private java.lang.Long freezePredeposit;
	
	private java.lang.Long availableRcBalance;
	
	private java.lang.Long freezeRcBalance;
	
	private java.lang.Boolean memberState;
	
	private java.lang.Integer memberAreaid;
	
	private java.lang.Integer memberCityid;
	
	private java.lang.Integer memberProvinceid;
	private java.lang.String memberAreainfo;
	
	private java.lang.Integer topManState;
	
	private java.lang.String secret;
	private java.lang.String memberPaysecret;
	
	public Dmember(){
	}

	public Dmember(
		java.lang.Integer memberId
	){
		this.memberId = memberId;
	}

	public void setMemberId(java.lang.Integer value) {
		this.memberId = value;
	}
	
	public java.lang.Integer getMemberId() {
		return this.memberId;
	}
	public void setWxTokenId(java.lang.String value) {
		this.wxTokenId = value;
	}
	
	public java.lang.String getWxTokenId() {
		return this.wxTokenId;
	}
	
	public java.lang.String getSecret() {
		return secret;
	}

	public void setSecret(java.lang.String secret) {
		this.secret = secret;
	}

	public java.lang.String getMemberPaysecret() {
		return memberPaysecret;
	}

	public void setMemberPaysecret(java.lang.String memberPaysecret) {
		this.memberPaysecret = memberPaysecret;
	}

	public void setMemberName(java.lang.String value) {
		this.memberName = value;
	}
	
	public java.lang.String getMemberName() {
		return this.memberName;
	}
	public void setMemberTruename(java.lang.String value) {
		this.memberTruename = value;
	}
	
	public java.lang.String getMemberTruename() {
		return this.memberTruename;
	}
	public void setMemberAvatar(java.lang.String value) {
		this.memberAvatar = value;
	}
	
	public java.lang.String getMemberAvatar() {
		return this.memberAvatar;
	}
	public void setMemberSex(java.lang.Boolean value) {
		this.memberSex = value;
	}
	
	public java.lang.Boolean getMemberSex() {
		return this.memberSex;
	}
	
	public void setMemberBirthday(java.util.Date value) {
		this.memberBirthday = value;
	}
	
	public java.util.Date getMemberBirthday() {
		return this.memberBirthday;
	}
	public void setMemberPasswd(java.lang.String value) {
		this.memberPasswd = value;
	}
	
	public java.lang.String getMemberPasswd() {
		return this.memberPasswd;
	}
	public void setMemberPaypwd(java.lang.String value) {
		this.memberPaypwd = value;
	}
	
	public java.lang.String getMemberPaypwd() {
		return this.memberPaypwd;
	}
	public void setMemberEmail(java.lang.String value) {
		this.memberEmail = value;
	}
	
	public java.lang.String getMemberEmail() {
		return this.memberEmail;
	}
	public void setMemberEmailBind(Integer value) {
		this.memberEmailBind = value;
	}
	
	public Integer getMemberEmailBind() {
		return this.memberEmailBind;
	}
	public void setMemberMobile(java.lang.String value) {
		this.memberMobile = value;
	}
	
	public java.lang.String getMemberMobile() {
		return this.memberMobile;
	}
	public void setMemberMobileBind(Integer value) {
		this.memberMobileBind = value;
	}
	
	public Integer getMemberMobileBind() {
		return this.memberMobileBind;
	}
	public void setMemberQq(java.lang.String value) {
		this.memberQq = value;
	}
	
	public java.lang.String getMemberQq() {
		return this.memberQq;
	}
	public void setMemberLoginNum(java.lang.Integer value) {
		this.memberLoginNum = value;
	}
	
	public java.lang.Integer getMemberLoginNum() {
		return this.memberLoginNum;
	}
	public void setMemberTime(java.lang.String value) {
		this.memberTime = value;
	}
	
	public java.lang.String getMemberTime() {
		return this.memberTime;
	}
	public void setMemberLoginTime(java.lang.String value) {
		this.memberLoginTime = value;
	}
	
	public java.lang.String getMemberLoginTime() {
		return this.memberLoginTime;
	}
	public void setMemberLoginIp(java.lang.String value) {
		this.memberLoginIp = value;
	}
	
	public java.lang.String getMemberLoginIp() {
		return this.memberLoginIp;
	}
	public void setMemberPoints(java.lang.Integer value) {
		this.memberPoints = value;
	}
	
	public java.lang.Integer getMemberPoints() {
		return this.memberPoints;
	}
	public void setAvailablePredeposit(java.lang.Long value) {
		this.availablePredeposit = value;
	}
	
	public java.lang.Long getAvailablePredeposit() {
		return this.availablePredeposit;
	}
	public void setFreezePredeposit(java.lang.Long value) {
		this.freezePredeposit = value;
	}
	
	public java.lang.Long getFreezePredeposit() {
		return this.freezePredeposit;
	}
	public void setAvailableRcBalance(java.lang.Long value) {
		this.availableRcBalance = value;
	}
	
	public java.lang.Long getAvailableRcBalance() {
		return this.availableRcBalance;
	}
	public void setFreezeRcBalance(java.lang.Long value) {
		this.freezeRcBalance = value;
	}
	
	public java.lang.Long getFreezeRcBalance() {
		return this.freezeRcBalance;
	}
	public void setMemberState(java.lang.Boolean value) {
		this.memberState = value;
	}
	
	public java.lang.Boolean getMemberState() {
		return this.memberState;
	}
	public void setMemberAreaid(java.lang.Integer value) {
		this.memberAreaid = value;
	}
	
	public java.lang.Integer getMemberAreaid() {
		return this.memberAreaid;
	}
	public void setMemberCityid(java.lang.Integer value) {
		this.memberCityid = value;
	}
	
	public java.lang.Integer getMemberCityid() {
		return this.memberCityid;
	}
	public void setMemberProvinceid(java.lang.Integer value) {
		this.memberProvinceid = value;
	}
	
	public java.lang.Integer getMemberProvinceid() {
		return this.memberProvinceid;
	}
	public void setMemberAreainfo(java.lang.String value) {
		this.memberAreainfo = value;
	}
	
	public java.lang.String getMemberAreainfo() {
		return this.memberAreainfo;
	}
	public void setTopManState(java.lang.Integer value) {
		this.topManState = value;
	}
	
	public java.lang.Integer getTopManState() {
		return this.topManState;
	}

}

