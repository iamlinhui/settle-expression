package com.holmes.settle.expression.lang.opt.bit;

import com.holmes.settle.expression.common.converter.TypeConverter;
import com.holmes.settle.expression.common.element.SettleDecimal;
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
        SettleDecimal rval = TypeConverter.warp(calculateItem(this.right), SettleDecimal.class);
        return ~rval.getInner().intValue();
    }

    public String fetchSelf() {
        return "~";
    }
}
