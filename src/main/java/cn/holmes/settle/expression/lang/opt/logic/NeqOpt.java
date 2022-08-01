package cn.holmes.settle.expression.lang.opt.logic;

/**
 * 不等于
 */
public class NeqOpt extends AbstractCompareOpt {

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
