package cn.holmes.settle.expression.lang.opt.object;

import cn.holmes.settle.expression.lang.opt.AbstractOpt;

import java.util.Queue;

/**
 * 方法执行
 * 以方法体右括号做为边界
 */
public class InvokeMethodOpt extends AbstractOpt {
    private Object left;

    public int fetchPriority() {
        return 1;
    }

    public Object calculate() {
        if (left instanceof MethodOpt) {
            return calculateItem(left);
        }
        return null;
    }

    public String fetchSelf() {
        return "method invoke";
    }

    public void wrap(Queue<Object> operand) {
        left = operand.poll();
    }

}
