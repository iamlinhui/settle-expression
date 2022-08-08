package cn.holmes.settle.expression.common;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBean implements ApplicationContextAware, DisposableBean {

    private static volatile ApplicationContext shareApplicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (shareApplicationContext == null) {
            synchronized (Object.class) {
                if (shareApplicationContext == null) {
                    shareApplicationContext = applicationContext;
                }
            }
        }
    }

    @Override
    public void destroy() {
        if (shareApplicationContext != null) {
            synchronized (Object.class) {
                if (shareApplicationContext != null) {
                    shareApplicationContext = null;
                }
            }
        }
    }

    public static <T> T getBean(Class<T> requiredType) {
        return getApplicationContext().getBean(requiredType);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) getApplicationContext().getBean(beanName);
    }

    private static ApplicationContext getApplicationContext() {
        if (shareApplicationContext == null) {
            throw new ElException("applicationContext属性为null,请检查是否注入成功!");
        }
        return shareApplicationContext;
    }
}
