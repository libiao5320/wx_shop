package cc.royao.commons.exception;

public class ThirdpartyLoginException extends Exception {

    private static final long serialVersionUID = 2835762630253780808L;

    private String code;

    public ThirdpartyLoginException() {
    }

    public ThirdpartyLoginException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
