package cn.holmes.settle.expression.lang.opt.logic;

import cn.holmes.settle.expression.common.converter.TypeConverter;
import cn.holmes.settle.expression.lang.opt.TwoTernary;

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
                if (TypeConverter.convert(lval, Boolean.class)) {
                    return true;
                }
            } else if ((Boolean) lval) {
                return true;
            }
        }
        Object rval = calculateItem(right);
        if (null != rval) {
            if (!(rval instanceof Boolean)) {
                return TypeConverter.convert(rval, Boolean.class);
            }
            return rval;
        }
        return false;
    }

    public String fetchSelf() {
        return "||";
    }

}
