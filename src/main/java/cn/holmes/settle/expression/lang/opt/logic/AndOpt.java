package cn.holmes.settle.expression.lang.opt.logic;

import cn.holmes.settle.expression.common.converter.TypeConverter;
import cn.holmes.settle.expression.lang.opt.TwoTernary;

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
            if (!TypeConverter.convert(lval, Boolean.class)) {
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
            return TypeConverter.convert(rval, Boolean.class);
        }
        return rval;
    }

    public String fetchSelf() {
        return "&&";
    }

}
