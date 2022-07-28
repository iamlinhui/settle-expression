package com.holmes.settle.expression.lang.opt.logic;

/**
 * 小于
 */
public class LtOpt extends AbstractCompareOpt {

    public int fetchPriority() {
        return 6;
    }

    public String fetchSelf() {
        return "<";
    }

    public Object calculate() {
        return compare() < 0;
    }

}
