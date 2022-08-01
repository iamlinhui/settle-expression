package cn.holmes.settle.expression.lang.opt.logic;

/**
 * 大于
 */
public class GtOpt extends AbstractCompareOpt {

    public int fetchPriority() {
        return 6;
    }

    public Object calculate() {
        return compare() > 0;
    }

    public String fetchSelf() {
        return ">";
    }

}
