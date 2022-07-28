package com.dshamc.settle.expression.el.opt.arithmetic;

import com.dshamc.settle.expression.common.converter.TypeConverter;
import com.dshamc.settle.expression.common.element.SettleDecimal;
import com.dshamc.settle.expression.el.opt.TwoTernary;

/**
 * "-"
 */
public class SubOpt extends TwoTernary {

    public String fetchSelf() {
        return "-";
    }

    public int fetchPriority() {
        return 4;
    }

    public Object calculate() {
        SettleDecimal lval = TypeConverter.warp(calculateItem(this.left), SettleDecimal.class);
        SettleDecimal rval = TypeConverter.warp(calculateItem(this.right), SettleDecimal.class);
        return lval.subtract(rval);
    }

}
