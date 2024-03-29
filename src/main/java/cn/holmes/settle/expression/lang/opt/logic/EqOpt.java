package cn.holmes.settle.expression.lang.opt.logic;

/**
 * 等于
 */
public class EqOpt extends AbstractCompareOpt {

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
