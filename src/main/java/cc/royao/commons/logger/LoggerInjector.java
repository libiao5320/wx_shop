package cc.royao.commons.logger;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

public class LoggerInjector implements BeanPostProcessor {

    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }

    public Object postProcessBeforeInitialization(final Object bean,
            String beanName) throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(), new FieldCallback() {
            public void doWith(Field field) throws IllegalArgumentException,IllegalAccessException {
                ReflectionUtils.makeAccessible(field);
                if (field.getAnnotation(SysLogger.class) != null) {
                    Logger logger = Logger.getLogger(bean.getClass());
                    field.set(bean, logger);
                }
            }
        });
        return bean;
    }
}
