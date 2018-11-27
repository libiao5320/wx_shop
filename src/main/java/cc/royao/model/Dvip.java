/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2015
 */

package cc.royao.model;


import cc.royao.model.base.BaseEntity;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


public class Dvip extends BaseEntity implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Dvip";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "等级名称";
	public static final String ALIAS_REQUIRE = "要求达到的金额";
	public static final String ALIAS_REMARK = "备注说明";
	public static final String ALIAS_STATUS = "normal-正常显示(正常使用)，delete-逻辑删除(不显示)";
	
	//date formats
	
	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	
	private Long id;

	private String name;
	
	private Integer require;

	private String remark;

	private String status;
	//columns END

	public Dvip(){
	}

	public Dvip(
		Long id
	){
		this.id = id;
	}

	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setRequire(Integer value) {
		this.require = value;
	}
	
	public Integer getRequire() {
		return this.require;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setStatus(String value) {
		this.status = value;
	}
	
	public String getStatus() {
		return this.status;
	}


}

