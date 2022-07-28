package com.dshamc.settle.expression.el.opt.arithmetic;

import com.dshamc.settle.expression.common.converter.TypeConverter;
import com.dshamc.settle.expression.common.element.SettleDecimal;
import com.dshamc.settle.expression.el.opt.TwoTernary;

/**
 * "+"
 */
public class PlusOpt extends TwoTernary {

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
