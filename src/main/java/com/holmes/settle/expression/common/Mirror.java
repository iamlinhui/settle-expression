package com.holmes.settle.expression.common;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class Mirror<T> {

    private final Class<T> clazz;

    private Mirror(Class<T> classOfT) {
        clazz = classOfT;
    }

    /**
     * 包裹一个类
     *
     * @param classOfT 类
     * @return Mirror
     */
    public static <T> Mirror<T> me(Class<T> classOfT) {
        return null == classOfT ? null : new Mirror<T>(classOfT);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> Mirror<T> me(T obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Class<?>) {
            return (Mirror<T>) me((Class<?>) obj);
        }
        if (obj instanceof Enum) {
            return me(((Enum) obj).getDeclaringClass());
        }
        return (Mirror<T>) me(obj.getClass());
    }


    /**
     * @return 当前对象是否在表示日期或时间
     */
    public boolean isDateTimeLike() {
        return Calendar.class.isAssignableFrom(clazz)
                || java.util.Date.class.isAssignableFrom(clazz)
                || java.sql.Date.class.isAssignableFrom(clazz)
                || java.sql.Time.class.isAssignableFrom(clazz);
    }

    /**
     * 判断当前对象是否为一个类型。精确匹配，即使是父类和接口，也不相等
     *
     * @param type 类型
     * @return 是否相等
     */
    public boolean is(Class<?> type) {
        return null != type && clazz == type;
    }

    /**
     * @param type 类型或接口名
     * @return 当前对象是否为一个类型的子类，或者一个接口的实现类
     */
    public boolean isOf(Class<?> type) {
        return type.isAssignableFrom(clazz);
    }

    /**
     * @return 当前对象是否为CharSequence的子类
     */
    public boolean isStringLike() {
        return CharSequence.class.isAssignableFrom(clazz);
    }

    /**
     * @return 当前对象是否为布尔
     */
    public boolean isBoolean() {
        return is(boolean.class) || is(Boolean.class);
    }

    /**
     * @return 当前对象是否为数字
     */
    public boolean isNumber() {
        return Number.class.isAssignableFrom(clazz)
                || clazz.isPrimitive() && !is(boolean.class) && !is(char.class);
    }

    /**
     * @return 当前对象是否为字符
     */
    public boolean isChar() {
        return is(char.class) || is(Character.class);
    }


    /**
     * @return 当前对象是否为枚举
     */
    public boolean isEnum() {
        return clazz.isEnum();
    }


    /**
     * @return 当前对象是否为浮点
     */
    public boolean isFloat() {
        return is(float.class) || is(Float.class);
    }

    /**
     * @return 当前对象是否为双精度浮点
     */
    public boolean isDouble() {
        return is(double.class) || is(Double.class);
    }

    /**
     * @return 当前对象是否为整型
     */
    public boolean isInt() {
        return is(int.class) || is(Integer.class);
    }


    /**
     * 获取一个字段。这个字段可以是当前类型或者其父类的私有字段。
     *
     * @param name 字段名
     * @return 字段
     * @throws NoSuchFieldException
     */
    public Field getField(String name) throws NoSuchFieldException {
        Class<?> cc = clazz;
        while (null != cc && cc != Object.class) {
            try {
                return cc.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                cc = cc.getSuperclass();
            }
        }
        throw new NoSuchFieldException(String.format("Can NOT find field [%s] in class [%s] and it's parents classes",
                name,
                clazz.getName()));
    }

    /**
     * 根据名称获取一个 Getter。
     * <p>
     * 比如，你想获取 abc 的 getter ，那么优先查找 getAbc()，如果没有则查找isAbc()，最后才是查找 abc()。
     *
     * @param fieldName
     * @return 方法
     * @throws NoSuchMethodException 没有找到 Getter
     */
    public Method getGetter(String fieldName) throws NoSuchMethodException {
        return getGetter(fieldName, null);
    }

    /**
     * 优先通过 getter 获取字段值，如果没有，则直接获取字段值
     *
     * @param obj  对象
     * @param name 字段名
     * @return 字段值
     * @throws ElException 既没发现 getter，又没有字段
     */
    @SuppressWarnings("rawtypes")
    public Object getValue(Object obj, String name) throws ElException {
        try {
            return this.getGetter(name).invoke(obj);
        } catch (Exception e) {
            try {
                return getValue(obj, getField(name));
            } catch (NoSuchFieldException e1) {
                if (obj != null) {
                    if (obj.getClass().isArray() && "length".equals(name)) {
                        return Lang.eleSize(obj);
                    }
                    if (obj instanceof Map) {
                        return ((Map) obj).get(name);
                    }
                    if (obj instanceof List) {
                        try {
                            return ((List) obj).get(Integer.parseInt(name));
                        } catch (Exception e2) {
                        }
                    }
                }
                throw makeGetValueException(obj == null ? getType() : obj.getClass(), name, e);
            }
        }
    }

    /**
     * @return 对象类型
     */
    public Class<T> getType() {
        return clazz;
    }

    /**
     * 调用对象的一个方法
     *
     * @param obj        对象
     * @param methodName 方法名
     * @param args       参数
     * @return 调用结果
     */
    public Object invoke(Object obj, String methodName, Object... args) {
        Class<?>[] argsClazz = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argsClazz[i] = args[i].getClass();
        }
        Method method = ReflectionUtils.findMethod(clazz, methodName, argsClazz);
        if (method == null) {
            throw new ElException("method not find");
        }
        return ReflectionUtils.invokeMethod(method, obj, args);
    }

    /**
     * 将字符串首字母大写
     *
     * @param s 字符串
     * @return 首字母大写后的新字符串
     */
    public static String upperFirst(CharSequence s) {
        if (null == s) {
            return null;
        }
        int len = s.length();
        if (len == 0) {
            return "";
        }
        char c = s.charAt(0);
        if (Character.isUpperCase(c)) {
            return s.toString();
        }
        return String.valueOf(Character.toUpperCase(c)) + s.subSequence(1, len);
    }

    /**
     * 根据名称和返回值获取一个 Getter。
     * <p>
     * 比如，你想获取 abc 的 getter ，那么优先查找 getAbc()，如果没有则查找isAbc()，最后才是查找 abc()。
     *
     * @param fieldName  字段名
     * @param returnType 返回值
     * @return 方法
     * @throws NoSuchMethodException 没有找到 Getter
     */
    public Method getGetter(String fieldName, Class<?> returnType) throws NoSuchMethodException {
        String fn = upperFirst(fieldName);
        String getFn = "get" + fn;
        String isFn = "is" + fn;
        Method methodFn = null;
        for (Method method : clazz.getMethods()) {
            if (method.getParameterTypes().length != 0) {
                continue;
            }
            Class<?> mrt = method.getReturnType();
            // 必须有返回类型
            if ("void".equals(mrt.getName())) {
                continue;
            }
            // 如果给了返回类型，用它判断一下
            if (null != returnType && !returnType.equals(mrt)) {
                continue;
            }
            // 有些时候,即使是public的方法,也不一定能访问
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }

            if (getFn.equals(method.getName())) {
                return method;
            }

            if (isFn.equals(method.getName())) {
                if (!Mirror.me(mrt).isBoolean()) {
                    throw new NoSuchMethodException();
                }
                return method;
            }

            if (fieldName.equals(method.getName())) {
                methodFn = method;
            }
        }
        if (methodFn != null) {
            return methodFn;
        }
        throw new NoSuchMethodException(String.format("Fail to find getter for [%s]->[%s]", clazz.getName(), fieldName));
    }


    /**
     * 不调用 getter，直接获得字段的值
     *
     * @param obj 对象
     * @param f   字段
     * @return 字段的值。
     * @throws ElException
     */
    public Object getValue(Object obj, Field f) throws ElException {
        if (!f.isAccessible()) {
            f.setAccessible(true);
        }
        try {
            return f.get(obj);
        } catch (Exception e) {
            throw makeGetValueException(obj.getClass(), f.getName(), e);
        }
    }

    private static RuntimeException makeGetValueException(Class<?> type, String name, Throwable e) {
        return new ElException(String.format("Fail to get value for [%s]->[%s]",
                type.getName(),
                name),
                e);
    }

    public List<Field> getFields() {
        List<Field> fieldList = new ArrayList<>();
        ReflectionUtils.doWithFields(clazz, field -> {
            if (!Modifier.isStatic(field.getModifiers())) {
                fieldList.add(field);
            }
        });
        return fieldList;
    }
}
