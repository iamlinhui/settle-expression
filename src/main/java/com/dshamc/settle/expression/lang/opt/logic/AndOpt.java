package com.dshamc.settle.expression.lang.opt.logic;

import com.dshamc.settle.expression.common.converter.TypeConverter;
import com.dshamc.settle.expression.lang.opt.TwoTernary;

/**
 * and
 */
public class AndOpt extends TwoTernary {

    public int fetchPriority() {
        return 11;
    }

    public Object calculate() {

        Object lval = calculateItem(this.left);
        if (null == lval) {
            return false;
        }

        if (!(lval instanceof Boolean)) {
            if (Boolean.FALSE.equals(TypeConverter.warp(lval, Boolean.class))) {
                return false;
            }
        } else if (!(Boolean) lval) {
            return false;
        }

        Object rval = calculateItem(this.right);
        if (null == rval) {
            return false;
        }
        if (!(rval instanceof Boolean)) {
            // throw new ElException("操作数类型错误!");
            return TypeConverter.warp(rval, Boolean.class);
        }
        return (Boolean) rval;
    }

    public String fetchSelf() {
        return "&&";
    }

}
