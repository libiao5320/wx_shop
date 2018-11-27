/**
 * 
 */
package cc.royao.wx.tag;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * Created on 2014-9-23
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
@SuppressWarnings("deprecation")
@Component("tagHandler")
public class TagHandler implements TemplateMethodModel, ApplicationContextAware {
	private final Logger logger = Logger.getLogger(super.getClass());

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (TagHandler.applicationContext == null) {
			TagHandler.applicationContext = applicationContext;
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes" })
	public Object exec(List args) throws TemplateModelException {
		TagResult result = new TagResult();
		if (args == null || args.size() < 1 || args.get(0) == null) {
			result.setMessage("参数不正确");
			return result;
		}

		String beanName = "tag." + args.get(0).toString();

		String parameter = ((args.size() < 2) || (args.get(1) == null)) ? null : args.get(1).toString();

		return this.execTag(beanName, parameter);
	}

	private TagResult execTag(String beanName, String parameter) {
		TagResult result = new TagResult();
		Object bean = TagHandler.applicationContext.getBean(beanName);
		if (bean == null) {
			result.setMessage("标签不存在");
			return result;
		} else {
			TagBean tagBean = (TagBean) bean;
			try {
				Map<String, String> parameterMap = tagBean.parameterToBean(parameter);
				Object value = tagBean.getObject(parameterMap);
				result.setSuccess(true);
				result.setResultData(value);
			} catch (Exception e) {
				result.setMessage(e.getMessage());
				this.logger.error("标签执行失败！", e);
			}
			return result;
		}
	}
}
