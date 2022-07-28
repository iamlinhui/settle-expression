package com.dshamc.settle.expression.lang.opt.arithmetic;

import com.dshamc.settle.expression.common.converter.TypeConverter;
import com.dshamc.settle.expression.common.element.SettleDecimal;
import com.dshamc.settle.expression.lang.opt.AbstractOpt;
import com.dshamc.settle.expression.lang.opt.object.CommaOpt;

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
        SettleDecimal rval = TypeConverter.warp(calculateItem(this.right), SettleDecimal.class);
        return rval.negate();
    }

    public String fetchSelf() {
        return "-";
    }

    public static boolean isNegetive(Object prev) {
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
        if (prev instanceof LBracketOpt) {
            return true;
        }
        if (prev instanceof PlusOpt) {
            return true;
        }
        if (prev instanceof MulOpt) {
            return true;
        }
        if (prev instanceof DivOpt) {
            return true;
        }
        if (prev instanceof ModOpt) {
            return true;
        }
        if (prev instanceof SubOpt) {
            return true;
        }
        if (prev instanceof CommaOpt) {
            return true;
        }
        return false;
    }

}
