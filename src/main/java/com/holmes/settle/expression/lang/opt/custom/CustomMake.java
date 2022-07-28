package com.holmes.settle.expression.lang.opt.custom;

import com.holmes.settle.expression.common.ElException;
import com.holmes.settle.expression.common.Mirror;
import com.holmes.settle.expression.lang.opt.RunMethod;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * 自定义函数工厂类
 */
public class CustomMake {

    protected Map<String, RunMethod> runMethodMap = new HashMap<>();

    protected static CustomMake me = new CustomMake();

    static {
        me.init();
    }

    /**
     * 加载插件
     */
    public void init() {
        ServiceLoader<RunMethod> serializations = ServiceLoader.load(RunMethod.class);
        for (RunMethod runMethod : serializations) {
            me().runMethodMap.put(runMethod.fetchSelf(), runMethod);
        }
    }

    /**
     * 自定义方法 工厂方法
     */
    public RunMethod make(String val) {
        return runMethodMap.get(val);
    }

    public static CustomMake me() {
        return me;
    }

    public void register(String name, RunMethod run) {
        runMethodMap.put(name, run);
    }

    public void register(String name, Method method) {
        if (!Modifier.isStatic(method.getModifiers())) {
            throw new ElException("must be static method");
        }
        runMethodMap.put(name, new StaticMethodRunMethod(method));
    }

    public static class StaticMethodRunMethod implements RunMethod {

        protected Method method;

        public StaticMethodRunMethod(Method method) {
            this.method = method;
        }

        public Object run(List<Object> fetchParam) {
            return Mirror.me(method.getDeclaringClass()).invoke(null, method.getName(), fetchParam.toArray());
        }

        public String fetchSelf() {
            return "custom method invoke";
        }
    }
}
