package cc.royao.commons.enums;

public enum LogsMethod {
		
		GST{
			public String getName(){
				return "消费";
			}
		},
		WXPAY{
			public String getName(){
				return "充值";
			}
		},
		KITING{
			public String getName(){
				return "提现";
			}
		},
		CARD{
			public String getName(){
				return "充值卡充值";
			}
		}
		;
		
		public abstract String getName();
}
