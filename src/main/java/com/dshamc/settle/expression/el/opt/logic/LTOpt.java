package com.dshamc.settle.expression.el.opt.logic;

/**
 * 小于
 */
public class LTOpt extends AbstractCompareOpt {

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
