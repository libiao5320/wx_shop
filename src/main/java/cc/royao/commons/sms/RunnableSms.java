/**
 * 
 */
package cc.royao.commons.sms;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;

import cc.royao.commons.httpClient.HttpProtocolHandler;
import cc.royao.commons.httpClient.HttpRequest;
import cc.royao.commons.httpClient.HttpResponse;
import cc.royao.commons.httpClient.HttpResultType;

/**
 * Created on 2015年1月13日
 * <p>
 * Title: 智企云_[子平台名]_[模块名]/p>
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company: 长沙亿网电子商务有限公司
 * </p>
 * 
 * @author 沈友军 me@joyphper.net
 * @version 1.0
 */
public class RunnableSms implements Runnable {
	private String url;

	private Map<String, String> params;

	private final Logger logger = Logger.getLogger(RunnableSms.class);

	RunnableSms(String url, Map<String, String> params) {
		this.url = url;
		this.params = params;

	}

	public void run() {

		// 待请求参数数组
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.STRING);

		request.setUrl(this.url);
		// 设置编码集
		request.setCharset("UTF-8");

		request.setParameters(HttpProtocolHandler.generatNameValuePair(this.params));

		try {
			HttpResponse response = httpProtocolHandler.execute(request);
			logger.info("RunnableSms.result" + response.getStringResult());
		} catch (HttpException e) {
			logger.error("发送短信", e);
		} catch (IOException e) {
			logger.error("发送短信", e);
		}

	}

}