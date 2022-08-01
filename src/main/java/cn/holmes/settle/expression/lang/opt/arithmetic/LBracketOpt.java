package cn.holmes.settle.expression.lang.opt.arithmetic;

import cn.holmes.settle.expression.common.ElException;
import cn.holmes.settle.expression.lang.opt.AbstractOpt;

import java.util.Queue;

/**
 * "("
 */
public class LBracketOpt extends AbstractOpt {

    public String fetchSelf() {
        return "(";
    }

    public int fetchPriority() {
        return 100;
    }

    public void wrap(Queue<Object> obj) {
        throw new ElException("'('符号不能进行wrap操作!");
    }

    public Object calculate() {
        throw new ElException("'('符号不能进行计算操作!");
    }
}
