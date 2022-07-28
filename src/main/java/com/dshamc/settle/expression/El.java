package com.dshamc.settle.expression;

import com.dshamc.settle.expression.common.ElException;
import com.dshamc.settle.expression.common.ReversePolish;
import com.dshamc.settle.expression.common.ShuntingYard;
import com.dshamc.settle.expression.common.context.Context;
import com.dshamc.settle.expression.common.context.SimpleContext;
import com.dshamc.settle.expression.common.segment.CharSegment;
import com.dshamc.settle.expression.lang.opt.RunMethod;
import com.dshamc.settle.expression.lang.opt.custom.CustomMake;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Queue;

public class El {

    private ReversePolish rc = null;
    private CharSequence elstr = "";

    public El() {
    }

    /**
     * 预编译
     */
    public El(CharSequence cs) {
        elstr = cs;
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
        return elstr.toString();
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
            if (el == null)
                el = new El(key);
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
