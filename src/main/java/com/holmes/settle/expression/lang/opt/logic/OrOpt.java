package com.holmes.settle.expression.lang.opt.logic;

import com.holmes.settle.expression.common.converter.TypeConverter;
import com.holmes.settle.expression.lang.opt.TwoTernary;

/**
 * or(||)
 */
public class OrOpt extends TwoTernary {

    public int fetchPriority() {
        return 12;
    }

    public Object calculate() {
        Object lval = calculateItem(left);
        if (null != lval) {
            if (!(lval instanceof Boolean)) {
                // throw new ElException("操作数类型错误!");
                if (Boolean.TRUE.equals(TypeConverter.warp(lval, Boolean.class))) {
                    return true;
                }
            } else if ((Boolean) lval) {
                return true;
            }
        }
        Object rval = calculateItem(right);
        if (null != rval) {
            if (!(rval instanceof Boolean)) {
                // throw new ElException("操作数类型错误!");
                return TypeConverter.warp(rval, Boolean.class);
            }
            if ((Boolean) rval) {
                return true;
            }
        }
        return false;
    }

    public String fetchSelf() {
        return "||";
    }

}
