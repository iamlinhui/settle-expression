package cn.holmes.settle.expression.lang.opt;

import java.util.Queue;

/**
 * 操作符
 */
public interface Operator {

    /**
     * 优先级
     */
    int fetchPriority();

    /**
     * 打包数据.
     * 每个操作符都有相应的操作数,这个方法目的在于,根据操作符自身的需求,从operand中读取相应的操作数
     *
     * @param operand 操作数
     */
    void wrap(Queue<Object> operand);

    /**
     * 计算
     */
    Object calculate();

}
