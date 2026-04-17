package cn.holmes.settle.expression.lang.opt.arithmetic;

import cn.holmes.settle.expression.common.converter.TypeConverter;
import cn.holmes.settle.expression.common.element.SettleDecimal;
import cn.holmes.settle.expression.lang.opt.AbstractOpt;
import cn.holmes.settle.expression.lang.opt.Operator;
import cn.holmes.settle.expression.lang.opt.object.InvokeMethodOpt;

import java.util.Queue;

/**
 * 负号:'-'
 */
public class NegativeOpt extends AbstractOpt {

    private Object right;

    public int fetchPriority() {
        return 2;
    }

    public void wrap(Queue<Object> operand) {
        right = operand.poll();
    }

    public Object calculate() {
        SettleDecimal rval = TypeConverter.convert(calculateItem(this.right), SettleDecimal.class);
        return rval.negate();
    }

    public String fetchSelf() {
        return "-";
    }

    public static boolean isNegative(Object prev) {
        if (prev == null) {
            return true;
        }
        if (prev instanceof Object[]) {
            Object[] tmp = (Object[]) prev;
            if (tmp.length == 0) {
                return true;
            }
            // 最后一个
            prev = tmp[tmp.length - 1];
        }
        // 右括号或方法调用后面的 '-' 是减法，不是负号
        if (prev instanceof RBracketOpt || prev instanceof InvokeMethodOpt) {
            return false;
        }
        // 任何操作符后面的 '-' 都是负号
        return prev instanceof Operator;
    }

}
