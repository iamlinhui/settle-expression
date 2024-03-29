package cn.holmes.settle.expression.lang.opt.logic;

/**
 * 大于等于
 */
public class GteOpt extends AbstractCompareOpt {

    public int fetchPriority() {
        return 6;
    }

    public Object calculate() {
        return compare() >= 0;
    }

    public String fetchSelf() {
        return ">=";
    }

}
