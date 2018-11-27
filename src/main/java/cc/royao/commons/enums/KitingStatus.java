package cc.royao.commons.enums;

public enum KitingStatus {

	
	HANDLE_OK{
		public String getName(){
			return "处理成功";
		}
	},
	AUDIT{
		public String getName(){
			return "审核中";
		}
	};
	
	public abstract String getName();
}
