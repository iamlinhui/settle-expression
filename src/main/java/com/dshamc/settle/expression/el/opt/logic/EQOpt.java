package com.dshamc.settle.expression.el.opt.logic;

/**
 * 等于
 */
public class EQOpt extends AbstractCompareOpt {

    public int fetchPriority() {
        return 7;
    }

    public Boolean calculate() {
        return compare() == 0;
    }

    public String fetchSelf() {
        return "==";
    }
}
