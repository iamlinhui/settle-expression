package com.holmes.settle.expression.lang.opt;

import com.holmes.settle.expression.common.ElException;
import com.holmes.settle.expression.common.element.SettleDecimal;
import com.holmes.settle.expression.lang.obj.Elobj;

/**
 * 操作符抽象类
 */
public abstract class AbstractOpt implements Operator {
    /**
     * 操作符对象自身的符号
     */
    public abstract String fetchSelf();

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj.equals(fetchSelf())) {
            return true;
        }
        return super.equals(obj);
    }

    public String toString() {
        return String.valueOf(fetchSelf());
    }

    /**
     * 计算子项
     */
    protected Object calculateItem(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof SettleDecimal) {
            return obj;
        }
        if (obj instanceof Number) {
            return SettleDecimal.warp(obj.toString());
        }
        if (obj instanceof Boolean) {
            return obj;
        }
        if (obj instanceof String) {
            return obj;
        }
        if (obj instanceof Elobj) {
            return ((Elobj) obj).fetchVal();
        }
        if (obj instanceof Operator) {
            return ((Operator) obj).calculate();
        }
        throw new ElException("未知计算类型!" + obj);

    }
}
