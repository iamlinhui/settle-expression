package cn.holmes.settle.expression.lang.obj;

import cn.holmes.settle.expression.common.context.Context;
import cn.holmes.settle.expression.common.context.ElCache;
import cn.holmes.settle.expression.common.element.SettleDecimal;

/**
 * 对象
 */
public class AbstractObj implements Elobj {

    private final String val;

    private ElCache ec;

    public AbstractObj(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public Object fetchVal() {
        Context context = ec.getContext();
        if (context != null && context.has(val)) {
            return warp(context.get(val));
        }
        return null;
    }

    protected Object warp(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof SettleDecimal) {
            return obj;
        }
        if (obj instanceof Number) {
            return SettleDecimal.warp(obj.toString());
        }
        return obj;
    }

    public String toString() {
        return val;
    }

    public void setEc(ElCache ec) {
        this.ec = ec;
    }
}
