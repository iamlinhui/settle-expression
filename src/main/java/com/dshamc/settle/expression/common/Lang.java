package com.dshamc.settle.expression.common;

import com.dshamc.settle.expression.common.context.Context;
import com.dshamc.settle.expression.common.context.SimpleContext;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

public class Lang {

    /**
     * 创建一个新的上下文对象
     *
     * @return 一个新创建的上下文对象
     */
    public static Context context() {
        return new SimpleContext();
    }

    /**
     * 用运行时异常包裹抛出对象，如果抛出对象本身就是运行时异常，则直接返回。
     * <p>
     * 如果是 InvocationTargetException，那么将其剥离，只包裹其 TargetException
     *
     * @param e 抛出对象
     * @return 运行时异常
     */
    public static RuntimeException wrapThrow(Throwable e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        if (e instanceof InvocationTargetException) {
            return wrapThrow(((InvocationTargetException) e).getTargetException());
        }
        return new RuntimeException(e);
    }

    /**
     * 根据一段文本模拟出一个文本输入流对象
     *
     * @param cs 文本
     * @return 文本输出流对象
     */
    public static Reader inr(CharSequence cs) {
        return new StringReader(cs.toString());
    }

    /**
     * 根据格式化字符串，生成运行时异常
     *
     * @param format 格式
     * @param args   参数
     * @return 运行时异常
     */
    public static RuntimeException makeThrow(String format, Object... args) {
        return new RuntimeException(String.format(format, args));
    }

    /**
     * 获得一个容器（Map/集合/数组）对象包含的元素数量
     * <ul>
     * <li>null : 0
     * <li>数组
     * <li>集合
     * <li>Map
     * <li>一般 Java 对象。 返回 1
     * </ul>
     *
     * @param obj
     * @return 对象长度
     */
    public static int eleSize(Object obj) {
        // 空指针，就是 0
        if (null == obj) {
            return 0;
        }
        // 数组
        if (obj.getClass().isArray()) {
            return Array.getLength(obj);
        }
        // 容器
        if (obj instanceof Collection<?>) {
            return ((Collection<?>) obj).size();
        }
        // Map
        if (obj instanceof Map<?, ?>) {
            return ((Map<?, ?>) obj).size();
        }
        // 其他的就是1
        return 1;
    }
}
