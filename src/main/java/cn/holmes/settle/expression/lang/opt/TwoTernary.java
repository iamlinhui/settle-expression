package cn.holmes.settle.expression.lang.opt;

import java.util.Queue;

/**
 * 二元运算,只是提取了公共部分
 */
public abstract class TwoTernary extends AbstractOpt {

    protected Object right;
    protected Object left;

    public void wrap(Queue<Object> rpn) {
        right = rpn.poll();
        left = rpn.poll();
    }

    public Object getRight() {
        return calculateItem(right);
    }

    public Object getLeft() {
        return calculateItem(left);
    }
}
