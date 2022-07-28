package com.holmes.settle.expression.lang.opt.logic;

/**
 * 不等于
 */
public class NEQOpt extends AbstractCompareOpt {

    public int fetchPriority() {
        return 6;
    }

    public Object calculate() {
        return compare() != 0;
    }

    public String fetchSelf() {
        return "!=";
    }

}
