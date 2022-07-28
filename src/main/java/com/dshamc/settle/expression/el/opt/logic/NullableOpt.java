package com.dshamc.settle.expression.el.opt.logic;

import com.dshamc.settle.expression.el.opt.AbstractOpt;


import java.util.Queue;

public class NullableOpt extends AbstractOpt {
    
    private Object right;

    public int fetchPriority() {
        return 0;
    }

    public void wrap(Queue<Object> rpn) {
        right = rpn.poll();
    }

    public Object calculate() {
        try {
            return this.calculateItem(right);
        } catch (Throwable e) {
        }
        return null;
    }

    public String fetchSelf() {
        return "!!";
    }

}
