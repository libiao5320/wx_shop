package cc.royao.commons.auth;

import java.util.Date;
import java.util.Map;

public class AuthUser implements java.io.Serializable {

    private static final long serialVersionUID = 7210548290087150327L;



    private Long id; // 唯一标识
    private String wxCode ;
    private String memberName;
    private String headImg;
    
    private Long vipRankId;//vip等级
    
    private String loginName; // 用户名

    private String realName;// 真实姓名

    private String sid; // 用户登录标识

    private Date loginTime; // 最后登录时间

    private String loginIP;

    private String authorities; // 用户所拥有的权限内容
    
    private String recommendCode;//邀请码

    private Map<String, Object> data;

    public AuthUser(){}

    public AuthUser(Long id, String loginName, String realName, Date loginTime, String loginIP, String authorities) {
        this.id = id;
        this.loginName = loginName;
        this.realName = realName;
        this.loginTime = loginTime;
        this.loginIP = loginIP;
        this.authorities = authorities;
    }

    public AuthUser(Long id, String loginName, String realName, Date loginTime, String loginIP, String authorities, Map<String, Object> data) {
        this.id = id;
        this.loginName = loginName;
        this.realName = realName;
        this.loginTime = loginTime;
        this.loginIP = loginIP;
        this.authorities = authorities;
        this.data = data;
    }
    
    public String getRecommendCode() {
		return recommendCode;
	}

	public void setRecommendCode(String recommendCode) {
		this.recommendCode = recommendCode;
	}

	public Long getVipRankId() {
		return vipRankId;
	}

	public void setVipRankId(Long vipRankId) {
		this.vipRankId = vipRankId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getWxCode() {
        return wxCode;
    }

    public void setWxCode(String wxCode) {
        this.wxCode = wxCode;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}
