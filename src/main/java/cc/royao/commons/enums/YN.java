package cc.royao.commons.enums;

public enum YN {
    Y {
        public String getName() {
            return "是";
        }
    },
    N {
        public String getName() {
            return "否";
        }
    },
    on {
    	public String getName() {
    		return "在职";
    	}
    },
    off {
    	public String getName() {
    		return "离职";
    	}
    };

    public abstract String getName();
}
