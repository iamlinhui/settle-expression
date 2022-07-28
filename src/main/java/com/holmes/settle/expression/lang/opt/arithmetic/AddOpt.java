package com.holmes.settle.expression.lang.opt.arithmetic;

import com.holmes.settle.expression.common.converter.TypeConverter;
import com.holmes.settle.expression.common.element.SettleDecimal;
import com.holmes.settle.expression.lang.opt.TwoTernary;

/**
 * "+"
 */
public class AddOpt extends TwoTernary {

    public int fetchPriority() {
        return 4;
    }

    public String fetchSelf() {
        return "+";
    }

    public Object calculate() {
        Object lval = calculateItem(this.left);
        Object rval = calculateItem(this.right);

        if (lval instanceof String || rval instanceof String) {
            return lval.toString() + rval.toString();
        }

        SettleDecimal nlval = TypeConverter.warp(calculateItem(this.left), SettleDecimal.class);
        SettleDecimal nrval = TypeConverter.warp(calculateItem(this.right), SettleDecimal.class);
        return nlval.add(nrval);
    }

}
