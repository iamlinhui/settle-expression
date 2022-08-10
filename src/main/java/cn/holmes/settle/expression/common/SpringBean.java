package cn.holmes.settle.expression.common;

import cn.holmes.settle.expression.common.annotation.HolmesFunction;
import cn.holmes.settle.expression.lang.opt.custom.CustomMake;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

@Configuration
public class SpringBean implements ApplicationContextAware, DisposableBean, BeanPostProcessor {

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

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        final List<Method> res = new LinkedList<>();
        ReflectionUtils.doWithMethods(clazz, method -> {
            if (Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())) {
                if (method.getAnnotation(HolmesFunction.class) != null) {
                    res.add(method);
                }
            }
        });
        for (Method method : res) {
            HolmesFunction holmesFunction = method.getAnnotation(HolmesFunction.class);
            CustomMake.me().register(holmesFunction.value(), method);
        }
        return bean;
    }
}
