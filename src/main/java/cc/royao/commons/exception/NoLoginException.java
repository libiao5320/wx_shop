/**
 * 
 */
package cc.royao.commons.exception;

/**
 * Created on 2014-9-19
 * <p>Title:       智企云_[子平台名]_[模块名]/p>
 * <p>Description: [描述该类概要功能介绍]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     长沙亿网电子商务有限公司</p>
 * @author        沈友军 me@joyphper.net
 * @version        1.0
*/
public class NoLoginException extends Exception {

    private static final long serialVersionUID = 1L;

    public NoLoginException() {
    }

    public NoLoginException(String message) {
        super(message);
    }

    public NoLoginException(Throwable cause) {
        super(cause);
    }

    public NoLoginException(String message, Throwable cause) {
        super(message, cause);
    }

}
