/**
 * 
 */
package cc.royao.wx.tag;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * Created on 2014-9-23
 * <p>Title:       智企云_[子平台名]_[模块名]/p>
 * <p>Description: [描述该类概要功能介绍]</p>
 * <p>Copyright:   Copyright (c) 2014</p>
 * <p>Company:     长沙亿网电子商务有限公司</p>
 * @author        沈友军 me@joyphper.net
 * @version        1.0
*/
public abstract class TagBean {
    protected final Logger logger = Logger.getLogger(super.getClass());

    @SuppressWarnings("unchecked")
    public Map<String, String> parameterToBean(String param) throws Exception {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap = JSON.parseObject("{" + param + "}", Map.class);
        return paramMap;
    }

    public abstract Object getObject(Map<String, String> parameterMap) throws Exception;
}
