package com.dshamc.settle.expression.el.opt.object;

import com.dshamc.settle.expression.el.opt.AbstractOpt;

import java.util.Queue;

/**
 * ']',数组封装.
 * 本身没做什么操作,只是对'[' ArrayOpt 做了一个封装而已
 *
 */
public class FetchArrayOpt extends AbstractOpt {
    private Object left;

    public void wrap(Queue<Object> operand) {
        left = operand.poll();
    }

    public int fetchPriority() {
        return 1;
    }

    public Object calculate() {
        if (left instanceof ArrayOpt) {
            return ((ArrayOpt) left).calculate();
        }
        return null;
    }

    public String fetchSelf() {
        return "]";
    }
}
