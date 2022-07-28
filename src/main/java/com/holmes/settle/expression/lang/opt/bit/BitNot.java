package com.holmes.settle.expression.lang.opt.bit;

import com.holmes.settle.expression.common.converter.TypeConverter;
import com.holmes.settle.expression.lang.opt.AbstractOpt;

import java.util.Queue;

/**
 * 非
 */
public class BitNot extends AbstractOpt {

    private Object right;

    public int fetchPriority() {
        return 2;
    }

    public void wrap(Queue<Object> operand) {
        right = operand.poll();
    }

    public Object calculate() {
        Integer rval = TypeConverter.convert(calculateItem(this.right), Integer.class);
        return ~rval;
    }

    public String fetchSelf() {
        return "~";
    }
}
