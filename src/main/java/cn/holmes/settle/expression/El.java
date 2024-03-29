package cn.holmes.settle.expression;

import cn.holmes.settle.expression.common.ElException;
import cn.holmes.settle.expression.common.ReversePolish;
import cn.holmes.settle.expression.common.ShuntingYard;
import cn.holmes.settle.expression.common.context.Context;
import cn.holmes.settle.expression.common.context.SimpleContext;
import cn.holmes.settle.expression.common.converter.TypeConverter;
import cn.holmes.settle.expression.common.segment.CharSegment;
import cn.holmes.settle.expression.lang.opt.RunMethod;
import cn.holmes.settle.expression.lang.opt.custom.CustomMake;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Queue;

public class El {

    private ReversePolish rc = null;
    private CharSequence el = "";

    public El() {
    }

    /**
     * 预编译
     */
    public El(CharSequence cs) {
        el = cs;
        ShuntingYard sy = new ShuntingYard();
        Queue<Object> rpn = sy.parseToRPN(cs.toString());
        rc = new ReversePolish(rpn);
    }

    /**
     * 解析预编译后的EL表达式
     */
    public Object eval(Context context) {
        if (rc == null) {
            throw new ElException("没有进行预编译!");
        }
        return rc.calculate(context);
    }

    public <T> T eval(Context context, Class<T> clazz) {
        Object result = eval(context);
        return TypeConverter.convert(result, clazz);
    }

    public static <T> T eval(String val, Class<T> clazz) {
        return eval(null, val, clazz);
    }

    public static <T> T eval(Context context, String val, Class<T> clazz) {
        Object result = eval(context, val);
        return TypeConverter.convert(result, clazz);
    }

    /**
     * 对参数代表的表达式进行运算
     */
    public static Object eval(String val) {
        //逆波兰表示法（Reverse Polish notation，RPN，或逆波兰记法）
        return eval(null, val);
    }

    public static Object eval(Context context, String val) {
        ShuntingYard sy = new ShuntingYard();
        ReversePolish rc = new ReversePolish();
        Queue<Object> rpn = sy.parseToRPN(val);
        return rc.calculate(context, rpn);
    }

    public String toString() {
        return el.toString();
    }

    /**
     * 说明:
     * 1. 操作符优先级参考<Java运算符优先级参考图表>, 但不完全遵守,比如"()"
     * 2. 使用Queue 的原因是,调用peek()方法不会读取串中的数据.
     * 因为我希望达到的效果是,我只读取我需要的,我不需要的数据我不读出来.
     */
    public static String render(String seg, Context ctx) {
        return render(new CharSegment(seg), ctx);
    }

    public static String render(CharSegment seg, Context ctx) {
        Context main = new SimpleContext();
        for (String key : seg.keys()) {
            main.set(key, new El(key).eval(ctx));
        }
        return String.valueOf(seg.render(main));
    }

    public static String render(CharSegment seg, Map<String, El> els, Context ctx) {
        Context main = new SimpleContext();
        for (String key : seg.keys()) {
            El el = els.get(key);
            if (el == null) {
                el = new El(key);
            }
            main.set(key, el.eval(ctx));
        }
        return String.valueOf(seg.render(main));
    }

    public static void register(String name, RunMethod run) {
        CustomMake.me().register(name, run);
    }

    public static void register(String name, Method method) {
        CustomMake.me().register(name, method);
    }
}
