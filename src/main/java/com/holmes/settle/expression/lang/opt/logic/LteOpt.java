package com.holmes.settle.expression.lang.opt.logic;

/**
 * 小于等于
 */
public class LteOpt extends AbstractCompareOpt {

    public int fetchPriority() {
        return 6;
    }

    public Object calculate() {
        return compare() <= 0;
    }

    public String fetchSelf() {
        return "<=";
    }
}
