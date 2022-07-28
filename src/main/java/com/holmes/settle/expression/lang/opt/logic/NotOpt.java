package com.holmes.settle.expression.lang.opt.logic;

import com.holmes.settle.expression.common.converter.TypeConverter;
import com.holmes.settle.expression.lang.opt.AbstractOpt;

import java.util.Queue;

/**
 * Not(!)
 */
public class NotOpt extends AbstractOpt {
    private Object right;

    public int fetchPriority() {
        return 7;
    }

    public void wrap(Queue<Object> rpn) {
        right = rpn.poll();
    }

    public Object calculate() {
        Object rval = calculateItem(this.right);
        if (null == rval) {
            return true;
        }
        if (rval instanceof Boolean) {
            return !(Boolean) rval;
        }
        return Boolean.FALSE.equals(TypeConverter.warp(rval, Boolean.class));
    }

    public String fetchSelf() {
        return "!";
    }
}
